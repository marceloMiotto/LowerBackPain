package com.lowerback.lowerback;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.lowerback.lowerback.Receivers.AlarmReceiver;

public class MainActivity extends AppCompatActivity {

    AlarmReceiver alarm = new AlarmReceiver();
    private Toolbar toolbar;
    Switch  mSwitch;
    SharedPreferences.Editor mEditor;
    ImageView mImageView;

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
        mImageView = (ImageView) findViewById(R.id.reminder_icon);
        mSwitch = (Switch) findViewById(R.id.switch1);
        final SharedPreferences sharedPref = MainActivity.this.getSharedPreferences(
                    MainActivity.this.getString(R.string.alarm_interval_key_pref), MainActivity.this.MODE_PRIVATE);

        String alarmStartStop  = sharedPref.getString(this.getString(R.string.alarm_state_start_stop_pref)
                , this.getString(R.string.button_stop_label));

        if(alarmStartStop.equals(getString(R.string.button_stop_label))){
            mSwitch.setChecked(false);
            mImageView.setImageResource(R.drawable.no_reminder);
        }else{
            mSwitch.setChecked(true);
            mImageView.setImageResource(R.drawable.reminder);
        }

        mEditor = sharedPref.edit();

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    alarm.setAlarm(MainActivity.this);
                    mImageView.setImageResource(R.drawable.reminder);
                    Toast.makeText(MainActivity.this,getResources().getText(R.string.alarm_set), Toast.LENGTH_SHORT).show();
                    mEditor.putString(getString(R.string.alarm_state_start_stop_pref), getString(R.string.button_start_label));
                    mEditor.commit();
                }else {
                    alarm.cancelAlarm(MainActivity.this);
                    mImageView.setImageResource(R.drawable.no_reminder);
                    Toast.makeText(MainActivity.this, getResources().getText(R.string.alarm_canceled), Toast.LENGTH_SHORT).show();
                    mEditor.putString(getString(R.string.alarm_state_start_stop_pref), getString(R.string.button_stop_label));
                    mEditor.commit();
                }
            }
        });
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

        return super.onOptionsItemSelected(item);
    }


}
