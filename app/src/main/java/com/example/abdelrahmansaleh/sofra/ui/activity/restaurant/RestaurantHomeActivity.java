package com.example.abdelrahmansaleh.sofra.ui.activity.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.helper.HelperMethod;
import com.example.abdelrahmansaleh.sofra.helper.SharedPreferencesManger;
import com.example.abdelrahmansaleh.sofra.ui.activity.SplashActivity;
import com.example.abdelrahmansaleh.sofra.ui.fragment.restaurant.MyProductsFragment;
import com.example.abdelrahmansaleh.sofra.ui.fragment.restaurant.RestaurantDetailsFragment;
import com.example.abdelrahmansaleh.sofra.ui.fragment.user.LoginUser;

public class RestaurantHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_restaurant_home );
        setTitle( " " );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        final DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        toolbar.setNavigationIcon( R.drawable.ic_nav_logo );
        toolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen( Gravity.RIGHT )) {
                    drawer.closeDrawer( Gravity.RIGHT );
                } else {
                    drawer.openDrawer( Gravity.RIGHT );
                }
            }
        } );
        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
        HelperMethod.replaceFragment( new LoginUser(), getSupportFragmentManager(), R.id.restaurantHomeActivityFrame, null, null );

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.END )) {
            drawer.closeDrawer( GravityCompat.END );
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_rest_home) {
            HelperMethod.replaceFragment( new RestaurantDetailsFragment(), getSupportFragmentManager(),
                    R.id.restaurantHomeActivityFrame, null, null );

            // Handle the camera action
        } else if (id == R.id.nav_rest_about) {

        } else if (id == R.id.nav_rest_budget) {

        } else if (id == R.id.nav_rest_conditions) {

        } else if (id == R.id.nav_rest_connect) {

        } else if (id == R.id.nav_notification) {

        } else if (id == R.id.nav_rest_offer) {

        } else if (id == R.id.nav_rest_share) {

        } else if (id == R.id.nav_rest_logout) {
            SharedPreferencesManger.clean( this );
            startActivity( new Intent( this, SplashActivity.class ) );
            finish();
        } else if (id == R.id.nav_rest_product) {
            HelperMethod.replaceFragment( new MyProductsFragment(), getSupportFragmentManager(), R.id.restaurantHomeActivityFrame, null, null );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.END );
        return true;
    }
}
