package com.example.myrecordapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myrecordapi.model.ApiModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText name, email, phone, address ;
    private Button submit, getRecords ;
    private ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        submit = findViewById(R.id.submit_btn);
        getRecords = findViewById(R.id.records_btn);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("Creating a new user record");
        progressDialog.setMessage("Please wait while we check the credentials ...");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                submitForm(name, email, phone, address);
            }
        });

        getRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }
        });

    }

    private void submitForm(TextInputEditText name, TextInputEditText email, TextInputEditText phone, TextInputEditText address)
    {
        if(TextUtils.isEmpty(name.getText()))
        {
            progressDialog.dismiss();
            name.setError("Name cannot be empty");
            return;
        }else if(TextUtils.isEmpty(email.getText()) || !Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches())
        {
            progressDialog.dismiss();
            email.setError("Invalid Email");
            return;
        }else if(TextUtils.isEmpty(phone.getText()))
        {
            progressDialog.dismiss();
            phone.setError("Name cannot be empty");
            return;
        }else if(TextUtils.isEmpty(address.getText()))
        {
            progressDialog.dismiss();
            address.setError("Name cannot be empty");
            return;
        }else
        {
            ApiModel apiModel = new ApiModel(name.getText().toString(), email.getText().toString(), Double.parseDouble(phone.getText().toString()), address.getText().toString());
            try {
                Call<ApiModel> apiModelCall = ApiClient.getInstance().getApi().postUser(apiModel);
                apiModelCall.enqueue(new Callback<ApiModel>() {
                    @Override
                    public void onResponse(Call<ApiModel> call, Response<ApiModel> response) {
                        if(response.code() == 201)
                        {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        }else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Error: "+response.code() , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiModel> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Error error)
            {
                progressDialog.dismiss();
                Toast.makeText(this, "Error : " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}