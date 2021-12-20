package com.chucai.hotel.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

public class RoomStateBean implements Serializable {


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
        @JSONField( name = "list")
        private List<ListRoomDTO> list_room;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void setList_room(List<ListRoomDTO> list_room) {
            this.list_room = list_room;
        }

        public List<ListRoomDTO> getList_room() {
            return list_room;
        }

        public static class ListRoomDTO implements Serializable {
            private int floor;
            private int count;
            @JSONField( name = "room_list")
            private List<RoomListDTO> room_list;

            public int getFloor() {
                return floor;
            }

            public void setFloor(int floor) {
                this.floor = floor;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<RoomListDTO> getRoom_list() {
                return room_list;
            }

            public void setRoom_list(List<RoomListDTO> room_list) {
                this.room_list = room_list;
            }

            public static class RoomListDTO implements Serializable {
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
                private int max_user_count;
                private String lock_name;
                private int bill_id;
                private String users;
                private String common_code;
                private String link_man;
                private String link_phone;
                private String gender;
                private String room_status_name;
                private String color;
                private int price;
                private double cash_pledge;
                private int room_type_id;
                private int room_id;
                private String connect_code;
                private int main_room;
                private int already_pay;
                private int bill_money;
                private int stay_time;
                private String grade_name;
                private String bill_status;
                private List<?> room_status_record;
                private List<RoomServiceDTO> room_service;
                private List<?> connect_room_list;
                private List<?> tags;

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

                public int getMax_user_count() {
                    return max_user_count;
                }

                public void setMax_user_count(int max_user_count) {
                    this.max_user_count = max_user_count;
                }

                public String getLock_name() {
                    return lock_name;
                }

                public void setLock_name(String lock_name) {
                    this.lock_name = lock_name;
                }

                public int getBill_id() {
                    return bill_id;
                }

                public void setBill_id(int bill_id) {
                    this.bill_id = bill_id;
                }

                public String getUsers() {
                    return users;
                }

                public void setUsers(String users) {
                    this.users = users;
                }

                public String getCommon_code() {
                    return common_code;
                }

                public void setCommon_code(String common_code) {
                    this.common_code = common_code;
                }

                public String getLink_man() {
                    return link_man;
                }

                public void setLink_man(String link_man) {
                    this.link_man = link_man;
                }

                public String getLink_phone() {
                    return link_phone;
                }

                public void setLink_phone(String link_phone) {
                    this.link_phone = link_phone;
                }

                public String getGender() {
                    return gender;
                }

                public void setGender(String gender) {
                    this.gender = gender;
                }

                public String getRoom_status_name() {
                    return room_status_name;
                }

                public void setRoom_status_name(String room_status_name) {
                    this.room_status_name = room_status_name;
                }

                public String getColor() {
                    return color;
                }

                public void setColor(String color) {
                    this.color = color;
                }

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                }

                public double getCash_pledge() {
                    return cash_pledge;
                }

                public void setCash_pledge(double cash_pledge) {
                    this.cash_pledge = cash_pledge;
                }

                public int getRoom_type_id() {
                    return room_type_id;
                }

                public void setRoom_type_id(int room_type_id) {
                    this.room_type_id = room_type_id;
                }

                public int getRoom_id() {
                    return room_id;
                }

                public void setRoom_id(int room_id) {
                    this.room_id = room_id;
                }

                public String getConnect_code() {
                    return connect_code;
                }

                public void setConnect_code(String connect_code) {
                    this.connect_code = connect_code;
                }

                public int getMain_room() {
                    return main_room;
                }

                public void setMain_room(int main_room) {
                    this.main_room = main_room;
                }

                public int getAlready_pay() {
                    return already_pay;
                }

                public void setAlready_pay(int already_pay) {
                    this.already_pay = already_pay;
                }

                public int getBill_money() {
                    return bill_money;
                }

                public void setBill_money(int bill_money) {
                    this.bill_money = bill_money;
                }

                public int getStay_time() {
                    return stay_time;
                }

                public void setStay_time(int stay_time) {
                    this.stay_time = stay_time;
                }

                public String getGrade_name() {
                    return grade_name;
                }

                public void setGrade_name(String grade_name) {
                    this.grade_name = grade_name;
                }

                public String getBill_status() {
                    return bill_status;
                }

                public void setBill_status(String bill_status) {
                    this.bill_status = bill_status;
                }

                public List<?> getRoom_status_record() {
                    return room_status_record;
                }

                public void setRoom_status_record(List<?> room_status_record) {
                    this.room_status_record = room_status_record;
                }

                public List<RoomServiceDTO> getRoom_service() {
                    return room_service;
                }

                public void setRoom_service(List<RoomServiceDTO> room_service) {
                    this.room_service = room_service;
                }

                public List<?> getConnect_room_list() {
                    return connect_room_list;
                }

                public void setConnect_room_list(List<?> connect_room_list) {
                    this.connect_room_list = connect_room_list;
                }

                public List<?> getTags() {
                    return tags;
                }

                public void setTags(List<?> tags) {
                    this.tags = tags;
                }

                public static class RoomServiceDTO implements Serializable {
                    private int id;
                    private int room_type_id;
                    private int member_grade_id;
                    private int price;
                    private int create_time;
                    private int room_property_id;
                    private int status;
                    private String service_name;
                    private String service_content;
                    private Object week;
                    private Object date;
                    private int is_show;
                    private int sys_setting;
                    private int cancelable;
                    private String before_end_time;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
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

                    public int getPrice() {
                        return price;
                    }

                    public void setPrice(int price) {
                        this.price = price;
                    }

                    public int getCreate_time() {
                        return create_time;
                    }

                    public void setCreate_time(int create_time) {
                        this.create_time = create_time;
                    }

                    public int getRoom_property_id() {
                        return room_property_id;
                    }

                    public void setRoom_property_id(int room_property_id) {
                        this.room_property_id = room_property_id;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
                    }

                    public String getService_name() {
                        return service_name;
                    }

                    public void setService_name(String service_name) {
                        this.service_name = service_name;
                    }

                    public String getService_content() {
                        return service_content;
                    }

                    public void setService_content(String service_content) {
                        this.service_content = service_content;
                    }

                    public Object getWeek() {
                        return week;
                    }

                    public void setWeek(Object week) {
                        this.week = week;
                    }

                    public Object getDate() {
                        return date;
                    }

                    public void setDate(Object date) {
                        this.date = date;
                    }

                    public int getIs_show() {
                        return is_show;
                    }

                    public void setIs_show(int is_show) {
                        this.is_show = is_show;
                    }

                    public int getSys_setting() {
                        return sys_setting;
                    }

                    public void setSys_setting(int sys_setting) {
                        this.sys_setting = sys_setting;
                    }

                    public int getCancelable() {
                        return cancelable;
                    }

                    public void setCancelable(int cancelable) {
                        this.cancelable = cancelable;
                    }

                    public String getBefore_end_time() {
                        return before_end_time;
                    }

                    public void setBefore_end_time(String before_end_time) {
                        this.before_end_time = before_end_time;
                    }
                }
            }
        }
    }
}
