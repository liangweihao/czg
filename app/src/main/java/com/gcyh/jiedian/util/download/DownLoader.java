package com.gcyh.jiedian.util.download;

import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by caizhiguang on 2018/4/10.
 * 下载
 */

public class DownLoader extends AsyncTask<String, Void, Void> {

    private String mFileName;//文件名

    private DownLoaderListener mDownLoaderListener;

    public DownLoader(String filePath, DownLoaderListener listener) {
        this.mFileName = filePath;
        this.mDownLoaderListener = listener;
    }

    /**
     * 存在就不下载
     * @param url
     * @return
     */
    public String downLoad(String url) {

        File file = new File(mFileName);//如果文件存在就不在下载
        if (!file.exists()){
            execute(url);
        }

        return mFileName;
    }

/*    *//**//**
     * 更新下载,存在就覆盖
     * @return
     *//**//*
    public String updateDownLoad(String pic_url) {
        StringBuffer sb = new StringBuffer(filePath);
        sb.append(mContext.getPackageName());
        sb.append("/cache").append("/giftGif/").append(fileName).append(".gif");
        execute(pic_url);
        return sb.toString();
    }*/

    @Override
    protected Void doInBackground(String... params) {
        String resultPath = "";
        String urlPath = params[0];
        InputStream is = null;
        FileOutputStream os = null;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            is = urlConn.getInputStream();

            os = new FileOutputStream(createFile());
            byte buffer[] = new byte[1024*4];
            int temp = 0;
            while((temp = is.read(buffer)) != -1){
                os.write(buffer, 0, temp);
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null){
                    is.close();
                }
                if (os != null){
                    os.close();
                }
            } catch (IOException e) {
//                LogUtil.error(DownLoader.class, e.getMessage());
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (mDownLoaderListener != null){
            mDownLoaderListener.onDownloadComplete(mFileName);
        }
    }

    private File createFile() {
        File gifFile = new File(mFileName);
        if (gifFile.exists()) {
            gifFile.delete();
        }

        return gifFile;
    }

    public interface DownLoaderListener{
        void onDownloadComplete(String path);
    }
}
