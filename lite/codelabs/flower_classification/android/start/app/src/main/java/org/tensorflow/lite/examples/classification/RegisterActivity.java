package org.tensorflow.lite.examples.classification;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    Button registerButton;
    private static EditText editText, editText2, editText3, editText4;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRegisterInfo();
//                showMessage();
//                showMessage("true");
            }
        });
    }


    public void sendRegisterInfo() {
        Log.v("태그", "메시지");
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nickName", editText.getText());
            jsonObject.put("email", editText2.getText());
            jsonObject.put("password", editText3.getText());
            jsonObject.put("password2", editText4.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url("http://ec2-3-36-221-249.ap-northeast-2.compute.amazonaws.com:8080/members/new")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            //          Callback function to check data returned
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body().string() == "true") {
                    showMessage();
                    Log.d("TEST", "성공");
                } else {
//                String resp = response.body().string();
                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                            if (resp == "true") {
                        Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_LONG).show();
//                            }
                    }
                }, 0);
                Log.d("TEST", "실패");
                }




            }
        });
    }

    private void showMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("회원 가입이 완료되었습니다");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

//    private void showMessage(String type) {
//
//        if (type == "true") {
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("안내");
//            builder.setMessage("회원 가입이 완료되었습니다");
//            builder.setIcon(android.R.drawable.ic_dialog_alert);
//
//            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
////                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
////                    startActivity(intent);
//                }
//            });
//
//            AlertDialog dialog = builder.create();
//            dialog.show();
//
//
//        } else {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("안내");
//            builder.setMessage("회원 정보를 다시 입력해주세요");
//            builder.setIcon(android.R.drawable.ic_dialog_alert);
//
//            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            });
//
//            AlertDialog dialog = builder.create();
//            dialog.show();
//
//        }
//    }
}