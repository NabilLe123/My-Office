package com.lelab.myoffice;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.lelab.myoffice.api.GetDataService;
import com.lelab.myoffice.api.RetrofitClientInstance;
import com.lelab.myoffice.model.JoinInfoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText meetingEditText;
    EditText nameEditText;
    ProgressBar authenticationProgressBar;
    Button buttonContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meetingEditText = findViewById(R.id.editMeetingId);
        nameEditText = findViewById(R.id.editName);
        authenticationProgressBar = findViewById(R.id.progressAuthentication);

        buttonContinue = findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(v -> joinMeeting());
    }

    private void joinMeeting() {
        String meetingID = meetingEditText.getText().toString().trim();
        String yourName = nameEditText.getText().toString().trim();

        if (TextUtils.isEmpty(meetingID)) {
            Toast.makeText(this, getString(R.string.user_notification_meeting_id_invalid), Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(yourName)) {
            Toast.makeText(this, getString(R.string.user_notification_attendee_name_invalid), Toast.LENGTH_LONG).show();
        } else {
            authenticate(meetingID, yourName);
        }
    }

    private void authenticate(String meetingID, String yourName) {
        authenticationProgressBar.setVisibility(View.VISIBLE);
        buttonContinue.setVisibility(View.GONE);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<JoinInfoResponse> call = service.joinMeeting(meetingID, yourName);
        call.enqueue(new Callback<JoinInfoResponse>() {
            @Override
            public void onResponse(@NonNull Call<JoinInfoResponse> call, @NonNull Response<JoinInfoResponse> response) {
                Log.d("rxjava", "response raw: " + response.raw());

                if (response.code() == 200 && response.body() != null) {
                    JoinInfoResponse joinInfoResponse = response.body();
                    Log.d("rxjava", "response getJoinToken: " + joinInfoResponse.getJoinInfo().getAttendee().getJoinToken());

                    authenticationProgressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    authenticationProgressBar.setVisibility(View.GONE);
                    buttonContinue.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, R.string.user_notification_meeting_start_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JoinInfoResponse> call, @NonNull Throwable t) {
                authenticationProgressBar.setVisibility(View.GONE);
                buttonContinue.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, R.string.user_notification_meeting_start_error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
