package com.example.abdelrahmansaleh.sofra.ui.fragment.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.adapter.ItemsFoodAdapter;
import com.example.abdelrahmansaleh.sofra.data.local.OrderRoom.ItemsOrder;
import com.example.abdelrahmansaleh.sofra.data.local.OrderRoom.OrderRoomDao;
import com.example.abdelrahmansaleh.sofra.data.local.OrderRoom.RoomManger;
import com.example.abdelrahmansaleh.sofra.ui.fragment.restaurant.FoodFragment;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFoodFragment extends Fragment {


    @BindView(R.id.itemFoodFgImage)
    ImageView itemFoodFgImage;
    @BindView(R.id.itemFoodFgTvTittle)
    TextView itemFoodFgTvTittle;
    @BindView(R.id.itemFoodFgTvDec)
    TextView itemFoodFgTvDec;
    @BindView(R.id.itemFoodFgTvPrice)
    TextView itemFoodFgTvPrice;
    @BindView(R.id.itemFoodFgTvPreparingTime)
    TextView itemFoodFgTvPreparingTime;
    @BindView(R.id.itemFoodFgEtSpecial)
    EditText itemFoodFgEtSpecial;
    @BindView(R.id.itemFoodFgEtAddOne)
    ImageButton itemFoodFgEtAddOne;
    @BindView(R.id.itemFoodFgEtQuantity)
    EditText itemFoodFgEtQuantity;
    @BindView(R.id.itemFoodFgSubOne)
    ImageButton itemFoodFgSubOne;
    @BindView(R.id.itemFoodFgBtnAddToCart)
    RelativeLayout itemFoodFgBtnAddToCart;
    Unbinder unbinder;
    private int quant = 1;
    private OrderRoomDao roomDao;

    public ItemFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_item_food, container, false );
        unbinder = ButterKnife.bind( this, view );
        itemFoodFgEtQuantity.setText( String.valueOf( quant ) );
        if (ItemsFoodAdapter.itemsFoodDataSelected != null) {
            Glide.with( getContext() ).load( ItemsFoodAdapter.itemsFoodDataSelected.getPhotoUrl() )
                    .into( itemFoodFgImage );
            itemFoodFgTvDec.setText( ItemsFoodAdapter.itemsFoodDataSelected.getDescription() );
            itemFoodFgTvPrice.setText( ItemsFoodAdapter.itemsFoodDataSelected.getPrice() + " EGP " );
            itemFoodFgTvPreparingTime.setText( getActivity().getResources().getString( R.string.preparingTime ) + " " + ItemsFoodAdapter.itemsFoodDataSelected.getPreparingTime() );
            itemFoodFgTvTittle.setText( ItemsFoodAdapter.itemsFoodDataSelected.getName() );
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    @OnClick({R.id.itemFoodFgEtAddOne, R.id.itemFoodFgSubOne, R.id.itemFoodFgBtnAddToCart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.itemFoodFgEtAddOne:
                itemFoodFgEtQuantity.setText( String.valueOf( ++quant ) );
                break;
            case R.id.itemFoodFgSubOne:
                if (quant == 1) {
                    StyleableToast.makeText( getContext(), "Not Valid", R.style.Error ).show();
                } else {
                    itemFoodFgEtQuantity.setText( String.valueOf( --quant ) );
                }
                break;
            case R.id.itemFoodFgBtnAddToCart:
                savInCart();
                break;
        }
    }

    private void savInCart() {
        roomDao = RoomManger.getInstance( getContext() ).roomDao();
        Executors.newSingleThreadExecutor().execute( new Runnable() {
            @Override
            public void run() {

                roomDao.onInsert( new ItemsOrder( ItemsFoodAdapter.itemsFoodDataSelected.getName()
                        , ItemsFoodAdapter.itemsFoodDataSelected.getCreatedAt()
                        , ItemsFoodAdapter.itemsFoodDataSelected.getPrice()
                        , ItemsFoodAdapter.itemsFoodDataSelected.getPreparingTime(),
                        ItemsFoodAdapter.itemsFoodDataSelected.getPhotoUrl(),
                        ItemsFoodAdapter.itemsFoodDataSelected.getRestaurantId().toString(),
                        ItemsFoodAdapter.itemsFoodDataSelected.getId().toString(),
                        itemFoodFgEtSpecial.getText().toString()
                ) );
                List<ItemsOrder> itemsOrders = roomDao.onGetAll();
                for (int i = 0; i < itemsOrders.size(); i++) {
                    Log.i( TAG, "savInCart: " + itemsOrders.get( i ).getName() );
                }

            }
        } );
    }
}
