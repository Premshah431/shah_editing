package com.example.photoeditorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;

public class EditActivity extends AppCompatActivity {
    String uri;
    ImageView imageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        uri = getIntent().getStringExtra("uri");
        imageView = findViewById(R.id.img);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.expand_in);
        imageView.startAnimation(animation);

        Intent dsPhotoEditorIntent = new Intent(this, DsPhotoEditorActivity.class);
        dsPhotoEditorIntent.setData(Uri.parse(uri));
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "Photo Editor");
        int[] toolsToHide = {DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_CROP};

        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide);
        startActivityForResult(dsPhotoEditorIntent, 200);
    }
}