package com.example.not2;

import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import androidx.work.Data;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText alertInputValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alertInputValue = (EditText)findViewById(R.id.alert_time);

        Button alertButton = (Button)findViewById(R.id.set_alert_button);
        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String inputValue = alertInputValue.getText().toString();
//                if(inputValue.equals("")){
//                    Toast.makeText(MainActivity.this, "Input value must not be empty", Toast.LENGTH_SHORT).show();
//                }

                //Generate notification string tag
                String tag = generateKey();



                //edw tha apothikeueis tin imerominia kai tin wra pou thes na
                //ginei to nitification
                //upologizei tin diafora me tin trexousa wra kai upologizei se posa
                //millisecond tha erthei to notification
                Calendar thatDay = Calendar.getInstance();
                thatDay.set(Calendar.HOUR,1);//dokimase kai HOURS_OF_DAY pou epistrefei 24wro
                thatDay.set(Calendar.MINUTE,59);
                thatDay.set(Calendar.DAY_OF_MONTH,25);
                thatDay.set(Calendar.MONTH,4); // 0-11
                thatDay.set(Calendar.YEAR, 2019);

                Calendar today = Calendar.getInstance();

                long diff = ( thatDay.getTimeInMillis() - today.getTimeInMillis() );

                long mins = diff/ ( 60 * 1000);

                long alertTime2 = getAlertTime((int)mins) - System.currentTimeMillis();



                //Get time before alarm
//                int minutesBeforeAlert = Integer.valueOf(inputValue);
//                long alertTime = getAlertTime(minutesBeforeAlert) - System.currentTimeMillis();
//                long current =  System.currentTimeMillis();

                //Log.d(TAG, "Alert time - " + alertTime + "Current time " + current);

                int random = (int )(Math.random() * 50 + 1);
                Toast.makeText(MainActivity.this, String.valueOf(mins)+" "+String.valueOf(alertTime2), Toast.LENGTH_SHORT).show();

                //Data
               Data data = createWorkInputData(Constants.TITLE, Constants.TEXT, random);

                NotificationHandler.scheduleReminder(alertTime2, data, tag);
            }
        });
    }

    private Data createWorkInputData(String title, String text, int id){
        return new Data.Builder()
                .putString(Constants.EXTRA_TITLE, title)
                .putString(Constants.EXTRA_TEXT, text)
                .putInt(Constants.EXTRA_ID, id)
                .build();
    }

    private long getAlertTime(int userInput){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, userInput);
        return cal.getTimeInMillis();
    }

    private String generateKey(){
        return UUID.randomUUID().toString();
    }
}