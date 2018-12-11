//2018 FALL COSC 732 group project. Written by Myungsik kim
//Implement Android client side via emulator
package com.example.administrator.socket_client;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import static android.os.StrictMode.ThreadPolicy;
import static android.os.StrictMode.setThreadPolicy;


public class MainActivity extends AppCompatActivity {

    public static final int serverPort = 40001;
    Socket s = null;
    ImageView imageView = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView1);
        //String pathtoimage= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+ "/Facebook-thumbs-up.jpg";
        String pathtoimage= Environment.getExternalStorageDirectory().getPath()+"/Download/Facebook-thumbs-up.jpg";


        Log.d("image path",pathtoimage);

        ThreadPolicy policy = new ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(policy);

        try{
            s = new Socket("10.0.2.2",serverPort); //for emulator use 10.0.2.2  //for real smartphone use actual ip address.
            Log.d("connect","socket connected.");
        }catch (Exception e){
            Log.d("error01", Log.getStackTraceString(e));
        }

        try{
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            byte[] buffer = (byte[]) ois.readObject();

            String log = new String(buffer);
            Log.d("buffer", log);

            FileOutputStream fos = new FileOutputStream(pathtoimage);
            fos.write(buffer);
            Log.d("fos","image received.");
        }catch (Exception e){
            Log.d("error02", Log.getStackTraceString(e));
        }

        File imgFile = new File(pathtoimage);
        if(imgFile.exists())
        {
            Log.d("image detected!", pathtoimage);
            Bitmap img = BitmapFactory.decodeFile(pathtoimage);
            imageView.setImageBitmap(img);
        }
        else
            Log.d("error03", "No image detected.");
    }
}