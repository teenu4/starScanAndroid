package com.sevenkapps.moviesearchandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.sevenkapps.moviesearchandroid.R;
import com.sevenkapps.moviesearchandroid.db.AccessToken;
import com.sevenkapps.moviesearchandroid.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FacebookLoginActivity extends BaseActivity {


    private static final String EMAIL = "email";

    CallbackManager callbackManager;
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Map<String, String> params = new HashMap();
                params.put("access_token", loginResult.getAccessToken().getToken());
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
                                    AccessToken.createToken(getApplicationContext(), accessToken, "facebook", name, image);

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
                final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext(), getHurlStack());

                requestQueue.add(jsonObjectRequest);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
