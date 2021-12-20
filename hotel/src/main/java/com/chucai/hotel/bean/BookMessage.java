package com.chucai.hotel.bean;

import java.io.Serializable;
import java.util.List;

public class BookMessage implements Serializable {

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

    public static class DataDTO implements Serializable  {
        private int id;
        private String bill_code;
        private int user_type;
        private String common_code;
        private String link_man;
        private String link_phone;
        private int shop_id;
        private int pay_type_id;
        private int bill_status;
        private int consume_type;
        private List<?> room_type_ids;
        private int room_count;
        private int room_price;
        private int user_count;
        private double cash_pledge;
        private int user_coupon_id;
        private int amount_reduction;
        private int add_reduce_money;
        private String add_reduce_reason;
        private int bill_money;
        private int already_pay;
        private Object over_money;
        private Object over_memo;
        private String memo;
        private int enter_time_plan;
        private Object enter_time;
        private long leave_time_plan;
        private Object leave_time;
        private int last_edit_time;
        private long create_time;
        private int evaluate_status;
        private int bill_source;
        private int admin_id_create;
        private String admin_name_create;
        private Object admin_id_close;
        private Object admin_name_close;
        private int stay_type;
        private String other_bill_code;
        private int stay_time;
        private int unit;
        private Object old_consume_type;
        private Object extend_hours;
        private double original_cash_pledge;
        private int intermediaries_id;
        private int total_commission_price;
        private int price_project;
        private double already_pay_cash_pledge;
        private int is_arrage;
        private String connect_code;
        private Object third_part_bill_id;
        private Object pay_type_name;
        private String bill_status_name;
        private String source_sign;
        private String bill_source_name;
        private String room_property_name;
        private int immobilization;
        private int consume_type_stay_time;
        private String admin_name;
        private RoomInfoDTO room_info;
        private List<RoomListDTO> room_list;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBill_code() {
            return bill_code;
        }

        public void setBill_code(String bill_code) {
            this.bill_code = bill_code;
        }

        public int getUser_type() {
            return user_type;
        }

        public void setUser_type(int user_type) {
            this.user_type = user_type;
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

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public int getPay_type_id() {
            return pay_type_id;
        }

        public void setPay_type_id(int pay_type_id) {
            this.pay_type_id = pay_type_id;
        }

        public int getBill_status() {
            return bill_status;
        }

        public void setBill_status(int bill_status) {
            this.bill_status = bill_status;
        }

        public int getConsume_type() {
            return consume_type;
        }

        public void setConsume_type(int consume_type) {
            this.consume_type = consume_type;
        }

        public List<?> getRoom_type_ids() {
            return room_type_ids;
        }

        public void setRoom_type_ids(List<?> room_type_ids) {
            this.room_type_ids = room_type_ids;
        }

        public int getRoom_count() {
            return room_count;
        }

        public void setRoom_count(int room_count) {
            this.room_count = room_count;
        }

        public int getRoom_price() {
            return room_price;
        }

        public void setRoom_price(int room_price) {
            this.room_price = room_price;
        }

        public int getUser_count() {
            return user_count;
        }

        public void setUser_count(int user_count) {
            this.user_count = user_count;
        }

        public double getCash_pledge() {
            return cash_pledge;
        }

        public void setCash_pledge(double cash_pledge) {
            this.cash_pledge = cash_pledge;
        }

        public int getUser_coupon_id() {
            return user_coupon_id;
        }

        public void setUser_coupon_id(int user_coupon_id) {
            this.user_coupon_id = user_coupon_id;
        }

        public int getAmount_reduction() {
            return amount_reduction;
        }

        public void setAmount_reduction(int amount_reduction) {
            this.amount_reduction = amount_reduction;
        }

        public int getAdd_reduce_money() {
            return add_reduce_money;
        }

        public void setAdd_reduce_money(int add_reduce_money) {
            this.add_reduce_money = add_reduce_money;
        }

        public String getAdd_reduce_reason() {
            return add_reduce_reason;
        }

        public void setAdd_reduce_reason(String add_reduce_reason) {
            this.add_reduce_reason = add_reduce_reason;
        }

        public int getBill_money() {
            return bill_money;
        }

        public void setBill_money(int bill_money) {
            this.bill_money = bill_money;
        }

        public int getAlready_pay() {
            return already_pay;
        }

        public void setAlready_pay(int already_pay) {
            this.already_pay = already_pay;
        }

        public Object getOver_money() {
            return over_money;
        }

        public void setOver_money(Object over_money) {
            this.over_money = over_money;
        }

        public Object getOver_memo() {
            return over_memo;
        }

        public void setOver_memo(Object over_memo) {
            this.over_memo = over_memo;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public int getEnter_time_plan() {
            return enter_time_plan;
        }

        public void setEnter_time_plan(int enter_time_plan) {
            this.enter_time_plan = enter_time_plan;
        }

        public Object getEnter_time() {
            return enter_time;
        }

        public void setEnter_time(Object enter_time) {
            this.enter_time = enter_time;
        }

        public long getLeave_time_plan() {
            return leave_time_plan;
        }

        public void setLeave_time_plan(long leave_time_plan) {
            this.leave_time_plan = leave_time_plan;
        }

        public Object getLeave_time() {
            return leave_time;
        }

        public void setLeave_time(Object leave_time) {
            this.leave_time = leave_time;
        }

        public int getLast_edit_time() {
            return last_edit_time;
        }

        public void setLast_edit_time(int last_edit_time) {
            this.last_edit_time = last_edit_time;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public int getEvaluate_status() {
            return evaluate_status;
        }

        public void setEvaluate_status(int evaluate_status) {
            this.evaluate_status = evaluate_status;
        }

        public int getBill_source() {
            return bill_source;
        }

        public void setBill_source(int bill_source) {
            this.bill_source = bill_source;
        }

        public int getAdmin_id_create() {
            return admin_id_create;
        }

        public void setAdmin_id_create(int admin_id_create) {
            this.admin_id_create = admin_id_create;
        }

        public String getAdmin_name_create() {
            return admin_name_create;
        }

        public void setAdmin_name_create(String admin_name_create) {
            this.admin_name_create = admin_name_create;
        }

        public Object getAdmin_id_close() {
            return admin_id_close;
        }

        public void setAdmin_id_close(Object admin_id_close) {
            this.admin_id_close = admin_id_close;
        }

        public Object getAdmin_name_close() {
            return admin_name_close;
        }

        public void setAdmin_name_close(Object admin_name_close) {
            this.admin_name_close = admin_name_close;
        }

        public int getStay_type() {
            return stay_type;
        }

        public void setStay_type(int stay_type) {
            this.stay_type = stay_type;
        }

        public String getOther_bill_code() {
            return other_bill_code;
        }

        public void setOther_bill_code(String other_bill_code) {
            this.other_bill_code = other_bill_code;
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

        public Object getOld_consume_type() {
            return old_consume_type;
        }

        public void setOld_consume_type(Object old_consume_type) {
            this.old_consume_type = old_consume_type;
        }

        public Object getExtend_hours() {
            return extend_hours;
        }

        public void setExtend_hours(Object extend_hours) {
            this.extend_hours = extend_hours;
        }

        public double getOriginal_cash_pledge() {
            return original_cash_pledge;
        }

        public void setOriginal_cash_pledge(double original_cash_pledge) {
            this.original_cash_pledge = original_cash_pledge;
        }

        public int getIntermediaries_id() {
            return intermediaries_id;
        }

        public void setIntermediaries_id(int intermediaries_id) {
            this.intermediaries_id = intermediaries_id;
        }

        public int getTotal_commission_price() {
            return total_commission_price;
        }

        public void setTotal_commission_price(int total_commission_price) {
            this.total_commission_price = total_commission_price;
        }

        public int getPrice_project() {
            return price_project;
        }

        public void setPrice_project(int price_project) {
            this.price_project = price_project;
        }

        public double getAlready_pay_cash_pledge() {
            return already_pay_cash_pledge;
        }

        public void setAlready_pay_cash_pledge(double already_pay_cash_pledge) {
            this.already_pay_cash_pledge = already_pay_cash_pledge;
        }

        public int getIs_arrage() {
            return is_arrage;
        }

        public void setIs_arrage(int is_arrage) {
            this.is_arrage = is_arrage;
        }

        public String getConnect_code() {
            return connect_code;
        }

        public void setConnect_code(String connect_code) {
            this.connect_code = connect_code;
        }

        public Object getThird_part_bill_id() {
            return third_part_bill_id;
        }

        public void setThird_part_bill_id(Object third_part_bill_id) {
            this.third_part_bill_id = third_part_bill_id;
        }

        public Object getPay_type_name() {
            return pay_type_name;
        }

        public void setPay_type_name(Object pay_type_name) {
            this.pay_type_name = pay_type_name;
        }

        public String getBill_status_name() {
            return bill_status_name;
        }

        public void setBill_status_name(String bill_status_name) {
            this.bill_status_name = bill_status_name;
        }

        public String getSource_sign() {
            return source_sign;
        }

        public void setSource_sign(String source_sign) {
            this.source_sign = source_sign;
        }

        public String getBill_source_name() {
            return bill_source_name;
        }

        public void setBill_source_name(String bill_source_name) {
            this.bill_source_name = bill_source_name;
        }

        public String getRoom_property_name() {
            return room_property_name;
        }

        public void setRoom_property_name(String room_property_name) {
            this.room_property_name = room_property_name;
        }

        public int getImmobilization() {
            return immobilization;
        }

        public void setImmobilization(int immobilization) {
            this.immobilization = immobilization;
        }

        public int getConsume_type_stay_time() {
            return consume_type_stay_time;
        }

        public void setConsume_type_stay_time(int consume_type_stay_time) {
            this.consume_type_stay_time = consume_type_stay_time;
        }

        public String getAdmin_name() {
            return admin_name;
        }

        public void setAdmin_name(String admin_name) {
            this.admin_name = admin_name;
        }

        public RoomInfoDTO getRoom_info() {
            return room_info;
        }

        public void setRoom_info(RoomInfoDTO room_info) {
            this.room_info = room_info;
        }

        public List<RoomListDTO> getRoom_list() {
            return room_list;
        }

        public void setRoom_list(List<RoomListDTO> room_list) {
            this.room_list = room_list;
        }

        public static class RoomInfoDTO implements Serializable  {
            private int id;
            private int bill_id;
            private int room_id;
            private String room_number;
            private int start_time;
            private int end_time;
            private int status;
            private int create_time;
            private int room_price;
            private int user_count;
            private int update_time;
            private String room_service;
            private int stay_time;
            private int unit;
            private int main_room;
            private int original_room_price;
            private int custom_cash_pledge;
            private int room_type_id;
            private List<RoomPricesDTO> room_prices;
            private String qr_url;
            private String enter_code;
            private int price_project;
            private int intermediaries_id;
            private String new_room_number;
            private String room_code;
            private String room_type_name;
            private String cash_pledge;
            private int max_user_count;
            private String type_code;
            private Object wifi_name;
            private Object wifi_password;
            private List<?> user_list;
            private int room_service_selected;
            private List<RoomServiceListDTO> room_service_list;
            private int room_service_price;
            private String room_service_name;
            private int already_pay;
            private int bill_money;
            private int already_consume;
            private String lock_id;
            private int password_status;
            private int permission_begin;
            private int permission_end;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getBill_id() {
                return bill_id;
            }

            public void setBill_id(int bill_id) {
                this.bill_id = bill_id;
            }

            public int getRoom_id() {
                return room_id;
            }

            public void setRoom_id(int room_id) {
                this.room_id = room_id;
            }

            public String getRoom_number() {
                return room_number;
            }

            public void setRoom_number(String room_number) {
                this.room_number = room_number;
            }

            public int getStart_time() {
                return start_time;
            }

            public void setStart_time(int start_time) {
                this.start_time = start_time;
            }

            public int getEnd_time() {
                return end_time;
            }

            public void setEnd_time(int end_time) {
                this.end_time = end_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getRoom_price() {
                return room_price;
            }

            public void setRoom_price(int room_price) {
                this.room_price = room_price;
            }

            public int getUser_count() {
                return user_count;
            }

            public void setUser_count(int user_count) {
                this.user_count = user_count;
            }

            public int getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(int update_time) {
                this.update_time = update_time;
            }

            public String getRoom_service() {
                return room_service;
            }

            public void setRoom_service(String room_service) {
                this.room_service = room_service;
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

            public int getMain_room() {
                return main_room;
            }

            public void setMain_room(int main_room) {
                this.main_room = main_room;
            }

            public int getOriginal_room_price() {
                return original_room_price;
            }

            public void setOriginal_room_price(int original_room_price) {
                this.original_room_price = original_room_price;
            }

            public int getCustom_cash_pledge() {
                return custom_cash_pledge;
            }

            public void setCustom_cash_pledge(int custom_cash_pledge) {
                this.custom_cash_pledge = custom_cash_pledge;
            }

            public int getRoom_type_id() {
                return room_type_id;
            }

            public void setRoom_type_id(int room_type_id) {
                this.room_type_id = room_type_id;
            }

            public List<RoomPricesDTO> getRoom_prices() {
                return room_prices;
            }

            public void setRoom_prices(List<RoomPricesDTO> room_prices) {
                this.room_prices = room_prices;
            }

            public String getQr_url() {
                return qr_url;
            }

            public void setQr_url(String qr_url) {
                this.qr_url = qr_url;
            }

            public String getEnter_code() {
                return enter_code;
            }

            public void setEnter_code(String enter_code) {
                this.enter_code = enter_code;
            }

            public int getPrice_project() {
                return price_project;
            }

            public void setPrice_project(int price_project) {
                this.price_project = price_project;
            }

            public int getIntermediaries_id() {
                return intermediaries_id;
            }

            public void setIntermediaries_id(int intermediaries_id) {
                this.intermediaries_id = intermediaries_id;
            }

            public String getNew_room_number() {
                return new_room_number;
            }

            public void setNew_room_number(String new_room_number) {
                this.new_room_number = new_room_number;
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

            public String getCash_pledge() {
                return cash_pledge;
            }

            public void setCash_pledge(String cash_pledge) {
                this.cash_pledge = cash_pledge;
            }

            public int getMax_user_count() {
                return max_user_count;
            }

            public void setMax_user_count(int max_user_count) {
                this.max_user_count = max_user_count;
            }

            public String getType_code() {
                return type_code;
            }

            public void setType_code(String type_code) {
                this.type_code = type_code;
            }

            public Object getWifi_name() {
                return wifi_name;
            }

            public void setWifi_name(Object wifi_name) {
                this.wifi_name = wifi_name;
            }

            public Object getWifi_password() {
                return wifi_password;
            }

            public void setWifi_password(Object wifi_password) {
                this.wifi_password = wifi_password;
            }

            public List<?> getUser_list() {
                return user_list;
            }

            public void setUser_list(List<?> user_list) {
                this.user_list = user_list;
            }

            public int getRoom_service_selected() {
                return room_service_selected;
            }

            public void setRoom_service_selected(int room_service_selected) {
                this.room_service_selected = room_service_selected;
            }

            public List<RoomServiceListDTO> getRoom_service_list() {
                return room_service_list;
            }

            public void setRoom_service_list(List<RoomServiceListDTO> room_service_list) {
                this.room_service_list = room_service_list;
            }

            public int getRoom_service_price() {
                return room_service_price;
            }

            public void setRoom_service_price(int room_service_price) {
                this.room_service_price = room_service_price;
            }

            public String getRoom_service_name() {
                return room_service_name;
            }

            public void setRoom_service_name(String room_service_name) {
                this.room_service_name = room_service_name;
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

            public int getAlready_consume() {
                return already_consume;
            }

            public void setAlready_consume(int already_consume) {
                this.already_consume = already_consume;
            }

            public String getLock_id() {
                return lock_id;
            }

            public void setLock_id(String lock_id) {
                this.lock_id = lock_id;
            }

            public int getPassword_status() {
                return password_status;
            }

            public void setPassword_status(int password_status) {
                this.password_status = password_status;
            }

            public int getPermission_begin() {
                return permission_begin;
            }

            public void setPermission_begin(int permission_begin) {
                this.permission_begin = permission_begin;
            }

            public int getPermission_end() {
                return permission_end;
            }

            public void setPermission_end(int permission_end) {
                this.permission_end = permission_end;
            }

            public static class RoomPricesDTO implements Serializable {
                private String date;
                private int room_price;
                private int original_room_price;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public int getRoom_price() {
                    return room_price;
                }

                public void setRoom_price(int room_price) {
                    this.room_price = room_price;
                }

                public int getOriginal_room_price() {
                    return original_room_price;
                }

                public void setOriginal_room_price(int original_room_price) {
                    this.original_room_price = original_room_price;
                }
            }

            public static class RoomServiceListDTO implements Serializable  {
                private int id;
                private int room_type_id;
                private int member_grade_id;
                private int price;
                private int create_time;
                private int room_property_id;
                private int status;
                private String service_name;
                private Object service_content;
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

                public Object getService_content() {
                    return service_content;
                }

                public void setService_content(Object service_content) {
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

        public static class RoomListDTO implements Serializable  {
            private int id;
            private int bill_id;
            private int room_id;
            private String room_number;
            private long start_time;
            private long end_time;
            private int status;
            private long create_time;
            private int room_price;
            private int user_count;
            private int update_time;
            private String room_service;
            private int stay_time;
            private int unit;
            private int main_room;
            private int original_room_price;
            private double custom_cash_pledge;
            private int room_type_id;
            private List<RoomInfoDTO.RoomPricesDTO> room_prices;
            private String qr_url;
            private String enter_code;
            private int price_project;
            private int intermediaries_id;
            private String new_room_number;
            private String room_code;
            private String room_type_name;
            private double cash_pledge;
            private int max_user_count;
            private String type_code;
            private Object wifi_name;
            private Object wifi_password;
            private List<EnterMsgDTO> user_list;
            private String room_service_selected;
            private List<RoomServiceListDTO> room_service_list;
            private int room_service_price;
            private String room_service_name;
            private int already_pay;
            private int bill_money;
            private int already_consume;
            private String lock_id;
            private int password_status;
            private int permission_begin;
            private int permission_end;
            private List<PriceOriginalDTO> price_original;
            private List<PriceOriginalDTO> price;
            private double cash_pledge_original;
            private Object custom_room_price;
            private List<EnterMsgDTO> enter_msg;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getBill_id() {
                return bill_id;
            }

            public void setBill_id(int bill_id) {
                this.bill_id = bill_id;
            }

            public int getRoom_id() {
                return room_id;
            }

            public void setRoom_id(int room_id) {
                this.room_id = room_id;
            }

            public String getRoom_number() {
                return room_number;
            }

            public void setRoom_number(String room_number) {
                this.room_number = room_number;
            }

            public long getStart_time() {
                return start_time;
            }

            public void setStart_time(long start_time) {
                this.start_time = start_time;
            }

            public long getEnd_time() {
                return end_time;
            }

            public void setEnd_time(long end_time) {
                this.end_time = end_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }

            public int getRoom_price() {
                return room_price;
            }

            public void setRoom_price(int room_price) {
                this.room_price = room_price;
            }

            public int getUser_count() {
                return user_count;
            }

            public void setUser_count(int user_count) {
                this.user_count = user_count;
            }

            public int getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(int update_time) {
                this.update_time = update_time;
            }

            public String getRoom_service() {
                return room_service;
            }

            public void setRoom_service(String room_service) {
                this.room_service = room_service;
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

            public int getMain_room() {
                return main_room;
            }

            public void setMain_room(int main_room) {
                this.main_room = main_room;
            }

            public int getOriginal_room_price() {
                return original_room_price;
            }

            public void setOriginal_room_price(int original_room_price) {
                this.original_room_price = original_room_price;
            }

            public double getCustom_cash_pledge() {
                return custom_cash_pledge;
            }

            public void setCustom_cash_pledge(double custom_cash_pledge) {
                this.custom_cash_pledge = custom_cash_pledge;
            }

            public int getRoom_type_id() {
                return room_type_id;
            }

            public void setRoom_type_id(int room_type_id) {
                this.room_type_id = room_type_id;
            }

            public List<RoomInfoDTO.RoomPricesDTO> getRoom_prices() {
                return room_prices;
            }

            public void setRoom_prices(List<RoomInfoDTO.RoomPricesDTO> room_prices) {
                this.room_prices = room_prices;
            }

            public String getQr_url() {
                return qr_url;
            }

            public void setQr_url(String qr_url) {
                this.qr_url = qr_url;
            }

            public String getEnter_code() {
                return enter_code;
            }

            public void setEnter_code(String enter_code) {
                this.enter_code = enter_code;
            }

            public int getPrice_project() {
                return price_project;
            }

            public void setPrice_project(int price_project) {
                this.price_project = price_project;
            }

            public int getIntermediaries_id() {
                return intermediaries_id;
            }

            public void setIntermediaries_id(int intermediaries_id) {
                this.intermediaries_id = intermediaries_id;
            }

            public String getNew_room_number() {
                return new_room_number;
            }

            public void setNew_room_number(String new_room_number) {
                this.new_room_number = new_room_number;
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

            public double getCash_pledge() {
                return cash_pledge;
            }

            public void setCash_pledge(double cash_pledge) {
                this.cash_pledge = cash_pledge;
            }

            public int getMax_user_count() {
                return max_user_count;
            }

            public void setMax_user_count(int max_user_count) {
                this.max_user_count = max_user_count;
            }

            public String getType_code() {
                return type_code;
            }

            public void setType_code(String type_code) {
                this.type_code = type_code;
            }

            public Object getWifi_name() {
                return wifi_name;
            }

            public void setWifi_name(Object wifi_name) {
                this.wifi_name = wifi_name;
            }

            public Object getWifi_password() {
                return wifi_password;
            }

            public void setWifi_password(Object wifi_password) {
                this.wifi_password = wifi_password;
            }

            public List<EnterMsgDTO> getUser_list() {
                return user_list;
            }

            public void setUser_list(List<EnterMsgDTO> user_list) {
                this.user_list = user_list;
            }

            public String getRoom_service_selected() {
                return room_service_selected;
            }

            public void setRoom_service_selected(String room_service_selected) {
                this.room_service_selected = room_service_selected;
            }

            public List<RoomServiceListDTO> getRoom_service_list() {
                return room_service_list;
            }

            public void setRoom_service_list(List<RoomServiceListDTO> room_service_list) {
                this.room_service_list = room_service_list;
            }

            public int getRoom_service_price() {
                return room_service_price;
            }

            public void setRoom_service_price(int room_service_price) {
                this.room_service_price = room_service_price;
            }

            public String getRoom_service_name() {
                return room_service_name;
            }

            public void setRoom_service_name(String room_service_name) {
                this.room_service_name = room_service_name;
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

            public int getAlready_consume() {
                return already_consume;
            }

            public void setAlready_consume(int already_consume) {
                this.already_consume = already_consume;
            }

            public String getLock_id() {
                return lock_id;
            }

            public void setLock_id(String lock_id) {
                this.lock_id = lock_id;
            }

            public int getPassword_status() {
                return password_status;
            }

            public void setPassword_status(int password_status) {
                this.password_status = password_status;
            }

            public int getPermission_begin() {
                return permission_begin;
            }

            public void setPermission_begin(int permission_begin) {
                this.permission_begin = permission_begin;
            }

            public int getPermission_end() {
                return permission_end;
            }

            public void setPermission_end(int permission_end) {
                this.permission_end = permission_end;
            }

            public List<PriceOriginalDTO> getPrice_original() {
                return price_original;
            }

            public void setPrice_original(List<PriceOriginalDTO> price_original) {
                this.price_original = price_original;
            }

            public List<PriceOriginalDTO> getPrice() {
                return price;
            }

            public void setPrice(List<PriceOriginalDTO> price) {
                this.price = price;
            }

            public double getCash_pledge_original() {
                return cash_pledge_original;
            }

            public void setCash_pledge_original(double cash_pledge_original) {
                this.cash_pledge_original = cash_pledge_original;
            }

            public Object getCustom_room_price() {
                return custom_room_price;
            }

            public void setCustom_room_price(Object custom_room_price) {
                this.custom_room_price = custom_room_price;
            }

            public List<EnterMsgDTO> getEnter_msg() {
                return enter_msg;
            }

            public void setEnter_msg(List<EnterMsgDTO> enter_msg) {
                this.enter_msg = enter_msg;
            }

            public static class RoomServiceListDTO implements Serializable  {
                private int id;
                private int room_type_id;
                private int member_grade_id;
                private int price;
                private int create_time;
                private int room_property_id;
                private int status;
                private String service_name;
                private Object service_content;
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

                public Object getService_content() {
                    return service_content;
                }

                public void setService_content(Object service_content) {
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

            public static class PriceOriginalDTO implements Serializable  {
                private String date;
                private int price;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                }
            }

            public static class EnterMsgDTO implements Serializable  {
                private String name;
                private int gender;
                private int card_type;
                private String id_card;
                private String link_phone;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getGender() {
                    return gender;
                }

                public void setGender(int gender) {
                    this.gender = gender;
                }

                public int getCard_type() {
                    return card_type;
                }

                public void setCard_type(int card_type) {
                    this.card_type = card_type;
                }

                public String getId_card() {
                    return id_card;
                }

                public void setId_card(String id_card) {
                    this.id_card = id_card;
                }

                public String getLink_phone() {
                    return link_phone;
                }

                public void setLink_phone(String link_phone) {
                    this.link_phone = link_phone;
                }
            }
        }
    }
}
