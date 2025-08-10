/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import util.GoogleFacebookConfig;

/**
 *
 * @author This PC
 */

@WebServlet("/login-facebook")
public class LoginFacebookServlet extends HttpServlet {
    private static final String APP_ID = GoogleFacebookConfig.FB_APP_ID;
    private static final String REDIRECT_URI = GoogleFacebookConfig.REDIRECT_URI_FB;
    private static final String AUTH_URL = "https://www.facebook.com/v17.0/dialog/oauth";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = AUTH_URL + "?client_id=" + APP_ID
                + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8")
                + "&scope=email";
        resp.sendRedirect(url);
    }
}
