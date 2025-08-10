package util;

import io.github.cdimascio.dotenv.Dotenv;

public class GoogleFacebookConfig {
    // Load file .env từ path tuyệt đối
    private static final Dotenv dotenv = Dotenv.configure()
            .directory("D:/FPT_Uni/PRJ/PRJAssignment/PRJAssignment") // Dùng \\ khi viết trong string Java
            .filename(".env") // tên file
            .load();

    public static final String CLIENT_ID = dotenv.get("GOOGLE_CLIENT_ID");
    public static final String CLIENT_SECRET = dotenv.get("GOOGLE_CLIENT_SECRET");
    public static final String REDIRECT_URI_GOOGLE = dotenv.get("GOOGLE_REDIRECT_URI");
    public static final String TOKEN_URL = dotenv.get("TOKEN_URL");
    public static final String USERINFO_URL = dotenv.get("USERINFO_URL");

    public static final String FB_APP_ID = dotenv.get("FACEBOOK_APP_ID");
    public static final String FB_APP_SECRET = dotenv.get("FACEBOOK_APP_SECRET");
    public static final String REDIRECT_URI_FB = dotenv.get("FACEBOOK_REDIRECT_URI");
}
