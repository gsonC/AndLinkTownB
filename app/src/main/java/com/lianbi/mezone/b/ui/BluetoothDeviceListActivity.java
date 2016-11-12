package com.lianbi.mezone.b.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

import java.util.ArrayList;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import cn.com.hgh.view.MyListView;

public class BluetoothDeviceListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    @Bind(R.id.back)
    ImageView back;

    @Bind(R.id.search_bluetooth_device_button)
    Button button;

    @Bind(R.id.is_searching)
    LinearLayout is_searching;

    @Bind(R.id.progressbar)
    ContentLoadingProgressBar progressbar;

    @Bind(R.id.paired_devices_list_view)
    MyListView pairedListView;

    @Bind(R.id.no_paired_bluetooth_device)
    TextView no_paired_bluetooth_device;

    @Bind(R.id.new_devices_list_view)
    MyListView newListView;

    @Bind(R.id.no_new_bluetooth_device)
    TextView no_new_bluetooth_device;

    // Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    // Member fields
    private BluetoothAdapter mBtAdapter;
    private ArrayList<BluetoothDevice> mPairedDevicesArrayList = new ArrayList<>();
    private ArrayList<BluetoothDevice> mNewDevicesArrayList = new ArrayList<>();
    private BluetoothDevicesListViewAdapter pairedAdapter;
    private BluetoothDevicesListViewAdapter newAdapter;

    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    mNewDevicesArrayList.add(device);
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                progressbar.hide();
                is_searching.setVisibility(View.GONE);
                button.setVisibility(View.VISIBLE);
                if (mNewDevicesArrayList.isEmpty()) {
                    newListView.setVisibility(View.GONE);
                    no_new_bluetooth_device.setVisibility(View.VISIBLE);
                } else {
                    newListView.setVisibility(View.VISIBLE);
                    no_new_bluetooth_device.setVisibility(View.GONE);
                    newAdapter.addDevicesArrayList(mNewDevicesArrayList);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_device_list);
        ButterKnife.bind(this);

        // Set result CANCELED incase the user backs out
        setResult(Activity.RESULT_CANCELED);

        initListViewAdapter();

        addIntentFilter();

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.isEmpty()) {
            pairedListView.setVisibility(View.GONE);
            no_paired_bluetooth_device.setVisibility(View.VISIBLE);
        } else {
            pairedListView.setVisibility(View.VISIBLE);
            no_paired_bluetooth_device.setVisibility(View.GONE);
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayList.add(device);
            }
            pairedAdapter.setDevicesArrayList(mPairedDevicesArrayList);
        }
    }

    private void addIntentFilter() {
        IntentFilter filter = new IntentFilter();
        // Register for broadcasts when a device is discovered
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        // Register for broadcasts when discovery has finished
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);
    }

    private void initListViewAdapter() {
        pairedAdapter = new BluetoothDevicesListViewAdapter(mPairedDevicesArrayList);
        newAdapter = new BluetoothDevicesListViewAdapter(mNewDevicesArrayList);
        pairedListView.setAdapter(pairedAdapter);
        newListView.setAdapter(newAdapter);
    }

    @Override
    @OnClick({R.id.back, R.id.search_bluetooth_device_button})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.search_bluetooth_device_button:
                doDiscovery();
                break;
        }
    }

    private void doDiscovery() {
        button.setVisibility(View.GONE);
        is_searching.setVisibility(View.VISIBLE);
        progressbar.show();

        // If we're already discovering, stop it
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
    }

    @Override
    @OnItemClick({R.id.paired_devices_list_view, R.id.new_devices_list_view})
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Cancel discovery because it's costly and we're about to connect
        mBtAdapter.cancelDiscovery();
        String address = null;
        switch (parent.getId()) {
            case R.id.paired_devices_list_view:
                address = pairedAdapter.getDevicesArrayList().get(position).getAddress();
                break;
            case R.id.new_devices_list_view:
                address = newAdapter.getDevicesArrayList().get(position).getAddress();
                break;
        }
        // Create the result Intent and include the MAC address
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

        // Set result and finish this Activity
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    private class BluetoothDevicesListViewAdapter extends BaseAdapter {
        private ArrayList<BluetoothDevice> devicesArrayList;

        public BluetoothDevicesListViewAdapter() {
            this(new ArrayList<BluetoothDevice>());
        }

        public BluetoothDevicesListViewAdapter(ArrayList<BluetoothDevice> devicesArrayList) {
            this.devicesArrayList = devicesArrayList;
        }

        @Override
        public int getCount() {
            return devicesArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            if (devicesArrayList.isEmpty())
                return null;
            return devicesArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(BluetoothDeviceListActivity.this).inflate(R.layout.bluetooth_device_list_view_item, null);
            TextView deviceName = (TextView) convertView.findViewById(R.id.bluetooth_device_name);
            TextView deviceAddress = (TextView) convertView.findViewById(R.id.bluetooth_device_address);
            BluetoothDevice device = devicesArrayList.get(position);
            deviceName.setText(device.getName());
            deviceAddress.setText(device.getAddress());
            return convertView;
        }

        public ArrayList<BluetoothDevice> getDevicesArrayList() {
            return devicesArrayList;
        }

        public void setDevicesArrayList(ArrayList<BluetoothDevice> devicesArrayList) {
            if (this.devicesArrayList != null && !this.devicesArrayList.isEmpty()) {
                this.devicesArrayList.clear();
            }
            this.devicesArrayList = devicesArrayList;
            notifyDataSetChanged();
        }

        public void addDevicesArrayList(ArrayList<BluetoothDevice> devicesArrayList) {
            if (devicesArrayList != null && !devicesArrayList.isEmpty()) {
                this.devicesArrayList.addAll(devicesArrayList);
                notifyDataSetChanged();
            }
        }
    }
}
