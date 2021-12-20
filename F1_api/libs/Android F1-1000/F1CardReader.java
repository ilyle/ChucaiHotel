package com.act.F1_api;
import android.util.Log;

/**
 * Created by XSH on 18-10-22.
 */
public class F1CardReader {
    private class RDATA {
        public byte[] buffer = null;
    }
    public static   final  int STATUS_CAPTURE_BOX_FULL	 =   0x1000;
    public static   final  int STATUS_DISPENSING =       (0x0800);
    public  final  int STATUS_CAPTURING =           (0x0400);
    public  final  int STATUS_DISPENSE_ERROR	 =  (0x0200);
    public  final  int STATUS_CAPTURE_ERROR =      (0x0100);
    public  final  int STATUS_CARD_OVERLAPPED =    (0x0040);
    public  final  int STATUS_CARD_JAMMED =        (0x0020);
    public  final  int STATUS_CARD_INSUFFICIENT = (0x0010);
    public  final  int STATUS_CARD_BOX_EMPTY	 = (0x0008);
    public  final  int STATUS_S3_CARD_IN	 =     (0x0004);
    public  final  int STATUS_S2_CARD_IN	 =     (0x0002);
    public  final  int STATUS_S1_CARD_IN	 =     (0x0001);

    private String mPortName = null;
    private int mHandle = 0;

    private static native int csConnect(String portName, int baudRate,int address);
    private static native int csDisconnect(int handle);
    private static native int csExecute(int handle, byte[] cmdData, int stOff, int respOff, RDATA respData, int expMinLen);

    static {
      System.loadLibrary("F1jni");
    }
    public boolean isConnected() {
        return mHandle != 0;
    }

    /**
     * 在调用程序和设备之间建立一个连接。
     *
     * @param portName 通信端口名称。例如："/dev/ttyS0"。
     * @param baudRate 波特率。可用值：9600，19200，38400，57600，115200。默认值为 9600。
     * @throws java.lang.Exception 如果在建立连接时出现错误
     */
    public void connect(String portName, int baudRate) throws Exception {
        if (mHandle != 0
                && mPortName != null
                && mPortName.equalsIgnoreCase(portName))
            return;

        int ret = csConnect(portName, baudRate,15);
        Log.i("串口连接：  ", "csConnect : ret = " + ret);
        if (ret < 0) triggException(ret);
        mHandle = ret;
        mPortName = portName;
    }

    /**
     * 断开调用程序和阅读器之间的连接。
     *
     * @throws java.lang.Exception 如果断开连接时出现错误
     */
    public void disconnect() throws Exception {
        int ret = csDisconnect(mHandle);
        if (ret < 0)
            triggException(ret);

        mHandle = 0;
        mPortName = null;
    }

    /**
     * 复位发卡器。
     *
     * @throws java.lang.Exception 如果在执行命令时出现错误
     */
    public int reset() throws R1Exception {
        final byte[] cmdData = new byte[] { 'R', 'S' };
        RDATA respData = new RDATA();
        execute(mHandle, cmdData, -1, 0, null, 0);
        return 0;
    }

    /**
     * 卡片操作。
     *bMode: 	0x30 发到出卡口
            	0x34 发卡持卡位
     #         	0x36 发到读卡位
     * @throws java.lang.Exception 如果在执行命令时出现错误
     */
    public int  F1_Dispense(byte 	bMode) throws R1Exception {
        byte[] cmdData = new byte[] { 'F', 'C',bMode };
        RDATA respData = new RDATA();
        execute(mHandle, cmdData, -1, 0, null, 0);

       return 0;
    }

    /**
     * 回收卡片
     * @return
     * @throws R1Exception 如果在执行命令时出现错误
     */
    public int  F1_Capture() throws R1Exception {
        byte[] cmdData = new byte[] { 'C', 'P' };
        RDATA respData = new RDATA();
        execute(mHandle, cmdData, -1, 0, null, 0);
        return 0;
    }

    /**
     * 获取卡机状态
     * @return 成功返回设备状态值的一个或多个的组合，
	 * 得到的数值和上面的宏与操作 true 表示该状态存在，否则不存在
	 *
	 * 如下：
     *
     *
    回收箱满   STATUS_CAPTURE_BOX_FULL	 = 0x1000
    正在发卡   STATUS_DISPENSING	     = 0x0800
    正在回收卡 STATUS_CAPTURING	         = 0x0400
    发卡错误   STATUS_DISPENSE_ERROR	 = 0x0200
    回收卡错误 STATUS_CAPTURE_ERROR	     = 0x0100
    卡重叠    STATUS_CARD_OVERLAPPED	 = 0x0040
    卡堵塞    STATUS_CARD_JAMMED	     = 0x0020
    卡箱卡少   STATUS_CARD_INSUFFICIENT  = 0x0010
    卡箱空     STATUS_CARD_BOX_EMPTY	 = 0x0008
    S3 传感器检测到卡 STATUS_S3_CARD_IN	 = 0x0004
    S2 传感器检测到卡 STATUS_S2_CARD_IN	 = 0x0002
    S3 传感器检测到卡 STATUS_S1_CARD_IN	 = 0x0001
     *
     *
     * @throws R1Exception
     */
    public int F1_GetStatus() throws R1Exception {
        byte[] cmdData = new byte[]{'A', 'P'};
        RDATA respData = new RDATA();
        execute(mHandle, cmdData, 0, 1, respData, 4);
        int bStatusFlags =  (int)( ((respData.buffer[0] - 0x30) << 12)
                | ((respData.buffer[1] - 0x30) << 8)
                | (int)((respData.buffer[2] - 0x30) << 4)
                | (int)(respData.buffer[3] - 0x30));
        return bStatusFlags;
    }


    public int  F1_SetEntryMode(byte 	bMode) throws R1Exception {
        byte[] cmdData = new byte[] { 'I', 'N',bMode };
        execute(mHandle, cmdData, -1, 0, null, 0);
        return 0;
    }

    /**
     * 获取当前的进卡模式
     * @return  成功返回 当前进卡模式值
     *
     * 禁止进卡 EM_PROHIBITED	                     = 	0x30
      允放进卡并回收 EM_CAPTURE_TO_BOX               = 	0x31
      允许进卡并移动卡到读卡位 EM_DOCKED_TO_READ_POS = 	0x32
     *
     * @throws R1Exception
     */
    public byte  F1_GetEntryMode() throws R1Exception {

        String str;


        byte[] cmdData = new byte[] { 'S', 'I' };
        RDATA respData = new RDATA();
        execute(mHandle, cmdData, -1, 0, respData, 0);
        return respData.buffer[0];
    }

    private void execute(int handle, byte[] cmdData, int stOff, int respOff, RDATA respData, int expMinLen) throws R1Exception {
        int ret = csExecute(handle, cmdData, stOff, respOff, respData, expMinLen);
        if (ret < 0) triggException(ret);
    }

    private void triggException(int ret) throws R1Exception {
        String detail="";
        switch (ret) {
            case -1:
                detail = ("通信端口不可用");
                break;
            case -2:
                detail = ("设备未上电或未连接计算机，或串口故障，或设备故障");
                break;
            case -3:
                detail = ("未建立连接");
                break;
            case -4:
                detail = ("通信超时");
                break;
            case -5:
                detail = ("内部错误");
                break;
            case -6:
                detail = ("内存不足");
                break;
            case -8:
                detail = ("提供的一个或多个参数不能被正确解释");
                break;
            case -10:
                detail = ("命令不能在当前的状态下执行");
                break;
            case -11:
                detail = ("命令执行失败");
                break;
            case -12:
                detail = ("机内无卡");
                break;
            case -13:
                detail = ("密钥不正确");
                break;
            case -14:
                detail = ("扇区未认证");
                break;
            case -15:
                detail = ("UID 错误");
                break;
            case -17:
                detail = ("值溢出");
                break;
            case -18:
                detail = ("波特率错误");
                break;
            case -19:
                detail = ("数据发送失败");
                break;
            case -20:
                detail = ("接收数据失败");
                break;
            case -21:
                detail = ("读/写块错误");
            default :
                detail = String.format("返回错误码： %d",ret);
                break;
        }
        throw new R1Exception(ret, detail);
    }
}
