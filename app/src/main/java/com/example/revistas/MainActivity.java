package com.example.revistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.revistas.Adapter.AdapterRevistas;
import com.example.revistas.Model.revistas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycleRevis;

    ArrayList<revistas> lstRevis;
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycleRevis =new RecyclerView(this);
        recycleRevis =(RecyclerView)findViewById(R.id.recy);

        recycleRevis.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));



        lstRevis =new ArrayList<revistas>();
        progress=new ProgressDialog(this);
        progress.setMessage("Consultando...");
        progress.show();
        String url="https://revistas.uteq.edu.ec/ws/journals.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsoreq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        revistas revis = null;
                        try {
                            for (int i=0;i<response.length();i++){
                                revis = new revistas();
                                JSONObject obj = response.getJSONObject(i);
                                revis.setId(obj.getString("journal_id"));
                                revis.setPortada(obj.getString("portada"));
                                revis.setDescripcion(obj.getString("description"));
                                revis.setTitulo(obj.getString("name"));



                                lstRevis.add(revis);
                            }

                            AdapterRevistas adapter=new AdapterRevistas(lstRevis, MainActivity.this);
                            adapter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    int opcselec= recycleRevis.getChildAdapterPosition(view);
                                    String nombreselec= lstRevis.get(opcselec).getId();
                                    Intent intent = new Intent(MainActivity.this,EdicionesRevista.class);

                                    Bundle b = new Bundle();
                                    b.putString("revistaID", nombreselec);
                                    intent.putExtras(b);

                                    startActivity(intent);

                                }
                            });

                            recycleRevis.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();


                        }
                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        queue.add(jsoreq);

    }
}