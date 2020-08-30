package com.example.dildil.video.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.dildil.R;
import com.example.dildil.util.XToastUtils;
import com.example.dildil.video.adapter.TubatuAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rewriting_view.ClipViewPager;
import rewriting_view.ScalePageTransformer;

public class CoinDialog extends Dialog implements View.OnClickListener{
    private Context mContext;
    private Activity mActivity;
    private View view;
    private TubatuAdapter mPagerAdapter;
    private ClipViewPager mViewPager;
    private ImageView close;

    public CoinDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init(){
        mActivity = (Activity) mContext;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_coin,null);
        close = view.findViewById(R.id.close);
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
        mPagerAdapter = new TubatuAdapter(mContext,strList);
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
            if (position==0){
                XToastUtils.toast("投1个币成功");
            }else{
                XToastUtils.toast("投2个币成功");
            }
            dismiss();
        }
    };

    private void initData(){
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.coin_one);
        list.add(R.mipmap.coin_two);
        /**这里需要将setOffscreenPageLimit的值设置成数据源的总个数，如果不加这句话，会导致左右切换异常；**/
        mViewPager.setOffscreenPageLimit(list.size());
        mPagerAdapter.addAll(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close:
                dismiss();
                break;
        }
    }
}
