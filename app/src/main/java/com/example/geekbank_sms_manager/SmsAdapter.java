package com.example.geekbank_sms_manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.SmsViewHolder> {

    private List<Sms> smsList;

    public SmsAdapter(List<Sms> smsList) {
        this.smsList = smsList;
    }

    @NonNull
    @Override
    public SmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sms_item, parent, false);
        return new SmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsViewHolder holder, int position) {
        Sms sms = smsList.get(position);
        holder.phoneNumber.setText(sms.getPhoneNumber());
        holder.messageBody.setText(sms.getMessageBody());
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }

    public static class SmsViewHolder extends RecyclerView.ViewHolder {
        TextView phoneNumber;
        TextView messageBody;

        public SmsViewHolder(View itemView) {
            super(itemView);
            phoneNumber = itemView.findViewById(R.id.textViewPhoneNumber);
            messageBody = itemView.findViewById(R.id.textViewMessageBody);
        }
    }
}