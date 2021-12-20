//package com.chucai.hotel;
//
//
//import android.Manifest;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.hardware.usb.UsbDevice;
//import android.hardware.usb.UsbManager;
//import android.os.Build;
//import android.os.Handler;
//import android.os.Message;
//import android.support.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import com.icod.printer.R;
//import com.icod.printer.base.BaseActivity;
//import com.icod.printer.utils.DataUtils;
//import com.icod.printer.utils.StatusDescribe;
//import com.icod.serial.SerialPortFinder;
//import com.szsicod.print.escpos.PrinterAPI;
//import com.szsicod.print.io.BluetoothAPI;
//import com.szsicod.print.io.InterfaceAPI;
//import com.szsicod.print.io.SerialAPI;
//import com.szsicod.print.io.SocketAPI;
//import com.szsicod.print.io.USBAPI;
//import com.szsicod.print.io.UsbNativeAPI;
//import com.szsicod.print.utils.BitmapUtils;
//
//import java.io.File;
//import java.io.UnsupportedEncodingException;
//
//public class MainActivity extends BaseActivity implements View.OnClickListener {
//    private static final String TAG = "MainActivity";
//    private static final int DISCONNECT = -5;           // 断开连接
//    private static final int NOCONNECT = -6;            // 未连接
//    private static final int TOAST_CODE = 10001;        // 吐司弹出
//    private static final int REQUEST_CODE_INTENT = 10002;
//
//    // View
//    private Button btnConnect;
//    private Spinner spConnectType;
//    private LinearLayout llSerial, llIp;
//    private Spinner spDeviceList, spBaudRate, spFlowcontrol;
//    private EditText etIp, etPort;
//    private Button btnPrintText, btnPrintBarcode, btnPrintQrcode, btnPrintImage, btnPrintState;
//    private TextView tvPrintShow;
//
//    private UsbBroadCastReceiver mUsbBroadCastReceiver;
//    private PrinterAPI mPrinter;
//    private Runnable runnable;
//
//    private Boolean circleQuery = false;
//    private int queryCount = 0;
//
//    private DataUtils dataUtils;
//    private String[] connect_type_string;
//    private String[] mSerialportList;
//    private String[] baud_rate_string;
//    private int[] baud_rate_int;
//    private String[] serial_flow;
//
//
//    static {
//        //如果使用UsbNativeAPI 必须加载 适用root板 这个不需要权限窗口和申请权限
//        System.loadLibrary("usb1.0");
//        //串口
//        System.loadLibrary("serial_icod");
//        //图片
//        System.loadLibrary("image_icod");
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_main;
//    }
//
//    // 打印机的连接
//    private void funcPrinterConnect() {
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                if (mPrinter.isConnect()) {
//                    mPrinter.disconnect();
//                    handler.obtainMessage(DISCONNECT).sendToTarget();
//                }
//                InterfaceAPI io = null;
//                switch (spConnectType.getSelectedItemPosition()) {
//                    case 0:
//                        // USB
//                        io = new USBAPI(MainActivity.this);
//                        break;
//                    case 1:
//                        // Bluetooth
//                        Intent intent = new Intent(MainActivity.this, BluetoothActivity.class);
//                        startActivityForResult(intent, REQUEST_CODE_INTENT);
//                        break;
//                    case 2:
//                        // Serial
//                        io = new SerialAPI(new File(mSerialportList[spDeviceList.getSelectedItemPosition()]),
//                                baud_rate_int[spBaudRate.getSelectedItemPosition()],
//                                spFlowcontrol.getSelectedItemPosition());
//                        break;
//                    case 3:
//                        // Wifi
//                        String etIpStr = etIp.getText().toString().trim();
//                        String etPortStr = etPort.getText().toString().trim();
//                        if (TextUtils.isEmpty(etIpStr) || TextUtils.isEmpty(etPortStr))
//                            handler.obtainMessage(TOAST_CODE, getString(R.string.str_Error) + getString(R.string.str_IpPort_Null)).sendToTarget();
//                        else
//                            io = new SocketAPI(etIpStr, Integer.valueOf(etPortStr));
//                        break;
//                    case 4:
//                        // UsbNative
//                        // if your Android mainboard have root permission you can usb this;
//                        io = new UsbNativeAPI();
//                        break;
//                }
//
//                if (io != null) {
//                    handler.obtainMessage(mPrinter.connect(io)).sendToTarget();
//                }
//            }
//        };
//        new Thread(runnable).start();
//    }
//
//    // 打印文本
//    private void funcPrintText() {
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                if (mPrinter.isConnect()) {
//                    try {
//                        queryCount = 0;
//                        // 打印方法：printString
//                        // 打印例范文本
//                        String str = "可将正体繁体字、中文简体字或QQ非主流繁体字转换翻译汉字繁简体。\n" +
//                                "可將正體繁體字、中文簡體字或QQ非主流繁體字轉換翻譯漢字繁簡體。\n";
//                        int ret = mPrinter.printString(str, "gbk", true);
//
//                        handler.obtainMessage(TOAST_CODE, ret == PrinterAPI.SUCCESS ? getString(R.string.str_Func_Success) : getString(R.string.str_Func_Failed)).sendToTarget();
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    handler.obtainMessage(NOCONNECT).sendToTarget();
//                }
//            }
//        };
//        new Thread(runnable).start();
//    }
//
//    // 打印一维码
//    private void funcPrintBarcode() {
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                if (mPrinter.isConnect()) {
//                    queryCount = 0;
//                    // 打印方法：printBarCode 参数说明查看文档
//                    // 打印例范文本
//                    try {
//                        String barStr = "6972187000491";
//                        mPrinter.init();
//
//                        mPrinter.sendOrder(new byte[]{29, 72, 2});
//                        mPrinter.setAlignMode(0);                                   // 居左
//                        mPrinter.printBarCode(73, barStr.length(), barStr);     // 打印条码
//                        mPrinter.setAlignMode(1);                                   // 居中
//                        mPrinter.printBarCode(73, barStr.length(), barStr);      // 打印条码
//                        mPrinter.setAlignMode(2);                                   // 居右
//                        mPrinter.printBarCode(73, barStr.length(), barStr);      // 打印条码
//                        mPrinter.printFeed();
//                        mPrinter.printFeed();
//                        int ret = mPrinter.fullCut();
//                        handler.obtainMessage(TOAST_CODE, ret == PrinterAPI.SUCCESS ? getString(R.string.str_Func_Success) : getString(R.string.str_Func_Failed)).sendToTarget();
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    handler.obtainMessage(NOCONNECT).sendToTarget();
//                }
//            }
//        };
//        new Thread(runnable).start();
//    }
//
//    // 打印二维码
//    private void funcPrintQrcode() {
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                if (mPrinter.isConnect()) {
//                    queryCount = 0;
//                    // 打印方法：printQRCode 参数说明查看文档
//                    // 打印例范文本
//                    String qrStr = "http://www,szicod.com/";
//
//                    int ret = mPrinter.printQRCode(qrStr, 6, true);
//                    handler.obtainMessage(TOAST_CODE, ret == PrinterAPI.SUCCESS ? getString(R.string.str_Func_Success) : getString(R.string.str_Func_Failed)).sendToTarget();
//                } else {
//                    handler.obtainMessage(NOCONNECT).sendToTarget();
//                }
//            }
//        };
//        new Thread(runnable).start();
//    }
//
//    // 打印图片
//    private void funcPrintImage() {
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                if (mPrinter.isConnect()) {
//                    queryCount = 0;
//                    // 打印方法：printRasterBitmap
//                    // 打印例范文本
//                    try {
//                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.image);
//                        bitmap = BitmapUtils.reSize(bitmap, 72 * 8, (72 * 8 * bitmap.getHeight() / bitmap.getWidth()));
//
//                        mPrinter.printRasterBitmap(bitmap);
//                        int ret = mPrinter.cutPaper(66, 0);      // 切刀
//                        handler.obtainMessage(TOAST_CODE, ret == PrinterAPI.SUCCESS ? getString(R.string.str_Func_Success) : getString(R.string.str_Func_Failed)).sendToTarget();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                } else {
//                    handler.obtainMessage(NOCONNECT).sendToTarget();
//                }
//            }
//        };
//        new Thread(runnable).start();
//    }
//
//    // 查询打印机状态
//    private void funcPrintState() {
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                queryCount = 0;
//                if (mPrinter.isConnect()) {
//                    // 打印方法：getStatus
//                    // 打印例范文本
////                    String stateStr = StatusDescribe.getStatusDescribe(mPrinter.getStatus());
////                    handler.obtainMessage(TOAST_CODE, getString(R.string.str_Status) + stateStr).sendToTarget();
//
//                    byte[] bytes = new byte[]{0x10, 0x04, 0x01, 0x10, 0x04, 0x02, 0x10, 0x04, 0x03, 0x10, 0x04, 0x04};
//                    mPrinter.sendOrder(bytes);
//                    byte[] ret = new byte[4];
//                    mPrinter.readIO(ret, 0, ret.length, 2 * 1000);
//                    handler.obtainMessage(TOAST_CODE, getString(R.string.str_Status) + StatusDescribe.getStatusDescribe(mPrinter.getStatus()) + "   " + dataUtils.bytesToHexString(ret)).sendToTarget();
//                } else {
//                    handler.obtainMessage(NOCONNECT).sendToTarget();
//                }
//            }
//        };
//        new Thread(runnable).start();
//    }
//
//    @Override
//    protected void initView() {
//        btnConnect = findViewById(R.id.btn_connect);
//        spConnectType = findViewById(R.id.sp_connect_type);
//        llSerial = findViewById(R.id.ll_serial);
//        llIp = findViewById(R.id.ll_ip);
//        spDeviceList = findViewById(R.id.sp_device_list);
//        spBaudRate = findViewById(R.id.sp_baud_rate);
//        spFlowcontrol = findViewById(R.id.sp_flowcontrol);
//        etIp = findViewById(R.id.et_ip);
//        etPort = findViewById(R.id.et_port);
//        btnPrintText = findViewById(R.id.btn_print_text);
//        btnPrintBarcode = findViewById(R.id.btn_print_barcode);
//        btnPrintQrcode = findViewById(R.id.btn_print_qrcode);
//        btnPrintImage = findViewById(R.id.btn_print_image);
//        btnPrintState = findViewById(R.id.btn_print_state);
//        tvPrintShow = findViewById(R.id.tv_print_show);
//
//        btnConnect.setOnClickListener(this);
//        btnPrintText.setOnClickListener(this);
//        btnPrintBarcode.setOnClickListener(this);
//        btnPrintQrcode.setOnClickListener(this);
//        btnPrintImage.setOnClickListener(this);
//        btnPrintState.setOnClickListener(this);
//
//        spConnectType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String sInfo = parent.getItemAtPosition(position).toString();
//                if (connect_type_string[0].equals(sInfo)) {
//                    if (mUsbBroadCastReceiver == null) {
//                        IntentFilter intentFilter = new IntentFilter();
//                        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
//                        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
//                        mUsbBroadCastReceiver = new UsbBroadCastReceiver();
//                        registerReceiver(mUsbBroadCastReceiver, intentFilter);
//                    }
//                } else if (connect_type_string[1].equals(sInfo) || connect_type_string[4].equals(sInfo)) {
//                    llSerial.setVisibility(View.GONE);
//                    llIp.setVisibility(View.GONE);
//                } else if (connect_type_string[2].equals(sInfo)) {
//                    llSerial.setVisibility(View.VISIBLE);
//                    llIp.setVisibility(View.GONE);
//
//                    SerialPortFinder spf = new SerialPortFinder();
//                    mSerialportList = spf.getAllDevicesPath();
//                    if (mSerialportList.length <= 0) mSerialportList = new String[]{"NONE"};
//                    ArrayAdapter<String> adapterTemp = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, mSerialportList);
//                    adapterTemp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spDeviceList.setAdapter(adapterTemp);
//
//                    ArrayAdapter<String> adapterTemp2 = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, baud_rate_string);
//                    adapterTemp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spBaudRate.setAdapter(adapterTemp2);
//
//                    ArrayAdapter<String> adapterTemp3 = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, serial_flow);
//                    adapterTemp3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spFlowcontrol.setAdapter(adapterTemp3);
//                } else if (connect_type_string[3].equals(sInfo)) {
//                    llSerial.setVisibility(View.GONE);
//                    llIp.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
//
//    @Override
//    protected void initData() {
//        mPrinter = PrinterAPI.getInstance();
//        dataUtils = DataUtils.getInstance(this);
//        connect_type_string = dataUtils.getStringArr(R.array.connect_type_string);
//        mSerialportList = dataUtils.getStringArr(R.array.serial_port_list);
//        baud_rate_string = dataUtils.getStringArr(R.array.baud_rate_string);
//        baud_rate_int = dataUtils.getIntegerArr(R.array.baud_rate);
//        serial_flow = dataUtils.getStringArr(R.array.serial_flow);
//
//
//        ArrayAdapter<String> adapterTemp = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, connect_type_string);
//        adapterTemp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spConnectType.setAdapter(adapterTemp);
//        initPermission();
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_connect:
//                funcPrinterConnect();
//                break;
//            case R.id.btn_print_text:
//                funcPrintText();
//                break;
//            case R.id.btn_print_barcode:
//                funcPrintBarcode();
//                break;
//            case R.id.btn_print_qrcode:
//                funcPrintQrcode();
//                break;
//            case R.id.btn_print_image:
//                funcPrintImage();
//                break;
//            case R.id.btn_print_state:
//                funcPrintState();
//                break;
//        }
//    }
//
//    //usb 监听 由于权限问题而且sdk内部有超时时间 ,适用情况应该是系统默认usb权限开放或者root 板则使用UsbNativeApi
//    private class UsbBroadCastReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
//            onToast("Device: vid:" + device.getVendorId() + "  pid:" + device.getProductId());
//            switch (intent.getAction()) {
//                case UsbManager.ACTION_USB_DEVICE_ATTACHED:
//                    // 当USB设备连接到USB总线时，在主机模式下发送此意图。
//                    runnable = new Runnable() {
//                        @Override
//                        public void run() {
//                            if (!mPrinter.isConnect()) {
//                                USBAPI usbapi = new USBAPI(MainActivity.this);
//                                handler.obtainMessage(mPrinter.connect(usbapi)).sendToTarget();
//                            }
//                        }
//                    };
//                    new Thread(runnable).start();
//                    break;
//                case UsbManager.ACTION_USB_DEVICE_DETACHED:
//                    // 当USB设备在主机模式下脱离USB总线时发送此意图。
//                    runnable = new Runnable() {
//                        @Override
//                        public void run() {
//                            if (mPrinter.isConnect()) {
//                                InterfaceAPI io = mPrinter.getIO();
//                                if (io instanceof USBAPI || io instanceof UsbNativeAPI) {
//                                    mPrinter.disconnect();
//                                    handler.obtainMessage(DISCONNECT).sendToTarget();
//                                }
//                            }
//                        }
//                    };
//                    new Thread(runnable).start();
//                    break;
//            }
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // 蓝牙返回
//        if (resultCode == BluetoothActivity.RESULTCODE_CODE_INTENT && requestCode == REQUEST_CODE_INTENT && data != null) {
//            final String address = data.getStringExtra("address");
//            runnable = new Runnable() {
//                @Override
//                public void run() {
//                    if (!mPrinter.isConnect()) {
//                        BluetoothAPI mBluetoothApi = new BluetoothAPI(getApplicationContext());
//                        mBluetoothApi.setPin("0000");//我厂自动配对的;
//                        mBluetoothApi.setPairStrings(new String[]{"BTPrinter"});//设置自动匹配的设备名
//                        mBluetoothApi.checkDevice(address);
//                        handler.obtainMessage(mPrinter.connect(mBluetoothApi)).sendToTarget();
//                    }
//                }
//            };
//            new Thread(runnable).start();
//        }
//    }
//
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case PrinterAPI.SUCCESS:
//                    tvPrintShow.setText(getString(R.string.str_Connect_Printer) + getString(R.string.str_Connect_Succeed));
//                    circleQuery = true;
//                    new Thread() {
//                        @Override
//                        public void run() {
//                            super.run();
//                            while (circleQuery) {
//                                try {
//                                    Thread.sleep(100);
//                                    queryCount++;
//                                    if (queryCount == 10) {
//                                        queryCount = 0;
//                                        if (!mPrinter.isConnect()) {
//                                            USBAPI usbapi = new USBAPI(MainActivity.this);
//                                            handler.obtainMessage(mPrinter.connect(usbapi)).sendToTarget();
//                                        }
//                                    }
//
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    }.start();
//                    break;
//                case PrinterAPI.FAIL:
//                case PrinterAPI.ERR_PARAM:
//                    tvPrintShow.setText(getString(R.string.str_Connect_Printer) + getString(R.string.str_Connect_Fail));
//                    break;
//                case DISCONNECT:
//                    tvPrintShow.setText(getString(R.string.str_Connect_Printer) + getString(R.string.str_Connect_Cancel));
//                    break;
//                case NOCONNECT:
//                    tvPrintShow.setText(getString(R.string.str_Connect_Printer) + getString(R.string.str_No_Connect));
//                    break;
//                case TOAST_CODE:
//                    tvPrintShow.setText((String) msg.obj);
//                    break;
//            }
//        }
//    };
//
//    private void initPermission() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            //检查是否已经给了权限
//            int checkpermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            if (checkpermission != PackageManager.PERMISSION_GRANTED) {//没有给权限
//                //参数分别是当前活动，权限字符串数组，requestcode
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//            }
//        }
//    }
//
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mUsbBroadCastReceiver != null)
//            unregisterReceiver(mUsbBroadCastReceiver);
//
//        if (mPrinter.isConnect()) {
//            mPrinter.disconnect();
//        }
//    }
//}
