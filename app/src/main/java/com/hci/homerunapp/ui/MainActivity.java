package com.hci.homerunapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.hci.homerunapp.MyApplication;
import com.hci.homerunapp.R;
import com.hci.homerunapp.data.Resource;
import com.hci.homerunapp.data.Status;
import com.hci.homerunapp.databinding.ActivityMainBinding;
import com.hci.homerunapp.ui.home.RoomData;
import com.hci.homerunapp.ui.room.DeviceData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private ImageButton upButton, notificationsButton;
    private TextView title;
    private List<DeviceData> recentDevices = new ArrayList<>();
    private List<RoomData> rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setU
        View v = getLayoutInflater().inflate(R.layout.action_bar, null);
        title = v.findViewById(R.id.action_bar_title);
        title.setText(getTitle());
        notificationsButton = v.findViewById(R.id.notification_button);
        upButton = v.findViewById(R.id.up_button);

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp();
            }
        });

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        getSupportActionBar().setCustomView(v, layoutParams);
        getSupportActionBar().setElevation(10);
        Toolbar parent = (Toolbar) v.getParent();
        parent.setContentInsetsAbsolute(0, 0);

            NavHostFragment navHostFragment =
                    (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
            navController = navHostFragment.getNavController();


        BottomNavigationView navView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_recents, R.id.navigation_routines, R.id.navigation_room, R.id.navigation_device)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void addToRecents(DeviceData device) {
        if (recentDevices.contains(device))
            return;

        recentDevices.add(device);

        if (recentDevices.size() > 10)
            recentDevices.remove(0);
    }

    public void setRooms(List<RoomData> rooms) {
        this.rooms = rooms;
    }

    public List<DeviceData> getRecentDevices() {
        return recentDevices;
    }

    public ImageButton getUpButton() {
        return upButton;
    }

    public ImageButton getNotificationsButton() {
        return notificationsButton;
    }

    public TextView getTitleText() {
        return title;
    }

    @Override
    protected void onResumeFragments() {
        Log.d("CURRENT FRAGM", String.valueOf(getForegroundFragment()));

        super.onResumeFragments();
    }

    public void showProgressBar() {
        binding.loading.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        binding.loading.setVisibility(View.GONE);
    }

    public Fragment getForegroundFragment(){
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        return navHostFragment == null ? null : navHostFragment.getChildFragmentManager().getFragments().get(0);
    }

    public MutableLiveData<Resource<List<RoomData>>> getRooms() {
        MediatorLiveData<Resource<List<RoomData>>> rooms = new MediatorLiveData<>();
        rooms.addSource(((MyApplication)getApplication()).getRoomRepository().getRooms(), resource -> {
            if (resource.status == Status.SUCCESS) {
                rooms.setValue(Resource.success(resource.data));
            } else {
                rooms.setValue(resource);
            }
        });

        return rooms;
    }




    //    @Override
//    public void onBackPressed() {
//        showTabIfReturningHome();
//        super.onBackPressed();
//
//    }

//    private void showTabIfReturningHome() {
//        List<Integer> homeIds = Arrays.asList(R.id.navigation_room, R.id.routineFragment, R.id.navigation_home);
//        List<Fragment> fragments = getSupportFragmentManager().getFragments();
//        Fragment lastFragment = fragments.get(fragments.size()-1);
//        Log.d("id", String.valueOf(lastFragment.getId()));
//        Log.d("id", String.valueOf(R.id.room_fragment));
//
//
////        if(homeIds.contains())
////            showBottomNav();
//    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    public void hideBottomNav() {
        if (binding != null)
            binding.navView.setVisibility(View.GONE);
    }

    public void showBottomNav() {
        if (binding != null)
            binding.navView.setVisibility(View.VISIBLE);
    }

}