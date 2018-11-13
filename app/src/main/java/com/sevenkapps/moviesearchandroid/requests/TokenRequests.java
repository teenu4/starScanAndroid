package com.sevenkapps.moviesearchandroid.requests;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sevenkapps.moviesearchandroid.db.AccessToken;
import com.sevenkapps.moviesearchandroid.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TokenRequests extends BaseRequests {
    public static void getTokenFromServer(final Context context, String type, String value) {
        Map<String, String> params = new HashMap();
        params.put("access_token", value);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, Constants.FACEBOOK_LOGIN_URL, parameters, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response: " + response.toString());
                        try {
                            JSONObject userInfo = response.getJSONObject("user");
                            String name = userInfo.getString("name");
                            String image = userInfo.getString("image_url");
                            String accessToken = response.getString("access_token");
                            AccessToken.createToken(context, accessToken, "facebook", name, image);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
