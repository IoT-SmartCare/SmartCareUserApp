package com.example.smartcareuser;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor ;
    private static SharedPreference mysharedPreferance=null;

    private SharedPreference(Context context)
    {
        sharedPreferences = context.getSharedPreferences("shared",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }


    public static synchronized SharedPreference getPreferences(Context context)
    {

        if(mysharedPreferance==null) {
            mysharedPreferance = new SharedPreference(context);
        }

        return mysharedPreferance;
    }


    public void setData(String user)
    {
        editor.putString("login",user);
        editor.apply();
    }

    public String getData()
    {
        return sharedPreferences.getString("login","none");
    }



    public void setDevice_id(String device_id)
    {
        editor.putString("device_id",device_id);
        editor.apply();
    }

    public String getDevice_id()
    {
        return sharedPreferences.getString("device_id","none");
    }


    public void setdocId(String doc_id)
    {
        editor.putString("doc_id",doc_id);
        editor.apply();
    }

    public String getDocId()
    {
        return sharedPreferences.getString("doc_id","none");
    }


    public void setNusId(String nus_id)
    {
        editor.putString("nus_id",nus_id);
        editor.apply();
    }

    public String getNusId()
    {
        return sharedPreferences.getString("nus_id","none");
    }

}
