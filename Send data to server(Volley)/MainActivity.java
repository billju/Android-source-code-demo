package com.example.chuboy.senddatatoserver;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button SendButton;
    private EditText EmailInput, PasswordInput;
    private AlertDialog.Builder builder;
    //URL為ip位址加上伺服器中login.php所在的位址
    String server_url = "http://192.168.1.102/mystore/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SendButton = (Button)findViewById(R.id.send);
        EmailInput = (EditText)findViewById(R.id.Email_input);
        PasswordInput = (EditText)findViewById(R.id.Password_input);
        builder = new AlertDialog.Builder(MainActivity.this);

        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                final String email_string, password_string;
                email_string = EmailInput.getText().toString();
                password_string = PasswordInput.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response){
                                builder.setTitle("Server Response");
                                builder.setMessage(response);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        EmailInput.setText("");
                                        PasswordInput.setText("");
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        },new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error){
                                Toast.makeText(MainActivity.this,"Error...",Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                            }
                        }
                ){

                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError{
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("email", email_string);
                        params.put("password", password_string);
                        return params;
                    }

                };
                requestQueue.add(stringRequest);
            }
        });

    }
}
