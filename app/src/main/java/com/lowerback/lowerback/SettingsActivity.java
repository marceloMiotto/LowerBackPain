package com.lowerback.lowerback;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText mAlarmIntervalEditText;
    private  Toolbar toolbar;
    String   mDefaultMusic;
    Button   mSaveButton;


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

        mAlarmIntervalEditText = (EditText) findViewById(R.id.alarm_interval_EditText);
        mSaveButton   = (Button)   findViewById(R.id.save_id);

        mSaveButton.setOnClickListener(this);
        final SharedPreferences sharedPref = this.getSharedPreferences(
                this.getString(R.string.alarm_interval_key_pref), this.MODE_PRIVATE);
        String alarmIntervalPref  = sharedPref.getString(this.getString(R.string.alarm_interval_alarm_interval_pref)
                , this.getString(R.string.alarm_interval_default));

        mAlarmIntervalEditText.setText(alarmIntervalPref);

    }


    @Override
    public void onClick(View v) {
        final SharedPreferences sharedPref = this.getSharedPreferences(
                this.getString(R.string.alarm_interval_key_pref), this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        switch (v.getId()){
            case R.id.save_id:
                editor.putString(getString(R.string.alarm_interval_alarm_interval_pref),  mAlarmIntervalEditText.getText().toString());
                editor.commit();
                Toast.makeText(SettingsActivity.this,getResources().getText(R.string.alarm_interval_saved), Toast.LENGTH_SHORT).show();
                break;

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
