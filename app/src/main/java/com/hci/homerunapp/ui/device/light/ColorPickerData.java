package com.hci.homerunapp.ui.device.light;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.android.material.slider.Slider;
import com.hci.homerunapp.MyApplication;
import com.hci.homerunapp.R;
import com.hci.homerunapp.data.remote.device.action.ColorActionBody;
import com.hci.homerunapp.data.remote.device.action.IntActionBody;
import com.hci.homerunapp.ui.MainActivity;
import com.hci.homerunapp.ui.device.ControlData;
import com.hci.homerunapp.ui.device.DeviceFragment;
import com.hci.homerunapp.ui.device.SliderData;

public class ColorPickerData extends ControlData {
    private int red, green, blue;

    public ColorPickerData(Context context, String deviceId) {
        super(context, R.layout.color_picker_item, context.getResources().getString(R.string.color_picker), deviceId);
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

    public void setRGB(String apiRgb) {
        red = Integer.valueOf( apiRgb.substring( 0, 2 ), 16 );
           green =     Integer.valueOf( apiRgb.substring( 2, 4 ), 16 );
                blue = Integer.valueOf( apiRgb.substring( 4, 6 ), 16 );
    }

//    @Override
//    public void setupViewHolder(ControlDataAdapter.ViewHolder holder) {
//        super.setupViewHolder(holder);
//        ColorPickerData.ViewHolder colorPickerViewHolder = (ColorPickerData.ViewHolder) holder;
//        CardView colorCard = colorPickerViewHolder.getColorCard();
//        Slider redSlider = colorPickerViewHolder.getRedSlider();
//        redSlider.addOnChangeListener(new Slider.OnChangeListener() {
//            @Override
//            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
//                setRed((int) value);
//                colorCard.setCardBackgroundColor(getCardColor());
//            }
//        });
//        Slider greenSlider = colorPickerViewHolder.getGreenSlider();
//        greenSlider.addOnChangeListener(new Slider.OnChangeListener() {
//            @Override
//            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
//                setGreen((int) value);
//                colorCard.setCardBackgroundColor(getCardColor());
//            }
//        });
//        Slider blueSlider = colorPickerViewHolder.getBlueSlider();
//        blueSlider.addOnChangeListener(new Slider.OnChangeListener() {
//            @Override
//            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
//                setBlue((int) value);
//                colorCard.setCardBackgroundColor(getCardColor());
//
//            }
//        });
//        redSlider.setValue(getRed());
//        greenSlider.setValue(getGreen());
//        blueSlider.setValue(getBlue());
//        colorCard.setCardBackgroundColor(getCardColor());
//    }

    public static class ViewHolder extends ControlDataViewHolder<ColorPickerData> {
        private final Slider redSlider, greenSlider, blueSlider;
        private final CardView colorCard;

        private static void configureSlider(Slider slider) {
            slider.setValueTo(255);
            slider.setValueFrom(0);
        }

        public ViewHolder(@NonNull View itemView, DeviceFragment deviceFragment) {
            super(itemView, deviceFragment);
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

        @Override
        public void bindTo(ColorPickerData controlData) {
            super.bindTo(controlData);
//            ColorPickerData.ViewHolder colorPickerViewHolder = (ColorPickerData.ViewHolder) holder;
            CardView colorCard = getColorCard();
            Slider redSlider = getRedSlider();
            redSlider.addOnChangeListener(new Slider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    controlData.setRed((int) value);
                    colorCard.setCardBackgroundColor(controlData.getCardColor());
                    ((MyApplication)((MainActivity)context).getApplication()).getDeviceRepository().putAction(controlData.getDeviceId(), "setColor", new ColorActionBody(controlData.getRed(),controlData.getGreen(), controlData.getBlue()), ViewHolder.this, false);

                }
            });
            Slider greenSlider = getGreenSlider();
            greenSlider.addOnChangeListener(new Slider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    controlData.setGreen((int) value);
                    colorCard.setCardBackgroundColor(controlData.getCardColor());
                    ((MyApplication)((MainActivity)context).getApplication()).getDeviceRepository().putAction(controlData.getDeviceId(), "setColor", new ColorActionBody(controlData.getRed(),controlData.getGreen(), controlData.getBlue()), ViewHolder.this, false);
                }
            });
            Slider blueSlider = getBlueSlider();
            blueSlider.addOnChangeListener(new Slider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    controlData.setBlue((int) value);
                    colorCard.setCardBackgroundColor(controlData.getCardColor());
                    ((MyApplication)((MainActivity)context).getApplication()).getDeviceRepository().putAction(controlData.getDeviceId(), "setColor", new ColorActionBody(controlData.getRed(),controlData.getGreen(), controlData.getBlue()), ViewHolder.this, false);

                }
            });
            redSlider.setValue(controlData.getRed());
            greenSlider.setValue(controlData.getGreen());
            blueSlider.setValue(controlData.getBlue());
            colorCard.setCardBackgroundColor(controlData.getCardColor());

        }
    }
}
