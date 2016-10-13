package com.qyadat.fares;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnSetWall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSetWall = (Button) findViewById(R.id.btnWallpaper);
        btnSetWall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                openWallpaperSettings();

            }
        });
    }
    private void openWallpaperSettings() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 16) {
            intent = new Intent("android.service.wallpaper.CHANGE_LIVE_WALLPAPER");
            intent.putExtra("android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT", new ComponentName(getApplicationContext(), LiveWallpaperService.class));
            startActivity(intent);
            return;
        }
        Toast.makeText(this, "Choose '" + getString(R.string.app_name) + "' in the list to start the Live Wallpaper.", Toast.LENGTH_LONG).show();
        intent = new Intent();
        intent.setAction("android.service.wallpaper.LIVE_WALLPAPER_CHOOSER");
        startActivity(intent);
    }
}
