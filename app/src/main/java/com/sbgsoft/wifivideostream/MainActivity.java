package com.sbgsoft.wifivideostream;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static Context mContext;
    Button connectButton;
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
        ssidText = findViewById(R.id.editTextSSID);
        passwordText = findViewById(R.id.editTextPassword);
        outputConsole = findViewById(R.id.editTextOutputConsole);

        connectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Close the keyboard
                passwordText.onEditorAction(EditorInfo.IME_ACTION_DONE);
                ssidText.onEditorAction(EditorInfo.IME_ACTION_DONE);

                outputConsole.append("Connecting to: " + ssidText.getText() + "...\n");

                wifiManager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(true);

                WifiConfiguration wifiConfig = new WifiConfiguration();
                wifiConfig.SSID = String.format("\"%s\"", ssidText.getText());
                wifiConfig.preSharedKey = String.format("\"%s\"", passwordText.getText());

                int netId = wifiManager.addNetwork(wifiConfig);
                wifiManager.disconnect();
                wifiManager.enableNetwork(netId, true);
                wifiManager.reconnect();

                outputConsole.append("Connection complete\n");
            }
        });
    }
}