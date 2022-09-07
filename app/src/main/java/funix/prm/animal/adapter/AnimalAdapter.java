package funix.prm.animal.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import funix.prm.animal.R;
import funix.prm.animal.activity.MainActivity;
import funix.prm.animal.fragment.DetailFragment;
import funix.prm.animal.model.Animal;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.MyHolder> {

    private Context mContext;
    private ArrayList<Animal> listAnimals;

    public AnimalAdapter(ArrayList<Animal> listAnimals, Context mContext) {
        this.mContext = mContext;
        this.listAnimals = listAnimals;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_animal, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {
        Animal item = listAnimals.get(position);
        myHolder.tvAnimal.setText(item.getName());
        myHolder.ivAnimal.setImageBitmap(item.getPhoto());
        myHolder.cvAnimal.setOnClickListener(view -> {
            myHolder.cvAnimal.setAlpha((float) 0.5);
            //Mở DetailFragment
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            DetailFragment detailFragment = new DetailFragment(listAnimals, position);
            activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left).replace(R.id.ln_main, detailFragment,null).commit();
        });

        //Kiểm tra trạng thái thả tim từ file xml Shared Preferences
        SharedPreferences pref = mContext.getSharedPreferences(MainActivity.SAVE_PREF_HEART_FLAG, Context.MODE_PRIVATE);
        Boolean isFav = pref.getBoolean(item.getName(), false);
        if (isFav) {
            myHolder.ivFav.setVisibility(View.VISIBLE);
        } else {
            myHolder.ivFav.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listAnimals.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvAnimal;
        ImageView ivAnimal, ivFav;
        CardView cvAnimal;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvAnimal = itemView.findViewById(R.id.tv_animal);
            ivAnimal = itemView.findViewById(R.id.iv_animal);
            cvAnimal = itemView.findViewById(R.id.cv_animal);
            ivFav = itemView.findViewById(R.id.iv_fav);
        }
    }
}
