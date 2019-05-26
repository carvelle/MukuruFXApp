package za.co.makuru.makurufx.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import za.co.makuru.makurufx.R;


public class AppUtils {

    private AppUtils() {
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static String getDateFromTimestamp(String timestamp){
            Date date = new java.util.Date(Long.valueOf(timestamp)*1000L);
            DateFormat formatter;
            formatter = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm:ss:S");
            return formatter.format(date);

    }
}
