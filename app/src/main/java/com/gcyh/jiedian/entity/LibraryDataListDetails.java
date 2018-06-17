package com.gcyh.jiedian.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/6/8.
 */

public class LibraryDataListDetails {


    /**
     * responseParams : [{"chapter":{"commonDataId":1,"contentsId":1,"id":1,"name":"詹姆斯成终结者"},"contentsList":[{"chapterId":1,"content":"詹姆斯暴砍50","contentOrder":1,"id":1,"type":1},{"chapterId":1,"content":"骑士勇士第三场","contentOrder":2,"id":2,"type":2},{"chapterId":1,"content":"詹姆斯拼到底也没赢，队友是真垃圾，我跟詹姆斯都能拿总冠军","contentOrder":3,"id":3,"type":3}]},{"chapter":{"commonDataId":1,"contentsId":2,"id":2,"name":"科比一战封神"},"contentsList":[{"chapterId":2,"content":"科比退役","contentOrder":1,"id":4,"type":1},{"chapterId":2,"content":"科比退役狂砍60","contentOrder":2,"id":5,"type":2},{"chapterId":2,"content":"从此再无科比，退役站最高得分","contentOrder":3,"id":6,"type":3},{"chapterId":2,"content":"jd_kf_01","contentOrder":4,"id":7,"type":6}]}]
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
         * chapter : {"commonDataId":1,"contentsId":1,"id":1,"name":"詹姆斯成终结者"}
         * contentsList : [{"chapterId":1,"content":"詹姆斯暴砍50","contentOrder":1,"id":1,"type":1},{"chapterId":1,"content":"骑士勇士第三场","contentOrder":2,"id":2,"type":2},{"chapterId":1,"content":"詹姆斯拼到底也没赢，队友是真垃圾，我跟詹姆斯都能拿总冠军","contentOrder":3,"id":3,"type":3}]
         */

        private ChapterBean chapter;
        private List<ContentsListBean> contentsList;

        public ChapterBean getChapter() {
            return chapter;
        }

        public void setChapter(ChapterBean chapter) {
            this.chapter = chapter;
        }

        public List<ContentsListBean> getContentsList() {
            return contentsList;
        }

        public void setContentsList(List<ContentsListBean> contentsList) {
            this.contentsList = contentsList;
        }

        public static class ChapterBean {
            /**
             * commonDataId : 1
             * contentsId : 1
             * id : 1
             * name : 詹姆斯成终结者
             */

            private int commonDataId;
            private int contentsId;
            private int id;
            private String name;

            public int getCommonDataId() {
                return commonDataId;
            }

            public void setCommonDataId(int commonDataId) {
                this.commonDataId = commonDataId;
            }

            public int getContentsId() {
                return contentsId;
            }

            public void setContentsId(int contentsId) {
                this.contentsId = contentsId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class ContentsListBean {
            /**
             * chapterId : 1
             * content : 詹姆斯暴砍50
             * contentOrder : 1
             * id : 1
             * type : 1
             */

            private int chapterId;
            private String content;
            private int contentOrder;
            private int id;
            private int type;

            public int getChapterId() {
                return chapterId;
            }

            public void setChapterId(int chapterId) {
                this.chapterId = chapterId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getContentOrder() {
                return contentOrder;
            }

            public void setContentOrder(int contentOrder) {
                this.contentOrder = contentOrder;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
