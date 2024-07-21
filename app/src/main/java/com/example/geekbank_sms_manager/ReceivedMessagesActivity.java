package com.example.geekbank_sms_manager;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ReceivedMessagesActivity extends AppCompatActivity {

    private SmsAdapter smsAdapter;
    private List<Sms> smsList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_messages);

        smsList = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);

        // Leer los mensajes desde la base de datos
        readSmsFromDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        smsAdapter = new SmsAdapter(smsList);
        recyclerView.setAdapter(smsAdapter);

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllMessages();
            }
        });

        Log.d("ReceivedMessagesActivity", "Number of SMS received: " + smsList.size());
    }

    private void readSmsFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_SMS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String phoneNumber = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PHONE_NUMBER));
                @SuppressLint("Range") String messageBody = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_BODY));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));

                Sms sms = new Sms(phoneNumber, messageBody, date);
                smsList.add(sms);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
    }

    private void deleteAllMessages() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_SMS, null, null);
        smsList.clear();
        smsAdapter.notifyDataSetChanged();
        db.close();
    }
}