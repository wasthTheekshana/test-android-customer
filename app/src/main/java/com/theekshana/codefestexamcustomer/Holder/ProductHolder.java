package com.theekshana.codefestexamcustomer.Holder;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theekshana.codefestexamcustomer.DeatilNewsMap;
import com.theekshana.codefestexamcustomer.Model.NewsModel;
import com.theekshana.codefestexamcustomer.Payment;
import com.theekshana.codefestexamcustomer.R;

public class ProductHolder extends RecyclerView.ViewHolder{

    public TextView titles, diss, prices, times;
    public ImageView images;
    public NewsModel newsModels;
    public String FCMTOken;
    public String getId;
    public Button accptBtns;

    public ProductHolder(@NonNull View itemView) {
        super(itemView);

        titles = itemView.findViewById(R.id.newsTitle);
        diss = itemView.findViewById(R.id.newsDis);
        prices = itemView.findViewById(R.id.newLoca);
        images = itemView.findViewById(R.id.imageView3);
        accptBtns = itemView.findViewById(R.id.button2);

        accptBtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(), "Job Accpect !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(itemView.getContext(), Payment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("title", titles.getText().toString());
                intent.putExtra("price", prices.getText().toString());
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
