package com.lianbi.mezone.b.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;

import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lzy.okgo.request.BaseRequest;
import com.xizhi.mezone.b.R;

import java.io.UnsupportedEncodingException;

import cn.com.hgh.service.BluetoothService;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.PicFromPrintUtils;
import cn.com.hgh.utils.Result;

/**
 * Created by zemin.zheng on 2016/11/1.
 */

public class BluetoothBaseActivity extends BaseActivity {

    protected boolean isRequestingPrintTicketData;//是否正在进行请求小票数据网络请求

    // Message types sent from the BluetoothService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1001;
    private static final int REQUEST_ENABLE_BT = 1002;

    // Name of the connected device
    private String mConnectedDeviceName = null;

    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;

    // Member object for the services
    public static BluetoothService mService = null;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    ProgressDialog dialog = new ProgressDialog(BluetoothBaseActivity.this);
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            dialog.dismiss();
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.setCancelable(false);
                            dialog.setIndeterminate(false);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setMessage("正在连接...");
                            dialog.show();
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            ContentUtils.showMsg(BluetoothBaseActivity.this, "无连接");
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    //byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    //String writeMessage = new String(writeBuf);
                    break;
                case MESSAGE_READ:
                    //byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    //String readMessage = new String(readBuf, 0, msg.arg1);
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    ContentUtils.showMsg(BluetoothBaseActivity.this, "连接至"
                            + mConnectedDeviceName);
                    break;
                case MESSAGE_TOAST:
                    ContentUtils.showMsg(BluetoothBaseActivity.this, msg.getData().getString(TOAST));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBluetoothExist();
    }

    protected void checkBluetoothExist() {
        if (mBluetoothAdapter == null) {
            ContentUtils.showMsg(BluetoothBaseActivity.this, "您的设备不支持蓝牙");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mBluetoothAdapter.isEnabled()) {
            //打开蓝牙
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        if (mService == null) {
            mService = new BluetoothService(this, mHandler);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras()
                            .getString(BluetoothDeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // Get the BLuetoothDevice object
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    // Attempt to connect to the device
                    mService.connect(device);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    ContentUtils.showMsg(BluetoothBaseActivity.this, "蓝牙已打开");
                    // Launch the DeviceListActivity to see devices and do scan
                    Intent serverIntent = new Intent(this, BluetoothDeviceListActivity.class);
                    startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    ContentUtils.showMsg(BluetoothBaseActivity.this, "蓝牙没有打开");
                }
        }
    }

    /**
     * 打印
     *
     * @param message
     */
    protected void sendMessage(String message) {
        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothService to write
            byte[] send;
            try {
                send = message.getBytes("GB2312");
            } catch (UnsupportedEncodingException e) {
                send = message.getBytes();
            }

            mService.write(send);
        }
    }


    protected void sendMessage(Bitmap bitmap) {
        // 发送打印图片前导指令
        byte[] start = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1B,
                0x40, 0x1B, 0x33, 0x00};
        mService.write(start);

        mService.printCenter();
        byte[] draw2PxPoint = PicFromPrintUtils.draw2PxPoint(bitmap);

        mService.write(draw2PxPoint);
        // 发送结束指令
        byte[] end = {0x1d, 0x4c, 0x1f, 0x00};
        mService.write(end);
    }

    //是否打印小票弹窗
    protected void showPrintTicketDialog(final String tableId) {
        if (!mBluetoothAdapter.isEnabled()) {
            //打开蓝牙
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            return;
        }

        // Check that we're actually connected before trying anything
        if (mService.getState() != BluetoothService.STATE_CONNECTED) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BluetoothBaseActivity.this);
            builder.setTitle("蓝牙没有连接");
            builder.setMessage("请选择下一步操作");
            builder.setCancelable(false);
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("重选设备", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Launch the DeviceListActivity to see devices and do scan
                    Intent serverIntent = new Intent(BluetoothBaseActivity.this, BluetoothDeviceListActivity.class);
                    startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                }
            });
            AlertDialog d = builder.create();
            d.setCanceledOnTouchOutside(false);
            d.show();
            return;
        }

        final Dialog dialog = new Dialog(BluetoothBaseActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(BluetoothBaseActivity.this, R.layout.print_ticket_dialog_layout, null);
        view.findViewById(R.id.positive_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                printTicket(tableId);//打印小票
            }
        });
        view.findViewById(R.id.negative_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    //打印小票
    private void printTicket(String tableId) {
        okHttpsImp.tableInfoPrint(new MyResultCallback<String>() {
            @Override
            public void onAfter(@Nullable String s, @Nullable Exception e) {
                super.onAfter(s, e);
                isRequestingPrintTicketData = false;
            }

            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                isRequestingPrintTicketData = true;
            }

            @Override
            public void onResponseResult(Result result) {
                mService.printCenter();
                sendMessage("*******老板娘订单(消费单)*******");
                sendMessage("\n");
                sendMessage("\n");
                sendMessage("鹅掌门(地中海店)");
                sendMessage("\n");
                sendMessage("\n");
                sendMessage("消费清单");
                sendMessage("\n");
                sendMessage("\n");

                mService.printLeft();
                sendMessage("桌号：A02           人数：4");
                sendMessage("\n");
                sendMessage("\n");

                sendMessage("单号：A022131232322");
                sendMessage("\n");
                sendMessage("\n");

                sendMessage("时间：2016-10-13   13:10:52");
                sendMessage("\n");
                sendMessage("\n");

                mService.printCenter();
                sendMessage("-------------------------");
                sendMessage("\n");
                sendMessage("\n");
                sendMessage("\n");
            }

            @Override
            public void onResponseFailed(String msg) {

            }
        }, userShopInfoBean.getBusinessId(), tableId);
    }
}
