package com.theekshana.codefestexamcustomer.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theekshana.codefestexamcustomer.R;

public class ticketHolder extends RecyclerView.ViewHolder {

    public TextView titles,dis,complain;

    public ticketHolder(@NonNull View itemView) {
        super(itemView);
        titles = itemView.findViewById(R.id.newsTitle);
        dis = itemView.findViewById(R.id.newsDis);
        complain = itemView.findViewById(R.id.newLoca);

    }
}
