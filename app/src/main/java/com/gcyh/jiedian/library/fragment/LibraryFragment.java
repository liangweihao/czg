package com.gcyh.jiedian.library.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.androidkun.xtablayout.XTabLayout;
import com.bumptech.glide.Glide;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.TabLayoutFragmentAdapter;
import com.gcyh.jiedian.base.BaseFragment;
import com.gcyh.jiedian.library.activity.LibrarySearchActivity;
import com.gcyh.jiedian.library.fragment.datalibrary.DataLibraryFragment;
import com.gcyh.jiedian.library.fragment.notelibrary.NoteLibraryFragment;
import com.gcyh.jiedian.library.fragment.projectlibrary.ProjectLibraryFragment;
import com.gcyh.jiedian.my.activity.MyDownLoadActivity;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;
import com.gcyh.jiedian.util.HttpConnectLtp;
import com.gcyh.jiedian.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Caizhiguang on 2018/4/18.
 */

public class LibraryFragment extends BaseFragment {

    @BindView(R.id.iv_titlebar_type)
    ImageView ivTitlebarType;
    @BindView(R.id.iv_titlebar_search)
    ImageView ivTitlebarSearch;
    @BindView(R.id.et_titlebar_search)
    EditText etTitlebarSearch;
    @BindView(R.id.iv_titlebar_delete)
    ImageView ivTitlebarDelete;
    @BindView(R.id.iv_titlebar_jia)
    ImageView ivTitlebarJia;
    @BindView(R.id.libraryXxTablayout)
    XTabLayout libraryXxTablayout;
    @BindView(R.id.libraryViewPager)
    ViewPager libraryViewPager;
    @BindView(R.id.rl_titlebar)
    RelativeLayout rlTitlebar;
    Unbinder unbinder;
    @BindView(R.id.titlebar)
    LinearLayout titlebar;


    List<Fragment> fragments = new ArrayList<>();

    private int position;
    private boolean isType = true;
    private ViewGroup anim_mask_layout;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_library;
    }

    @Override
    protected void initPresenter() {

        ivTitlebarType.setVisibility(View.VISIBLE);

        ivTitlebarType.setImageResource(R.mipmap.library_download);
        etTitlebarSearch.setHint("石膏板吊顶....");

        int type = SPUtil.getInt(getActivity(), "LIBRARY_NOTE_CODE", 1);
        if (type == 1) {
            //列表
            ivTitlebarJia.setImageResource(R.mipmap.type_list);
            isType = true;
        } else {
            //宫格
            ivTitlebarJia.setImageResource(R.mipmap.type_box);
            isType = false;
        }

        List<String> titles = new ArrayList<>();
        titles.add("节点库");
        titles.add("项目库");
        titles.add("常用资料库");

        for (int i = 0; i < titles.size(); i++) {
            if (i == 0) {
                fragments.add(new NoteLibraryFragment());
            } else if (i == 1) {
                fragments.add(new ProjectLibraryFragment());
            } else if (i == 2) {
                fragments.add(new DataLibraryFragment());
            }
        }
        TabLayoutFragmentAdapter adatper = new TabLayoutFragmentAdapter(getChildFragmentManager(), fragments, titles);

        libraryViewPager.setAdapter(adatper);
        libraryViewPager.setOffscreenPageLimit(0);
        //将TabLayout和ViewPager关联起来。
        libraryXxTablayout.setupWithViewPager(libraryViewPager);
        libraryViewPager.setCurrentItem(0);

    }

    @Override
    protected void initView() {

        libraryXxTablayout.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                position = tab.getPosition();
                if (tab.getPosition() == 0) {
                    libraryViewPager.setCurrentItem(0);
                    int type = SPUtil.getInt(getActivity(), "LIBRARY_NOTE_CODE", 1);
                    if (type == 1) {
                        //列表
                        ivTitlebarJia.setImageResource(R.mipmap.type_list);
                        isType = true;
                    } else {
                        //宫格
                        ivTitlebarJia.setImageResource(R.mipmap.type_box);
                        isType = false;
                    }
                } else if (tab.getPosition() == 1) {
                    libraryViewPager.setCurrentItem(1);
                    int type = SPUtil.getInt(getActivity(), "LIBRARY_PROJECT_CODE", 2);
                    if (type == 1) {
                        //列表
                        ivTitlebarJia.setImageResource(R.mipmap.type_list);
                        isType = true;
                    } else {
                        //宫格
                        ivTitlebarJia.setImageResource(R.mipmap.type_big_pic);
                        isType = false;
                    }
                } else if (tab.getPosition() == 2) {
                    libraryViewPager.setCurrentItem(2);
                    int type = SPUtil.getInt(getActivity(), "LIBRARY_DATA_CODE", 1);
                    if (type == 1) {
                        //列表
                        ivTitlebarJia.setImageResource(R.mipmap.type_list);
                        isType = true;
                    } else {
                        //宫格
                        ivTitlebarJia.setImageResource(R.mipmap.type_box);
                        isType = false;
                    }
                }
            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void initValue() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_titlebar_type, R.id.rl_titlebar, R.id.iv_titlebar_jia, R.id.iv_titlebar_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_type:
                //跳转到下载管理
                startIntent(MyDownLoadActivity.class);
                break;
            case R.id.rl_titlebar:

                break;
            case R.id.iv_titlebar_jia:

                if (isType) {
                    //列表
                    if (position == 1) {
                        ivTitlebarJia.setImageResource(R.mipmap.type_big_pic);
                        isType = false;
                    } else {
                        ivTitlebarJia.setImageResource(R.mipmap.type_box);
                        isType = false;
                    }

                } else {
                    //宫格
                    ivTitlebarJia.setImageResource(R.mipmap.type_list);
                    isType = true;
                }

                if (position == 0) {
                    //节点库切换条目类型
                    EventBusUtil.postEvent(EventBusCode.CODE_LIBRARY_NOTE);
                } else if (position == 1) {
                    //项目库切换条目类型
                    EventBusUtil.postEvent(EventBusCode.CODE_LIBRARY_PROJECT);
                } else if (position == 2) {
                    //常用数据库切换条目类型
                    EventBusUtil.postEvent(EventBusCode.CODE_LIBRARY_DATA);
                }

                break;
            case R.id.iv_titlebar_search:
                ArrayList<HttpConnectLtp.InfoBean> split = split(etTitlebarSearch.getText().toString());
                if (split != null) {
                    String string = "";
                    for (int a = 0; a < split.size(); a++) {
                        String cont = split.get(a).cont;
                        if (cont.length() > 1) {
                            string = string + "," + cont;
                        }
                    }
//               分词字体设置为红色
//                    String str = et.getText().toString();
//                    String tempstr = "吊顶";
//                    str = str.replaceAll(tempstr, "<font color='red'>" + tempstr + "</font>");
//                    text.setText(Html.fromHtml(str));

                }
                //分词成功 跳转
                startIntent(LibrarySearchActivity.class);
                break;
        }
    }

    //分词
    public ArrayList<HttpConnectLtp.InfoBean> split(String text) {
        HttpConnectLtp htp = new HttpConnectLtp(text, "ws");
        htp.start();
        try {
            htp.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return htp.infoBeans;
    }

    @Override
    public void onEventMainThread(int eventCode, Bundle bundle) {
        if (eventCode == EventBusCode.LIBRARY_DOWNLOAD_ANIMATION) {
            //开始下载动画
            int[] startLocation = bundle.getIntArray("LIBRARY_DOWNLOAD_ANIMATION");
            setAnim(startLocation) ;
        }
    }


    /*
    * 下载动画（仿购物车）
    * */
    private void setAnim(int[] startLocation) {
        final ImageView ball = new ImageView(getActivity());// buyImg是动画的图片，我的是一个小球（R.drawable.sign）
        Glide.with(getActivity()).load(R.mipmap.jiedian_item).asBitmap().override(200 , 100).into(ball) ;
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(ball);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, ball,
                startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        ivTitlebarType.getLocationInWindow(endLocation);// ivTitlebarType是那个抛物线最后掉落的控件

        // 计算位移
        int endX = 0 - startLocation[0]  ;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1] ;// 动画位移的y坐标

        TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endX, 0, 0);

        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
                0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(1,0.5f,1,0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f) ;
        scaleAnimation.setRepeatCount(0);
        scaleAnimation.setFillAfter(true);

        final AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(scaleAnimation);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);


        set.setDuration(1000);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                ball.setVisibility(View.VISIBLE);
                //    Log.e("动画","asdasdasdasd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                ball.setVisibility(View.GONE);
                set.cancel();
                animation.cancel();
            }
        });

    }

    /**
     * @param
     * @return void
     * @throws
     * @Description: 创建动画层
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) getActivity().getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

}
