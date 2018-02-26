package com.example.sandysatriya.stegandroid;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by SandySatriya on 4/19/2017.
 */
public class ProgressBar extends ProgressDialog {


    /**
     * @param context
     */
    public ProgressBar(Context context) {
        super(context);
        setProgressStyle(STYLE_HORIZONTAL);
    }
}
