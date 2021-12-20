package com.chucai.hotel.bean;

public class UpdateBean {

    /**
     * retcode : 0
     * returnMsg : 获取成功
     * data : {"machineVersionId":1,"machineVersionUrl":"www.baidu.com","machineCode":10}
     */

    private int retcode;
    private String returnMsg;
    private DataDTO data;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        /**
         * machineVersionId : 1
         * machineVersionUrl : www.baidu.com
         * machineCode : 10
         */

        private int machineVersionId;
        private String machineVersionUrl;
        private int machineCode;

        public int getMachineVersionId() {
            return machineVersionId;
        }

        public void setMachineVersionId(int machineVersionId) {
            this.machineVersionId = machineVersionId;
        }

        public String getMachineVersionUrl() {
            return machineVersionUrl;
        }

        public void setMachineVersionUrl(String machineVersionUrl) {
            this.machineVersionUrl = machineVersionUrl;
        }

        public int getMachineCode() {
            return machineCode;
        }

        public void setMachineCode(int machineCode) {
            this.machineCode = machineCode;
        }
    }
}
