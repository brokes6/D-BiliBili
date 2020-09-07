package com.example.dildil.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.dildil.abstractclass.Constants;
import com.example.dildil.login_page.bean.LoginBean;

import java.util.HashSet;
import java.util.Locale;

import static com.example.dildil.abstractclass.Constants.SpKey.TAG_LANGUAGE;


/**
 * 本地SharePreference工具
 * <p>
 * Created By Rikka on 2019/7/12
 */
public class SharePreferenceUtil {
    private static final String TAG = "SharePreferenceUtil";

    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;
    private static SharePreferenceUtil mInstance;
    private Locale systemCurrentLocal = Locale.CHINESE;

    private SharePreferenceUtil() {
    }

    @SuppressLint("CommitPrefEdits")
    private static void init(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(Constants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        }
        editor = sp.edit();
    }

    public static SharePreferenceUtil getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SharePreferenceUtil.class) {
                if (mInstance == null) {
                    init(context);
                    mInstance = new SharePreferenceUtil();
                }
            }
        }
        return mInstance;
    }

    int getSelectLanguage() {
        return getInt(TAG_LANGUAGE, 0);
    }

    Locale getSystemCurrentLocal() {
        return systemCurrentLocal;
    }


    /**
     * 保存用户的信息以及电话号码（因为bean里的电话号码要处理字符串，所以这里直接暴力传比较高效）
     * 这里有点小问题，新创建的账号，bean.getBindings().size()为 1，之前的老号size为3（不清楚为啥）
     * 所以 新账号会无法保存登录状态
     * @param
     */
    public void saveUserInfo(LoginBean bean, String account) {
//        if (bean.getBindings().size() > 0) {
//            saveAuthToken(bean.getBindings().get(0).getTokenJsonStr());
//        }
        saveAccountNum(account);
        saveString(Constants.SpKey.USER_INFO, GsonUtil.toJson(bean));
    }

    public String getUserInfo(String defaultValue) {
        return getString(Constants.SpKey.USER_INFO, defaultValue);
    }

    private void saveAccountNum(String phoneNumber) {
        saveString(Constants.SpKey.PHONE_NUMBER, phoneNumber);
    }

    public String getAccountNum() {
        return getString(Constants.SpKey.PHONE_NUMBER, "");
    }

    private void saveAuthToken(String token) {
        saveString(Constants.SpKey.AUTH_TOKEN, token);
    }

    public void setCookies(String name, HashSet<String> cookies){
        saveHashSet(name,cookies);
    }

    public HashSet<String> getCookies(String key){
        return (HashSet<String>) sp.getStringSet(key,null);
    }

    public void remove(String key){
        editor.remove(key).apply();
    }
    /**
     * 获取AuthToken
     *
     * @param defaultValue
     * @return
     */
    public String getAuthToken(String defaultValue) {
        return getString(Constants.SpKey.AUTH_TOKEN, defaultValue);
    }


    private String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    private void saveLong(String key, long values) {
        editor.putLong(key, values);
    }

    private long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    private int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    private void saveString(String key, String value) {
        editor.putString(key, value).apply();
    }
    private void saveHashSet(String name, HashSet<String> cookies){
        editor.putStringSet(name,cookies).apply();
    }
}
