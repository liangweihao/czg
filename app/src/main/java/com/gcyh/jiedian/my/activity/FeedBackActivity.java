package com.gcyh.jiedian.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.PublicPhotoAdapter;
import com.gcyh.jiedian.base.BaseActivity;
import com.yanzhenjie.album.Album;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener , PublicPhotoAdapter.IPublicItemListener{


    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.rb_feed_back_product_idea)
    RadioButton rbFeedBackProductIdea;
    @BindView(R.id.rb_feed_back_program_error)
    RadioButton rbFeedBackProgramError;
    @BindView(R.id.rb_feed_back_lack_content)
    RadioButton rbFeedBackLackContent;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.et_feed_back_content)
    EditText etFeedBackContent;
    @BindView(R.id.tv_feed_back_content_number)
    TextView tvFeedBackContentNumber;
    @BindView(R.id.recycleView_Feed_Back)
    RecyclerView recycleViewFeedBack;
    @BindView(R.id.et_feed_back_phone)
    EditText etFeedBackPhone;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private static final int ACTIVITY_REQUEST_SELECT_PHOTO = 1024;
    private List<String> piclist = new ArrayList<>();  //图片集合
    private PublicPhotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("意见反馈");
        radioGroup.setOnCheckedChangeListener(this);
        rbFeedBackProductIdea.setChecked(true);
    }

    @Override
    protected void initView() {
        recycleViewFeedBack.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new PublicPhotoAdapter(piclist, this, this);
        adapter.setShopNum(4);
        recycleViewFeedBack.setAdapter(adapter);
    }

    @Override
    protected void initPresenter() {

        //对EditView输入内容监听
        etFeedBackContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tvFeedBackContentNumber.setText(editable.length()+"/500");
            }
        });

    }

    @Override
    protected void initValue() {

    }

    @OnClick({R.id.btn_back, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_submit:
                //提交
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_feed_back_product_idea:

                break;
            case R.id.rb_feed_back_program_error:

                break;
            case R.id.rb_feed_back_lack_content:

                break;
            default:
                break;
        }
    }

    @Override
    public void onAdd() {
        picSelect() ;
    }

    @Override
    public void onDelete(View v, int position) {
        piclist.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onShop(int position) {

    }

    private void picSelect() {
        // 1. 使用默认风格，并指定选择数量：
        // 第一个参数Activity/Fragment； 第二个request_code； 第三个允许选择照片的数量，不填可以无限选择。
        // Album.startAlbum(this, ACTIVITY_REQUEST_SELECT_PHOTO, 9);
        // 2. 使用默认风格，不指定选择数量：
        // Album.startAlbum(this, ACTIVITY_REQUEST_SELECT_PHOTO); // 第三个参数不填的话，可以选择无数个。
        // 3. 指定风格，并指定选择数量，如果不想限制数量传入Integer.MAX_VALUE;
        Album.startAlbum(FeedBackActivity.this, ACTIVITY_REQUEST_SELECT_PHOTO
                , 4-piclist.size()                                                         // 指定选择数量。
                , ContextCompat.getColor(this, R.color.colorAccent)        // 指定Toolbar的颜色。
                , ContextCompat.getColor(this, R.color.albumTransparentHalf));  // 指定状态栏的颜色。
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_REQUEST_SELECT_PHOTO) {
            // 拿到用户选择的图片路径List：
            if (data != null){
                List<String> pathList = Album.parseResult(data);
                piclist.addAll(pathList);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
