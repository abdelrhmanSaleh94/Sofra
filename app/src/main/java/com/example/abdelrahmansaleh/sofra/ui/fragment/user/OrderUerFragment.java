package com.example.abdelrahmansaleh.sofra.ui.fragment.user;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.ui.fragment.restaurant.FoodFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderUerFragment extends Fragment {


    @BindView(R.id.myOrderTabLayout)
    TabLayout myOrderTabLayout;
    @BindView(R.id.myOrderVPager)
    ViewPager myOrderVPager;
    Unbinder unbinder;
    Fragment[] fragmentes = {new CurrentOrderFragment(), new CompleteOrderFragment()};

    public OrderUerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_order_uer, container, false );
        unbinder = ButterKnife.bind( this, view );
        PagerAdapterOrder adapterOrder = new PagerAdapterOrder( getActivity().getSupportFragmentManager() );
        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {

            try {
                CurrentOrderFragment currentOrderFragment = (CurrentOrderFragment) fragment;
                getFragmentManager().beginTransaction().remove( fragment ).commit();
            } catch (Exception e) {

            }
        }
        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {

            try {
                CompleteOrderFragment completeOrderFragment = (CompleteOrderFragment) fragment;
                getFragmentManager().beginTransaction().remove( fragment ).commit();
            } catch (Exception e) {

            }
        }
        myOrderVPager.setAdapter( adapterOrder );
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class PagerAdapterOrder extends FragmentPagerAdapter {
        public PagerAdapterOrder(FragmentManager fm) {
            super( fm );
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentes[position];
        }

        @Override
        public int getCount() {
            return fragmentes.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position) {
                case 0:
                    title = getActivity().getResources().getString( R.string.currentOrder );
                    break;
                case 1:
                    title = getActivity().getResources().getString( R.string.completeOrder );
                    break;
            }
            return title;
        }
    }
}
