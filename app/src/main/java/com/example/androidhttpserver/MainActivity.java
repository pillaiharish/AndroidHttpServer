package com.example.androidhttpserver;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private AndroidHttpServer webServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String ipAddress = NetworkUtils.getHotspotIPAddress(this);
//        String ipAddress = NetworkUtils.getWifiIpAddress(this);
        // getHotspotIPAdd is working in android mobile
//        String ipAddress = NetworkUtils.getHotspotIPAdd(this);
        String ipAddress = NetworkUtils.getHotspotIP(this);
        Toast.makeText(this, "Hotspot IP: " + ipAddress, Toast.LENGTH_LONG).show();
        webServer = new AndroidHttpServer(8080, this);
        try {
            webServer.startServer();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "The server could not be started.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webServer != null) {
            webServer.stop();
        }
    }
}