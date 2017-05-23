package com.wiggins.zoominscrollview;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.wiggins.zoominscrollview.base.BaseActivity;
import com.wiggins.zoominscrollview.utils.ToastUtil;
import com.wiggins.zoominscrollview.utils.UIUtils;
import com.wiggins.zoominscrollview.widget.TitleView;

/**
 * @Description 自定义ScrollView实现放大回弹效果
 * @Author 一花一世界
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TitleView titleView;
    private LinearLayout mLlAttention;
    private LinearLayout mLlFans;
    private LinearLayout mLlVisitors;
    private LinearLayout mLlTalkAabout;
    private LinearLayout mLlAlbum;
    private LinearLayout mLlCollection;
    private LinearLayout mLlAtNight;
    private LinearLayout mLlSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListener();
    }

    private void initView() {
        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setAppTitle(UIUtils.getString(R.string.title));
        titleView.setLeftImageVisibility(View.GONE);
        mLlAttention = (LinearLayout) findViewById(R.id.ll_attention);
        mLlFans = (LinearLayout) findViewById(R.id.ll_fans);
        mLlVisitors = (LinearLayout) findViewById(R.id.ll_visitors);
        mLlTalkAabout = (LinearLayout) findViewById(R.id.ll_talk_about);
        mLlAlbum = (LinearLayout) findViewById(R.id.ll_album);
        mLlCollection = (LinearLayout) findViewById(R.id.ll_collection);
        mLlAtNight = (LinearLayout) findViewById(R.id.ll_at_night);
        mLlSetting = (LinearLayout) findViewById(R.id.ll_setting);
    }

    private void setListener() {
        mLlAttention.setOnClickListener(this);
        mLlFans.setOnClickListener(this);
        mLlVisitors.setOnClickListener(this);
        mLlTalkAabout.setOnClickListener(this);
        mLlAlbum.setOnClickListener(this);
        mLlCollection.setOnClickListener(this);
        mLlAtNight.setOnClickListener(this);
        mLlSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_attention:
                ToastUtil.showText(UIUtils.getString(R.string.attention));
                break;
            case R.id.ll_fans:
                ToastUtil.showText(UIUtils.getString(R.string.fans));
                break;
            case R.id.ll_visitors:
                ToastUtil.showText(UIUtils.getString(R.string.visitors));
                break;
            case R.id.ll_talk_about:
                ToastUtil.showText(UIUtils.getString(R.string.talk_about));
                break;
            case R.id.ll_album:
                ToastUtil.showText(UIUtils.getString(R.string.album));
                break;
            case R.id.ll_collection:
                ToastUtil.showText(UIUtils.getString(R.string.collection));
                break;
            case R.id.ll_at_night:
                ToastUtil.showText(UIUtils.getString(R.string.at_night));
                break;
            case R.id.ll_setting:
                ToastUtil.showText(UIUtils.getString(R.string.setting));
                break;
        }
    }
}
