package com.example.smartcareuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.smartcareuser.databinding.ActivityMainBinding;
import com.example.smartcareuser.databinding.ActivityUserDetailsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.TooManyListenersException;
import java.util.jar.JarEntry;

public class UserDetailsActivity extends AppCompatActivity {

    private static final String TAG ="fsdfsd" ;
    private ActivityUserDetailsBinding binding;
    private ArrayList<Model_data> artistList;
    public static final int request_call = 0;

    SharedPreference sharedPreference;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference name_reference, age_reference, bpm_reference, weight_reference, gender_reference, suggestion_ref, time_ref, doc_nameRef, doc_phoneRef, doc_mailRef, nurse_nameRef, nurse_phoneRef, nurse_emailRef, doc_idRef, nurseIdRef;
    ;
    String device_id, doc_id, nurse_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        sharedPreference = SharedPreference.getPreferences(UserDetailsActivity.this);


        binding.tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreference.setData("none");
                sharedPreference.setDevice_id("none");
                sharedPreference.setdocId("none");
                sharedPreference.setNusId("none");
                //  stopService(new Intent(UserDetailsActivity.this,myBackgroundProcess.class));
                Intent intent = new Intent(UserDetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // data comes from login reference

        device_id = sharedPreference.getDevice_id();


        ActivityCompat.requestPermissions(UserDetailsActivity.this, new String[]{

                Manifest.permission.FOREGROUND_SERVICE
        }, PackageManager.PERMISSION_GRANTED);


        startService(new Intent(UserDetailsActivity.this, myBackgroundProcess.class));


        firebaseDatabase = FirebaseDatabase.getInstance();
        name_reference = firebaseDatabase.getReference("patients/" + device_id + "/patient_name");
        age_reference = firebaseDatabase.getReference("patients/" + device_id + "/patient_age");
        weight_reference = firebaseDatabase.getReference("patients/" + device_id + "/patient_weight");
        bpm_reference = firebaseDatabase.getReference("patients/" + device_id + "/bpm");
        gender_reference = firebaseDatabase.getReference("patients/" + device_id + "/patient_gender");
        doc_idRef = firebaseDatabase.getReference("patients/" + device_id + "/doctor_id");
        nurseIdRef = firebaseDatabase.getReference("patients/" + device_id + "/nurse_id");


        nurseIdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                sharedPreference.setNusId(dataSnapshot.getValue(String.class));

                nurse_nameRef = firebaseDatabase.getReference("employee/" + sharedPreference.getNusId() + "/name");
                nurse_phoneRef = firebaseDatabase.getReference("employee/" + sharedPreference.getNusId() + "/cell");
                nurse_emailRef = firebaseDatabase.getReference("employee/" + sharedPreference.getNusId() + "/email");

                nurse_nameRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        binding.tvNurseName.setText("Name : " + dataSnapshot.getValue(String.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                nurse_phoneRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        binding.tvNursePhone.setText("  " + dataSnapshot.getValue(String.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                nurse_emailRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        binding.nurseMail.setText("  " + dataSnapshot.getValue(String.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        doc_idRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                sharedPreference.setdocId(dataSnapshot.getValue(String.class));

                doc_nameRef = firebaseDatabase.getReference("employee/" + sharedPreference.getDocId() + "/name");
                doc_phoneRef = firebaseDatabase.getReference("employee/" + sharedPreference.getDocId() + "/cell");
                doc_mailRef = firebaseDatabase.getReference("employee/" + sharedPreference.getDocId() + "/email");

                doc_nameRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        binding.tvDoctorName.setText("Name : " + dataSnapshot.getValue(String.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                doc_phoneRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        binding.tvDoctorPhone.setText("  " + dataSnapshot.getValue(String.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                doc_mailRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        binding.docMail.setText("  " + dataSnapshot.getValue(String.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        suggestion_ref = firebaseDatabase.getReference("suggestion/" + sharedPreference.getDevice_id() + "/suggestion");

        suggestion_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String data = "" + dataSnapshot.getValue(String.class);
                if (!data.equals("null")) {
                    binding.tvShowSuggestion.setText("" + dataSnapshot.getValue(String.class));

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        time_ref = firebaseDatabase.getReference("suggestion/" + sharedPreference.getDevice_id() + "/last_send");

        time_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String data = "" + dataSnapshot.getValue(String.class);
                if (!data.equals("null")) {
                    binding.time.setText("" + dataSnapshot.getValue(String.class));

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        name_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String get_name = dataSnapshot.getValue(String.class);

                binding.tvPatientName.setText("" + get_name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        age_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String get_age = dataSnapshot.getValue(String.class);
                binding.tvAge.setText("Age: " + get_age);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        weight_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String get_weight = dataSnapshot.getValue(String.class);
                binding.tvPatientWeight.setText("Weight: " + get_weight);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        gender_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String get_gender = dataSnapshot.getValue(String.class);
                binding.tvPatientGender.setText("Gender: " + get_gender);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        bpm_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {

                    //  sharedPreference.setData("login");
                    //  sharedPreference.setDevice_id(device_id);

                    String get_bpm = dataSnapshot.getValue(String.class);
                    binding.tvCurrentHeartRate.setText("  BPM: " + get_bpm);


                    int bpm = Integer.parseInt("" + get_bpm);
                    if (bpm < 60 || bpm > 100) {
                        binding.tvCurrentHeartRate.setTextColor(Color.parseColor("#FF2A2A"));
                        binding.tvHealthStatus.setTextColor(Color.parseColor("#FF2A2A"));
                        binding.tvHealthStatus.setText("Health Status : Dangerous");
                    } else {
                        binding.tvHealthStatus.setText("Health Status : Normal");
                        binding.tvHealthStatus.setTextColor(Color.parseColor("#29B30D"));
                    }


                } catch (Exception e) {

                    sharedPreference.setData("none");
                    binding.gifImageView.setVisibility(View.GONE);
                    Toast.makeText(UserDetailsActivity.this, "Device Not Found !", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(UserDetailsActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });













        binding.tvNursePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                call(binding.tvNursePhone.getText().toString());
            }
        });

        binding.tvDoctorPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                call(binding.tvDoctorPhone.getText().toString());
            }
        });



        binding.nurseMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail(binding.nurseMail.getText().toString());
            }
        });

        binding.docMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail(binding.docMail.getText().toString());
            }
        });


    }

    void call(String number) {
        // number = binding.patinetPhone.getText().toString();

        if (ContextCompat.checkSelfPermission(UserDetailsActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) UserDetailsActivity.this, new String[]{
                    Manifest.permission.CALL_PHONE}, request_call);

        } else {

            String dail = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dail)));
        }
    }


    void mail(String myemail)
    {

        //String myemail =binding.docMail.getText().toString();
        String recipientlist = myemail.toString();
        String[] recipients = recipientlist.split(",");
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        // intent.putExtra(Intent.EXTRA_SUBJECT, get_subject);
        // intent.putExtra(Intent.EXTRA_TEXT, get_message);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "* Select Gmail *"));

    }

}