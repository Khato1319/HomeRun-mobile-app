package com.hci.homerunapp.ui.device;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;

import com.google.android.material.slider.Slider;
import com.hci.homerunapp.R;

import java.util.List;

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

    public void setSelected(String value) {
        this.selected = value;
    }

    public String getSelected() {
        return selected;
    }

    public String[] getItems() {
        return items;
    }

    public String getHint() {
        return hint;
    }

    @Override
    public void setupViewHolder(CustomAdapter.ViewHolder holder) {
        super.setupViewHolder(holder);
        DropDownData.ViewHolder dropDownViewHolder = (DropDownData.ViewHolder) holder;
        AutoCompleteTextView autoCompleteTextView = dropDownViewHolder.getAutoCompleteTextView();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(autoCompleteTextView.getContext(), R.layout.drop_down_item, getItems());
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                setSelected(arrayAdapter.getItem(position));
            }
        });
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setHint(getHint());
        autoCompleteTextView.setText(getSelected(), false);
    }

    public static class ViewHolder extends CustomAdapter.ViewHolder {
        private final AutoCompleteTextView autoCompleteTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            autoCompleteTextView = itemView.findViewById(R.id.autoCompleteTextView);
        }


        public AutoCompleteTextView getAutoCompleteTextView() {
            return autoCompleteTextView;
        }


    }
}
