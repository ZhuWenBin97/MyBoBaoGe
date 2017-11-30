package com.zcy.hnkjxy.moudle_app;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.guiying.module.common.base.BaseApplication;
import com.guiying.module.common.utils.Utils;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.FunctionOptions;
import com.luck.picture.lib.model.PictureConfig;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.collector.CrashReportData;
import org.acra.sender.EmailIntentSender;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

/**
 * Created by zhang chen yang on 2017/11/2 0002.
 */
//@ReportsCrashes(
//        mailTo = "911152556@qq.com",
//        mode = ReportingInteractionMode.DIALOG,
//        customReportContent = {
//                ReportField.APP_VERSION_NAME,
//                ReportField.ANDROID_VERSION,
//                ReportField.PHONE_MODEL,
//                ReportField.CUSTOM_DATA,
//                ReportField.BRAND,
//                ReportField.STACK_TRACE,
//                ReportField.LOGCAT,
//                ReportField.USER_COMMENT},
//        resToastText = R.string.res_toast_text,
//        resDialogText = R.string.res_dialog_text,
//        resDialogTitle = R.string.res_dialog_title)
public class MyApplication extends BaseApplication {
    private RefWatcher refWatcher;


    @Override
    public void onCreate() {
        super.onCreate();
        if (Utils.isAppDebug()) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        //崩溃日志记录初始化
        ACRA.init(this);
        LeakCanary.install(this);

        FunctionOptions options = new FunctionOptions.Builder()
                .setType(FunctionConfig.TYPE_IMAGE)
                .setCompress(true)
                .setGrade(Luban.THIRD_GEAR)
                .setNumComplete(true)
                .create();

        PictureConfig.getInstance().init(options);
    }


    /**
     * 发送崩溃日志
     */
//    private class CrashReportSender implements ReportSender {
//        CrashReportSender() {
//            ACRA.getErrorReporter().putCustomData("PLATFORM", "ANDROID");
//            ACRA.getErrorReporter().putCustomData("BUILD_ID", android.os.Build.ID);
//            ACRA.getErrorReporter().putCustomData("DEVICE_NAME", android.os.Build.PRODUCT);
//        }
//
//        @Override
//        public void send(Context context, CrashReportData crashReportData) throws ReportSenderException {
//            EmailIntentSender emailSender = new EmailIntentSender(null);
//            emailSender.send(context, crashReportData);
//        }
//    }
}
