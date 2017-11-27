package com.example.aloui.myfianlapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aloui.myfianlapplication.Entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UsersListActivity extends AppCompatActivity {
    ListView listuser ;
    ArrayList<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        users= new ArrayList<>();
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
listuser   = findViewById(R.id.commentList);
        mRequestQueue.start();

        String url = "http://10.0.2.2:18080/pidev-web/api/users/";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        Log.i("response tag",response);
                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject j = array.getJSONObject(i);
                                User u = new User(j.getInt("id"),j.getString("email"),
                                        j.getString("username"));
                                users.add(u);
                                Log.i("contrat" , u.toString());
                                Log.i("sdffff",users.get(0).toString() );
                                System.out.println(u);



                            }
                            listuser.setAdapter(new UserCustomAdapter(getApplicationContext(), R.layout.one_article, users));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        System.out.println("Erreur "+error.getMessage());
                    }
                });

        mRequestQueue.add(stringRequest);
        //posts.add(new Post("dd","dd"));

    }

}
