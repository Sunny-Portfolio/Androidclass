package com.example.unitconverter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText userInput;
    private TextView result;
    private Button convert;

    private Spinner spinner_from, spinner_to;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup first spinner (From temperature unit)
        spinner_from = (Spinner) findViewById(R.id.tempUnitSelect1);
        spinner_from.setOnItemSelectedListener(new SpinnerMsg());

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.temperature_unit, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_from.setAdapter(adapter1);

        // Setup second spinner (To temperature unit)
        spinner_to = (Spinner) findViewById(R.id.tempUnitSelect2);
        spinner_to.setOnItemSelectedListener(new SpinnerMsg());

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.temperature_unit, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_to.setAdapter(adapter2);

        // setOnClickListener and calculate the temperature conversion
        userInput = (EditText) findViewById(R.id.userInputSigned);
        result = (TextView) findViewById(R.id.textView_result);
        convert = (Button) findViewById(R.id.button_convert);

        convert.setOnClickListener(view -> {

            Double valueOriginal;
            Double valueConverted;
            String entered_number = userInput.getText().toString();

            // Condition if both temperature units are the same
            if (spinner_from.getSelectedItem().toString().equals(spinner_to.getSelectedItem().toString())) {
                Toast.makeText(getBaseContext(), "No Conversion Needed!", Toast.LENGTH_SHORT).show();
                if (entered_number.isEmpty()) {
                    result.setText("");
                } else {
                    valueConverted = Double.valueOf(entered_number);
                    result.setText(String.format(java.util.Locale.US, "%.2f", valueConverted));
                }
            }
            // Condition if user input is empty
            else if (userInput.getText().toString().isEmpty()) {
                Toast.makeText(getBaseContext(), "Please Enter a Value!", Toast.LENGTH_SHORT).show();
                result.setText("Null");
            }
            // Condition if converting from Celsius to Fahrenheit
            else if (spinner_from.getSelectedItem().toString().equals("Celsius")) {
                valueOriginal = Double.valueOf(entered_number);
                valueConverted = (valueOriginal * 9 / 5) + 32;
                result.setText(String.format(java.util.Locale.US, "%.2f", valueConverted));
            }
            // Condition if converting from Fahrenheit to Celsius
            else if (spinner_from.getSelectedItem().toString().equals("Fahrenheit")) {
                valueOriginal = Double.valueOf(entered_number);
                valueConverted = (valueOriginal - 32) * 5 / 9;
                result.setText(String.format(java.util.Locale.US, "%.2f", valueConverted));
            }
        });

    }

    private class SpinnerMsg implements AdapterView.OnItemSelectedListener {
        private boolean firstRun = true;

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            // Display toast message on temperature unit selection
            // and preventing it from displaying on app launch
            if (!firstRun)
                Toast.makeText(getApplicationContext(), "From " + spinner_from.getSelectedItem().toString() + " to " + spinner_to.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            firstRun = false;
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }
}
