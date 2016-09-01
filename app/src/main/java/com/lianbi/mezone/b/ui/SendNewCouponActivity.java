package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import cn.com.hgh.timeselector.TimeSelectorE;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.DialogCommon;

public class SendNewCouponActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnFocusChangeListener {
    @Bind(R.id.input_coupon_name)
    EditText input_coupon_name;
    @Bind(R.id.coupon_money_num)
    EditText coupon_money_num;
    @Bind(R.id.linearLayout)
    LinearLayout linearLayout;
    @Bind(R.id.changeable_text)
    TextView changeable_text;
    @Bind(R.id.minimum)
    EditText minimum;
    @Bind(R.id.valid_period_from)
    TextView valid_period_from;
    @Bind(R.id.valid_period_to)
    TextView valid_period_to;
    @Bind(R.id.selectable_member)
    TextView selectable_member;
    @Bind(R.id.expected_coupon_send_num)
    TextView expected_coupon_send_num;
    @Bind(R.id.is_need_send_sms)
    CheckBox is_need_send_sms;
    @Bind(R.id.selectable_templet)
    TextView selectable_templet;
    @Bind(R.id.editable_sms_content)
    TextView editable_sms_content;
    @Bind(R.id.expected_sms_send_num_parent)
    LinearLayout expected_sms_send_num_parent;
    @Bind(R.id.expected_sms_send_num)
    TextView expected_sms_send_num;
    @Bind(R.id.send_new_coupon)
    TextView send_new_coupon;

    private final String ENDTIME = "2030-01-01 00:00";
    //可选模板
    private static final int REQUEST_CODE_TEMPLATE_RESULT = 1009;
    //可选会员
    private static final int REQUEST_CODE_MEMBER_RESULT = 1010;
    private String vipPhones;//要发送的会员手机号码拼接字符串
    private String msgId;//短信模板id
    private String coupName;
    private String limitAmt;
    private String coupAmt;
    private String beginTime;
    private String endTime;
    private String info;//短信模板

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_new_coupon, HAVETYPE);
        ButterKnife.bind(this);
        setPageTitle("发送新优惠券");
        initTextWatcher();
        changeText(minimum.length());
    }

    private void initTextWatcher() {
        minimum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                limitAmt = s.toString();
                changeText(s.length());
                replacingWords();
            }
        });
        input_coupon_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                coupName = s.toString();
                replacingWords();
            }
        });
        coupon_money_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                coupAmt = s.toString();
                replacingWords();
            }
        });
    }

    private void changeText(int length) {
        StringBuilder stringBuilder = new StringBuilder("          ");
        for (int i = 0; i < length; i++) {
            stringBuilder.append("  ");
        }
        stringBuilder.append("使用，单次消费限用一张，只支持微信商城使用");
        changeable_text.setText(stringBuilder.toString());
    }

    @Override
    @OnClick({R.id.linearLayout, R.id.valid_period_from, R.id.valid_period_to,
            R.id.selectable_member, R.id.selectable_templet, R.id.send_new_coupon})
    protected void onChildClick(View view) {
        super.onChildClick(view);
        switch (view.getId()) {
            case R.id.linearLayout:
                resetMinmum();
                break;
            case R.id.valid_period_from:// 起始时间
                resetMinmum();
                TimeSelectorE timeSelectorFrom = new TimeSelectorE(SendNewCouponActivity.this,
                        new TimeSelectorE.ResultHandler() {
                            @Override
                            public void handle(String time) {
                                valid_period_from.setText(time);
                                beginTime = time;
                                replacingWords();
                            }
                        }, AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMDHM),
                        ENDTIME);
                timeSelectorFrom.setMode(TimeSelectorE.MODE.YMD);
                timeSelectorFrom.setTitle("起始时间");
                timeSelectorFrom.show();
                break;
            case R.id.valid_period_to:// 结束时间
                resetMinmum();
                TimeSelectorE timeSelectorTo = new TimeSelectorE(this,
                        new TimeSelectorE.ResultHandler() {
                            @Override
                            public void handle(String time) {
                                valid_period_to.setText(time);
                                endTime = time;
                                replacingWords();
                            }
                        }, AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMDHM),
                        ENDTIME);
                timeSelectorTo.setMode(TimeSelectorE.MODE.YMD);
                timeSelectorTo.setTitle("结束时间");
                timeSelectorTo.show();
                break;
            case R.id.selectable_member:
                resetMinmum();
                Intent intentmember = new Intent(SendNewCouponActivity.this, MarketingSelectMemberActivity.class);
                startActivityForResult(intentmember, REQUEST_CODE_MEMBER_RESULT);
                break;
            case R.id.selectable_templet:
                resetMinmum();
                Intent intenttemplate = new Intent(SendNewCouponActivity.this, MarketingSMSexampleActivity.class);
                intenttemplate.putExtra(MarketingSMSexampleActivity.TEMPLATE_TYPE, "P");
                startActivityForResult(intenttemplate, REQUEST_CODE_TEMPLATE_RESULT);
                break;
            case R.id.send_new_coupon:
                sendNewCoupon();
                break;
        }
    }

    private void sendNewCoupon() {
        final String businessId = userShopInfoBean.getBusinessId();
        if (AbStrUtil.isEmpty(businessId)) {
            DialogCommon dialog = new DialogCommon(SendNewCouponActivity.this) {
                @Override
                public void onCheckClick() {
                    dismiss();
                }

                @Override
                public void onOkClick() {
                    startActivity(new Intent(SendNewCouponActivity.this, AddShopActivity.class));
                    dismiss();
                    finish();
                }
            };
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setTextTitle("您还没有自己的商铺，是否去新增商铺？");
            dialog.setTv_dialog_common_ok("新增商铺");
            dialog.setTv_dialog_common_cancel("  取消  ");
            dialog.show();
            return;
        }

        if (AbStrUtil.isEmpty(coupName)) {
            input_coupon_name.requestFocus();
            ContentUtils.showMsg(SendNewCouponActivity.this, input_coupon_name.getHint().toString());
            return;
        }

        if (AbStrUtil.isEmpty(coupAmt)) {
            coupon_money_num.requestFocus();
            ContentUtils.showMsg(SendNewCouponActivity.this, "请输入优惠券金额");
            return;
        }

        limitAmt = minimum.getText().toString();
        if (AbStrUtil.isEmpty(limitAmt)) {
            minimum.requestFocus();
            resetMinmum();
            ContentUtils.showMsg(SendNewCouponActivity.this, "请输入优惠券使用条件,如:满100元");
            return;
        }

        if (AbStrUtil.isEmpty(beginTime)) {
            ContentUtils.showMsg(SendNewCouponActivity.this, "请输入优惠券生效时间");
            return;
        }

        if (AbStrUtil.isEmpty(endTime)) {
            ContentUtils.showMsg(SendNewCouponActivity.this, "请输入优惠券失效时间");
            return;
        }

        if (AbStrUtil.isEmpty(vipPhones)) {
            ContentUtils.showMsg(SendNewCouponActivity.this, "请点击'可选会员'选择接收优惠券的会员");
            return;
        }

        if (is_need_send_sms.isChecked() && AbStrUtil.isEmpty(msgId)) {
            ContentUtils.showMsg(SendNewCouponActivity.this, "请点击'可选模板'选择营销短信的模板");
            return;
        }

        DialogCommon dialog = new DialogCommon(SendNewCouponActivity.this) {
            @Override
            public void onCheckClick() {
                dismiss();
            }

            @Override
            public void onOkClick() {
                try {
                    dismiss();
                    initCommonParameter();
                    okHttpsImp.sendNewCoupon(uuid, "app", reqTime, coupName, coupAmt, limitAmt, vipPhones,
                            beginTime, endTime, is_need_send_sms.isChecked() ? msgId : "",
                            businessId, "", "", "app", userShopInfoBean.getShopName(), new MyResultCallback<String>() {
                                @Override
                                public void onResponseResult(Result result) {

                                }

                                @Override
                                public void onResponseFailed(String msg) {

                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTextTitle("已确认无误！发送新优惠券？");
        dialog.setTv_dialog_common_ok("确定");
        dialog.setTv_dialog_common_cancel("取消");
        dialog.show();
    }

    @Override
    @OnCheckedChanged({R.id.is_need_send_sms})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            selectable_templet.setVisibility(View.VISIBLE);
            editable_sms_content.setVisibility(View.VISIBLE);
            expected_sms_send_num_parent.setVisibility(View.VISIBLE);
        } else {
            selectable_templet.setVisibility(View.GONE);
            editable_sms_content.setVisibility(View.GONE);
            expected_sms_send_num_parent.setVisibility(View.GONE);
        }
    }

    @Override
    @OnFocusChange({R.id.minimum})
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus && (0 == ((TextView) v).getText().length() || 0 == Integer.parseInt(((TextView) v).getText().toString())))
            ((TextView) v).setText("100");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_TEMPLATE_RESULT:
                    if (data != null) {
                        msgId = data.getStringExtra("templateID");
                        info = data.getStringExtra("info");
                        replacingWords();
                    }
                    break;
                case REQUEST_CODE_MEMBER_RESULT:
                    if (data != null) {
                        vipPhones = data.getStringExtra("sendphones");
                        String sendtotal = data.getStringExtra("sendtotal");
                        sendtotal = sendtotal.substring(0, sendtotal.length() - 1);
                        expected_coupon_send_num.setText(sendtotal);
                        expected_sms_send_num.setText(sendtotal);
                    }
                    break;
            }
        }
    }

    private void replacingWords() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!AbStrUtil.isEmpty(info)) {
                    String businessName = userShopInfoBean.getShopName();
                    if (info.contains("{businessName}") && !AbStrUtil.isEmpty(businessName))
                        info = replaceWord("businessName", businessName);

                    if (info.contains("{cardName}") && !AbStrUtil.isEmpty(coupName))
                        info = replaceWord("cardName", coupName.endsWith("优惠券") ? coupName : coupName + "优惠券");

                    if (info.contains("{value}") && !AbStrUtil.isEmpty(coupAmt))
                        info = replaceWord("value", coupAmt);

                    if (info.contains("{condition}") && !AbStrUtil.isEmpty(limitAmt))
                        info = replaceWord("condition", limitAmt);

                    if (info.contains("{beginTime}") && !AbStrUtil.isEmpty(beginTime))
                        info = replaceWord("beginTime", beginTime);

                    if (info.contains("{endTime}") && !AbStrUtil.isEmpty(endTime))
                        info = replaceWord("endTime", endTime);

                    editable_sms_content.setText(info);
                }
            }
        });
    }

    private String replaceWord(String oldString, String newString) {
        String[] array = info.split(oldString);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < (array.length - 1); i++) {
            result.append(array[i]).append(newString);
        }
        result.append(array[array.length - 1]);
        return result.toString();
    }

    private void resetMinmum() {
        if (0 == minimum.getText().length() || 0 == Integer.parseInt(minimum.getText().toString()))
            minimum.setText("100");
    }
}