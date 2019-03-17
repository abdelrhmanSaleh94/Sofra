package com.example.abdelrahmansaleh.sofra.data.local.OrderRoom;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {ItemsOrder.class}, version = 1)
@TypeConverters(DataTypeConverter.class)
public abstract class RoomManger extends RoomDatabase {
    public static RoomManger roomManger;
    public abstract OrderRoomDao roomDao();
    public static synchronized RoomManger getInstance(Context context) {
        if (roomManger == null) {
            roomManger= Room.databaseBuilder( context.getApplicationContext(),RoomManger.class,
                    "sofraDataBase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return roomManger;
    }

}
