package com.example.dildil.video.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.dildil.R;
import com.example.dildil.api.ApiEngine;
import com.example.dildil.api.ApiService;
import com.example.dildil.util.SharedPreferencesUtil;
import com.example.dildil.video.adapter.TubatuAdapter;
import com.example.dildil.video.bean.CoinBean;
import com.example.dildil.video.bean.ThumbsUpBean;
import com.example.dildil.video.bean.dto;
import com.example.dildil.video.bean.str;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import rewriting_view.ClipViewPager;
import rewriting_view.ScalePageTransformer;

public class CoinDialog extends Dialog implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private Context mContext;
    private Activity mActivity;
    private View view;
    private TubatuAdapter mPagerAdapter;
    private ClipViewPager mViewPager;
    private ImageView close;
    private CheckBox CB_thumbsUp;
    private int vid;
    private throwCoinResultListener CoinListener;
    private boolean isThumbsUp = false;
    private ApiService mService;

    public CoinDialog(@NonNull Context context, int vid) {
        super(context);
        mContext = context;
        this.vid = vid;
        init();
    }

    private void init() {
        mActivity = (Activity) mContext;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_coin, null);
        close = view.findViewById(R.id.close);
        CB_thumbsUp = view.findViewById(R.id.CB_thumbsUp);
        CB_thumbsUp.setOnCheckedChangeListener(this);
        CB_thumbsUp.setChecked((Boolean) SharedPreferencesUtil.getData("isThumbsUp", false));

        close.setOnClickListener(this);
        mViewPager = view.findViewById(R.id.viewpager);
        /**调节ViewPager的滑动速度**/
        mViewPager.setSpeedScroller(300);

        /**给ViewPager设置缩放动画，这里通过PageTransformer来实现**/
        mViewPager.setPageTransformer(true, new ScalePageTransformer());
        List<String> strList = Arrays.asList("投1个币", "投2个币");
        /**
         * 需要将整个页面的事件分发给ViewPager，不然的话只有ViewPager中间的view能滑动，其他的都不能滑动，
         * 这是肯定的，因为ViewPager总体布局就是中间那一块大小，其他的子布局都跑到ViewPager外面来了
         */
        view.findViewById(R.id.page_container).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });
        mPagerAdapter = new TubatuAdapter(mContext, strList);
        mPagerAdapter.setListener(listener);
        mViewPager.setAdapter(mPagerAdapter);

        setContentView(view);

        //设置弹框窗口背景颜色透明
        this.getWindow().setBackgroundDrawableResource(R.color.transparent);
        this.getWindow().setGravity(Gravity.CENTER);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams attributes = this.getWindow().getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        this.getWindow().setAttributes(attributes);

        initData();
    }

    TubatuAdapter.OnItemListener listener = new TubatuAdapter.OnItemListener() {
        @Override
        public void onLikeClick(int position) {
            boolean key = (boolean) SharedPreferencesUtil.getData("isThumbsUp", false);
            dto dto;
            switch (position){
                case 0:
                    dto = new dto(1, vid);
                    if (key){
                        str str = new str(vid);
                        throwCoin(dto, 1);
                        ThumbsUp("http://116.196.105.203/videoservice",str);
                    }else{
                        throwCoin(dto, 1);
                    }
                    break;
                case 1:
                    dto = new dto(2, vid);
                    if (key){
                        str str = new str(vid);
                        throwCoin(dto, 1);
                        ThumbsUp("http://116.196.105.203/videoservice",str);
                    }else{
                        throwCoin(dto, 1);
                    }
                    break;
            }
            dismiss();
        }
    };

    private void initData() {
        mService = ApiEngine.getInstance().getApiService();
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.coin_one);
        list.add(R.mipmap.coin_two);
        /**这里需要将setOffscreenPageLimit的值设置成数据源的总个数，如果不加这句话，会导致左右切换异常；**/
        mViewPager.setOffscreenPageLimit(list.size());
        mPagerAdapter.addAll(list);
    }

    private void throwCoin(dto dto, int uid) {
        mService.coin_Operated(dto, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CoinBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CoinBean coinBean) {
                        if (CoinListener != null) {
                            CoinListener.throwCoinSuccess(coinBean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (CoinListener != null) {
                            CoinListener.throwCoinFail(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void ThumbsUp(String url, str str) {
        mService.thumbsUp(url, str).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ThumbsUpBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ThumbsUpBean thumbsUpBean) {
                        if (CoinListener != null) {
                            CoinListener.ThumbsUpSuccess(thumbsUpBean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (CoinListener != null) {
                            CoinListener.ThumbsUpFail(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.CB_thumbsUp:
                if (isChecked) {
                    isThumbsUp = true;
                    SharedPreferencesUtil.putData("isThumbsUp", true);
                } else {
                    isThumbsUp = false;
                    SharedPreferencesUtil.putData("isThumbsUp", false);
                }
                break;
        }
    }

    public interface throwCoinResultListener {
        void throwCoinSuccess(CoinBean coinBean);

        void throwCoinFail(String e);

        void ThumbsUpSuccess(ThumbsUpBean bean);

        void ThumbsUpFail(String e);
    }

    public void setListener(throwCoinResultListener listener) {
        this.CoinListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                dismiss();
                break;
        }
    }
}
