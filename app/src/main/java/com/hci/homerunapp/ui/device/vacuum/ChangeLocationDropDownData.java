package com.hci.homerunapp.ui.device.vacuum;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;

import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.device.ControlData;
import com.hci.homerunapp.ui.device.CustomAdapter;
import com.hci.homerunapp.ui.device.DropDownData;

public class ChangeLocationDropDownData extends ControlData {
//    private String[] items;
    private String hint;
    private String selected = null;

    ChangeLocationDropDownData(Context context, String deviceId) {
        super(context, R.layout.drop_down_container_item, context.getResources().getString(R.string.vacuum_location), deviceId );
//        this.items = items;
        this.hint = context.getResources().getString(R.string.vacuum_location);
    }

    public void setSelected(String value) {
        this.selected = value;
    }

    public String getSelected() {
        return selected;
    }

    public String[] getItems() {

        return new String[]{"hola", "uno"};
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

//    public static class ViewHolder extends CustomAdapter.ViewHolder {
//        private final AutoCompleteTextView autoCompleteTextView;
//
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            autoCompleteTextView = itemView.findViewById(R.id.autoCompleteTextView);
//        }
//
//
//        public AutoCompleteTextView getAutoCompleteTextView() {
//            return autoCompleteTextView;
//        }


//    }
}
