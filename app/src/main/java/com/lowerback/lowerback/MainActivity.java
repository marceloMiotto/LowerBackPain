package com.lowerback.lowerback;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.lowerback.lowerback.Receivers.AlarmReceiver;

public class MainActivity extends AppCompatActivity {

    AlarmReceiver alarm = new AlarmReceiver();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.ToolBarId);
        setSupportActionBar(toolbar);

        /*
        ** Added from http://stackoverflow.com/questions/4776933/android-application-icon-not-showing-up
         */
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        /*
        ** Added End
         */


        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            final SharedPreferences sharedPref = MainActivity.this.getSharedPreferences(
                    MainActivity.this.getString(R.string.alarm_interval_key_pref), MainActivity.this.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    alarm.setAlarm(MainActivity.this);
                    Toast.makeText(MainActivity.this, getResources().getText(R.string.alarm_set), Toast.LENGTH_SHORT).show();
                    editor.putString(getString(R.string.alarm_state_start_stop_pref), getString(R.string.button_start_label));
                    editor.commit();
                    Log.i("Debug01", "set to true again");


                } else {
                    alarm.cancelAlarm(MainActivity.this);
                    Toast.makeText(MainActivity.this,getResources().getText(R.string.alarm_canceled), Toast.LENGTH_SHORT).show();
                    editor.putString(getString(R.string.alarm_state_start_stop_pref), getString(R.string.button_stop_label));
                    editor.commit();
                    Log.i("Debug01", "set to false again");
                }
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);

        final SharedPreferences sharedPref = this.getSharedPreferences(
                this.getString(R.string.alarm_interval_key_pref), this.MODE_PRIVATE);
        String alarmButtonState  = sharedPref.getString(this.getString(R.string.alarm_state_start_stop_pref)
                , this.getString(R.string.button_start_label));

        Log.i("Debug02", "state "+alarmButtonState);

        if(alarmButtonState.equals(this.getString(R.string.button_start_label))){
            toggle.setChecked(false);
            Log.i("Debug01","set to true");

        }else{
           toggle.setChecked(false);
            Log.i("Debug01", "set to false");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_cancel){
            alarm.cancelAlarm(this);
            Toast.makeText(this,getResources().getText(R.string.alarm_canceled), Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


}
