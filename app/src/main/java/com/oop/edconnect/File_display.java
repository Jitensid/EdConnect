package com.oop.edconnect;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.List;

public class File_display extends AppCompatActivity {

    private boolean check_your_file;

    private String filename;
    private String fileUrl;
    private File path;

    private TextView name_of_file, downloadfile;
    private Button openfile;

    private long downloadRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_display);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        filename = extras.getString("Name");
        fileUrl = extras.getString("DownloadUrl");

        name_of_file = findViewById(R.id.name_of_file);

        downloadfile = findViewById(R.id.download_url);
        openfile = findViewById(R.id.open_file);

        name_of_file.setText(filename);

        openfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check_your_file = isCheck_your_file();

                if(check_your_file  == false)
                    download(filename, fileUrl);

                else
                    open_existing(filename, fileUrl);

            }
        });

        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        
        }

    @Override
    protected void onDestroy() {            //This code is for removing the receivers after the Activity is closed
        super.onDestroy();

        unregisterReceiver(onDownloadComplete);

    }

    public void download(String filename, String fileUrl){

            Uri fileUri = Uri.parse(fileUrl);   //get file uri from url

            DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);

            DownloadManager.Request request = new DownloadManager.Request(fileUri);

            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);

            request.setAllowedOverRoaming(false);

            request.setTitle(filename);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, filename);
            }

            downloadRef = downloadManager.enqueue(request);
        }


        private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                long downlloadid = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                if(downlloadid == downloadRef){

                    Intent myintent = new Intent(Intent.ACTION_VIEW);

                    File myfile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename);

                    Uri fileuri = FileProvider.getUriForFile(getApplicationContext(),BuildConfig.APPLICATION_ID+".provider",myfile);

                    grantUriPermission(getApplicationContext().getPackageName(),fileuri,Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    String mimeType = getContentResolver().getType(fileuri);

                    myintent.setDataAndType(fileuri, mimeType);

                    myintent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


                    if(check_for_app(myintent))
                        getApplicationContext().startActivity(myintent);

                }
            }
        };

        public boolean isCheck_your_file(){

            File myfile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename);

            if(myfile.exists()){
                return true;
            }

            return false;
        }


        public void open_existing(String filename, String fileUrl){

            File myfile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),filename);

            Intent myintent = new Intent(Intent.ACTION_VIEW);

            Uri fileuri = FileProvider.getUriForFile(getApplicationContext(),BuildConfig.APPLICATION_ID+".provider",myfile);

            grantUriPermission(getApplicationContext().getPackageName(),fileuri,Intent.FLAG_GRANT_READ_URI_PERMISSION);

            String mimeType = getContentResolver().getType(fileuri);

            myintent.setDataAndType(fileuri, mimeType);

            myintent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            if(check_for_app(myintent))
                getApplicationContext().startActivity(myintent);

        }

        public boolean check_for_app(Intent myintent){

            List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(myintent, 0);

            if(resolveInfoList.size()>0)
                return true;

            else {
                Toast.makeText(getApplicationContext(),"No App Found to open this file",Toast.LENGTH_SHORT).show();
                return false;
            }
        }

}