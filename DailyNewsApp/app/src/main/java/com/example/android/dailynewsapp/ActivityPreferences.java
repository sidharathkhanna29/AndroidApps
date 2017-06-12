package com.example.android.dailynewsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by hp on 29-May-17.
 */

public class ActivityPreferences extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_pref_activity);
    }

    public static class NewsPrefFrag extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.news_preferences);
            Preference type = findPreference(getString(R.string.settings_category_key));
            bindPreferenceSummaryToValue(type);
        }

        public boolean onPreferenceChange(Preference preference, Object o) {
            String value = o.toString();
            if (preference instanceof ListPreference) {
                ListPreference lPref = (ListPreference) preference;
                int indexPreference = lPref.findIndexOfValue(value);
                if (indexPreference >= 0) {
                    CharSequence[] enrty = lPref.getEntries();
                    preference.setSummary(enrty[indexPreference]);
                }
            } else {
                preference.setSummary(value);
            }
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference type) {
            type.setOnPreferenceChangeListener(this);
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(type.getContext());
            String prefString = sharedPref.getString(type.getKey(), "");
            onPreferenceChange(type, prefString);
        }

    }
}
