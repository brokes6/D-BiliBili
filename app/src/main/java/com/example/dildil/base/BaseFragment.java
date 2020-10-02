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

import com.example.dildil.MyApplication;
import com.example.dildil.login_page.bean.LoginBean;
import com.example.dildil.util.GsonUtil;
import com.example.dildil.util.LoadingsDialog;
import com.example.dildil.util.NetUtil;
import com.example.dildil.util.SharePreferenceUtil;
import com.example.dildil.util.XToastUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isFirstLoad = true;
        isPrepared = true;
        View view = initView(inflater, container, savedInstanceState);
        initView();
        lazyLoad();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
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

    public LoginBean getUserData() {
        return GsonUtil.fromJSON(SharePreferenceUtil.getInstance(getContext()).getUserInfo(""), LoginBean.class);
    }

    /**
     * 保存数据
     *
     * @param data
     */
    public void save(String data, String name) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            //设置文件名称，以及存储方式
            out = MyApplication.getContext().openFileOutput(name, Context.MODE_PRIVATE);
            //创建一个OutputStreamWriter对象，传入BufferedWriter的构造器中
            writer = new BufferedWriter(new OutputStreamWriter(out));
            //向文件中写入数据
            writer.write(data);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取数据
     *
     * @return
     */
    public String load(String name) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            //设置将要打开的存储文件名称
            in = MyApplication.getContext().openFileInput(name);
            //FileInputStream -> InputStreamReader ->BufferedReader
            reader = new BufferedReader(new InputStreamReader(in));
            String line = new String();
            //读取每一行数据，并追加到StringBuilder对象中，直到结束
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
}
