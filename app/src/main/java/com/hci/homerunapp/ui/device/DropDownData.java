package com.hci.homerunapp.ui.device;

import android.view.View;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;

import com.google.android.material.slider.Slider;
import com.hci.homerunapp.R;

import java.util.List;

public class DropDownData extends ControlData{
    private String[] items;
    private String hint;
    private String selected = null;

    DropDownData(String actionLabel, String[] items, String hint) {
        super(R.layout.drop_down_container_item, actionLabel);
        this.items = items;
        this.hint = hint;
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
