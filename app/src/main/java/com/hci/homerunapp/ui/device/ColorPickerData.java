package com.hci.homerunapp.ui.device;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.android.material.slider.Slider;
import com.hci.homerunapp.R;

public class ColorPickerData extends ControlData{
    private int red, green, blue;

    ColorPickerData(String actionLabel, int red, int green, int blue) {
        super(R.layout.color_picker_item, actionLabel);
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return red;
    }

    public int getBlue() {
        return blue;
    }

    public int getGreen() {
        return green;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getCardColor() {
        return Color.rgb(red, green, blue);
    }

    @Override
    public void setupViewHolder(CustomAdapter.ViewHolder holder) {
        super.setupViewHolder(holder);
        ColorPickerData.ViewHolder colorPickerViewHolder = (ColorPickerData.ViewHolder) holder;
        CardView colorCard = colorPickerViewHolder.getColorCard();
        Slider redSlider = colorPickerViewHolder.getRedSlider();
        redSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                setRed((int) value);
                colorCard.setCardBackgroundColor(getCardColor());
            }
        });
        Slider greenSlider = colorPickerViewHolder.getGreenSlider();
        greenSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                setGreen((int) value);
                colorCard.setCardBackgroundColor(getCardColor());
            }
        });
        Slider blueSlider = colorPickerViewHolder.getBlueSlider();
        blueSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                setBlue((int) value);
                colorCard.setCardBackgroundColor(getCardColor());

            }
        });
        redSlider.setValue(getRed());
        greenSlider.setValue(getGreen());
        blueSlider.setValue(getBlue());
        colorCard.setCardBackgroundColor(getCardColor());
    }

    public static class ViewHolder extends CustomAdapter.ViewHolder {
        private final Slider redSlider, greenSlider, blueSlider;
        private final CardView colorCard;

        private static void configureSlider(Slider slider) {
            slider.setValueTo(100);
            slider.setValueFrom(0);
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            redSlider = itemView.findViewById(R.id.redSlider);
            configureSlider(redSlider);
            greenSlider = itemView.findViewById(R.id.greenSlider);
            configureSlider(greenSlider);
            blueSlider = itemView.findViewById(R.id.blueSlider);
            configureSlider(blueSlider);
            colorCard = itemView.findViewById(R.id.color_card);
        }


        public CardView getColorCard() {
            return colorCard;
        }

        public Slider getRedSlider() {
            return redSlider;
        }

        public Slider getGreenSlider() {
            return greenSlider;
        }

        public Slider getBlueSlider() {
            return blueSlider;
        }
    }
}
