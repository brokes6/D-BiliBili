package com.example.dildil.util;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.dildil.R;

/**
 * 兼容Android 8。0 APP 在线更新，权限申请界面
 * Created by zhouwei on 2018/3/23.
 */

public class AndroidOPermissionActivity extends AppCompatActivity {
    public static final int INSTALL_PACKAGES_REQUESTCODE = 1;
    private AlertDialog mAlertDialog;
    public static AppDownloadManager.AndroidOInstallPermissionListener sListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 弹窗

        if (Build.VERSION.SDK_INT >= 26) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, INSTALL_PACKAGES_REQUESTCODE);
        } else {
            finish();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case INSTALL_PACKAGES_REQUESTCODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (sListener != null) {
                        sListener.permissionSuccess();
                        finish();
                    }
                } else {
                    //startInstallPermissionSettingActivity();
                    showDialog();
                }
                break;

        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("为了正常升级 xxx APP，请点击设置按钮，允许安装未知来源应用，本功能只限用于 xxx APP版本升级");
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startInstallPermissionSettingActivity();
                mAlertDialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (sListener != null) {
                    sListener.permissionFail();
                }
                mAlertDialog.dismiss();
                finish();
            }
        });
        mAlertDialog = builder.create();
        mAlertDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // 授权成功
            if (sListener != null) {
                sListener.permissionSuccess();
            }
        } else {
            // 授权失败
            if (sListener != null) {
                sListener.permissionFail();
            }
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sListener = null;
    }
}