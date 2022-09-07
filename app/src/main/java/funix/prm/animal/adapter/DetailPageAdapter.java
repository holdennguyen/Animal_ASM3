package funix.prm.animal.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import funix.prm.animal.R;
import funix.prm.animal.fragment.ViewPhoneDialog;
import funix.prm.animal.activity.MainActivity;
import funix.prm.animal.model.Animal;

public class DetailPageAdapter extends PagerAdapter {
    private final List<Animal> listAnimals;
    private final Context mContext;

    public DetailPageAdapter(List<Animal> listAnimals, Context mContext) {
        this.listAnimals = listAnimals;
        this.mContext = mContext;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_detail, container, false);
        Animal item = listAnimals.get(position);

        ImageView ivPhotoBg = view.findViewById(R.id.iv_photo_bg);
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvContent = view.findViewById(R.id.tv_content);
        ImageView ivFav = view.findViewById(R.id.iv_fav);
        ImageView ivPhone = view.findViewById(R.id.iv_phone);
        TextView tvPhone = view.findViewById(R.id.tv_phone);

        ivPhotoBg.setImageBitmap(item.getPhotoBg());
        tvName.setText(item.getName());
        tvContent.setText(item.getContent());

        //kiểm tra trạng thái thả tim trong Shared Preferences
        SharedPreferences pref = mContext.getSharedPreferences(MainActivity.SAVE_PREF_HEART_FLAG, Context.MODE_PRIVATE);
        Boolean isFav = pref.getBoolean(tvName.getText().toString(), false);
        //hiển thị hình ảnh trái tim tương ứng
        if (isFav) {
            ivFav.setImageDrawable(mContext.getDrawable(R.drawable.ic_favorite));
        } else {
            ivFav.setImageDrawable(mContext.getDrawable(R.drawable.ic_favorite_empty));
        }
        //ghi nhận sự kiện thả tim và cập nhật vào Shared Preferences: name - true/false
        ivFav.setOnClickListener(v -> {
            if (!isFav) {
                ivFav.setImageDrawable(mContext.getDrawable(R.drawable.ic_favorite));
                pref.edit().putBoolean(item.getName(), true).commit();
            } else {
                ivFav.setImageDrawable(mContext.getDrawable(R.drawable.ic_favorite_empty));
                pref.edit().putBoolean(item.getName(), false).commit();
            }
        });

        //kiểm tra số điện thoại của đối tượng trong Shared Preferences
        SharedPreferences phoneNumberSharedPref = mContext.getSharedPreferences(MainActivity.SAVE_PREF_DIALOG_PHONE_NUMBER, Context.MODE_PRIVATE);
        String phoneNumber = phoneNumberSharedPref.getString(item.getName(), null);

        if (item.getName() != null) {
            tvPhone.setText(phoneNumber);
        }

        //ghi nhận sự kiện click để mở ViewPhoneDialog
        ivPhone.setOnClickListener(v -> {
            FragmentActivity activity = (FragmentActivity) mContext;
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            ViewPhoneDialog dialog = new ViewPhoneDialog(mContext, item, tvPhone);
            dialog.show(fragmentManager, "PhoneNumberDialog");
        });

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return listAnimals.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
