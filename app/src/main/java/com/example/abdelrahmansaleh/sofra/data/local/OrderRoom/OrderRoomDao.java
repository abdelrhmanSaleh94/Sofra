package com.example.abdelrahmansaleh.sofra.data.local.OrderRoom;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.abdelrahmansaleh.sofra.data.local.OrderRoom.ItemsOrder;

import java.util.List;

@Dao
public interface OrderRoomDao {
    @Insert
    void onInsert(ItemsOrder... itemsOrders);

    @Update
    void onUpdate(ItemsOrder... itemsOrders);

    @Delete
    void onDelete(ItemsOrder... itemsOrders);

    @Query("select * from ItemsOrder")
    List<ItemsOrder> onGetAll();

}
