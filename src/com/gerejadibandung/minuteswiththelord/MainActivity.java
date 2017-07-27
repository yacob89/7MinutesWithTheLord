/*
 * Creator: churchinbandung
 * March 29 2014
 * File name: MainActivity.java
 * Project: 7MinutesWithTheLord
 * Public repository: https://github.com/yacob89/7MinutesWithTheLord
 */

package com.gerejadibandung.minuteswiththelord;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends Activity implements OnClickListener {
	
	public TextList textList;
	 private boolean timerHasStarted = false;
	 private boolean shouldLoadPortuguese = false;
	 private Button startB;
	 private Button exitButton;
	 private Button pauseButton;
	 private Button restartButton;
	 private Button englishButton;
	 private Button indonesiaButton;
	 private Button spanishButton;
	 private Button koreanButton;
	 private Button portugueseButton;
	 private Button chineseButton;
	 private Button chineseTraditionalButton;
	 private Button germanButton;
	private Button tagalogButton;
	private Button russianButton;
	private Button ukrainianButton;
	 private ImageButton forwardButton;
	 private ImageButton backwardButton;
	 public TextView text;
	 public TextView title;
	 public TextView description;
	 public ScrollView scrollV;
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
	 private CountDownTimer continueTimer;
	 
	 private int counter;
	 private Locale myLocale;
	 private AlertDialog alertDialog;
	 
	 private int currentProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // UI Initialization
        startB = (Button) this.findViewById(R.id.button);
        exitButton = (Button) this.findViewById(R.id.exitButton);
        pauseButton = (Button) this.findViewById(R.id.buttonPause);
        forwardButton = (ImageButton) this.findViewById(R.id.buttonForward);
        backwardButton = (ImageButton) this.findViewById(R.id.buttonBackward);
        restartButton = (Button) this.findViewById(R.id.buttonRestart);
        englishButton = (Button) this.findViewById(R.id.buttonEnglish);
        indonesiaButton = (Button) this.findViewById(R.id.buttonIndonesia);
        spanishButton = (Button) this.findViewById(R.id.buttonSpanish);
        koreanButton = (Button) this.findViewById(R.id.buttonKorean);
        portugueseButton = (Button) this.findViewById(R.id.buttonPortuguese);
        chineseButton = (Button) this.findViewById(R.id.buttonChinese);
        chineseTraditionalButton = (Button) this.findViewById(R.id.buttonChineseTaiwan);
        germanButton = (Button) this.findViewById(R.id.buttonGerman);
		tagalogButton = (Button) this.findViewById(R.id.buttonTagalog);
		russianButton = (Button) this.findViewById(R.id.buttonRussian);
		ukrainianButton = (Button) this.findViewById(R.id.buttonUkrainian);
        text = (TextView) this.findViewById(R.id.timer);
        title = (TextView) this.findViewById(R.id.textView1);
        description = (TextView) this.findViewById(R.id.textView2);
        scrollV = (ScrollView) this.findViewById(R.id.scrollV);
        
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
        Typeface mFont = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-R.ttf");
        Typeface boldFont = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-B.ttf");
        startB.setTypeface(mFont);
        startB.setTextSize(12 * getResources().getDisplayMetrics().density);
        exitButton.setTypeface(mFont);
        exitButton.setTextSize(7* getResources().getDisplayMetrics().density);
        text.setTypeface(mFont);
        text.setTextSize(25* getResources().getDisplayMetrics().density);
        title.setTypeface(boldFont);
        title.setTextSize(12* getResources().getDisplayMetrics().density);
        description.setTypeface(mFont);
        description.setTextSize(9* getResources().getDisplayMetrics().density);
        pauseButton.setTypeface(mFont);
        pauseButton.setTextSize(7* getResources().getDisplayMetrics().density);
        englishButton.setTypeface(mFont);
        englishButton.setTextSize(7* getResources().getDisplayMetrics().density);
        indonesiaButton.setTypeface(mFont);
        indonesiaButton.setTextSize(7* getResources().getDisplayMetrics().density);
        spanishButton.setTypeface(mFont);
        spanishButton.setTextSize(7* getResources().getDisplayMetrics().density);
        koreanButton.setTypeface(mFont);
        koreanButton.setTextSize(7* getResources().getDisplayMetrics().density);
        portugueseButton.setTypeface(mFont);
        portugueseButton.setTextSize(7* getResources().getDisplayMetrics().density);
        chineseButton.setTypeface(mFont);
        chineseButton.setTextSize(7* getResources().getDisplayMetrics().density);
        chineseTraditionalButton.setTypeface(mFont);
        chineseTraditionalButton.setTextSize(7* getResources().getDisplayMetrics().density);
        germanButton.setTypeface(mFont);
        germanButton.setTextSize(7* getResources().getDisplayMetrics().density);
		tagalogButton.setTypeface(mFont);
		tagalogButton.setTextSize(7* getResources().getDisplayMetrics().density);
		russianButton.setTypeface(mFont);
		russianButton.setTextSize(7* getResources().getDisplayMetrics().density);
		ukrainianButton.setTypeface(mFont);
		ukrainianButton.setTextSize(7* getResources().getDisplayMetrics().density);
        
        // Keep The screen on during activity
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        timerHasStarted = false;
        
        // Hid Keluar Button
        exitButton.setVisibility(View.GONE);
        startB.setVisibility(View.GONE);
        forwardButton.setVisibility(View.GONE);
        backwardButton.setVisibility(View.GONE);
        pauseButton.setVisibility(View.GONE);
        restartButton.setVisibility(View.GONE);
        text.setVisibility(View.GONE);
        englishButton.setVisibility(View.VISIBLE);
        indonesiaButton.setVisibility(View.VISIBLE);
        spanishButton.setVisibility(View.VISIBLE);
        koreanButton.setVisibility(View.VISIBLE);
        portugueseButton.setVisibility(View.VISIBLE);
        chineseButton.setVisibility(View.VISIBLE);
        chineseTraditionalButton.setVisibility(View.VISIBLE);
        germanButton.setVisibility(View.VISIBLE);
		tagalogButton.setVisibility(View.VISIBLE);
		russianButton.setVisibility(View.VISIBLE);
		ukrainianButton.setVisibility(View.VISIBLE);
        //scrollV.setVisibility(View.VISIBLE);
        scrollV.setVerticalScrollBarEnabled(true);
        
        /*
        //Check Fist Install
        //boolean firstrun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstrun", true);
        //if (firstrun){
        	final CharSequence[] gender = {"English (US)","Bahasa Indonesia"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("")
                   .setItems(gender, new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int which) {
                       // The 'which' argument contains the index position
                       // of the selected item
                    	   if(gender[which]=="English (US)")
                           {
                           	changeLang("en");
                         	 exitButton.setVisibility(View.GONE);
                 			counter = 7;
                 		       menyeru_countDownTimer.start();
                 		       timerHasStarted = true;
                 		       startB.setText(textList.stop);
                 		       title.setText(textList.calling);
                 		       description.setText(textList.calling_desc);
                 		       //forwardButton.setVisibility(View.VISIBLE);
                 		       backwardButton.setVisibility(View.VISIBLE);
                 		       pauseButton.setVisibility(View.VISIBLE);
                 		       exitButton.setVisibility(View.VISIBLE);
                 		       startB.setVisibility(View.GONE);
                 		       restartButton.setVisibility(View.GONE);
                 		       pauseButton.setText(textList.pause);
                 		       r.play();
                           }
                           else if (gender[which]=="Bahasa Indonesia")
                           {
                           	changeLang("in");
                         	 exitButton.setVisibility(View.GONE);
                 			counter = 7;
                 		       menyeru_countDownTimer.start();
                 		       timerHasStarted = true;
                 		       startB.setText(textList.stop);
                 		       title.setText(textList.calling);
                 		       description.setText(textList.calling_desc);
                 		       //forwardButton.setVisibility(View.VISIBLE);
                 		       backwardButton.setVisibility(View.VISIBLE);
                 		       pauseButton.setVisibility(View.VISIBLE);
                 		       exitButton.setVisibility(View.VISIBLE);
                 		       startB.setVisibility(View.GONE);
                 		       restartButton.setVisibility(View.GONE);
                 		       pauseButton.setText(textList.pause);
                 		       r.play();
                           }
                   }
            });
            builder.show();
        	
        // Save the state
        getSharedPreferences("PREFERENCE", MODE_PRIVATE)
            .edit()
            .putBoolean("firstrun", false)
            .commit();
        //}
        /*else
        {
        	loadLocale();
        }*/
        
        textListInit(); //Strings initialization
        title.setText(textList.title);
        exitButton.setText(textList.exit);
    }


    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//pauseTime();
	}
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
      // refresh your views here
    	textListInit();
      super.onConfigurationChanged(newConfig);
    }
    
    public void pauseTime()
    {
    	menyeru_countDownTimer.cancel();
	    berdoa_countDownTimer.cancel();
	    doabaca_countDownTimer.cancel();
	    mengakuDosa_countDownTimer.cancel();
	    konsikrasi_countDownTimer.cancel();
	    ucapSyukur_countDownTimer.cancel();
	    doaPermohonan_countDownTimer.cancel();
	    if (continueTimer != null)
	    {
	    	continueTimer.cancel();
	    }
	    
	    timerHasStarted = false;
	    pauseButton.setText(textList.unpause);
    }


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        exitByBackKey();

	        //moveTaskToBack(false);

	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

	protected void exitByBackKey() {
		
		if (counter == 7)
		{
			showExitAlert(textList.number_7);
		}
		else if (counter == 6)
		{
			showExitAlert(textList.number_65);
		}
		else if (counter == 5)
		{
			showExitAlert(textList.number_55);
		}
		else if (counter == 4)
		{
			showExitAlert(textList.number_3);
		}
		else if (counter == 3)
		{
			showExitAlert(textList.number_2);
		}
		else if (counter == 2)
		{
			showExitAlert(textList.number_15);
		}
		else if (counter == 1)
		{
			showExitAlert(textList.number_1);
		}
		else
		{
			finish();          
	        moveTaskToBack(true);
		}

	}
	
	public void showExitAlert(String menit)
	{
		Typeface boldFont = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-B.ttf");
        //startB.setTypeface(boldFont);
		String frontGate;
		if (shouldLoadPortuguese)
		{
			frontGate = "";
		}
		else
		{
			frontGate = " [";
		}
		
		pauseTime();
		AlertDialog alertbox = new AlertDialog.Builder(this)
	    .setMessage(textList.alert_message1+" "+Html.fromHtml("<b>"+frontGate+"</b>")+ Html.fromHtml("<b>"+menit+"</b>") + " " + Html.fromHtml("<b>"+textList.alert_message3+"</b>")+" " +textList.alert_message2)
	    .setPositiveButton("RESUME", new DialogInterface.OnClickListener() {

	        // do something when the button is clicked
	        public void onClick(DialogInterface arg0, int arg1) {
	        	currentProgress = Integer.parseInt(text.getText().toString());
	        	continueTimer = new MyCountDownTimer(currentProgress * 1000, interval);
				continueTimer.start();
				timerHasStarted = true;
				pauseButton.setText(textList.pause);
	        }
	    })
	    .setNegativeButton("EXIT ANYWAY", new DialogInterface.OnClickListener() {

	        // do something when the button is clicked
	        public void onClick(DialogInterface arg0, int arg1) {
	        	finish();
	            //close();
	                       }
	    })
	      .show();
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
    		  pauseButton.setVisibility(View.GONE);
    		  startB.setVisibility(View.GONE);
  	  	    forwardButton.setVisibility(View.GONE);
  	  	    backwardButton.setVisibility(View.GONE);
  	  	restartButton.setVisibility(View.VISIBLE);
    		  r.play();
    		  title.setText(textList.the_end);
    		  description.setText("");
    		  text.setText(textList.halelujah);
    	      startB.setText(textList.reset);
    	      timerHasStarted = false;
    	      counter--;
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
		       //forwardButton.setVisibility(View.VISIBLE);
		       backwardButton.setVisibility(View.VISIBLE);
		       pauseButton.setVisibility(View.VISIBLE);
		       exitButton.setVisibility(View.VISIBLE);
		       startB.setVisibility(View.GONE);
		       restartButton.setVisibility(View.GONE);
		       englishButton.setVisibility(View.GONE);
		        indonesiaButton.setVisibility(View.GONE);
		        spanishButton.setVisibility(View.GONE);
		        koreanButton.setVisibility(View.GONE);
		        portugueseButton.setVisibility(View.GONE);
		        chineseButton.setVisibility(View.GONE);
		        chineseTraditionalButton.setVisibility(View.GONE);
		        germanButton.setVisibility(View.GONE);
			tagalogButton.setVisibility(View.GONE);
			russianButton.setVisibility(View.GONE);
			ukrainianButton.setVisibility(View.GONE);
		        //scrollV.setVisibility(View.GONE);
		        text.setVisibility(View.VISIBLE);
		       pauseButton.setText(textList.pause);
		       r.play();
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
		       startB.setText(textList.start);
		       exitButton.setVisibility(View.VISIBLE);
		       startB.setVisibility(View.VISIBLE);
		}
	}
	
	public void restartActivity(View v)
	{
		if (!timerHasStarted) {
			exitButton.setVisibility(View.GONE);
			counter = 7;
		       menyeru_countDownTimer.start();
		       timerHasStarted = true;
		       startB.setText(textList.stop);
		       title.setText(textList.calling);
		       description.setText(textList.calling_desc);
		       //forwardButton.setVisibility(View.VISIBLE);
		       backwardButton.setVisibility(View.VISIBLE);
		       pauseButton.setVisibility(View.VISIBLE);
		       exitButton.setVisibility(View.VISIBLE);
		       startB.setVisibility(View.GONE);
		       restartButton.setVisibility(View.GONE);
		       pauseButton.setText(textList.pause);
		}
	}
	
	public void exitApp(View v)
	{
		//finish();          
        //moveTaskToBack(true);
		if (counter == 7)
		{
			showExitAlert(textList.number_7);
		}
		else if (counter == 6)
		{
			showExitAlert(textList.number_65);
		}
		else if (counter == 5)
		{
			showExitAlert(textList.number_55);
		}
		else if (counter == 4)
		{
			showExitAlert(textList.number_3);
		}
		else if (counter == 3)
		{
			showExitAlert(textList.number_2);
		}
		else if (counter == 2)
		{
			showExitAlert(textList.number_15);
		}
		else if (counter == 1)
		{
			showExitAlert(textList.number_1);
		}
		else
		{
			finish();          
	        moveTaskToBack(true);
		}
	}
	
	public void pauseTimer(View v)
	{
		currentProgress = Integer.parseInt(text.getText().toString());
		
		if(timerHasStarted)
		{
			menyeru_countDownTimer.cancel();
		    berdoa_countDownTimer.cancel();
		    doabaca_countDownTimer.cancel();
		    mengakuDosa_countDownTimer.cancel();
		    konsikrasi_countDownTimer.cancel();
		    ucapSyukur_countDownTimer.cancel();
		    doaPermohonan_countDownTimer.cancel();
		    if (continueTimer != null)
		    {
		    	continueTimer.cancel();
		    }
		    
		    timerHasStarted = false;
		    pauseButton.setText(textList.unpause);
		}
		else
		{
			continueTimer = new MyCountDownTimer(currentProgress * 1000, interval);
			continueTimer.start();
			timerHasStarted = true;
			pauseButton.setText(textList.pause);
		}
	}
	
	public void forwardTimer(View v)
	{
		if (counter > 0)
		{
			counter--;
		}
		
		menyeru_countDownTimer.cancel();
	    berdoa_countDownTimer.cancel();
	    doabaca_countDownTimer.cancel();
	    mengakuDosa_countDownTimer.cancel();
	    konsikrasi_countDownTimer.cancel();
	    ucapSyukur_countDownTimer.cancel();
	    doaPermohonan_countDownTimer.cancel();
	    if (continueTimer != null)
	    {
	    	continueTimer.cancel();
	    }
		
		timerHasStarted = false;
		pauseButton.setVisibility(View.VISIBLE);
		exitButton.setVisibility(View.GONE);
		//forwardButton.setVisibility(View.VISIBLE);
  	    backwardButton.setVisibility(View.VISIBLE);
		
		if (counter == 6)
		{
			text.setText(String.valueOf(60));
			title.setText(textList.praying);
  		  	description.setText(textList.praying_desc);
			continueTimer = new MyCountDownTimer(60 * 1000, interval);
		}
		else if (counter == 5)
		{
			text.setText(String.valueOf(150));
			title.setText(textList.pray_reading);
  		  	description.setText(textList.pray_reading_desc);
			continueTimer = new MyCountDownTimer(150 * 1000, interval);
		}
		else if (counter == 4)
		{
			text.setText(String.valueOf(60));
			title.setText(textList.confession);
  		  	description.setText(textList.confession_desc);
			continueTimer = new MyCountDownTimer(60 * 1000, interval);
		}
		else if (counter == 3)
		{
			text.setText(String.valueOf(30));
			title.setText(textList.consecration);
  		  	description.setText(textList.consecration_desc);
			continueTimer = new MyCountDownTimer(30 * 1000, interval);
		}
		else if (counter == 2)
		{
			text.setText(String.valueOf(30));
			title.setText(textList.thanksgiving);
  		  	description.setText(textList.thanksgiving_desc);
			continueTimer = new MyCountDownTimer(30 * 1000, interval);
		}
		else if (counter == 1)
		{
			text.setText(String.valueOf(60));
			title.setText(textList.petition);
  		  	description.setText(textList.petition_desc);
			continueTimer = new MyCountDownTimer(60 * 1000, interval);
		}
		else if (counter == 0)
		{
			title.setText(textList.the_end);
	  		description.setText("");
	  		text.setText(textList.halelujah);
	  	    startB.setText(textList.reset);
	  	    exitButton.setVisibility(View.VISIBLE);
	  	    pauseButton.setVisibility(View.GONE);
	  	    startB.setVisibility(View.VISIBLE);
	  	    //forwardButton.setVisibility(View.GONE);
	  	    backwardButton.setVisibility(View.GONE);
		}
		pauseButton.setText(textList.unpause);
	}
	
	public void backwardTimer(View v)
	{
		if (counter < 7)
		{
			counter++;
		}
		
		menyeru_countDownTimer.cancel();
	    berdoa_countDownTimer.cancel();
	    doabaca_countDownTimer.cancel();
	    mengakuDosa_countDownTimer.cancel();
	    konsikrasi_countDownTimer.cancel();
	    ucapSyukur_countDownTimer.cancel();
	    doaPermohonan_countDownTimer.cancel();
	    if (continueTimer != null)
	    {
	    	continueTimer.cancel();
	    }
		
		timerHasStarted = false;
		pauseButton.setVisibility(View.VISIBLE);
		//forwardButton.setVisibility(View.VISIBLE);
  	    backwardButton.setVisibility(View.VISIBLE);
		exitButton.setVisibility(View.VISIBLE);
		
		if (counter == 7)
		{
			text.setText(String.valueOf(30));
			title.setText(textList.calling);
  		  	description.setText(textList.calling_desc);
			continueTimer = new MyCountDownTimer(30 * 1000, interval);
		}
		else if (counter == 6)
		{
			text.setText(String.valueOf(60));
			title.setText(textList.praying);
  		  	description.setText(textList.praying_desc);
			continueTimer = new MyCountDownTimer(60 * 1000, interval);
		}
		else if (counter == 5)
		{
			text.setText(String.valueOf(150));
			title.setText(textList.pray_reading);
  		  	description.setText(textList.pray_reading_desc);
			continueTimer = new MyCountDownTimer(150 * 1000, interval);
		}
		else if (counter == 4)
		{
			text.setText(String.valueOf(60));
			title.setText(textList.confession);
  		  	description.setText(textList.confession_desc);
			continueTimer = new MyCountDownTimer(60 * 1000, interval);
		}
		else if (counter == 3)
		{
			text.setText(String.valueOf(30));
			title.setText(textList.consecration);
  		  	description.setText(textList.consecration_desc);
			continueTimer = new MyCountDownTimer(30 * 1000, interval);
		}
		else if (counter == 2)
		{
			text.setText(String.valueOf(30));
			title.setText(textList.thanksgiving);
  		  	description.setText(textList.thanksgiving_desc);
			continueTimer = new MyCountDownTimer(30 * 1000, interval);
		}
		else if (counter == 1)
		{
			text.setText(String.valueOf(60));
			title.setText(textList.petition);
  		  	description.setText(textList.petition_desc);
			continueTimer = new MyCountDownTimer(60 * 1000, interval);
		}
		else if (counter == 0)
		{
			title.setText(textList.the_end);
	  		description.setText("");
	  		text.setText(textList.halelujah);
	  	    startB.setText(textList.reset);
	  	    exitButton.setVisibility(View.VISIBLE);
	  	    pauseButton.setVisibility(View.GONE);
	  	    startB.setVisibility(View.VISIBLE);
	  	    //forwardButton.setVisibility(View.GONE);
	  	    backwardButton.setVisibility(View.GONE);
		}
		pauseButton.setText(textList.unpause);
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
		textList.pause = getResources().getString(R.string.pause);
		textList.unpause = getResources().getString(R.string.unpause);
		textList.alert_message1 = getResources().getString(R.string.alert_message1);
		textList.alert_message2 = getResources().getString(R.string.alert_message2);
		textList.alert_message3 = getResources().getString(R.string.alert_message3);
		textList.number_7 = getResources().getString(R.string.number_7);
		textList.number_65 = getResources().getString(R.string.number_65);
		textList.number_55 = getResources().getString(R.string.number_55);
		textList.number_3 = getResources().getString(R.string.number_3);
		textList.number_2 = getResources().getString(R.string.number_2);
		textList.number_15 = getResources().getString(R.string.number_15);
		textList.number_1 = getResources().getString(R.string.number_1);
	}
	
	public void setIndonesianLocale(View v)
	{
		changeLang("in");
    	setupGeneralUILocale();
    	shouldLoadPortuguese = false;
	}
	
	public void setEnglishLocale(View v)
	{
		changeLang("en");
		setupGeneralUILocale();
		shouldLoadPortuguese = false;
	}
	
	public void setSpanishLocale(View v)
	{
		changeLang("es");
		setupGeneralUILocale();
		shouldLoadPortuguese = false;
	}
	
	public void setKoreanLocale(View v)
	{
		changeLang("ko");
		setupGeneralUILocale();
		shouldLoadPortuguese = false;
	}
	
	public void setPortugueseLocale(View v)
	{
		changeLang("pt");
		setupGeneralUILocale();
		shouldLoadPortuguese = true;
	}
	
	public void setChineseLocale(View v)
	{
		changeLang("zh");
		setupGeneralUILocale();
		shouldLoadPortuguese = false;
	}
	
	public void setChineseTraditionalLocale(View v)
	{
		changeLang("zt");
		setupGeneralUILocale();
		shouldLoadPortuguese = false;
	}
	
	public void setGermanLocale(View v)
	{
		changeLang("de");
		setupGeneralUILocale();
		shouldLoadPortuguese = false;
	}

	public void setTagalogLocale(View v)
	{
		changeLang("tl");
		setupGeneralUILocale();
		shouldLoadPortuguese = true;
	}

	public void setRussianLocale(View v)
	{
		changeLang("ru");
		setupGeneralUILocale();
		shouldLoadPortuguese = true;
	}

	public void setUkrainianLocale(View v)
	{
		changeLang("uk");
		setupGeneralUILocale();
		shouldLoadPortuguese = true;
	}
	
	public void setupGeneralUILocale()
	{
		exitButton.setVisibility(View.GONE);
		counter = 7;
	       menyeru_countDownTimer.start();
	       timerHasStarted = true;
	       startB.setText(textList.stop);
	       title.setText(textList.calling);
	       description.setText(textList.calling_desc);
	       //forwardButton.setVisibility(View.VISIBLE);
	       backwardButton.setVisibility(View.VISIBLE);
	       pauseButton.setVisibility(View.VISIBLE);
	       exitButton.setVisibility(View.VISIBLE);
	       startB.setVisibility(View.GONE);
	       restartButton.setVisibility(View.GONE);
	       pauseButton.setText(textList.pause);
	       englishButton.setVisibility(View.GONE);
	        indonesiaButton.setVisibility(View.GONE);
	        spanishButton.setVisibility(View.GONE);
	        koreanButton.setVisibility(View.GONE);
	        portugueseButton.setVisibility(View.GONE);
	        chineseButton.setVisibility(View.GONE);
	        chineseTraditionalButton.setVisibility(View.GONE);
	        germanButton.setVisibility(View.GONE);
		tagalogButton.setVisibility(View.GONE);
		russianButton.setVisibility(View.GONE);
		ukrainianButton.setVisibility(View.GONE);
	        text.setVisibility(View.VISIBLE);
	       r.play();
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
	    
	    Resources res = getResources();
	    DisplayMetrics dm = res.getDisplayMetrics();
	    Configuration conf = res.getConfiguration();
	    conf.locale = myLocale;
	    res.updateConfiguration(conf, dm);
	    onConfigurationChanged(conf);
	}
}
