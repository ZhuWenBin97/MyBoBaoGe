package com.zhuwb.moudle_main.bean;

import java.util.List;

/**
 * ======================================
 * 创建人   ： ZhuWB
 * 创建时间 :2017/12/4 15:42
 * ======================================
 */

public class TypeBean {

    /**
     * code : 1
     * message : [{"msg_type_id":"11","msg_type_name":"招横机","msg_type_state":"1","msg_parent_cid":"2","msg_is_search":"1","msg_type_num":"1"},{"msg_type_id":"15","msg_type_name":"承接横机加工","msg_type_state":"1","msg_parent_cid":"3","msg_is_search":"1","msg_type_num":"2"},{"msg_type_id":"12","msg_type_name":"招套口","msg_type_state":"1","msg_parent_cid":"2","msg_is_search":"1","msg_type_num":"3"},{"msg_type_id":"16","msg_type_name":"承接套口加工","msg_type_state":"1","msg_parent_cid":"3","msg_is_search":"1","msg_type_num":"4"},{"msg_type_id":"13","msg_type_name":"招平车","msg_type_state":"1","msg_parent_cid":"2","msg_is_search":"1","msg_type_num":"5"},{"msg_type_id":"20","msg_type_name":"承接平车加工","msg_type_state":"1","msg_parent_cid":"3","msg_is_search":"1","msg_type_num":"6"},{"msg_type_id":"14","msg_type_name":"招绣花","msg_type_state":"1","msg_parent_cid":"2","msg_is_search":"1","msg_type_num":"7"},{"msg_type_id":"19","msg_type_name":"承接绣花加工","msg_type_state":"1","msg_parent_cid":"3","msg_is_search":"1","msg_type_num":"8"},{"msg_type_id":"34","msg_type_name":"招包头","msg_type_state":"1","msg_parent_cid":"2","msg_is_search":"1","msg_type_num":"9"},{"msg_type_id":"25","msg_type_name":"承接印花加工","msg_type_state":"1","msg_parent_cid":"3","msg_is_search":"1","msg_type_num":"10"},{"msg_type_id":"26","msg_type_name":"承接整烫加工","msg_type_state":"1","msg_parent_cid":"3","msg_is_search":"1","msg_type_num":"11"},{"msg_type_id":"30","msg_type_name":"承接倒毛","msg_type_state":"1","msg_parent_cid":"3","msg_is_search":"1","msg_type_num":"12"},{"msg_type_id":"33","msg_type_name":"承接维修","msg_type_state":"1","msg_parent_cid":"3","msg_is_search":"1","msg_type_num":"13"},{"msg_type_id":"46","msg_type_name":"承接淘宝摄影","msg_type_state":"1","msg_parent_cid":"3","msg_is_search":"1","msg_type_num":"14"},{"msg_type_id":"48","msg_type_name":"承接加工","msg_type_state":"1","msg_parent_cid":"3","msg_is_search":"1","msg_type_num":"15"},{"msg_type_id":"27","msg_type_name":"招聘","msg_type_state":"1","msg_parent_cid":"4","msg_is_search":"3","msg_type_num":"16"},{"msg_type_id":"28","msg_type_name":"招工","msg_type_state":"1","msg_parent_cid":"4","msg_is_search":"3","msg_type_num":"17"},{"msg_type_id":"49","msg_type_name":"找工作","msg_type_state":"1","msg_parent_cid":"5","msg_is_search":"3","msg_type_num":"18"},{"msg_type_id":"31","msg_type_name":"招机修","msg_type_state":"1","msg_parent_cid":"4","msg_is_search":"1","msg_type_num":"19"},{"msg_type_id":"43","msg_type_name":"出租","msg_type_state":"1","msg_parent_cid":"7","msg_is_search":"1","msg_type_num":"20"},{"msg_type_id":"41","msg_type_name":"转租","msg_type_state":"1","msg_parent_cid":"7","msg_is_search":"1","msg_type_num":"21"},{"msg_type_id":"50","msg_type_name":"求租","msg_type_state":"1","msg_parent_cid":"6","msg_is_search":"1","msg_type_num":"22"},{"msg_type_id":"17","msg_type_name":"转让店铺","msg_type_state":"1","msg_parent_cid":"8","msg_is_search":"1","msg_type_num":"23"},{"msg_type_id":"18","msg_type_name":"转让其他","msg_type_state":"1","msg_parent_cid":"8","msg_is_search":"1","msg_type_num":"24"},{"msg_type_id":"44","msg_type_name":"求购","msg_type_state":"1","msg_parent_cid":"10","msg_is_search":"1","msg_type_num":"25"},{"msg_type_id":"35","msg_type_name":"找货源","msg_type_state":"1","msg_parent_cid":"21","msg_is_search":"1","msg_type_num":"26"},{"msg_type_id":"37","msg_type_name":"供应","msg_type_state":"1","msg_parent_cid":"9","msg_is_search":"1","msg_type_num":"27"},{"msg_type_id":"32","msg_type_name":"求助","msg_type_state":"1","msg_parent_cid":"9","msg_is_search":"1","msg_type_num":"28"},{"msg_type_id":"47","msg_type_name":"其他需求","msg_type_state":"1","msg_parent_cid":"9","msg_is_search":"1","msg_type_num":"29"},{"msg_type_id":"45","msg_type_name":"顺风车","msg_type_state":"1","msg_parent_cid":"9","msg_is_search":"1","msg_type_num":"30"},{"msg_type_id":"53","msg_type_name":"投诉举报","msg_type_state":"1","msg_parent_cid":"9","msg_is_search":"1","msg_type_num":"31"},{"msg_type_id":"52","msg_type_name":"播报哥专用红包贴","msg_type_state":"1","msg_parent_cid":"51","msg_is_search":"1","msg_type_num":"32"},{"msg_type_id":"58","msg_type_name":"锁眼订扣","msg_type_state":"1","msg_parent_cid":"3","msg_is_search":"1","msg_type_num":"33"},{"msg_type_id":"42","msg_type_name":"找货","msg_type_state":"1","msg_parent_cid":"1","msg_is_search":"2","msg_type_num":"34"},{"msg_type_id":"39","msg_type_name":"供应女装","msg_type_state":"1","msg_parent_cid":"36","msg_is_search":"2","msg_type_num":"35"},{"msg_type_id":"38","msg_type_name":"供应男装","msg_type_state":"1","msg_parent_cid":"36","msg_is_search":"2","msg_type_num":"36"},{"msg_type_id":"40","msg_type_name":"供应童装","msg_type_state":"1","msg_parent_cid":"36","msg_is_search":"2","msg_type_num":"37"},{"msg_type_id":"55","msg_type_name":"供应中老年装","msg_type_state":"1","msg_parent_cid":"36","msg_is_search":"2","msg_type_num":"38"},{"msg_type_id":"61","msg_type_name":"叉车出租","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"1","msg_type_num":"41"},{"msg_type_id":"62","msg_type_name":"承接制版","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"1","msg_type_num":"42"},{"msg_type_id":"63","msg_type_name":"招电商人才","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"3","msg_type_num":"43"},{"msg_type_id":"64","msg_type_name":"招店长营业员","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"3","msg_type_num":"44"},{"msg_type_id":"65","msg_type_name":"招学员学徒","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"3","msg_type_num":"45"},{"msg_type_id":"66","msg_type_name":"采购皮衣皮草","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"4","msg_type_num":"46"},{"msg_type_id":"67","msg_type_name":"供应皮衣皮草","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"4","msg_type_num":"47"},{"msg_type_id":"68","msg_type_name":"采购羊绒衫","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"4","msg_type_num":"48"},{"msg_type_id":"69","msg_type_name":"供应羊绒衫","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"4","msg_type_num":"49"},{"msg_type_id":"70","msg_type_name":"采购貂绒衫","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"4","msg_type_num":"50"},{"msg_type_id":"71","msg_type_name":"供应貂绒衫","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"4","msg_type_num":"51"},{"msg_type_id":"83","msg_type_name":"采购驼绒牦牛绒衫","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"4","msg_type_num":"52"},{"msg_type_id":"84","msg_type_name":"供应驼绒牦牛绒衫","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"4","msg_type_num":"53"},{"msg_type_id":"72","msg_type_name":"采购毛呢双面呢大衣","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"4","msg_type_num":"54"},{"msg_type_id":"73","msg_type_name":"供应毛呢双面呢大衣","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"4","msg_type_num":"55"},{"msg_type_id":"74","msg_type_name":"品牌工厂店","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"4","msg_type_num":"56"},{"msg_type_id":"75","msg_type_name":"品牌招募代理商","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"4","msg_type_num":"57"},{"msg_type_id":"76","msg_type_name":"品牌订货会","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"4","msg_type_num":"58"},{"msg_type_id":"77","msg_type_name":"寻人启事","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"5","msg_type_num":"59"},{"msg_type_id":"78","msg_type_name":"遗失财物","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"5","msg_type_num":"60"},{"msg_type_id":"80","msg_type_name":"找猫找狗","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"5","msg_type_num":"62"},{"msg_type_id":"79","msg_type_name":"失物招领","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"5","msg_type_num":"62"},{"msg_type_id":"81","msg_type_name":"美食信息","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"6","msg_type_num":"63"},{"msg_type_id":"82","msg_type_name":"土特产","msg_type_state":"1","msg_parent_cid":"200","msg_is_search":"6","msg_type_num":"64"}]
     */

    private int code;
    private List<MessageBean> message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * msg_type_id : 11
         * msg_type_name : 招横机
         * msg_type_state : 1
         * msg_parent_cid : 2
         * msg_is_search : 1
         * msg_type_num : 1
         */

        private String msg_type_id;
        private String msg_type_name;
        private String msg_type_state;
        private String msg_parent_cid;
        private String msg_is_search;
        private String msg_type_num;

        public String getMsg_type_id() {
            return msg_type_id;
        }

        public void setMsg_type_id(String msg_type_id) {
            this.msg_type_id = msg_type_id;
        }

        public String getMsg_type_name() {
            return msg_type_name;
        }

        public void setMsg_type_name(String msg_type_name) {
            this.msg_type_name = msg_type_name;
        }

        public String getMsg_type_state() {
            return msg_type_state;
        }

        public void setMsg_type_state(String msg_type_state) {
            this.msg_type_state = msg_type_state;
        }

        public String getMsg_parent_cid() {
            return msg_parent_cid;
        }

        public void setMsg_parent_cid(String msg_parent_cid) {
            this.msg_parent_cid = msg_parent_cid;
        }

        public String getMsg_is_search() {
            return msg_is_search;
        }

        public void setMsg_is_search(String msg_is_search) {
            this.msg_is_search = msg_is_search;
        }

        public String getMsg_type_num() {
            return msg_type_num;
        }

        public void setMsg_type_num(String msg_type_num) {
            this.msg_type_num = msg_type_num;
        }
    }
}
