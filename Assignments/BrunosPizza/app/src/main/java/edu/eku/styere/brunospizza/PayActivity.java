package edu.eku.styere.brunospizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PayActivity extends AppCompatActivity {
    public static final String AMOUNT_EXTRA = "amount";
    double amountDue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        // get the amount due
        Intent intent = getIntent();
        amountDue = intent.getDoubleExtra( AMOUNT_EXTRA, 0 );

        TextView tvAmountDue = findViewById( R.id.tv_amtdue );
        String dispAmount = String.format( "Due: $%.2f", amountDue );
        tvAmountDue.setText( dispAmount );

        // user is pressing "pay" button
        Button btnPay = findViewById( R.id.btn_submit_cc );
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView status = findViewById( R.id.tv_message );

                // get the name on the credit card
                EditText etName = findViewById( R.id.et_name );
                String ccName = etName.getText().toString();
                // must be present
                if ( ccName.length() == 0  ){
                    status.setText("Customer name is missing");
                    return;
                }

                // get and check credit card number
                EditText etCardNumber = findViewById( R.id.et_ccno );
                String ccNumber = etCardNumber.getText().toString();
                // check for 16 chars
                if ( ccNumber.length() != 16 ) {
                    status.setText("Credit card number is invalid");
                    return;
                }

                // get and check credit card CVV
                EditText etCVV = findViewById( R.id.et_cvv );
                String strCVV = etCVV.getText().toString();
                // check for 16 chars
                if ( strCVV.length() != 3 ) {
                    status.setText("Credit card number is invalid");
                    return;
                }

                // get and check expiration date month
                int expMonth = getEditTextAsInteger( R.id.et_exp_month );
                if ( expMonth < 1 || expMonth > 12 ) {
                    status.setText("Invalid expriation month");
                    return;
                }

                // get and check expiration date year
                // accept 2019 to 2025
                int expYear = getEditTextAsInteger( R.id.et_exp_year );
                // two-digit or four-digit year?
                if ( (expYear >= 19 && expYear <= 25) ||
                        (expYear >= 2019 && expYear <= 2025)) {
                    // accepted payment
                    setResult( RESULT_OK );
                    finish();
                } else {
                    status.setText("Invalid expiration year");
                }
            }
        });
    }

    // save over orientation change
    @Override
    public void onSaveInstanceState( Bundle savedInstanceState ) {
        super.onSaveInstanceState( savedInstanceState );

        // save amount due
        savedInstanceState.putDouble( AMOUNT_EXTRA, amountDue );
    }

    // recover amount due
    @Override
    public void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState( savedInstanceState );

        // restore amount due
        amountDue = savedInstanceState.getDouble( AMOUNT_EXTRA );

        TextView tvAmount = findViewById( R.id.tv_amtdue );
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
}
