package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
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
    private String businessId;
    private String businessName;
    private String vipPhones;//要发送的会员手机号码拼接字符串
    private String msgId;//短信模板id
    private String coupName;
    private String limitAmt;
    private String coupAmt;
    private String beginTime;
    private String endTime;
    private String[] templetSplitedArrs;
    private ArrayList<String> replacedStrs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_new_coupon, HAVETYPE);
        ButterKnife.bind(this);
        setPageTitle("发送新优惠券");
        limitAmt = minimum.getText().toString();
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
                replacingWords("condition", limitAmt);
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
                String ss = s.toString();
                if (s.length() == 0) {
                    coupName = "";
                } else {
                    coupName = ss.endsWith("优惠券") ? ss : ss + "优惠券";
                }
                replacingWords("cardName", coupName);
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
                replacingWords("value", coupAmt);
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
                                replacingWords("beginTime", beginTime);
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
                                replacingWords("endTime", endTime);
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

    @Override
    protected void onResume() {
        super.onResume();
        businessId = userShopInfoBean.getBusinessId();
        businessName = userShopInfoBean.getShopName();
        if (AbStrUtil.isEmpty(businessId) || AbStrUtil.isEmpty(businessName)) {
            DialogCommon dialog = new DialogCommon(SendNewCouponActivity.this) {
                @Override
                public void onCheckClick() {
                    dismiss();
                    finish();
                }

                @Override
                public void onOkClick() {
                    dismiss();
                    startActivity(new Intent(SendNewCouponActivity.this, AddShopActivity.class));
                }
            };
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setTextTitle("您还没有自己的商铺，是否去新增商铺？");
            dialog.setTv_dialog_common_ok("新增商铺");
            dialog.setTv_dialog_common_cancel("  取消  ");
            dialog.show();
        }
    }

    private void sendNewCoupon() {
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
                dismiss();
                initCommonParameter();
                try {
                    okHttpsImp.sendNewCoupon(uuid, "app", reqTime, coupName, coupAmt, limitAmt, vipPhones,
                            beginTime, endTime, is_need_send_sms.isChecked() ? msgId : "",
                            businessId, "", "", "app", businessName, new MyResultCallback<String>() {
                                @Override
                                public void onResponseResult(Result result) {
                                    if (result != null) {
                                        if (result.getCode() == 0) {
                                            DialogCommon dialog = new DialogCommon(SendNewCouponActivity.this) {
                                                @Override
                                                public void onCheckClick() {
                                                    finish();
                                                }

                                                @Override
                                                public void onOkClick() {
                                                    dismiss();
                                                    coupName = "";
                                                    input_coupon_name.setText(coupName);
                                                    coupAmt = "";
                                                    coupon_money_num.setText(coupAmt);
                                                    minimum.setText("100");
                                                    vipPhones = "";
                                                    expected_coupon_send_num.setText("0");
                                                    expected_sms_send_num.setText("0");
                                                    beginTime = "";
                                                    valid_period_from.setText(beginTime);
                                                    endTime = "";
                                                    valid_period_to.setText(endTime);
                                                    msgId = "";
                                                    is_need_send_sms.setChecked(false);
                                                    templetSplitedArrs = new String[0];
                                                    replacedStrs.clear();
                                                    editable_sms_content.setText("");
                                                }
                                            };
                                            dialog.setCancelable(false);
                                            dialog.setCanceledOnTouchOutside(false);
                                            dialog.setTextTitle("发送成功");
                                            dialog.setTv_dialog_common_ok("再发一条");
                                            dialog.setTv_dialog_common_cancel("查看详情");
                                            dialog.show();
                                        } else {
                                            ContentUtils.showMsg(SendNewCouponActivity.this, result.getMsg());
                                        }
                                    }
                                }

                                @Override
                                public void onResponseFailed(String msg) {
                                    ContentUtils.showMsg(SendNewCouponActivity.this, "发送失败，请重试");
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTextTitle("已确认无误？");
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
                        initTemplate(data.getStringExtra("info"));
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

    private void initTemplate(String template) {
        replacedStrs.clear();
        ArrayList<String> arrayList = new ArrayList<>();
        String[] a0 = template.split("\\{");
        for (int i = 0; i < a0.length; i++) {
            String[] a1 = a0[i].split("\\}");
            for (int j = 0; j < a1.length; j++) {
                if (a1[j].equals("businessName"))
                    arrayList.add(businessName);
                else
                    arrayList.add(a1[j]);
            }
        }
        templetSplitedArrs = new String[arrayList.size()];
        arrayList.toArray(templetSplitedArrs);
        replacedStrs = arrayList;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < replacedStrs.size(); i++) {
            String s = replacedStrs.get(i);
            if (s.equals("cardName") && !AbStrUtil.isEmpty(coupName)) {
                replacedStrs.remove(i);
                replacedStrs.add(i, coupName);
            }
            if (s.equals("value") && !AbStrUtil.isEmpty(coupAmt)) {
                replacedStrs.remove(i);
                replacedStrs.add(i, coupAmt);
            }
            if (s.equals("condition") && !AbStrUtil.isEmpty(limitAmt)) {
                replacedStrs.remove(i);
                replacedStrs.add(i, limitAmt);
            }
            if (s.equals("beginTime") && !AbStrUtil.isEmpty(beginTime)) {
                replacedStrs.remove(i);
                replacedStrs.add(i, beginTime);
            }
            if (s.equals("endTime") && !AbStrUtil.isEmpty(endTime)) {
                replacedStrs.remove(i);
                replacedStrs.add(i, endTime);
            }
            sb.append(replacedStrs.get(i));
        }
        editable_sms_content.setText(sb.toString());
    }

    private void resetMinmum() {
        if (0 == minimum.getText().length() || 0 == Integer.parseInt(minimum.getText().toString()))
            minimum.setText("100");
    }

    private void replacingWords(String befor, String after) {
        if (templetSplitedArrs == null || templetSplitedArrs.length == 0 ||
                replacedStrs == null || replacedStrs.isEmpty())
            return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < templetSplitedArrs.length; i++) {
            String s = templetSplitedArrs[i];
            if (befor.equals(s)) {
                replacedStrs.remove(i);
                if (AbStrUtil.isEmpty(after)) {
                    replacedStrs.add(i, befor);
                } else {
                    replacedStrs.add(i, after);
                }
            }
            sb.append(replacedStrs.get(i));
        }
        editable_sms_content.setText(sb.toString());
    }
}