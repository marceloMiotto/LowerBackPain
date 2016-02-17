package com.lowerback.lowerback;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    EditText alarmIntervalEditText;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = (Toolbar) findViewById(R.id.ToolBarId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.settings_activity_title);
        /*
        ** Added from http://stackoverflow.com/questions/4776933/android-application-icon-not-showing-up
         */
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        /*
        ** Added End
         */

        alarmIntervalEditText = (EditText) findViewById(R.id.alarm_interval_EditText);
        Button   saveButton                  = (Button)   findViewById(R.id.save_id);

        final SharedPreferences sharedPref = this.getSharedPreferences(
                this.getString(R.string.alarm_interval_key_pref), this.MODE_PRIVATE);
        String alarmIntervalPref  = sharedPref.getString(this.getString(R.string.alarm_interval_alarm_interval_pref)
                , this.getString(R.string.alarm_interval_default));

        alarmIntervalEditText.setText(alarmIntervalPref);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.alarm_interval_alarm_interval_pref),  alarmIntervalEditText.getText().toString());
                editor.commit();
                Toast.makeText(SettingsActivity.this,getResources().getText(R.string.alarm_interval_saved), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
