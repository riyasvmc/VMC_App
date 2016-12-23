package com.zeefive.vmcapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zeefive.vmcapp.R;

public class ActivityConcreteCalculator extends ActivityBase {

    EditText mEditTextQty;
    TextView mTextViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        mEditTextQty = (EditText) findViewById(R.id.editText);
        mTextViewResult = (TextView) findViewById(R.id.textView_result);
    }

    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.button_calculate :
                calculateMaterialsQuanitity();
                break;
        }
    }

    private void calculateMaterialsQuanitity(){
        String quantity = mEditTextQty.getText().toString();
        if(!quantity.equals("")){
            double volume = Float.valueOf(quantity);
            double volumeInCubicFeet = volume * 0.3532;
            double cement = volumeInCubicFeet * 18;
            double metal = volumeInCubicFeet * 100;
            double sand = volumeInCubicFeet * 60;
            mTextViewResult.setText("Cement: " + formatValue(cement) + " Bags\n Metal: " + formatValue(metal) + " Boxes\n Sand: " + formatValue(sand) + " Boxes");
        }else{
            Toast.makeText(ActivityConcreteCalculator.this, "Enter qty in m\u00B3", Toast.LENGTH_SHORT).show();
        }
    }

    private String formatValue(Double value){
        return String.format("%.2f", value);
    }
}
