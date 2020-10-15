package com.saifi.shoppurchase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.saifi.shoppurchase.constants.SessonManager;
import com.saifi.shoppurchase.updateChecker.GooglePlayStoreAppVersionNameLoader;
import com.saifi.shoppurchase.updateChecker.WSCallerVersionListener;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class MainActivity extends AppCompatActivity implements WSCallerVersionListener {


    Button shopButton, managerButton, returnButton;
    RelativeLayout mainLaout;
    long back_pressed = 0;
    SessonManager sessonManager;
    TextView txtLogout;
    boolean isForceUpdate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        sessonManager = new SessonManager(getBaseContext());

        new GooglePlayStoreAppVersionNameLoader(getApplicationContext(), this).execute();
        askForPermissioncamera(Manifest.permission.CAMERA, CAMERA);


        managerButton = findViewById(R.id.managerButton);
        shopButton = findViewById(R.id.shopButton);
        returnButton = findViewById(R.id.returnButton);
        txtLogout = findViewById(R.id.txtLogout);
        mainLaout = findViewById(R.id.mainLaout);
        if (sessonManager.getUserType().equalsIgnoreCase("Manager")) {
            managerButton.setVisibility(View.VISIBLE);
        } else {
            managerButton.setVisibility(View.GONE);
        }

        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShopActivity.class));
            }
        });

        managerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ManagerActivity.class));
            }
        });

        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessonManager.setToken("");
                Toast.makeText(MainActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ReturnActivity.class));
            }
        });
    }

   @Override
    public void onGetResponse(boolean isUpdateAvailable) {
        if (isUpdateAvailable) {
            showUpdateDialog();
        }
    }
    // popup for location

    public void showUpdateDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        alertDialogBuilder.setTitle(MainActivity.this.getString(R.string.app_name));
        alertDialogBuilder.setMessage(MainActivity.this.getString(R.string.update_message));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(R.string.update_now, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                dialog.cancel();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isForceUpdate) {
                    finish();
                }
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }

    @Override
    public void onBackPressed() {

        if (back_pressed + 2000 > System.currentTimeMillis())
            super.onBackPressed();
        else {
            Snackbar snackbar = Snackbar.make(mainLaout, "Double Tap to Exit!", Snackbar.LENGTH_SHORT);
            View view = snackbar.getView();
            view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            snackbar.show();
            back_pressed = System.currentTimeMillis();
        }
    }

    private void askForPermissioncamera(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        } else {
//            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }


    }

}
