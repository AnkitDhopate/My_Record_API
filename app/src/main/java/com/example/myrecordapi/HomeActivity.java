package com.example.myrecordapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myrecordapi.adapter.RecyclerViewAdapter;
import com.example.myrecordapi.model.ApiModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ApiModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            final Call<ArrayList<ApiModel>> apiModelList = ApiClient.getInstance().getApi().getUsers();
            apiModelList.enqueue(new Callback<ArrayList<ApiModel>>() {
                @Override
                public void onResponse(Call<ArrayList<ApiModel>> call, Response<ArrayList<ApiModel>> response) {
                    if (response.code() == 201) {
                        for (ApiModel apiModel : response.body()) {
                            arrayList.add(apiModel);
                        }
                        RecyclerViewAdapter adapter = new RecyclerViewAdapter(arrayList, HomeActivity.this);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(HomeActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ApiModel>> call, Throwable t) {
                    Toast.makeText(HomeActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Error error) {
            Toast.makeText(this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}