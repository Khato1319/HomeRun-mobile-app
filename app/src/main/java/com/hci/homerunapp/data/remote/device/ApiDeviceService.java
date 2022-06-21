package com.hci.homerunapp.data.remote.device;

import android.database.Observable;

import androidx.lifecycle.LiveData;

import com.hci.homerunapp.data.remote.ApiResponse;
import com.hci.homerunapp.data.remote.RemoteResult;
import com.hci.homerunapp.data.remote.device.action.ActionBody;
import com.hci.homerunapp.data.remote.room.RemoteRoom;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiDeviceService {

        @GET("devices")
        LiveData<ApiResponse<RemoteResult<List<RemoteDevice>>>> getDevices();

        @GET("devices/{deviceId}")
        LiveData<ApiResponse<RemoteResult<RemoteDevice>>> getDevice(@Path("deviceId") String deviceId);


        @PUT("devices/{deviceId}/{actionName}")
        Call<ApiResponse<RemoteResult<Object>>> putAction(@Path("deviceId") String deviceId, @Path("actionName") String actionName, @Body ActionBody action);

        @PUT("devices/{deviceId}")
        Call<ApiResponse<RemoteResult<Object>>> updateDevice(@Path("deviceId") String deviceId, @Body ModifiedDevice device);


}
