package com.lelab.myoffice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;

import com.amazonaws.services.chime.sdk.meetings.session.Attendee;
import com.amazonaws.services.chime.sdk.meetings.session.MediaPlacement;
import com.amazonaws.services.chime.sdk.meetings.session.Meeting;
import com.google.gson.Gson;
import com.lelab.myoffice.api.GetDataService;
import com.lelab.myoffice.api.RetrofitClientInstance;
import com.lelab.myoffice.model.JoinInfoResponse;
import com.lelab.myoffice.model.MediaPlacementVal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final int RESULT_PERMISSION = 1;

    EditText meetingEditText;
    EditText nameEditText;
    ProgressBar authenticationProgressBar;
    Button buttonContinue;

    public static String MEETING_STRING = "MEETING_RESPONSE";
    public static String ATTENDEE_STRING = "ATTENDEE_STRING";
    public static String MEETING_ID_KEY = "MEETING_ID";
    public static String NAME_KEY = "NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meetingEditText = findViewById(R.id.editMeetingId);
        nameEditText = findViewById(R.id.editName);
        authenticationProgressBar = findViewById(R.id.progressAuthentication);

        buttonContinue = findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(v -> joinMeeting());

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.MODIFY_AUDIO_SETTINGS) == PackageManager.PERMISSION_DENIED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MODIFY_AUDIO_SETTINGS,
                    Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA}, RESULT_PERMISSION);
        }
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
                    authenticationProgressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

                    JoinInfoResponse joinInfoResponse = response.body();
                    Log.d("rxjava", "response getJoinToken: " + joinInfoResponse.getJoinInfo().getAttendee().getJoinToken());

                    MediaPlacementVal mediaPlacementVal = joinInfoResponse.getJoinInfo().getMeeting().getMediaPlacement();
                    MediaPlacement mediaPlacement = new MediaPlacement(mediaPlacementVal.getAudioFallbackUrl(), mediaPlacementVal.getAudioHostUrl(),
                            mediaPlacementVal.getSignalingUrl(), mediaPlacementVal.getTurnControlUrl());
                    Meeting meeting = new Meeting(joinInfoResponse.getJoinInfo().getMeeting().getExternalMeetingId(), mediaPlacement,
                            joinInfoResponse.getJoinInfo().getMeeting().getMediaRegion(), joinInfoResponse.getJoinInfo().getMeeting().getMeetingId());
                    Attendee attendee = new Attendee(joinInfoResponse.getJoinInfo().getAttendee().getAttendeeId(),
                            joinInfoResponse.getJoinInfo().getAttendee().getExternalUserId(), joinInfoResponse.getJoinInfo().getAttendee().getJoinToken());

                    Gson gson = new Gson();
                    String meetingString = gson.toJson(meeting);
                    String attendeeString = gson.toJson(attendee);

                    Intent intent = new Intent(MainActivity.this, InMeetingActivity.class);
                    intent.putExtra(MEETING_STRING, meetingString);
                    intent.putExtra(ATTENDEE_STRING, attendeeString);
                    intent.putExtra(MEETING_ID_KEY, meetingID);
                    intent.putExtra(NAME_KEY, yourName);
                    startActivity(intent);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (requestCode == RESULT_PERMISSION) {
            boolean allAccepted = true;
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        allAccepted = false;
                        break;
                    }
                }

                if (!allAccepted) {
                    Toast.makeText(this, getString(R.string.permission_required), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }

            } else {
                Toast.makeText(this, getString(R.string.permission_required), Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        }
    }
}
