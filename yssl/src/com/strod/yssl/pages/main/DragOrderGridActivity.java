package com.strod.yssl.pages.main;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.roid.ui.FragmentControlActivity;
import com.roid.util.DebugLog;
import com.strod.yssl.R;
import com.strod.yssl.bean.main.ItemType;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.view.dynamicgrid.DynamicGridView;

public class DragOrderGridActivity extends FragmentControlActivity {

    private static final String TAG = DragOrderGridActivity.class.getName();
    
    public static final String ITEM_LIST = "item_list";

    private List<ItemType> mItemList;
    private DynamicGridView gridView;
    private GragOrderDynamicAdapter mGragOrderDynamicAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Config.getInstance().isNightMode) {
			setTheme(R.style.NightTheme);
		} else {
			setTheme(R.style.DayTheme);
		}
        setContentView(R.layout.activity_dragorder_grid);
        
        mItemList = getIntent().getParcelableArrayListExtra(ITEM_LIST);
        
        gridView = (DynamicGridView) findViewById(R.id.dynamic_grid);
        mGragOrderDynamicAdapter = new GragOrderDynamicAdapter(this,mItemList,4);
        gridView.setAdapter(mGragOrderDynamicAdapter);
//        add callback to stop edit mode if needed
//        gridView.setOnDropListener(new DynamicGridView.OnDropListener()
//        {
//            @Override
//            public void onActionDrop()
//            {
//                gridView.stopEditMode();
//            }
//        });
        gridView.setOnDragListener(new DynamicGridView.OnDragListener() {
            @Override
            public void onDragStarted(int position) {
                DebugLog.d(TAG, "drag started at position " + position);
            }

            @Override
            public void onDragPositionsChanged(int oldPosition, int newPosition) {
                DebugLog.d(TAG, String.format("drag item position changed from %d to %d", oldPosition, newPosition));
                ItemType itemType = mItemList.remove(oldPosition);
                mItemList.add(newPosition, itemType);
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                gridView.startEditMode(position);
                mGragOrderDynamicAdapter.setEditMode(true);
                return true;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(DragOrderGridActivity.this, parent.getAdapter().getItem(position).toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    public void deleteItem(int position){
    	mItemList.remove(position);
    	mGragOrderDynamicAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (gridView.isEditMode()) {
            gridView.stopEditMode();
            mGragOrderDynamicAdapter.setEditMode(false);
        } else {
            super.onBackPressed();
        }
    }
}
