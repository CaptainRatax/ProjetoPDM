package tk.milkenm.tirarfoto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {
    Api Api = new Api();
    static final int IMG_REQUEST = 21;
    public RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setEnabled(true);

        Button button = findViewById(R.id.button);
        button.setEnabled(true);

        TextView textView = findViewById(R.id.textView);
        textView.setVisibility(View.INVISIBLE);

        if (!checkPermission()) {
            requestPermission();
        }
    }

    public void button_takePhoto_click(View v) {
        Intent takePictureIntent = new Intent();
        takePictureIntent.setType("image/*");
        takePictureIntent.setAction(Intent.ACTION_GET_CONTENT);
        try {
            startActivityForResult(takePictureIntent, IMG_REQUEST);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "There was an error with the selection.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected synchronized void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {

            Uri path = data.getData();
            try {

                ImageView imageView = findViewById(R.id.imageView);
                imageView.setEnabled(false);

                Button button = findViewById(R.id.button);
                button.setEnabled(false);

                TextView textView = findViewById(R.id.textView);
                textView.setVisibility(View.VISIBLE);

                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
                String json = "{\n" +
                        "    \"Titulo\":\"Cano do painel rachado\",\n" +
                        "    \"Descricao\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla cursus ante in metus sollicitudin, at dictum neque lobortis. Aenean eu odio faucibus, porta dui a, vestibulum odio. Nulla et velit vitae metus blandit fringilla non at diam. Sed venenatis lectus in aliquam tristique. Maecenas non luctus elit. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vestibulum volutpat lacus sit amet odio fringilla ullamcorper. Sed non iaculis sapien. Donec nec mattis tellus. Nullam pretium tellus lectus, eu blandit libero venenatis sed. Nunc vitae scelerisque sem, eget viverra mauris. Sed ullamcorper diam vitae vulputate volutpat. Mauris dignissim nulla libero, at varius ipsum iaculis in. Pellentesque venenatis lorem diam, vitae porttitor sem iaculis in. Fusce ultrices cursus ullamcorper. Aenean blandit libero non ante tincidunt laoreet.\",\n" +
                        "    \"IdObra\":1,\n" +
                        "    \"ListaDeImagens\":[\n" +
                        "        \"" + imageEncoded + "\"\n" +
                        "    ]\n" +
                        "}";

                String BASE_URL = "https://testesandrepinto.outsystemscloud.com/BackofficePDM/rest/PDMAPI/Casos/Criar";
                requestQueue = Volley.newRequestQueue(MainActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objres = new JSONObject(response);
                            Toast.makeText(MainActivity.this, objres.getString("Message"), Toast.LENGTH_LONG).show();

                            String idOnly = objres.getString("Message").replaceAll("\\D+","");

                            String BASE_URL2 = "https://testesandrepinto.outsystemscloud.com/BackofficePDM/rest/PDMAPI/Caso?Id=" + idOnly;
                            requestQueue = Volley.newRequestQueue(MainActivity.this);
                            StringRequest stringRequest2 = new StringRequest(Request.Method.GET, BASE_URL2, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject objres = new JSONObject(response);
                                        JSONArray fotografias = objres.getJSONArray("Fotografias");
                                        String ficheiroEncoded = fotografias.getJSONObject(0).getString("Ficheiro");
                                        byte[] decodedByte = Base64.decode(ficheiroEncoded, 0);
                                        Bitmap asdasd = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);

                                        ImageView imageView = findViewById(R.id.imageView);
                                        imageView.setEnabled(true);

                                        Button button = findViewById(R.id.button);
                                        button.setEnabled(true);

                                        TextView textView = findViewById(R.id.textView);
                                        textView.setVisibility(View.INVISIBLE);

                                        imageView.setImageBitmap(asdasd);
                                    } catch (JSONException e) {
                                        ImageView imageView = findViewById(R.id.imageView);
                                        imageView.setEnabled(true);

                                        Button button = findViewById(R.id.button);
                                        button.setEnabled(true);

                                        TextView textView = findViewById(R.id.textView);
                                        textView.setVisibility(View.INVISIBLE);
                                        Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    ImageView imageView = findViewById(R.id.imageView);
                                    imageView.setEnabled(true);

                                    Button button = findViewById(R.id.button);
                                    button.setEnabled(true);

                                    TextView textView = findViewById(R.id.textView);
                                    textView.setVisibility(View.INVISIBLE);
                                    error.toString();
                                }
                            });
                            requestQueue.add(stringRequest2);

                        } catch (JSONException e) {
                            ImageView imageView = findViewById(R.id.imageView);
                            imageView.setEnabled(true);

                            Button button = findViewById(R.id.button);
                            button.setEnabled(true);

                            TextView textView = findViewById(R.id.textView);
                            textView.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ImageView imageView = findViewById(R.id.imageView);
                        imageView.setEnabled(true);

                        Button button = findViewById(R.id.button);
                        button.setEnabled(true);

                        TextView textView = findViewById(R.id.textView);
                        textView.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this,"Erro",Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    public String getBodyContentType() { return "application/json; charset=utf-8"; }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return json == null ? null : json.getBytes("utf-8");
                        }catch(UnsupportedEncodingException uee){
                            return null;
                        }
                    }
                };
                requestQueue.add(stringRequest);
            } catch (IOException e) {
                ImageView imageView = findViewById(R.id.imageView);
                imageView.setEnabled(true);

                Button button = findViewById(R.id.button);
                button.setEnabled(true);

                TextView textView = findViewById(R.id.textView);
                textView.setVisibility(View.INVISIBLE);
                e.printStackTrace();
            }
        }
    }

    private static final int PERMISSION_REQUEST_CODE = 200;


    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow the camera permission",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }
}