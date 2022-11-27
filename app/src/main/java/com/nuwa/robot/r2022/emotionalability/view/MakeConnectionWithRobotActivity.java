package com.nuwa.robot.r2022.emotionalability.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.amrdeveloper.lottiedialog.LottieDialog;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.conn.util.InetAddressUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.networking.ClientThread;
import com.nuwa.robot.r2022.emotionalability.networking.TeacherClient;
import com.nuwa.robot.r2022.emotionalability.networking.TeacherSocketClient;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.PreferenceManager;

import org.java_websocket.client.WebSocketClient;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

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
            TeacherSocketClient client = null;
            try {
                client = new TeacherSocketClient(new URI("ws://"+SERVER_IP+":8887"));
                client.connect();


            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            String myIp = getMyIPAddress().substring(0,getMyIPAddress().lastIndexOf("."));
            String serverIp = SERVER_IP.substring(0,SERVER_IP.lastIndexOf("."));
            if (myIp.equals(serverIp)){
                showDialog();
            }else {
                Toast.makeText(this, "Please make sure that the device is connected to the same network as the robot app", Toast.LENGTH_LONG).show();
                IntentIntegrator intentIntegrator = new IntentIntegrator(MakeConnectionWithRobotActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();
            }


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

    public static String getMyIPAddress() {//P.S there might be better way to get your IP address (NetworkInfo) could do it.
        String myIP = null;
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (isIPv4)
                            myIP = sAddr;
                    }
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
        return myIP;
    }
}