package com.example.lab4th;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.telephony.SmsMessage;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Khai báo biến (giống slide)
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo broadcastReceiver (giống slide)
        initBroadcastReceiver();
    }

    // Tự động đăng ký/hủy đăng ký khi Resume/Stop (giống slide)
    @Override
    protected void onResume() {
        super.onResume();
        // Make sure broadcastReceiver was created
        if (broadcastReceiver == null) initBroadcastReceiver();

        // Register Receiver
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // UnregisterReceiver when app is destroyed
        unregisterReceiver(broadcastReceiver);
    }

    // Xây dựng hàm khởi tạo broadcastReceiver (giống hình)
    private void initBroadcastReceiver() {
        // Create filter to listen to incoming messages
        filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

        // Create broadcastReceiver
        broadcastReceiver = new BroadcastReceiver() {
            // Process when a message comes
            @Override
            public void onReceive(Context context, Intent intent) {
                processReceive(context, intent);
            }
        };
    }

    // Xây dựng hàm xử lý khi tin nhắn đến (giống hình)
    public void processReceive(Context context, Intent intent) {
        Toast.makeText(context, getString(R.string.you_have_a_new_message),
                Toast.LENGTH_LONG).show();

        TextView tvContent = (TextView) findViewById(R.id.tv_content);

        // Use "pdus" as key to get message
        final String SMS_EXTRA = "pdus";
        Bundle bundle = intent.getExtras();
        if (bundle == null) return;

        // Get array of messages which were received at the same time
        Object[] messages = (Object[]) bundle.get(SMS_EXTRA);
        if (messages == null) return;

        String sms = "";
        SmsMessage smsMsg;

        for (int i = 0; i < messages.length; i++) {
            if (Build.VERSION.SDK_INT >= 23) {
                // format có thể null, để "" giống slide
                smsMsg = SmsMessage.createFromPdu((byte[]) messages[i], "");
            } else {
                smsMsg = SmsMessage.createFromPdu((byte[]) messages[i]);
            }

            // Get message body
            String msgBody = smsMsg.getMessageBody();
            // Get source address of message
            String address = smsMsg.getDisplayOriginatingAddress();

            sms += address + ":\n" + msgBody + "\n";
        }

        // Show messages in textview
        tvContent.setText(sms);
    }
}
