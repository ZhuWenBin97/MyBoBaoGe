package com.zhuwb.moudle_main.contract;

import com.youth.banner.Banner;
import com.zhuwb.moudle_main.bean.BannerMessage;
import com.zhuwb.moudle_main.bean.ListMessageitem;

import java.util.List;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/21 10:23
 */

public class MessageContract {

    /**
     * View层接口
     */
    public interface IFragmentView {

        /**
         * 显示list数据
         */
        void showList(List<ListMessageitem.MessageBean> messageBeans);

        /**
         * 显示轮播图
         *
         * @param listImgs
         */
        void showBanner(List<String> listImgs);
    }


    /**
     * P层接口
     */
    public interface IFragmentPresenter {

        /**
         * 列表数据
         */
        void loadListMessage(Integer curPage);

        /**
         * 轮播图数据
         *
         * @param banner
         */
        void loadBannerMessage(Banner banner);
    }


    /**
     * 网络请求接口
     */
    public interface IFragmentModel {

        /**
         * 网络请求接口
         *
         * @param url
         */
        void requestNetwork(String url);

        /**
         * 数据监听
         */
        interface OnLoadListener {

            /**
             * 失败
             *
             * @param e
             */
            void onFail(Exception e);

            /**
             * 成功
             *
             * @param json
             */
            void onSucced(String json);

        }
    }
}
