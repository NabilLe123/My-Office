package com.lelab.myoffice.api;

import com.lelab.myoffice.model.JoinInfoResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetDataService {

    @POST("/Axionic Meeting/AxionicMeetingService/weatherforecast/joinMeeting")
    Call<JoinInfoResponse> joinMeeting(@Query("meetingID") String meetingID, @Query("userID") String userID);
}
