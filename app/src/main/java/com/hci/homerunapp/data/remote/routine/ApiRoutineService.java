package com.hci.homerunapp.data.remote.routine;

import androidx.lifecycle.LiveData;

import com.hci.homerunapp.data.remote.ApiResponse;
import com.hci.homerunapp.data.remote.RemoteResult;
import com.hci.homerunapp.data.remote.room.RemoteRoom;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiRoutineService {
    @GET("routines")
    LiveData<ApiResponse<RemoteResult<List<RemoteRoutine>>>> getRoutines();

    @PUT("routines/{routineId}/execute")
    LiveData<ApiResponse<RemoteResult<Object>>> executeRoutine(@Path("routineId") String routineId);
}
