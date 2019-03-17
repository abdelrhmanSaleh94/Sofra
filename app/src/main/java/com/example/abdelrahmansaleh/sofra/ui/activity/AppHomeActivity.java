package com.example.abdelrahmansaleh.sofra.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.helper.HelperMethod;
import com.example.abdelrahmansaleh.sofra.ui.fragment.restaurant.RestaurantsFragment;
import com.example.abdelrahmansaleh.sofra.ui.fragment.user.LoginUser;
import com.example.abdelrahmansaleh.sofra.ui.fragment.user.OfferFragment;
import com.example.abdelrahmansaleh.sofra.ui.fragment.user.OrderUerFragment;

public class AppHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_app_home );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setTitle( "" );
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
        HelperMethod.replaceFragment( new LoginUser(), getSupportFragmentManager(), R.id.counterFrame, null, null );
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

        if (id == R.id.nav_home) {
            HelperMethod.replaceFragment( new RestaurantsFragment(), getSupportFragmentManager(), R.id.counterFrame, null, null );
        } else if (id == R.id.nav_order) {
            HelperMethod.replaceFragment( new OrderUerFragment(), getSupportFragmentManager(), R.id.counterFrame, null, null );
        } else if (id == R.id.nav_notification) {

        } else if (id == R.id.nav_offer) {
            HelperMethod.replaceFragment( new OfferFragment(), getSupportFragmentManager(), R.id.counterFrame, null, null );

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_conditions) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_connect) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.END );
        return true;
    }
}
