package com.example.stefan.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.stefan.recipesuggestor.R;

public class CaptureImageActivity extends Activity implements View.OnClickListener{

    private ImageView mImageView;
    private Button mAddToGalleryBtn, mCaptureImgBtn;
    private static final int CAMERA_REQUEST = 1888;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);

        this.initViews();
        this.init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_capture_image, menu);
        return true;
    }

    private void init(){
        mAddToGalleryBtn.setOnClickListener(this);
        mCaptureImgBtn.setOnClickListener(this);
    }

    private void initViews(){
        mImageView = (ImageView)findViewById(R.id.captureImageImgViewId);
        mAddToGalleryBtn = (Button)findViewById(R.id.captureImageAddBtnId);
        mCaptureImgBtn = (Button)findViewById(R.id.captureImageNewImageBtnId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (mAddToGalleryBtn.getId() == view.getId()){
            //TODO implement adding to gallery
        }
        else if (mCaptureImgBtn.getId() == view.getId()){
            //TODO implement new image capture
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent,CAMERA_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST){
            Bitmap img = (Bitmap)data.getExtras().get("data");
            mImageView.setImageBitmap(img);
        }
    }
}
