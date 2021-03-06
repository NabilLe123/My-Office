/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.lelab.myoffice

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.services.chime.sdk.meetings.audiovideo.AudioVideoFacade
import com.amazonaws.services.chime.sdk.meetings.session.*
import com.amazonaws.services.chime.sdk.meetings.utils.logger.ConsoleLogger
import com.amazonaws.services.chime.sdk.meetings.utils.logger.LogLevel
import com.google.gson.Gson

class InMeetingActivity : AppCompatActivity(),
        DeviceManagementFragment.DeviceManagementEventListener,
        RosterViewFragment.RosterViewEventListener {

    private val logger = ConsoleLogger(LogLevel.DEBUG)
    private val gson = Gson()
    private lateinit var meetingId: String
    private lateinit var name: String
    private lateinit var meetingString: String
    private lateinit var attendeeString: String
    private lateinit var audioVideo: AudioVideoFacade
    private lateinit var rosterViewFragment: RosterViewFragment

    private val TAG = "InMeetingActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_meeting)
        meetingId = intent.getStringExtra(MainActivity.MEETING_ID_KEY) as String
        name = intent.getStringExtra(MainActivity.NAME_KEY) as String
        meetingString = intent.getStringExtra(MainActivity.MEETING_STRING) as String
        attendeeString = intent.getStringExtra(MainActivity.ATTENDEE_STRING) as String

        val sessionConfig = createSessionConfiguration()
        val meetingSession = sessionConfig?.let {
            logger.info(TAG, "Creating meeting session for meeting Id: $meetingId")
            DefaultMeetingSession(
                    it,
                    logger,
                    applicationContext
            )
        }

        if (meetingSession == null) {
            Toast.makeText(
                    applicationContext,
                    getString(R.string.user_notification_meeting_start_error),
                    Toast.LENGTH_LONG
            ).show()
            finish()
        } else {
            audioVideo = meetingSession.audioVideo
        }

        if (savedInstanceState == null) {
            val deviceManagementFragment = DeviceManagementFragment.newInstance(meetingId, name)
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.root_layout, deviceManagementFragment, "deviceManagement")
                    .commit()
        }
    }

    override fun onJoinMeetingClicked() {
        rosterViewFragment = RosterViewFragment.newInstance(meetingId)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.root_layout, rosterViewFragment, "rosterViewFragment")
                .commit()
    }

    override fun onLeaveMeeting() {
        onBackPressed()
    }

    override fun onBackPressed() {
        audioVideo.stop()
        audioVideo.removeActiveSpeakerObserver(rosterViewFragment)
        super.onBackPressed()
    }

    fun getAudioVideo(): AudioVideoFacade = audioVideo

    private fun urlRewriter(url: String): String {
        // You can change urls by url.replace("example.com", "my.example.com")
        return url
    }

    private fun createSessionConfiguration(): MeetingSessionConfiguration? {
        return try {
            val meeting = gson.fromJson(meetingString, Meeting::class.java)
            val attendee = gson.fromJson(attendeeString, Attendee::class.java)

            MeetingSessionConfiguration(
                    CreateMeetingResponse(meeting),
                    CreateAttendeeResponse(attendee),
                    ::urlRewriter
            )
        } catch (exception: Exception) {
            logger.error(
                    TAG,
                    "Error creating session configuration: ${exception.localizedMessage}"
            )
            null
        }
    }
}
