/*
 * Creator: churchinbandung
 * March 29 2014
 * File name: MainActivity.java
 * Project: 7MinutesWithTheLord
 * Public repository: https://github.com/yacob89/7MinutesWithTheLord
 */

package com.gerejadibandung.minuteswiththelord;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadLocale();
    }

    @OnClick(R.id.buttonIndonesia)
    void setIndonesianLocale(View v) {
        changeLang("in");
        runPrayActivity();
    }

    @OnClick(R.id.buttonEnglish)
    void setEnglishLocale(View v) {
        changeLang("en");
        runPrayActivity();
    }

    @OnClick(R.id.buttonSpanish)
    void setSpanishLocale(View v) {
        changeLang("es");
        runPrayActivity();
    }

    @OnClick(R.id.buttonKorean)
    void setKoreanLocale(View v) {
        changeLang("ko");
        runPrayActivity();
    }

    @OnClick(R.id.buttonPortuguese)
    void setPortugueseLocale(View v) {
        changeLang("pt");
        runPrayActivity();
    }

    @OnClick(R.id.buttonChinese)
    void setChineseLocale(View v) {
        changeLang("zh");
        runPrayActivity();
    }

    @OnClick(R.id.buttonChineseTaiwan)
    void setChineseTraditionalLocale(View v) {
        changeLang("zt");
        runPrayActivity();
    }

    @OnClick(R.id.buttonGerman)
    void setGermanLocale(View v) {
        changeLang("de");
        runPrayActivity();
    }

    @OnClick(R.id.buttonTagalog)
    void setTagalogLocale(View v) {
        changeLang("tl");
        runPrayActivity();
    }

    @OnClick(R.id.buttonRussian)
    void setRussianLocale(View v) {
        changeLang("ru");
        runPrayActivity();
    }

    @OnClick(R.id.buttonUkrainian)
    void setUkrainianLocale(View v) {
        changeLang("uk");
        runPrayActivity();
    }

    private void runPrayActivity() {
        Intent run = new Intent(this, PrayActivity.class);
        startActivity(run);
        finish();
    }

    private void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.apply();
    }

    private void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);
    }

    private void changeLang(String lang) {
        if (lang != null && !lang.equalsIgnoreCase("")) {
            Locale myLocale = new Locale(lang);
            saveLocale(lang);
            Locale.setDefault(myLocale);
            android.content.res.Configuration config = new android.content.res.Configuration();
            config.locale = myLocale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            onConfigurationChanged(conf);
        }
    }
}
