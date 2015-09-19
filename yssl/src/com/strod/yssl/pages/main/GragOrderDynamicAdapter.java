package com.strod.yssl.pages.main;

/**
 * Author: alex askerov
 * Date: 9/9/13
 * Time: 10:52 PM
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;

import com.strod.yssl.R;
import com.strod.yssl.pages.main.entity.ItemType;

import java.util.List;

/**
 * Author: alex askerov
 * Date: 9/7/13
 * Time: 10:56 PM
 */
public class GragOrderDynamicAdapter extends BaseDynamicGridAdapter {
	
	private boolean mIsEditMode = false;
	
	public void setEditMode(boolean editMode){
		mIsEditMode = editMode;
		notifyDataSetChanged();
	}
	
    public GragOrderDynamicAdapter(Context context, List<ItemType> items, int columnCount) {
        super(context, items, columnCount);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CheeseViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_dragorder_grid, null);
            holder = new CheeseViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CheeseViewHolder) convertView.getTag();
        }
        holder.build(((ItemType)getItem(position)).getName());
        holder.setDeleteVisi(mIsEditMode==false?View.GONE:View.VISIBLE);
        holder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((DragOrderGridActivity) getContext()).deleteItem(position);
			}
		});
        return convertView;
    }

    private class CheeseViewHolder {
        private TextView titleText;
        private ImageView itemDelete;

        private CheeseViewHolder(View view) {
            titleText = (TextView) view.findViewById(R.id.item_title);
            itemDelete = (ImageView) view.findViewById(R.id.item_delete);
        }

        void build(String title) {
            titleText.setText(title);
        }
        
        void setDeleteVisi(int visibility){
        	itemDelete.setVisibility(visibility);
        }
        
        void setOnClickListener(OnClickListener l){
        	itemDelete.setOnClickListener(l);
        }
    }
}