package com.dj.katchup.interfaces;


import android.widget.ImageView;

import com.dj.katchup.model.responseModel.HomeResponseM;

public interface EventSelectListerner {
    public void onResturantClicked(HomeResponseM responseM, ImageView event_image);
}
