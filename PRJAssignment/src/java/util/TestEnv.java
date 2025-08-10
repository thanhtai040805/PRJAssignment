package util;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author This PC
 */
public class TestEnv {
    public static void main(String[] args) {
        System.out.println("GOOGLE_CLIENT_ID       = " + GoogleFacebookConfig.CLIENT_ID);
        System.out.println("GOOGLE_CLIENT_SECRET   = " + GoogleFacebookConfig.CLIENT_SECRET);
        System.out.println("GOOGLE_REDIRECT_URI    = " + GoogleFacebookConfig.REDIRECT_URI_GOOGLE);
        System.out.println("TOKEN_URL              = " + GoogleFacebookConfig.TOKEN_URL);
        System.out.println("USERINFO_URL           = " + GoogleFacebookConfig.USERINFO_URL);
        System.out.println("FACEBOOK_APP_ID        = " + GoogleFacebookConfig.FB_APP_ID);
        System.out.println("FACEBOOK_APP_SECRET    = " + GoogleFacebookConfig.FB_APP_SECRET);
        System.out.println("FACEBOOK_REDIRECT_URI  = " + GoogleFacebookConfig.REDIRECT_URI_FB);
    }
}
