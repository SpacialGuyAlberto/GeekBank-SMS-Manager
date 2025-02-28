package com.example.geekbank_sms_manager;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SentMessagesActivity extends AppCompatActivity {

    private SmsAdapter smsAdapter;
    private List<Sms> smsList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_messages);

        dbHelper = new DatabaseHelper(this);
        smsList = fetchSentMessages();

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
    }

    private List<Sms> fetchSentMessages() {
        List<Sms> messages = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_SMS, null, DatabaseHelper.COLUMN_SENT + "=1", null, null, null, null);

        Log.d("SentMessagesActivity", "Cursor count: " + cursor.getCount()); // Log cursor count

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String phoneNumber = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PHONE_NUMBER));
            @SuppressLint("Range") String messageBody = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_BODY));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
            Sms sms = new Sms(phoneNumber, messageBody, date);
            Log.d("SentMessagesActivity", "Fetched SMS: " + phoneNumber + ", " + messageBody + ", " + date); // Log fetched SMS
            messages.add(sms);
        }
        cursor.close();
        db.close();
        return messages;
    }
}