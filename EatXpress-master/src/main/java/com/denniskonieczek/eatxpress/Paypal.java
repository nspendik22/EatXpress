package com.denniskonieczek.eatxpress;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

/**
 * DenNikRak - EatXpress v1.0
 * Dennis Konieczek
 * Nikolas Spendik
 * Rakul Mahenthiran
 *
 * Submission Date: Dec 8 2015
 * */
public class Paypal extends AppCompatActivity {


    //using paypal in sanbox testing environment
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    //api key
    private static final String CONFIG_CLIENT_ID = "AUAngGiSc1w99pZZ0SJ7w4fuTV1jJoNGgb-i2_LiOxw09X4tyUBfRyWH-jW3FhxDTmxojXa7hD5-nbeZ";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

    //configure paypal
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);

    com.paypal.android.sdk.payments.PayPalPayment mealOrder;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Intent intent2 = new Intent(this, PayPalService.class);
        intent2.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent2);

        Bundle extras = getIntent().getExtras();

        //restaurant username
        username = extras.getString("username");
        //meal total
        mealOrder = new com.paypal.android.sdk.payments.PayPalPayment(new BigDecimal(extras.getString("total")), "CAD",
                extras.get("name")+ getString(R.string.mealTotal), com.paypal.android.sdk.payments.PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent3 = new Intent(Paypal.this, PaymentActivity.class);

        intent3.putExtra(PaymentActivity.EXTRA_PAYMENT,mealOrder);

        startActivityForResult(intent3, REQUEST_CODE_PAYMENT);
    }


    //payment status
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data
                        .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        System.out.println(confirm.toJSONObject().toString(4));
                        System.out.println(confirm.getPayment().toJSONObject()
                                .toString(4));

                        Toast.makeText(getApplicationContext(), getString(R.string.orderPlaced),
                                Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(Paypal.this, RateUs.class);
                        intent.putExtra("username",username);
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println(getString(R.string.paymentCancelled));
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                System.out
                        .println(getString(R.string.error2));
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data
                        .getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i(getString(R.string.future), auth.toJSONObject()
                                .toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i(getString(R.string.future), authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(getApplicationContext(),
                                getString(R.string.future2),
                                Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Log.e(getString(R.string.future),
                                getString(R.string.oops), e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i(getString(R.string.future), getString(R.string.paymentCancelled));
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(getString(R.string.future),
                        getString(R.string.error3));
            }
        }
    }



    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

    }

    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }




}
