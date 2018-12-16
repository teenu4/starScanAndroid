package com.sevenkapps.moviesearchandroid.requests;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sevenkapps.moviesearchandroid.db.AccessToken;
import com.sevenkapps.moviesearchandroid.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ImageRequests extends BaseRequests {
    public static void uploadImageToServer(final Context context, String base64) {
        Map<String, String> params = new HashMap();
        params.put("base64", base64);
        params.put("image_name", System.currentTimeMillis() + ".jpg");
        if (AccessToken.authenticated(context)) {
            params.put("access_token", AccessToken.getToken(context));
        }
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constants.POST_IMAGES_URL, parameters, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response: " + response.toString());
//                        try {
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // TODO: Handle error

                    }
                });
        final RequestQueue requestQueue = Volley.newRequestQueue(context, getHurlStack(context));

        requestQueue.add(jsonObjectRequest);
    }

    public static void getImagesFromServer(final Context context) {
        Map<String, String> params = new HashMap();
//        params.put("base64", base64);
//        params.put("image_name", System.currentTimeMillis() + ".jpg");
        if (AccessToken.authenticated(context)) {
            params.put("access_token", AccessToken.getToken(context));
        }
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, Constants.POST_IMAGES_URL, parameters, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response: " + response.toString());
//                        try {
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // TODO: Handle error

                    }
                });
        final RequestQueue requestQueue = Volley.newRequestQueue(context, getHurlStack(context));

        requestQueue.add(jsonObjectRequest);
    }

}
