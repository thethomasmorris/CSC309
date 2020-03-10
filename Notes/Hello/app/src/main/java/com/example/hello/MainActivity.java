package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // have a button that copies the value of the EditText to the TextView
        Button buttonCopy = findViewById( R.id.btn_copy );
        buttonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get what the user typed
                EditText etInputValue = findViewById(R.id.et_input);
                String data = etInputValue.getText().toString();
                // display it
                TextView tvOutputValue = findViewById(R.id.tv_output);
                tvOutputValue.setText(data);
            }
        });
    }
}
