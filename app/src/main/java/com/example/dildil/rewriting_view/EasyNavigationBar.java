package com.example.dildil.rewriting_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dildil.R;
import com.example.dildil.home_page.adapter.ViewPagerAdapter;
import com.example.dildil.util.NavigationUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jue on 2018/6/1.
 */

public class EasyNavigationBar extends LinearLayout {


    private RelativeLayout AddContainerLayout;

    //Tab数量
    private int tabCount = 0;

    private LinearLayout navigationLayout;
    private RelativeLayout contentView;
    //分割线
    private View lineView;

    //红点集合
    private List<View> hintPointList = new ArrayList<>();

    //消息数量集合
    private List<TextView> msgPointList = new ArrayList<>();

    //底部Image集合
    private List<ImageView> imageViewList = new ArrayList<>();

    //底部Text集合
    private List<TextView> textViewList = new ArrayList<>();

    //底部TabLayout（除中间加号）
    private List<View> tabList = new ArrayList<>();

    private ViewPager mViewPager;
    private ViewPager2 mViewPager2;
    //private GestureDetector detector;

    private ViewGroup addViewLayout;


    //文字集合
    private String[] titleItems = new String[]{};
    //未选择 图片集合
    private int[] normalIconItems = new int[]{};
    //已选择 图片集合
    private int[] selectIconItems = new int[]{};
    //fragment集合
    private List<Fragment> fragmentList = new ArrayList<>();

    private FragmentManager fragmentManager;

    //    //Tab点击动画效果
//    private Techniques anim = null;
    //ViewPager切换动画
    private boolean smoothScroll = false;
    //图标大小
    private int iconSize;

    //提示红点大小
    private float hintPointSize;
    //提示红点距Tab图标右侧的距离
    private float hintPointLeft;
    //提示红点距图标顶部的距离
    private float hintPointTop;

    private EasyNavigationBar.OnTabClickListener onTabClickListener;
    private EasyNavigationBar.OnCenterTabSelectListener onCenterTabClickListener;
    private EasyNavigationBar.OnTabLoadListener onTabLoadListener;

    //消息红点字体大小
    private float msgPointTextSize;
    //消息红点大小
    private float msgPointSize;
    //消息红点99+的长度
    private float msgPointMoreWidth;
    //消息红点99+的高度
    private float msgPointMoreHeight;
    //消息红点99+的半径
    private int msgPointMoreRadius;
    //消息红点颜色
    private int msgPointColor;
    //消息红点距Tab图标右侧的距离   默认为Tab图标的一半
    private float msgPointLeft;
    //消息红点距图标顶部的距离  默认为Tab图标的一半
    private float msgPointTop;
    //Tab文字距Tab图标的距离
    private float tabTextTop;
    //Tab文字大小
    private float tabTextSize;
    //未选中Tab字体颜色
    private int normalTextColor;
    //选中字体颜色
    private int selectTextColor;
    //分割线高度
    private float lineHeight;
    //分割线颜色
    private int lineColor;

    private int navigationBackground;
    private float navigationHeight;

    private ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_INSIDE;

    private boolean canScroll;
    private ViewPagerAdapter adapter;


    private float centerIconSize;
    private float centerLayoutHeight = navigationHeight;

    private float centerLayoutBottomMargin;

    //RULE_CENTER 居中只需调节centerLayoutHeight 默认和navigationHeight相等 此时centerLayoutBottomMargin属性无效
    //RULE_BOTTOM centerLayoutHeight属性无效、自适应、只需调节centerLayoutBottomMargin距底部的距离
    private int centerLayoutRule = RULE_CENTER;

    public static final int RULE_CENTER = 0;
    public static final int RULE_BOTTOM = 1;

    //true  ViewPager在Navigation上面
    //false  ViewPager和Navigation重叠
    private boolean hasPadding;


    //1、普通的Tab 2、中间带按钮（如加号）3、
    private int mode;

    //true 点击加号切换fragment
    //false 点击加号不切换fragment进行其他操作（跳转界面等）
    private boolean centerAsFragment;
    //自定义加号view
    private View customAddView;
    private float centerTextSize;
    //加号文字未选中颜色（默认同其他tab）
    private int centerNormalTextColor;
    //加号文字选中颜色（默认同其他tab）
    private int centerSelectTextColor;
    //加号文字距离顶部加号的距离
    private float centerTextTopMargin;
    //是否和其他tab文字底部对齐
    private boolean centerAlignBottom;
    private ImageView centerImage;
    private View empty_line;

    private int contentType;

    //中间布局的图片资源
    private int centerImageRes;
    //中间布局的文字
    private String centerTextStr;
    //只是导航没有ViewPager
    private boolean onlyNavigation;
    //记录位置
    private int currentPosition;
    //Tab内容布局方式
    private int tabContentRule;
    //Tab内容距底部距离
    private int tabContentBottomMargin;
    //字体显示为DP还是SP  默认1 为DP 2SP
    private int textSizeType;
    //是否为ViewPager2
    private boolean isViewPager2;


    public EasyNavigationBar(Context context) {
        super(context);

        initViews(context, null);
    }

    public EasyNavigationBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {

        defaultSetting();

        contentView = (RelativeLayout) View.inflate(context, R.layout.container_layout, null);
        addViewLayout = contentView.findViewById(R.id.add_view_ll);
        AddContainerLayout = contentView.findViewById(R.id.add_rl);
        empty_line = contentView.findViewById(R.id.empty_line);
        navigationLayout = contentView.findViewById(R.id.navigation_ll);
        lineView = contentView.findViewById(R.id.common_horizontal_line);
        lineView.setTag(-100);
        empty_line.setTag(-100);
        navigationLayout.setTag(-100);


        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.EasyNavigationBar);
        parseStyle(attributes);

        addView(contentView);
    }

    private void parseStyle(TypedArray attributes) {
        if (attributes != null) {
            //要放在前面
            textSizeType = attributes.getInt(R.styleable.EasyNavigationBar_Easy_textSizeType, textSizeType);


            msgPointColor = attributes.getColor(R.styleable.EasyNavigationBar_Easy_msgPointColor, msgPointColor);
            navigationHeight = attributes.getDimension(R.styleable.EasyNavigationBar_Easy_navigationHeight, navigationHeight);
            navigationBackground = attributes.getColor(R.styleable.EasyNavigationBar_Easy_navigationBackground, navigationBackground);

            msgPointMoreWidth = attributes.getDimension(R.styleable.EasyNavigationBar_Easy_msgPointMoreWidth, msgPointMoreWidth);
            msgPointMoreHeight = attributes.getDimension(R.styleable.EasyNavigationBar_Easy_msgPointMoreHeight, msgPointMoreHeight);
            msgPointMoreRadius = attributes.getInt(R.styleable.EasyNavigationBar_Easy_msgPointMoreRadius, msgPointMoreRadius);
            tabTextSize = NavigationUtil.compareTo(getContext(), attributes.getDimension(R.styleable.EasyNavigationBar_Easy_tabTextSize, 0), tabTextSize, textSizeType);
            tabTextTop = attributes.getDimension(R.styleable.EasyNavigationBar_Easy_tabTextTop, tabTextTop);
            iconSize = (int) attributes.getDimension(R.styleable.EasyNavigationBar_Easy_tabIconSize, iconSize);
            hintPointSize = attributes.getDimension(R.styleable.EasyNavigationBar_Easy_hintPointSize, hintPointSize);
            msgPointSize = attributes.getDimension(R.styleable.EasyNavigationBar_Easy_msgPointSize, msgPointSize);
            hintPointLeft = attributes.getDimension(R.styleable.EasyNavigationBar_Easy_hintPointLeft, hintPointLeft);
            msgPointTop = attributes.getDimension(R.styleable.EasyNavigationBar_Easy_msgPointTop, -iconSize * 3 / 5);
            hintPointTop = attributes.getDimension(R.styleable.EasyNavigationBar_Easy_hintPointTop, hintPointTop);

            msgPointLeft = attributes.getDimension(R.styleable.EasyNavigationBar_Easy_msgPointLeft, -iconSize / 2);
            msgPointTextSize = NavigationUtil.compareTo(getContext(), attributes.getDimension(R.styleable.EasyNavigationBar_Easy_msgPointTextSize, 0), msgPointTextSize, textSizeType);
            centerIconSize = attributes.getDimension(R.styleable.EasyNavigationBar_Easy_centerIconSize, centerIconSize);
            centerLayoutBottomMargin = attributes.getDimension(R.styleable.EasyNavigationBar_Easy_centerLayoutBottomMargin, centerLayoutBottomMargin);

            //加号属性
            centerSelectTextColor = attributes.getColor(R.styleable.EasyNavigationBar_Easy_centerSelectTextColor, centerSelectTextColor);
            centerNormalTextColor = attributes.getColor(R.styleable.EasyNavigationBar_Easy_centerNormalTextColor, centerNormalTextColor);
            centerTextSize = NavigationUtil.compareTo(getContext(), attributes.getDimension(R.styleable.EasyNavigationBar_Easy_centerTextSize, 0), centerTextSize, textSizeType);
            centerTextTopMargin = attributes.getDimension(R.styleable.EasyNavigationBar_Easy_centerTextTopMargin, centerTextTopMargin);
            centerAlignBottom = attributes.getBoolean(R.styleable.EasyNavigationBar_Easy_centerAlignBottom, centerAlignBottom);


            lineHeight = attributes.getDimension(R.styleable.EasyNavigationBar_Easy_lineHeight, lineHeight);
            lineColor = attributes.getColor(R.styleable.EasyNavigationBar_Easy_lineColor, lineColor);


            centerLayoutHeight = attributes.getDimension(R.styleable.EasyNavigationBar_Easy_centerLayoutHeight, navigationHeight + lineHeight);

            normalTextColor = attributes.getColor(R.styleable.EasyNavigationBar_Easy_tabNormalColor, normalTextColor);
            selectTextColor = attributes.getColor(R.styleable.EasyNavigationBar_Easy_tabSelectColor, selectTextColor);

            int type = attributes.getInt(R.styleable.EasyNavigationBar_Easy_scaleType, 0);
            if (type == 0) {
                scaleType = ImageView.ScaleType.CENTER_INSIDE;
            } else if (type == 1) {
                scaleType = ImageView.ScaleType.CENTER_CROP;
            } else if (type == 2) {
                scaleType = ImageView.ScaleType.CENTER;
            } else if (type == 3) {
                scaleType = ImageView.ScaleType.FIT_CENTER;
            } else if (type == 4) {
                scaleType = ImageView.ScaleType.FIT_END;
            } else if (type == 5) {
                scaleType = ImageView.ScaleType.FIT_START;
            } else if (type == 6) {
                scaleType = ImageView.ScaleType.FIT_XY;
            } else if (type == 7) {
                scaleType = ImageView.ScaleType.MATRIX;
            }

            centerLayoutRule = attributes.getInt(R.styleable.EasyNavigationBar_Easy_centerLayoutRule, centerLayoutRule);
            hasPadding = attributes.getBoolean(R.styleable.EasyNavigationBar_Easy_hasPadding, hasPadding);

            centerAsFragment = attributes.getBoolean(R.styleable.EasyNavigationBar_Easy_centerAsFragment, centerAsFragment);
            attributes.recycle();
        }
    }


    public EasyNavigationBar setupWithViewPager(@NonNull ViewPager viewPager) {
//        final PagerAdapter adapter = viewPager.getAdapter();
//        if (adapter == null) {
//            throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
//        }
        isViewPager2 = false;
        onlyNavigation = true;
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectTab(position, smoothScroll, false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return this;
    }


    public EasyNavigationBar setupWithViewPager(@NonNull ViewPager2 viewPager2) {
//        final PagerAdapter adapter = viewPager.getAdapter();
//        if (adapter == null) {
//            throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
//        }
        isViewPager2 = true;
        onlyNavigation = true;
        mViewPager2 = viewPager2;
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                selectTab(position, smoothScroll, false);
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        return this;
    }


    public void build() {

        if (centerLayoutHeight < navigationHeight + lineHeight)
            centerLayoutHeight = navigationHeight + lineHeight;

        if (centerLayoutRule == RULE_CENTER) {
            RelativeLayout.LayoutParams addLayoutParams = (RelativeLayout.LayoutParams) AddContainerLayout.getLayoutParams();
            addLayoutParams.height = (int) centerLayoutHeight;
            AddContainerLayout.setLayoutParams(addLayoutParams);
        } else if (centerLayoutRule == RULE_BOTTOM) {
           /* RelativeLayout.LayoutParams addLayoutParams = (RelativeLayout.LayoutParams) AddContainerLayout.getLayoutParams();
            if ((centerIconSize + addIconBottom) > (navigationHeight + 1))
                addLayoutParams.height = (int) (centerIconSize + addIconBottom);
            else
                addLayoutParams.height = (int) (navigationHeight + 1);
            AddContainerLayout.setLayoutParams(addLayoutParams);*/
        }


        navigationLayout.setBackgroundColor(navigationBackground);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) navigationLayout.getLayoutParams();
        params.height = (int) navigationHeight;
        navigationLayout.setLayoutParams(params);


        RelativeLayout.LayoutParams lineParams = (RelativeLayout.LayoutParams) lineView.getLayoutParams();
        lineParams.height = (int) lineHeight;
        lineView.setBackgroundColor(lineColor);
        lineView.setLayoutParams(lineParams);

//若没有设置中间添加的文字字体大小、颜色、则同其他Tab一样
        if (centerTextSize == 0) {
            centerTextSize = tabTextSize;
        }
        if (centerNormalTextColor == 0) {
            centerNormalTextColor = normalTextColor;
        }
        if (centerSelectTextColor == 0) {
            centerSelectTextColor = selectTextColor;
        }

        if (!checkCanBuild())
            return;

        switch (mode) {
            case EasyNavigationBar.NavigationMode.MODE_NORMAL:
                buildNavigation();
                break;
            case EasyNavigationBar.NavigationMode.MODE_ADD:
                buildAddNavigation();
                break;
            case EasyNavigationBar.NavigationMode.MODE_ADD_VIEW:
                buildAddViewNavigation();
                break;
            default:
                buildNavigation();
                break;
        }


    }

    /**
     * 重置各个参数
     */
    public EasyNavigationBar defaultSetting() {
        this.titleItems = new String[]{};
        this.normalIconItems = new int[]{};
        this.selectIconItems = new int[]{};
        this.fragmentList = new ArrayList<>();
        if (this.adapter != null)
            this.adapter.notifyDataSetChanged();

        mViewPager2 = null;

//        //Tab点击动画效果
//        anim = null;
        //ViewPager切换动画
        smoothScroll = false;
        //图标大小
        iconSize = NavigationUtil.sp2px(getContext(), 22);

        //提示红点大小
        hintPointSize = NavigationUtil.sp2px(getContext(), 6);
        //提示红点距Tab图标右侧的距离
        hintPointLeft = NavigationUtil.dip2px(getContext(), -3);
        //提示红点距图标顶部的距离
        hintPointTop = NavigationUtil.dip2px(getContext(), -3);

        //消息红点字体大小
        msgPointTextSize = 11;
        //消息红点大小
        msgPointSize = NavigationUtil.dip2px(getContext(), 16);
        //消息红点距Tab图标右侧的距离   默认为Tab图标的一半
        msgPointLeft = NavigationUtil.dip2px(getContext(), -10);
        //消息红点距图标顶部的距离  默认为Tab图标的一半
        msgPointTop = NavigationUtil.dip2px(getContext(), -12);
        //Tab文字距Tab图标的距离
        tabTextTop = NavigationUtil.dip2px(getContext(), 2);
        //Tab文字大小
        tabTextSize = 12;
        //未选中Tab字体颜色
        normalTextColor = Color.parseColor("#666666");
        //选中字体颜色
        selectTextColor = Color.parseColor("#333333");
        //分割线高度
        lineHeight = 1;
        //分割线颜色
        lineColor = Color.parseColor("#f7f7f7");

        navigationBackground = Color.parseColor("#ffffff");
        navigationHeight = NavigationUtil.dip2px(getContext(), 60);

        scaleType = ImageView.ScaleType.CENTER_INSIDE;

        canScroll = false;

        centerIconSize = 0;
        centerLayoutHeight = navigationHeight;
        centerLayoutBottomMargin = NavigationUtil.dip2px(getContext(), 10);

        //RULE_CENTER 居中只需调节centerLayoutHeight 默认和navigationHeight相等 此时centerLayoutBottomMargin属性无效
        //RULE_BOTTOM centerLayoutHeight属性无效、自适应、只需调节centerLayoutBottomMargin距底部的距离
        centerLayoutRule = RULE_CENTER;

        //true  ViewPager在Navigation上面
        //false  ViewPager和Navigation重叠
        hasPadding = true;


        //1、普通的Tab 2、中间带按钮（如加号）3、
        mode = EasyNavigationBar.NavigationMode.MODE_NORMAL;

        //true 点击加号切换fragment
        //false 点击加号不切换fragment进行其他操作（跳转界面等）
        centerAsFragment = false;

        centerTextSize = 0;
        //加号文字未选中颜色（默认同其他tab）
        centerNormalTextColor = 0;
        //加号文字选中颜色（默认同其他tab）
        centerSelectTextColor = 0;
        //加号文字距离顶部加号的距离
        centerTextTopMargin = NavigationUtil.dip2px(getContext(), 3);
        //是否和其他tab文字底部对齐
        centerAlignBottom = false;

        contentType = EasyNavigationBar.TabContentType.TYPE_NORMAL;
        centerTextStr = "";

        onTabClickListener = null;
        onCenterTabClickListener = null;
        onTabClickListener = null;


        tabContentRule = 0;
        tabContentBottomMargin = 0;
        textSizeType = 1;

        //消息红点99+的长度
        msgPointMoreWidth = NavigationUtil.dip2px(getContext(), 30);
        //消息红点99+的高度
        msgPointMoreHeight = NavigationUtil.dip2px(getContext(), 16);
        //消息红点99+的半径
        msgPointMoreRadius = 10;
        //消息红点颜色
        msgPointColor = Color.parseColor("#ff0000");
        isViewPager2 = false;

        return this;
    }

    public EasyNavigationBar centerImageRes(int centerImageRes) {
        this.centerImageRes = centerImageRes;
        return this;
    }


    /**
     * 更新导航栏图标
     *
     * @param position
     * @param isNormal
     * @param res
     */
    public void updateNavigationIcon(int position, boolean isNormal, int res) {
        if (isNormal) {
            if (normalIconItems == null | position >= normalIconItems.length) return;
            normalIconItems[position] = res;
        } else {
            if (selectIconItems == null | position >= selectIconItems.length) return;
            selectIconItems[position] = res;
        }
        updateNavigation(false);
    }

    /**
     * 更新导航栏文字
     *
     * @param position
     * @param isNormal
     * @param str
     */
    public void updateNavigationText(int position, boolean isNormal, String str) {
        if (titleItems == null | position >= titleItems.length) return;
        titleItems[position] = str;
        updateNavigation(false);
    }

    /**
     * 更新导航栏UI
     */
    public void updateNavigation(boolean showAnim) {
        if (isCenterAsFragment()) {
            if (isCenterPosition(currentPosition)) {
                selectCenterTabUI();
            } else if (isBeforeCenter(currentPosition)) {
                selectNormalTabUI(currentPosition, showAnim);
            } else {
                selectNormalTabUI(currentPosition - 1, showAnim);
            }
        } else {
            selectNormalTabUI(currentPosition, showAnim);
        }
    }

    public EasyNavigationBar centerTextStr(String centerTextStr) {
        this.centerTextStr = centerTextStr;
        return this;
    }

    @IntDef({
            EasyNavigationBar.TextSizeType.TYPE_DP,
            EasyNavigationBar.TextSizeType.TYPE_SP,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface TextSizeType {
        //文字单位：1、DP   2、SP
        int TYPE_DP = 1;
        int TYPE_SP = 2;
    }

    @IntDef({
            EasyNavigationBar.TabContentType.TYPE_NORMAL,
            EasyNavigationBar.TabContentType.TYPE_ONLY_IMAGE,
            EasyNavigationBar.TabContentType.TYPE_ONLY_TEXT,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface TabContentType {
        //Tab内容类型：0默认（有选中、未选中两种状态）  1仅图片  2仅文字
        int TYPE_NORMAL = 0;
        int TYPE_ONLY_IMAGE = 1;
        int TYPE_ONLY_TEXT = 2;
    }


    @IntDef({
            EasyNavigationBar.NavigationMode.MODE_NORMAL,
            EasyNavigationBar.NavigationMode.MODE_ADD,
            EasyNavigationBar.NavigationMode.MODE_ADD_VIEW,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface NavigationMode {
        //Tab内容类型：0默认（有选中、未选中两种状态）  1仅图片  2仅文字
        int MODE_NORMAL = 0;
        int MODE_ADD = 1;
        int MODE_ADD_VIEW = 2;
    }

    public void buildNavigation() {

        post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < tabCount; i++) {
                    addTabItemView(i);
                }
                selectNormalTabUI(0, false);
                if (onTabLoadListener != null)
                    onTabLoadListener.onTabLoadCompleteEvent();
            }
        });


    }

    /**
     * 验证能否构建
     *
     * @return
     */
    private boolean checkCanBuild() {
        if (titleItems.length < 1 && normalIconItems.length < 1) {
            Log.e(getClass().getName(), "titleItems和normalIconItems不能同时为空");
            return false;
        }
        buildCommonNavigation();

        return true;
    }


    /**
     * 构建导航栏前的通用操作
     */
    private void buildCommonNavigation() {
        if (fragmentList == null || fragmentList.size() < 1 || fragmentManager == null) {
            onlyNavigation = true;
        } else {
            onlyNavigation = false;
        }

        if (titleItems == null || titleItems.length < 1) {
            contentType = EasyNavigationBar.TabContentType.TYPE_ONLY_IMAGE;
            tabCount = normalIconItems.length;
        } else if (normalIconItems == null || normalIconItems.length < 1) {
            contentType = EasyNavigationBar.TabContentType.TYPE_ONLY_TEXT;
            tabCount = titleItems.length;
        } else {
            contentType = EasyNavigationBar.TabContentType.TYPE_NORMAL;
            if (titleItems.length > normalIconItems.length) {
                tabCount = titleItems.length;
            } else {
                tabCount = normalIconItems.length;
            }
        }

        if (isAddPage() && tabCount % 2 == 1) {
            Log.e(getClass().getName(), "1.5.0之后、添加中间Tab、则普通Tab数量应为偶数");
            return;
        }

        if (selectIconItems == null || selectIconItems.length < 1) {
            selectIconItems = normalIconItems;
        }


        removeNavigationAllView();

        if (!onlyNavigation) {
            setViewPagerAdapter();
        }

        if (hasPadding) {
            if (isViewPager2) {
                if (getViewPager2() != null) {
                    getViewPager2().setPadding(0, 0, 0, (int) (navigationHeight + lineHeight));
                }
            } else {
                if (getViewPager() != null) {
                    getViewPager().setPadding(0, 0, 0, (int) (navigationHeight + lineHeight));
                }
            }
        }
    }

    private ViewPager2 getViewPager2() {
        return mViewPager2;
    }

    /**
     * 添加ViewPager
     */
    private void setViewPagerAdapter() {
        if (mViewPager == null) {
            mViewPager = new CustomViewPager(getContext());
            mViewPager.setId(R.id.vp_layout);
            contentView.addView(mViewPager, 0);
        }
        adapter = new ViewPagerAdapter(fragmentManager, fragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(10);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectTab(position, smoothScroll, false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (canScroll) {
            ((CustomViewPager) getViewPager()).setScanScroll(true);
        } else {
            ((CustomViewPager) getViewPager()).setScanScroll(false);
        }

    }

    /**
     * 是否是前面位置
     *
     * @param position
     * @return
     */
    private boolean isBeforeCenter(int position) {
        if (position < (tabCount / 2))
            return true;
        return false;
    }

    /**
     * 是否是中间位置
     *
     * @param position
     * @return
     */
    private boolean isCenterPosition(int position) {
        if (position == tabCount / 2)
            return true;
        return false;
    }

    //构建中间带按钮的navigation
    public void buildAddNavigation() {

        if (centerImageRes == 0) {
            Log.e("EasyNavigation", "MODE_ADD模式下centerImageRes不能为空");
            return;
        }


        post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < tabCount; i++) {

                    if (i == tabCount / 2) {
                        addCenterTabView(i);
                    }
                    addTabItemView(i);
                }
                selectNormalTabUI(0, false);
                if (onTabLoadListener != null)
                    onTabLoadListener.onTabLoadCompleteEvent();
            }
        });

    }


    /**
     * 添加中间view的布局
     *
     * @param index
     */
    private void addCenterTabView(int index) {
        RelativeLayout addItemView = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams addItemParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        addItemParams.width = getWidth() / (tabCount + 1);
        addItemView.setLayoutParams(addItemParams);
        navigationLayout.addView(addItemView);

        final LinearLayout centerLinearLayout = new LinearLayout(getContext());
        centerLinearLayout.setOrientation(VERTICAL);
        centerLinearLayout.setGravity(Gravity.CENTER);
        final RelativeLayout.LayoutParams linearParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        centerImage = new ImageView(getContext());
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (centerIconSize > 0) {
            imageParams.width = (int) centerIconSize;
            imageParams.height = (int) centerIconSize;
        }
        centerImage.setLayoutParams(imageParams);


        if (centerLayoutRule == RULE_CENTER) {
            linearParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        } else if (centerLayoutRule == RULE_BOTTOM) {
            linearParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            linearParams.addRule(RelativeLayout.ABOVE, R.id.empty_line);
            if (centerAlignBottom) {
                if (textViewList != null && textViewList.size() > 0) {
                    textViewList.get(0).post(new Runnable() {
                        @Override
                        public void run() {
                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) empty_line.getLayoutParams();
                            params.height = (int) ((navigationHeight - textViewList.get(0).getHeight() - iconSize - tabTextTop) / 2);
                            empty_line.setLayoutParams(params);
                            //linearParams.bottomMargin = (int) ((navigationHeight - textViewList.get(0).getHeight() - iconSize - tabTextTop) / 2);
                        }
                    });

                }
            } else {
                linearParams.bottomMargin = (int) centerLayoutBottomMargin;
            }
        }

        centerImage.setId(-1);
        centerImage.setImageResource(centerImageRes);
        centerImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCenterTabClickListener != null) {
                    if (!onCenterTabClickListener.onCenterTabSelectEvent(view)) {
                        if (centerAsFragment)
                            selectTab(tabCount / 2, smoothScroll);
                    }
                } else {
                    if (centerAsFragment)
                        selectTab(tabCount / 2, smoothScroll);
                }
            }
        });

        centerLinearLayout.addView(centerImage);

        if (!TextUtils.isEmpty(centerTextStr)) {
            TextView centerText = new TextView(getContext());
            centerText.setTextSize(textSizeType, centerTextSize);
            LinearLayout.LayoutParams addTextParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            addTextParams.topMargin = (int) centerTextTopMargin;
            centerText.setLayoutParams(addTextParams);
            centerText.setText(centerTextStr);
            centerLinearLayout.addView(centerText);
        }


        AddContainerLayout.addView(centerLinearLayout, linearParams);
    }

    /**
     * 生成普通Tab的布局
     */
    private void addTabItemView(final int position) {
        View itemView = View.inflate(getContext(), R.layout.navigation_tab_layout, null);

        LinearLayout ll_tab_content = itemView.findViewById(R.id.ll_tab_content);
        RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) ll_tab_content.getLayoutParams();

        if (tabContentRule == 0) {
            llParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        } else {
            llParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            llParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            llParams.bottomMargin = tabContentBottomMargin;
        }

        ll_tab_content.setLayoutParams(llParams);


        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        int index = 0;
        if (isCenterAsFragment()) {
            index = position < (tabCount / 2) ? position : position + 1;
        } else {
            index = position;
        }
        final int finalIndex = index;

        if (mode == EasyNavigationBar.NavigationMode.MODE_NORMAL) {
            params.width = getWidth() / tabCount;
        } else if (mode == EasyNavigationBar.NavigationMode.MODE_ADD) {
            params.width = getWidth() / (tabCount + 1);
        } else if (mode == EasyNavigationBar.NavigationMode.MODE_ADD_VIEW) {
            params.width = getWidth() / (tabCount + 1);
        }
        itemView.setTag(R.id.tag_view_position, position);
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTabClickListener != null) {
                    if (currentPosition == position) {
                        onTabClickListener.onTabReSelectEvent(view, currentPosition);
                    }
                    if (!onTabClickListener.onTabSelectEvent(view, position)) {
                        selectTab(finalIndex, smoothScroll);
                    }
                } else {
                    selectTab(finalIndex, smoothScroll);
                }
            }
        });

        itemView.setLayoutParams(params);


        View hintPoint = itemView.findViewById(R.id.red_point);

        //提示红点
        RelativeLayout.LayoutParams hintPointParams = (RelativeLayout.LayoutParams) hintPoint.getLayoutParams();
        hintPointParams.bottomMargin = (int) hintPointTop;
        hintPointParams.width = (int) hintPointSize;
        hintPointParams.height = (int) hintPointSize;
        hintPointParams.leftMargin = (int) hintPointLeft;
        NavigationUtil.setOvalBg(hintPoint, msgPointColor);
        hintPoint.setLayoutParams(hintPointParams);

        //消息红点
        TextView msgPoint = itemView.findViewById(R.id.msg_point_tv);
        msgPoint.setTextSize(textSizeType, msgPointTextSize);
        RelativeLayout.LayoutParams msgPointParams = (RelativeLayout.LayoutParams) msgPoint.getLayoutParams();
        msgPointParams.bottomMargin = (int) msgPointTop;
        msgPointParams.leftMargin = (int) msgPointLeft;
        msgPoint.setLayoutParams(msgPointParams);


        hintPointList.add(hintPoint);
        msgPointList.add(msgPoint);

        TextView text = itemView.findViewById(R.id.tab_text_tv);
        ImageView icon = itemView.findViewById(R.id.tab_icon_iv);

        switch (contentType) {
            case EasyNavigationBar.TabContentType.TYPE_ONLY_IMAGE:
                text.setVisibility(GONE);

                icon.setScaleType(scaleType);
                LayoutParams iconParams = (LayoutParams) icon.getLayoutParams();
                iconParams.width = (int) iconSize;
                iconParams.height = (int) iconSize;
                icon.setLayoutParams(iconParams);
                imageViewList.add(icon);
                icon.setVisibility(VISIBLE);
                break;
            case EasyNavigationBar.TabContentType.TYPE_ONLY_TEXT:
                textViewList.add(text);
                LayoutParams textParams = (LayoutParams) text.getLayoutParams();
                textParams.topMargin = 0;
                text.setLayoutParams(textParams);
                text.setText(titleItems[position]);
                text.setTextSize(textSizeType, tabTextSize);
                text.setVisibility(VISIBLE);

                icon.setVisibility(GONE);
                break;
            default:
                textViewList.add(text);
                LayoutParams textParams2 = (LayoutParams) text.getLayoutParams();
                textParams2.topMargin = (int) tabTextTop;
                text.setLayoutParams(textParams2);
                text.setText(titleItems[position]);
                text.setTextSize(textSizeType, tabTextSize);


                icon.setScaleType(scaleType);
                LayoutParams iconParams2 = (LayoutParams) icon.getLayoutParams();
                iconParams2.width = (int) iconSize;
                iconParams2.height = (int) iconSize;
                icon.setLayoutParams(iconParams2);
                imageViewList.add(icon);

                text.setVisibility(VISIBLE);
                icon.setVisibility(VISIBLE);
                break;
        }

        tabList.add(itemView);
        navigationLayout.addView(itemView);
    }

    /**
     * 切换ViewPager页面
     */
    public void selectTab(int position, boolean smoothScroll, boolean selectPager) {
        if (currentPosition == position)
            return;
        currentPosition = position;
        if (selectPager) {
            if (isViewPager2) {
                if (getViewPager2() != null)
                    getViewPager2().setCurrentItem(position, smoothScroll);
            } else {
                if (getViewPager() != null) {
                    getViewPager().setCurrentItem(position, smoothScroll);
                }
            }
        }

        updateNavigation(true);
    }

    /**
     * 切换ViewPager页面
     */
    public void selectTab(int position, boolean smoothScroll) {
        selectTab(position, smoothScroll, true);
    }

    /**
     * 是否有中间局部
     *
     * @return
     */
    private boolean isAddPage() {
        if (mode == EasyNavigationBar.NavigationMode.MODE_ADD || mode == EasyNavigationBar.NavigationMode.MODE_ADD_VIEW)
            return true;
        return false;
    }


    private void removeNavigationAllView() {

        for (int i = 0; i < AddContainerLayout.getChildCount(); i++) {
            if (AddContainerLayout.getChildAt(i).getTag() == null) {
                AddContainerLayout.removeViewAt(i);
            }
        }

        msgPointList.clear();
        hintPointList.clear();
        imageViewList.clear();
        textViewList.clear();
        tabList.clear();

        navigationLayout.removeAllViews();
    }

    //自定义中间按钮
    public void buildAddViewNavigation() {

        post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < tabCount; i++) {

                    if (i == tabCount / 2) {
                        addCenterTabCustomView(i);
                    }
                    addTabItemView(i);
                }

                selectNormalTabUI(0, false);
                if (onTabLoadListener != null)
                    onTabLoadListener.onTabLoadCompleteEvent();
            }
        });

    }

    /**
     * 添加自定义view到导航中间布局
     *
     * @param i
     */
    private void addCenterTabCustomView(int i) {
        RelativeLayout addItemView = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams addItemParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        addItemParams.width = getWidth() / (tabCount + 1);
        addItemView.setLayoutParams(addItemParams);
        navigationLayout.addView(addItemView);

        final RelativeLayout.LayoutParams linearParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (centerLayoutRule == RULE_CENTER) {
            linearParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        } else if (centerLayoutRule == RULE_BOTTOM) {
            linearParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            if (centerAlignBottom) {
                linearParams.addRule(RelativeLayout.ABOVE, R.id.empty_line);
                if (textViewList != null && textViewList.size() > 0) {
                    textViewList.get(0).post(new Runnable() {
                        @Override
                        public void run() {
                            linearParams.bottomMargin = (int) ((navigationHeight - textViewList.get(0).getHeight() - iconSize - tabTextTop) / 2);
                        }
                    });

                }
            } else {
                linearParams.addRule(RelativeLayout.ABOVE, R.id.empty_line);
                linearParams.bottomMargin = (int) centerLayoutBottomMargin;
            }
        }
        customAddView.setId(-1);
        customAddView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCenterTabClickListener != null) {
                    if (!onCenterTabClickListener.onCenterTabSelectEvent(view)) {
                        if (centerAsFragment)
                            selectTab(tabCount / 2, smoothScroll);
                    }
                } else {
                    if (centerAsFragment)
                        selectTab(tabCount / 2, smoothScroll);
                }
            }
        });

        AddContainerLayout.addView(customAddView, linearParams);
    }


    public ViewPager getViewPager() {
        return mViewPager;
    }


    public void setAddViewLayout(View addViewLayout) {
        FrameLayout.LayoutParams addParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addViewLayout.addView(addViewLayout, addParams);
    }

    public ViewGroup getAddViewLayout() {
        return addViewLayout;
    }


    /**
     * 选择中间Tab UI变化
     */
    private void selectCenterTabUI() {
        for (int i = 0; i < tabCount; i++) {
            switch (contentType) {
                case EasyNavigationBar.TabContentType.TYPE_NORMAL:
                    imageViewList.get(i).setImageResource(normalIconItems[i]);
                    textViewList.get(i).setTextColor(normalTextColor);
                    textViewList.get(i).setText(titleItems[i]);
                case EasyNavigationBar.TabContentType.TYPE_ONLY_IMAGE:
                    imageViewList.get(i).setImageResource(normalIconItems[i]);
                    break;
                case EasyNavigationBar.TabContentType.TYPE_ONLY_TEXT:
                    textViewList.get(i).setTextColor(normalTextColor);
                    textViewList.get(i).setText(titleItems[i]);
                    break;
            }
        }
    }

    /**
     * 选择普通tab UI变化
     *
     * @param position
     */
    private void selectNormalTabUI(int position, boolean showAnim) {
        for (int i = 0; i < tabCount; i++) {
            if (i == position && imageViewList != null) {
//                if (anim != null && showAnim)
//                    YoYo.with(anim).duration(300).playOn(tabList.get(i));
                switch (contentType) {
                    case EasyNavigationBar.TabContentType.TYPE_NORMAL:
                        imageViewList.get(i).setImageResource(selectIconItems[i]);
                        textViewList.get(i).setTextColor(selectTextColor);
                        textViewList.get(i).setText(titleItems[i]);
                        break;
                    case EasyNavigationBar.TabContentType.TYPE_ONLY_IMAGE:
                        imageViewList.get(i).setImageResource(selectIconItems[i]);
                        break;
                    case EasyNavigationBar.TabContentType.TYPE_ONLY_TEXT:
                        textViewList.get(i).setTextColor(selectTextColor);
                        textViewList.get(i).setText(titleItems[i]);
                        break;
                }

            } else {
                switch (contentType) {
                    case EasyNavigationBar.TabContentType.TYPE_NORMAL:
                        imageViewList.get(i).setImageResource(normalIconItems[i]);
                        textViewList.get(i).setTextColor(normalTextColor);
                        textViewList.get(i).setText(titleItems[i]);
                    case EasyNavigationBar.TabContentType.TYPE_ONLY_IMAGE:
                        imageViewList.get(i).setImageResource(normalIconItems[i]);
                        break;
                    case EasyNavigationBar.TabContentType.TYPE_ONLY_TEXT:
                        textViewList.get(i).setTextColor(normalTextColor);
                        textViewList.get(i).setText(titleItems[i]);
                        break;
                }
            }
        }

    }


    /**
     * 设置是否显示小红点
     *
     * @param position 第几个tab
     * @param isShow   是否显示
     */
    public void setHintPoint(int position, boolean isShow) {
        if (hintPointList == null || hintPointList.size() < (position + 1))
            return;
        if (isShow) {
            hintPointList.get(position).setVisibility(VISIBLE);
        } else {
            hintPointList.get(position).setVisibility(GONE);
        }
    }


    /**
     * 设置消息数量
     *
     * @param position 第几个tab
     * @param count    显示的数量  99个以上显示99+  少于1则不显示
     */
    public void setMsgPointCount(int position, int count) {
        if (msgPointList == null || msgPointList.size() < (position + 1))
            return;
        TextView msgPointView = msgPointList.get(position);
        if (count > 99) {
            NavigationUtil.setRoundRectBg(getContext(), msgPointView, (int) msgPointMoreRadius, msgPointColor);
            msgPointView.setText("99+");
            ViewGroup.LayoutParams params = msgPointView.getLayoutParams();
            params.width = (int) msgPointMoreWidth;
            params.height = (int) msgPointMoreHeight;
            msgPointView.setLayoutParams(params);
            msgPointView.setVisibility(VISIBLE);
        } else if (count < 1) {
            msgPointView.setVisibility(GONE);
        } else {
            ViewGroup.LayoutParams params = msgPointView.getLayoutParams();
            params.width = (int) msgPointSize;
            params.height = (int) msgPointSize;
            msgPointView.setLayoutParams(params);
            NavigationUtil.setOvalBg(msgPointView, msgPointColor);
            msgPointView.setText(count + "");
            msgPointView.setVisibility(VISIBLE);
        }
    }

    /**
     * 清除数字消息
     *
     * @param position
     */
    public void clearMsgPoint(int position) {
        if (msgPointList == null || msgPointList.size() < (position + 1))
            return;
        msgPointList.get(position).setVisibility(GONE);
    }

    /**
     * 清除提示红点
     *
     * @param position
     */
    public void clearHintPoint(int position) {
        if (hintPointList == null || hintPointList.size() < (position + 1))
            return;
        hintPointList.get(position).setVisibility(GONE);
    }

    /**
     * 清空所有提示红点
     */
    public void clearAllHintPoint() {
        for (int i = 0; i < hintPointList.size(); i++) {
            hintPointList.get(i).setVisibility(GONE);
        }
    }

    /**
     * 清空所有消息红点
     */
    public void clearAllMsgPoint() {
        for (int i = 0; i < msgPointList.size(); i++) {
            msgPointList.get(i).setVisibility(GONE);
        }
    }

    public interface OnTabClickListener {
        boolean onTabSelectEvent(View view, int position);

        /**
         * 重复点击
         */
        boolean onTabReSelectEvent(View view, int position);
    }

    public interface OnCenterTabSelectListener {
        /**
         * 中间布局点击事件
         */
        boolean onCenterTabSelectEvent(View view);
    }

    public interface OnTabLoadListener {
        /**
         * Tab加载完毕
         */
        void onTabLoadCompleteEvent();
    }


    public EasyNavigationBar centerLayoutHeight(int centerLayoutHeight) {
        this.centerLayoutHeight = NavigationUtil.dip2px(getContext(), centerLayoutHeight);
        return this;
    }

    public EasyNavigationBar scaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        return this;
    }


    public EasyNavigationBar mode(int mode) {
        this.mode = mode;
        return this;
    }

    public EasyNavigationBar hasPadding(boolean hasPadding) {
        this.hasPadding = hasPadding;
        return this;
    }

    public EasyNavigationBar centerIconSize(float centerIconSize) {
        this.centerIconSize = NavigationUtil.dip2px(getContext(), centerIconSize);
        return this;
    }


    public EasyNavigationBar navigationBackground(int navigationBackground) {
        this.navigationBackground = navigationBackground;
        return this;
    }

    public EasyNavigationBar navigationHeight(int navigationHeight) {
        this.navigationHeight = NavigationUtil.dip2px(getContext(), navigationHeight);
        return this;
    }

    public EasyNavigationBar normalTextColor(int normalTextColor) {
        this.normalTextColor = normalTextColor;
        return this;
    }

    public EasyNavigationBar selectTextColor(int selectTextColor) {
        this.selectTextColor = selectTextColor;
        return this;
    }

    public EasyNavigationBar lineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    public EasyNavigationBar lineColor(int lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    public EasyNavigationBar tabTextSize(int tabTextSize) {
        this.tabTextSize = tabTextSize;
        return this;
    }

    public EasyNavigationBar tabTextTop(int tabTextTop) {
        this.tabTextTop = NavigationUtil.dip2px(getContext(), tabTextTop);
        return this;
    }

    public EasyNavigationBar msgPointTextSize(int msgPointTextSize) {
        this.msgPointTextSize = msgPointTextSize;
        return this;
    }

    public EasyNavigationBar msgPointSize(float msgPointSize) {
        this.msgPointSize = NavigationUtil.dip2px(getContext(), msgPointSize);
        return this;
    }

    public EasyNavigationBar msgPointLeft(int msgPointLeft) {
        this.msgPointLeft = NavigationUtil.dip2px(getContext(), msgPointLeft);
        return this;
    }

    public EasyNavigationBar msgPointTop(int msgPointTop) {
        this.msgPointTop = NavigationUtil.dip2px(getContext(), msgPointTop);
        return this;
    }


    public EasyNavigationBar hintPointSize(float hintPointSize) {
        this.hintPointSize = NavigationUtil.dip2px(getContext(), hintPointSize);
        return this;
    }

    public EasyNavigationBar hintPointLeft(int hintPointLeft) {
        this.hintPointLeft = NavigationUtil.dip2px(getContext(), hintPointLeft);
        return this;
    }

    public EasyNavigationBar hintPointTop(int hintPointTop) {
        this.hintPointTop = NavigationUtil.dip2px(getContext(), hintPointTop);
        return this;
    }


    public EasyNavigationBar titleItems(String[] titleItems) {
        this.titleItems = titleItems;
        return this;
    }

    public EasyNavigationBar normalIconItems(int[] normalIconItems) {
        this.normalIconItems = normalIconItems;
        return this;
    }

    public EasyNavigationBar selectIconItems(int[] selectIconItems) {
        this.selectIconItems = selectIconItems;
        return this;
    }

    public EasyNavigationBar fragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
        return this;
    }

    public EasyNavigationBar fragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        return this;
    }

//    public EasyNavigationBar anim(Anim anim) {
//        if (anim != null) {
//            this.anim = anim.getYoyo();
//        } else {
//            this.anim = null;
//        }
//        return this;
//    }

    public EasyNavigationBar centerLayoutRule(int centerLayoutRule) {
        this.centerLayoutRule = centerLayoutRule;
        return this;
    }

    public EasyNavigationBar canScroll(boolean canScroll) {
        this.canScroll = canScroll;
        return this;
    }

    public EasyNavigationBar smoothScroll(boolean smoothScroll) {
        this.smoothScroll = smoothScroll;
        return this;
    }


    public EasyNavigationBar setOnTabClickListener(EasyNavigationBar.OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
        return this;
    }

    public EasyNavigationBar setOnCenterTabClickListener(EasyNavigationBar.OnCenterTabSelectListener onCenterTabClickListener) {
        this.onCenterTabClickListener = onCenterTabClickListener;
        return this;
    }

    public EasyNavigationBar iconSize(float iconSize) {
        this.iconSize = NavigationUtil.dip2px(getContext(), iconSize);
        return this;
    }

    public EasyNavigationBar centerLayoutBottomMargin(int centerLayoutBottomMargin) {
        this.centerLayoutBottomMargin = NavigationUtil.dip2px(getContext(), centerLayoutBottomMargin);
        return this;
    }

    public EasyNavigationBar centerAsFragment(boolean centerAsFragment) {
        this.centerAsFragment = centerAsFragment;
        return this;
    }

    public EasyNavigationBar addCustomView(View customAddView) {
        this.customAddView = customAddView;
        return this;
    }

    public EasyNavigationBar centerTextSize(int centerTextSize) {
        this.centerTextSize = NavigationUtil.dip2px(getContext(), centerTextSize);
        return this;
    }

    public EasyNavigationBar centerNormalTextColor(int centerNormalTextColor) {
        this.centerNormalTextColor = centerNormalTextColor;
        return this;
    }

    public EasyNavigationBar centerSelectTextColor(int centerSelectTextColor) {
        this.centerSelectTextColor = centerSelectTextColor;
        return this;
    }

    public EasyNavigationBar centerTextTopMargin(int centerTextTopMargin) {
        this.centerTextTopMargin = NavigationUtil.dip2px(getContext(), centerTextTopMargin);
        return this;
    }

    public EasyNavigationBar centerAlignBottom(boolean centerAlignBottom) {
        this.centerAlignBottom = centerAlignBottom;
        return this;
    }

    public String[] getTitleItems() {
        return titleItems;
    }

    public int[] getNormalIconItems() {
        return normalIconItems;
    }

    public int[] getSelectIconItems() {
        return selectIconItems;
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

//    public Techniques getAnim() {
//        return anim;
//    }

    public boolean isSmoothScroll() {
        return smoothScroll;
    }

    public EasyNavigationBar.OnTabClickListener getOnTabClickListener() {
        return onTabClickListener;
    }

    public int getIconSize() {
        return iconSize;
    }


    public float getHintPointSize() {
        return hintPointSize;
    }

    public float getHintPointLeft() {
        return hintPointLeft;
    }

    public float getHintPointTop() {
        return hintPointTop;
    }


    public float getMsgPointTextSize() {
        return msgPointTextSize;
    }

    public float getMsgPointSize() {
        return msgPointSize;
    }

    public float getMsgPointLeft() {
        return msgPointLeft;
    }

    public float getMsgPointTop() {
        return msgPointTop;
    }

    public float getTabTextTop() {
        return tabTextTop;
    }

    public float getTabTextSize() {
        return tabTextSize;
    }

    public int getNormalTextColor() {
        return normalTextColor;
    }

    public int getSelectTextColor() {
        return selectTextColor;
    }

    public float getLineHeight() {
        return lineHeight;
    }

    public int getLineColor() {
        return lineColor;
    }

    public float getcenterIconSize() {
        return centerIconSize;
    }

    public float getcenterLayoutHeight() {
        return centerLayoutHeight;
    }

    public int getNavigationBackground() {
        return navigationBackground;
    }

    public float getNavigationHeight() {
        return navigationHeight;
    }

    public boolean isCanScroll() {
        return canScroll;
    }

    public ViewPagerAdapter getAdapter() {
        return adapter;
    }


    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    public int getMode() {
        return mode;
    }

    public LinearLayout getNavigationLayout() {
        return navigationLayout;
    }

    public RelativeLayout getContentView() {
        return contentView;
    }

    public View getLineView() {
        return lineView;
    }

    public ViewGroup getAddLayout() {
        return addViewLayout;
    }

    public float getcenterLayoutBottomMargin() {
        return centerLayoutBottomMargin;
    }

    public int getCenterLayoutRule() {
        return centerLayoutRule;
    }

    public RelativeLayout getAddContainerLayout() {
        return AddContainerLayout;
    }

    public boolean isHasPadding() {
        return hasPadding;
    }

    public boolean isCenterAsFragment() {
        return centerAsFragment && isAddPage();
    }

    public View getCustomAddView() {
        return customAddView;
    }

    public float getcenterTextSize() {
        return centerTextSize;
    }

    public int getcenterNormalTextColor() {
        return centerNormalTextColor;
    }

    public int getcenterSelectTextColor() {
        return centerSelectTextColor;
    }

    public float getcenterTextTopMargin() {
        return centerTextTopMargin;
    }

    public boolean iscenterAlignBottom() {
        return centerAlignBottom;
    }

    public ImageView getCenterImage() {
        return centerImage;
    }

    public EasyNavigationBar textSizeType(int textSizeType) {
        this.textSizeType = textSizeType;
        return this;
    }

    public int getTextSizeType() {
        return textSizeType;
    }

    public float getMsgPointMoreWidth() {
        return msgPointMoreWidth;
    }

    public EasyNavigationBar setMsgPointMoreWidth(float msgPointMoreWidth) {
        this.msgPointMoreWidth = NavigationUtil.dip2px(getContext(), msgPointMoreWidth);
        return this;
    }

    public float getMsgPointMoreHeight() {
        return msgPointMoreHeight;
    }

    public EasyNavigationBar setMsgPointMoreHeight(float msgPointMoreHeight) {
        this.msgPointMoreHeight = NavigationUtil.dip2px(getContext(), msgPointMoreHeight);
        return this;
    }

    public float getMsgPointMoreRadius() {
        return msgPointMoreRadius;
    }

    public EasyNavigationBar setMsgPointMoreRadius(int msgPointMoreRadius) {
        this.msgPointMoreRadius = msgPointMoreRadius;
        return this;
    }

    public int getMsgPointColor() {
        return msgPointColor;
    }

    public EasyNavigationBar setMsgPointColor(int msgPointColor) {
        this.msgPointColor = msgPointColor;
        return this;
    }

    public EasyNavigationBar setOnTabLoadListener(EasyNavigationBar.OnTabLoadListener onTabLoadListener) {
        this.onTabLoadListener = onTabLoadListener;
        return this;
    }

    public List<View> getTabList() {
        return tabList;
    }

    public List<ImageView> getImageViewList() {
        return imageViewList;
    }

    public List<TextView> getTextViewList() {
        return textViewList;
    }
}
