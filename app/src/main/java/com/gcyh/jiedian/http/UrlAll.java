package com.gcyh.jiedian.http;

/**
 * 网络请求
 *
 * @author 蔡志广
 * @date: 2018/4/16
 * @time: 16:33
 */
public class UrlAll {


    //  http://192.168.168.120:8080/gcyh_app/api/pri/login/sendSMS


    public static final String LOCATION_SERVICE_ADDRESS = "http://39.104.173.169:8080/gcyh_app/api/pri/";
//    public static final String LOCATION_SERVICE_ADDRESS = "http://192.168.1.113:8080/gcyh_app/api/pri/";
    public static final String DOWN_LOAD_IMAGE = "http://gcyhnodelibrary.oss-cn-beijing.aliyuncs.com/";

    /**
     * 获取短信验证码
     */
    public static final String SendCode = "login/sendSMS";

    /**
     * 注册
     */
    public static final String Register = "login/userRegister";

    /**
     * 登录
     */
    public static final String Login = "login/userLogin";
    /**
     * 默认登录
     */
    public static final String DefaultLogin = "login/defaultLogin";
    /**
     * 忘记密码
     */
    public static final String Forget_Password = "login/ResetPassword";

    /**
     * 用户详情
     */
    public static final String My = "login/detailUserInfo";
    /**
     * 签到
     */
    public static final String MySign = "login/signIn";

    /**
     * 节点库列表
     */
    public static final String Library_Note_List = "data/getNodeDataList";
    /**
     * 项目库列表
     */
    public static final String Library_Project_List = "data/getProjectDataList";
    /**
     * 常用资料库列表
     */
    public static final String Library_Data_List = "data/getCommonDataList";
    /**
     * 常用资料库详情
     */
    public static final String Library_Data_List_Details = "data/getCommonDataDetail";

    /**
     * 添加购物车
     */
    public static final String Add_Shop_Car = "UserDeal/addShopCart";

    /**
     * 购物车列表
     */
    public static final String Shop_Car_List = "UserDeal/getShopCartList";

    /**
     * 删除购物车
     */
    public static final String Delete_Shop_Car = "UserDeal/delShopCartById";

    /**
     * 收藏列表
     */
    public static final String Collect_List = "data/getUserCollection";

    /**
     * 添加收藏
     */
    public static final String Add_Collect = "data/addUserCollection";

    /**
     * 移除收藏
     */
    public static final String Delete_Collect = "data/delUserCollection";

    /**
     * 关注
     */
    public static final String Notice = "login/userFollow";

    /**
     * 取消关注
     */
    public static final String Delete_Notice = "login/delUserFollow";

    /*
    * 粉丝列表
    * */
    public static final String Fan_List = "login/getUserFans";
    /*
   * 关注列表
   * */
    public static final String Notice_List = "login/getUserFollow";

    /*
   * 意见反馈
   * */
    public static final String Feed_Back = "login/userFeedback";

    /*
  * 发帖
  * */
    public static final String Post_Message = "postManage/userSendPost";

    /*
  * 发帖列表
   * */
    public static final String Post_Message_List = "postManage/getSendPostList";

    /*
  * 点赞
   * */
    public static final String Zan = "postManage/addFabulous";

    /*
  * 取消点赞
   * */
    public static final String Delete_Zan = "postManage/delFabulous";

    /*
    * 发现列表
    * */
    public static final String Find_List = "postManage/getAllSendPost";

    /*
    * 其他人主题---点击发现头像
    * */
    public static final String Other_find_List = "postManage/getSendPostListByUser";

    /*
   * 我的主题
   * */
    public static final String My_Theme_List = "postManage/getSendPostList";

    /*
   * 上传个人信息
    * */
    public static final String My_Information = "login/updateUserInfo";



}
