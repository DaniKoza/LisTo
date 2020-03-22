package com.example.listo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.listo.R;
import com.google.firebase.database.DatabaseReference;

public class GuestActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
    }
}
