package com.chucai.hotel.ac.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chucai.hotel.R;
import com.chucai.hotel.animutils.AnimationsContainerResId;
import com.chucai.hotel.control.CardControl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class InsertCardDialog extends Dialog {
    private ImageView mBackBtn;
    private TextView mPageTimeTv;
    private ImageView mImage;
    List<Integer> idList;
    AnimationDrawable animationDrawable1;
    AnimationsContainerResId.FramesSequenceAnimation animation1;

    public InsertCardDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_insert_card);
        initView();


    }

    private void initView() {
        mBackBtn = (ImageView) findViewById(R.id.back_btn);
        mPageTimeTv = (TextView) findViewById(R.id.page_time_tv);
        mImage = (ImageView) findViewById(R.id.image);
//        try {
//            idList.clear();
////            files = sortFileByName(files, "asc");
//            idList=new ArrayList<>();
//            for (int i = 1; i < 7; i++) {
////                Bitmap bitmap = BitmapFactory.decodeFile(files[i].getAbsolutePath());
//                int id=0;
//                if(i<10) {
//                    id = getContext().getResources().getIdentifier("chaka_0" + i, "drawable", getContext().getPackageName());
//                }
//                Log.i("xtf", "initView: "+id);
//                idList.add(id);
//            }
//            Integer[] integers = idList.toArray(new Integer[idList.size()]);
//            animation1= AnimationsContainerResId.getInstance(getContext(),integers,20).createProgressDialogAnim(mImage);
//            animation1.start();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width=-1;
        attributes.height=-1;
        getWindow().setAttributes(attributes);
        mImage.setImageResource(R.drawable.a1_list);
         animationDrawable1 = (AnimationDrawable) mImage.getDrawable();
        animationDrawable1.start();
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }





    @Override
    public void show() {
        super.show();
        startPageTimeCount();
        animationDrawable1.start();
        CardControl.newInstance().huiShouCard();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(animation1!=null){
            animation1.stop();
        }
        if(pageTimer!=null){
            pageTimer.cancel();
            pageTimer=null;
        }
        CardControl.newInstance().setNoCardMode();

    }



    Timer pageTimer;
    private void startPageTimeCount(){
        if(pageTimer!=null){
            pageTimer.cancel();
        }
        int[] time={
                21
        };
        pageTimer=new Timer();
        pageTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                time[0]--;
                mImage.post(new Runnable() {
                    @Override
                    public void run() {
                            mPageTimeTv.setText(time[0]+"s");
                            if(time[0]==0){
                                dismiss();
                            }
                    }
                });

            }
        },0,1000);

    }






}
