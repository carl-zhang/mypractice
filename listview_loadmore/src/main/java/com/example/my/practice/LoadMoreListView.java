package com.example.my.practice;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.AbsListView;

public class LoadMoreListView extends ListView implements AbsListView.OnScrollListener {

    private View mFootView;// 底部视图
    private int mTotalItemCount;// item 的总数

    private OnLoadMoreListener loadMoreListener;
    private boolean isLoading = false;// 是否正在加载



    public LoadMoreListView(Context context) {
        super(context);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mFootView = LayoutInflater.from(context).inflate(R.layout.foot_view, null);
        setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 到达底部后，判断 listView 已经停止并且最后可见的条目等于 adapter 的条目
        int lastVisibleIndex = view.getLastVisiblePosition();// The position within the adapter's data set
        if(!isLoading && scrollState == OnScrollListener.SCROLL_STATE_IDLE// 停止滑动
                && lastVisibleIndex == mTotalItemCount - 1) {// 滑动到最后一项
            isLoading = true;// 标记位设置为正在加载
            addFooterView(mFootView);// 添加底部上拉加载动画
            if(loadMoreListener != null) {
                loadMoreListener.onLoadMore();// 启动监听器，加载更多数据
            }

        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mTotalItemCount = totalItemCount;
    }

    /**
     * 加载完毕
     */
    public void setLoadCompleted() {
        isLoading = false;
        removeFooterView(mFootView);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        loadMoreListener = listener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
