/*
 * Creator: churchinbandung
 * March 29 2014
 * File name: MainActivity.java
 * Project: 7MinutesWithTheLord
 * Public repository: https://github.com/yacob89/7MinutesWithTheLord
 */

package com.gerejadibandung.minuteswiththelord;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	
	public TextList textList;
	 private boolean timerHasStarted = false;
	 private Button startB;
	 private Button exitButton;
	 public TextView text;
	 public TextView title;
	 public TextView description;
	 private final long interval = 1 * 1000;
	 
	 private Uri notification;
	 private Ringtone r;
	 
	 //Menyeru nama Tuhan
	 private CountDownTimer menyeru_countDownTimer;
	 private final long menyeru_startTime = 30 * 1000;
	 //private final long menyeru_startTime = 5 * 1000;
	 // Berdoa
	 private CountDownTimer berdoa_countDownTimer;
	 private final long berdoa_startTime = 60 * 1000;
	 //private final long berdoa_startTime = 5 * 1000;
	 // Doa-baca
	 private CountDownTimer doabaca_countDownTimer;
	 private final long doabaca_startTime = 150 * 1000;
	 //private final long doabaca_startTime = 5 * 1000;
	 // Mengaku Dosa
	 private CountDownTimer mengakuDosa_countDownTimer;
	 private final long mengakuDosa_startTime = 60 * 1000;
	 //private final long mengakuDosa_startTime = 5 * 1000;
	 // Konsikrasi
	 private CountDownTimer konsikrasi_countDownTimer;
	 private final long konsikrasi_startTime = 30 * 1000;
	 //private final long konsikrasi_startTime = 5 * 1000;
	 // Mengucap Syukur
	 private CountDownTimer ucapSyukur_countDownTimer;
	 private final long ucapSyukur_startTime = 30 * 1000;
	 //private final long ucapSyukur_startTime = 5 * 1000;
	 // Doa Permohonan
	 private CountDownTimer doaPermohonan_countDownTimer;
	 private final long doaPermohonan_startTime = 60 * 1000;
	 //private final long doaPermohonan_startTime = 5 * 1000;
	 // 
	 private int counter;
	 private Locale myLocale;
	 private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textListInit(); //Strings initialization
        // UI Initialization
        startB = (Button) this.findViewById(R.id.button);
        exitButton = (Button) this.findViewById(R.id.exitButton);
        text = (TextView) this.findViewById(R.id.timer);
        title = (TextView) this.findViewById(R.id.textView1);
        description = (TextView) this.findViewById(R.id.textView2);
        
        // Timer Initialization
        menyeru_countDownTimer = new MyCountDownTimer(menyeru_startTime, interval);
        berdoa_countDownTimer = new MyCountDownTimer(berdoa_startTime, interval);
        doabaca_countDownTimer = new MyCountDownTimer(doabaca_startTime, interval);
        mengakuDosa_countDownTimer = new MyCountDownTimer(mengakuDosa_startTime, interval);
        konsikrasi_countDownTimer = new MyCountDownTimer(konsikrasi_startTime, interval);
        ucapSyukur_countDownTimer = new MyCountDownTimer(ucapSyukur_startTime, interval);
        doaPermohonan_countDownTimer = new MyCountDownTimer(doaPermohonan_startTime, interval);
        text.setText(text.getText() + String.valueOf(menyeru_startTime / 1000));
        description.setText("");
        
        // Notification sound initialization
        // Use default notification
        notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        Uri path = Uri.parse("android.resource://com.gerejadibandung.minuteswiththelord/"+R.raw.sirius);
        r = RingtoneManager.getRingtone(getApplicationContext(), path);
        
        // Set custom roboto fonts
        Typeface mFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        startB.setTypeface(mFont);
        exitButton.setTypeface(mFont);
        text.setTypeface(mFont);
        title.setTypeface(mFont);
        description.setTypeface(mFont);
        
        // Keep The screen on during activity
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        timerHasStarted = false;
        
        // Hid Keluar Button
        exitButton.setVisibility(View.GONE);
        startB.setVisibility(View.VISIBLE);
        
        //Check Fist Install
        boolean firstrun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstrun", true);
        if (firstrun){
        	alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Select your language");
            alertDialog.setMessage("Pilih Bahasa Anda");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "English (US)", new DialogInterface.OnClickListener() {

              public void onClick(DialogInterface dialog, int id) {
            	  changeLang("en");
            	  setEnglishLocale();
            	  Intent intent = getIntent();
            	  finish();
            	  startActivity(intent);
            } }); 

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Bahasa Indonesia", new DialogInterface.OnClickListener() {

              public void onClick(DialogInterface dialog, int id) {
            	  changeLang("in");
            	  setIndonesianLocale();
            	  Intent intent = getIntent();
            	  finish();
            	  startActivity(intent);
            }}); 

            /*alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Bahasa Mandarin", new DialogInterface.OnClickListener() {

              public void onClick(DialogInterface dialog, int id) {
            	  // Set Bahasa Mandarin
            }});*/
            
            alertDialog.show();
        	
        // Save the state
        getSharedPreferences("PREFERENCE", MODE_PRIVATE)
            .edit()
            .putBoolean("firstrun", false)
            .commit();
        }
    }


    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
     public class MyCountDownTimer extends CountDownTimer {
      public MyCountDownTimer(long startTime, long interval) {
       super(startTime, interval);
      }
    
      @Override
      public void onFinish() {
    	  if (counter == 7){
    		  r.play();
    		  title.setText(textList.praying);
    		  description.setText(textList.praying_desc);
    		  berdoa_countDownTimer.start();
    		  counter--;
    	  }
    	  else if (counter == 6)
    	  {
    		  r.play();
    		  title.setText(textList.pray_reading);
    		  description.setText(textList.pray_reading_desc);
    		  doabaca_countDownTimer.start();
    		  counter--;
    	  }
    	  else if (counter == 5)
    	  {
    		  r.play();
    		  title.setText(textList.confession);
    		  description.setText(textList.confession_desc);
    		  mengakuDosa_countDownTimer.start();
    		  counter--;
    	  }
    	  else if (counter == 4)
    	  {
    		  r.play();
    		  title.setText(textList.consecration);
    		  description.setText(textList.consecration_desc);
    		  konsikrasi_countDownTimer.start();
    		  counter--;
    	  }
    	  else if (counter == 3)
    	  {
    		  r.play();
    		  title.setText(textList.thanksgiving);
    		  description.setText(textList.thanksgiving_desc);
    		  ucapSyukur_countDownTimer.start();
    		  counter--;
    	  }
    	  else if (counter == 2)
    	  {
    		  r.play();
    		  title.setText(textList.petition);
    		  description.setText(textList.petition_desc);
    		  doaPermohonan_countDownTimer.start();
    		  counter--;
    	  }
    	  else if (counter == 1)
    	  {
    		  exitButton.setVisibility(View.VISIBLE);
    		  r.play();
    		  title.setText(textList.the_end);
    		  description.setText("");
    		  text.setText(textList.halelujah);
    	      startB.setText(textList.reset);
    	      timerHasStarted = false;
    	  }
      }
    
      @Override
      public void onTick(long millisUntilFinished) {
       //text.setText("" + millisUntilFinished / 1000);
    	  int secondsLeft = 0;
    	  if (Math.round((float)millisUntilFinished / 1000.0f) != secondsLeft)
          {  
              secondsLeft = Math.round((float)millisUntilFinished / 1000.0f);
              text.setText("" +secondsLeft );
          }
      }
     }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
	
	public void startActivity(View v)
	{
		if (!timerHasStarted) {
			exitButton.setVisibility(View.GONE);
			counter = 7;
		       menyeru_countDownTimer.start();
		       timerHasStarted = true;
		       startB.setText(textList.stop);
		       title.setText(textList.calling);
		       description.setText(textList.calling_desc);
		      } else {
		    	  //Stop All Activated timer
		       menyeru_countDownTimer.cancel();
		       berdoa_countDownTimer.cancel();
		       doabaca_countDownTimer.cancel();
		       mengakuDosa_countDownTimer.cancel();
		       konsikrasi_countDownTimer.cancel();
		       ucapSyukur_countDownTimer.cancel();
		       doaPermohonan_countDownTimer.cancel();
		       
		       timerHasStarted = false;
		       startB.setText(textList.reset);
		       exitButton.setVisibility(View.VISIBLE);
		       startB.setVisibility(View.VISIBLE);
		      }
	}
	
	public void exitApp(View v)
	{
		finish();          
        moveTaskToBack(true);
	}
	
	public void textListInit()
	{
		textList = new TextList();
		textList.start = getResources().getString(R.string.start);
		textList.title = getResources().getString(R.string.title);
		textList.exit = getResources().getString(R.string.exit);
		textList.calling = getResources().getString(R.string.calling);
		textList.calling_desc = getResources().getString(R.string.calling_desc);
		textList.praying = getResources().getString(R.string.praying);
		textList.praying_desc = getResources().getString(R.string.praying_desc);
		textList.pray_reading = getResources().getString(R.string.pray_reading);
		textList.pray_reading_desc = getResources().getString(R.string.pray_reading_desc);
		textList.confession = getResources().getString(R.string.confession);
		textList.confession_desc = getResources().getString(R.string.confession_desc);
		textList.consecration = getResources().getString(R.string.consecration);
		textList.consecration_desc = getResources().getString(R.string.consecration_desc);
		textList.thanksgiving = getResources().getString(R.string.thanksgiving);
		textList.thanksgiving_desc = getResources().getString(R.string.thanksgiving_desc);
		textList.petition = getResources().getString(R.string.petition);
		textList.petition_desc = getResources().getString(R.string.petition_desc);
		textList.the_end = getResources().getString(R.string.the_end);
		textList.halelujah = getResources().getString(R.string.halelujah);
		textList.reset = getResources().getString(R.string.reset);
		textList.stop = getResources().getString(R.string.stop);
	}
	
	public void setIndonesianLocale()
	{
		Locale locale = new Locale("in");
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,
		      getBaseContext().getResources().getDisplayMetrics());
	}
	
	public void setEnglishLocale()
	{
		Locale locale = new Locale("en");
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,
		      getBaseContext().getResources().getDisplayMetrics());
	}
	
	public void saveLocale(String lang)
	{
	    String langPref = "Language";
	    SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(langPref, lang);
	    editor.commit();
	}
	

	public void loadLocale()
	{
	    String langPref = "Language";
	    SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
	    String language = prefs.getString(langPref, "");
	    changeLang(language);
	}
	
	public void changeLang(String lang)
	{
	    if (lang.equalsIgnoreCase(""))
	     return;
	    myLocale = new Locale(lang);
	    saveLocale(lang);
	    Locale.setDefault(myLocale);
	    android.content.res.Configuration config = new android.content.res.Configuration();
	    config.locale = myLocale;
	    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
	}
}
