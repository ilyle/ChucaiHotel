package com.example.e4_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    Button bt_id;
    ImageView img;
    TextView id ,qianm,info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_id =(Button)findViewById(R.id.bt_id);
        img = (ImageView)findViewById(R.id.img);
        id=(TextView)findViewById(R.id.id);
        qianm=(TextView)findViewById(R.id.qianming);
        info=(TextView)findViewById(R.id.info);

        bt_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,change_layout.class);
                startActivityForResult(intent,011);
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,img_change_layout.class);
                startActivityForResult(intent,014);
            }
        });
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==014 && resultCode==014){
            Bundle bundle =data.getExtras();
            int picId=bundle.getInt("pic");
            ImageView imageView = (ImageView)findViewById(R.id.img);
            img.setImageResource(picId);
        }
        if(requestCode==011 && resultCode==011){
            Bundle bundle =data.getExtras();
            id.setText(bundle.getString("ID"));
            qianm.setText(bundle.getString("签名"));
            info.setText(bundle.getString("个人信息"));



        }
    }
}