package com.strod.yssl.view.autoscrollviewpager;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.strod.yssl.R;

import java.util.List;

/**
 * @author lying
 */
public class Banner extends RelativeLayout{

    private Context context;
    private List<IBannerEntity> bannerEntityList;

    private RelativeLayout mContainer;
    private AutoScrollViewPager viewPager;
    private CircleIndicator indicator;

    public Banner(Context context) {
        this(context, null);
        initView(context);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initView(context);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public Banner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        this.context = context;
//        initView(context);
//    }

    public void initView(Context act) {
        View mView = LayoutInflater.from(act).inflate(R.layout.banner, this, true);

        mContainer = (RelativeLayout) mView.findViewById(R.id.banner);
        viewPager = (AutoScrollViewPager) mView.findViewById(R.id.viewpager);
        indicator = (CircleIndicator) mView.findViewById(R.id.indicator);
    }

    public void setBannerEntityList(List<IBannerEntity> bannerEntityList){
        if (bannerEntityList == null)
            throw new NullPointerException("bannerEntity cannot be null");
        this.bannerEntityList = bannerEntityList;
    }

    public void setBannerPagerAdapter(){
        viewPager.setAdapter(new BannerPagerAdapter(context, bannerEntityList));
    }

    public void setIndicator(){
        indicator.setViewPager(viewPager);
        indicator.setSelectedPos(0);
    }


}
