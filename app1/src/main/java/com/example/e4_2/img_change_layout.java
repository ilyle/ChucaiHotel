package com.example.e4_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class img_change_layout extends Activity {
    public int[] pic ={
            R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,
    };
    GridView gridView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chenge_img);

        gridView = (GridView)findViewById(R.id.gd);
        BaseAdapter adapter= new BaseAdapter() {
            @Override
            public int getCount() {
                return pic.length;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView imageView;
                if (convertView==null){
                    imageView=new ImageView(img_change_layout.this);
                    imageView.setMaxWidth(150);
                    imageView.setMaxHeight(150);
                    imageView.setAdjustViewBounds(true);
                    imageView.setPadding(5,5,5,5);

                }else {
                    imageView =(ImageView)convertView;
                }
                imageView.setImageResource(pic[position]);

                return imageView;
            }
        };
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =getIntent();
                Bundle bundle=new Bundle();
                bundle.putInt("pic",pic[position]);
                intent.putExtras(bundle);
                setResult(014,intent);

                finish();
            }
        });


    }
}
