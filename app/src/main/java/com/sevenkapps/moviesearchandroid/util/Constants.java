package com.sevenkapps.moviesearchandroid.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Constants {

    public static final String DEV_EMULATOR_ROOT_URL = "https://10.0.2.2:3000";
    public static final String DEV_USB_ROOT_URL = "https://localhost:3000";
    public static final String ROOT_URL = DEV_EMULATOR_ROOT_URL;

    public static String POST_IMAGES_URL = ROOT_URL + "/api/images.json";
    public static String GOOGLE_LOGIN_URL = ROOT_URL + "/api/google_token.json";

    public static String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    public static String encodeImage(String path) {
        File imagefile = new File(path);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(imagefile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        //Base64.de
        return encImage;

    }

    public static String escapeForJava(String value, boolean quote) {
        StringBuilder builder = new StringBuilder();
        if (quote)
            builder.append("\"");
        for (char c : value.toCharArray()) {
            if (c == '\'')
                builder.append("\\'");
            else if (c == '\"')
                builder.append("\\\"");
            else if (c == '\r')
                builder.append("\\r");
            else if (c == '\n')
                builder.append("\\n");
            else if (c == '\t')
                builder.append("\\t");
            else if (c < 32 || c >= 127)
                builder.append(String.format("\\u%04x", (int) c));
            else
                builder.append(c);
        }
        if (quote)
            builder.append("\"");
        return builder.toString();
    }
}
