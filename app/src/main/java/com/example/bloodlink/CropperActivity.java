package com.example.bloodlink;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.UUID;

public class CropperActivity extends AppCompatActivity {

    String result;
    Uri fileUri;

    ImageView imageView;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropper);

        readIntent();

        String desti_uri = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();

        UCrop.Options options = new UCrop.Options();
        options.setCircleDimmedLayer(true);

        UCrop.of(fileUri,Uri.fromFile(new File(getCacheDir(),desti_uri)))
                .withOptions(options)
                .withAspectRatio(0,0)
                .useSourceImageAspectRatio()
                .withMaxResultSize(2000,2000)
                .start(CropperActivity.this);

        Log.d("crop", "onCreate: is Working, below the UCrop Setting");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("crop", "Inside the onActivityResult");

        if (resultCode==RESULT_OK  && requestCode==UCrop.REQUEST_CROP){

            final  Uri resultUri = UCrop.getOutput(data);
            Intent returnIntent = new Intent();

            returnIntent.putExtra("RESULT",resultUri + "");
            setResult(-1,returnIntent);
            finish();


        }
        else if (resultCode==UCrop.RESULT_ERROR){
            final Throwable croperror = UCrop.getError(data);


        }
        else {
            Intent intent=new Intent(CropperActivity.this, UserDetailActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void readIntent() {

        Intent intent = getIntent();
        if(intent.getExtras()!=null){

            result = intent.getStringExtra("DATA");
            fileUri = Uri.parse(result);

        }

}
}