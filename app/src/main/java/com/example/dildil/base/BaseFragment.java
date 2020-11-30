package com.example.dildil.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dildil.util.LoadingsDialog;
import com.example.dildil.util.NetUtil;
import com.example.dildil.util.XToastUtils;

import org.jetbrains.annotations.NotNull;


/**
 * 考虑是用懒加载来加载Fragment
 * Created By fuxinbo on 2020/5/14
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements View.OnClickListener {

    //Loading加载类
    protected LoadingsDialog mDialogs;

    protected Activity activity;

    //fragment标题，用于Tab的标题
    public String fragmentTitle;

    //判断是否隐藏
    private boolean isFragmentVisible;

    //View是否加载完成
    private boolean isPrepared;

    //是否为第一次加载
    private boolean isFirstLoad = true;

    //强制刷新数据 但仍然要 visible & Prepared，采取reset数据的方式，所以要重新走initData
    private boolean forceLoad = false;

    //首页的离线数据
    public static String offlineData = "OfflineData";

    //追番的离线数据
    public static String LocalHua = "LocalHua";

    public static ICallBackListener listener;

    public boolean isScroll = true;

    private View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = initView(inflater, container, savedInstanceState);
            isFirstLoad = true;
            isPrepared = true;
            initView();
            lazyLoad();
        }
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPrepared = false;
    }

    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 要实现延迟加载Fragment内容,需要在 onCreateView
     * isPrepared = true;
     */
    protected void lazyLoad() {
        if (isPrepared() && isFragmentVisible()) {
            if (forceLoad || isFirstLoad()) {
                forceLoad = false;
                isFirstLoad = false;
                if (NetUtil.isNetworkAvailable(getContext())) {
                    initData();
                } else {
                    XToastUtils.info("当前无网络！");
                    initLocalData();
                }
//                initData();
            }
        }
    }

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据方法
     */
    protected abstract void initData();

    protected abstract void initLocalData();

    /**
     * ViewPager联合使用
     * isVisibleToUser表示是否显示出来了
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    /**
     * 显示fragment，顺便刷新数据
     */
    protected void onVisible() {
        isFragmentVisible = true;
        lazyLoad();
    }

    /**
     * 隐藏fragment
     */
    protected void onInvisible() {
        isFragmentVisible = false;
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    /**
     * 对Loading进行初始化
     */
    private void createDialog() {
        if (mDialogs == null) {
            mDialogs = LoadingsDialog.getInstance(getContext());
        }
    }

    /**
     * 忽略isFirstLoad的值，强制刷新数据，但仍要Visible & Prepared
     */
    public void setForceLoad(boolean forceLoad) {
        this.forceLoad = forceLoad;
    }

    public boolean isPrepared() {
        return isPrepared;
    }

    public boolean isFragmentVisible() {
        return isFragmentVisible;
    }

    public boolean isFirstLoad() {
        return isFirstLoad;
    }

    /**
     * 刷新数据
     */
    public void refreshData() {
        if (isFragmentVisible()) {
            initData();
        } else {
            setForceLoad(true);
        }
    }

    public interface ICallBackListener {
        void onItemClick(boolean value);
    }

    public void setCallBackListener(ICallBackListener listener1) {
        listener = listener1;
    }

    public void setScanScroll(boolean value) {
        if (listener != null) {
            listener.onItemClick(value);
        }
    }

    /**
     * 设置fragment的标题文字
     *
     * @param title
     */
    public void setFragmentTitle(String title) {
        fragmentTitle = title;
    }

    /**
     * 获取fragment的标题文字
     *
     * @return
     */
    public String getTitle() {
        return fragmentTitle;
    }

    public void showDialog() {
        mDialogs.show();
    }

    public void hideDialog() {
        mDialogs.dismiss();
    }

}
