package com.hci.homerunapp.data.remote.device;

import androidx.lifecycle.LiveData;

import com.hci.homerunapp.data.remote.ApiResponse;
import com.hci.homerunapp.data.remote.RemoteResult;
import com.hci.homerunapp.data.remote.device.action.ActionBody;
import com.hci.homerunapp.data.remote.room.RemoteRoom;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiDeviceService {

        @GET("devices")
        LiveData<ApiResponse<RemoteResult<List<RemoteDevice>>>> getDevices();

        @GET("rooms/{roomId}")
        LiveData<ApiResponse<RemoteResult<RemoteRoom>>> getRoom(@Path("roomId") String roomId);

        @PUT("device/{deviceId}/{actionName}")
        LiveData<ApiResponse<RemoteResult<Boolean>>> putAction(@Path("deviceId") String deviceId, @Path("actionName") String actionName, @Body ActionBody action);


}
