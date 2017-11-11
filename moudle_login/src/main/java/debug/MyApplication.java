package debug;

import com.alibaba.android.arouter.launcher.ARouter;
import com.guiying.module.common.base.BaseApplication;

/**
 * Created by zhang chen yang on 2017/11/2 0002.
 */

public class MyApplication extends BaseApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
    }
}
