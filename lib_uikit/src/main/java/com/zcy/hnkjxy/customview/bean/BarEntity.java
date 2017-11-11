package com.zcy.hnkjxy.customview.bean;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/11 19:52
 */

public class BarEntity {

    /**
     * 导航栏文字
     */
    private String tableText;

    /**
     * 选中后tableid
     */
    private int tableSelectResId;

    /**
     * 未选中tableid
     */
    private int tableUnSelectResId;

    public BarEntity(String tableText, int tableSelectResId, int tableUnSelectResId) {
        this.tableText = tableText;
        this.tableSelectResId = tableSelectResId;
        this.tableUnSelectResId = tableUnSelectResId;
    }

    public String getTableText() {
        return tableText;
    }

    public void setTableText(String tableText) {
        this.tableText = tableText;
    }

    public int getTableSelectResId() {
        return tableSelectResId;
    }

    public void setTableSelectResId(int tableSelectResId) {
        this.tableSelectResId = tableSelectResId;
    }

    public int getTableUnSelectResId() {
        return tableUnSelectResId;
    }

    public void setTableUnSelectResId(int tableUnSelectResId) {
        this.tableUnSelectResId = tableUnSelectResId;
    }
}
