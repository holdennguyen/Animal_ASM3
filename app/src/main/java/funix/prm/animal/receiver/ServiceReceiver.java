package funix.prm.animal.receiver;


import android.content.BroadcastReceiver;

import android.content.Context;

import android.content.Intent;

import android.telephony.PhoneStateListener;

import android.telephony.TelephonyManager;


public class ServiceReceiver extends BroadcastReceiver {

    TelephonyManager telephony;

    @Override
    public void onReceive(Context context, Intent intent) {
        MyPhoneStateListener phoneStateListener = new MyPhoneStateListener(context);
        telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

}