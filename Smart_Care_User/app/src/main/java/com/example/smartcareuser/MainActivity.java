package com.example.smartcareuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcareuser.databinding.ActivityMainBinding;
import com.example.smartcareuser.databinding.ActivityUserDetailsBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference,doc_idRef,nurseIdRef;
    FirebaseDatabase firebaseDatabase;
    private ActivityMainBinding binding;
    SharedPreference sharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        sharedPreference = SharedPreference.getPreferences(MainActivity.this);




        binding.tvForgotKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final View mview = LayoutInflater.from(MainActivity.this).inflate(R.layout.forgot_id_help, null);
                builder.setView(mview);
                final AlertDialog dialog_condition = builder.create();
                dialog_condition.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog_condition.show();

            }
        });




        binding.btGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (binding.devKey.getText().toString().isEmpty())
                {
                    binding.devKey.setError("Invalid Key");
                }
                else
                {
                    firebaseDatabase=FirebaseDatabase.getInstance();
                    databaseReference=firebaseDatabase.getReference("patients/"+binding.devKey.getText().toString()+"/device_id");

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //value match for login

                            if(binding.devKey.getText().toString().equals(dataSnapshot.getValue(String.class)))
                            {

                                sharedPreference.setData("login");
                                sharedPreference.setDevice_id(binding.devKey.getText().toString());
                                Intent intent=new Intent(MainActivity.this,UserDetailsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else {

                                loginFailed();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {


                            Toast.makeText(MainActivity.this, "Something Wrong !", Toast.LENGTH_SHORT).show();

                        }
                    });
                }



            }
        });

    }
    public void loginFailed()
    {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mSnackBar = Snackbar.make(parentLayout, "Login Failed ! ID Not Match", Snackbar.LENGTH_LONG);
        View view = mSnackBar.getView();
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        view.setBackgroundColor(Color.RED);
        TextView mainTextView = (TextView) (view).findViewById(R.id.snackbar_text);
        mainTextView.setTextColor(Color.WHITE);
        mSnackBar.show();
    }
}