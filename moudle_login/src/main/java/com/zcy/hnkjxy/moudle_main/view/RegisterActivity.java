package com.zcy.hnkjxy.moudle_main.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zcy.hnkjxy.customview.TimeButton;
import com.zcy.hnkjxy.moudle_main.R;
import com.zcy.hnkjxy.moudle_main.compoent.DaggerRegisterCompoent;
import com.zcy.hnkjxy.moudle_main.contract.RegisterContract;
import com.zcy.hnkjxy.moudle_main.module.RegisterModule;
import com.zcy.hnkjxy.moudle_main.presenter.RegisterPresenter;

import javax.inject.Inject;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View, View.OnClickListener {
    @Inject
    public RegisterPresenter presenter;
    private ImageView registrationBack;
    /**
     * 请输入你的手机号码
     */
    private EditText rPhonenum;
    /**
     * 获取验证码
     */
    private TimeButton rVerificationBtn;

    /**
     * 请输验证码
     */
    private EditText rVerification;

    /**
     * 请输入6-16位密码
     */
    private EditText rPassword;
    /**
     * 请输入微信号码(必填)
     */
    private EditText rWechatNum;
    /**
     * 请输入QQ号码(选填)
     */
    private EditText rQqNum;
    /**
     * 注  册
     */
    private Button registerBtn;
    /**
     * 《播报哥服务条款与用户须知》
     */
    private TextView rPolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
     DaggerRegisterCompoent.builder().registerModule(new RegisterModule(this)).build().inject(this);
    }

    @Override
    public String getPhoneNumber() {
        return rPhonenum.getText().toString().trim();
    }

    @Override
    public String getCheckCode() {
        return rVerification.getText().toString().trim();
    }

    @Override
    public String getPassWorld() {
        return rPassword.getText().toString().trim();
    }

    @Override
    public String getWeChatId() {
        return rWechatNum.getText().toString().trim();
    }

    @Override
    public String getQQId() {
        return rQqNum.getText().toString().trim();
    }

    @Override
    public void clearCheckCode() {
        rVerification.setText("");
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startButtonTime() {
        rVerificationBtn.goRun();
    }

    private void initView() {
        registrationBack = (ImageView) findViewById(R.id.registration_back);
        rPhonenum = (EditText) findViewById(R.id.r_phonenum);
        rVerificationBtn = (TimeButton) findViewById(R.id.r_verification_btn);
        rVerification = (EditText) findViewById(R.id.r_verification);
        rPassword = (EditText) findViewById(R.id.r_password);
        rWechatNum = (EditText) findViewById(R.id.r_wechat_num);
        rQqNum = (EditText) findViewById(R.id.r_qq_num);
        registerBtn = (Button) findViewById(R.id.register_btn);
        rPolicy = (TextView) findViewById(R.id.r_policy);
        rVerificationBtn.setOnClickListener(new TimeButton.OnClickListener() {
            @Override
            public void onClick() {
                presenter.getVer();
            }
        });
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        presenter.doRegister();
    }
}
