package com.chucai.hotel.bean;

import java.io.Serializable;
import java.util.List;

public class PriceData implements Serializable {

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
        private List<ColumnsDTO> columns;
        private Object dataSource;


        public void setDataSource(Object dataSource) {
            this.dataSource = dataSource;
        }

        public Object getDataSource() {
            return dataSource;
        }

        public List<ColumnsDTO> getColumns() {
            return columns;
        }

        public void setColumns(List<ColumnsDTO> columns) {
            this.columns = columns;
        }



        public static class ColumnsDTO implements Serializable {
            private String date;
            private String week;
            private List<RoomPriceData> roomPriceData;


            public void setRoomPriceData(List<RoomPriceData> roomPriceData) {
                this.roomPriceData = roomPriceData;
            }

            public List<RoomPriceData> getRoomPriceData() {
                return roomPriceData;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }
        }



        public static class RoomPriceData implements Serializable {

            private double over_time_price;
            private int room_property_id;
            private int room_service_status;
            private int create_time;
            private double price;
            private int upgrade_growth_value;
            private int room_type_id;
            private int member_grade_id;
            private String grade_name;
            private int status;

            public double getOver_time_price() {
                return over_time_price;
            }

            public void setOver_time_price(double over_time_price) {
                this.over_time_price = over_time_price;
            }

            public int getRoom_property_id() {
                return room_property_id;
            }

            public void setRoom_property_id(int room_property_id) {
                this.room_property_id = room_property_id;
            }

            public int getRoom_service_status() {
                return room_service_status;
            }

            public void setRoom_service_status(int room_service_status) {
                this.room_service_status = room_service_status;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getUpgrade_growth_value() {
                return upgrade_growth_value;
            }

            public void setUpgrade_growth_value(int upgrade_growth_value) {
                this.upgrade_growth_value = upgrade_growth_value;
            }

            public int getRoom_type_id() {
                return room_type_id;
            }

            public void setRoom_type_id(int room_type_id) {
                this.room_type_id = room_type_id;
            }

            public int getMember_grade_id() {
                return member_grade_id;
            }

            public void setMember_grade_id(int member_grade_id) {
                this.member_grade_id = member_grade_id;
            }

            public String getGrade_name() {
                return grade_name;
            }

            public void setGrade_name(String grade_name) {
                this.grade_name = grade_name;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

    }
}
