package com.gcyh.jiedian.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Caizhiguang on 2018/5/21.
 */

public class HttpConnectLtp extends Thread {

    public String api_key;
    public String pattern;
    public String format;
    public String text = "";
    public String result = "";
    public ArrayList<InfoBean> infoBeans;

    public HttpConnectLtp(String txt, String pat) {
        api_key = "U1o8a791w85xwoqFcmTeRpPqWYtnHBVQQkIitIhE";//api_key,申请账号后生成，免费申请。
        pattern = URLEncoder.encode(pat);//ws表示只分词，除此还有pos词性标注、ner命名实体识别、dp依存句法分词、srl语义角色标注、all全部
        format = "json";//指定结果格式类型，plain表示简洁文本格式
        text = URLEncoder.encode(txt);
    }

    public class InfoBean {
        public String cont;
        int id;

        public void setCont(String cont) {
            this.cont = cont;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    @Override
    public void run() {
        URL url;
        try {
            String path = "https://api.ltp-cloud.com/analysis/?"
                    + "api_key=" + api_key + "&"
                    + "text=" + text + "&"
                    + "format=" + format + "&"
                    + "pattern=" + pattern;
            url = new URL(path);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (conn.getResponseCode() == 200) {
                result = changeInputStream(conn.getInputStream());

                JSONArray jsonArray = new JSONArray(result);
                JSONArray jsonArray1 = jsonArray.getJSONArray(0).getJSONArray(0);
                infoBeans = new ArrayList<>();
                for (int i = 0; i < jsonArray1.length(); i++) {
                    JSONObject jsonObject = jsonArray1.getJSONObject(i);
                    InfoBean infoBean = new InfoBean();
                    String cont = jsonObject.getString("cont");
                    int id = jsonObject.getInt("id");
                    infoBean.setCont(cont);
                    infoBean.setId(id);
                    infoBeans.add(infoBean);
                }
                Log.i("===", "onViewClicked:==result== " + infoBeans.size()+"&&&&&&"+infoBeans.get(0).cont+"******"+infoBeans.get(1).cont);

            } else {
                Log.i(TAG, "run: code = " + conn.getResponseCode());
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    /**
     * 将一个输入流转换成指定编码的字符串
     *
     * @param inputStream
     * @param
     *
     * @return
     */
    private static String changeInputStream(InputStream inputStream) {

        // 内存流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = null;
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    byteArrayOutputStream.write(data, 0, len);
                }
                result = new String(byteArrayOutputStream.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
