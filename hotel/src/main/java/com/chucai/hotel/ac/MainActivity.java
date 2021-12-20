package com.chucai.hotel.ac;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.bumptech.glide.Glide;
import com.caysn.autoreplyprint.AutoReplyPrint;
import com.chucai.hotel.R;
import com.chucai.hotel.ac.dialog.InsertCardDialog;
import com.chucai.hotel.control.Printusb;
import com.donkingliang.banner.CustomBanner;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.LongByReference;
import com.xtf.xtflib.widget.bam.BamConstraintLayout;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private static final long WEATHER_TIME = 30*60*1000;
    private CustomBanner banner;
    private BamConstraintLayout xianchangRuzhuLayout;
    private BamConstraintLayout dingdanRuzhuLayout;
    private BamConstraintLayout fangkedengjiLayout;
    private BamConstraintLayout xuzhujiezhangLayout;

    private ConstraintLayout timeLayout;
    private ImageView tianqiIcon;
    private TextView tianQiName;
    private TextView dateTv;
    private TextView timeTv;
    private static int FAN_YE_TIME = 5000;
    public static SimpleDateFormat dayTimeFormate = new SimpleDateFormat("yyyy/MM/dd");
    public static SimpleDateFormat secTimeFormate = new SimpleDateFormat("HH:mm:ss");
    public static LocalWeatherLive slocalWeatherLive;
    private int clickTimes;
    private long lastTouchTime;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    public static LocalWeatherLive getSlocalWeatherLive() {
        return slocalWeatherLive;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        initView();
        initData();
        Printusb.print_init(this);

    }

    AMapLocationClient mLocationClient;

    private void initData() {
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
//声明定位回调监听器
        AMapLocationListener mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                Log.i(TAG, "onLocationChanged: "+aMapLocation.toStr());
                mLocationClient.stopLocation();

                WeatherSearchQuery    mquery = new WeatherSearchQuery(aMapLocation.getCity(), WeatherSearchQuery.WEATHER_TYPE_LIVE);
                WeatherSearch  mweathersearch=new WeatherSearch(MainActivity.this);
                mweathersearch.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {
                    @Override
                    public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int i) {
//                        localWeatherLiveResult.getLiveResult().getWeather();
                        Log.i(TAG, "onWeatherLiveSearched: "+i);
                        if(i==1000) {
                            Log.i(TAG, "onWeatherLiveSearched: " + localWeatherLiveResult.getLiveResult().getWeather());
                            slocalWeatherLive=localWeatherLiveResult.getLiveResult();
                            showPageWeatherInfo();


                        }
                        Log.i(TAG, "onWeatherLiveSearched: ");

                    }

                    @Override
                    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {
                        Log.i(TAG, "onWeatherForecastSearched: "+i);
                    }
                });
                mweathersearch.setQuery(mquery);
                mweathersearch.searchWeatherAsyn(); //异步搜索
            }
        };
//初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
           mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
             mLocationOption.setNeedAddress(true);
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效

            mLocationClient.stopLocation();
             mLocationClient.startLocation();


            List<String> dataList = new ArrayList<>();
            dataList.add("http://cdn.szpuze.com/6aaf591b8598487c9d16c925dca1f109.png");
            dataList.add("http://cdn.szpuze.com/ae001bc8ccb14d7cb810b1eba3646b66.png");
            banner.setPages(new CustomBanner.ViewCreator<String>() {
                @Override
                public View createView(Context context, int position) {
                    //这里返回的是轮播图的项的布局 支持任何的布局
                    //position 轮播图的第几个项
                    ImageView imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    return imageView;
                }

                @Override
                public void updateUI(Context context, View view, int position, String data) {
                    //在这里更新轮播图的UI
                    //position 轮播图的第几个项
                    //view 轮播图当前项的布局 它是createView方法的返回值
                    //data 轮播图当前项对应的数据
                    Glide.with(context).load(data).into((ImageView) view);
                }
            }, dataList);
            xianchangRuzhuLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this,BookRoomActivity.class));
                }
            });

            dingdanRuzhuLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this,OrderSearchActivity.class));

                }
            });

            xuzhujiezhangLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InsertCardDialog insertCardDialog=new InsertCardDialog(MainActivity.this,R.style.dim_dialog);
                    insertCardDialog.show();


                }
            });
            fangkedengjiLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("pyq","vs="+ AutoReplyPrint.INSTANCE.CP_Library_Version());

                    Pointer h = Printusb.getPointer() ;
                    if (h != Pointer.NULL) {
                        Log.d("pyq chk ","pyq chk print");
                        Test_Pos_SampleTicket_58MM_1(h);
                      //  AutoReplyPrint.INSTANCE.CP_Port_Close(h);
                    }



                }
            });




            findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickTimes==0){
                        lastTouchTime=System.currentTimeMillis();
                    }
                    if(System.currentTimeMillis()-lastTouchTime<2000) {
                        clickTimes++;
                        lastTouchTime = System.currentTimeMillis();
                        if (clickTimes >=6) {
                            clickTimes = 0;
                            lastTouchTime = 0;
                            Intent mIntent = new Intent();
                            ComponentName comp = new ComponentName("com.android.settings",
                                    "com.android.settings.Settings");
                            mIntent.setComponent(comp);
                            mIntent.setAction("android.intent.action.VIEW");

                            startActivity(mIntent);

                        }
                    }else {
                        clickTimes=0;
                        lastTouchTime=0;

                    }
                }
            });

        }
    void Test_Label_SampleTicket_58MM_1_WithCut(Pointer h)
    {
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteMode(h);
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteEncoding(h, AutoReplyPrint.CP_MultiByteEncoding_UTF8);

        AutoReplyPrint.INSTANCE.CP_Label_PageBegin(h, 0, 0, 384, 240, 0);
        AutoReplyPrint.INSTANCE.CP_Label_DrawBox(h, 0, 0, 384, 240, 1, 1);
        AutoReplyPrint.INSTANCE.CP_Label_DrawText(h, 10, 10, 24, 0, "型号：P58A+");
        AutoReplyPrint.INSTANCE.CP_Label_DrawText(h, 10, 40, 24, 0, "MFG ：00");
        AutoReplyPrint.INSTANCE.CP_Label_DrawBarcode(h, 10, 70, AutoReplyPrint.CP_Label_BarcodeType_CODE128, AutoReplyPrint.CP_Label_BarcodeTextPrintPosition_BelowBarcode, 60, 2, 0, "No.123456");
        AutoReplyPrint.INSTANCE.CP_Label_PagePrint(h, 1);
        //AutoReplyPrint.INSTANCE.CP_Pos_HalfCutPaper(h);
        AutoReplyPrint.INSTANCE.CP_Pos_FullCutPaper(h);

        {
            boolean result = AutoReplyPrint.INSTANCE.CP_Pos_QueryPrintResult(h, 30000);
            if (!result)
                Log.i(TAG, "Print failed"+result);
            else
                Log.i(TAG, "Print Success"+result);
        }
    }


    void Label_SampleTicket_WithCut(Pointer h) {


        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteMode(h);
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteEncoding(h, AutoReplyPrint.CP_MultiByteEncoding_UTF8);
        AutoReplyPrint.INSTANCE.CP_Label_PageBegin(h, 0, 0, 576, 240, 0);
        AutoReplyPrint.INSTANCE.CP_Label_DrawBox(h, 0, 0, 576, 240, 1, 1);
        AutoReplyPrint.INSTANCE.CP_Label_DrawText(h, 60, 20, 24, 0, "福家智能国际酒店");
        AutoReplyPrint.INSTANCE.CP_Label_DrawText(h, 60, 60, 24, 0, "   房间号码："+"12345678");
        AutoReplyPrint.INSTANCE.CP_Label_DrawText(h, 60, 100, 24, 0, "   房   型："+"99999");
        AutoReplyPrint.INSTANCE.CP_Label_DrawText(h, 60, 140, 24, 0, "   房   费："+"88888");
        AutoReplyPrint.INSTANCE.CP_Label_DrawText(h, 60, 180, 24, 0, "   押   金："+"6666");
        AutoReplyPrint.INSTANCE.CP_Label_DrawText(h, 60, 220, 24, 0, "   入住时间："+"2021-08-08");
        AutoReplyPrint.INSTANCE.CP_Label_DrawText(h, 60, 260, 24, 0, "   离店时间："+"2021-08-09");
        AutoReplyPrint.INSTANCE.CP_Label_DrawText(h, 60, 300, 24, 0, "   联系方式："+"123456789");
        AutoReplyPrint.INSTANCE.CP_Label_PagePrint(h, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_FullCutPaper(h);
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_QueryPrintResult(h, 30000);
        if (!result)
            Log.i(TAG, "Print failed"+result);
        else
            Log.i(TAG, "Print Success"+result);
    }
    private Bitmap getImageFromAssetsFile(String fileName) {
        Bitmap image = null;
        AssetManager am = getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    private boolean QueryPrintResult(Pointer h)
    {
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_QueryPrintResult(h, 30000);
        Log.i(TAG, result ? "Print Success" : "Print Failed");
        if (!result) {
            LongByReference printer_error_status = new LongByReference();
            LongByReference printer__info_status = new LongByReference();
            LongByReference timestamp_ms_printer_status = new LongByReference();
            if (AutoReplyPrint.INSTANCE.CP_Printer_GetPrinterStatusInfo(h, printer_error_status, printer__info_status, timestamp_ms_printer_status)) {
                AutoReplyPrint.CP_PrinterStatus status = new AutoReplyPrint.CP_PrinterStatus(printer_error_status.getValue(), printer__info_status.getValue());
                String error_status_string = String.format("Printer Error Status: 0x%04X", printer_error_status.getValue() & 0xffff);
                if (status.ERROR_OCCURED()) {
                    if (status.ERROR_CUTTER())
                        error_status_string += "[ERROR_CUTTER]";
                    if (status.ERROR_FLASH())
                        error_status_string += "[ERROR_FLASH]";
                    if (status.ERROR_NOPAPER())
                        error_status_string += "[ERROR_NOPAPER]";
                    if (status.ERROR_VOLTAGE())
                        error_status_string += "[ERROR_VOLTAGE]";
                    if (status.ERROR_MARKER())
                        error_status_string += "[ERROR_MARKER]";
                    if (status.ERROR_ENGINE())
                        error_status_string += "[ERROR_ENGINE]";
                    if (status.ERROR_OVERHEAT())
                        error_status_string += "[ERROR_OVERHEAT]";
                    if (status.ERROR_COVERUP())
                        error_status_string += "[ERROR_COVERUP]";
                    if (status.ERROR_MOTOR())
                        error_status_string += "[ERROR_MOTOR]";
                }
                Log.i(TAG, error_status_string);
            } else {
                Log.i(TAG, "CP_Printer_GetPrinterStatusInfo Failed");
            }
        }
        return result;
    }

    private void Test_Pos_SampleTicket_58MM_1(Pointer h)
    {
        if (h != Pointer.NULL) {

            Bitmap bitmap = getImageFromAssetsFile("RasterImage/blackwhite.png");
            AutoReplyPrint.CP_Pos_PrintRasterImageFromData_Helper.PrintRasterImageFromBitmap(h, bitmap.getWidth(), bitmap.getHeight(), bitmap, AutoReplyPrint.CP_ImageBinarizationMethod_Thresholding, AutoReplyPrint.CP_ImageCompressionMethod_None);
/*
            if (kickDrawer) {
                AutoReplyPrint.INSTANCE.CP_Pos_KickOutDrawer(h, 0, 100, 100);
                AutoReplyPrint.INSTANCE.CP_Pos_KickOutDrawer(h, 1, 100, 100);
            }

            if (cutPaper) {
                AutoReplyPrint.INSTANCE.CP_Pos_FeedAndHalfCutPaper(h);
            } else {
                AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 10);
            }

 */
            AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 10);
            QueryPrintResult(h);


            AutoReplyPrint.INSTANCE.CP_Port_Close(h);
        }
    }


    private void showPageWeatherInfo() {
        if(slocalWeatherLive!=null) {
            slocalWeatherLive.getTemperature();
            Calendar cal = Calendar.getInstance();
            int i = cal.get(Calendar.HOUR_OF_DAY);

            String name = "weather/day/undefined" + slocalWeatherLive.getWeather() + ".png";
            if (i > 7 && i < 19) {
                name = "weather/day/" + slocalWeatherLive.getWeather() + ".png";
            } else {
                name = "weather/night/" + slocalWeatherLive.getWeather() + ".png";
            }
            try {
                InputStream open = getResources().getAssets().open(name);

                tianqiIcon.setImageBitmap(BitmapFactory.decodeStream(open));
                tianQiName.setText(slocalWeatherLive.getTemperature() + "℃  " + slocalWeatherLive.getWeather());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
        protected void onResume () {
            super.onResume();
            banner.startTurning(FAN_YE_TIME);
            startPageTime();
        }

        @Override
        protected void onPause () {
            super.onPause();
            banner.stopTurning();
            canclePageTime();

        }

        private void initView () {
            banner = (CustomBanner) findViewById(R.id.banner);
            xianchangRuzhuLayout = (BamConstraintLayout) findViewById(R.id.xianchang_ruzhu_layout);
            dingdanRuzhuLayout = (BamConstraintLayout) findViewById(R.id.dingdan_ruzhu_layout);
            fangkedengjiLayout = (BamConstraintLayout) findViewById(R.id.fangkedengji_layout);
            xuzhujiezhangLayout = (BamConstraintLayout) findViewById(R.id.xuzhujiezhang_layout);
            timeLayout = (ConstraintLayout) findViewById(R.id.time_layout);
            tianqiIcon = (ImageView) findViewById(R.id.tianqi_icon);
            tianQiName = (TextView) findViewById(R.id.tian_qi_name);
            dateTv = (TextView) findViewById(R.id.date_tv);
            timeTv = (TextView) findViewById(R.id.time_tv);
        }
        Timer pageTimeTimer;

        public void startPageTime () {
            if (pageTimeTimer != null) {
                pageTimeTimer.cancel();
            }
            pageTimeTimer = null;
            pageTimeTimer = new Timer();
            pageTimeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Date d = new Date();
                            dateTv.setText(dayTimeFormate.format(d));
                            timeTv.setText(secTimeFormate.format(d));
                        }
                    });
                }
            }, 1000, 1000);
        }

        public void canclePageTime () {
            if (pageTimeTimer != null) {
                pageTimeTimer.cancel();
            }
            pageTimeTimer = null;
        }
    private Timer weatherQueryTimer;

        public void startWeatherQueryTimer(){
            if(weatherQueryTimer!=null){
                weatherQueryTimer.cancel();
                weatherQueryTimer=null;
            }
            weatherQueryTimer=new Timer();
            weatherQueryTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(mLocationClient!=null){
                        mLocationClient.stopLocation();
                        mLocationClient.startLocation();

                    }
                }
            },WEATHER_TIME, WEATHER_TIME);
        }

}
