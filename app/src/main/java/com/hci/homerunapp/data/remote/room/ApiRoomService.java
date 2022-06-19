package com.hci.homerunapp.data.remote.room;

import androidx.lifecycle.LiveData;

import com.hci.homerunapp.data.remote.ApiResponse;
import com.hci.homerunapp.data.remote.RemoteResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiRoomService {
    @GET("rooms")
    LiveData<ApiResponse<RemoteResult<List<RemoteRoom>>>> getRooms();

    @GET("rooms/{roomId}")
    LiveData<ApiResponse<RemoteResult<RemoteRoom>>> getRoom(@Path("roomId") String roomId);
}
