package com.example.smartcareuser;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;




public class myBackgroundProcess extends Service {

    int bpm;
    SharedPreference sharedPreference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,suggestion_ref;

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sharedPreference = SharedPreference.getPreferences(myBackgroundProcess.this);



        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference("patients/"+sharedPreference.getDevice_id()+"/bpm");

       reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    String get_bpm=dataSnapshot.getValue(String.class);

                    bpm= Integer.parseInt(""+get_bpm);

                    if (bpm==0)
                    {

                    }
                   else if (bpm<60 ||bpm>100)
                    {

                        scheduleNotification(getNotification("Your current BP : "+bpm+". ASAP call your nurse"),1000);

                    }
                    else
                    {


                    }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        suggestion_ref=firebaseDatabase.getReference("suggestion/"+sharedPreference.getDevice_id()+"/suggestion");

        suggestion_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String get_suggestion=""+dataSnapshot.getValue(String.class);
                if (!get_suggestion.equals("null"))
                {
                    scheduleNotification(getNotification_sms(""+get_suggestion),1000);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }



    public static void wait(int s)
    {
        try
        {
            Thread.sleep(s);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }


    private void scheduleNotification (Notification notification , int delay) {
        Intent notificationIntent = new Intent( this, MyService. class ) ;
        notificationIntent.putExtra(MyService. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyService. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        long futureInMillis = SystemClock. elapsedRealtime () + delay ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent) ;
    }

    private Notification getNotification (String content) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
        builder.setContentTitle( "Abnormal Health Condition ! " ) ;
        builder.setContentText(content) ;
        builder.setSmallIcon(R.mipmap.ic_final_launcher ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;
    }

    private Notification getNotification_sms (String content) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
        builder.setContentTitle( "You have a new suggestion" ) ;
        builder.setContentText(content) ;
        builder.setSmallIcon(R.mipmap.ic_final_launcher ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;
    }
}
