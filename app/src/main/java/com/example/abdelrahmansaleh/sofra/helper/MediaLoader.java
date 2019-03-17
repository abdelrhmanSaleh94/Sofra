package com.example.abdelrahmansaleh.sofra.helper;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.abdelrahmansaleh.sofra.R;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;

class MediaLoader implements AlbumLoader {
    @Override
    public void load(ImageView imageView, AlbumFile albumFile) {
        load(imageView, albumFile.getPath());
    }

    @Override
    public void load(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .error( R.drawable.background_splash)
                .placeholder(R.drawable.background_splash)
                .into(imageView);
    }
}
