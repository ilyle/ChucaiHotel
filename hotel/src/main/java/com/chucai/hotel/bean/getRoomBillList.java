package com.chucai.hotel.bean;

import java.io.Serializable;
import java.util.List;

public class getRoomBillList implements Serializable {

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
        private List<ListDTO> list;
        private int count;
        private NumTipDTO numTip;
        private ToShopTipsDTO to_shop_tips;
        private List<?> leave_shop_tips;

        public List<ListDTO> getList() {
            return list;
        }

        public void setList(List<ListDTO> list) {
            this.list = list;
        }

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

        public ToShopTipsDTO getTo_shop_tips() {
            return to_shop_tips;
        }

        public void setTo_shop_tips(ToShopTipsDTO to_shop_tips) {
            this.to_shop_tips = to_shop_tips;
        }

        public List<?> getLeave_shop_tips() {
            return leave_shop_tips;
        }

        public void setLeave_shop_tips(List<?> leave_shop_tips) {
            this.leave_shop_tips = leave_shop_tips;
        }

        public static class NumTipDTO {
            private int status_all;
            private int status_2;
            private int status_3;
            private int status_4;
            private int status_5;
            private int status_6;
            private int status_7;
            private int status_8;
            private int status_9;
            private int status_10;

            public int getStatus_all() {
                return status_all;
            }

            public void setStatus_all(int status_all) {
                this.status_all = status_all;
            }

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

            public int getStatus_9() {
                return status_9;
            }

            public void setStatus_9(int status_9) {
                this.status_9 = status_9;
            }

            public int getStatus_10() {
                return status_10;
            }

            public void setStatus_10(int status_10) {
                this.status_10 = status_10;
            }
        }

        public static class ToShopTipsDTO {
            private int tip1;
            private int tip2;

            public int getTip1() {
                return tip1;
            }

            public void setTip1(int tip1) {
                this.tip1 = tip1;
            }

            public int getTip2() {
                return tip2;
            }

            public void setTip2(int tip2) {
                this.tip2 = tip2;
            }
        }

        public static class ListDTO {
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
            private int cash_pledge;
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
            private int leave_time_plan;
            private Object leave_time;
            private int last_edit_time;
            private int create_time;
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
            private int original_cash_pledge;
            private int intermediaries_id;
            private int total_commission_price;
            private int price_project;
            private int already_pay_cash_pledge;
            private int is_arrage;
            private String connect_code;
            private Object third_part_bill_id;
            private String bill_status_name;
            private String shop_name;
            private String source_sign;
            private String bill_source_name;
            private int immobilization;
            private int consume_type_stay_time;
            private String consume_type_name;
            private int total_room_amount;
            private int already_consume;
            private String room_number;
            private String room_type_name;
            private int room_type_id;
            private int first_day_room_price;
            private boolean canCancel;

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

            public int getCash_pledge() {
                return cash_pledge;
            }

            public void setCash_pledge(int cash_pledge) {
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

            public int getLeave_time_plan() {
                return leave_time_plan;
            }

            public void setLeave_time_plan(int leave_time_plan) {
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

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
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

            public int getOriginal_cash_pledge() {
                return original_cash_pledge;
            }

            public void setOriginal_cash_pledge(int original_cash_pledge) {
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

            public int getAlready_pay_cash_pledge() {
                return already_pay_cash_pledge;
            }

            public void setAlready_pay_cash_pledge(int already_pay_cash_pledge) {
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

            public String getBill_status_name() {
                return bill_status_name;
            }

            public void setBill_status_name(String bill_status_name) {
                this.bill_status_name = bill_status_name;
            }

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
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

            public String getConsume_type_name() {
                return consume_type_name;
            }

            public void setConsume_type_name(String consume_type_name) {
                this.consume_type_name = consume_type_name;
            }

            public int getTotal_room_amount() {
                return total_room_amount;
            }

            public void setTotal_room_amount(int total_room_amount) {
                this.total_room_amount = total_room_amount;
            }

            public int getAlready_consume() {
                return already_consume;
            }

            public void setAlready_consume(int already_consume) {
                this.already_consume = already_consume;
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

            public int getRoom_type_id() {
                return room_type_id;
            }

            public void setRoom_type_id(int room_type_id) {
                this.room_type_id = room_type_id;
            }

            public int getFirst_day_room_price() {
                return first_day_room_price;
            }

            public void setFirst_day_room_price(int first_day_room_price) {
                this.first_day_room_price = first_day_room_price;
            }

            public boolean isCanCancel() {
                return canCancel;
            }

            public void setCanCancel(boolean canCancel) {
                this.canCancel = canCancel;
            }
        }
    }
}
