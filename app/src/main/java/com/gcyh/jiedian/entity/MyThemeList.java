package com.gcyh.jiedian.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/6/15.
 */

public class MyThemeList {


    /**
     * responseParams : {"userInfoDTO":{"AVIP":0,"BVIP":0,"SVIP":0,"concerns":0,"exp":0,"fans":0,"grade":1,"isSign":0,"shoppingCart":0,"star":0,"userCollection":0,"userInfo":{"backImg":"","balance":0,"city":"天津市","email":"","id":23,"nameNick":"那些年","occupation":"设计师","password":"E10ADC3949BA59ABBE56E057F20F883E","phone":"15910361753","photo":"天津市","province":"天津市","qq":"","remark1":3,"remark2":"","remark3":"","userCode":"8","weixin":""}},"userPostFabulousDTOtList":[{"isFabulous":1,"userpost":{"commentNumber":0,"createDate":"2018-06-14 00:00:00","id":10,"image":"175315289780823691,175315289780823692","likeNumber":0,"material":"","position":"","postDetail":"世界你好呀，美女","space":"","type":"","userCode":"8"}}]}
     * rntCode : OK
     * rntCodeValue : 1
     * rntMsg : 成功
     */

    private ResponseParamsBean responseParams;
    private String rntCode;
    private int rntCodeValue;
    private String rntMsg;

    public ResponseParamsBean getResponseParams() {
        return responseParams;
    }

    public void setResponseParams(ResponseParamsBean responseParams) {
        this.responseParams = responseParams;
    }

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

    public static class ResponseParamsBean {
        /**
         * userInfoDTO : {"AVIP":0,"BVIP":0,"SVIP":0,"concerns":0,"exp":0,"fans":0,"grade":1,"isSign":0,"shoppingCart":0,"star":0,"userCollection":0,"userInfo":{"backImg":"","balance":0,"city":"天津市","email":"","id":23,"nameNick":"那些年","occupation":"设计师","password":"E10ADC3949BA59ABBE56E057F20F883E","phone":"15910361753","photo":"天津市","province":"天津市","qq":"","remark1":3,"remark2":"","remark3":"","userCode":"8","weixin":""}}
         * userPostFabulousDTOtList : [{"isFabulous":1,"userpost":{"commentNumber":0,"createDate":"2018-06-14 00:00:00","id":10,"image":"175315289780823691,175315289780823692","likeNumber":0,"material":"","position":"","postDetail":"世界你好呀，美女","space":"","type":"","userCode":"8"}}]
         */

        private UserInfoDTOBean userInfoDTO;
        private List<UserPostFabulousDTOtListBean> userPostFabulousDTOtList;

        public UserInfoDTOBean getUserInfoDTO() {
            return userInfoDTO;
        }

        public void setUserInfoDTO(UserInfoDTOBean userInfoDTO) {
            this.userInfoDTO = userInfoDTO;
        }

        public List<UserPostFabulousDTOtListBean> getUserPostFabulousDTOtList() {
            return userPostFabulousDTOtList;
        }

        public void setUserPostFabulousDTOtList(List<UserPostFabulousDTOtListBean> userPostFabulousDTOtList) {
            this.userPostFabulousDTOtList = userPostFabulousDTOtList;
        }

        public static class UserInfoDTOBean {
            /**
             * AVIP : 0
             * BVIP : 0
             * SVIP : 0
             * concerns : 0
             * exp : 0
             * fans : 0
             * grade : 1
             * isSign : 0
             * shoppingCart : 0
             * star : 0
             * userCollection : 0
             * userInfo : {"backImg":"","balance":0,"city":"天津市","email":"","id":23,"nameNick":"那些年","occupation":"设计师","password":"E10ADC3949BA59ABBE56E057F20F883E","phone":"15910361753","photo":"天津市","province":"天津市","qq":"","remark1":3,"remark2":"","remark3":"","userCode":"8","weixin":""}
             */

            private int AVIP;
            private int BVIP;
            private int SVIP;
            private int concerns;
            private int exp;
            private int fans;
            private int grade;
            private int isSign;
            private int shoppingCart;
            private int star;
            private int userCollection;
            private UserInfoBean userInfo;

            public int getAVIP() {
                return AVIP;
            }

            public void setAVIP(int AVIP) {
                this.AVIP = AVIP;
            }

            public int getBVIP() {
                return BVIP;
            }

            public void setBVIP(int BVIP) {
                this.BVIP = BVIP;
            }

            public int getSVIP() {
                return SVIP;
            }

            public void setSVIP(int SVIP) {
                this.SVIP = SVIP;
            }

            public int getConcerns() {
                return concerns;
            }

            public void setConcerns(int concerns) {
                this.concerns = concerns;
            }

            public int getExp() {
                return exp;
            }

            public void setExp(int exp) {
                this.exp = exp;
            }

            public int getFans() {
                return fans;
            }

            public void setFans(int fans) {
                this.fans = fans;
            }

            public int getGrade() {
                return grade;
            }

            public void setGrade(int grade) {
                this.grade = grade;
            }

            public int getIsSign() {
                return isSign;
            }

            public void setIsSign(int isSign) {
                this.isSign = isSign;
            }

            public int getShoppingCart() {
                return shoppingCart;
            }

            public void setShoppingCart(int shoppingCart) {
                this.shoppingCart = shoppingCart;
            }

            public int getStar() {
                return star;
            }

            public void setStar(int star) {
                this.star = star;
            }

            public int getUserCollection() {
                return userCollection;
            }

            public void setUserCollection(int userCollection) {
                this.userCollection = userCollection;
            }

            public UserInfoBean getUserInfo() {
                return userInfo;
            }

            public void setUserInfo(UserInfoBean userInfo) {
                this.userInfo = userInfo;
            }

            public static class UserInfoBean {
                /**
                 * backImg :
                 * balance : 0
                 * city : 天津市
                 * email :
                 * id : 23
                 * nameNick : 那些年
                 * occupation : 设计师
                 * password : E10ADC3949BA59ABBE56E057F20F883E
                 * phone : 15910361753
                 * photo : 天津市
                 * province : 天津市
                 * qq :
                 * remark1 : 3
                 * remark2 :
                 * remark3 :
                 * userCode : 8
                 * weixin :
                 */

                private String backImg;
                private int balance;
                private String city;
                private String email;
                private int id;
                private String nameNick;
                private String occupation;
                private String password;
                private String phone;
                private String photo;
                private String province;
                private String qq;
                private int remark1;
                private String remark2;
                private String remark3;
                private String userCode;
                private String weixin;

                public String getBackImg() {
                    return backImg;
                }

                public void setBackImg(String backImg) {
                    this.backImg = backImg;
                }

                public int getBalance() {
                    return balance;
                }

                public void setBalance(int balance) {
                    this.balance = balance;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getNameNick() {
                    return nameNick;
                }

                public void setNameNick(String nameNick) {
                    this.nameNick = nameNick;
                }

                public String getOccupation() {
                    return occupation;
                }

                public void setOccupation(String occupation) {
                    this.occupation = occupation;
                }

                public String getPassword() {
                    return password;
                }

                public void setPassword(String password) {
                    this.password = password;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getPhoto() {
                    return photo;
                }

                public void setPhoto(String photo) {
                    this.photo = photo;
                }

                public String getProvince() {
                    return province;
                }

                public void setProvince(String province) {
                    this.province = province;
                }

                public String getQq() {
                    return qq;
                }

                public void setQq(String qq) {
                    this.qq = qq;
                }

                public int getRemark1() {
                    return remark1;
                }

                public void setRemark1(int remark1) {
                    this.remark1 = remark1;
                }

                public String getRemark2() {
                    return remark2;
                }

                public void setRemark2(String remark2) {
                    this.remark2 = remark2;
                }

                public String getRemark3() {
                    return remark3;
                }

                public void setRemark3(String remark3) {
                    this.remark3 = remark3;
                }

                public String getUserCode() {
                    return userCode;
                }

                public void setUserCode(String userCode) {
                    this.userCode = userCode;
                }

                public String getWeixin() {
                    return weixin;
                }

                public void setWeixin(String weixin) {
                    this.weixin = weixin;
                }
            }
        }

        public static class UserPostFabulousDTOtListBean {
            /**
             * isFabulous : 1
             * userpost : {"commentNumber":0,"createDate":"2018-06-14 00:00:00","id":10,"image":"175315289780823691,175315289780823692","likeNumber":0,"material":"","position":"","postDetail":"世界你好呀，美女","space":"","type":"","userCode":"8"}
             */

            private int isFabulous;
            private UserpostBean userpost;

            public int getIsFabulous() {
                return isFabulous;
            }

            public void setIsFabulous(int isFabulous) {
                this.isFabulous = isFabulous;
            }

            public UserpostBean getUserpost() {
                return userpost;
            }

            public void setUserpost(UserpostBean userpost) {
                this.userpost = userpost;
            }

            public static class UserpostBean {
                /**
                 * commentNumber : 0
                 * createDate : 2018-06-14 00:00:00
                 * id : 10
                 * image : 175315289780823691,175315289780823692
                 * likeNumber : 0
                 * material :
                 * position :
                 * postDetail : 世界你好呀，美女
                 * space :
                 * type :
                 * userCode : 8
                 */

                private int commentNumber;
                private String createDate;
                private int id;
                private String image;
                private int likeNumber;
                private String material;
                private String position;
                private String postDetail;
                private String space;
                private String type;
                private String userCode;

                public int getCommentNumber() {
                    return commentNumber;
                }

                public void setCommentNumber(int commentNumber) {
                    this.commentNumber = commentNumber;
                }

                public String getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(String createDate) {
                    this.createDate = createDate;
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

                public int getLikeNumber() {
                    return likeNumber;
                }

                public void setLikeNumber(int likeNumber) {
                    this.likeNumber = likeNumber;
                }

                public String getMaterial() {
                    return material;
                }

                public void setMaterial(String material) {
                    this.material = material;
                }

                public String getPosition() {
                    return position;
                }

                public void setPosition(String position) {
                    this.position = position;
                }

                public String getPostDetail() {
                    return postDetail;
                }

                public void setPostDetail(String postDetail) {
                    this.postDetail = postDetail;
                }

                public String getSpace() {
                    return space;
                }

                public void setSpace(String space) {
                    this.space = space;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getUserCode() {
                    return userCode;
                }

                public void setUserCode(String userCode) {
                    this.userCode = userCode;
                }
            }
        }
    }
}
