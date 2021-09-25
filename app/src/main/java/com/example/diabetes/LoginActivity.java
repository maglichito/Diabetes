package com.example.diabetes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.diabetes.data.API;
import com.example.diabetes.data.Network;
import com.example.diabetes.data.SharedPrefManager;
import com.example.diabetes.data.User;
import com.example.diabetes.data.VolleySingleton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private API api = new API();
    private EditText email_login,password_login;
    private ProgressBar bar;
    private ImageButton login_button;
    private TextView text;
    private Network network = new Network(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);
        bar = findViewById(R.id.bar_login);
        login_button = findViewById(R.id.login_button);
        text = findViewById(R.id.error);

        login_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                // If device connected to network then attempt login
                if(network.isNetworkAvailable()){
                        userLogin();
                }else{
                    Toast.makeText(getApplicationContext(), "You are not connected to a network.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    // this method will log user in
    private void userLogin() {

        //URL
        String URL = api.getLogin();
        //first getting the values
        final String email = email_login.getText().toString();
        final String password = password_login.getText().toString();
        //validating inputs
        if (TextUtils.isEmpty(email)) {
            email_login.setError("This field is required.");
            email_login.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_login.setError("Enter valid e-mail.");
            email_login.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            password_login.setError("This field is required!");
            password_login.requestFocus();
            return;
        }
        text.setVisibility(View.INVISIBLE);
        bar.setVisibility(View.VISIBLE);
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);
                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                String ads = "";
                                if(userJson.getBoolean("ads")){
                                    ads = "1";
                                }else{
                                    ads = "0";
                                }
                                //creating a new user object
                                User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("first_name"),
                                        userJson.getString("last_name"),
                                        userJson.getString("email"),
                                        userJson.getString("diabetes_type"),
                                        userJson.getString("diabetic_since"),
                                        userJson.getString("country"),
                                        ads,
                                        obj.getString("access_token")
                                );
                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                bar.setVisibility(View.INVISIBLE);

                        } catch (JSONException e) {
                            text.setText("Server side error.");
                            text.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            //This indicates that the reuest has either time out or there is no connection
                            text.setText("Check your connection.");
                            text.setVisibility(View.VISIBLE);
                        } else if (error instanceof AuthFailureError) {
                            // Error indicating that there was an Authentication Failure while performing the request
                            text.setText("Wrong information.");
                            text.setVisibility(View.VISIBLE);
                        } else if (error instanceof ServerError) {
                            //Indicates that the server responded with a error response
                            text.setText("Server side error.");
                            text.setVisibility(View.VISIBLE);
                        } else if (error instanceof NetworkError) {
                            //Indicates that there was network error while performing the request
                            text.setText("Network error.");
                            text.setVisibility(View.VISIBLE);
                        } else if (error instanceof ParseError) {
                            // Indicates that the server response could not be parsed
                            text.setText("Server side error.");
                            text.setVisibility(View.VISIBLE);
                        }
                        else if (error instanceof ClientError) {
                            text.setText("Wrong information.");
                            text.setVisibility(View.VISIBLE);
                        }else{
                            text.setText("Server side error.");
                            text.setVisibility(View.VISIBLE);
                        }
                        bar.setVisibility(View.INVISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
