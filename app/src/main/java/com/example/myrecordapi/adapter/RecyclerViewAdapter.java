package com.example.myrecordapi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecordapi.R;
import com.example.myrecordapi.model.ApiModel;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>
{
    private ArrayList<ApiModel> apiModelList ;
    private Context context;

    public RecyclerViewAdapter(ArrayList<ApiModel> apiModelList, Context context) {
        this.apiModelList = apiModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.name.setText("Name: "+apiModelList.get(position).getName());
        holder.email.setText("Email: "+apiModelList.get(position).getEmail());
        String phone_no = apiModelList.get(position).getPhone().toString();
        holder.phone.setText("Phone: "+phone_no);
        holder.address.setText("Address: "+apiModelList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return apiModelList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name, email, phone, address ;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.user_name);
            email = itemView.findViewById(R.id.user_email);
            phone = itemView.findViewById(R.id.user_Phone);
            address = itemView.findViewById(R.id.user_address);
        }
    }
}
