package com.example.fpvdrone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class DisplayActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 101;
    private static final int STORAGE_PERMISSION_CODE2 = 102;
    private SwitchCompat mSwitchAudio;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        //audio switch
        mSwitchAudio = findViewById(R.id.switch_audio);
        if (mSwitchAudio.isChecked()) {
            AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            manager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
        } else {
            AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            manager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
        }

        //screen shot button
        Button button = findViewById(R.id.button_ss);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                checkPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        STORAGE_PERMISSION_CODE);
                checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        STORAGE_PERMISSION_CODE2);
                screenshot();
            }
        });


        Button btnSaveMediaPage = findViewById(R.id.button);
        btnSaveMediaPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             nextPage();
            }
        });

    }

    public void nextPage(){
        Intent intent = new Intent(this, SaveMedia.class);
        startActivity(intent);
    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this,
                    new String[] { permission },
                    requestCode);
        }
    }


    public Bitmap getBitmapFromView(View view)
    {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void screenshot()
    {
        Date date = new Date();
        CharSequence now = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
        String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() +  "/" + now + ".jpg";

        View root = getWindow().getDecorView();
        Bitmap bitmap = getBitmapFromView(root);

        File imageFile = new File(path);
        try {
            FileOutputStream stream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

