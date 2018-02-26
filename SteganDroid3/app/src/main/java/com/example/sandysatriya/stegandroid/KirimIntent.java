package com.example.sandysatriya.stegandroid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by SandySatriya on 4/19/2017.
 */

public class KirimIntent {

    private Uri uri;
    private final Context context;

    public Uri getUri() {

        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }


    /**
     * Create a template to send MMS with encoded image.
     * @param uri Uri of the encoded image.
     * @param context Handler to start Activity.
     */
    public KirimIntent(Uri uri,Context context) {
        this.uri = uri;
        this.context=context;
    }


    /**
     * Invoke default android editor MMS, with encoded image attached.
     */
    public void send()
    {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM,uri);
        sendIntent.setType("image/png");
        context.startActivity(sendIntent);
    }

}