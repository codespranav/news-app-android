package com.innovexa.apiintro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ArrayList<UserModel> allNewsList;
    private RequestQueue requestQueue;
    RecyclerView recView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        progressBar = findViewById(R.id.progressBar);
        recView = findViewById(R.id.recView);
        recView.setLayoutManager(new LinearLayoutManager(this));
        allNewsList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        // Define the URL for the News API request
        String url = "https://newsdata.io/api/1/news?apikey=pub_28085bda5f01137cbe172a9cc9d64ab4994ba&q=india";

        // Create a JsonObjectRequest for the API call

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        progressBar.setVisibility(View.GONE);
//                        recView.setVisibility(View.VISIBLE);
                        try {
                            JSONArray articlesArray = response.getJSONArray("results");

                            for (int i = 0; i < articlesArray.length(); i++) {
                                JSONObject articleObject = articlesArray.getJSONObject(i);
                                UserModel singleData = new UserModel(articleObject.getString("title"),
                                        articleObject.getString("link"),
                                        articleObject.getString("description"), articleObject.getString("content"),
                                        articleObject.getString("image_url"), articleObject.getString("language"),
                                        articleObject.getString("pubDate"));
                                allNewsList.add(singleData);
                            }
                            Log.d("myApp", "Number of articles: " + allNewsList.size());
                            recView.setAdapter(new NewsAdapter(getApplicationContext(), allNewsList));

                            // Now you can use 'allNewsList' to display the data in your app's UI or perform other operations.
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("myApp", "JSON parsing error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("myApp", "API request failed: " + error.toString());
                    }
                }
        );

        // Add the request to the request queue
        requestQueue.add(jsonObjectRequest);
    }
}
