package com.gcyh.jiedian.util.download;


import com.gcyh.jiedian.app.App;

import java.io.File;

/**
 * Created by caizhiguang on 2018/4/10.
 * 下载
 */

public class DownLoadManager {

    public String filePath;//文件目录

    public static DownLoadManager mInstance;

    public static DownLoadManager getInstance(){
        if (mInstance == null){
            mInstance = new DownLoadManager();
        }
        return mInstance;
    }

    public DownLoadManager() {
        init();
    }

    public void init() {
        createFile();//初始化判断文件目录是否存在,不存在创建
    }

    private void createFile() {
        String rootPath = App.getInstance().getFilesDir().getPath()+"/";
        StringBuffer sb = new StringBuffer(rootPath);
        sb.append("/down/voice/");

        // 判断文件夹是否存在
        File picFile = new File(sb.toString());
        if (!picFile.exists()) {
            picFile.mkdirs();
        }

        filePath = sb.toString();
    }

    private void deleteFile(File file) {
        if (file.isFile()){
            file.delete();
            return;
        }
        if (file.isDirectory()){
            File[] childFiles = file.listFiles();

            if (childFiles == null || childFiles.length == 0){
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                deleteFile(childFiles[i]);
            }
            file.delete();
        }
    }

    //下载文件
    public String download(String downUrl, String giftName, DownLoader.DownLoaderListener listener) {
        StringBuffer sb = new StringBuffer(filePath);
        sb.append(giftName);
        DownLoader downLoader = new DownLoader(sb.toString(), listener);
        return downLoader.downLoad(downUrl);
    }
}
