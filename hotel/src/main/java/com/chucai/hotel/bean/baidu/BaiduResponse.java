package com.chucai.hotel.bean.baidu;

/**
 * Created by xujie on 2021/12/19.
 * Mail : 617314917@qq.com
 */
public class BaiduResponse<T> {

    private Integer error_code;
    private String error_msg;
    private Long lod_id;
    private Integer cached;
    private T result;

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public Long getLod_id() {
        return lod_id;
    }

    public void setLod_id(Long lod_id) {
        this.lod_id = lod_id;
    }

    public Integer getCached() {
        return cached;
    }

    public void setCached(Integer cached) {
        this.cached = cached;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
