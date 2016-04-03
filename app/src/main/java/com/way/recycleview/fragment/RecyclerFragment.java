package com.way.recycleview.fragment;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.way.recycleview.R;
import com.way.recycleview.adapter.MyAdapter;
import com.way.recycleview.model.DetailTransition;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by way on 16/4/1.
 */
public class RecyclerFragment extends Fragment implements MyAdapter.OnItemClickListener{
    private static final String STAGGERED_KEY = "STAGGERED_LAYOUT_MANAGER_KEY";
    private MyAdapter mAdapter;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.rececler_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_change_layout:
                changeLayoutManager();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_recycler, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        initRecyclerView(mRecyclerView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 初始化RecyclerView
     *
     * @param recyclerView 主控件
     */
    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true); // 设置固定大小
        initRecyclerLayoutManager(recyclerView); // 初始化布局
        initRecyclerAdapter(recyclerView); // 初始化适配器
        initItemDecoration(recyclerView); // 初始化装饰
        initItemAnimator(recyclerView); // 初始化动画效果
    }
    private void initRecyclerLayoutManager(RecyclerView recyclerView) {
        boolean isStaggered = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(STAGGERED_KEY, true);
        if (isStaggered)
            // 错列网格布局
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL));
        else
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }
    private void initRecyclerAdapter(RecyclerView recyclerView) {
        mAdapter = new MyAdapter(this);
        recyclerView.setAdapter(mAdapter);
    }
    /**
     * 初始化RecyclerView的(ItemDecoration)项目装饰
     *
     * @param recyclerView 主控件
     */
    private void initItemDecoration(RecyclerView recyclerView) {
       // recyclerView.addItemDecoration(new MyItemDecoration(this));
    }
    /**
     * 初始化RecyclerView的(ItemAnimator)项目动画
     *
     * @param recyclerView 主控件
     */
    private void initItemAnimator(RecyclerView recyclerView) {
        recyclerView.setItemAnimator(new DefaultItemAnimator()); // 默认动画
    }
    private boolean mIsDetailsActivityStarted;

    @Override
    public void onResume() {
        super.onResume();
        mIsDetailsActivityStarted = false;
    }

    @Override
    public void onClick(View view) {
        if(mIsDetailsActivityStarted)
            return;
        mIsDetailsActivityStarted = true;
        int position = (int) view.getTag();
        DetailFragment detailFragment = DetailFragment.newInstance(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            detailFragment.setSharedElementEnterTransition(new DetailTransition());
            setExitTransition(new Fade());
            detailFragment.setEnterTransition(new Fade());
            detailFragment.setSharedElementReturnTransition(new DetailTransition());
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        getActivity().getSupportFragmentManager().beginTransaction()
                .addSharedElement(imageView, getResources().getString(R.string.image_transition))
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();

        // Construct an Intent as normal
        //Intent intent = new Intent(getActivity(), DetailActivity.class);
        //intent.putExtra(DetailActivity.EXTRA_PARAM_ID, position);


        /**
         * Now create an {@link android.app.ActivityOptions} instance using the
         * {@link ActivityOptionsCompat#makeSceneTransitionAnimation(Activity, Pair[])} factory
         * method.
         */
//        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                getActivity(),
//
//                // Now we provide a list of Pair items which contain the view we can transitioning
//                // from, and the name of the view it is transitioning to, in the launched activity
//                holder.imageView, DetailActivity.VIEW_NAME_HEADER_IMAGE
//                );
//
//        // Now we can start the Activity, providing the activity options as a bundle
//        ActivityCompat.startActivity(getActivity(), intent, activityOptions.toBundle());
    }

    public void changeLayoutManager() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean isStaggered = sharedPreferences.getBoolean(STAGGERED_KEY, true);
        if(isStaggered) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }else{
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL));
        }
        sharedPreferences.edit().putBoolean(STAGGERED_KEY, !isStaggered).apply();
        Log.i("way", "changeLayoutManager...");
    }
}
