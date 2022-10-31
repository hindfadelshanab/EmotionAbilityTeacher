package com.nuwa.robot.r2022.emotionalability.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.amrdeveloper.lottiedialog.LottieDialog;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.networking.ClientThread;
import com.nuwa.robot.r2022.emotionalability.networking.TeacherClient;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.PreferenceManager;

import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;

public class MakeConnectionWithRobotActivity extends AppCompatActivity {
    public   String SERVER_IP ;
    private PreferenceManager preferenceManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_connection_with_robot);
        preferenceManager = new PreferenceManager(this);


        IntentIntegrator intentIntegrator = new IntentIntegrator(MakeConnectionWithRobotActivity.this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);
        if (result != null) {
            SERVER_IP = result.getContents();
            preferenceManager.putString(Constants.IPKEY , SERVER_IP);
            WebSocketClient client = null;
            try {

                Log.d("TAG", "onActivityResult:SERVER_IP "+SERVER_IP);
                client = new TeacherClient(new URI("ws://"+SERVER_IP+":8887"));

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            client.connect();
//
//            ClientThread clientThread = ClientThread.getInstance();
//            clientThread.init(SERVER_IP , null);
            showDialog();

        }
    }

    private void showDialog() {

        LottieDialog dialog = new LottieDialog(MakeConnectionWithRobotActivity.this)
                .setAnimation(R.raw.successfully_connected)
                .setAnimationRepeatCount(LottieDialog.INFINITE)
                .setAutoPlayAnimation(true)
                .setMessageColor(Color.BLACK)
                .setMessage("Successfully connected")
                .setDialogBackground(Color.WHITE)
                .setCancelable(false)
                .setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent intent =new Intent(MakeConnectionWithRobotActivity.this , MainActivity.class);
                                intent.putExtra("ip" ,SERVER_IP) ;
                                preferenceManager.putString(Constants.IPKEY,SERVER_IP);
                                startActivity(intent);

                            }
                        } , 2000);
                    }
                })
                ;

        dialog.show();
    }
}