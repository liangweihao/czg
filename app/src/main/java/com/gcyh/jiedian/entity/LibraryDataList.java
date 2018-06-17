package com.gcyh.jiedian.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/6/8.
 */

public class LibraryDataList {


    /**
     * responseParams : [{"collection":0,"database":{"createTime":"2018-06-07 14:51:48","data":"","dataDetail":"施工图设计规范均按照国家规范进行","id":1,"image":"","materialConsumption":"","standard":"","symbolCode":"","title":"建筑设计图例代号","titleEnglish":"Beijing holiday hotel sample houses"}}]
     * rntCode : OK
     * rntCodeValue : 1
     * rntMsg : 成功
     */

    private String rntCode;
    private int rntCodeValue;
    private String rntMsg;
    private List<ResponseParamsBean> responseParams;

    public String getRntCode() {
        return rntCode;
    }

    public void setRntCode(String rntCode) {
        this.rntCode = rntCode;
    }

    public int getRntCodeValue() {
        return rntCodeValue;
    }

    public void setRntCodeValue(int rntCodeValue) {
        this.rntCodeValue = rntCodeValue;
    }

    public String getRntMsg() {
        return rntMsg;
    }

    public void setRntMsg(String rntMsg) {
        this.rntMsg = rntMsg;
    }

    public List<ResponseParamsBean> getResponseParams() {
        return responseParams;
    }

    public void setResponseParams(List<ResponseParamsBean> responseParams) {
        this.responseParams = responseParams;
    }

    public static class ResponseParamsBean {

        /**
         * collection : 0
         * database : {"createTime":"2018-06-07 14:51:48","data":"","dataDetail":"施工图设计规范均按照国家规范进行","id":1,"image":"","materialConsumption":"","standard":"","symbolCode":"","title":"建筑设计图例代号","titleEnglish":"Beijing holiday hotel sample houses"}
         */

        private int collection;
        private DatabaseBean database;

        public int getCollection() {
            return collection;
        }

        public void setCollection(int collection) {
            this.collection = collection;
        }

        public DatabaseBean getDatabase() {
            return database;
        }

        public void setDatabase(DatabaseBean database) {
            this.database = database;
        }

        public static class DatabaseBean {
            /**
             * createTime : 2018-06-07 14:51:48
             * data :
             * dataDetail : 施工图设计规范均按照国家规范进行
             * id : 1
             * image :
             * materialConsumption :
             * standard :
             * symbolCode :
             * title : 建筑设计图例代号
             * titleEnglish : Beijing holiday hotel sample houses
             */

            private String createTime;
            private String data;
            private String dataDetail;
            private int id;
            private String image;
            private String materialConsumption;
            private String standard;
            private String symbolCode;
            private String title;
            private String titleEnglish;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }

            public String getDataDetail() {
                return dataDetail;
            }

            public void setDataDetail(String dataDetail) {
                this.dataDetail = dataDetail;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getMaterialConsumption() {
                return materialConsumption;
            }

            public void setMaterialConsumption(String materialConsumption) {
                this.materialConsumption = materialConsumption;
            }

            public String getStandard() {
                return standard;
            }

            public void setStandard(String standard) {
                this.standard = standard;
            }

            public String getSymbolCode() {
                return symbolCode;
            }

            public void setSymbolCode(String symbolCode) {
                this.symbolCode = symbolCode;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTitleEnglish() {
                return titleEnglish;
            }

            public void setTitleEnglish(String titleEnglish) {
                this.titleEnglish = titleEnglish;
            }
        }
    }
}
