package com.zcy.hnkjxy.moudle_main.bean;

/**
 * Created by zhang chen yang on 2017/11/2 0002.
 * 基础用户
 */
public class BasisUser {
    //账号
    private String account;
    //手机号
    private String phoneNum;
    //验证码
    private String verificationNum;
    //密码
    private String passWorld;
    //微信号
    private String weChatNum;
    //qq号
    private String qqNum;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getVerificationNum() {
        return verificationNum;
    }

    public void setVerificationNum(String verificationNum) {
        this.verificationNum = verificationNum;
    }

    public String getPassWorld() {
        return passWorld;
    }

    public void setPassWorld(String passWorld) {
        this.passWorld = passWorld;
    }

    public String getWeChatNum() {
        return weChatNum;
    }

    public void setWeChatNum(String weChatNum) {
        this.weChatNum = weChatNum;
    }

    public String getQqNum() {
        return qqNum;
    }

    public void setQqNum(String qqNum) {
        this.qqNum = qqNum;
    }
}
