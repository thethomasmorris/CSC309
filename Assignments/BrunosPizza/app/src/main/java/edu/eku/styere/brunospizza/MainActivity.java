package edu.eku.styere.brunospizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected static int REQUEST_CODE_PAY = 101;

    // price per adult and child
    protected final double adultPrice = 29.95;
    protected final double childPrice = 15.95;
    protected double amountDue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPay = findViewById( R.id.btn_pay );
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcAmountDue();

                // pass the value to the pay activity
                Intent intent = new Intent( getApplicationContext(), PayActivity.class );
                intent.putExtra( PayActivity.AMOUNT_EXTRA, amountDue );
                startActivityForResult( intent, REQUEST_CODE_PAY );
            }
        });

        // update amount due as user types numbers
        EditText etAdults = findViewById( R.id.et_adults );
        etAdults.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                calcAmountDue();
            }
        });
        // update amount due as user types numbers
        EditText etChild = findViewById( R.id.et_child );
        etChild.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                calcAmountDue();
            }
        });
    }

    // calculate the amount due
    public void calcAmountDue() {
        try {
            int numAdults = getEditTextAsInteger( R.id.et_adults );
            int numChildren = getEditTextAsInteger( R.id.et_child );

            double rawAmountDue = (numAdults * adultPrice + numChildren * childPrice ) * 1.06;

            // round to two decimal places
            amountDue = Math.round( rawAmountDue * 100 ) / 100;

            TextView tvAmount = findViewById( R.id.tv_amount );
            String dispAmount = String.format( "Due: $%.2f", amountDue );
            tvAmount.setText( dispAmount );
        } catch ( NumberFormatException n ) {
            return;
        }
    }

    protected static String AMOUNT_DUE = "amountdue";

    // save over orientation change
    @Override
    public void onSaveInstanceState( Bundle savedInstanceState ) {
        super.onSaveInstanceState( savedInstanceState );

        // save amount due
        savedInstanceState.putDouble( AMOUNT_DUE, amountDue );
    }

    // recover amount due
    @Override
    public void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState( savedInstanceState );

        // restore amount due
        amountDue = savedInstanceState.getDouble( AMOUNT_DUE );

        TextView tvAmount = findViewById( R.id.tv_amount );
        String dispAmount = String.format( "Due: $%.2f", amountDue );
        tvAmount.setText( dispAmount );
    }

    // get a number from an edit text
    // return 0 if the field is blank
    public int getEditTextAsInteger(int id ) throws NumberFormatException {
        // get the EditText
        EditText etNum = findViewById( id );
        // get the value as a string
        String strNum = etNum.getText().toString();
        // empty value?
        if ( strNum.length() == 0 )
            return 0;
        // throws an excption if the number isn't valid (such as blank)
        return Integer.parseInt( strNum );
    }

    // pay activiy has exited, clear edit text boxes if payment was successful
    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );

        // OK from payment activity?
        if ( requestCode == REQUEST_CODE_PAY && resultCode == RESULT_OK ) {
            EditText editText = findViewById( R.id.et_adults );
            editText.setText("");

            editText = findViewById( R.id.et_child );
            editText.setText("");

            TextView textView = findViewById( R.id.tv_amount );
            textView.setText( "Due: $0.00");

            Toast.makeText( this, "Thank You!", Toast.LENGTH_LONG ).show();
        }
    }
}
