package com.example.sandysatriya.stegandroid;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final int AMBIL_GAMBAR = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intentku= new Intent(MainActivity.this,Help.class);
            startActivity(intentku);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case (AMBIL_GAMBAR) :
                if (resultCode == RESULT_OK) {
                    Uri photoUri = intent.getData();
                    if (photoUri != null) {
                        try {
                            Intent intent1 = new Intent();
                            intent1.setData(photoUri);
                            intent1.setComponent(new ComponentName(DecodeActivity.class.getPackage().getName(),
                                    DecodeActivity.class.getCanonicalName()));
                            startActivity(intent1);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            default:
                break;

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_encode) {
            Intent intentku= new Intent(MainActivity.this,EncodeActivity.class);
            startActivity(intentku);

        } else if (id == R.id.nav_decode) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/png");
            startActivityForResult(photoPickerIntent, AMBIL_GAMBAR);

        } else if (id == R.id.nav_help) {
            Intent intentku= new Intent(MainActivity.this,Help.class);
            startActivity(intentku);

        } else if (id == R.id.nav_about) {
            Intent intentku= new Intent(MainActivity.this,About.class);
            startActivity(intentku);

        } else if (id == R.id.nav_exit) {
            AlertDialog.Builder keluar = new AlertDialog.Builder(MainActivity.this);
            keluar.setMessage("Are You Sure ?").setCancelable(false).setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    MainActivity.this.finish();
                }
            }).setNegativeButton("No", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog1 = keluar.create();
            dialog1.setTitle("Exit");
            dialog1.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
