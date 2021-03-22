package edu.nitt.cse.preferencesetting;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.Toast;
public class Preference extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.prefs);

        Load_setting();


    }

    private void Load_setting()
    {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        boolean chk_night = sp.getBoolean("NIGHT", false);

        if (chk_night)
        {
            getListView().setBackgroundColor(Color.parseColor("#222222"));
        }
        else
        {
            getListView().setBackgroundColor(Color.parseColor("#ffffff"));
        }







        CheckBoxPreference chk_night_instant = (CheckBoxPreference) findPreference("NIGHT");

        chk_night_instant.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference prefs, Object obj) {

                boolean yes = (boolean) obj;

                if (yes) {
                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (!mBluetoothAdapter.isEnabled()) {
                        mBluetoothAdapter.enable();
                    }
                } else {
                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter.isEnabled()) {
                        mBluetoothAdapter.disable();
                    }
                }

                return true;
            }
        });




        ListPreference LP = (ListPreference) findPreference("ORIENTATION");

        String orien = sp.getString("ORIENTATION", "false");

        if ("1".equals(orien))
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);

            LP.setSummary(LP.getEntry());
        }

        else if ("2".equals(orien))
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            LP.setSummary(LP.getEntry());
        }

        else if ("3".equals(orien))
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            LP.setSummary(LP.getEntry());
        }






        LP.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener()
        {
            @Override
            public boolean onPreferenceChange(android.preference.Preference prefs, Object obj)
            {

                String items = (String) obj;
                if (prefs.getKey().equals("ORIENTATION"))
                {
                    switch (items)
                    {
                        case "1":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
                            break;
                        case "2":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            break;
                        case "3":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            break;
                    }

                    ListPreference LPP = (ListPreference) prefs;
                    LPP.setSummary(LPP.getEntries()[LPP.findIndexOfValue(items)]);

                }
                return true;
            }
        });



        android.preference.Preference b = findPreference("BRIGHTNESS");
        b.setOnPreferenceChangeListener((prefs, obj) -> {
            float sb = (int) obj;
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = sb / 100.0f;
            getWindow().setAttributes(lp);
            return true;
        });


        android.preference.Preference vol = findPreference("VOLUME");
        vol.setOnPreferenceChangeListener((prefs, obj) -> {
            int sb = (int) obj;
            AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, sb, 1);
            return true;
        });





    }

    @Override
    protected void onResume() {
        Load_setting();
        super.onResume();
    }



}