package com.way.recycleview.fragment;

import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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
        mIsFinished = false;
//        return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int number = getArguments().getInt(ARG_NUMBER);
        Item item = Item.ITEMS[number];
        //Glide.with(imageView.getContext()).load(item.getPhoto()).dontAnimate().into(imageView);
        imageView.setImageResource(item.getPhoto());
        addTransitionListener();
//        mImage.setImageResource(mDetailDatas.get(number).getImage());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsFinished)
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mIsFinished = false;
    }
    private boolean mIsFinished;
    private boolean addTransitionListener() {
        final Transition transition = getActivity().getWindow().getSharedElementEnterTransition();

        if (transition != null) {
            // There is an entering shared element transition so add a listener to it
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    // As the transition has ended, we can now load the full-size image
                    //loadFullSizeImage();
                    mIsFinished = true;
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionStart(Transition transition) {
                    // No-op
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {
                    // No-op
                }

                @Override
                public void onTransitionResume(Transition transition) {
                    // No-op
                }
            });
            return true;
        }

        // If we reach here then we have not added a listener
        return false;
    }
}
