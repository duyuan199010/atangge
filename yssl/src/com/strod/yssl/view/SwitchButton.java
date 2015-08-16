package com.strod.yssl.view;

import com.strod.yssl.R;

import android.content.Context;  
import android.graphics.Bitmap;  
import android.graphics.BitmapFactory;  
import android.graphics.Canvas;  
import android.graphics.Matrix;  
import android.graphics.Paint;  
import android.graphics.Rect;  
import android.util.AttributeSet;  
import android.view.MotionEvent;  
import android.view.View;  
import android.view.View.OnTouchListener;  

public class SwitchButton extends View implements OnTouchListener{
	
	public interface OnSwitchChangeListener{
		public void onSwitchChanged(View view, boolean swtichState);
	}

	private boolean swtichState = false;// 记录当前按钮是否打开,true为打，flase为关  
    private boolean onSlip = false;// 记录用户是否在滑动的变量  
    private float downX, currentX;// 按下时的x,当前的x,  
    private Rect btnOnRect, btnOffRect;// 打开和关闭状态下,游标的Rect  
    private OnSwitchChangeListener onSwitchChangeListener;   
    private Bitmap bgOnBitmap, bgOffBitmap, slipBtnBitmap;   
    
    public void SetOnSwitchChangeListener(OnSwitchChangeListener l) {// 设置监听?当状态修改的时?  
        onSwitchChangeListener = l;  
    }  
    
    //此构造函数在直接new控件时调用  
    public SwitchButton(Context context) {  
        super(context);  
        // TODO Auto-generated constructor stub  
        init();  
    }  
      
    public boolean isChecked(){  
        return swtichState;  
    }  
   
    public void setChecked(boolean check){  
        swtichState = check;  
        invalidate();  
    }  
      
  //此构造函数在xml中使用控件时调用  
    public SwitchButton(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        // TODO Auto-generated constructor stub  
        init();  
    }  
   
    private void init() {// 初始资源  
        // 载入图片资源  
        bgOnBitmap = BitmapFactory.decodeResource(getResources(),  R.drawable.btn_on);  
        bgOffBitmap = BitmapFactory.decodeResource(getResources(),  R.drawable.btn_off);  
        slipBtnBitmap = BitmapFactory.decodeResource(getResources(),  R.drawable.slide);  

        btnOnRect = new Rect(0, 0, slipBtnBitmap.getWidth(), slipBtnBitmap.getHeight());  
        btnOffRect = new Rect(bgOffBitmap.getWidth() - slipBtnBitmap.getWidth(), 0,  
                bgOffBitmap.getWidth(), slipBtnBitmap.getHeight());  
        setOnTouchListener(this);// 设置触控监听，也可以直接复写OnTouchEvent  
    }  
   
      
    protected void onDraw(Canvas canvas) {// 绘图函数  
        // TODO Auto-generated method stub  
        super.onDraw(canvas);  
        Matrix matrix = new Matrix();  
        Paint paint = new Paint();  
        float x;  
        {  
            if ((currentX < (bgOnBitmap.getWidth() / 2))&&!swtichState)// 滑动到前半段与后半段的背景时在此做判是否关闭或打开  
            {  
                canvas.drawBitmap(bgOffBitmap, matrix, paint);// 画出关闭时的背景  
            }  
            else{  
                canvas.drawBitmap(bgOnBitmap, matrix, paint);// 画出打开时的背景  
            }  
   
            if (onSlip)// 是否是在滑动状  
            {  
                if (currentX >= bgOnBitmap.getWidth())// 是否划出指定范围,不能让游标跑到绘制控件范围外?必须做这个判?  
                    x = bgOnBitmap.getWidth() - slipBtnBitmap.getWidth() / 2;// 减去游标1/2的长度  
                else  
                    x = currentX - slipBtnBitmap.getWidth() / 2;  
            } else {// 非滑动状  
                if (swtichState)// 根据现在的开关状态设置画游标的位  
                    x = btnOffRect.left;  
                else  
                    x = btnOnRect.left;  
            }  
            if (x < 0)// 对游标位置进行异常判  
                x = 0;  
            else if (x > bgOnBitmap.getWidth() - slipBtnBitmap.getWidth())  
                x = bgOnBitmap.getWidth() - slipBtnBitmap.getWidth();  
            canvas.drawBitmap(slipBtnBitmap, x, 0, paint);// 画出游标.  
        }  
    }  
   
      
    public boolean onTouch(View v, MotionEvent event) {  
        // TODO Auto-generated method stub  
        switch (event.getAction())// 根据动作来执行代  
        {  
        case MotionEvent.ACTION_MOVE:// 滑动  
            currentX = event.getX();  
            break;  
        case MotionEvent.ACTION_DOWN:// 按下  
            if (event.getX() > bgOnBitmap.getWidth()  
                    || event.getY() > bgOnBitmap.getHeight())  
                return false;  
            onSlip = true;  
            downX = event.getX();  
            currentX = downX;  
            break;  
        case MotionEvent.ACTION_UP:// 松开  
            onSlip = false;  
            boolean lastChoose = swtichState;  
            if (event.getX() >= (bgOnBitmap.getWidth() / 2))  
                swtichState = true;  
            else  
                swtichState = false;  
            if (lastChoose != swtichState)// 如果设置了监听器,就调用其方法..  
            	if(onSwitchChangeListener!=null){
            		onSwitchChangeListener.onSwitchChanged(this, swtichState);
            	}
            break;  
        default:  
   
        }  
        invalidate();// 重画控件  
        return true;  
    }  
   
    
}