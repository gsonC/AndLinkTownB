package com.lianbi.mezone.b.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lianbi.mezone.b.bean.OneDishBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lzy.okgo.request.BaseRequest;
import com.xizhi.mezone.b.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.com.hgh.service.BluetoothService;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.PicFromPrintUtils;
import cn.com.hgh.utils.Result;

/**
 * Created by zemin.zheng on 2016/11/1.
 */

public abstract class BluetoothBaseActivity extends BaseActivity {
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
    private static final int REQUEST_ENABLE_BT = 1001;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1002;
    private static final int REQUEST_CODE_LOCATION_SETTINGS = 1003;

    // Name of the connected device
    private String mConnectedDeviceName = null;

    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;

    // Member object for the services
    public static BluetoothService mService = null;

    protected static ArrayList<BluetoothDevice> mBluetoothDeviceList = new ArrayList<>();

    protected BluetoothDevice deviceIsSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//如果 API level 是大于等于 23(Android 6.0) 时
            if (isLocationEnable(this)) {
                setLocationService();
            }

            //判断是否具有权限
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //判断是否需要向用户解释为什么需要申请该权限
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    ContentUtils.showMsg(this, "自Android 6.0开始需要打开位置权限才可以搜索到蓝牙设备");
                }
                //请求权限
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
            }
        }
        checkBluetoothExist();
        addIntentFilter();

        if (mBluetoothAdapter.isEnabled()) {
            // Get a set of currently paired devices
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            // If there are paired devices, add each one to the ArrayAdapter
            if (!pairedDevices.isEmpty()) {
                for (BluetoothDevice device : pairedDevices) {
                    if (!compareTo(mBluetoothDeviceList, device)) {
                        mBluetoothDeviceList.add(device);
                    }
                }
            }
        }
    }

    private void setLocationService() {
        Intent locationIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        this.startActivityForResult(locationIntent, REQUEST_CODE_LOCATION_SETTINGS);
    }

    public boolean isLocationEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean networkProvider = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean gpsProvider = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (networkProvider || gpsProvider) return true;
        return false;
    }

    protected void checkBluetoothExist() {
        if (mBluetoothAdapter == null) {
            ContentUtils.showMsg(this, "您的设备不支持蓝牙");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mService == null) {
            mService = new BluetoothService(this, mHandler);
        }
        if (!mBluetoothAdapter.isEnabled()) {
            //打开蓝牙
            openBluetooth();
        }
    }

    //打开蓝牙
    private void openBluetooth() {
        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    ContentUtils.showMsg(this, "蓝牙已打开");

                    // Get a set of currently paired devices
                    Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                    // If there are paired devices, add each one to the ArrayAdapter
                    if (pairedDevices.isEmpty()) {
                        showSearchBluetoothDeviceDialog();
                    } else {
                        for (BluetoothDevice device : pairedDevices) {
                            if (!compareTo(mBluetoothDeviceList, device)) {
                                mBluetoothDeviceList.add(device);
                            }
                        }
                        connectingBluetoothDialog();
                    }
                } else {
                    ContentUtils.showMsg(this, "蓝牙没有打开");
                }
                break;
            case REQUEST_CODE_LOCATION_SETTINGS:
                if (resultCode == Activity.RESULT_OK) {
                    if (isLocationEnable(this)) {
                        //定位已打开的处理
                        ContentUtils.showMsg(this, "定位已打开");
                    } else {
                        //定位依然没有打开的处理
                        ContentUtils.showMsg(this, "请打开定位");
                    }
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
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
            openBluetooth();
            return;
        }

        if (mBluetoothDeviceList.isEmpty()) {
            showSearchBluetoothDeviceDialog();
            return;
        }

        // Check that we're actually connected before trying anything
        if (mService.getState() != BluetoothService.STATE_CONNECTED) {
            connectingBluetoothDialog();
            return;
        }

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(this, R.layout.print_ticket_dialog_layout, null);
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
        Window win = dialog.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = (int) (screenWidth * 0.8);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(lp);
        dialog.show();
    }

    private void showSearchBluetoothDeviceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("没有匹配的蓝牙设备");
        builder.setMessage("是否搜索新设备");
        builder.setCancelable(false);
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                doDiscovery();
            }
        });
        AlertDialog d = builder.create();
        d.setCanceledOnTouchOutside(false);
        d.show();
    }

    protected abstract void connectingBluetoothDialog();

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
                String data = result.getData();
                if (!TextUtils.isEmpty(data)) {
                    JSONObject jsonObject = JSON.parseObject(data);
                    JSONObject bapsOrderDetlList = jsonObject.getJSONObject("bapsOrderDetlList");
                    List<OneDishBean> list = JSON.parseArray(bapsOrderDetlList.getString("list"), OneDishBean.class);
                    String allAmount = bapsOrderDetlList.getString("allAmount");//总额
                    String qrUrl = jsonObject.getString("qrUrl");//生成二维码
                    if (list == null || list.isEmpty()) {
                        ContentUtils.showMsg(BluetoothBaseActivity.this, "没有订单信息");
                        return;
                    }

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
                    OneDishBean bean = list.get(0);
                    sendMessage("桌号：" + bean.getTableName() + "           人数：" + bean.getPersonNum());
                    sendMessage("\n");

                    String time = AbDateUtil.getSpecialFormatTimeFromTimeMillisString(bean.getCreateTime(),
                            AbDateUtil.dateFormatYMDHMS);
                    sendMessage("时间：" + time);
                    sendMessage("\n");
                    sendMessage("\n");

                    mService.printCenter();
                    sendMessage("--------------------------------");
                    sendMessage("\n");
                    sendMessage("\n");

                    mService.printLeft();
                    sendMessage("商品名称      数量   单价   小计");
                    sendMessage("\n");
                    for (OneDishBean b : list) {
                        String proName = b.getProName();
                        String num = b.getNum();
                        String price = b.getPrice();
                        String totalAmount = b.getTotalAmount();
                        sendMessage(proName);
                        String s0 = "      ";
                        if ("商品名称".length() > proName.length()) {
                            for (int i = 0; i < "商品名称".length() - proName.length(); i++) {
                                s0 = s0.concat("  ");
                            }
                        }
                        if ("商品名称".length() < proName.length()) {
                            for (int i = 0; i < proName.length() - "商品名称".length(); i++) {
                                s0 = s0.substring(2);
                            }
                        }
                        sendMessage(s0);
                        sendMessage(num);
                        for (int i = 0; i < "数量".length() * 2 - num.length(); i++) {
                            sendMessage(" ");
                        }
                        sendMessage("   ");
                        try {
                            price = AbStrUtil.changeF2Y(Long.parseLong(price));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        sendMessage(price);
                        String s1 = "   ";
                        if ("单价".length() * 2 > price.length()) {
                            for (int i = 0; i < "单价".length() * 2 - price.length(); i++) {
                                s1 = s1.concat(" ");
                            }
                        }
                        if ("单价".length() * 2 < price.length()) {
                            for (int i = 0; i < price.length() - "单价".length() * 2; i++) {
                                s1 = s1.substring(1);
                            }
                        }
                        sendMessage(s1);
                        try {
                            totalAmount = AbStrUtil.changeF2Y(Long.parseLong(totalAmount));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        sendMessage(totalAmount);
                        sendMessage("\n");
                    }
                    try {
                        allAmount = AbStrUtil.changeF2Y(Long.parseLong(allAmount));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sendMessage("总计                        " + allAmount);
                    sendMessage("\n");
                    sendMessage("\n");

                    mService.printReset();
                    mService.printCenter();
                    sendMessage("--------------------------------");

                    BufferedInputStream bis = null;
                    try {
                        bis = new BufferedInputStream(getAssets().open("qr.jpg"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = BitmapFactory.decodeStream(bis);
                    sendMessage(bitmap);

                    mService.printReset();
                    mService.printCenter();
                    sendMessage("扫码关注享更多优惠");
                    sendMessage("\n");
                    sendMessage("\n");
                    sendMessage("\n");
                    sendMessage("--------------------------------");
                    sendMessage("\n");
                    sendMessage("\n");
                    sendMessage("\n");

                    sendMessage("*******老板娘订单(服务单)*******");
                    sendMessage("\n");
                    sendMessage("\n");
                    mService.printLeft();
                    sendMessage("桌号：" + bean.getTableId() + "         人数：" + bean.getPersonNum());
                    sendMessage("\n");
                    sendMessage("时间：" + time);
                    sendMessage("\n");
                    sendMessage("\n");

                    mService.printCenter();
                    sendMessage("--------------------------------");
                    sendMessage("\n");
                    sendMessage("\n");

                    mService.printLeft();
                    mService.printSize(1);
                    sendMessage("商品名称            数量");
                    mService.printSize(0);
                    sendMessage("\n");
                    sendMessage("\n");
                    for (OneDishBean b : list) {
                        String proName = b.getProName();
                        String num = b.getNum();
                        mService.printSize(1);
                        sendMessage(proName);
                        String s0 = "            ";
                        if ("商品名称".length() > proName.length()) {
                            for (int i = 0; i < "商品名称".length() - proName.length(); i++) {
                                s0 = s0.concat("  ");
                            }
                        }
                        if ("商品名称".length() < proName.length()) {
                            for (int i = 0; i < proName.length() - "商品名称".length(); i++) {
                                s0 = s0.substring(2);
                            }
                        }
                        sendMessage(s0);
                        sendMessage(num);
                        mService.printSize(0);
                        sendMessage("\n");
                    }

                    mService.printReset();
                    mService.printCenter();
                    sendMessage("\n");
                    sendMessage("--------------------------------");
                    sendMessage("\n");
                    sendMessage("\n");
                    sendMessage("\n");
                    sendMessage("\n");
                    sendMessage("--------------------------------");
                    sendMessage("\n");
                    sendMessage("--------------------------------");
                    sendMessage("\n");
                    sendMessage("\n");
                    sendMessage("\n");
                    sendMessage("\n");
                }
            }

            @Override
            public void onResponseFailed(String msg) {

            }
        }, userShopInfoBean.getBusinessId(), tableId);
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            ContentUtils.showMsg(BluetoothBaseActivity.this, "蓝牙已连接");
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            ContentUtils.showMsg(BluetoothBaseActivity.this, "正在连接蓝牙...");
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

    protected void doDiscovery() {
        ContentUtils.showMsg(BluetoothBaseActivity.this, "正在查找新蓝牙设备...");

        // If we're already discovering, stop it
        bluetoothAdapterCancelDiscovery();

        // Request discover from BluetoothAdapter
        mBluetoothAdapter.startDiscovery();
    }

    // If we're already discovering, stop it
    protected void bluetoothAdapterCancelDiscovery() {
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = null;
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    if (!compareTo(mBluetoothDeviceList, device)) {
                        mBluetoothDeviceList.add(device);
                    }
                    connectingBluetoothDialog();
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                if (device == null) {
                    ContentUtils.showMsg(BluetoothBaseActivity.this, "没有找到新设备");
                }
            }
        }
    };

    private void addIntentFilter() {
        IntentFilter filter = new IntentFilter();
        // Register for broadcasts when a device is discovered
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        // Register for broadcasts when discovery has finished
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_COARSE_LOCATION) {
            //用户允许改权限，0表示允许，-1表示拒绝 PERMISSION_GRANTED = 0， PERMISSION_DENIED = -1
            //permission was granted, yay! Do the contacts-related task you need to do.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //这里进行授权被允许的处理

                if (!mBluetoothAdapter.isEnabled()) {
                    //打开蓝牙
                    openBluetooth();
                    return;
                }

                if (mBluetoothDeviceList.isEmpty()) {
                    showSearchBluetoothDeviceDialog();
                    return;
                }

                // Check that we're actually connected before trying anything
                if (mService.getState() != BluetoothService.STATE_CONNECTED) {
                    connectingBluetoothDialog();
                    return;
                }
            } else {
                //permission denied, boo! Disable the functionality that depends on this permission.
                //这里进行权限被拒绝的处理
                ContentUtils.showMsg(this, "权限申请被拒，无法搜索到蓝牙设备");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean compareTo(List<BluetoothDevice> data, BluetoothDevice enity) {
        int s = data.size();
        if (enity != null) {
            for (int i = 0; i < s; i++) {
                if (enity.getAddress().equals(data.get(i).getAddress())) {
                    return true;
                }
            }
        }
        return false;
    }
}
