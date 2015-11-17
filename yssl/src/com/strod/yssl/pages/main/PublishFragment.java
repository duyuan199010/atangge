package com.strod.yssl.pages.main;

/**
 * 页面
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.roid.ui.AbsFragment;
import com.roid.util.Toaster;
import com.strod.yssl.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PublishFragment extends AbsFragment{

	private GridView mGridView;
	private final int IMAGE_OPEN = 1;        //打开图片标记
	private String pathImage;                //选择图片路径
	private Bitmap bmp;                      //导入临时图片
	private ArrayList<HashMap<String, Object>> imageItem;
	private SimpleAdapter simpleAdapter;     //适配器
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_publish, container, false);
		
		initView(rootView);
		
		return rootView;
	}

	private void initView(View rootView){
		mGridView = (GridView)rootView.findViewById(R.id.grid_view);

		/*
         * 载入默认图片添加图片加号
         * 通过适配器实现
         * SimpleAdapter参数imageItem为数据源 R.layout.griditem_addpic为布局
         */
		//获取资源图片加号
		bmp = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_input_add);
		imageItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("itemImage", bmp);
		imageItem.add(map);
		simpleAdapter = new SimpleAdapter(getActivity(),
				imageItem, R.layout.item_upload_image_grid,
				new String[] { "itemImage"}, new int[] { R.id.imageView1});
        /*
         * HashMap载入bmp图片在GridView中不显示,但是如果载入资源ID能显示 如
         * map.put("itemImage", R.drawable.img);
         * 解决方法:
         *              1.自定义继承BaseAdapter实现
         *              2.ViewBinder()接口实现
         *  参考 http://blog.csdn.net/admin_/article/details/7257901
         */
		simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
			@Override
			public boolean setViewValue(View view, Object data,
										String textRepresentation) {
				// TODO Auto-generated method stub
				if(view instanceof ImageView && data instanceof Bitmap){
					ImageView i = (ImageView)view;
					i.setImageBitmap((Bitmap) data);
					return true;
				}
				return false;
			}
		});
		mGridView.setAdapter(simpleAdapter);

        /*
         * 监听GridView点击事件
         * 报错:该函数必须抽象方法 故需要手动导入import android.view.View;
         */
		mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id)
			{
				if( imageItem.size() == 4) { //第一张为默认图片
					Toaster.showDefToast(getActivity(),"图片数3张已满");
				}
				else if(position == 0) { //点击图片位置为+ 0对应0张图片
					Toaster.showDefToast(getActivity(), "添加图片");
					//选择图片
					Intent intent = new Intent(Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, IMAGE_OPEN);
					//通过onResume()刷新数据
				}
				else {
					dialog(position);
					//Toast.makeText(MainActivity.this, "点击第"+(position + 1)+" 号图片",
					//      Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	//刷新图片
	@Override
	public void onResume() {
		super.onResume();
		if(!TextUtils.isEmpty(pathImage)){
			Bitmap addbmp=BitmapFactory.decodeFile(pathImage);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", addbmp);
			imageItem.add(map);
			simpleAdapter = new SimpleAdapter(getActivity(),
					imageItem, R.layout.item_upload_image_grid,
					new String[] { "itemImage"}, new int[] { R.id.imageView1});
			simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
				@Override
				public boolean setViewValue(View view, Object data,
											String textRepresentation) {
					// TODO Auto-generated method stub
					if(view instanceof ImageView && data instanceof Bitmap){
						ImageView i = (ImageView)view;
						i.setImageBitmap((Bitmap) data);
						return true;
					}
					return false;
				}
			});
			mGridView.setAdapter(simpleAdapter);
			simpleAdapter.notifyDataSetChanged();
			//刷新后释放防止手机休眠后自动添加
			pathImage = null;
		}
	}

	//获取图片路径 响应startActivityForResult
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//打开图片
		if(resultCode==getActivity().RESULT_OK && requestCode==IMAGE_OPEN) {
			Uri uri = data.getData();
			if (!TextUtils.isEmpty(uri.getAuthority())) {
				//查询选择图片
				Cursor cursor = getActivity().getContentResolver().query(
						uri,
						new String[] { MediaStore.Images.Media.DATA },
						null,
						null,
						null);
				//返回 没找到选择图片
				if (null == cursor) {
					return;
				}
				//光标移动至开头 获取图片路径
				cursor.moveToFirst();
				pathImage = cursor.getString(cursor
						.getColumnIndex(MediaStore.Images.Media.DATA));
			}
		}  //end if 打开图片
	}

	/*
     * Dialog对话框提示用户删除操作
     * position为删除图片位置
     */
	protected void dialog(final int position) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("确认移除已添加图片吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				imageItem.remove(position);
				simpleAdapter.notifyDataSetChanged();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

}
