package com.strod.yssl.view.autoscrollviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.strod.yssl.clientcore.Config;

import java.util.List;

/**
 * @author lying
 */
public class BannerPagerAdapter extends PagerAdapter {
    public List<IBannerEntity> bannerEntityList;
    public Context context;


    public BannerPagerAdapter(Context context, List<IBannerEntity> bannerEntityList) {
        this.context = context;
        this.bannerEntityList = bannerEntityList;
    }

    @Override
    public int getCount() {
        return bannerEntityList ==null ?0:bannerEntityList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = (getCount() + position % getCount()) % getCount();
        ImageView iv = new ImageView(context);
        String imgUrl = bannerEntityList.get(position).getImgUrl();
        ImageLoader.getInstance().displayImage(imgUrl,iv, Config.getInstance().getDisplayImageOptions());
        container.addView(iv);
        return iv;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
