package com.example.photoeditorandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;


public class Select extends AppCompatActivity {
    Uri uri;
    TextView tv,tv1;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        tv = findViewById(R.id.textView3);
        tv1 = findViewById(R.id.textView2);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.expand_in);
        tv1.startAnimation(animation);
        tv.startAnimation(animation);


        requestPermissions();


    }

    private void requestPermissions() {
        // below line is use to request permission in the current activity.
        // this method is use to handle error in runtime permissions
        Dexter.withActivity(this)
                // below line is use to request the number of permissions which are required in our app.
                .withPermissions(
                        // below is the list of permissions
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                // after adding permissions we are calling an with listener method.
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        // this method is called when all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            // do you work now
                            Toast.makeText(Select.this, "All the permissions are granted..", Toast.LENGTH_SHORT).show();
                        }
                        // check for permanent denial of any permission
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permanently, we will show user a dialog message.
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();

                    }
                }).withErrorListener(error -> {
                    // we are displaying a toast message for error message.
                    Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                })
                // below line is use to run the permissions on same thread and to check the permissions
                .onSameThread().check();
    }

    // below is the shoe setting dialog method which is use to display a dialogue message.
    private void showSettingsDialog() {
        // we are displaying an alert dialog for permissions
        AlertDialog.Builder builder = new AlertDialog.Builder(Select.this);

        // below line is the title for our alert dialog.
        builder.setTitle("Need Permissions");
        builder.setCancelable(false);

        // below line is our message for our dialog
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            // this method is called on click on positive button and on clicking shit button
            // we are redirecting our user from our app to the settings page of our app.
            dialog.cancel();
            // below is the intent from which we are redirecting our user.
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, 101);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // this method is called when user click on negative button.
            dialog.cancel();
        });
        // below line is used to display our dialog
        builder.show();
    }

    public void opengallery(View view) {
        ImagePicker.Companion.with(this)
                .crop()
                .galleryOnly()
                .start();
    }

    public void opencamera(View view) {
        ImagePicker.Companion.with(this)
                .crop()
                .cameraOnly()
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            uri = data.getData();

            if (uri!=null) {
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                intent.putExtra("uri", uri.toString());
                startActivity(intent);
            }
        } catch (Exception e) {
            Log.d("Error", "Erorr");
        }

    }
}