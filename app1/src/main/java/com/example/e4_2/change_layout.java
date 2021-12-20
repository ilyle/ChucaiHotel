package com.example.e4_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

public class change_layout  extends Activity {
    String editText1,editText2,editText3;
    Button bt;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.change);

        bt=(Button)findViewById(R.id.bt_tj);
        editText1 =((EditText)findViewById(R.id.edit_query1)).getText().toString();
        editText2 =((EditText)findViewById(R.id.edit_query2)).getText().toString();
        editText3 =((EditText)findViewById(R.id.edit_query3)).getText().toString();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =getIntent();
                Bundle bundle=new Bundle();
//                bundle.putInt();
                bundle.putCharSequence("ID",editText1);
                bundle.putCharSequence("签名",editText2);
                bundle.putCharSequence("个人信息",editText3);
                intent.putExtras(bundle);
                setResult(011,intent);

                finish();
            }
        });


    }
}
