package com.strod.yssl.view.pulltorefresh;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.strod.yssl.R;

/**
 * @author lying
 */
public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {

    public PullToRefreshRecyclerView(Context context) {
        super(context);
        setScrollingWhileRefreshingEnabled(true);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScrollingWhileRefreshingEnabled(true);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
        setScrollingWhileRefreshingEnabled(true);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
        setScrollingWhileRefreshingEnabled(true);
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView recyclerView = new RecyclerView(context, attrs);
        recyclerView.setId(R.id.recyclerview);
        return recyclerView;
    }

    @Override
    protected boolean isReadyForPullStart() {
        RecyclerView.LayoutManager layoutManager = mRefreshableView.getLayoutManager();
        if (0 == layoutManager.getItemCount()) {
            return true;
        }
        if (layoutManager instanceof LinearLayoutManager || layoutManager instanceof GridLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            if (0 == linearLayoutManager.findFirstCompletelyVisibleItemPosition()) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int[] positions = staggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(null);
            boolean b = false;
            for (int p : positions) {
                if (0 == p) {
                    b = true;
                    break;
                }
            }
            return b;
        }
        return false;

//        if (mRefreshableView.getChildCount() <= 0) return true;
//        int firstVisiblePosition = mRefreshableView.getChildAdapterPosition(mRefreshableView.getChildAt(0));
//        if (firstVisiblePosition == 0) {
//            return mRefreshableView.getChildAt(0).getTop() == mRefreshableView.getPaddingTop();
//        } else {
//            return false;
//        }
    }

    @Override
    protected boolean isReadyForPullEnd() {

        RecyclerView.LayoutManager layoutManager = mRefreshableView.getLayoutManager();
        if (0 == layoutManager.getItemCount()) {
            return true;
        }
        if (layoutManager instanceof LinearLayoutManager || layoutManager instanceof GridLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            if (linearLayoutManager.getItemCount() - 1 == linearLayoutManager.findLastCompletelyVisibleItemPosition()) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int[] positions = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(null);
            boolean b = false;
            for (int p : positions) {
                if (staggeredGridLayoutManager.getItemCount() - 1 == p) {
                    b = true;
                    break;
                }
            }
            return b;
        }

        return false;

//        int lastVisiblePostion =
//                mRefreshableView.getChildAdapterPosition(mRefreshableView.getChildAt(mRefreshableView.getChildCount() - 1));
//        if (lastVisiblePostion >= mRefreshableView.getAdapter().getItemCount() - 1) {
//            return mRefreshableView.getChildAt(mRefreshableView.getChildCount() - 1).getBottom()
//                    <= mRefreshableView.getBottom();
//        }
//        return false;
    }
}
