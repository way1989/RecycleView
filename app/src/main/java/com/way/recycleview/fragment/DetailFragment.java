package com.way.recycleview.fragment;

import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.way.recycleview.R;
import com.way.recycleview.model.Item;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by way on 16/4/2.
 */
public class DetailFragment extends Fragment {
    private static final String ARG_NUMBER = "arg_number";
    @Bind(R.id.detail_image_view)
    ImageView imageView;

    public static DetailFragment newInstance(@IntRange(from = 0, to = 5) int number) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_NUMBER, number);

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);

        return detailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final int number = getArguments().getInt(ARG_NUMBER);
        Item item = Item.ITEMS[number];
        //Glide.with(imageView.getContext()).load(item.getPhoto()).dontAnimate().into(imageView);
        imageView.setImageResource(item.getPhoto());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setOnClickListener(null);
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
