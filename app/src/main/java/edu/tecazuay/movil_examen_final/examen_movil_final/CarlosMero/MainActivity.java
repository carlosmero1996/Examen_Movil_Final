package edu.tecazuay.movil_examen_final.examen_movil_final.CarlosMero;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    ArrayList<String> Datos = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    ListView listView;

    Button botoncito;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botoncito=findViewById(R.id.idbotoncito);
        botoncito.setOnClickListener(this::abrir);

        listView = findViewById(R.id.list_view_noticias);
        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, Datos);

        listView.setAdapter(arrayAdapter);
        obtenerDatos();

    }



    public void abrir(View view) {
        Intent intent = new Intent(this, Ingresardatos.class);
        startActivity(intent);
    }

    private void obtenerDatos() {
        String url = "https://fakestoreapi.com/products";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //manejo el json
                mmanejarJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    public void mmanejarJson(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
                ModeloApi publicacion = new ModeloApi();
                publicacion.setId(jsonObject.getInt("id"));
                publicacion.setTitle(jsonObject.getString("title"));
                publicacion.setDescription(jsonObject.getString("description"));
                publicacion.setPrice(jsonObject.getDouble("price"));

                //almacenamos en arraylist los datos dl json
                Datos.add(String.valueOf(publicacion.getId()));
                Datos.add(publicacion.getTitle());
                Datos.add(publicacion.getDescription());
                Datos.add(String.valueOf(publicacion.getPrice()));
            } catch (JSONException jsonException) {
                jsonException.getMessage();
            }
            arrayAdapter.notifyDataSetChanged();
        }
    }


}