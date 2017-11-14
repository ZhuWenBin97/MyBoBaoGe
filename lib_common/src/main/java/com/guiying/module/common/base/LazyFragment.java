package com.guiying.module.common.base;

import android.support.v4.app.Fragment;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/13 16:35
 */

public abstract class LazyFragment extends Fragment {

    protected boolean isVisible;


    /**
     * 在这里实现Fragment数据加载
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            isVisible=true;
            onVisible();
        }else {
            isVisible=false;
            onInvisible();
        }


    }

    protected abstract void onInvisible();

    protected  void onVisible(){
        lazyload();
    }

    protected abstract void lazyload();
}
