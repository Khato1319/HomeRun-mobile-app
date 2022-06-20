package com.hci.homerunapp.ui.device.vacuum;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.hci.homerunapp.MyApplication;
import com.hci.homerunapp.R;
import com.hci.homerunapp.data.Resource;
import com.hci.homerunapp.data.remote.device.RemoteDeviceRoom;
import com.hci.homerunapp.ui.MainActivity;
import com.hci.homerunapp.ui.device.ControlData;
import com.hci.homerunapp.ui.device.ControlDataAdapter;
import com.hci.homerunapp.ui.device.DropDownData;
import com.hci.homerunapp.ui.home.RoomData;

import java.util.ArrayList;
import java.util.List;

public class ChangeLocationDropDownData extends ControlData {
//    private String[] items;
    private String hint;
    private RoomData selected = null;
    private List<RoomData> rooms = new ArrayList<>();

    ChangeLocationDropDownData(Context context, String deviceId) {
        super(context, R.layout.change_location_drop_down_container_item, context.getResources().getString(R.string.vacuum_location), deviceId );
//        this.items = items;
        this.hint = context.getResources().getString(R.string.vacuum_location);
    }

    public void setRooms(List<RoomData> rooms) {
        this.rooms = rooms;
    }

    public void setSelected(RoomData value) {
        this.selected = value;
    }

    public void setSelected(String roomName) {
        for(RoomData room : rooms) {
            if (room.getName().equals(roomName)){
                selected = room;
                return;
            }
        }
        throw new IllegalStateException("roomName does not match any available room");
    }

    public String getSelected() {
        return selected == null ? null : selected.getName();
    }

    public String[] getItems() {

        return (String[])rooms.stream().map(RoomData::getName).toArray();
    }

    public String getHint() {
        return hint;
    }

//    @Override
//    public void setupViewHolder(ControlDataAdapter.ViewHolder holder) {
//        super.setupViewHolder(holder);
//        DropDownData.ViewHolder dropDownViewHolder = (DropDownData.ViewHolder) holder;
//        AutoCompleteTextView autoCompleteTextView = dropDownViewHolder.getAutoCompleteTextView();
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(autoCompleteTextView.getContext(), R.layout.drop_down_item, getItems());
//        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                setSelected(arrayAdapter.getItem(position));
//            }
//        });
//        autoCompleteTextView.setAdapter(arrayAdapter);
//        autoCompleteTextView.setHint(getHint());
//        autoCompleteTextView.setText(getSelected(), false);
//    }

    public static class ViewHolder extends ControlDataViewHolder<ChangeLocationDropDownData> {
        private final AutoCompleteTextView autoCompleteTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            autoCompleteTextView = itemView.findViewById(R.id.autoCompleteTextView);
        }


        public AutoCompleteTextView getAutoCompleteTextView() {
            return autoCompleteTextView;
        }

        @Override
        public void bindTo(ChangeLocationDropDownData controlData) {
            super.bindTo(controlData);

//            DropDownData.ViewHolder dropDownViewHolder = (DropDownData.ViewHolder) holder;
            AutoCompleteTextView autoCompleteTextView = getAutoCompleteTextView();
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(autoCompleteTextView.getContext(), R.layout.drop_down_item, controlData.getItems());
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    controlData.setSelected(arrayAdapter.getItem(position));
                }
            });
            autoCompleteTextView.setAdapter(arrayAdapter);
            autoCompleteTextView.setHint(controlData.getHint());
            autoCompleteTextView.setText(controlData.getSelected(), false);
            controlData.setRooms(((MainActivity) context).getRooms());

        }
    }
}
