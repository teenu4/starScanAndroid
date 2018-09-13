package com.sevenkapps.moviesearchandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
//import android.util.Base64;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sevenkapps.moviesearchandroid.util.Constants;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends Activity {

    Button btpic, btnup, btnsel;
    private Uri fileUri;
    String picturePath;
    Uri selectedImage;
    Bitmap photo;
    String ba1;
    ImageView imageView;
    public static String URL = "http://10.0.2.2:3000/api/images.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        btpic = (Button) findViewById(R.id.bShoot);
        btpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickpic();
            }
        });

        btnsel = (Button) findViewById(R.id.bSelect);
        btnsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select();
            }
        });


        btnup = (Button) findViewById(R.id.bUpload);
        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
    }

    private void upload() {
        // Image location URL
//        Log.e("path", "----------------" + picturePath);
//
//        // Image
//        Bitmap bm = BitmapFactory.decodeFile(picturePath);
//        ByteArrayOutputStream bao = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.JPEG, 90, bao);
//        byte[] ba = bao.toByteArray();
//        ba1 =  Base64.encodeToString(ba, Base64.DEFAULT);



        Log.e("base64", "-----" + ba1);

        // Upload image to server
        new uploadToServer().execute();

    }
    //HCBk9MPt

    private void clickpic() {
        // Check Camera
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // Open default camera
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture Intent
            startActivityForResult(intent, 0);

        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }

    private void select() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
    }

    //    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 100 && resultCode == RESULT_OK) {
//
//            selectedImage = data.getData();
//            photo = (Bitmap) data.getExtras().get("data");
//
//            // Cursor to get image uri to display
//
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            picturePath = cursor.getString(columnIndex);
//            Log.e("path", "----------------" + picturePath);
//            cursor.close();
//
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            ImageView imageView = (ImageView) findViewById(R.id.imageView);
//            imageView.setImageBitmap(photo);
//        }
//    }
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = imageReturnedIntent.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(imageUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    ba1 = Constants.encodeImage(selectedImage);
                    //Log.d("imageuri",selectedImage.toString());
                    imageView.setImageURI(imageUri);
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = imageReturnedIntent.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(imageUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    ba1 = Constants.encodeImage(selectedImage);
                    //Log.d("imageuri",selectedImage.toString());
                    imageView.setImageURI(imageUri);
                }
                break;
        }
    }

    public class uploadToServer extends AsyncTask<Void, Void, String> {

        private ProgressDialog pd = new ProgressDialog(MainActivity.this);

        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Wait image uploading!");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("base64", ba1));
            nameValuePairs.add(new BasicNameValuePair("ImageName", System.currentTimeMillis() + ".jpg"));
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                String st = EntityUtils.toString(response.getEntity());
                Log.v("log_tag", "In the try Loop" + st);

            } catch (Exception e) {
                Log.v("log_tag", "Error in http connection " + e.toString());
            }
            return "Success";

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pd.hide();
            pd.dismiss();
        }
    }
}