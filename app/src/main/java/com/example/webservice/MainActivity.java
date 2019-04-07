package com.example.webservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ListView lis_clientes;
    private ArrayAdapter adapter;
    private String getAllClientsURL = "https://ferreteriajfgo.herokuapp.com/api_clientes?user_hash=12345&action=get";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        lis_clientes = (ListView)findViewById(R.id.listview_clientes);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        lis_clientes.setAdapter(adapter);
        webServiceRest(getAllClientsURL);
    }

    private void webServiceRest(String requestURL){
        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
            parseInformation(webServiceResult);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String id_cliente;
        String nombre;
        String apellido_paterno;
        String apellido_materno;
        String telefono;
        String email;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            e.printStackTrace();
        }
        for(int i=0; i<jsonArray.length(); i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_cliente = jsonObject.getString("id_cliente");
                nombre = jsonObject.getString("nombre");
                apellido_paterno = jsonObject.getString("apellido_paterno");
                apellido_materno = jsonObject.getString("apellido_materno");
                telefono = jsonObject.getString("telefono");
                email = jsonObject.getString("email");
                adapter.add("Id: " + id_cliente + "\n" + "Nombre: " + "" + nombre + "\n" + "Apellido paterno: " + apellido_paterno + "\n" + "Apellido materno: " + apellido_materno + "\n" + "Telefono: " + telefono + "\n" + "Email: " + email );
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

}
