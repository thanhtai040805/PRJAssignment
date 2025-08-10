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

@WebServlet("/oauth2callback/facebook")
public class FacebookCallbackServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(FacebookCallbackServlet.class.getName());

    // Thông tin Facebook App
    private static final String APP_ID = GoogleFacebookConfig.FB_APP_ID;
    private static final String APP_SECRET = GoogleFacebookConfig.FB_APP_SECRET;
    private static final String REDIRECT_URI = GoogleFacebookConfig.REDIRECT_URI_FB;

    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("PRJPU");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String code = request.getParameter("code");
        if (code == null) {
            response.sendRedirect(request.getContextPath() + "/login?error=facebook");
            return;
        }

        EntityManager em = null;
        try {
            // 1. Lấy access_token từ Facebook
            String tokenUrl = "https://graph.facebook.com/v17.0/oauth/access_token"
                    + "?client_id=" + APP_ID
                    + "&redirect_uri=" + java.net.URLEncoder.encode(REDIRECT_URI, "UTF-8")
                    + "&client_secret=" + APP_SECRET
                    + "&code=" + code;
            String tokenResponse = HttpUtil.get(tokenUrl);
            JSONObject tokenJson = new JSONObject(tokenResponse);
            String accessToken = tokenJson.getString("access_token");

            // 2. Lấy profile user từ Facebook
            String userInfoUrl = "https://graph.facebook.com/me?fields=id,name,email&access_token=" + accessToken;
            String userInfoResponse = HttpUtil.get(userInfoUrl);
            JSONObject profile = new JSONObject(userInfoResponse);
            String email = profile.optString("email", profile.getString("id") + "@facebook.com");
            String name = profile.optString("name", "Người dùng Facebook");

            em = emf.createEntityManager();
            UserDao userDao = new UserDao();
            userDao.setEntityManager(em);
            CustomerDAO customerDao = new CustomerDAO();
            customerDao.setEntityManager(em);

            em.getTransaction().begin();

            // 3. Tìm user qua email
            User existingUser = userDao.findByCustomerEmail(email);
            if (existingUser == null) {
                // Nếu chưa có, tạo Customer và User mới
                Customer newCus = new Customer();
                newCus.setFullName(name);
                newCus.setEmail(email);
                newCus.setCreatedDate(new Date());
                newCus.setStatus("Hoạt động");
                customerDao.add(newCus);
                em.flush();

                User newUser = new User();
                newUser.setUsername(email);
                newUser.setPassword(""); // social login
                newUser.setRole("khachhang");
                newUser.setStatus("Hoạt động");
                newUser.setCustomerId(newCus.getCustomerId());
                userDao.add(newUser);

                existingUser = newUser;
            }

            em.getTransaction().commit();

            // Đăng nhập session
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", existingUser);
            if (existingUser.getCustomerId() != null) {
                Customer cus = customerDao.getById(existingUser.getCustomerId());
                session.setAttribute("loggedInCustomer", cus);
            }
            response.sendRedirect(request.getContextPath() + "/");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Facebook login callback error", e);
            response.sendRedirect(request.getContextPath() + "/login?error=facebook");
        } finally {
            if (em != null) em.close();
        }
    }
}
