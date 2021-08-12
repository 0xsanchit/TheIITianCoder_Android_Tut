package com.example.youtubevideo;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_DOCUMENT = 1;
    private static final int WRITE_PERMISSION = 2;
    private static final int RESULT_LOAD_IMAGE = 3;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
//                verifyStoragePermissions();
//                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                // Update with mime types
//                intent.setType("application/pdf");
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
//                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                intent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
//                startActivityForResult(intent, RESULT_LOAD_DOCUMENT);
                try {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                        requestWritePermission();
//                        verifyStoragePermissions(getActivity());
                        requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE}, WRITE_PERMISSION);
                        Log.v("RequestingPermission","Requesting");
                    } else {
                        Intent i = new Intent(
                                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
//                        images.add(picturePath);
//                        adapter.notifyDataSetChanged();
                        cursor.close();
                    }
                } else if (data.getData() != null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String path = cursor.getString(columnIndex);
//                    images.add(picturePath);
//                    adapter.notifyDataSetChanged();
                    cursor.close();
                }
            }
        }
    }

    //    public void verifyStoragePermissions() {
//        // Check if we have write permission
//        int permission = ActivityCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
//
//        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.R)
//        {
//            if(Environment.isExternalStorageManager())
//            {
//
//            }
//            else
//            {
//                try {
//                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
//                    intent.addCategory("android.intent.category.DEFAULT");
//                    intent.setData(Uri.parse(String.format("package:%s",getPackageName())));
//                    startActivityForResult(intent,100);
//                } catch (Exception e) {
//                    Intent intent = new Intent();
//                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                    startActivityForResult(intent,100);
//                }
//            }
//        }
////        if (permission != PackageManager.PERMISSION_GRANTED) {
////            // We don't have permission so prompt the user
////            ActivityCompat.requestPermissions(
////                    activity,
////                    PERMISSIONS_STORAGE,
////                    REQUEST_EXTERNAL_STORAGE
////            );
////        }
////        else
////        {
////            Log.v("Permission","Has Permission");
////        }
//    }

}