package com.hci.homerunapp.ui.routines;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.ButtonListenerMaker;
import com.hci.homerunapp.ui.device.ac.DeviceAC;
import com.hci.homerunapp.ui.device.oven.DeviceOven;
import com.hci.homerunapp.ui.device.vacuum.DeviceVacuum;
import com.hci.homerunapp.ui.room.DeviceData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoutineActionsAdapter extends RecyclerView.Adapter<RoutineActionsAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private final List<RoutineAction> actions;

    RoutineActionsAdapter(List<RoutineAction> actions) {
        this.actions = actions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.routine_action, parent, false);

        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        Context context = holder.getActionText().getContext();

        RoutineAction action = actions.get(position);

        String deviceString = String.format(context.getString(R.string.device_from), action.getDevice(), action.getRoom());

        String setMode = context.getString(R.string.set_mode) + ": ";
        String setSpeed = context.getString(R.string.set_speed) + ": ";

        DeviceData deviceData = new DeviceData(null, "1", null, null);

        String actionString = switch(action.getActionName()) {
            case "turnOn", "start" ->  context.getString(R.string.action_on);
            case "turnOff", "pause" -> context.getString(R.string.action_off);
            case "dock" -> context.getString(R.string.action_dock);
            case "setTemperature" -> String.format(context.getString(R.string.action_temperature), Math.round((double)action.getParam()));
            case "setMode" -> {
                String apiMode = (String)action.getParam();
                if (action.getDeviceType().equals("ac"))
                    yield setMode + new DeviceAC(deviceData, context).getCoolingModeDropDown().getDisplayValue(apiMode);
                else
                    yield setMode + new DeviceVacuum(deviceData, context).getSetModeDropDownData().getDisplayValue(apiMode);
            }
            case "setVerticalSwing" -> setMode + new DeviceAC(deviceData, context).getVerticalSwingDropDown().getDisplayValue((String)action.getParam());
            case "setHorizontalSwing" -> setMode + new DeviceAC(deviceData, context).getHorizontalSwingDropDown().getDisplayValue((String)action.getParam());
            case "setFanSpeed" -> setSpeed + new DeviceAC(deviceData, context).getSpeedDropDown().getDisplayValue((String)action.getParam());
            case "setLocation" -> context.getString(R.string.vacuum_location) + ": " + action.getRoom();
            case "setBrightness" -> context.getString(R.string.brightness) + ": " + (Math.round((double)action.getParam()));
            case "setColor" -> {
                CardView colorCard = holder.getColorCard();
                colorCard.setVisibility(View.VISIBLE);
                colorCard.setCardBackgroundColor(Color.parseColor("#" + action.getParam()));
                yield context.getString(R.string.color_picker) + ": ";
            }
            case "open" -> context.getString(R.string.action_open);
            case "close" -> context.getString(R.string.action_close);
            case "setLevel" -> context.getString(R.string.action_set_level) + ": " + (Math.round((double)action.getParam()));
            case "setHeat" -> context.getString(R.string.heat_source_title) + ": " + new DeviceOven(deviceData, context).getChangeHeatSourceDropDown().getDisplayValue((String)action.getParam());
            case "setGrill" -> context.getString(R.string.grill_mode_title) + ": " + new DeviceOven(deviceData, context).getSetGrillModeDropDown().getDisplayValue((String)action.getParam());
            case "setConvection" -> context.getString(R.string.convection_mode_title) + ": " + new DeviceOven(deviceData, context).getSetConvectionModeDropDown().getDisplayValue((String)action.getParam());
            default -> throw new IllegalStateException();
        };

        if(!action.getActionName().equals("setColor"))
            holder.getColorCard().setVisibility(View.INVISIBLE);


        holder.getDeviceText().setText(deviceString);
        holder.getActionText().setText(actionString);

    }

    @Override
    public int getItemCount() {
        return actions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView deviceText;
        private final TextView actionText;
        private final CardView colorCard;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceText = itemView.findViewById(R.id.text_device);
            actionText = itemView.findViewById(R.id.text_action);
            colorCard = itemView.findViewById(R.id.color_card);
        }

        public TextView getDeviceText() {
            return deviceText;
        }

        public TextView getActionText() {
            return actionText;
        }

        public CardView getColorCard() {
            return colorCard;
        }
    }
}
