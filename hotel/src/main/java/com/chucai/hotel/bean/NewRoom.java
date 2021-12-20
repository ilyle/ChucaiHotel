package com.chucai.hotel.bean;

import com.alibaba.fastjson.annotation.JSONField;

import org.litepal.annotation.Column;

import java.io.Serializable;
import java.util.List;

public class NewRoom implements Serializable {


    private int code;
    private String msg;
    private DataDTO data;

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

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO implements Serializable {
        private int count;

        private List<ListDTO> list;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ListDTO> getList() {
            return list;
        }

        public void setList(List<ListDTO> list) {
            this.list = list;
        }

        public static class ListDTO implements Serializable {
            private int id;
            private int shop_id;
            private int room_type;
            private int floor;
            private String room_number;
            private int room_status;
            private int sort;
            private int delete_status;
            private int create_time;
            private int last_edit_time;
            private int status;
            private Object lock_id;
            private int lock_type_id;
            private Object wifi_id;
            private String room_code;
            private String room_type_name;
            private String type_code;
            private Object lock_name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getShop_id() {
                return shop_id;
            }

            public void setShop_id(int shop_id) {
                this.shop_id = shop_id;
            }

            public int getRoom_type() {
                return room_type;
            }

            public void setRoom_type(int room_type) {
                this.room_type = room_type;
            }

            public int getFloor() {
                return floor;
            }

            public void setFloor(int floor) {
                this.floor = floor;
            }

            public String getRoom_number() {
                return room_number;
            }

            public void setRoom_number(String room_number) {
                this.room_number = room_number;
            }

            public int getRoom_status() {
                return room_status;
            }

            public void setRoom_status(int room_status) {
                this.room_status = room_status;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getDelete_status() {
                return delete_status;
            }

            public void setDelete_status(int delete_status) {
                this.delete_status = delete_status;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getLast_edit_time() {
                return last_edit_time;
            }

            public void setLast_edit_time(int last_edit_time) {
                this.last_edit_time = last_edit_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public Object getLock_id() {
                return lock_id;
            }

            public void setLock_id(Object lock_id) {
                this.lock_id = lock_id;
            }

            public int getLock_type_id() {
                return lock_type_id;
            }

            public void setLock_type_id(int lock_type_id) {
                this.lock_type_id = lock_type_id;
            }

            public Object getWifi_id() {
                return wifi_id;
            }

            public void setWifi_id(Object wifi_id) {
                this.wifi_id = wifi_id;
            }

            public String getRoom_code() {
                return room_code;
            }

            public void setRoom_code(String room_code) {
                this.room_code = room_code;
            }

            public String getRoom_type_name() {
                return room_type_name;
            }

            public void setRoom_type_name(String room_type_name) {
                this.room_type_name = room_type_name;
            }

            public String getType_code() {
                return type_code;
            }

            public void setType_code(String type_code) {
                this.type_code = type_code;
            }

            public Object getLock_name() {
                return lock_name;
            }

            public void setLock_name(Object lock_name) {
                this.lock_name = lock_name;
            }
        }
    }
}
