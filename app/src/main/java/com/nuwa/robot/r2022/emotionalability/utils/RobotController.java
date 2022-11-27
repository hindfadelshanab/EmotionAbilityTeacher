package com.nuwa.robot.r2022.emotionalability.utils;

import android.content.Context;
import android.util.Log;

import com.nuwa.robot.r2022.emotionalability.networking.ClientThread;
import com.nuwa.robot.r2022.emotionalability.networking.TeacherClient;
import com.nuwa.robot.r2022.emotionalability.networking.TeacherSocketClient;

import java.net.URI;
import java.net.URISyntaxException;

public class RobotController {
    private PreferenceManager preferenceManager;

    private Context context;
    private String serverIp;

    TeacherSocketClient teacherClient;

    public RobotController(Context context) {
        this.context = context;
        preferenceManager = new PreferenceManager(context);
        serverIp = preferenceManager.getString(Constants.IPKEY);

        if (serverIp != null) {
            try {
                Log.d("TAG", "RobotController: " + serverIp);
                teacherClient = new TeacherSocketClient(new URI("ws://" + serverIp + ":8887"));
                try {
                    teacherClient.connectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            } catch (URISyntaxException e) {
                e.printStackTrace();
            }


        }
    }


    public void sendMessageForRobot(String message) {
        Log.d("TAG", "sendMessageForRobot: ");
        if (teacherClient.isOpen()) {

            teacherClient.send(message);
            Log.d("TAG", "sendMessageForRobot message: " + message);
            Log.d("TAG", "sendMessageForRobot serverIp: " + serverIp);
        } else {


        }
    }


    public void sendMessageForStudent(String message) {
        Log.d("TAG", "sendMessageForRobot: ");
        message = "{\"messageForStudentKey\":" + message + "}";
        teacherClient.send(message);
        Log.d("TAG", "sendMessageForRobot message: " + message);
        Log.d("TAG", "sendMessageForRobot serverIp: " + serverIp);

    }


}
