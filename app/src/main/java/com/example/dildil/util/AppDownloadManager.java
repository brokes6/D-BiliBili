package com.example.dildil.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.maning.updatelibrary.InstallUtils;

import java.lang.ref.WeakReference;

public class AppDownloadManager {
    private WeakReference<Activity> weakReference;
    private DownloadManager mDownloadManager;
    private OnUpdateListener mUpdateListener;
    public static final String APK_SAVE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/dildil.apk";
    private InstallUtils.DownloadCallBack downloadCallBack;
    private String apkDownloadPath;

    public AppDownloadManager(Activity activity) {
        weakReference = new WeakReference<Activity>(activity);
        mDownloadManager = (DownloadManager) weakReference.get().getSystemService(Context.DOWNLOAD_SERVICE);
        initCallBack();
    }

    public void setUpdateListener(OnUpdateListener mUpdateListener) {
        this.mUpdateListener = mUpdateListener;
    }

    public void downloadApk(String apkUrl) {
        InstallUtils.with(weakReference.get())
                //必须-下载地址
                .setApkUrl(apkUrl)
//                //非必须-下载保存的文件的完整路径+name.apk
//                .setApkPath(APK_SAVE_PATH)
                //非必须-下载回调
                .setCallBack(downloadCallBack)
                //开始下载
                .startDownload();
    }

    private void initCallBack() {
        downloadCallBack = new InstallUtils.DownloadCallBack() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(String path) {
                apkDownloadPath = path;
                //先判断有没有安装权限
                InstallUtils.checkInstallPermission(weakReference.get(), new InstallUtils.InstallPermissionCallBack() {
                    @Override
                    public void onGranted() {
                        //去安装APK
                        installApp(weakReference.get(), apkDownloadPath);
                    }

                    @Override
                    public void onDenied() {
                        //弹出弹框提醒用户
                        AlertDialog alertDialog = new AlertDialog.Builder(weakReference.get())
                                .setTitle("温馨提示")
                                .setMessage("必须授权才能安装APK，请设置允许安装")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //打开设置页面
                                        InstallUtils.openInstallPermissionSetting(weakReference.get(), new InstallUtils.InstallPermissionCallBack() {
                                            @Override
                                            public void onGranted() {
                                                //去安装APK
                                                installApp(weakReference.get(), apkDownloadPath);
                                            }

                                            @Override
                                            public void onDenied() {
                                                //还是不允许咋搞？
                                                Toast.makeText(weakReference.get(), "不允许安装咋搞？强制更新就退出应用程序吧！", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                })
                                .create();
                        alertDialog.show();
                    }
                });
            }

            @Override
            public void onLoading(long l, long l1) {
                mUpdateListener.update((int) l1, (int) l);
            }

            @Override
            public void onFail(Exception e) {

            }

            @Override
            public void cancle() {

            }
        };
    }

    public static void installApp(Activity context, String url) {
        InstallUtils.installAPK(context, url, new InstallUtils.InstallCallBack() {
            @Override
            public void onSuccess() {
                //onSuccess：表示系统的安装界面被打开
                //防止用户取消安装，在这里可以关闭当前应用，以免出现安装被取消
                Toast.makeText(context, "正在安装程序", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(Exception e) {
                Log.e("why", "onFail:出现错误 " + e.getMessage());
                //安装出现异常，这里可以提示用用去用浏览器下载安装
            }
        });
    }


    public interface OnUpdateListener {

        void update(int currentByte, int totalByte);
    }

    public interface AndroidOInstallPermissionListener {
        void permissionSuccess();

        void permissionFail();
    }
}
