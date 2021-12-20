package com.chucai.hotel.bean;

import java.util.List;

public class RoomMessage {


    /**
     * retcode : 0
     * returnMsg : success
     * data : {"total":0,"data":[{"gId":1,"gSId":17653,"gEsId":0,"gUniqueId":"","gWdItemid":0,"gYzItemid":0,"gName":"大床房","gLabel":"","gCover":"/upload/gallery/thumbnail/65388DA3-A896-4873-BB64D28181AF-tbl.png","gCover1":"","gQrcode":"","gShareQrcode":"","gCId":0,"gKind1":1,"gKind2":0,"gKind3":0,"gCost":0,"gPrice":0.01,"gGoodsWeight":0,"gGoodsWeightType":2,"gShowVip":1,"gVipPrice":0,"gVipPriceList":"","gHadVipPrice":0,"gPoints":0,"gUnit":"","gSendPoint":0,"gBackNum":1,"gBackUnit":1,"gOriPrice":0.01,"gVipDiscount":100,"gType":1,"gKnowledgePayType":0,"gKnowledgeTotal":0,"gIsSale":1,"gIsTop":1,"gIsGlobal":0,"gWeight":0,"gStock":-1,"gDayStock":0,"gDayStockUpdateTime":0,"gStockType":1,"gHotelStock":5,"gStockShow":1,"gSold":0,"gSoldShow":0,"gLimit":0,"gDayLimit":0,"gHasBranch":0,"gIsBack":1,"gSpecial":0,"gIsQuality":1,"gIsTruth":1,"gCustomLabel":"","gCakeGift":"","gVipBuy":0,"gHasFormat":1,"gIsDeduct":0,"gExpfeeType":1,"gExpfeeShow":1,"gUnifiedFee":0,"gUnifiedTpid":0,"gJoinAct":1,"gBrief":"","gDetail":"","gParameter":"","gVideoUrl":"","gVrUrl":"","gFormatType":"","gAppointmentLength":"","gAppointmentTime":"","gAppointmentDate":"","gAppointmentKind":0,"gHasWindow":1,"gAddBed":1,"gHouseIsopen":0,"gBedInfo":"单床","gDatePrice":0.01,"gHotelDateList":"[]","gShowNum":0,"gShowNumShow":0,"gRoomSize":"23","gPush":0,"gExtraMessage":"","gMessageTpid":0,"gRecommendGoods":"","gIsDiscuss":0,"gDiscussInfo":"[\"2020-07-01\",\"2020-07-01\",\"2020-07-01\",\"2020-07-01\"]","gDeleted":0,"gCreateTime":1615367187,"gUpdateTime":1617084591,"gSoldType":0,"gFakeBuynum":0,"gApplayGoodsShow":1,"gSequenceDay":0,"gSmallNum":1,"gSupplierId":0,"gJoinDiscount":1,"gHasBreakfast":0,"gIndependentMall":0,"gNumberLimit":0,"gShowJoinList":0,"gForward":0,"gShowRealNum":0,"gRegionAddBy":0,"gSequenceDayShow":0,"gSupplierExport":0,"gOsId":0,"gVerifyRemark":"","gVerifyApplyTime":0,"gLeastBuy":0,"gH5Qrcode":""}]}
     */

    private int retcode;
    private String returnMsg;
    private DataDTOX data;

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

    public DataDTOX getData() {
        return data;
    }

    public void setData(DataDTOX data) {
        this.data = data;
    }

    public static class DataDTOX {
        /**
         * total : 0
         * data : [{"gId":1,"gSId":17653,"gEsId":0,"gUniqueId":"","gWdItemid":0,"gYzItemid":0,"gName":"大床房","gLabel":"","gCover":"/upload/gallery/thumbnail/65388DA3-A896-4873-BB64D28181AF-tbl.png","gCover1":"","gQrcode":"","gShareQrcode":"","gCId":0,"gKind1":1,"gKind2":0,"gKind3":0,"gCost":0,"gPrice":0.01,"gGoodsWeight":0,"gGoodsWeightType":2,"gShowVip":1,"gVipPrice":0,"gVipPriceList":"","gHadVipPrice":0,"gPoints":0,"gUnit":"","gSendPoint":0,"gBackNum":1,"gBackUnit":1,"gOriPrice":0.01,"gVipDiscount":100,"gType":1,"gKnowledgePayType":0,"gKnowledgeTotal":0,"gIsSale":1,"gIsTop":1,"gIsGlobal":0,"gWeight":0,"gStock":-1,"gDayStock":0,"gDayStockUpdateTime":0,"gStockType":1,"gHotelStock":5,"gStockShow":1,"gSold":0,"gSoldShow":0,"gLimit":0,"gDayLimit":0,"gHasBranch":0,"gIsBack":1,"gSpecial":0,"gIsQuality":1,"gIsTruth":1,"gCustomLabel":"","gCakeGift":"","gVipBuy":0,"gHasFormat":1,"gIsDeduct":0,"gExpfeeType":1,"gExpfeeShow":1,"gUnifiedFee":0,"gUnifiedTpid":0,"gJoinAct":1,"gBrief":"","gDetail":"","gParameter":"","gVideoUrl":"","gVrUrl":"","gFormatType":"","gAppointmentLength":"","gAppointmentTime":"","gAppointmentDate":"","gAppointmentKind":0,"gHasWindow":1,"gAddBed":1,"gHouseIsopen":0,"gBedInfo":"单床","gDatePrice":0.01,"gHotelDateList":"[]","gShowNum":0,"gShowNumShow":0,"gRoomSize":"23","gPush":0,"gExtraMessage":"","gMessageTpid":0,"gRecommendGoods":"","gIsDiscuss":0,"gDiscussInfo":"[\"2020-07-01\",\"2020-07-01\",\"2020-07-01\",\"2020-07-01\"]","gDeleted":0,"gCreateTime":1615367187,"gUpdateTime":1617084591,"gSoldType":0,"gFakeBuynum":0,"gApplayGoodsShow":1,"gSequenceDay":0,"gSmallNum":1,"gSupplierId":0,"gJoinDiscount":1,"gHasBreakfast":0,"gIndependentMall":0,"gNumberLimit":0,"gShowJoinList":0,"gForward":0,"gShowRealNum":0,"gRegionAddBy":0,"gSequenceDayShow":0,"gSupplierExport":0,"gOsId":0,"gVerifyRemark":"","gVerifyApplyTime":0,"gLeastBuy":0,"gH5Qrcode":""}]
         */

        private int total;
        private List<DataDTO> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataDTO> getData() {
            return data;
        }

        public void setData(List<DataDTO> data) {
            this.data = data;
        }

        public static class DataDTO {
            /**
             * gId : 1
             * gSId : 17653
             * gEsId : 0
             * gUniqueId :
             * gWdItemid : 0
             * gYzItemid : 0
             * gName : 大床房
             * gLabel :
             * gCover : /upload/gallery/thumbnail/65388DA3-A896-4873-BB64D28181AF-tbl.png
             * gCover1 :
             * gQrcode :
             * gShareQrcode :
             * gCId : 0
             * gKind1 : 1
             * gKind2 : 0
             * gKind3 : 0
             * gCost : 0.0
             * gPrice : 0.01
             * gGoodsWeight : 0.0
             * gGoodsWeightType : 2
             * gShowVip : 1
             * gVipPrice : 0.0
             * gVipPriceList :
             * gHadVipPrice : 0
             * gPoints : 0
             * gUnit :
             * gSendPoint : 0.0
             * gBackNum : 1
             * gBackUnit : 1
             * gOriPrice : 0.01
             * gVipDiscount : 100
             * gType : 1
             * gKnowledgePayType : 0
             * gKnowledgeTotal : 0
             * gIsSale : 1
             * gIsTop : 1
             * gIsGlobal : 0
             * gWeight : 0
             * gStock : -1
             * gDayStock : 0
             * gDayStockUpdateTime : 0
             * gStockType : 1
             * gHotelStock : 5
             * gStockShow : 1
             * gSold : 0
             * gSoldShow : 0
             * gLimit : 0
             * gDayLimit : 0
             * gHasBranch : 0
             * gIsBack : 1
             * gSpecial : 0
             * gIsQuality : 1
             * gIsTruth : 1
             * gCustomLabel :
             * gCakeGift :
             * gVipBuy : 0
             * gHasFormat : 1
             * gIsDeduct : 0
             * gExpfeeType : 1
             * gExpfeeShow : 1
             * gUnifiedFee : 0.0
             * gUnifiedTpid : 0
             * gJoinAct : 1
             * gBrief :
             * gDetail :
             * gParameter :
             * gVideoUrl :
             * gVrUrl :
             * gFormatType :
             * gAppointmentLength :
             * gAppointmentTime :
             * gAppointmentDate :
             * gAppointmentKind : 0
             * gHasWindow : 1
             * gAddBed : 1
             * gHouseIsopen : 0
             * gBedInfo : 单床
             * gDatePrice : 0.01
             * gHotelDateList : []
             * gShowNum : 0
             * gShowNumShow : 0
             * gRoomSize : 23
             * gPush : 0
             * gExtraMessage :
             * gMessageTpid : 0
             * gRecommendGoods :
             * gIsDiscuss : 0
             * gDiscussInfo : ["2020-07-01","2020-07-01","2020-07-01","2020-07-01"]
             * gDeleted : 0
             * gCreateTime : 1615367187
             * gUpdateTime : 1617084591
             * gSoldType : 0
             * gFakeBuynum : 0
             * gApplayGoodsShow : 1
             * gSequenceDay : 0
             * gSmallNum : 1
             * gSupplierId : 0
             * gJoinDiscount : 1
             * gHasBreakfast : 0
             * gIndependentMall : 0
             * gNumberLimit : 0
             * gShowJoinList : 0
             * gForward : 0
             * gShowRealNum : 0
             * gRegionAddBy : 0
             * gSequenceDayShow : 0
             * gSupplierExport : 0
             * gOsId : 0
             * gVerifyRemark :
             * gVerifyApplyTime : 0
             * gLeastBuy : 0
             * gH5Qrcode :
             */

            private int gId;
            private int gSId;
            private int gEsId;
            private String gUniqueId;
            private int gWdItemid;
            private int gYzItemid;
            private String gName;
            private String gLabel;
            private String gCover;
            private String gCover1;
            private String gQrcode;
            private String gShareQrcode;
            private int gCId;
            private int gKind1;
            private int gKind2;
            private int gKind3;
            private double gCost;
            private double gPrice;
            private double gGoodsWeight;
            private int gGoodsWeightType;
            private int gShowVip;
            private double gVipPrice;
            private String gVipPriceList;
            private int gHadVipPrice;
            private int gPoints;
            private String gUnit;
            private double gSendPoint;
            private int gBackNum;
            private int gBackUnit;
            private double gOriPrice;
            private int gVipDiscount;
            private int gType;
            private int gKnowledgePayType;
            private int gKnowledgeTotal;
            private int gIsSale;
            private int gIsTop;
            private int gIsGlobal;
            private int gWeight;
            private int gStock;
            private int gDayStock;
            private int gDayStockUpdateTime;
            private int gStockType;
            private int gHotelStock;
            private int gStockShow;
            private int gSold;
            private int gSoldShow;
            private int gLimit;
            private int gDayLimit;
            private int gHasBranch;
            private int gIsBack;
            private int gSpecial;
            private int gIsQuality;
            private int gIsTruth;
            private String gCustomLabel;
            private String gCakeGift;
            private int gVipBuy;
            private int gHasFormat;
            private int gIsDeduct;
            private int gExpfeeType;
            private int gExpfeeShow;
            private double gUnifiedFee;
            private int gUnifiedTpid;
            private int gJoinAct;
            private String gBrief;
            private String gDetail;
            private String gParameter;
            private String gVideoUrl;
            private String gVrUrl;
            private String gFormatType;
            private String gAppointmentLength;
            private String gAppointmentTime;
            private String gAppointmentDate;
            private int gAppointmentKind;
            private int gHasWindow;
            private int gAddBed;
            private int gHouseIsopen;
            private String gBedInfo;
            private double gDatePrice;
            private String gHotelDateList;
            private int gShowNum;
            private int gShowNumShow;
            private String gRoomSize;
            private int gPush;
            private String gExtraMessage;
            private int gMessageTpid;
            private String gRecommendGoods;
            private int gIsDiscuss;
            private String gDiscussInfo;
            private int gDeleted;
            private int gCreateTime;
            private int gUpdateTime;
            private int gSoldType;
            private int gFakeBuynum;
            private int gApplayGoodsShow;
            private int gSequenceDay;
            private int gSmallNum;
            private int gSupplierId;
            private int gJoinDiscount;
            private int gHasBreakfast;
            private int gIndependentMall;
            private int gNumberLimit;
            private int gShowJoinList;
            private int gForward;
            private int gShowRealNum;
            private int gRegionAddBy;
            private int gSequenceDayShow;
            private int gSupplierExport;
            private int gOsId;
            private String gVerifyRemark;
            private int gVerifyApplyTime;
            private int gLeastBuy;
            private String gH5Qrcode;

            public int getGId() {
                return gId;
            }

            public void setGId(int gId) {
                this.gId = gId;
            }

            public int getGSId() {
                return gSId;
            }

            public void setGSId(int gSId) {
                this.gSId = gSId;
            }

            public int getGEsId() {
                return gEsId;
            }

            public void setGEsId(int gEsId) {
                this.gEsId = gEsId;
            }

            public String getGUniqueId() {
                return gUniqueId;
            }

            public void setGUniqueId(String gUniqueId) {
                this.gUniqueId = gUniqueId;
            }

            public int getGWdItemid() {
                return gWdItemid;
            }

            public void setGWdItemid(int gWdItemid) {
                this.gWdItemid = gWdItemid;
            }

            public int getGYzItemid() {
                return gYzItemid;
            }

            public void setGYzItemid(int gYzItemid) {
                this.gYzItemid = gYzItemid;
            }

            public String getGName() {
                return gName;
            }

            public void setGName(String gName) {
                this.gName = gName;
            }

            public String getGLabel() {
                return gLabel;
            }

            public void setGLabel(String gLabel) {
                this.gLabel = gLabel;
            }

            public String getGCover() {
                return gCover;
            }

            public void setGCover(String gCover) {
                this.gCover = gCover;
            }

            public String getGCover1() {
                return gCover1;
            }

            public void setGCover1(String gCover1) {
                this.gCover1 = gCover1;
            }

            public String getGQrcode() {
                return gQrcode;
            }

            public void setGQrcode(String gQrcode) {
                this.gQrcode = gQrcode;
            }

            public String getGShareQrcode() {
                return gShareQrcode;
            }

            public void setGShareQrcode(String gShareQrcode) {
                this.gShareQrcode = gShareQrcode;
            }

            public int getGCId() {
                return gCId;
            }

            public void setGCId(int gCId) {
                this.gCId = gCId;
            }

            public int getGKind1() {
                return gKind1;
            }

            public void setGKind1(int gKind1) {
                this.gKind1 = gKind1;
            }

            public int getGKind2() {
                return gKind2;
            }

            public void setGKind2(int gKind2) {
                this.gKind2 = gKind2;
            }

            public int getGKind3() {
                return gKind3;
            }

            public void setGKind3(int gKind3) {
                this.gKind3 = gKind3;
            }

            public double getGCost() {
                return gCost;
            }

            public void setGCost(double gCost) {
                this.gCost = gCost;
            }

            public double getGPrice() {
                return gPrice;
            }

            public void setGPrice(double gPrice) {
                this.gPrice = gPrice;
            }

            public double getGGoodsWeight() {
                return gGoodsWeight;
            }

            public void setGGoodsWeight(double gGoodsWeight) {
                this.gGoodsWeight = gGoodsWeight;
            }

            public int getGGoodsWeightType() {
                return gGoodsWeightType;
            }

            public void setGGoodsWeightType(int gGoodsWeightType) {
                this.gGoodsWeightType = gGoodsWeightType;
            }

            public int getGShowVip() {
                return gShowVip;
            }

            public void setGShowVip(int gShowVip) {
                this.gShowVip = gShowVip;
            }

            public double getGVipPrice() {
                return gVipPrice;
            }

            public void setGVipPrice(double gVipPrice) {
                this.gVipPrice = gVipPrice;
            }

            public String getGVipPriceList() {
                return gVipPriceList;
            }

            public void setGVipPriceList(String gVipPriceList) {
                this.gVipPriceList = gVipPriceList;
            }

            public int getGHadVipPrice() {
                return gHadVipPrice;
            }

            public void setGHadVipPrice(int gHadVipPrice) {
                this.gHadVipPrice = gHadVipPrice;
            }

            public int getGPoints() {
                return gPoints;
            }

            public void setGPoints(int gPoints) {
                this.gPoints = gPoints;
            }

            public String getGUnit() {
                return gUnit;
            }

            public void setGUnit(String gUnit) {
                this.gUnit = gUnit;
            }

            public double getGSendPoint() {
                return gSendPoint;
            }

            public void setGSendPoint(double gSendPoint) {
                this.gSendPoint = gSendPoint;
            }

            public int getGBackNum() {
                return gBackNum;
            }

            public void setGBackNum(int gBackNum) {
                this.gBackNum = gBackNum;
            }

            public int getGBackUnit() {
                return gBackUnit;
            }

            public void setGBackUnit(int gBackUnit) {
                this.gBackUnit = gBackUnit;
            }

            public double getGOriPrice() {
                return gOriPrice;
            }

            public void setGOriPrice(double gOriPrice) {
                this.gOriPrice = gOriPrice;
            }

            public int getGVipDiscount() {
                return gVipDiscount;
            }

            public void setGVipDiscount(int gVipDiscount) {
                this.gVipDiscount = gVipDiscount;
            }

            public int getGType() {
                return gType;
            }

            public void setGType(int gType) {
                this.gType = gType;
            }

            public int getGKnowledgePayType() {
                return gKnowledgePayType;
            }

            public void setGKnowledgePayType(int gKnowledgePayType) {
                this.gKnowledgePayType = gKnowledgePayType;
            }

            public int getGKnowledgeTotal() {
                return gKnowledgeTotal;
            }

            public void setGKnowledgeTotal(int gKnowledgeTotal) {
                this.gKnowledgeTotal = gKnowledgeTotal;
            }

            public int getGIsSale() {
                return gIsSale;
            }

            public void setGIsSale(int gIsSale) {
                this.gIsSale = gIsSale;
            }

            public int getGIsTop() {
                return gIsTop;
            }

            public void setGIsTop(int gIsTop) {
                this.gIsTop = gIsTop;
            }

            public int getGIsGlobal() {
                return gIsGlobal;
            }

            public void setGIsGlobal(int gIsGlobal) {
                this.gIsGlobal = gIsGlobal;
            }

            public int getGWeight() {
                return gWeight;
            }

            public void setGWeight(int gWeight) {
                this.gWeight = gWeight;
            }

            public int getGStock() {
                return gStock;
            }

            public void setGStock(int gStock) {
                this.gStock = gStock;
            }

            public int getGDayStock() {
                return gDayStock;
            }

            public void setGDayStock(int gDayStock) {
                this.gDayStock = gDayStock;
            }

            public int getGDayStockUpdateTime() {
                return gDayStockUpdateTime;
            }

            public void setGDayStockUpdateTime(int gDayStockUpdateTime) {
                this.gDayStockUpdateTime = gDayStockUpdateTime;
            }

            public int getGStockType() {
                return gStockType;
            }

            public void setGStockType(int gStockType) {
                this.gStockType = gStockType;
            }

            public int getGHotelStock() {
                return gHotelStock;
            }

            public void setGHotelStock(int gHotelStock) {
                this.gHotelStock = gHotelStock;
            }

            public int getGStockShow() {
                return gStockShow;
            }

            public void setGStockShow(int gStockShow) {
                this.gStockShow = gStockShow;
            }

            public int getGSold() {
                return gSold;
            }

            public void setGSold(int gSold) {
                this.gSold = gSold;
            }

            public int getGSoldShow() {
                return gSoldShow;
            }

            public void setGSoldShow(int gSoldShow) {
                this.gSoldShow = gSoldShow;
            }

            public int getGLimit() {
                return gLimit;
            }

            public void setGLimit(int gLimit) {
                this.gLimit = gLimit;
            }

            public int getGDayLimit() {
                return gDayLimit;
            }

            public void setGDayLimit(int gDayLimit) {
                this.gDayLimit = gDayLimit;
            }

            public int getGHasBranch() {
                return gHasBranch;
            }

            public void setGHasBranch(int gHasBranch) {
                this.gHasBranch = gHasBranch;
            }

            public int getGIsBack() {
                return gIsBack;
            }

            public void setGIsBack(int gIsBack) {
                this.gIsBack = gIsBack;
            }

            public int getGSpecial() {
                return gSpecial;
            }

            public void setGSpecial(int gSpecial) {
                this.gSpecial = gSpecial;
            }

            public int getGIsQuality() {
                return gIsQuality;
            }

            public void setGIsQuality(int gIsQuality) {
                this.gIsQuality = gIsQuality;
            }

            public int getGIsTruth() {
                return gIsTruth;
            }

            public void setGIsTruth(int gIsTruth) {
                this.gIsTruth = gIsTruth;
            }

            public String getGCustomLabel() {
                return gCustomLabel;
            }

            public void setGCustomLabel(String gCustomLabel) {
                this.gCustomLabel = gCustomLabel;
            }

            public String getGCakeGift() {
                return gCakeGift;
            }

            public void setGCakeGift(String gCakeGift) {
                this.gCakeGift = gCakeGift;
            }

            public int getGVipBuy() {
                return gVipBuy;
            }

            public void setGVipBuy(int gVipBuy) {
                this.gVipBuy = gVipBuy;
            }

            public int getGHasFormat() {
                return gHasFormat;
            }

            public void setGHasFormat(int gHasFormat) {
                this.gHasFormat = gHasFormat;
            }

            public int getGIsDeduct() {
                return gIsDeduct;
            }

            public void setGIsDeduct(int gIsDeduct) {
                this.gIsDeduct = gIsDeduct;
            }

            public int getGExpfeeType() {
                return gExpfeeType;
            }

            public void setGExpfeeType(int gExpfeeType) {
                this.gExpfeeType = gExpfeeType;
            }

            public int getGExpfeeShow() {
                return gExpfeeShow;
            }

            public void setGExpfeeShow(int gExpfeeShow) {
                this.gExpfeeShow = gExpfeeShow;
            }

            public double getGUnifiedFee() {
                return gUnifiedFee;
            }

            public void setGUnifiedFee(double gUnifiedFee) {
                this.gUnifiedFee = gUnifiedFee;
            }

            public int getGUnifiedTpid() {
                return gUnifiedTpid;
            }

            public void setGUnifiedTpid(int gUnifiedTpid) {
                this.gUnifiedTpid = gUnifiedTpid;
            }

            public int getGJoinAct() {
                return gJoinAct;
            }

            public void setGJoinAct(int gJoinAct) {
                this.gJoinAct = gJoinAct;
            }

            public String getGBrief() {
                return gBrief;
            }

            public void setGBrief(String gBrief) {
                this.gBrief = gBrief;
            }

            public String getGDetail() {
                return gDetail;
            }

            public void setGDetail(String gDetail) {
                this.gDetail = gDetail;
            }

            public String getGParameter() {
                return gParameter;
            }

            public void setGParameter(String gParameter) {
                this.gParameter = gParameter;
            }

            public String getGVideoUrl() {
                return gVideoUrl;
            }

            public void setGVideoUrl(String gVideoUrl) {
                this.gVideoUrl = gVideoUrl;
            }

            public String getGVrUrl() {
                return gVrUrl;
            }

            public void setGVrUrl(String gVrUrl) {
                this.gVrUrl = gVrUrl;
            }

            public String getGFormatType() {
                return gFormatType;
            }

            public void setGFormatType(String gFormatType) {
                this.gFormatType = gFormatType;
            }

            public String getGAppointmentLength() {
                return gAppointmentLength;
            }

            public void setGAppointmentLength(String gAppointmentLength) {
                this.gAppointmentLength = gAppointmentLength;
            }

            public String getGAppointmentTime() {
                return gAppointmentTime;
            }

            public void setGAppointmentTime(String gAppointmentTime) {
                this.gAppointmentTime = gAppointmentTime;
            }

            public String getGAppointmentDate() {
                return gAppointmentDate;
            }

            public void setGAppointmentDate(String gAppointmentDate) {
                this.gAppointmentDate = gAppointmentDate;
            }

            public int getGAppointmentKind() {
                return gAppointmentKind;
            }

            public void setGAppointmentKind(int gAppointmentKind) {
                this.gAppointmentKind = gAppointmentKind;
            }

            public int getGHasWindow() {
                return gHasWindow;
            }

            public void setGHasWindow(int gHasWindow) {
                this.gHasWindow = gHasWindow;
            }

            public int getGAddBed() {
                return gAddBed;
            }

            public void setGAddBed(int gAddBed) {
                this.gAddBed = gAddBed;
            }

            public int getGHouseIsopen() {
                return gHouseIsopen;
            }

            public void setGHouseIsopen(int gHouseIsopen) {
                this.gHouseIsopen = gHouseIsopen;
            }

            public String getGBedInfo() {
                return gBedInfo;
            }

            public void setGBedInfo(String gBedInfo) {
                this.gBedInfo = gBedInfo;
            }

            public double getGDatePrice() {
                return gDatePrice;
            }

            public void setGDatePrice(double gDatePrice) {
                this.gDatePrice = gDatePrice;
            }

            public String getGHotelDateList() {
                return gHotelDateList;
            }

            public void setGHotelDateList(String gHotelDateList) {
                this.gHotelDateList = gHotelDateList;
            }

            public int getGShowNum() {
                return gShowNum;
            }

            public void setGShowNum(int gShowNum) {
                this.gShowNum = gShowNum;
            }

            public int getGShowNumShow() {
                return gShowNumShow;
            }

            public void setGShowNumShow(int gShowNumShow) {
                this.gShowNumShow = gShowNumShow;
            }

            public String getGRoomSize() {
                return gRoomSize;
            }

            public void setGRoomSize(String gRoomSize) {
                this.gRoomSize = gRoomSize;
            }

            public int getGPush() {
                return gPush;
            }

            public void setGPush(int gPush) {
                this.gPush = gPush;
            }

            public String getGExtraMessage() {
                return gExtraMessage;
            }

            public void setGExtraMessage(String gExtraMessage) {
                this.gExtraMessage = gExtraMessage;
            }

            public int getGMessageTpid() {
                return gMessageTpid;
            }

            public void setGMessageTpid(int gMessageTpid) {
                this.gMessageTpid = gMessageTpid;
            }

            public String getGRecommendGoods() {
                return gRecommendGoods;
            }

            public void setGRecommendGoods(String gRecommendGoods) {
                this.gRecommendGoods = gRecommendGoods;
            }

            public int getGIsDiscuss() {
                return gIsDiscuss;
            }

            public void setGIsDiscuss(int gIsDiscuss) {
                this.gIsDiscuss = gIsDiscuss;
            }

            public String getGDiscussInfo() {
                return gDiscussInfo;
            }

            public void setGDiscussInfo(String gDiscussInfo) {
                this.gDiscussInfo = gDiscussInfo;
            }

            public int getGDeleted() {
                return gDeleted;
            }

            public void setGDeleted(int gDeleted) {
                this.gDeleted = gDeleted;
            }

            public int getGCreateTime() {
                return gCreateTime;
            }

            public void setGCreateTime(int gCreateTime) {
                this.gCreateTime = gCreateTime;
            }

            public int getGUpdateTime() {
                return gUpdateTime;
            }

            public void setGUpdateTime(int gUpdateTime) {
                this.gUpdateTime = gUpdateTime;
            }

            public int getGSoldType() {
                return gSoldType;
            }

            public void setGSoldType(int gSoldType) {
                this.gSoldType = gSoldType;
            }

            public int getGFakeBuynum() {
                return gFakeBuynum;
            }

            public void setGFakeBuynum(int gFakeBuynum) {
                this.gFakeBuynum = gFakeBuynum;
            }

            public int getGApplayGoodsShow() {
                return gApplayGoodsShow;
            }

            public void setGApplayGoodsShow(int gApplayGoodsShow) {
                this.gApplayGoodsShow = gApplayGoodsShow;
            }

            public int getGSequenceDay() {
                return gSequenceDay;
            }

            public void setGSequenceDay(int gSequenceDay) {
                this.gSequenceDay = gSequenceDay;
            }

            public int getGSmallNum() {
                return gSmallNum;
            }

            public void setGSmallNum(int gSmallNum) {
                this.gSmallNum = gSmallNum;
            }

            public int getGSupplierId() {
                return gSupplierId;
            }

            public void setGSupplierId(int gSupplierId) {
                this.gSupplierId = gSupplierId;
            }

            public int getGJoinDiscount() {
                return gJoinDiscount;
            }

            public void setGJoinDiscount(int gJoinDiscount) {
                this.gJoinDiscount = gJoinDiscount;
            }

            public int getGHasBreakfast() {
                return gHasBreakfast;
            }

            public void setGHasBreakfast(int gHasBreakfast) {
                this.gHasBreakfast = gHasBreakfast;
            }

            public int getGIndependentMall() {
                return gIndependentMall;
            }

            public void setGIndependentMall(int gIndependentMall) {
                this.gIndependentMall = gIndependentMall;
            }

            public int getGNumberLimit() {
                return gNumberLimit;
            }

            public void setGNumberLimit(int gNumberLimit) {
                this.gNumberLimit = gNumberLimit;
            }

            public int getGShowJoinList() {
                return gShowJoinList;
            }

            public void setGShowJoinList(int gShowJoinList) {
                this.gShowJoinList = gShowJoinList;
            }

            public int getGForward() {
                return gForward;
            }

            public void setGForward(int gForward) {
                this.gForward = gForward;
            }

            public int getGShowRealNum() {
                return gShowRealNum;
            }

            public void setGShowRealNum(int gShowRealNum) {
                this.gShowRealNum = gShowRealNum;
            }

            public int getGRegionAddBy() {
                return gRegionAddBy;
            }

            public void setGRegionAddBy(int gRegionAddBy) {
                this.gRegionAddBy = gRegionAddBy;
            }

            public int getGSequenceDayShow() {
                return gSequenceDayShow;
            }

            public void setGSequenceDayShow(int gSequenceDayShow) {
                this.gSequenceDayShow = gSequenceDayShow;
            }

            public int getGSupplierExport() {
                return gSupplierExport;
            }

            public void setGSupplierExport(int gSupplierExport) {
                this.gSupplierExport = gSupplierExport;
            }

            public int getGOsId() {
                return gOsId;
            }

            public void setGOsId(int gOsId) {
                this.gOsId = gOsId;
            }

            public String getGVerifyRemark() {
                return gVerifyRemark;
            }

            public void setGVerifyRemark(String gVerifyRemark) {
                this.gVerifyRemark = gVerifyRemark;
            }

            public int getGVerifyApplyTime() {
                return gVerifyApplyTime;
            }

            public void setGVerifyApplyTime(int gVerifyApplyTime) {
                this.gVerifyApplyTime = gVerifyApplyTime;
            }

            public int getGLeastBuy() {
                return gLeastBuy;
            }

            public void setGLeastBuy(int gLeastBuy) {
                this.gLeastBuy = gLeastBuy;
            }

            public String getGH5Qrcode() {
                return gH5Qrcode;
            }

            public void setGH5Qrcode(String gH5Qrcode) {
                this.gH5Qrcode = gH5Qrcode;
            }
        }
    }
}
