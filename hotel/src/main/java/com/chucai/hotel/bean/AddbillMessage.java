package com.chucai.hotel.bean;

import java.io.Serializable;
import java.util.List;

public class AddbillMessage implements Serializable {


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

    public static class DataDTO {

        private int room_property_id;
        private String link_man;
        private String link_phone;
        private int price_project;
        private Object intermediaries_id;
        private int bill_source;
        private int stay_time;
        private int leave_time_plan;
        private Object coupon_id;
        private int coupon_discounts;
        private String other_bill_code;
        private String memo;
        private List<RoomListDTO> room_list;

        public int getRoom_property_id() {
            return room_property_id;
        }

        public void setRoom_property_id(int room_property_id) {
            this.room_property_id = room_property_id;
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

        public int getPrice_project() {
            return price_project;
        }

        public void setPrice_project(int price_project) {
            this.price_project = price_project;
        }

        public Object getIntermediaries_id() {
            return intermediaries_id;
        }

        public void setIntermediaries_id(Object intermediaries_id) {
            this.intermediaries_id = intermediaries_id;
        }

        public int getBill_source() {
            return bill_source;
        }

        public void setBill_source(int bill_source) {
            this.bill_source = bill_source;
        }

        public int getStay_time() {
            return stay_time;
        }

        public void setStay_time(int stay_time) {
            this.stay_time = stay_time;
        }

        public int getLeave_time_plan() {
            return leave_time_plan;
        }

        public void setLeave_time_plan(int leave_time_plan) {
            this.leave_time_plan = leave_time_plan;
        }

        public Object getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(Object coupon_id) {
            this.coupon_id = coupon_id;
        }

        public int getCoupon_discounts() {
            return coupon_discounts;
        }

        public void setCoupon_discounts(int coupon_discounts) {
            this.coupon_discounts = coupon_discounts;
        }

        public String getOther_bill_code() {
            return other_bill_code;
        }

        public void setOther_bill_code(String other_bill_code) {
            this.other_bill_code = other_bill_code;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public List<RoomListDTO> getRoom_list() {
            return room_list;
        }

        public void setRoom_list(List<RoomListDTO> room_list) {
            this.room_list = room_list;
        }

        public static class RoomListDTO {
            private int id;
            private int room_id;
            private int room_type_id;
            private String room_number;
            private String room_type_name;
            private List<PriceDTO> price;
            private double cash_pledge;
            private List<PriceDTO> price_original;
            private double cash_pledge_original;
            private List<RoomServiceDTO> room_service;
            private int room_service_selected;
            private List<EnterMsgDTO> enter_msg;
            private Object custom_room_price;
            private Object custom_cash_pledge;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getRoom_id() {
                return room_id;
            }

            public void setRoom_id(int room_id) {
                this.room_id = room_id;
            }

            public int getRoom_type_id() {
                return room_type_id;
            }

            public void setRoom_type_id(int room_type_id) {
                this.room_type_id = room_type_id;
            }

            public String getRoom_number() {
                return room_number;
            }

            public void setRoom_number(String room_number) {
                this.room_number = room_number;
            }

            public String getRoom_type_name() {
                return room_type_name;
            }

            public void setRoom_type_name(String room_type_name) {
                this.room_type_name = room_type_name;
            }

            public List<PriceDTO> getPrice() {
                return price;
            }

            public void setPrice(List<PriceDTO> price) {
                this.price = price;
            }

            public double getCash_pledge() {
                return cash_pledge;
            }

            public void setCash_pledge(double cash_pledge) {
                this.cash_pledge = cash_pledge;
            }

            public List<PriceDTO> getPrice_original() {
                return price_original;
            }

            public void setPrice_original(List<PriceDTO> price_original) {
                this.price_original = price_original;
            }

            public double getCash_pledge_original() {
                return cash_pledge_original;
            }

            public void setCash_pledge_original(double cash_pledge_original) {
                this.cash_pledge_original = cash_pledge_original;
            }

            public List<RoomServiceDTO> getRoom_service() {
                return room_service;
            }

            public void setRoom_service(List<RoomServiceDTO> room_service) {
                this.room_service = room_service;
            }

            public int getRoom_service_selected() {
                return room_service_selected;
            }

            public void setRoom_service_selected(int room_service_selected) {
                this.room_service_selected = room_service_selected;
            }

            public List<EnterMsgDTO> getEnter_msg() {
                return enter_msg;
            }

            public void setEnter_msg(List<EnterMsgDTO> enter_msg) {
                this.enter_msg = enter_msg;
            }

            public Object getCustom_room_price() {
                return custom_room_price;
            }

            public void setCustom_room_price(Object custom_room_price) {
                this.custom_room_price = custom_room_price;
            }

            public Object getCustom_cash_pledge() {
                return custom_cash_pledge;
            }

            public void setCustom_cash_pledge(Object custom_cash_pledge) {
                this.custom_cash_pledge = custom_cash_pledge;
            }

            public static class PriceDTO {
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

            public static class RoomServiceDTO {
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

            public static class EnterMsgDTO {
                private String name;
                private String gender;
                private String card_type;
                private String id_card;
                private String link_phone;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getGender() {
                    return gender;
                }

                public void setGender(String gender) {
                    this.gender = gender;
                }

                public String getCard_type() {
                    return card_type;
                }

                public void setCard_type(String card_type) {
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
