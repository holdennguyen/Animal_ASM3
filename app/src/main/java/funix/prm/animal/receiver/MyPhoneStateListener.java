package funix.prm.animal.receiver;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import funix.prm.animal.activity.MainActivity;

public class MyPhoneStateListener extends PhoneStateListener {

    public static Boolean phoneRinging = false;
    Context mContext;
    private SharedPreferences phoneNumberSharedPref;

    public MyPhoneStateListener(Context mContext) {
        this.mContext = mContext;
        this.phoneNumberSharedPref = mContext.getSharedPreferences(MainActivity.SAVE_PREF_DIALOG_PHONE_NUMBER, Context.MODE_PRIVATE);
    }

    @Override
    public void onCallStateChanged(int state, String phoneNumber) {
        super.onCallStateChanged(state, phoneNumber);
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d("DEBUG", "IDLE");
                phoneRinging = false;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d("DEBUG", "OFFHOOK");
                phoneRinging = false;
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d("DEBUG", "RINGING");
                showEmoij(mContext, state, phoneNumber);
                phoneRinging = true;
                break;
        }
    }

    private void showEmoij(Context context, int state, String phoneNumber) {
        try {
            String iconPath = phoneNumberSharedPref.getString(phoneNumber, null); //lấy đường dẫn đến icon của đối tượng đã lưu ở ViewPhoneDialog
            if (iconPath != null) {
                Bitmap bitmapIcon = BitmapFactory.decodeStream(context.getAssets().open(iconPath));
                Toast toast = new Toast(context);
                ImageView ivEmoij = new ImageView(context);
                ivEmoij.setImageBitmap(bitmapIcon);
                toast.setView(ivEmoij);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
            } else {

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

