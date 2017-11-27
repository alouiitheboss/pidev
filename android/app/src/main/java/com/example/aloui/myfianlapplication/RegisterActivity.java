package com.example.aloui.myfianlapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aloui.myfianlapplication.Entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    ArrayList<User> users;
    ListView listUsers;
Button register;
EditText username;
EditText password;
EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username  = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);

        users = new ArrayList<>();
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.start();
        String url = "http://10.0.2.2:18080/pidev-web/api/users/add";
        Log.i("link", url);
        register = findViewById(R.id.buttonadd);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue mRequestQueue = Volley.newRequestQueue(RegisterActivity.this);

                mRequestQueue.start();

                String URL = "http://10.0.2.2:18080/pidev-web/api/users/add";
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("username", username.getText().toString());
                params.put("password", password.getText().toString());
                params.put("email",email.getText().toString());


                JsonObjectRequest request_json = new JsonObjectRequest(URL, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(getBaseContext(),"Added "  , Toast.LENGTH_LONG);

                                //Process os success response
                                Log.i("tessdfsdft", "test");
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error123: ", error.getMessage());
                error.getStackTrace();
                        Toast.makeText(getBaseContext(),"Error !"  , Toast.LENGTH_LONG);

                    }
                });
                mRequestQueue.add(request_json);

            }
        });

        //added by oussef
       /* register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                String URL = "http://10.0.2.2:18080/pidev-web/api/users/add";
                StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.d("response", response);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error.Response", error.getMessage());

                            }
                        }
                ){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String,String> headers=

                        return super.getHeaders();
                    }


                };
                queue.add(postRequest);
            }
        });*/

        //


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject j = array.getJSONObject(i);
                                User u = new User(10, "test@gmail.com", "test", "test12345");
                                users.add(u);
                                Log.i("contrat", u.toString());
                                Log.i("sdffff", users.get(0).toString());
                                System.out.println(u);


                            }
                            //     listComments.setAdapter(new CommentCustomAdapter(getActivity(), R.layout.one_article, comments));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        System.out.println("Erreur " + error.getMessage());
                    }
                });

        mRequestQueue.add(stringRequest);
        users.add(new User(10, "test@gmail.com", "test", "test"));

    }

}
