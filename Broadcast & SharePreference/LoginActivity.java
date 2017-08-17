package com.example.chuboy.broadcast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ChuBoy on 2017/7/24.
 */

public class LoginActivity extends BaseActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private EditText accountEdit;
    private EditText passwordEdit;
    private Button login;
    private CheckBox rememberPassword;
    @Override
    protected void onCreate(Bundle savedInstanceStated){
        super.onCreate(savedInstanceStated);
        setContentView(R.layout.login);

        accountEdit = (EditText)findViewById(R.id.account);
        passwordEdit = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);

        //使用預設的SharedPreferences
        //SharedPreference pref = getSharedPreferences("data", MODE_PRIVATE).edit();
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        rememberPassword = (CheckBox)findViewById(R.id.remember_password);
        boolean isRemember = pref.getBoolean("remember_password", false);

        if(isRemember){
            //將帳號和密碼放置到文本中
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPassword.setChecked(true);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                //預設帳號為admin 密碼為123456

                if(account.equals("admin") && password.equals("123456")){

                    editor = pref.edit();
                    if(rememberPassword.isChecked()){   //當checkbox被選中時
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", account);
                        editor.putString("password", password);
                    }
                    else{
                        editor.clear();
                    }
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"account or password is invalid",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
