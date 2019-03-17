package com.example.abdelrahmansaleh.sofra.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.helper.HelperMethod;
import com.example.abdelrahmansaleh.sofra.helper.SharedPreferencesManger;
import com.example.abdelrahmansaleh.sofra.ui.activity.restaurant.RestaurantHomeActivity;
import com.example.abdelrahmansaleh.sofra.ui.fragment.user.LoginUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.abdelrahmansaleh.sofra.helper.Constant.VAL;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splashBackgroundImage)
    ImageView splashBackgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        ButterKnife.bind( this );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColor();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor() {

        Window window = this.getWindow();
        window.clearFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
        window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
        window.setStatusBarColor( this.getResources().getColor( R.color.black ) );
    }

    @OnClick({R.id.splashBtnBuyFood, R.id.splashBtnSellFood, R.id.splashBtnTwitter, R.id.splashBtnInstagrame})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.splashBtnBuyFood:
                SharedPreferencesManger.SaveData( this,VAL,"user" );
                startActivity( new Intent( SplashActivity.this, AppHomeActivity.class ) );
                break;
            case R.id.splashBtnSellFood:
                SharedPreferencesManger.SaveData( this,VAL,"rest" );
                startActivity( new Intent( SplashActivity.this, RestaurantHomeActivity.class ) );
                break;
            case R.id.splashBtnTwitter:
                break;
            case R.id.splashBtnInstagrame:
                break;
        }
    }
}
