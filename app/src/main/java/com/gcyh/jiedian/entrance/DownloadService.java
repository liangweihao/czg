package com.gcyh.jiedian.entrance;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.gcyh.jiedian.entity.LibraryNoteList;
import com.gcyh.jiedian.http.UrlAll;
import com.gcyh.jiedian.library.fragment.notelibrary.NoteLibraryFragment;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;
import com.gcyh.jiedian.util.SPUtil;
import com.othershe.dutil.DUtil;
import com.othershe.dutil.callback.SimpleDownloadCallback;
import com.othershe.dutil.download.DownloadManger;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Caizhiguang on 2018/6/7.
 */

public class DownloadService extends Service {

    private Context mContext;
    private DownloadBinder mBinder = new DownloadBinder();
    private List<LibraryNoteList.ResponseParamsBean> list;

    class DownloadBinder extends Binder {
                                  //文件路径           文件名称                   判断是否下载的参数          条目的位置
        public void startDownload(String path, final String name, String url, final String notifyId, final int position) {
            DUtil.init(mContext)
                    .path(path)
                    .name(name)
                    .url(url)
                    .childTaskCount(2)
                    .build()
                    .start(new SimpleDownloadCallback() {
                        @Override
                        public void onStart(long currentSize, long totalSize, float progress) {
                            //把数据添加到下载管理---正在下载
                            List<Map<String, String>> downloading = SPUtil.getInfo(mContext , "downloading");
                            Map<String , String> map = new HashMap<String, String>() ;
                            map.put("imageUrl" , list.get(position).getNodeData().getImageName()) ;
                            map.put("title" , list.get(position).getNodeData().getTitle()) ;
                            map.put("mb" , doubleToString(totalSize)) ;
                            map.put("id" , list.get(position).getNodeData().getId()+"") ;
                            downloading.add(0 , map);
                            SPUtil.saveInfo(mContext , "downloading" , downloading);

                        }

                        @Override
                        public void onProgress(long currentSize, long totalSize, float progress) {
                            Bundle bundle = new Bundle() ;
                            bundle.putInt("progress" , (int) progress) ;
                            bundle.putInt("position" , position);
                            bundle.putInt("id" , list.get(position).getNodeData().getId());
                            EventBusUtil.postEvent(EventBusCode.LIBRARY_UPDATE_PROCESS , bundle);
                            EventBusUtil.postEvent(EventBusCode.LIBRARY_UPDATE_PROCESS_DOWNLOADING , bundle);

                        }

                        @Override
                        public void onFinish(File file) {
                            //把数据添加到下载管理---已下载
                            double length = (double)file.length() / 1024 / 1024;
                            List<Map<String, String>> finish_download = SPUtil.getInfo(mContext , "finish_download");
                            Map<String , String> map = new HashMap<String, String>() ;
                            map.put("imageUrl" , list.get(position).getNodeData().getImageName()) ;
                            map.put("title" , list.get(position).getNodeData().getTitle()) ;
                            map.put("mb" , doubleToString(file.length())) ;
                            map.put("time" , setTime()) ;
                            finish_download.add(0 , map);
                            SPUtil.saveInfo(mContext , "finish_download" , finish_download);
                            //标记下载完成
                            SPUtil.setString(mContext , notifyId , notifyId);
                            //把数据移除下载管理---移除正在下载
                            List<Map<String, String>> downloading = SPUtil.getInfo(mContext , "downloading");
                            for (int i=0 ; i<downloading.size() ; i++){
                                Map<String, String> mapItem = downloading.get(i);
                                String id = mapItem.get("id");
                                if (id.equals(list.get(position).getNodeData().getId()+"")){
                                    downloading.remove(i) ;
                                }
                            }
                            SPUtil.saveInfo(mContext , "downloading" , downloading);

                        }

                        @Override
                        public void onWait() {

                        }
                    });
        }

        private String doubleToString(long file){
            DecimalFormat df = new DecimalFormat("0.0");
            String result = df.format((double) file / 1048576) + "";
            return result ;
        }

        private String setTime(){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// yyyy-MM-dd
            //获取当前时间
            Date date = new Date(System.currentTimeMillis());
            return simpleDateFormat.format(date) ;
        }


        public void pauseDownload(String url) {
            DownloadManger.getInstance(mContext).pause(url);
        }

        public void resumeDownload(String url) {
            DownloadManger.getInstance(mContext).resume(url);
        }

        public void cancelDownload(String url) {
            DownloadManger.getInstance(mContext).cancel(url);
        }

        public void restartDownload(String url) {
            DownloadManger.getInstance(mContext).restart(url);
        }

        public float getProgress(String url) {
            if (DownloadManger.getInstance(mContext).getCurrentData(url) != null) {
                return DownloadManger.getInstance(mContext).getCurrentData(url).getPercentage();
            }
            return -1;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        list = NoteLibraryFragment.list;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


}
