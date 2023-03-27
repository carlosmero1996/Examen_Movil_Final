package edu.tecazuay.movil_examen_final.examen_movil_final.CarlosMero;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Ingresardatos extends AppCompatActivity {

    private EditText titulo, descripcion, precio;
    Button botoncito1;
    private RequestQueue request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresardatos);

        botoncito1=findViewById(R.id.idbtnguardar);
        botoncito1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos();
            }
        });

        titulo=findViewById(R.id.idtxtTitulo);
        descripcion=findViewById(R.id.iddescripcion);
        precio=findViewById(R.id.idprecio);
        request= Volley.newRequestQueue(this);



    }


    public void guardarDatos() {
        String url = "https://fakestoreapi.com/products";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("title", titulo.getText().toString());
            jsonObject.put("price", precio.getText().toString());
            jsonObject.put("description", descripcion.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(Ingresardatos.this, "los datos se guardaron correctamente", Toast.LENGTH_SHORT).show();
                        titulo.setText("");
                        descripcion.setText("");
                        precio.setText("");
                        Intent intent = new Intent(getApplication(),MainActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Ingresardatos.this, "los datos no se guardaron", Toast.LENGTH_SHORT).show();
            }

        });
        request.add(jsonObjectRequest);
    }


}