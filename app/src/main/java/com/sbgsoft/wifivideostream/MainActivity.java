package com.sbgsoft.wifivideostream;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private static Context mContext;
    private WifiManager.LocalOnlyHotspotReservation mReservation;
    Button connectButton;
    Button disconnectButton;
    Button getIpButton;
    EditText ssidText;
    EditText passwordText;
    TextView outputConsole;
    WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();

        connectButton = findViewById(R.id.connectButton);
        disconnectButton = findViewById(R.id.disconnectButton);
        getIpButton = findViewById(R.id.getIpButton);
        ssidText = findViewById(R.id.editTextSSID);
        passwordText = findViewById(R.id.editTextPassword);
        outputConsole = findViewById(R.id.editTextOutputConsole);

        // Initialize the WiFi and WiFi AP manager
        wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
                // Close the keyboard
                passwordText.onEditorAction(EditorInfo.IME_ACTION_DONE);
                ssidText.onEditorAction(EditorInfo.IME_ACTION_DONE);

                outputConsole.append("Turning on WiFi Hotspot...\n");

                // Check to make sure we have permissions
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    outputConsole.append("OH NO!");
                    return;
                }

                // TODO: Configure the hotspot info

                // Start up the hotspot
                wifiManager.startLocalOnlyHotspot(new WifiManager.LocalOnlyHotspotCallback() {

                    @Override
                    public void onStarted(WifiManager.LocalOnlyHotspotReservation reservation) {
                        super.onStarted(reservation);
                        outputConsole.append("WiFi Hotspot is on now!\n");
                        mReservation = reservation;

                        // Show the connection info
                        WifiConfiguration currentConfig = mReservation.getWifiConfiguration();
                        outputConsole.append("SSID: " + currentConfig.SSID + " | PW: " + currentConfig.preSharedKey + "\n");

                        // Get the gateway address
                        String[] tmp = currentConfig.toString().split("\n");
                        outputConsole.append(tmp[3] + "\n");
                    }

                    @Override
                    public void onStopped() {
                        super.onStopped();
                        outputConsole.append("WiFi Hotspot is off now!\n");
                    }

                    @Override
                    public void onFailed(int reason) {
                        super.onFailed(reason);
                        outputConsole.append("Failed...\n");
                    }
                }, new Handler());
            }
        });

        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
                if (mReservation != null) {
                    mReservation.close();
                }
                outputConsole.append("WiFi Hotspot is off now!\n");
            }
        });

        getIpButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
                String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
                outputConsole.append("IP: " + ip + "\n");
            }
        });
    }
}