package com.example.abdelrahmansaleh.sofra.helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HelperMethod {
    private static Calendar myCalendar;
    private static ProgressDialog checkDialog = null;

    public static void replaceFragment(Fragment fragment, FragmentManager supportFragmentManager, int id, TextView tool_Bar_Tittle, String tittle) {
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.replace( id, fragment );
        transaction.addToBackStack( null );
        transaction.commit();
        if (tool_Bar_Tittle != null) {
            tool_Bar_Tittle.setText( tittle );
        }

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService( Context.INPUT_METHOD_SERVICE );

        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow( currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS );
        }
    }


    public static void getDate(final EditText editText, Activity activity) {
        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalendar.set( Calendar.YEAR, year );
                myCalendar.set( Calendar.MONTH, month );
                myCalendar.set( Calendar.DAY_OF_MONTH, dayOfMonth );
                updateLabel( editText );

            }
        };
        new DatePickerDialog( activity, date, myCalendar.get( Calendar.YEAR )
                , myCalendar.get( Calendar.MONTH ), myCalendar.get( Calendar.DAY_OF_MONTH ) ).show();
    }

    private static void updateLabel(EditText editText) {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat( myFormat, Locale.US );
        editText.setText( sdf.format( myCalendar.getTime() ) );
    }

    public static String formatDate(String dateFormat) {
        try {
            Date date = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).parse( dateFormat );
            String newstring = new SimpleDateFormat( "yyyy-MM-dd" ).format( date );
            return newstring;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void showProgressDialog(Activity activity, String title) {
        try {
            if (checkDialog == null) {
                checkDialog = new ProgressDialog( activity );
                checkDialog.setMessage( title );
                checkDialog.setIndeterminate( false );
                checkDialog.setCancelable( false );
            }
            checkDialog.show();

        } catch (Exception e) {

        }
    }

    public static void dismissProgressDialog() {
        try {
            if (checkDialog != null && checkDialog.isShowing()) {
                checkDialog.dismiss();
            }
        } catch (Exception e) {

        }
    }

    public static RequestBody convertToRequestBody(String part) {
        RequestBody requestBody = RequestBody.create( MediaType.parse( "multipart/form-data" ), part );
        return requestBody;
    }

    public static MultipartBody.Part convertFileToMultipart(String pathImageFile, String Key) {
        File file = new File( pathImageFile );
        RequestBody reqFileselect = RequestBody.create( MediaType.parse( "image/*" ), file );
        MultipartBody.Part Imagebody = MultipartBody.Part.createFormData( Key, file.getName(), reqFileselect );
        return Imagebody;
    }

    public static void openAlbum(int Counter, Context context,
                                 final ArrayList<AlbumFile> ImagesFiles, Action<ArrayList<AlbumFile>> action) {
        Album album = new Album();
        Album.initialize( AlbumConfig.newBuilder( context )
                .setAlbumLoader( new MediaLoader() )
                .setLocale( Locale.ENGLISH ).build() );
        album.image( context )// Image and video mix options.
                .multipleChoice()// Multi-Mode, Single-Mode: singleChoice().
                .columnCount( 3 ) // The number of columns in the page list.
                .selectCount( Counter )  // Choose up to a few images.
                .camera( true ) // Whether the camera appears in the Item.
                .checkedList( ImagesFiles ) // To reverse the list.
                .widget(
                        Widget.newLightBuilder( context )
                                .title( "" )
                                .statusBarColor( Color.WHITE ) // StatusBar color.
                                .toolBarColor( Color.WHITE ) // Toolbar color.
                                .navigationBarColor( Color.WHITE ) // Virtual NavigationBar color of Android5.0+.
                                .mediaItemCheckSelector( Color.BLUE, Color.GREEN ) // Image or video selection box.
                                .bucketItemCheckSelector( Color.RED, Color.YELLOW ) // Select the folder selection box.
                                .build()
                )
                .onResult( action )
                .onCancel( new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
// The user canceled the operation.
                    }
                } )
                .start();
    }
}
