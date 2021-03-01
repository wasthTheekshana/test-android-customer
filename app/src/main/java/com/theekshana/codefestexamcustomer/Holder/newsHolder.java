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
import com.theekshana.codefestexamcustomer.MapsActivity;
import com.theekshana.codefestexamcustomer.Model.NewsModel;
import com.theekshana.codefestexamcustomer.R;
import com.theekshana.codefestexamcustomer.newsFeedMap;

public class newsHolder extends RecyclerView.ViewHolder {

    public TextView title, dis, loc, time;
    public ImageView image;
    public NewsModel newsModel;
    public String FCMTOken;
    public Button accptBtn;

    public newsHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.newsTitle);
        dis = itemView.findViewById(R.id.newsDis);
        loc = itemView.findViewById(R.id.newLoca);
        time = itemView.findViewById(R.id.newTime);
        image = itemView.findViewById(R.id.imageView3);
        accptBtn = itemView.findViewById(R.id.button2);

        accptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(), "Job Accpect !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(itemView.getContext(), MapsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("jobDocId", title.getText().toString());
                intent.putExtra("riderDocId", loc.getText().toString());
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
