package com.gcyh.jiedian.http;

import com.gcyh.jiedian.entity.AddCollect;
import com.gcyh.jiedian.entity.AddShopCar;
import com.gcyh.jiedian.entity.CollectList;
import com.gcyh.jiedian.entity.DefaultLogin;
import com.gcyh.jiedian.entity.DeleteCollect;
import com.gcyh.jiedian.entity.DeleteNotice;
import com.gcyh.jiedian.entity.DeleteZan;
import com.gcyh.jiedian.entity.DeltetShopCar;
import com.gcyh.jiedian.entity.FanList;
import com.gcyh.jiedian.entity.FeedBack;
import com.gcyh.jiedian.entity.FindList;
import com.gcyh.jiedian.entity.ForgetPassword;
import com.gcyh.jiedian.entity.LibraryDataList;
import com.gcyh.jiedian.entity.LibraryDataListDetails;
import com.gcyh.jiedian.entity.LibraryNoteList;
import com.gcyh.jiedian.entity.LibraryProjectList;
import com.gcyh.jiedian.entity.Login;
import com.gcyh.jiedian.entity.My;
import com.gcyh.jiedian.entity.MyInformation;
import com.gcyh.jiedian.entity.MySign;
import com.gcyh.jiedian.entity.MyThemeList;
import com.gcyh.jiedian.entity.Notice;
import com.gcyh.jiedian.entity.NoticeList;
import com.gcyh.jiedian.entity.OtherFindList;
import com.gcyh.jiedian.entity.PostMessage;
import com.gcyh.jiedian.entity.PostMessageList;
import com.gcyh.jiedian.entity.Register;
import com.gcyh.jiedian.entity.SendCode;
import com.gcyh.jiedian.entity.ShopCarList;
import com.gcyh.jiedian.entity.Zan;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 网络请求
 *
 * @author 蔡志广
 * @date: 2018/4/16
 * @time: 18:33
 */

public interface ApiService {

    //   @Multipart  // 传文件时需要

    /*
    短信验证码
    * */
    @POST(UrlAll.SendCode)
    Observable<SendCode> sendcode(@Query("phone") String phone, @Query("type") int type);

    /*
    注册
    * */
    @FormUrlEncoded
    @POST(UrlAll.Register)
    Observable<Register> register(@Field("nameNick") String nameNick, @Field("occupation") String occupation, @Query("phone") String phone, @Field("SMSCode") String SMSCode, @Field("password") String password);

    /*
    登录
    * */
    @POST(UrlAll.Login)
    Observable<Login> login(@Query("phone") String phone, @Query("password") String password);

    /*
  登录
  * */
    @POST(UrlAll.DefaultLogin)
    Observable<DefaultLogin> defaultlogin(@Query("token") String token);

    /*
      我的页面--我的信息
   * */
    @POST(UrlAll.My)
    Observable<My> my(@Query("token") String token);

    /*
   签到
   * */
    @POST(UrlAll.MySign)
    Observable<MySign> mysign(@Query("token") String token);

    /*
   库--节点库列表
   * */
    @FormUrlEncoded
    @POST(UrlAll.Library_Note_List)
    Observable<LibraryNoteList> librarynotelist(@Query("token") String token,@Query("pages") int pagers , @Query("row") int row , @Field("material") String material, @Field("space") String space, @Field("position") String position, @Field("isNew") String isNew, @Field("isHot") String isHot);

    /*
    忘记密码
   * */
    @POST(UrlAll.Forget_Password)
    Observable<ForgetPassword> forgetpassword(@Query("phone") String phone, @Query("newPwd") String newPwd, @Query("SMSCode") String sMSCode);


    /*
     库--项目库列表
   * */
    @POST(UrlAll.Library_Project_List)
    Observable<LibraryProjectList> libraryprojectlist(@Query("token") String token,@Query("pages") int pages , @Query("row") int row );

    /*
    库--常用资料库列表
  * */
    @FormUrlEncoded
    @POST(UrlAll.Library_Data_List)
    Observable<LibraryDataList> librarydatalist(@Query("token") String token,@Query("pages") int pages , @Query("row") int row , @Field("standard") String standard, @Field("data") String data, @Field("materialConsumption") String materialConsumption, @Field("symbolCode") String symbolCode);

    /*
    库--项目库列表
  * */
    @POST(UrlAll.Library_Data_List_Details)
    Observable<LibraryDataListDetails> librarydatalistdetails(@Query("commonDataId") int commonDataId);

    /*
  添加购物车
* */
    @POST(UrlAll.Add_Shop_Car)
    Observable<AddShopCar> addshopcar(@Query("token") String token, @Query("id") int id, @Query("type") int type);

    /*
   购物车列表
  * */
    @POST(UrlAll.Shop_Car_List)
    Observable<ShopCarList> shopcarlist(@Query("token") String token);

    /*
   删除购物车
   * */
    @POST(UrlAll.Delete_Shop_Car)
    Observable<DeltetShopCar> deleteshopcar(@Query("token") String token, @Query("id") int id, @Query("type") int type);

    /*
   收藏列表
   * */
    @POST(UrlAll.Collect_List)
    Observable<CollectList> collectlist(@Query("token") String token);

    /*
    添加收藏
  * */
    @POST(UrlAll.Add_Collect)
    Observable<AddCollect> addcollect(@Query("token") String token, @Query("type") int type, @Query("id") int id);

    /*
   移除收藏
   * */
    @POST(UrlAll.Delete_Collect)
    Observable<DeleteCollect> deletecollect(@Query("token") String token, @Query("type") int type, @Query("id") int id);

    /*
   关注
 * */
    @POST(UrlAll.Notice)
    Observable<Notice> notice(@Query("token") String token, @Query("code") String code);

    /*
   取消关注
   * */
    @POST(UrlAll.Delete_Notice)
    Observable<DeleteNotice> deletenotice(@Query("token") String token, @Query("code") String code);

    /*
    粉丝列表
    * */
    @POST(UrlAll.Fan_List)
    Observable<FanList> fanlist(@Query("token") String token);

    /*
   关注列表
   * */
    @POST(UrlAll.Notice_List)
    Observable<NoticeList> noticelist(@Query("token") String token);

    /*
    意见反馈
    * */
    @FormUrlEncoded
    @POST(UrlAll.Feed_Back)
    Observable<FeedBack> feedback(@Query("type") String type, @Field("content") String content, @Field("imgs") String imgs, @Query("phone") String phone);

    /*
     发帖
     * */
    @FormUrlEncoded
    @POST(UrlAll.Post_Message)
    Observable<PostMessage> postmessage(@Query("token") String token, @Field("imgs") String imgs, @Field("content") String content);

    /*
    发帖列表
    * */
    @POST(UrlAll.Post_Message_List)
    Observable<PostMessageList> postmessagelist(@Query("token") String token);

    /*
   点赞
   * */
    @POST(UrlAll.Zan)
    Observable<Zan> zan(@Query("token") String token, @Query("postId") String postId);

    /*
   取消赞
   * */
    @POST(UrlAll.Delete_Zan)
    Observable<DeleteZan> deletezan(@Query("token") String token, @Query("postId") String postId);

    /*
    发现列表
   * */
    @POST(UrlAll.Find_List)
    Observable<FindList> findlist(@Query("token") String token,@Query("pages") int pages , @Query("row") int row);

     /*
    * 其他人主题---点击发现头像
    * */
     @POST(UrlAll.Other_find_List)
     Observable<OtherFindList> otherfindlist(@Query("token") String token ,@Query("userCode") String userCode);

    /*
  * 我的主题
  * */
    @POST(UrlAll.My_Theme_List)
    Observable<MyThemeList> mythemelist(@Query("token") String token , @Query("pages") int pages , @Query("row") int row);

    /*
   * 上传个人信息
   * */
    @FormUrlEncoded
    @POST(UrlAll.My_Information)
    Observable<MyInformation> myinformation(@Query("token") String token , @Query("photo") String photo , @Field("nameNick") String nameNick, @Field("province") String province , @Field("city") String city, @Query("email") String email , @Query("remark1") String remark1, @Query("backImg") String backImg);


}
