package com.hci.homerunapp.ui.device;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;

import com.hci.homerunapp.MyApplication;
import com.hci.homerunapp.R;
import com.hci.homerunapp.data.remote.device.action.StringActionBody;
import com.hci.homerunapp.ui.MainActivity;

public class DropDownData extends ControlData{
    private String[] items;
    private String[] apiItems;
    private String hint;
    private String selected = null;
    private String apiAction;

    public DropDownData(Context context, String actionLabel, String apiAction, String[] items, String[] apiItems, String deviceId) {
        super(context, R.layout.drop_down_container_item, actionLabel, deviceId);
        this.items = items;
        this.hint = context.getResources().getString(R.string.select_mode);
        this.apiAction = apiAction;
        this.apiItems = apiItems;
    }

    public String getApiAction() {
        return apiAction;
    }

    public String getApiSelectedValue() {
        for (int i=0 ; i< items.length ; i++) {
            if (items[i].equals(selected))
                return apiItems[i];
        }

        throw new IllegalStateException("La seleccion no existe");
    }

//    public String getSelectedValueFromApiValue(String ) {
//        for (int i=0 ; i< items.length ; i++) {
//            if (items[i].equals(selected))
//                return apiItems[i];
//        }
//
//        throw new IllegalStateException("La seleccion no existe");
//    }

    public void setSelected(String value) {
        this.selected = value;
    }

    public void setSelectedApi(String apiValue) {
        for (int i=0 ; i< apiItems.length ; i++) {
            if (apiItems[i].equals(apiValue))
                selected = items[i];
        }
    }

    public String getSelected() {
        return selected;
    }

    public String getSelectedApi() {
        for (int i=0 ; i< items.length ; i++) {
            if (items[i].equals(selected))
                return apiItems[i];
        }
        throw new IllegalStateException("No selected value that matches in items");
    }

    public String[] getItems() {
        return items;
    }

    public String getHint() {
        return hint;
    }

//    @Override
//    public void setupViewHolder(ControlDataAdapter.ViewHolder holder) {
//        super.setupViewHolder(holder);
//
//    }

    public static class ViewHolder extends ControlDataViewHolder<DropDownData> {
        private final AutoCompleteTextView autoCompleteTextView;


        public ViewHolder(@NonNull View itemView, DeviceFragment deviceFragment) {
            super(itemView, deviceFragment);
            autoCompleteTextView = itemView.findViewById(R.id.autoCompleteTextView);
        }

        @Override
        public void bindTo(DropDownData controlData) {
            super.bindTo(controlData);
//            DropDownData.ViewHolder dropDownViewHolder = (DropDownData.ViewHolder) holder;
            AutoCompleteTextView autoCompleteTextView = getAutoCompleteTextView();
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(autoCompleteTextView.getContext(), R.layout.drop_down_item, controlData.getItems());
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    controlData.setSelected(arrayAdapter.getItem(position));
                    ((MyApplication)((MainActivity)context).getApplication()).getDeviceRepository().putAction(controlData.getDeviceId(), controlData.getApiAction(), new StringActionBody(controlData.getApiSelectedValue()), ViewHolder.this, false);

                }
            });
            autoCompleteTextView.setAdapter(arrayAdapter);
            autoCompleteTextView.setHint(controlData.getHint());
            autoCompleteTextView.setText(controlData.getSelected(), false);

        }

        public AutoCompleteTextView getAutoCompleteTextView() {
            return autoCompleteTextView;
        }


    }
}
