package com.dj.katchup.ui.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dj.katchup.R;

import java.util.ArrayList;

public class LoginViewPagerAdapter extends PagerAdapter {
    private ArrayList<Integer> imageArray;
    private LayoutInflater inflater;
    private Context context;

    public LoginViewPagerAdapter(Context context, ArrayList<Integer> integers) {
        this.context = context;
        imageArray = integers;
        inflater = LayoutInflater.from(context);
      /*  imageUrls.add("https://www.mch-group.com/-/media/mch-group/Images/Content/News/Blog/2017/2017-04/mch-group-live-marketing-aktivierung.jpg");
        imageUrls.add("https://griebs.in/wp-content/uploads/2018/04/musicevent.jpg");
        imageUrls.add("https://i0.wp.com/www.onwardsandupwards.org/wp-content/uploads/2019/05/book-stack-books-bookshop-264635.jpg?fit=1280%2C853&ssl=1");
        imageUrls.add("https://smtd.umich.edu/wp-content/uploads/mc-image-cache/2017/01/DancingGlobally_posterimage_crop.jpg");*/
    }

    @Override
    public int getCount() {
        return imageArray.size();
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View imageLayout = inflater.inflate(R.layout.only_image, container, false);
        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.imageview);
        imageView.setImageResource(imageArray.get(position));
        container.addView(imageLayout, 0);
        return imageLayout;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }
}
