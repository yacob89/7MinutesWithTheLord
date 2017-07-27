package com.gerejadibandung.minuteswiththelord;

import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrayActivity extends AppCompatActivity {

    @BindView(R.id.startButton)
    Button startButton;
    @BindView(R.id.exitButton)
    Button exitButton;
    @BindView(R.id.buttonPause)
    Button pauseButton;
    @BindView(R.id.buttonRestart)
    Button restartButton;
    @BindView(R.id.timer)
    TextView timer;
    @BindView(R.id.buttonBackward)
    ImageButton backwardButton;
    @BindView(R.id.titlePray)
    TextView titlePray;
    @BindView(R.id.prayDescription)
    TextView description;

    private final long interval = 1000;

    //Menyeru nama Tuhan
    private final long menyeru_startTime = 30;
    // Berdoa
    private final long berdoa_startTime = 60;
    // Doa-baca
    private final long doabaca_startTime = 150;
    // Mengaku Dosa
    private final long mengakuDosa_startTime = 60;
    // Konsikrasi
    private final long konsikrasi_startTime = 30;
    // Mengucap Syukur
    private final long ucapSyukur_startTime = 30;
    // Doa Permohonan
    private final long doaPermohonan_startTime = 60;

    private CountDownTimer continueTimer;
    private int counter = 7;
    private int currentProgress;
    public TextList textList;
    private boolean timerHasStarted = false;
    private boolean fromPause = false;

    private Ringtone r;
    private AudioManager myAudioManager;
    private Uri path;
    private MediaPlayer media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pray);
        ButterKnife.bind(this);
        // Keep The screen on during activity
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        textListInit();
        initializeUI();

        path = Uri.parse("android.resource://com.gerejadibandung.minuteswiththelord/" + R.raw.sirius);
        r = RingtoneManager.getRingtone(getApplicationContext(), path);
    }

    @Override
    protected void onResume() {
        super.onResume();
        media = new MediaPlayer();
        media.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            media.setDataSource(getApplicationContext(), path);
            media.prepare();
        } catch (IOException | IllegalArgumentException | SecurityException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        fromPause = true;
        pauseTime();
        media.release();
        media = null;
    }

    private void initializeUI() {
        counter = 7;
        timerHasStarted = false;
        fromPause = false;
        restartButton.setVisibility(View.GONE);
        backwardButton.setVisibility(View.GONE);
        exitButton.setVisibility(View.GONE);
        pauseButton.setVisibility(View.GONE);
        setNewTimeByCurrentProgress();
        setTextByCurrentProgress();
    }

    private void setTextByCurrentProgress() {
        if (counter == 7) {
            timer.setText(String.valueOf(menyeru_startTime));
            titlePray.setText(textList.calling);
            description.setText(textList.calling_desc);
            backwardButton.setVisibility(View.GONE);
        } else if (counter == 6) {
            timer.setText(String.valueOf(berdoa_startTime));
            titlePray.setText(textList.praying);
            description.setText(textList.praying_desc);
            backwardButton.setVisibility(View.VISIBLE);
        } else if (counter == 5) {
            timer.setText(String.valueOf(doabaca_startTime));
            titlePray.setText(textList.pray_reading);
            description.setText(textList.pray_reading_desc);
            backwardButton.setVisibility(View.VISIBLE);
        } else if (counter == 4) {
            timer.setText(String.valueOf(mengakuDosa_startTime));
            titlePray.setText(textList.confession);
            description.setText(textList.confession_desc);
            backwardButton.setVisibility(View.VISIBLE);
        } else if (counter == 3) {
            timer.setText(String.valueOf(konsikrasi_startTime));
            titlePray.setText(textList.consecration);
            description.setText(textList.consecration_desc);
            backwardButton.setVisibility(View.VISIBLE);
        } else if (counter == 2) {
            timer.setText(String.valueOf(ucapSyukur_startTime));
            titlePray.setText(textList.thanksgiving);
            description.setText(textList.thanksgiving_desc);
            backwardButton.setVisibility(View.VISIBLE);
        } else if (counter == 1) {
            timer.setText(String.valueOf(doaPermohonan_startTime));
            titlePray.setText(textList.petition);
            description.setText(textList.petition_desc);
            backwardButton.setVisibility(View.VISIBLE);
        } else if (counter == 0) {
            titlePray.setText(textList.the_end);
            description.setText("");
            timer.setText(textList.halelujah);
            restartButton.setText(textList.reset);
            pauseButton.setVisibility(View.GONE);
            restartButton.setVisibility(View.VISIBLE);
            backwardButton.setVisibility(View.GONE);
        }
    }

    private void setNewTimeByCurrentProgress() {
        if (counter == 7) {
            continueTimer = new MyCountDownTimer(menyeru_startTime * interval, interval);
        } else if (counter == 6) {
            continueTimer = new MyCountDownTimer(berdoa_startTime * interval, interval);
        } else if (counter == 5) {
            continueTimer = new MyCountDownTimer(doabaca_startTime * interval, interval);
        } else if (counter == 4) {
            continueTimer = new MyCountDownTimer(mengakuDosa_startTime * interval, interval);
        } else if (counter == 3) {
            continueTimer = new MyCountDownTimer(konsikrasi_startTime * interval, interval);
        } else if (counter == 2) {
            continueTimer = new MyCountDownTimer(ucapSyukur_startTime * interval, interval);
        } else if (counter == 1) {
            continueTimer = new MyCountDownTimer(doaPermohonan_startTime * interval, interval);
        }
    }

    private void pauseTime() {
        currentProgress = Integer.parseInt(timer.getText().toString());
        if (timerHasStarted) {
            if (continueTimer != null) {
                continueTimer.cancel();
            }
            timerHasStarted = false;
            pauseButton.setText(textList.unpause);
        } else {
            if (!fromPause) {
                continueTime();
            }
        }
    }

    private void start() {
        startButton.setVisibility(View.GONE);
        pauseButton.setVisibility(View.VISIBLE);
        exitButton.setVisibility(View.VISIBLE);
        if (continueTimer != null) {
            timerHasStarted = true;
            continueTimer.start();
        }
    }

    @OnClick(R.id.startButton)
    void startButtonOnClick() {
        start();
    }

    @OnClick(R.id.buttonPause)
    void pauseTimer() {
        fromPause = false;
        pauseTime();
    }

    @OnClick(R.id.buttonBackward)
    void backwardTimer() {
        if (counter < 7) {
            counter++;
        }
        if (continueTimer != null) {
            continueTimer.cancel();
        }
        timerHasStarted = false;
        setTextByCurrentProgress();
        setNewTimeByCurrentProgress();
        pauseButton.setText(textList.unpause);
    }

    @OnClick(R.id.buttonRestart)
    void restartAll() {
        initializeUI();
    }

    private void nextTime() {
        if (counter >= 1) {
            if (continueTimer != null) {
                timerHasStarted = true;
                continueTimer.start();
            }
        }
    }

    private void continueTime() {
        currentProgress = Integer.parseInt(timer.getText().toString());
        continueTimer = new MyCountDownTimer(currentProgress * 1000, interval);
        continueTimer.start();
        timerHasStarted = true;
        pauseButton.setText(textList.pause);
    }

    public void textListInit() {
        textList = new TextList();
        textList.start = getResources().getString(R.string.start);
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
        textList.number_7 = getResources().getString(R.string.number_7);
        textList.number_65 = getResources().getString(R.string.number_65);
        textList.number_55 = getResources().getString(R.string.number_55);
        textList.number_3 = getResources().getString(R.string.number_3);
        textList.number_2 = getResources().getString(R.string.number_2);
        textList.number_15 = getResources().getString(R.string.number_15);
        textList.number_1 = getResources().getString(R.string.number_1);
    }

    private void exitByPhase() {
        if (counter == 7) {
            showExitAlert(textList.number_7);
        } else if (counter == 6) {
            showExitAlert(textList.number_65);
        } else if (counter == 5) {
            showExitAlert(textList.number_55);
        } else if (counter == 4) {
            showExitAlert(textList.number_3);
        } else if (counter == 3) {
            showExitAlert(textList.number_2);
        } else if (counter == 2) {
            showExitAlert(textList.number_15);
        } else if (counter == 1) {
            showExitAlert(textList.number_1);
        } else {
            if (continueTimer != null) {
                continueTimer.cancel();
            }
            finish();
            moveTaskToBack(true);
        }
    }

    @Override
    public void onBackPressed() {
        exitByPhase();
    }

    public void showExitAlert(String menit) {
        pauseTime();
        Typeface bold = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-B.ttf");
        Typeface normal = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-R.ttf");
        new MaterialDialog.Builder(this)
                .content(R.string.alert, menit)
                .positiveText(R.string.unpause)
                .negativeText(R.string.force_exit)
                .contentColorRes(R.color.black)
                .onPositive((dialog, which) -> continueTime())
                .onNegative((dialog, which) -> {
                    if (continueTimer != null) {
                        continueTimer.cancel();
                    }
                    finish();
                })
                .canceledOnTouchOutside(false)
                .positiveColorRes(R.color.green)
                .negativeColorRes(R.color.red)
                .typeface(bold, normal)
                .dismissListener(dialog -> continueTime())
                .show();
    }

    @OnClick(R.id.exitButton)
    void exitByBackKey() {
        exitByPhase();
    }

    private class MyCountDownTimer extends CountDownTimer {

        MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int secondsLeft = 0;
            if (Math.round((float) millisUntilFinished / 1000.0f) != secondsLeft) {
                secondsLeft = Math.round((float) millisUntilFinished / 1000.0f);
                timer.setText(String.valueOf(secondsLeft));
            }
        }

        @Override
        public void onFinish() {
            timerHasStarted = false;
            myAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            if (myAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
                r.play();
            } else {
                if (myAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) > 0) {
                    media.start();
                } else {
                    Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                }
            }
            counter--;
            setNewTimeByCurrentProgress();
            setTextByCurrentProgress();
            nextTime();
        }
    }
}
