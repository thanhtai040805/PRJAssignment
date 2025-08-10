package controller;

import customerDAO.CustomerDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Customer;
import model.User;
import org.json.JSONObject;
import userDao.UserDao;
import util.HttpUtil;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.GoogleFacebookConfig;

@WebServlet("/oauth2callback/google")
public class GoogleCallbackServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(GoogleCallbackServlet.class.getName());

    // Thông tin Google OAuth2
    private static final String CLIENT_ID = GoogleFacebookConfig.CLIENT_ID;
    private static final String CLIENT_SECRET = GoogleFacebookConfig.CLIENT_SECRET;
    private static final String REDIRECT_URI = GoogleFacebookConfig.REDIRECT_URI_GOOGLE;
    private static final String TOKEN_URL = GoogleFacebookConfig.TOKEN_URL;
    private static final String USERINFO_URL = GoogleFacebookConfig.USERINFO_URL;

    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("PRJPU");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String code = request.getParameter("code");
        if (code == null) {
            response.sendRedirect(request.getContextPath() + "/login?error=google");
            return;
        }

        EntityManager em = null;
        try {
            // 1. Lấy access_token từ Google
            String tokenResponse = HttpUtil.postForm(TOKEN_URL, java.util.Map.of(
                    "code", code,
                    "client_id", CLIENT_ID,
                    "client_secret", CLIENT_SECRET,
                    "redirect_uri", REDIRECT_URI,
                    "grant_type", "authorization_code"
            ));
            JSONObject tokenJson = new JSONObject(tokenResponse);
            String accessToken = tokenJson.getString("access_token");

            // 2. Lấy thông tin user từ Google
            String userInfoResponse = HttpUtil.get(USERINFO_URL + "?access_token=" + accessToken);
            JSONObject profile = new JSONObject(userInfoResponse);
            String email = profile.getString("email").trim();
            String name = profile.optString("name", "Người dùng Google").trim();

            LOGGER.info("Google profile: email=" + email + ", name=" + name);

            // 3. Làm việc với DB
            em = emf.createEntityManager();
            UserDao userDao = new UserDao();
            userDao.setEntityManager(em);
            CustomerDAO customerDao = new CustomerDAO();
            customerDao.setEntityManager(em);

            User loadedUser;

            em.getTransaction().begin();
            try {
                loadedUser = userDao.findByCustomerEmail(email);

                if (loadedUser == null) {
                    LOGGER.info("User chưa tồn tại -> tạo mới");

                    // Tạo Customer (chỉ cần email + tên)
                    Customer newCus = new Customer();
                    newCus.setFullName(name);
                    newCus.setEmail(email);
                    newCus.setCreatedDate(new Date());
                    newCus.setStatus("Hoạt động");
                    customerDao.add(newCus);
                    em.flush();

                    // Tạo User
                    User newUser = new User();
                    newUser.setUsername(email);
                    newUser.setPassword(""); // Social login => không đặt pass
                    newUser.setRole("khachhang");
                    newUser.setStatus("Hoạt động");
                    newUser.setCustomerId(newCus.getCustomerId());
                    userDao.add(newUser);
                    em.flush();

                    LOGGER.info("Tạo mới User thành công với ID=" + newUser.getUserId());
                    loadedUser = newUser;
                } else {
                    LOGGER.info("User đã tồn tại, ID=" + loadedUser.getUserId());
                }

                em.getTransaction().commit();
            } catch (Exception ex) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                LOGGER.log(Level.SEVERE, "Lỗi khi tạo mới User/Customer", ex);
                response.sendRedirect(request.getContextPath() + "/login?error=google_db");
                return;
            }

            // 4. Lưu vào session
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", loadedUser);
            if (loadedUser.getCustomerId() != null) {
                Customer cus = customerDao.getById(loadedUser.getCustomerId());
                session.setAttribute("loggedInCustomer", cus);
            }

            // 5. Về trang chủ
            response.sendRedirect(request.getContextPath() + "/");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Google login callback error", e);
            response.sendRedirect(request.getContextPath() + "/login?error=google");
        } finally {
            if (em != null) em.close();
        }
    }
}
