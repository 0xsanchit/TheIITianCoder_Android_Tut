package com.example.youtubevideo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.utils.ObjectUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CloudinaryActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int WRITE_PERMISSION = 2;
    ImageView imageView;
    Cloudinary cloudinary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloudinary);

        imageView = findViewById(R.id.cloudinary_id);

        Map config = new HashMap();
        config.put("cloud_name", "dzaqpdimp");
        MediaManager.init(getApplicationContext(), config);
//        cloudinary = new Cloudinary(ObjectUtils.asMap(
//                "cloud_name", "dzaqpdimp",
//                "api_key", "715749631725189",
//                "api_secret", "AE1RCc_K7kYr8idyUANnR9URbrk"));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                        requestWritePermission();
//                        verifyStoragePermissions(getActivity());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE}, WRITE_PERMISSION);
                        }
                        Log.v("RequestingPermission","Requesting");
                    } else {
                        Intent i = new Intent(
                                Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        i.setType("image/*");
                        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
//            if(requestCode==RESULT_LOAD_DOCUMENT)
//            {
//                if(data!=null)
//                {
//                    Uri uri = data.getData();
//                    MediaManager.get()upload(uri).callback(new UploadCallback()).dispatch();
//                }
//            }
            if(requestCode==RESULT_LOAD_IMAGE)
            {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for (int i = 0; i < count; i++) {
                        Uri selectedImage = data.getClipData().getItemAt(i).getUri();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);
                        String requestId = MediaManager.get().upload(path)
                                .unsigned("f2cr7ywe")
                                .option("resource_type", "image")
                                .option("folder", "/NewFolder/Images")
                                .option("public_id", UUID.randomUUID().toString())
                                .callback(new UploadCallback() {
                                    @Override
                                    public void onStart(String requestId) {

                                    }

                                    @Override
                                    public void onProgress(String requestId, long bytes, long totalBytes) {

                                    }

                                    @Override
                                    public void onSuccess(String requestId, Map resultData) {
                                        Log.v("Uploaded Image","Done");
                                    }

                                    @Override
                                    public void onError(String requestId, ErrorInfo error) {

                                    }

                                    @Override
                                    public void onReschedule(String requestId, ErrorInfo error) {

                                    }
                                }).dispatch();
//                        images.add(picturePath);
//                        adapter.notifyDataSetChanged();
                        cursor.close();
                    }
                } else if (data.getData() != null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String path = cursor.getString(columnIndex);
//                    try {
//                        cloudinary.uploader().upload(new File(path),
//                                ObjectUtils.asMap("public_id", "olympic_flag"));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                    String requestId = MediaManager.get().upload(selectedImage)
                            .unsigned("f2cr7ywe")
                            .option("resource_type", "image")
                            .option("folder", "/NewFolder/Images")
                            .option("public_id", UUID.randomUUID().toString())
                            .callback(new UploadCallback() {
                                @Override
                                public void onStart(String requestId) {

                                }

                                @Override
                                public void onProgress(String requestId, long bytes, long totalBytes) {

                                }

                                @Override
                                public void onSuccess(String requestId, Map resultData) {
                                    Log.v("Uploaded Image","Done");
                                }

                                @Override
                                public void onError(String requestId, ErrorInfo error) {

                                }

                                @Override
                                public void onReschedule(String requestId, ErrorInfo error) {

                                }
                            }).dispatch();
//                    images.add(picturePath);
//                    adapter.notifyDataSetChanged();
                    cursor.close();
                }
            }
        }
    }
}