package funix.prm.animal.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import funix.prm.animal.R;
import funix.prm.animal.activity.MainActivity;
import funix.prm.animal.adapter.DetailPageAdapter;
import funix.prm.animal.model.Animal;

public class DetailFragment extends Fragment {
    private Context mContext;
    private int position;
    private ArrayList<Animal> listAnimals;

    public DetailFragment(ArrayList<Animal> listAnimals, int position) {
        this.listAnimals = listAnimals;
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void initView(View view) {
        ViewPager contentDetail = view.findViewById(R.id.view_pager_content);
        DetailPageAdapter detailPageAdapter = new DetailPageAdapter(listAnimals, mContext);
        ImageView ivBack = view.findViewById(R.id.iv_backArrow);
        //Quay lại MainActivity và mở MenuFragment
        ivBack.setOnClickListener(v -> {
            ((MainActivity) getActivity()).showMenu();
        });
        contentDetail.setAdapter(detailPageAdapter);
        contentDetail.setCurrentItem(position);
    }
}
