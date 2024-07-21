package com.example.geekbank_sms_manager;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SmsAdapter smsAdapter;
    private List<Sms> smsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        smsList = new ArrayList<>();
        smsAdapter = new SmsAdapter(smsList);
        recyclerView.setAdapter(smsAdapter);

        Button receivedMessagesButton = findViewById(R.id.received_messages_button);
        Button sentMessagesButton = findViewById(R.id.sent_messages_button);

        receivedMessagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Received Messages Button Clicked");
                Toast.makeText(MainActivity.this, "Received Messages Button Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ReceivedMessagesActivity.class);
                intent.putExtra("smsList", new ArrayList<>(smsList));
                startActivity(intent);
            }
        });

        sentMessagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Sent Messages Button Clicked");
                Toast.makeText(MainActivity.this, "Sent Messages Button Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SentMessagesActivity.class);
                startActivity(intent);
            }
        });

        logAllSms();
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
        }
    }



    private void logAllSms() {
        Uri uri = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            uri = Telephony.Sms.CONTENT_URI;
        }
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS));
                @SuppressLint("Range") String body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(Telephony.Sms.DATE));
                Log.d(TAG, "SMS from " + address + " : " + body + " on " + date);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    public void addSms(Sms sms) {
        smsList.add(sms);
        smsAdapter.notifyDataSetChanged();
    }
}