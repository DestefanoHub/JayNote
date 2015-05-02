package com.csit551.appinventors.jaynote.Activities;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.csit551.appinventors.jaynote.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AudioControl extends LinearLayout {

    public static final int INITIATE_RECORDING = 1, RECORDING = 2, STOPPED_RECORDING = 3, INITIATE_PLAYING = 4, PLAYING = 5, STOPPED_PLAYING = 6;

    private ImageButton audioRecord;
    private ImageButton audioPlay;
    private String FilePath;
    private int currentState = 0;

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    public AudioControl(Context ctxt, AttributeSet attr)
    {
        super(ctxt, attr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ((Activity)getContext()).getLayoutInflater().inflate(R.layout.audio_control, this);
        audioRecord = (ImageButton) findViewById(R.id.audio_record);
        audioPlay = (ImageButton) findViewById(R.id.audio_play);
    }

    public void setOnClickListenerRecord(View.OnClickListener clkListen)
    {
        audioRecord.setOnClickListener(clkListen);
    }

    public void setOnClickListenerPlay(View.OnClickListener clkListen)
    {
        audioPlay.setOnClickListener(clkListen);
    }

    public void setFilePath(String strPath)
    {
        if (strPath == null || strPath.equals(""))
            return;
        FilePath = strPath;
    }

    public String getFilePath()
    {
        return FilePath;
    }

    public int getCurrentState()
    {
        return currentState;
    }

    public void startAction(int state)
    {
        currentState = state;
        switch(currentState) {
            case INITIATE_RECORDING:
                audioRecord.setImageResource(R.drawable.record);
                audioRecord.setVisibility(View.VISIBLE);
                audioPlay.setVisibility(View.INVISIBLE);
                break;
            case RECORDING:
                audioRecord.setImageResource(R.drawable.stop);
                audioPlay.setVisibility(View.INVISIBLE);
                startRecordingAudio();
                break;
            case STOPPED_RECORDING:
                stopRecordingAudio();
                audioRecord.setImageResource(R.drawable.record);
                audioPlay.setVisibility(View.VISIBLE);
                currentState = INITIATE_RECORDING;
                break;
            case INITIATE_PLAYING:
                audioPlay.setImageResource(R.drawable.play);
                audioRecord.setVisibility(View.INVISIBLE);
                if(getFilePath() == null)
                    audioPlay.setVisibility(View.INVISIBLE);
                else
                    audioPlay.setVisibility(View.VISIBLE);
                break;
            case PLAYING:
                if(getFilePath() == null)
                    break;
                startPlayingAudio();
                audioPlay.setImageResource(R.drawable.stop);
                audioRecord.setVisibility(View.INVISIBLE);
                break;
            case STOPPED_PLAYING:
                stopPlayingAudio();
                audioPlay.setImageResource(R.drawable.play);
                audioPlay.setVisibility(View.VISIBLE);
                currentState = INITIATE_PLAYING;
                break;
            default:
                Log.v("State", "current state is invalid...");
                break;
        }
    }

    public void startRecordingAudio()
    {
        if (mediaRecorder != null)
            mediaRecorder.release();
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        String strFileName = getFileName();
        mediaRecorder.setOutputFile(strFileName);

        try {
            mediaRecorder.prepare();
            Log.d("MyCameraApp -> MainActivity:", "prepare() successful");
            mediaRecorder.start();
        } catch (IOException e) {
            Log.e("MyCameraApp -> MainActivity:", "prepare() or start() failed");
            e.printStackTrace();
        }

        setFilePath(strFileName);
        Log.d("MyCameraApp -> MainActivity:", "Handle for audio recording start");
    }

    private String getFileName() {
        // Make a unique file name based on date and time
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String strFileName = timeStamp + ".3gp";
        String strFilePath1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS).getAbsolutePath() + "/" + strFileName;
        return strFilePath1;
    }

    private void stopRecordingAudio()
    {
        if (mediaRecorder == null)
            return;
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        Log.d("MyCameraApp -> MainActivity:", "Handle audio recording stop");
    }

    private void startPlayingAudio()
    {
        if (getFilePath() == null || getFilePath().equals(""))
            return;
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    startAction(STOPPED_PLAYING);
                }
            });
            mediaPlayer.setDataSource(getFilePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (IOException e) {
            Log.e("MyCameraApp -> MainActivity:", "Media player failed");
            e.printStackTrace();
        }
        Log.d("MyCameraApp -> MainActivity:", "Handled media player");
    }

    private void stopPlayingAudio()
    {
        if ( mediaPlayer == null)
            return;
        mediaPlayer.stop();
        mediaPlayer = null;
        Log.d("MyCameraApp -> MainActivity:", "Stopped media player");
    }

}