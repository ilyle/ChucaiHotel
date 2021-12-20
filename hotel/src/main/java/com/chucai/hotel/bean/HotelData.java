package com.chucai.hotel.bean;

import java.util.List;

public class HotelData {

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
        private int id;
        private String ip;
        private String admin_name;
        private String admin_pwd;
        private int login_times;
        private String set_web_title;
        private String set_web_copyright;
        private String set_web_copyright_url;
        private String set_web_logo;
        private String set_web_beian;
        private int set_auth;
        private int last_edit_time;
        private int create_time;
        private int last_login_time;
        private int update_time;
        private String admin_type;
        private String nickname;
        private Object phone;
        private String shop_id;
        private String shop_id_zong;
        private String shop_name;
        private int shop_limit_time;
        private int admin_id;
        private int limit_time;
        private String user_token;
        private List<AuthListDTO> auth_list;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getAdmin_name() {
            return admin_name;
        }

        public void setAdmin_name(String admin_name) {
            this.admin_name = admin_name;
        }

        public String getAdmin_pwd() {
            return admin_pwd;
        }

        public void setAdmin_pwd(String admin_pwd) {
            this.admin_pwd = admin_pwd;
        }

        public int getLogin_times() {
            return login_times;
        }

        public void setLogin_times(int login_times) {
            this.login_times = login_times;
        }

        public String getSet_web_title() {
            return set_web_title;
        }

        public void setSet_web_title(String set_web_title) {
            this.set_web_title = set_web_title;
        }

        public String getSet_web_copyright() {
            return set_web_copyright;
        }

        public void setSet_web_copyright(String set_web_copyright) {
            this.set_web_copyright = set_web_copyright;
        }

        public String getSet_web_copyright_url() {
            return set_web_copyright_url;
        }

        public void setSet_web_copyright_url(String set_web_copyright_url) {
            this.set_web_copyright_url = set_web_copyright_url;
        }

        public String getSet_web_logo() {
            return set_web_logo;
        }

        public void setSet_web_logo(String set_web_logo) {
            this.set_web_logo = set_web_logo;
        }

        public String getSet_web_beian() {
            return set_web_beian;
        }

        public void setSet_web_beian(String set_web_beian) {
            this.set_web_beian = set_web_beian;
        }

        public int getSet_auth() {
            return set_auth;
        }

        public void setSet_auth(int set_auth) {
            this.set_auth = set_auth;
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

        public int getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(int last_login_time) {
            this.last_login_time = last_login_time;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public String getAdmin_type() {
            return admin_type;
        }

        public void setAdmin_type(String admin_type) {
            this.admin_type = admin_type;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_id_zong() {
            return shop_id_zong;
        }

        public void setShop_id_zong(String shop_id_zong) {
            this.shop_id_zong = shop_id_zong;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public int getShop_limit_time() {
            return shop_limit_time;
        }

        public void setShop_limit_time(int shop_limit_time) {
            this.shop_limit_time = shop_limit_time;
        }

        public int getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(int admin_id) {
            this.admin_id = admin_id;
        }

        public int getLimit_time() {
            return limit_time;
        }

        public void setLimit_time(int limit_time) {
            this.limit_time = limit_time;
        }

        public String getUser_token() {
            return user_token;
        }

        public void setUser_token(String user_token) {
            this.user_token = user_token;
        }

        public List<AuthListDTO> getAuth_list() {
            return auth_list;
        }

        public void setAuth_list(List<AuthListDTO> auth_list) {
            this.auth_list = auth_list;
        }

        public static class AuthListDTO {
            private int id;
            private int shop_id_zong;
            private int shop_id;
            private int role_id;
            private int auth_id;
            private int create_time;
            private String pid;
            private String auth_name;
            private String auth_mark;
            private String auth_type;
            private String router_url;
            private String api_url;
            private String icon;
            private int sort;
            private int last_edit_time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getShop_id_zong() {
                return shop_id_zong;
            }

            public void setShop_id_zong(int shop_id_zong) {
                this.shop_id_zong = shop_id_zong;
            }

            public int getShop_id() {
                return shop_id;
            }

            public void setShop_id(int shop_id) {
                this.shop_id = shop_id;
            }

            public int getRole_id() {
                return role_id;
            }

            public void setRole_id(int role_id) {
                this.role_id = role_id;
            }

            public int getAuth_id() {
                return auth_id;
            }

            public void setAuth_id(int auth_id) {
                this.auth_id = auth_id;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getAuth_name() {
                return auth_name;
            }

            public void setAuth_name(String auth_name) {
                this.auth_name = auth_name;
            }

            public String getAuth_mark() {
                return auth_mark;
            }

            public void setAuth_mark(String auth_mark) {
                this.auth_mark = auth_mark;
            }

            public String getAuth_type() {
                return auth_type;
            }

            public void setAuth_type(String auth_type) {
                this.auth_type = auth_type;
            }

            public String getRouter_url() {
                return router_url;
            }

            public void setRouter_url(String router_url) {
                this.router_url = router_url;
            }

            public String getApi_url() {
                return api_url;
            }

            public void setApi_url(String api_url) {
                this.api_url = api_url;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getLast_edit_time() {
                return last_edit_time;
            }

            public void setLast_edit_time(int last_edit_time) {
                this.last_edit_time = last_edit_time;
            }
        }
    }
}
