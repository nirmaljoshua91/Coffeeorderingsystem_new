package com.example.nirmal.coffeeorderingsystem;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.server.converter.StringToIntConverter;

import java.util.Locale;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * This method is called when the order button is clicked.
     */
    int i = 0;
    String displaySummaryGlobal;

    public void increButton(View view){
        if(i<100)
        i++;
        else
            Toast.makeText(this,"Sorry! the quantitiy should not be above 100",Toast.LENGTH_SHORT).show();
        display(i);
    }
    public void decreButton(View view){
       if(i>0) {
           i--;
       }
        else{
           Toast.makeText(this,"Sorry! the quantitiy should be alteast 1 number",Toast.LENGTH_SHORT).show();
       }
        display(i);
    }

    public void submitOrder(View view) {
        int quantity=i;
        String message="Thank you!";
        int pricePerItem=10;
        display(i);
        String name =getName();
        if(hasChecked()){
            pricePerItem=pricePerItem+5;
        }
        else if(hasChecked2()){
            pricePerItem=pricePerItem+10;
        }

        int price =calculatePrice(i,pricePerItem);
        boolean checkboxstatus1 = hasChecked();
        boolean checkboxstatus2 = hasChecked2();
        displaySummary(price,quantity,checkboxstatus1,checkboxstatus2,name);
        Button mailButton = (Button) findViewById(R.id.mailButtonID);
        mailButton.setVisibility(View.VISIBLE);



    }
    public void sendEmailButton(View view){
        sendEmail(displaySummaryGlobal);
    }



    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    public int calculatePrice(int quantity,int pricePercup) {
        int price = quantity * pricePercup;
        return price;
    }

    private String getName(){
        EditText name = (EditText) findViewById(R.id.NameEdit);
        return String.valueOf(name.getText());
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public String displaySummary(int price,int quantity,boolean checkBoxStatus,boolean checkBoxStatus2,String name){
        TextView message=(TextView)findViewById(R.id.order_text_view);
        String content ="Name:"+name+"\n";
        content +="Add Whipped cream?"+checkBoxStatus+"\n";
        content +="Add Choclate?"+checkBoxStatus2+"\n";
        content +="Quantity:"+quantity+"\n";
        content += "Total Amount to be paid :"+NumberFormat.getCurrencyInstance(new Locale("en","IN")).format(price)+"\n"+ "Thank You!";
        //message.setText("Name: Lyla the Labyrinth"+"\n"+"Add Whipped cream?"+checkBoxStatus+"\n"+"Add Choclate?"+checkBoxStatus2+"\n"+"Quantity:"+quantity+"\n"+"Total Amount to be paid :"+NumberFormat.getCurrencyInstance(new Locale("en","IN")).format(price)+"\n"+ "Thank You!");
        message.setText(content);
        displaySummaryGlobal=content;
        return content;
    }

    private void sendEmail(String bodyText){
        Intent intent=new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT,bodyText);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else
            Toast.makeText(this,"Email not sent",Toast.LENGTH_SHORT).show();
    }
    private boolean hasChecked(){
        CheckBox status = (CheckBox) findViewById(R.id.checkbox1);
        boolean checkstatus =status.isChecked();
        return checkstatus;
    }
    private boolean hasChecked2(){
        CheckBox status = (CheckBox) findViewById(R.id.checkbox2);
        boolean checkstatus =status.isChecked();
        return checkstatus;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.nirmal.coffeeorderingsystem/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.nirmal.coffeeorderingsystem/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
