package com.example.buscarletrademusica;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String url;
    RequestQueue requestQueue;
    private EditText artistName, artistSong;
    private Button searchLyrics;
    private TextView viewLyrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        artistName = findViewById(R.id.ET_ArtistName);
        artistSong = findViewById(R.id.ET_ArtistSongName);
        searchLyrics = findViewById(R.id.BTN_SearchLyric);
        viewLyrics = findViewById(R.id.TV_Lyric);

        searchLyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://api.lyrics.ovh/v1/" + artistName.getText().toString() + "/" + artistSong.getText().toString();
                url.replaceAll("", "%50");
                requestQueue = Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            viewLyrics.setText(jsonObject.getString("lyrics"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Ocorreu um erro na sua busca, por favor verifique se os dados estão corretos e tente novamente.", Toast.LENGTH_LONG).show();
                            }
                        });
                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}
