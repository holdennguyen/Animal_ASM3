package funix.prm.animal.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import funix.prm.animal.R;
import funix.prm.animal.activity.MainActivity;
import funix.prm.animal.model.Animal;

public class ViewPhoneDialog extends DialogFragment {

    Context mContext;
    ImageView ivIcon;
    EditText edtPhone;
    private Animal animal;
    private TextView tvPhoneNumber;

    public ViewPhoneDialog( Context mContext, Animal animal, TextView tvPhoneNumber) {
        this.mContext = mContext;
        this.animal = animal;
        this.tvPhoneNumber = tvPhoneNumber;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.phone_dialog, null);

        ivIcon = viewDialog.findViewById(R.id.ic_avatar);
        edtPhone = viewDialog.findViewById(R.id.edt_phone);

        SharedPreferences phoneNumberSharedPref = mContext.getSharedPreferences(MainActivity.SAVE_PREF_DIALOG_PHONE_NUMBER, Context.MODE_PRIVATE);

        ivIcon.setImageBitmap(animal.getPhoto());
        String titleIcon = animal.getName();
        String fillPhoneNumber = phoneNumberSharedPref.getString(titleIcon,null);
        if  (fillPhoneNumber!=null){
            edtPhone.setText(fillPhoneNumber);
        }

        builder.setView(viewDialog)
                //tạo nút Save trong dialog
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        String iconPath = animal.getPath();
                        String phoneNumber = edtPhone.getText().toString();
                        tvPhoneNumber.setText(phoneNumber);
                        //lưu số điện thoại đã nhập vào file xml trong Shared Preference (key:name - value:số điện thoại)
                        phoneNumberSharedPref.edit().putString(titleIcon, phoneNumber).commit();
                        //lưu số điện thoại đã nhập vào file xml trong Shared Preference: (key:số điện thoại - value:đường dẫn đến icon)
                        phoneNumberSharedPref.edit().putString(phoneNumber,iconPath).commit();

                    }
                })
                //tạo nút Delete trong dialog
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        tvPhoneNumber.setText("");
                        ViewPhoneDialog.this.getDialog().cancel();
                        //tìm và xóa các cặp key-value trong Shared Preferences tương ứng
                        String phonePref = phoneNumberSharedPref.getString(titleIcon, null);
                        String pathPref = phoneNumberSharedPref.getString(phonePref, null);
                        if (phonePref != null) {
                            phoneNumberSharedPref.edit().remove(phonePref).commit();
                            phoneNumberSharedPref.edit().remove(titleIcon).commit();
                            phoneNumberSharedPref.edit().remove(pathPref).commit();
                        }
                    }
                });
        return builder.create();
    }
}

