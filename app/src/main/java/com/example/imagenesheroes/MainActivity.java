package com.example.imagenesheroes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    Adaptador adapter;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.listaNotas);
        requestQueue = Volley.newRequestQueue(this);
        peticionSimple();
    }

    public void peticionSimple() {
        String url = "https://simplifiedcoding.net/demos/view-flipper/heroes.php";
        JsonRequest jsonRequest = new JsonObjectRequest(
                url, null, response -> {
//            Gson gson = new Gson();
//            Type listType =
//                    new TypeToken<List<Heroes>>() {}.getType();
//            List<Heroes> list = gson.fromJson(response.toString(), listType);
//            ArrayList<Heroes> list = gson.fromJson(String.valueOf(response), (Type) Heroes.class);
//            for (Heroes itm : list){
//                String job = "";
//                job = itm.getName();
//                job += "\n" + itm.getImageurl();
//                Log.d("VOLLGSON", job);
//            }
            try {
                List<String> names = new ArrayList<>();
                List<String> imageurls = new ArrayList<>();
                JSONArray item = item = response.getJSONArray("heroes");
                for (int i = 0; i < item.length(); i++) {
                    JSONObject jsonObject;
                    jsonObject = item.getJSONObject(i);
                    names.add(jsonObject.getString("name"));
                    imageurls.add(jsonObject.getString("imageurl"));
                }
                String[] nom = new String[names.size()];
                String[] img = new String[names.size()];
                for (int i = 0; i < names.size(); i++) {
                    nom[i] = names.get(i);
                    img[i] = imageurls.get(i);
                }
                adapter = new Adaptador(getApplicationContext(), nom, img);
                lista.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },
                error -> {
                    Log.d("VOLL", "Error \n" + error.toString());
                });
        requestQueue.add(jsonRequest);
    }

    class Heroes{
        String name;
        String imageurl;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }
    }
}