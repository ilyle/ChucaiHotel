package com.chucai.hotel.bean;

import java.io.Serializable;
import java.util.List;

public class BookMessage2 implements Serializable {


    private int code;
    private DataDTO data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataDTO implements Serializable {
        private int count;
        private NumTipDTO numTip;
        private List<ListDTO> list;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public NumTipDTO getNumTip() {
            return numTip;
        }

        public void setNumTip(NumTipDTO numTip) {
            this.numTip = numTip;
        }

        public List<ListDTO> getList() {
            return list;
        }

        public void setList(List<ListDTO> list) {
            this.list = list;
        }

        public static class NumTipDTO implements Serializable {
            private int status_2;
            private int status_3;
            private int status_4;
            private int status_5;
            private int status_6;
            private int status_7;
            private int status_8;
            private int status_all;

            public int getStatus_2() {
                return status_2;
            }

            public void setStatus_2(int status_2) {
                this.status_2 = status_2;
            }

            public int getStatus_3() {
                return status_3;
            }

            public void setStatus_3(int status_3) {
                this.status_3 = status_3;
            }

            public int getStatus_4() {
                return status_4;
            }

            public void setStatus_4(int status_4) {
                this.status_4 = status_4;
            }

            public int getStatus_5() {
                return status_5;
            }

            public void setStatus_5(int status_5) {
                this.status_5 = status_5;
            }

            public int getStatus_6() {
                return status_6;
            }

            public void setStatus_6(int status_6) {
                this.status_6 = status_6;
            }

            public int getStatus_7() {
                return status_7;
            }

            public void setStatus_7(int status_7) {
                this.status_7 = status_7;
            }

            public int getStatus_8() {
                return status_8;
            }

            public void setStatus_8(int status_8) {
                this.status_8 = status_8;
            }

            public int getStatus_all() {
                return status_all;
            }

            public void setStatus_all(int status_all) {
                this.status_all = status_all;
            }
        }

        public static class ListDTO implements Serializable {
            private int add_reduce_money;
            private String add_reduce_reason;
            private int admin_id_create;
            private String admin_name_create;
            private int already_pay;
            private int amount_reduction;
            private String bill_code;
            private int bill_money;
            private int bill_source;
            private String bill_source_name;
            private int bill_status;
            private String bill_status_name;
            private int cash_pledge;
            private String common_code;
            private int consume_type;
            private int consume_type_stay_time;
            private int create_time;
            private int enter_time;
            private int enter_time_plan;
            private int evaluate_status;
            private int id;
            private int immobilization;
            private int last_edit_time;
            private int leave_time_plan;
            private String link_man;
            private String link_phone;
            private String memo;
            private String other_bill_code;
            private int pay_type_id;
            private int room_count;
            private int room_price;
            private int shop_id;
            private int stay_time;
            private int stay_type;
            private int unit;
            private int user_count;
            private int user_coupon_id;
            private int user_type;
            private List<RoomListDTO> room_list;
            private List<RoomTypeIdsDTO> room_type_ids;

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

            public int getAlready_pay() {
                return already_pay;
            }

            public void setAlready_pay(int already_pay) {
                this.already_pay = already_pay;
            }

            public int getAmount_reduction() {
                return amount_reduction;
            }

            public void setAmount_reduction(int amount_reduction) {
                this.amount_reduction = amount_reduction;
            }

            public String getBill_code() {
                return bill_code;
            }

            public void setBill_code(String bill_code) {
                this.bill_code = bill_code;
            }

            public int getBill_money() {
                return bill_money;
            }

            public void setBill_money(int bill_money) {
                this.bill_money = bill_money;
            }

            public int getBill_source() {
                return bill_source;
            }

            public void setBill_source(int bill_source) {
                this.bill_source = bill_source;
            }

            public String getBill_source_name() {
                return bill_source_name;
            }

            public void setBill_source_name(String bill_source_name) {
                this.bill_source_name = bill_source_name;
            }

            public int getBill_status() {
                return bill_status;
            }

            public void setBill_status(int bill_status) {
                this.bill_status = bill_status;
            }

            public String getBill_status_name() {
                return bill_status_name;
            }

            public void setBill_status_name(String bill_status_name) {
                this.bill_status_name = bill_status_name;
            }

            public int getCash_pledge() {
                return cash_pledge;
            }

            public void setCash_pledge(int cash_pledge) {
                this.cash_pledge = cash_pledge;
            }

            public String getCommon_code() {
                return common_code;
            }

            public void setCommon_code(String common_code) {
                this.common_code = common_code;
            }

            public int getConsume_type() {
                return consume_type;
            }

            public void setConsume_type(int consume_type) {
                this.consume_type = consume_type;
            }

            public int getConsume_type_stay_time() {
                return consume_type_stay_time;
            }

            public void setConsume_type_stay_time(int consume_type_stay_time) {
                this.consume_type_stay_time = consume_type_stay_time;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getEnter_time() {
                return enter_time;
            }

            public void setEnter_time(int enter_time) {
                this.enter_time = enter_time;
            }

            public int getEnter_time_plan() {
                return enter_time_plan;
            }

            public void setEnter_time_plan(int enter_time_plan) {
                this.enter_time_plan = enter_time_plan;
            }

            public int getEvaluate_status() {
                return evaluate_status;
            }

            public void setEvaluate_status(int evaluate_status) {
                this.evaluate_status = evaluate_status;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getImmobilization() {
                return immobilization;
            }

            public void setImmobilization(int immobilization) {
                this.immobilization = immobilization;
            }

            public int getLast_edit_time() {
                return last_edit_time;
            }

            public void setLast_edit_time(int last_edit_time) {
                this.last_edit_time = last_edit_time;
            }

            public int getLeave_time_plan() {
                return leave_time_plan;
            }

            public void setLeave_time_plan(int leave_time_plan) {
                this.leave_time_plan = leave_time_plan;
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

            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }

            public String getOther_bill_code() {
                return other_bill_code;
            }

            public void setOther_bill_code(String other_bill_code) {
                this.other_bill_code = other_bill_code;
            }

            public int getPay_type_id() {
                return pay_type_id;
            }

            public void setPay_type_id(int pay_type_id) {
                this.pay_type_id = pay_type_id;
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

            public int getStay_type() {
                return stay_type;
            }

            public void setStay_type(int stay_type) {
                this.stay_type = stay_type;
            }

            public int getUnit() {
                return unit;
            }

            public void setUnit(int unit) {
                this.unit = unit;
            }

            public int getUser_count() {
                return user_count;
            }

            public void setUser_count(int user_count) {
                this.user_count = user_count;
            }

            public int getUser_coupon_id() {
                return user_coupon_id;
            }

            public void setUser_coupon_id(int user_coupon_id) {
                this.user_coupon_id = user_coupon_id;
            }

            public int getUser_type() {
                return user_type;
            }

            public void setUser_type(int user_type) {
                this.user_type = user_type;
            }

            public List<RoomListDTO> getRoom_list() {
                return room_list;
            }

            public void setRoom_list(List<RoomListDTO> room_list) {
                this.room_list = room_list;
            }

            public List<RoomTypeIdsDTO> getRoom_type_ids() {
                return room_type_ids;
            }

            public void setRoom_type_ids(List<RoomTypeIdsDTO> room_type_ids) {
                this.room_type_ids = room_type_ids;
            }

            public static class RoomListDTO implements Serializable {
                private int bill_id;
                private int cash_pledge;
                private int create_time;
                private int end_time;
                private int id;
                private int lock_id;
                private int main_room;
                private int password_status;
                private int price;
                private int room_id;
                private String room_number;
                private int room_price;
                private String room_service_name;
                private String room_service_selected;
                private int room_type_id;
                private String room_type_name;
                private int start_time;
                private int status;
                private int stay_time;
                private int unit;
                private int update_time;
                private int user_count;
                private List<?> enter_msg;
                private List<RoomServiceDTO> room_service;
                private List<UserListDTO> user_list;

                public int getBill_id() {
                    return bill_id;
                }

                public void setBill_id(int bill_id) {
                    this.bill_id = bill_id;
                }

                public int getCash_pledge() {
                    return cash_pledge;
                }

                public void setCash_pledge(int cash_pledge) {
                    this.cash_pledge = cash_pledge;
                }

                public int getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(int create_time) {
                    this.create_time = create_time;
                }

                public int getEnd_time() {
                    return end_time;
                }

                public void setEnd_time(int end_time) {
                    this.end_time = end_time;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getLock_id() {
                    return lock_id;
                }

                public void setLock_id(int lock_id) {
                    this.lock_id = lock_id;
                }

                public int getMain_room() {
                    return main_room;
                }

                public void setMain_room(int main_room) {
                    this.main_room = main_room;
                }

                public int getPassword_status() {
                    return password_status;
                }

                public void setPassword_status(int password_status) {
                    this.password_status = password_status;
                }

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
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

                public int getRoom_price() {
                    return room_price;
                }

                public void setRoom_price(int room_price) {
                    this.room_price = room_price;
                }

                public String getRoom_service_name() {
                    return room_service_name;
                }

                public void setRoom_service_name(String room_service_name) {
                    this.room_service_name = room_service_name;
                }

                public String getRoom_service_selected() {
                    return room_service_selected;
                }

                public void setRoom_service_selected(String room_service_selected) {
                    this.room_service_selected = room_service_selected;
                }

                public int getRoom_type_id() {
                    return room_type_id;
                }

                public void setRoom_type_id(int room_type_id) {
                    this.room_type_id = room_type_id;
                }

                public String getRoom_type_name() {
                    return room_type_name;
                }

                public void setRoom_type_name(String room_type_name) {
                    this.room_type_name = room_type_name;
                }

                public int getStart_time() {
                    return start_time;
                }

                public void setStart_time(int start_time) {
                    this.start_time = start_time;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
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

                public int getUpdate_time() {
                    return update_time;
                }

                public void setUpdate_time(int update_time) {
                    this.update_time = update_time;
                }

                public int getUser_count() {
                    return user_count;
                }

                public void setUser_count(int user_count) {
                    this.user_count = user_count;
                }

                public List<?> getEnter_msg() {
                    return enter_msg;
                }

                public void setEnter_msg(List<?> enter_msg) {
                    this.enter_msg = enter_msg;
                }

                public List<RoomServiceDTO> getRoom_service() {
                    return room_service;
                }

                public void setRoom_service(List<RoomServiceDTO> room_service) {
                    this.room_service = room_service;
                }

                public List<UserListDTO> getUser_list() {
                    return user_list;
                }

                public void setUser_list(List<UserListDTO> user_list) {
                    this.user_list = user_list;
                }

                public static class RoomServiceDTO implements Serializable {
                    private String before_end_time;
                    private int cancelable;
                    private int create_time;
                    private int id;
                    private int is_show;
                    private int member_grade_id;
                    private int price;
                    private int room_property_id;
                    private int room_type_id;
                    private String service_content;
                    private String service_name;
                    private int status;
                    private int sys_setting;

                    public String getBefore_end_time() {
                        return before_end_time;
                    }

                    public void setBefore_end_time(String before_end_time) {
                        this.before_end_time = before_end_time;
                    }

                    public int getCancelable() {
                        return cancelable;
                    }

                    public void setCancelable(int cancelable) {
                        this.cancelable = cancelable;
                    }

                    public int getCreate_time() {
                        return create_time;
                    }

                    public void setCreate_time(int create_time) {
                        this.create_time = create_time;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public int getIs_show() {
                        return is_show;
                    }

                    public void setIs_show(int is_show) {
                        this.is_show = is_show;
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

                    public int getRoom_property_id() {
                        return room_property_id;
                    }

                    public void setRoom_property_id(int room_property_id) {
                        this.room_property_id = room_property_id;
                    }

                    public int getRoom_type_id() {
                        return room_type_id;
                    }

                    public void setRoom_type_id(int room_type_id) {
                        this.room_type_id = room_type_id;
                    }

                    public String getService_content() {
                        return service_content;
                    }

                    public void setService_content(String service_content) {
                        this.service_content = service_content;
                    }

                    public String getService_name() {
                        return service_name;
                    }

                    public void setService_name(String service_name) {
                        this.service_name = service_name;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
                    }

                    public int getSys_setting() {
                        return sys_setting;
                    }

                    public void setSys_setting(int sys_setting) {
                        this.sys_setting = sys_setting;
                    }
                }

                public static class UserListDTO implements Serializable {
                    private int bill_room_room_id;
                    private String card_id;
                    private int card_type;
                    private int create_time;
                    private int gender;
                    private int id;
                    private int is_main;
                    private String name;
                    private int nation_id;
                    private String nation_name;
                    private String phone;

                    public int getBill_room_room_id() {
                        return bill_room_room_id;
                    }

                    public void setBill_room_room_id(int bill_room_room_id) {
                        this.bill_room_room_id = bill_room_room_id;
                    }

                    public String getCard_id() {
                        return card_id;
                    }

                    public void setCard_id(String card_id) {
                        this.card_id = card_id;
                    }

                    public int getCard_type() {
                        return card_type;
                    }

                    public void setCard_type(int card_type) {
                        this.card_type = card_type;
                    }

                    public int getCreate_time() {
                        return create_time;
                    }

                    public void setCreate_time(int create_time) {
                        this.create_time = create_time;
                    }

                    public int getGender() {
                        return gender;
                    }

                    public void setGender(int gender) {
                        this.gender = gender;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public int getIs_main() {
                        return is_main;
                    }

                    public void setIs_main(int is_main) {
                        this.is_main = is_main;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public int getNation_id() {
                        return nation_id;
                    }

                    public void setNation_id(int nation_id) {
                        this.nation_id = nation_id;
                    }

                    public String getNation_name() {
                        return nation_name;
                    }

                    public void setNation_name(String nation_name) {
                        this.nation_name = nation_name;
                    }

                    public String getPhone() {
                        return phone;
                    }

                    public void setPhone(String phone) {
                        this.phone = phone;
                    }
                }
            }

            public static class RoomTypeIdsDTO implements Serializable {
                private int room_service_id;
                private int room_type_id;

                public int getRoom_service_id() {
                    return room_service_id;
                }

                public void setRoom_service_id(int room_service_id) {
                    this.room_service_id = room_service_id;
                }

                public int getRoom_type_id() {
                    return room_type_id;
                }

                public void setRoom_type_id(int room_type_id) {
                    this.room_type_id = room_type_id;
                }
            }
        }
    }
}
