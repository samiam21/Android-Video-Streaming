package com.sbgsoft.wifivideostream;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    Button connectButton;
    EditText ssidText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectButton = findViewById(R.id.connectButton);
        ssidText = findViewById(R.id.editTextSSID);
        passwordText = findViewById(R.id.editTextPassword);

        connectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Close the keyboard
                passwordText.onEditorAction(EditorInfo.IME_ACTION_DONE);
                ssidText.onEditorAction(EditorInfo.IME_ACTION_DONE);
                
                Snackbar.make(v, "Connecting to: " + ssidText.getText() + " | " + passwordText.getText(), Snackbar.LENGTH_LONG).show();
            }
        });
    }
}