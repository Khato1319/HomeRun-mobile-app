package com.hci.homerunapp.data.remote.device;

import androidx.lifecycle.LiveData;

import com.hci.homerunapp.data.remote.ApiResponse;
import com.hci.homerunapp.data.remote.RemoteResult;
import com.hci.homerunapp.data.remote.device.action.ActionBody;
import com.hci.homerunapp.ui.room.DeviceData;

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
        Call<RemoteResult<Boolean>> putAction(@Path("deviceId") String deviceId, @Path("actionName") String actionName, @Body ActionBody action);

        @PUT("devices/{deviceId}")
        Call<ApiResponse<RemoteResult<Object>>> updateDevice(@Path("deviceId") String deviceId, @Body ModifiedDevice device);

        @GET("devices")
        Call<RemoteResult<List<RemoteDevice>>> fetchDevices();



}
