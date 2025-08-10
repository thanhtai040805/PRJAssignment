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
@WebServlet("/login-google")
public class LoginGoogleServlet extends HttpServlet {
    private static final String CLIENT_ID = GoogleFacebookConfig.CLIENT_ID;
    private static final String REDIRECT_URI = GoogleFacebookConfig.REDIRECT_URI_GOOGLE;
    private static final String AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = AUTH_URL + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8")
                + "&response_type=code"
                + "&scope=email%20profile";
        resp.sendRedirect(url);
    }
}


