package com.vmc.vmcdaily.utility;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.format.DateUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utility {

    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream;
        Bitmap bitmap = null;
        try {
            inputStream = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static boolean isConnectedToInternet(AppCompatActivity activity){
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected){
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return isConnected;
    }

    public static String getTimeSpanStringFromDate(Context context, String dateString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(dateString);
            return DateUtils.formatDateTime(context, date.getTime(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_TIME);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static String getമലയാളംFormattedDateString(Context context, String dateString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = simpleDateFormat.parse(dateString);
            // get day of week
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            String ദിവസം = "";
            switch (day){
                case 1 : ദിവസം = "ഞായർ"; break;
                case 2 : ദിവസം = "തിങ്കൾ"; break;
                case 3 : ദിവസം = "ചൊവ്വ"; break;
                case 4 : ദിവസം = "ബുധൻ"; break;
                case 5 : ദിവസം = "വ്യാഴം"; break;
                case 6 : ദിവസം = "വെള്ളി"; break;
                case 7 : ദിവസം = "ശനി"; break;
            }

            if(DateUtils.isToday(date.getTime())){
                return "ഇന്ന്";
            }else {
                return ദിവസം + ", " + DateUtils.formatDateTime(context,
                        date.getTime(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_MONTH | DateUtils.FORMAT_SHOW_YEAR);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static void hideKeyboard(AppCompatActivity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

    }

    public static String getRelativeTimeSpanStringFromDate(Context context, String dateString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(dateString);
            return DateUtils.getRelativeDateTimeString(context, date.getTime(), DateUtils.DAY_IN_MILLIS, DateUtils.DAY_IN_MILLIS, 0).toString();

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static String getTimeStringFromDate(String dateString){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("h:mm a");
        // hh:mm a
        try {
            Date date = inputFormat.parse(dateString);
            return outputFormat.format(date);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static Intent getDialIntent(String phoneNo) {
        String uri = "tel:" + phoneNo.trim();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        return intent;
    }

    public static void notifySound(Context context){
        /*Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.sound);
        MediaPlayer mediaPlayer= MediaPlayer.create(context, uri);
        mediaPlayer.start();*/
    }
}
