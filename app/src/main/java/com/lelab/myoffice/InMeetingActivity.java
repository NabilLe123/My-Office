package com.lelab.myoffice;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lelab.myoffice.model.JoinInfoResponse;

public class InMeetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("rxjava", "MEETING_ID_KEY: " + getIntent().getStringExtra(MainActivity.MEETING_ID_KEY));
        Log.d("rxjava", "NAME_KEY: " + getIntent().getStringExtra(MainActivity.NAME_KEY));
        JoinInfoResponse joinInfoResponse = (JoinInfoResponse) getIntent().getSerializableExtra(MainActivity.MEETING_RESPONSE_KEY);
        createSessionConfiguration(joinInfoResponse);
    }

    private void createSessionConfiguration(JoinInfoResponse joinInfoResponse) {
    }
}
