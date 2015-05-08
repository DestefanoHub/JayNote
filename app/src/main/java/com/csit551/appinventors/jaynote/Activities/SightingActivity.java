package com.csit551.appinventors.jaynote.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.csit551.appinventors.jaynote.Database.DatabaseManager;
import com.csit551.appinventors.jaynote.Database.SightingsModel;
import com.csit551.appinventors.jaynote.R;

import java.io.File;
import java.util.Calendar;
import java.util.Date;


public class SightingActivity extends Activity
{
    private static final int REQUEST_CAMERA_GET = 0;
    private static final int REQUEST_IMAGE_GET = 1;
    private static final int REQUEST_AUDIO_GET = 2;
    private DatabaseManager db;
    private Context context;
    private SightingsModel sighting;
    private Intent intent;
    private Button save;
    private Button edit;
    private Button delete;
    private EditText sightingName;
    private EditText sightingSize;
    private EditText sightingType;
    private EditText sightingColor;
    private EditText sightingDateTime;
    private EditText sightingLocation;
    private EditText sightingMisc;
    private ImageButton sightingPhoto;
    private String sightingPhotoPath;
    private ImageButton sightingPhotoChoose;
    private ImageButton sightingPhotoView;
    private AudioControl sightingAudioControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sighting);

        db = new DatabaseManager(SightingActivity.this.getApplicationContext());
        context = this.getBaseContext();

        sightingName = (EditText) findViewById(R.id.sighting_title_input);
        sightingSize = (EditText) findViewById(R.id.size_animal_input);
        sightingType = (EditText) findViewById(R.id.type_organism_input);
        sightingColor = (EditText) findViewById(R.id.color_input);
        sightingDateTime = (EditText) findViewById(R.id.time_of_day);
        sightingLocation = (EditText) findViewById(R.id.location_input);
        sightingMisc = (EditText) findViewById(R.id.misc_input);
        save = (Button) findViewById(R.id.save_button);
        delete = (Button) findViewById(R.id.delete_button);
        edit = (Button) findViewById(R.id.edit_button);
        sightingPhoto = (ImageButton) findViewById(R.id.organism_photo_Button);
        sightingAudioControl = (AudioControl) findViewById(R.id.audioControl);
        sightingPhotoChoose = (ImageButton) findViewById(R.id.organism_photo_choose_Button);
        sightingPhotoView = (ImageButton) findViewById(R.id.organism_photo_View_Button);

        //Listener for ImageButton click to take a picture
        sightingPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent of existing camera app is used
                Intent inCam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(inCam, REQUEST_CAMERA_GET);
            }
        });

        //Listener for ImageButton click to choose a picture from the memory
        sightingPhotoChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent for opening the Gallery folder of pictures
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Select file"), REQUEST_IMAGE_GET);
            }
        });

        sightingAudioControl.setOnClickListenerRecord(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAudioControl(v);
            }
        });
        sightingAudioControl.setOnClickListenerPlay(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAudioControl(v);
            }
        });

        sightingAudioControl.setOnClickListenerFileChooser(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                handleAudioControl(v);
            }
        });

        intent = this.getIntent();
        if(intent.hasExtra("create_view_edit"))
        {
            int val = intent.getIntExtra("create_view_edit", -1);
            if(val == 0)
            {
                newSighting();
            }
            else if(val == 1)
            {
                int id = intent.getIntExtra("sighting_id", -1);
                if(id > 0)
                {
                    sighting = db.getSightingById(id);
                    viewSighting();
                }
            }
            else if(val == 2)
            {
                int id = intent.getIntExtra("sighting_id", -1);
                if(id > 0)
                {
                    sighting = db.getSightingById(id);
                    editSighting();
                }
            }
        }
    }

    public void newSighting()
    {
        edit.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        sightingDateTime.setText(now.toString());
        //Audio buttons enabled for recording or choosing a file
        sightingAudioControl.setCurrentSetup(AudioControl.SETUP_FOR_CREATING);
        sightingAudioControl.startAction(AudioControl.INITIATE_RECORDING);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!sightingName.getText().toString().equals("")) {
                    Animation alphanim = AnimationUtils.loadAnimation(context, R.anim.alph);
                    v.startAnimation(alphanim);
                    String name = sightingName.getText().toString();
                    String size = sightingSize.getText().toString();
                    String type = sightingType.getText().toString();
                    String color = sightingColor.getText().toString();
                    String dateTime = sightingDateTime.getText().toString();
                    String location = sightingLocation.getText().toString();
                    String misc = sightingMisc.getText().toString();
                    String audioFile = sightingAudioControl.getFilePath();
                    db.insertSighting(name, size, type, color, dateTime, audioFile, sightingPhotoPath, location, misc);
                    Toast toast = Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT);
                    toast.show();
                    setResult(RESULT_OK);
                    finish();
                }
                else {
                    Toast toast = Toast.makeText(context, "Please provide a name for the sighting!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    public void viewSighting()
    {
        save.setVisibility(View.GONE);
        //delete.setVisibility(View.GONE);
        sightingName.setText(sighting.getName());
        sightingSize.setText(sighting.getSize());
        sightingType.setText(sighting.getType());
        sightingColor.setText(sighting.getColor());
        sightingDateTime.setText(sighting.getDateTime());
        sightingLocation.setText(sighting.getLocation());
        sightingMisc.setText(sighting.getMisc());
        sightingPhotoPath = sighting.getImage();
        //Some of the buttons for picture are hidden
        sightingPhoto.setVisibility(View.INVISIBLE);
        sightingPhotoChoose.setVisibility(View.INVISIBLE);
        //Set the photo in view
        if (sightingPhotoPath != null) {
            Bitmap bp = getBitmapFromFile(sightingPhotoPath);
            if (bp != null) {
                sightingPhotoView.setImageBitmap(bp);
                sightingPhotoView.setVisibility(View.VISIBLE);
            }
        }
        //Set the file path of Audio
        sightingAudioControl.setFilePath(sighting.getAudio());
        //Audio buttons enabled for playing audio
        sightingAudioControl.setCurrentSetup(AudioControl.SETUP_FOR_VIEWING);
        sightingAudioControl.startAction(AudioControl.INITIATE_PLAYING);

        //make the edittexts uneditable
        sightingName.setEnabled(false);
        sightingName.setFocusable(false);
        sightingSize.setEnabled(false);
        sightingSize.setFocusable(false);
        sightingType.setEnabled(false);
        sightingType.setFocusable(false);
        sightingColor.setEnabled(false);
        sightingColor.setFocusable(false);
        sightingDateTime.setEnabled(false);
        sightingDateTime.setFocusable(false);
        sightingLocation.setEnabled(false);
        sightingLocation.setFocusable(false);
        sightingMisc.setEnabled(false);
        sightingMisc.setFocusable(false);
        sightingPhoto.setEnabled(false);
        sightingPhoto.setFocusable(false);
        sightingPhotoChoose.setEnabled(false);
        sightingPhotoChoose.setFocusable(false);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent newIntent = new Intent(context, SightingActivity.class);
                newIntent.putExtra("create_view_edit", 2);
                newIntent.putExtra("sighting_id", sighting.getId());
                startActivity(newIntent);
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Animation alphanim = AnimationUtils.loadAnimation(context, R.anim.alph);
                v.startAnimation(alphanim);
                db.deleteSighting(sighting);
                Toast toast = Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });
    }

    public void editSighting()
    {
        edit.setVisibility(View.GONE);
        sightingName.setText(sighting.getName());
        sightingSize.setText(sighting.getSize());
        sightingType.setText(sighting.getType());
        sightingColor.setText(sighting.getColor());
        sightingDateTime.setText(sighting.getDateTime());
        sightingLocation.setText(sighting.getLocation());
        sightingMisc.setText(sighting.getMisc());
        sightingPhotoPath = sighting.getImage();
        //Set the photo in view
        if (sightingPhotoPath != null) {
            Bitmap bp = getBitmapFromFile(sightingPhotoPath);
            if (bp != null) {
                sightingPhotoView.setImageBitmap(bp);
                sightingPhotoView.setVisibility(View.VISIBLE);
            }
        }
        //Set the file path of Audio
        sightingAudioControl.setFilePath(sighting.getAudio());
        //Audio buttons enabled for playing audio
        sightingAudioControl.setCurrentSetup(AudioControl.SETUP_FOR_EDITING);
        sightingAudioControl.startAction(AudioControl.INITIATE_RECORDING);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!sightingName.getText().toString().equals("")) {
                    Animation alphanim = AnimationUtils.loadAnimation(context, R.anim.alph);
                    v.startAnimation(alphanim);
                    sighting.setName(sightingName.getText().toString());
                    sighting.setSize(sightingSize.getText().toString());
                    sighting.setType(sightingType.getText().toString());
                    sighting.setColor(sightingColor.getText().toString());
                    sighting.setDateTime(sightingDateTime.getText().toString());
                    sighting.setLocation(sightingLocation.getText().toString());
                    sighting.setMisc(sightingMisc.getText().toString());
                    sighting.setImage(sightingPhotoPath);
                    sighting.setAudio(sightingAudioControl.getFilePath());
                    db.updateSighting(sighting);
                    Toast toast = Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent newIntent = new Intent(context, MainActivity.class);
                    startActivity(newIntent);
                    finish();
                }
                else {
                    Toast toast = Toast.makeText(context, "Please provide a name for the sighting!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                db.deleteSighting(sighting);
                Toast toast = Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });
    }

    //Below method will be triggered when user closed picture app by clicking save or discard
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_AUDIO_GET)
        {
            if (resultCode == RESULT_OK) {
                Uri audUri = data.getData();
                sightingAudioControl.setFilePath(getFilePathFromUri(audUri));
                sightingAudioControl.startAction(AudioControl.INITIATE_PLAYING);
            }
            else if(resultCode == RESULT_CANCELED) {
                Toast toast = Toast.makeText(context, "Audio is not chosen", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else if (requestCode == REQUEST_IMAGE_GET || requestCode == REQUEST_CAMERA_GET) {
            if (resultCode == RESULT_OK) {
                Uri imgUri = data.getData();
                sightingPhotoPath = getFilePathFromUri(imgUri);

                Bundle retBundle = data.getExtras();
                Bitmap bp = (Bitmap) retBundle.get("data");
                if (bp == null)
                    bp = getBitmapFromFile(sightingPhotoPath);
                sightingPhotoView.setImageBitmap(bp);
                sightingPhotoView.setVisibility(View.VISIBLE);
            } else if (resultCode == RESULT_CANCELED) {
                String strMsg = "";
                if (requestCode == 0)
                    strMsg = "Photo is not saved";
                else if (requestCode == 1)
                    strMsg = "Photo is not choosen";
                Toast toast = Toast.makeText(context, strMsg, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    //This method helps to get the path of the photo taken
    private String getFilePathFromUri(Uri u)
    {
        String strFilePath;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(u, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        strFilePath = cursor.getString(columnIndex);
        cursor.close();
        return strFilePath;
    }

    //This method is to get the bitmap of a given image file path
    private Bitmap getBitmapFromFile(String FileName)
    {
        Bitmap bMap = null;
        File imgFile = new  File(FileName);
        if(imgFile.exists()){
            //To Scale the size of image to fit in the image button
            int bitmapWidth = 100;
            int bitmapHeight = 100;
            // Get the dimensions of the image
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imgFile.getAbsolutePath(), bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Scale down factor
            int scaleFactor = Math.min(photoW/bitmapWidth, photoH/bitmapHeight);
            // Decode the image file
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;

            bMap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), bmOptions);
        }
        return bMap;
    }

    //This function handles the click of any audio control button based on the user actions
    private void handleAudioControl(View v)
    {
        int curState = sightingAudioControl.getCurrentState();
        if (v.getId() == R.id.audio_choose){
            // Intent for opening the audio folder of choosing
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "Select file"), REQUEST_AUDIO_GET);
        }
        else if (v.getId() == R.id.audio_record) {
            //step 2
            if (curState == AudioControl.START_RECORDING)
                sightingAudioControl.startAction(AudioControl.STOP_RECORDING);
                //step 1
            else //if (curState == AudioControl.INITIATE_RECORDING)
                sightingAudioControl.startAction(AudioControl.START_RECORDING);
        }
        else if (v.getId() == R.id.audio_play) {
            //step 2
            if (curState == AudioControl.START_PLAYING)
                sightingAudioControl.startAction(AudioControl.STOP_PLAYING);
                //step 1
            else //if (curState == AudioControl.INITIATE_PLAYING)
                sightingAudioControl.startAction(AudioControl.START_PLAYING);
        }
    }

    @Override
    public void finish()
    {
        //This function is overridden to close any audio recording or playing
        if (sightingAudioControl.getCurrentState() == AudioControl.START_PLAYING)
            sightingAudioControl.startAction(AudioControl.STOP_PLAYING);
        else if (sightingAudioControl.getCurrentState() == AudioControl.START_RECORDING) {
            sightingAudioControl.startAction(AudioControl.STOP_RECORDING);
            Toast tt = Toast.makeText(context, "Audio recording is stopped and saved", Toast.LENGTH_SHORT);
            tt.show();
        }
        super.finish();
    }
}