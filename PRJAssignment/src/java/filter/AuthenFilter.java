//package filter;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.FilterConfig;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//
//@WebFilter("/*")
//public class AuthenFilter implements Filter {
//    
//    // Danh sách các URL không cần xác thực
//    private static final List<String> PUBLIC_URLS = Arrays.asList(
//        "/", "/home", "/login", "/register", "/logout",
//        "/css/", "/js/", "/images/", "/assets/",
//        "/error", "/403", "/404", "/500"
//    );
//    
//    // Danh sách các URL chỉ dành cho ADMIN
//    private static final List<String> ADMIN_URLS = Arrays.asList(
//        "/admin/"
//    );
//    
//    // Danh sách các URL chỉ dành cho EMPLOYEE
//    private static final List<String> EMPLOYEE_URLS = Arrays.asList(
//        "/employee/"
//    );
//    
//    // Danh sách các URL chỉ dành cho CUSTOMER
//    private static final List<String> CUSTOMER_URLS = Arrays.asList(
//        "/customer/"
//    );
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        // Khởi tạo filter
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        
//        String requestURI = httpRequest.getRequestURI();
//        String contextPath = httpRequest.getContextPath();
//        String path = requestURI.substring(contextPath.length());
//        
//        // Kiểm tra nếu là URL công khai
//        if (isPublicUrl(path)) {
//            chain.doFilter(request, response);
//            return;
//        }
//        
//        // Lấy session và user hiện tại
//        HttpSession session = httpRequest.getSession(false);
//        User currentUser = null;
//        
//        if (session != null) {
//            currentUser = (User) session.getAttribute("currentUser");
//        }
//        
//        // Nếu chưa đăng nhập
//        if (currentUser == null) {
//            // Lưu URL người dùng muốn truy cập để redirect sau khi login
//            session = httpRequest.getSession();
//            session.setAttribute("redirectAfterLogin", requestURI);
//            
//            httpResponse.sendRedirect(contextPath + "/login");
//            return;
//        }
//        
//        // Kiểm tra quyền truy cập
//        if (!hasPermission(currentUser, path)) {
//            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, 
//                                 "Bạn không có quyền truy cập vào trang này.");
//            return;
//        }
//        
//        // Nếu có quyền, tiếp tục request
//        chain.doFilter(request, response);
//    }
//    
//    @Override
//    public void destroy() {
//        // Cleanup filter
//    }
//    
//    /**
//     * Kiểm tra URL có phải là public không
//     */
//    private boolean isPublicUrl(String path) {
//        return PUBLIC_URLS.stream().anyMatch(url -> 
//            path.equals(url) || path.startsWith(url)
//        );
//    }
//    
//    /**
//     * Kiểm tra quyền truy cập của user
//     */
//    private boolean hasPermission(User user, String path) {
//        String role = user.getRole();
//        
//        // Kiểm tra quyền ADMIN
//        if (isAdminPath(path)) {
//            return "ADMIN".equals(role);
//        }
//        
//        // Kiểm tra quyền EMPLOYEE
//        if (isEmployeePath(path)) {
//            return "EMPLOYEE".equals(role) || "ADMIN".equals(role);
//        }
//        
//        // Kiểm tra quyền CUSTOMER
//        if (isCustomerPath(path)) {
//            return "CUSTOMER".equals(role) || "EMPLOYEE".equals(role) || "ADMIN".equals(role);
//        }
//        
//        // Các path khác mặc định cho phép tất cả user đã đăng nhập
//        return true;
//    }
//    
//    /**
//     * Kiểm tra có phải path dành cho Admin
//     */
//    private boolean isAdminPath(String path) {
//        return ADMIN_URLS.stream().anyMatch(path::startsWith);
//    }
//    
//    /**
//     * Kiểm tra có phải path dành cho Employee
//     */
//    private boolean isEmployeePath(String path) {
//        return EMPLOYEE_URLS.stream().anyMatch(path::startsWith);
//    }
//    
//    /**
//     * Kiểm tra có phải path dành cho Customer
//     */
//    private boolean isCustomerPath(String path) {
//        return CUSTOMER_URLS.stream().anyMatch(path::startsWith);
//    }
//}
