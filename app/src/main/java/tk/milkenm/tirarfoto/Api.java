package tk.milkenm.tirarfoto;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Api {
    public String responseGlobal = null;
    public RequestQueue requestQueue;
    public synchronized void criarCaso (String data, Context applicationContext){
        final String savedata = data;
        String BASE_URL = "https://testesandrepinto.outsystemscloud.com/BackofficePDM/rest/PDMAPI/Casos/Criar";
        requestQueue = Volley.newRequestQueue(applicationContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres = new JSONObject(response);

                    Toast.makeText(applicationContext, objres.getString("Message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Toast.makeText(applicationContext, "Server Error", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(applicationContext,"Erro",Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public String getBodyContentType() { return "application/json; charset=utf-8"; }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return savedata == null ? null : savedata.getBytes("utf-8");
                }catch(UnsupportedEncodingException uee){
                    return null;
                }
            }
        };
        requestQueue.add(stringRequest);
    }
    public synchronized void getCaso (String id, Context applicationContext){

    }
}
