package knockknock.delivr_it.knocknock.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.managers.UserInformationStorageManager;
import knockknock.delivr_it.knocknock.tasks.RetrieveMasterAppId;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        final ImageView imageView = (ImageView) findViewById(R.id.splash_image);
        final Matrix matrix = imageView.getImageMatrix();
        final float imageWidth = imageView.getDrawable().getIntrinsicWidth();
        final int screenWidth = getResources().getDisplayMetrics().widthPixels;
        final float scaleRatio = screenWidth / imageWidth;
        matrix.postScale(scaleRatio, scaleRatio);
        imageView.setImageMatrix(matrix);

        new RetrieveMasterAppId(this).execute();

    }


    public void startNextActivity() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UserInformationStorageManager.getUserInformation(getBaseContext()) == null) {
                    Intent intent = new Intent(SplashActivity.this, RegistrationActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 2000);


    }
}
