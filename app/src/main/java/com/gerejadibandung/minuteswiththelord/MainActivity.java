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
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnClickListener {
	
	public TextList textList;
	 private boolean timerHasStarted = false;
	 private boolean shouldLoadPortuguese = false;

	@BindView(R.id.button)
	 Button startB;
    @BindView(R.id.exitButton)
    Button exitButton;
    @BindView(R.id.buttonPause)
    Button pauseButton;
    @BindView(R.id.buttonRestart)
    Button restartButton;
    @BindView(R.id.buttonEnglish)
    Button englishButton;
    @BindView(R.id.buttonIndonesia)
    Button indonesiaButton;
    @BindView(R.id.buttonSpanish)
    Button spanishButton;
    @BindView(R.id.buttonKorean)
    Button koreanButton;
    @BindView(R.id.buttonPortuguese)
    Button portugueseButton;
    @BindView(R.id.buttonChinese)
    Button chineseButton;
    @BindView(R.id.buttonChineseTaiwan)
    Button chineseTraditionalButton;
    @BindView(R.id.buttonGerman)
    Button germanButton;
    @BindView(R.id.buttonTagalog)
    Button tagalogButton;
    @BindView(R.id.buttonRussian)
    Button russianButton;
    @BindView(R.id.buttonUkrainian)
    Button ukrainianButton;
    @BindView(R.id.buttonForward)
    ImageButton forwardButton;
    @BindView(R.id.buttonBackward)
    ImageButton backwardButton;
    @BindView(R.id.timer)
    TextView timer;
    @BindView(R.id.textView1)
    TextView title;
    @BindView(R.id.textView2)
    TextView description;
    @BindView(R.id.scrollV)
    ScrollView scrollV;
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        // Timer Initialization
        menyeru_countDownTimer = new MyCountDownTimer(menyeru_startTime, interval);
        berdoa_countDownTimer = new MyCountDownTimer(berdoa_startTime, interval);
        doabaca_countDownTimer = new MyCountDownTimer(doabaca_startTime, interval);
        mengakuDosa_countDownTimer = new MyCountDownTimer(mengakuDosa_startTime, interval);
        konsikrasi_countDownTimer = new MyCountDownTimer(konsikrasi_startTime, interval);
        ucapSyukur_countDownTimer = new MyCountDownTimer(ucapSyukur_startTime, interval);
        doaPermohonan_countDownTimer = new MyCountDownTimer(doaPermohonan_startTime, interval);
        getTimer().setText(getTimer().getText() + String.valueOf(menyeru_startTime / 1000));
        getDescription().setText(getString(R.string.select_language));
        
        // Notification sound initialization
        // Use default notification
        notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        Uri path = Uri.parse("android.resource://com.gerejadibandung.minuteswiththelord/"+R.raw.sirius);
        r = RingtoneManager.getRingtone(getApplicationContext(), path);
        
        // Set custom roboto fonts
        Typeface mFont = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-R.ttf");
        Typeface boldFont = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-B.ttf");
        getStartB().setTypeface(mFont);
        getStartB().setTextSize(12 * getResources().getDisplayMetrics().density);
        getExitButton().setTypeface(mFont);
        getExitButton().setTextSize(7* getResources().getDisplayMetrics().density);
        getTimer().setTypeface(mFont);
        getTimer().setTextSize(25* getResources().getDisplayMetrics().density);
        getTitleView().setTypeface(boldFont);
        getTitleView().setTextSize(12* getResources().getDisplayMetrics().density);
        getDescription().setTypeface(mFont);
        getDescription().setTextSize(9* getResources().getDisplayMetrics().density);
        getPauseButton().setTypeface(mFont);
        getPauseButton().setTextSize(7* getResources().getDisplayMetrics().density);
        getEnglishButton().setTypeface(mFont);
        getEnglishButton().setTextSize(7* getResources().getDisplayMetrics().density);
        getIndonesiaButton().setTypeface(mFont);
        getIndonesiaButton().setTextSize(7* getResources().getDisplayMetrics().density);
        getSpanishButton().setTypeface(mFont);
        getSpanishButton().setTextSize(7* getResources().getDisplayMetrics().density);
        getKoreanButton().setTypeface(mFont);
        getKoreanButton().setTextSize(7* getResources().getDisplayMetrics().density);
        getPortugueseButton().setTypeface(mFont);
        getPortugueseButton().setTextSize(7* getResources().getDisplayMetrics().density);
        getChineseButton().setTypeface(mFont);
        getChineseButton().setTextSize(7* getResources().getDisplayMetrics().density);
        getChineseTraditionalButton().setTypeface(mFont);
        getChineseTraditionalButton().setTextSize(7* getResources().getDisplayMetrics().density);
        getGermanButton().setTypeface(mFont);
        getGermanButton().setTextSize(7* getResources().getDisplayMetrics().density);
		getTagalogButton().setTypeface(mFont);
		getTagalogButton().setTextSize(7* getResources().getDisplayMetrics().density);
		getRussianButton().setTypeface(mFont);
		getRussianButton().setTextSize(7* getResources().getDisplayMetrics().density);
		getUkrainianButton().setTypeface(mFont);
		getUkrainianButton().setTextSize(7* getResources().getDisplayMetrics().density);
        
        // Keep The screen on during activity
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        timerHasStarted = false;
        
        // Hid Keluar Button
        getExitButton().setVisibility(View.GONE);
        getStartB().setVisibility(View.GONE);
        getForwardButton().setVisibility(View.GONE);
        getBackwardButton().setVisibility(View.GONE);
        getPauseButton().setVisibility(View.GONE);
        getRestartButton().setVisibility(View.GONE);
        getTimer().setVisibility(View.GONE);
        getEnglishButton().setVisibility(View.VISIBLE);
        getIndonesiaButton().setVisibility(View.VISIBLE);
        getSpanishButton().setVisibility(View.VISIBLE);
        getKoreanButton().setVisibility(View.VISIBLE);
        getPortugueseButton().setVisibility(View.VISIBLE);
        getChineseButton().setVisibility(View.VISIBLE);
        getChineseTraditionalButton().setVisibility(View.VISIBLE);
        getGermanButton().setVisibility(View.VISIBLE);
		getTagalogButton().setVisibility(View.VISIBLE);
		getRussianButton().setVisibility(View.VISIBLE);
		getUkrainianButton().setVisibility(View.VISIBLE);
        //scrollV.setVisibility(View.VISIBLE);
        getScrollV().setVerticalScrollBarEnabled(true);
        
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
                 		       startB.setTimer(textList.stop);
                 		       title.setTimer(textList.calling);
                 		       description.setTimer(textList.calling_desc);
                 		       //forwardButton.setVisibility(View.VISIBLE);
                 		       backwardButton.setVisibility(View.VISIBLE);
                 		       pauseButton.setVisibility(View.VISIBLE);
                 		       exitButton.setVisibility(View.VISIBLE);
                 		       startB.setVisibility(View.GONE);
                 		       restartButton.setVisibility(View.GONE);
                 		       pauseButton.setTimer(textList.pause);
                 		       r.play();
                           }
                           else if (gender[which]=="Bahasa Indonesia")
                           {
                           	changeLang("in");
                         	 exitButton.setVisibility(View.GONE);
                 			counter = 7;
                 		       menyeru_countDownTimer.start();
                 		       timerHasStarted = true;
                 		       startB.setTimer(textList.stop);
                 		       title.setTimer(textList.calling);
                 		       description.setTimer(textList.calling_desc);
                 		       //forwardButton.setVisibility(View.VISIBLE);
                 		       backwardButton.setVisibility(View.VISIBLE);
                 		       pauseButton.setVisibility(View.VISIBLE);
                 		       exitButton.setVisibility(View.VISIBLE);
                 		       startB.setVisibility(View.GONE);
                 		       restartButton.setVisibility(View.GONE);
                 		       pauseButton.setTimer(textList.pause);
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
        getTitleView().setText(textList.title);
        getExitButton().setText(textList.exit);
    }


    @Override
	protected void onPause() {
		super.onPause();
		//pauseTime();
	}
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
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
	    getPauseButton().setText(textList.unpause);
    }


	@Override
	protected void onResume() {
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
        new MaterialDialog.Builder(this)
                .content(R.string.alert, menit)
                .positiveText(R.string.unpause)
                .negativeText(R.string.force_exit)
                .contentColorRes(R.color.black)
                .onPositive((dialog, which) -> {
                    currentProgress = Integer.parseInt(getTimer().getText().toString());
                    continueTimer = new MyCountDownTimer(currentProgress * 1000, interval);
                    continueTimer.start();
                    timerHasStarted = true;
                    getPauseButton().setText(textList.pause);
                })
                .onNegative((dialog, which) -> finish())
                .canceledOnTouchOutside(false)
                .positiveColorRes(R.color.green)
                .negativeColorRes(R.color.red)
                .show();
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public Button getStartB() {
        return startB;
    }

    public void setStartB(Button startB) {
        this.startB = startB;
    }

    public Button getExitButton() {
        return exitButton;
    }

    public void setExitButton(Button exitButton) {
        this.exitButton = exitButton;
    }

    public Button getPauseButton() {
        return pauseButton;
    }

    public void setPauseButton(Button pauseButton) {
        this.pauseButton = pauseButton;
    }

    public Button getRestartButton() {
        return restartButton;
    }

    public void setRestartButton(Button restartButton) {
        this.restartButton = restartButton;
    }

    public Button getEnglishButton() {
        return englishButton;
    }

    public void setEnglishButton(Button englishButton) {
        this.englishButton = englishButton;
    }

    public Button getIndonesiaButton() {
        return indonesiaButton;
    }

    public void setIndonesiaButton(Button indonesiaButton) {
        this.indonesiaButton = indonesiaButton;
    }

    public Button getSpanishButton() {
        return spanishButton;
    }

    public void setSpanishButton(Button spanishButton) {
        this.spanishButton = spanishButton;
    }

    public Button getKoreanButton() {
        return koreanButton;
    }

    public void setKoreanButton(Button koreanButton) {
        this.koreanButton = koreanButton;
    }

    public Button getPortugueseButton() {
        return portugueseButton;
    }

    public void setPortugueseButton(Button portugueseButton) {
        this.portugueseButton = portugueseButton;
    }

    public Button getChineseButton() {
        return chineseButton;
    }

    public void setChineseButton(Button chineseButton) {
        this.chineseButton = chineseButton;
    }

    public Button getChineseTraditionalButton() {
        return chineseTraditionalButton;
    }

    public void setChineseTraditionalButton(Button chineseTraditionalButton) {
        this.chineseTraditionalButton = chineseTraditionalButton;
    }

    public Button getGermanButton() {
        return germanButton;
    }

    public void setGermanButton(Button germanButton) {
        this.germanButton = germanButton;
    }

    public Button getTagalogButton() {
        return tagalogButton;
    }

    public void setTagalogButton(Button tagalogButton) {
        this.tagalogButton = tagalogButton;
    }

    public Button getRussianButton() {
        return russianButton;
    }

    public void setRussianButton(Button russianButton) {
        this.russianButton = russianButton;
    }

    public Button getUkrainianButton() {
        return ukrainianButton;
    }

    public void setUkrainianButton(Button ukrainianButton) {
        this.ukrainianButton = ukrainianButton;
    }

    public ImageButton getForwardButton() {
        return forwardButton;
    }

    public void setForwardButton(ImageButton forwardButton) {
        this.forwardButton = forwardButton;
    }

    public ImageButton getBackwardButton() {
        return backwardButton;
    }

    public void setBackwardButton(ImageButton backwardButton) {
        this.backwardButton = backwardButton;
    }

    public TextView getTimer() {
        return timer;
    }

    public void setTimer(TextView timer) {
        this.timer = timer;
    }

    public TextView getTitleView() {
        return title;
    }

    public void setTitleView(TextView title) {
        this.title = title;
    }

    public TextView getDescription() {
        return description;
    }

    public void setDescription(TextView description) {
        this.description = description;
    }

    public ScrollView getScrollV() {
        return scrollV;
    }

    public void setScrollV(ScrollView scrollV) {
        this.scrollV = scrollV;
    }

    public class MyCountDownTimer extends CountDownTimer {
      public MyCountDownTimer(long startTime, long interval) {
       super(startTime, interval);
      }
    
      @Override
      public void onFinish() {
    	  if (counter == 7){
    		  r.play();
    		  getTitleView().setText(textList.praying);
    		  getDescription().setText(textList.praying_desc);
    		  berdoa_countDownTimer.start();
    		  counter--;
    	  }
    	  else if (counter == 6)
    	  {
    		  r.play();
    		  getTitleView().setText(textList.pray_reading);
    		  getDescription().setText(textList.pray_reading_desc);
    		  doabaca_countDownTimer.start();
    		  counter--;
    	  }
    	  else if (counter == 5)
    	  {
    		  r.play();
    		  getTitleView().setText(textList.confession);
    		  getDescription().setText(textList.confession_desc);
    		  mengakuDosa_countDownTimer.start();
    		  counter--;
    	  }
    	  else if (counter == 4)
    	  {
    		  r.play();
    		  getTitleView().setText(textList.consecration);
    		  getDescription().setText(textList.consecration_desc);
    		  konsikrasi_countDownTimer.start();
    		  counter--;
    	  }
    	  else if (counter == 3)
    	  {
    		  r.play();
    		  getTitleView().setText(textList.thanksgiving);
    		  getDescription().setText(textList.thanksgiving_desc);
    		  ucapSyukur_countDownTimer.start();
    		  counter--;
    	  }
    	  else if (counter == 2)
    	  {
    		  r.play();
    		  getTitleView().setText(textList.petition);
    		  getDescription().setText(textList.petition_desc);
    		  doaPermohonan_countDownTimer.start();
    		  counter--;
    	  }
    	  else if (counter == 1)
    	  {
    		  getExitButton().setVisibility(View.VISIBLE);
    		  getPauseButton().setVisibility(View.GONE);
    		  getStartB().setVisibility(View.GONE);
  	  	    getForwardButton().setVisibility(View.GONE);
  	  	    getBackwardButton().setVisibility(View.GONE);
  	  	getRestartButton().setVisibility(View.VISIBLE);
    		  r.play();
    		  getTitleView().setText(textList.the_end);
    		  getDescription().setText("");
    		  getTimer().setText(textList.halelujah);
    	      getStartB().setText(textList.reset);
    	      timerHasStarted = false;
    	      counter--;
    	  }
      }
    
      @Override
      public void onTick(long millisUntilFinished) {
       //timer.setTimer("" + millisUntilFinished / 1000);
    	  int secondsLeft = 0;
    	  if (Math.round((float)millisUntilFinished / 1000.0f) != secondsLeft)
          {  
              secondsLeft = Math.round((float)millisUntilFinished / 1000.0f);
              getTimer().setText("" +secondsLeft );
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
			getExitButton().setVisibility(View.GONE);
			counter = 7;
		       menyeru_countDownTimer.start();
		       timerHasStarted = true;
		       getStartB().setText(textList.stop);
		       getTitleView().setText(textList.calling);
		       getDescription().setText(textList.calling_desc);
		       //forwardButton.setVisibility(View.VISIBLE);
		       getBackwardButton().setVisibility(View.VISIBLE);
		       getPauseButton().setVisibility(View.VISIBLE);
		       getExitButton().setVisibility(View.VISIBLE);
		       getStartB().setVisibility(View.GONE);
		       getRestartButton().setVisibility(View.GONE);
		       getEnglishButton().setVisibility(View.GONE);
		        getIndonesiaButton().setVisibility(View.GONE);
		        getSpanishButton().setVisibility(View.GONE);
		        getKoreanButton().setVisibility(View.GONE);
		        getPortugueseButton().setVisibility(View.GONE);
		        getChineseButton().setVisibility(View.GONE);
		        getChineseTraditionalButton().setVisibility(View.GONE);
		        getGermanButton().setVisibility(View.GONE);
			getTagalogButton().setVisibility(View.GONE);
			getRussianButton().setVisibility(View.GONE);
			getUkrainianButton().setVisibility(View.GONE);
		        //scrollV.setVisibility(View.GONE);
		        getTimer().setVisibility(View.VISIBLE);
		       getPauseButton().setText(textList.pause);
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
		       getStartB().setText(textList.start);
		       getExitButton().setVisibility(View.VISIBLE);
		       getStartB().setVisibility(View.VISIBLE);
		}
	}
	
	public void restartActivity(View v)
	{
		if (!timerHasStarted) {
			getExitButton().setVisibility(View.GONE);
			counter = 7;
		       menyeru_countDownTimer.start();
		       timerHasStarted = true;
		       getStartB().setText(textList.stop);
		       getTitleView().setText(textList.calling);
		       getDescription().setText(textList.calling_desc);
		       //forwardButton.setVisibility(View.VISIBLE);
		       getBackwardButton().setVisibility(View.VISIBLE);
		       getPauseButton().setVisibility(View.VISIBLE);
		       getExitButton().setVisibility(View.VISIBLE);
		       getStartB().setVisibility(View.GONE);
		       getRestartButton().setVisibility(View.GONE);
		       getPauseButton().setText(textList.pause);
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
		currentProgress = Integer.parseInt(getTimer().getText().toString());
		
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
		    getPauseButton().setText(textList.unpause);
		}
		else
		{
			continueTimer = new MyCountDownTimer(currentProgress * 1000, interval);
			continueTimer.start();
			timerHasStarted = true;
			getPauseButton().setText(textList.pause);
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
		getPauseButton().setVisibility(View.VISIBLE);
		getExitButton().setVisibility(View.GONE);
		//forwardButton.setVisibility(View.VISIBLE);
  	    getBackwardButton().setVisibility(View.VISIBLE);
		
		if (counter == 6)
		{
			getTimer().setText(String.valueOf(60));
			getTitleView().setText(textList.praying);
  		  	getDescription().setText(textList.praying_desc);
			continueTimer = new MyCountDownTimer(60 * 1000, interval);
		}
		else if (counter == 5)
		{
			getTimer().setText(String.valueOf(150));
			getTitleView().setText(textList.pray_reading);
  		  	getDescription().setText(textList.pray_reading_desc);
			continueTimer = new MyCountDownTimer(150 * 1000, interval);
		}
		else if (counter == 4)
		{
			getTimer().setText(String.valueOf(60));
			getTitleView().setText(textList.confession);
  		  	getDescription().setText(textList.confession_desc);
			continueTimer = new MyCountDownTimer(60 * 1000, interval);
		}
		else if (counter == 3)
		{
			getTimer().setText(String.valueOf(30));
			getTitleView().setText(textList.consecration);
  		  	getDescription().setText(textList.consecration_desc);
			continueTimer = new MyCountDownTimer(30 * 1000, interval);
		}
		else if (counter == 2)
		{
			getTimer().setText(String.valueOf(30));
			getTitleView().setText(textList.thanksgiving);
  		  	getDescription().setText(textList.thanksgiving_desc);
			continueTimer = new MyCountDownTimer(30 * 1000, interval);
		}
		else if (counter == 1)
		{
			getTimer().setText(String.valueOf(60));
			getTitleView().setText(textList.petition);
  		  	getDescription().setText(textList.petition_desc);
			continueTimer = new MyCountDownTimer(60 * 1000, interval);
		}
		else if (counter == 0)
		{
			getTitleView().setText(textList.the_end);
	  		getDescription().setText("");
	  		getTimer().setText(textList.halelujah);
	  	    getStartB().setText(textList.reset);
	  	    getExitButton().setVisibility(View.VISIBLE);
	  	    getPauseButton().setVisibility(View.GONE);
	  	    getStartB().setVisibility(View.VISIBLE);
	  	    //forwardButton.setVisibility(View.GONE);
	  	    getBackwardButton().setVisibility(View.GONE);
		}
		getPauseButton().setText(textList.unpause);
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
		getPauseButton().setVisibility(View.VISIBLE);
		//forwardButton.setVisibility(View.VISIBLE);
  	    getBackwardButton().setVisibility(View.VISIBLE);
		getExitButton().setVisibility(View.VISIBLE);
		
		if (counter == 7)
		{
			getTimer().setText(String.valueOf(30));
			getTitleView().setText(textList.calling);
  		  	getDescription().setText(textList.calling_desc);
			continueTimer = new MyCountDownTimer(30 * 1000, interval);
		}
		else if (counter == 6)
		{
			getTimer().setText(String.valueOf(60));
			getTitleView().setText(textList.praying);
  		  	getDescription().setText(textList.praying_desc);
			continueTimer = new MyCountDownTimer(60 * 1000, interval);
		}
		else if (counter == 5)
		{
			getTimer().setText(String.valueOf(150));
			getTitleView().setText(textList.pray_reading);
  		  	getDescription().setText(textList.pray_reading_desc);
			continueTimer = new MyCountDownTimer(150 * 1000, interval);
		}
		else if (counter == 4)
		{
			getTimer().setText(String.valueOf(60));
			getTitleView().setText(textList.confession);
  		  	getDescription().setText(textList.confession_desc);
			continueTimer = new MyCountDownTimer(60 * 1000, interval);
		}
		else if (counter == 3)
		{
			getTimer().setText(String.valueOf(30));
			getTitleView().setText(textList.consecration);
  		  	getDescription().setText(textList.consecration_desc);
			continueTimer = new MyCountDownTimer(30 * 1000, interval);
		}
		else if (counter == 2)
		{
			getTimer().setText(String.valueOf(30));
			getTitleView().setText(textList.thanksgiving);
  		  	getDescription().setText(textList.thanksgiving_desc);
			continueTimer = new MyCountDownTimer(30 * 1000, interval);
		}
		else if (counter == 1)
		{
			getTimer().setText(String.valueOf(60));
			getTitleView().setText(textList.petition);
  		  	getDescription().setText(textList.petition_desc);
			continueTimer = new MyCountDownTimer(60 * 1000, interval);
		}
		else if (counter == 0)
		{
			getTitleView().setText(textList.the_end);
	  		getDescription().setText("");
	  		getTimer().setText(textList.halelujah);
	  	    getStartB().setText(textList.reset);
	  	    getExitButton().setVisibility(View.VISIBLE);
	  	    getPauseButton().setVisibility(View.GONE);
	  	    getStartB().setVisibility(View.VISIBLE);
	  	    //forwardButton.setVisibility(View.GONE);
	  	    getBackwardButton().setVisibility(View.GONE);
		}
		getPauseButton().setText(textList.unpause);
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
		//textList.alert_message1 = getResources().getString(R.string.alert_message1);
		//textList.alert_message2 = getResources().getString(R.string.alert_message2);
		//textList.alert_message3 = getResources().getString(R.string.alert_message3);
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
		getExitButton().setVisibility(View.GONE);
		counter = 7;
	       menyeru_countDownTimer.start();
	       timerHasStarted = true;
	       getStartB().setText(textList.stop);
	       getTitleView().setText(textList.calling);
	       getDescription().setText(textList.calling_desc);
	       //forwardButton.setVisibility(View.VISIBLE);
	       getBackwardButton().setVisibility(View.VISIBLE);
	       getPauseButton().setVisibility(View.VISIBLE);
	       getExitButton().setVisibility(View.VISIBLE);
	       getStartB().setVisibility(View.GONE);
	       getRestartButton().setVisibility(View.GONE);
	       getPauseButton().setText(textList.pause);
	       getEnglishButton().setVisibility(View.GONE);
	        getIndonesiaButton().setVisibility(View.GONE);
	        getSpanishButton().setVisibility(View.GONE);
	        getKoreanButton().setVisibility(View.GONE);
	        getPortugueseButton().setVisibility(View.GONE);
	        getChineseButton().setVisibility(View.GONE);
	        getChineseTraditionalButton().setVisibility(View.GONE);
	        getGermanButton().setVisibility(View.GONE);
		getTagalogButton().setVisibility(View.GONE);
		getRussianButton().setVisibility(View.GONE);
		getUkrainianButton().setVisibility(View.GONE);
	        getTimer().setVisibility(View.VISIBLE);
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
