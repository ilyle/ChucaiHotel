package com.chucai.hotel.bean;

import java.io.Serializable;

public class PayData implements Serializable {


    private int retcode;
    private String returnMsg;
    private Object sign;
    private Object method;
    private Object timeStamp;
    private String data;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Object getSign() {
        return sign;
    }

    public void setSign(Object sign) {
        this.sign = sign;
    }

    public Object getMethod() {
        return method;
    }

    public void setMethod(Object method) {
        this.method = method;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static class PayMessage implements Serializable {


        private String return_code;
        private String return_msg;
        private String return_des;
        private String out_trade_no;
        private String return_url;
        private int total_amount;
        private int return_time;
        private String pay_way;
        private String sign;

        public String getReturn_code() {
            return return_code;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }

        public String getReturn_des() {
            return return_des;
        }

        public void setReturn_des(String return_des) {
            this.return_des = return_des;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getReturn_url() {
            return return_url;
        }

        public void setReturn_url(String return_url) {
            this.return_url = return_url;
        }

        public int getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(int total_amount) {
            this.total_amount = total_amount;
        }

        public int getReturn_time() {
            return return_time;
        }

        public void setReturn_time(int return_time) {
            this.return_time = return_time;
        }

        public String getPay_way() {
            return pay_way;
        }

        public void setPay_way(String pay_way) {
            this.pay_way = pay_way;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
