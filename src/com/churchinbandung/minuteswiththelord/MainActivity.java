/*
 * Creator: yacob
 * March 29 2014
 * File name: MainActivity.java
 * Project: 7MinutesWithTheLord
 * Public repository: https://github.com/yacob89/7MinutesWithTheLord
 */

package com.churchinbandung.minuteswiththelord;

import android.app.Activity;
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
import com.churchinbandung.minuteswiththelord.R;

public class MainActivity extends Activity implements OnClickListener {
	
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
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
        Uri path = Uri.parse("android.resource://com.churchinbandung.minuteswiththelord/"+R.raw.sirius);
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
    		  title.setText("Berdoa");
    		  description.setText("Membuka hati kita, melembutkan hati kita, mengosongkan diri kita. 'Tuhan, aku cinta pada-Mu'");
    		  berdoa_countDownTimer.start();
    		  counter--;
    	  }
    	  else if (counter == 6)
    	  {
    		  r.play();
    		  title.setText("Doa Baca");
    		  description.setText("Dengan roh mendoakan 1-2 ayat. Mengubah ayat2 itu menjadi doa pribadi kita.");
    		  doabaca_countDownTimer.start();
    		  counter--;
    	  }
    	  else if (counter == 5)
    	  {
    		  r.play();
    		  title.setText("Mengaku Dosa");
    		  description.setText("Mengakui dosa2 & pelanggaran2  kita.  Mohon pengampunan & pembasuhan.");
    		  mengakuDosa_countDownTimer.start();
    		  counter--;
    	  }
    	  else if (counter == 4)
    	  {
    		  r.play();
    		  title.setText("Konsikrasi");
    		  description.setText("Mempersembahkan diri kepada Tuhan dengan segar.");
    		  konsikrasi_countDownTimer.start();
    		  counter--;
    	  }
    	  else if (counter == 3)
    	  {
    		  r.play();
    		  title.setText("Mengucap Syukur");
    		  description.setText("Mengucap syukur atas segala sesuatu dan memuji Dia.");
    		  ucapSyukur_countDownTimer.start();
    		  counter--;
    	  }
    	  else if (counter == 2)
    	  {
    		  r.play();
    		  title.setText("Doa Permohonan");
    		  description.setText("Meminta kepada Tuhan untuk keperluan2  kita, pertumbuhan, orang2 yang perlu keselamatan.");
    		  doaPermohonan_countDownTimer.start();
    		  counter--;
    	  }
    	  else if (counter == 1)
    	  {
    		  exitButton.setVisibility(View.VISIBLE);
    		  r.play();
    		  title.setText("Selesai");
    		  description.setText("");
    		  text.setText("Haleluya!");
    	      startB.setText("Ulangi");
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
		       startB.setText("Berhenti");
		       title.setText("Menyeru Nama Tuhan");
		       description.setText("Berseru kepada nama Tuhan untuk mengarahkan pikiran kita kepada roh");
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
		       startB.setText("Ulangi");
		       exitButton.setVisibility(View.VISIBLE);
		       startB.setVisibility(View.VISIBLE);
		      }
	}
	
	public void exitApp(View v)
	{
		finish();          
        moveTaskToBack(true);
	}
    
}
