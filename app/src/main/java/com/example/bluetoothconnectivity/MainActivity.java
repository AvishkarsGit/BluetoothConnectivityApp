package com.example.bluetoothconnectivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.example.bluetoothconnectivity.R;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import static android.Manifest.permission.*;
public class MainActivity extends AppCompatActivity {

    Button get;
    private static final int PER_REQ_CODE = 1;
    private static final int BT_REQ_CODE = 12;

    ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = findViewById(R.id.tgbtn);

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (toggleButton.isChecked()){
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), BLUETOOTH) ==
                            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),BLUETOOTH_CONNECT)
                            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),BLUETOOTH_ADMIN)== PackageManager.PERMISSION_GRANTED){
                        if (bluetoothAdapter == null){
                            Toast.makeText(getApplicationContext(), "Bluetooth not support to these device", Toast.LENGTH_SHORT).show();
                        }else{
                            if (!bluetoothAdapter.isEnabled()){
                                Intent i =new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                                startActivityForResult(i,BT_REQ_CODE);
                            }
                        }
                    }
                    else {
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{BLUETOOTH,BLUETOOTH_CONNECT,BLUETOOTH_ADMIN},PER_REQ_CODE);

                    }
                }
                else {
                        if (bluetoothAdapter.isEnabled()){
                            bluetoothAdapter.disable();
                        }else {
                            Toast.makeText(getApplicationContext(), "bluetooth already oFf", Toast.LENGTH_SHORT).show();
                        }
                    }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == BT_REQ_CODE){
                if (requestCode == RESULT_OK){
                    Toast.makeText(getApplicationContext(), "Bluetooth is enabled..", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        if (requestCode == PER_REQ_CODE){
            if (grantResults.length>0){
                int b=grantResults[0];
                int bc=grantResults[1];
                int ba=grantResults[2];

                if (b == PackageManager.PERMISSION_GRANTED && bc == PackageManager.PERMISSION_GRANTED && ba == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this, "Permission is granted...", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Permission is denied!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}