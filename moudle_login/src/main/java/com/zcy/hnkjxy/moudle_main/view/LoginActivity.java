package com.zcy.hnkjxy.moudle_main.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.guiying.module.common.base.BaseActivity;
import com.zcy.hnkjxy.moudle_main.R;
import com.zcy.hnkjxy.moudle_main.compoent.DaggerLoginComponent;
import com.zcy.hnkjxy.moudle_main.contract.LoginContract;
import com.zcy.hnkjxy.moudle_main.module.LoginModule;
import com.zcy.hnkjxy.moudle_main.presenter.LoginPresenter;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginContract.View {
    /**
     * 手机号/播报号/微信号
     */
    private EditText mLUserIDEdit;
    /**
     * 请输入密码
     */
    private EditText mLUserPWEdit;
    /**
     * 记住用户
     */
    private CheckBox mRemember;
    /**
     * 登  录
     */
    private Button mLoginBtn;
    //
    @Inject
    public LoginPresenter mPresenter;
    //登录等待
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        //依赖注入
             DaggerLoginComponent.builder().loginModule(new LoginModule(this)).build().inject(this);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setTitle("登录中。。。请稍等!");
    }

    private void initView() {
        mLUserIDEdit = (EditText) findViewById(R.id.l_userID_edit);
        mLUserPWEdit = (EditText) findViewById(R.id.l_userPW_edit);
        mRemember = (CheckBox) findViewById(R.id.remember);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.login_btn) {
            mPresenter.login();
        }

    }

    @Override
    public String getUserName() {
        return mLUserIDEdit.getText().toString().trim();
    }

    @Override
    public String getUserPassWorld() {
        return mLUserPWEdit.getText().toString().trim();
    }

    @Override
    public void clearUserName() {
        mLUserIDEdit.setText("");
    }

    @Override
    public void clearUserPassWorld() {
        mLUserPWEdit.setText("");
    }

    @Override
    public void showProgress() {
        dialog.show();
    }

    @Override
    public void dismissProgress() {
        dialog.dismiss();
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFail() {
        Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
    }
}
