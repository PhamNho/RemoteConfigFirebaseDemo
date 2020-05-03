package com.fpoly.remoteconfigfirebasedemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fpoly.remoteconfigfirebasedemo.R;
import com.fpoly.remoteconfigfirebasedemo.models.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button btnTest;
    private ImageView imgTest;
    private Button btnUpdate;

    FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTest = findViewById(R.id.btnTest);
        imgTest = findViewById(R.id.imgTest);
        btnUpdate = findViewById(R.id.btnUpdate);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder().build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        Map<String, Object> defaultData = new HashMap<>();


//        // C1:
//        defaultData.put("btn_text", "Version 1.0");
//        defaultData.put("btn_enable", false);
//        defaultData.put("image_link", "https://giaybongro.vn/upload/images/anta/6411_1560157608.jpg");
//        // -----------------------------------------------------


        // C2: JSON
        defaultData.put("update_data", "{\"btn_text\":\"Version Default\",\"btn_enable\":false,\"image_link\":\"https://znews-photo.zadn.vn/w1024/Uploaded/kbd_bcvi/2019_11_23/5d828d976f24eb1a752053b5.jpg\"}");
        // -----------------------------------------------------


        mFirebaseRemoteConfig.setDefaultsAsync(defaultData);
        Picasso.get().load("https://giaybongro.vn/upload/images/anta/6411_1560157608.jpg").into(imgTest);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseRemoteConfig.fetch(0)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    mFirebaseRemoteConfig.activate();


//                                    // C1:
//                                    btnTest.setText(mFirebaseRemoteConfig.getString("btn_text"));
//                                    btnTest.setEnabled(mFirebaseRemoteConfig.getBoolean("btn_enable"));
//                                    Picasso.get().load(mFirebaseRemoteConfig.getString("image_link")).into(imgTest);
//                                    //  ------------------------------------------


                                    // C2: JSON
                                    Model model = new Gson().fromJson(mFirebaseRemoteConfig.getString("update_data"),
                                            new TypeToken<Model>() {
                                            }.getType());
                                    if (model != null) {
                                        btnTest.setText(model.getBtn_text());
                                        btnTest.setEnabled(model.isBtn_enable());
                                        Picasso.get().load(model.getImage_link()).into(imgTest);
                                    }
                                    // --------------------------------------------

                                } else {
                                    Toast.makeText(MainActivity.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
