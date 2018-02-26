package com.example.sandysatriya.stegandroid;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class DecodeActivity extends AppCompatActivity implements Runnable {

    private Context context;
    private Handler handler;
    private ProgressDialog dd;
    private Uri photoUri;

    private final Runnable runnableDismmiss = new Runnable() {

        public void run() {
            dd.dismiss();

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decoder);

        context = this;
        handler = new Handler();
        dd = new ProgressDialog(this);
        dd.setIndeterminate(true);
        dd.setMessage(context.getText(R.string.decoding));
        dd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dd.show();
        photoUri = getIntent().getData();
        Thread tt = new Thread(this, "Decoding Mobistego");
        tt.start();
    }

    public void run() {
        Bitmap image = null;

        try {

            Cursor cursor = getContentResolver().query(photoUri, null, null,
                    null, null);
            cursor.moveToFirst();

            int idx = cursor
                    .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            String absoluteFilePath = cursor.getString(idx);

            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inDither = false;
            opt.inScaled = false;
            opt.inDensity = 0;
            opt.inJustDecodeBounds = false;
            opt.inPurgeable = false;
            opt.inSampleSize = 1;
            opt.inScreenDensity = 0;
            opt.inTargetDensity = 0;
            image = BitmapFactory.decodeFile(absoluteFilePath, opt);

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
            ImageView imageView = (ImageView) findViewById(R.id.imageViewDecode);
            imageView.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }

        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getPixels(pixels, 0, image.getWidth(), 0, 0, image.getWidth(),
                image.getHeight());
        Log.v("Decode", "" + pixels[0]);
        Log.v("Decode Alpha", "" + (pixels[0] >> 24 & 0xFF));
        Log.v("Decode Red", "" + (pixels[0] >> 16 & 0xFF));
        Log.v("Decode Green", "" + (pixels[0] >> 8 & 0xFF));
        Log.v("Decode Blue", "" + (pixels[0] & 0xFF));
        Log.v("Decode", "" + pixels[0]);
        Log.v("Decode", "" + image.getPixel(0, 0));
        byte[] b = null;
        try {
            b = LSB2bit.convertArray(pixels);
        } catch (OutOfMemoryError er) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(context.getText(R.string.errorImageTooLarge))
                    .setCancelable(false).setPositiveButton(
                    context.getText(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int id) {
                            DecodeActivity.this.finish();
                        }
                    });
            handler.post(runnableDismmiss);
            AlertDialog alert = builder.create();
            alert.show();
            return;
        }
        final String vvv = LSB2bit.decodeMessage(b, image.getWidth(), image
                .getHeight());
        handler.post(runnableDismmiss);
        if (vvv == null) {
            handler.post(new Runnable() {

                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            context);
                    builder.setMessage(
                            context.getText(R.string.errorNoSteganDroidImage))
                            .setCancelable(false).setPositiveButton(
                            context.getText(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    DecodeActivity.this.finish();
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        } else {
            Log.v("Coded message", vvv);
            Runnable runnableSetText = new Runnable() {

                public void run() {
                    TextView textDec = (TextView) findViewById(R.id.TextViewDecodedMessage);
                    textDec.setText(vvv);
                }
            };
            handler.post(runnableSetText);

        }
    }
}