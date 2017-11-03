/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */
package com.example.android.justjava2;

import java.lang.Math;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.order;
import static android.R.attr.y;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the +  button is clicked.
     */
    public void increment(View view) {

        if(quantity>=10)
        {
            Context context = getApplicationContext();
            CharSequence text = "More than 10 not allowed!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the -  button is clicked.
     */

    public void decrement(View view)
    {
        if(quantity<=1) {
            Context context = getApplicationContext();
            CharSequence text = "Less than 1 coffee doesnt make sense!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /*This method is called when the order button is clicked*/

    public void submitOrder(View view) {
        // Whether customer wants whipped cream
        CheckBox whippedCreamCheckBox = (CheckBox)findViewById(R.id.whipped_cream_check_box);
        boolean whippedCreamChecked = whippedCreamCheckBox.isChecked();
        //whether customer wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox)findViewById(R.id.chocolate_check_box);
        boolean chocolateChecked = chocolateCheckBox.isChecked();
        //to get the name input
        EditText editText = (EditText)findViewById(R.id.plain_text_input);
        String customerName = editText.getText().toString();
        //this is to get the price
        int price = calculatePrice(whippedCreamChecked,chocolateChecked);
        //this is to get the order summary
        String orderSummary = createOrderSummary(price,whippedCreamChecked,chocolateChecked,customerName);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order Summary for "+customerName);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /*This method calculates the price of the order*/

    private int calculatePrice(boolean whippedCreamChecked,boolean chocolateChecked){
        int basePrice = 5;
        if(whippedCreamChecked)
            basePrice += 1;
        if(chocolateChecked)
            basePrice += 2;
        return quantity*basePrice;
    }

    /*This method return the order summary*/

    private String createOrderSummary(int price,boolean whippedCreamChecked,boolean chocolateChecked,String customerName){
        String orderSummary = getString(R.string.customer_name,customerName)+"\n" ;
        orderSummary += getString(R.string.whipped_cream)+whippedCreamChecked+"\n";
        orderSummary += getString(R.string.chocolate)+chocolateChecked+"\n";
        orderSummary += getString(R.string.total_quantity)+quantity+"\n";
        orderSummary += "Total:$"+ price;
        orderSummary += "\n" + getString(R.string.thank_you);
        return orderSummary;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */
    /*private String displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }*/

    /**
     * This method displays the given text on the screen.
     */


}
