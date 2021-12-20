package com.chucai.hotel.bean;

import java.io.Serializable;
import java.util.List;

public class RoomSaleType implements Serializable {

    private int code;
    private String msg;
    private List<DataDTO> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO implements Serializable {
        private int id;
        private String name;
        private String sign;
        private int same_day;
        private int shop_id;
        private int stay_time;
        private int unit;
        private int immobilization;
        private int status;
        private String start_time_limit;
        private String end_time_limit;
        private String remark;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public int getSame_day() {
            return same_day;
        }

        public void setSame_day(int same_day) {
            this.same_day = same_day;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public int getStay_time() {
            return stay_time;
        }

        public void setStay_time(int stay_time) {
            this.stay_time = stay_time;
        }

        public int getUnit() {
            return unit;
        }

        public void setUnit(int unit) {
            this.unit = unit;
        }

        public int getImmobilization() {
            return immobilization;
        }

        public void setImmobilization(int immobilization) {
            this.immobilization = immobilization;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStart_time_limit() {
            return start_time_limit;
        }

        public void setStart_time_limit(String start_time_limit) {
            this.start_time_limit = start_time_limit;
        }

        public String getEnd_time_limit() {
            return end_time_limit;
        }

        public void setEnd_time_limit(String end_time_limit) {
            this.end_time_limit = end_time_limit;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
