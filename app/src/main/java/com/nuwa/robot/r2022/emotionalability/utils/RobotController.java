package com.nuwa.robot.r2022.emotionalability.utils;

import android.content.Context;
import android.util.Log;

import com.nuwa.robot.r2022.emotionalability.networking.ClientThread;
import com.nuwa.robot.r2022.emotionalability.networking.TeacherClient;

import java.net.URI;
import java.net.URISyntaxException;

public class RobotController {
//    private ClientThread clientThread ;
    private PreferenceManager preferenceManager;

   private   Context context;
   private  String serverIp;

   TeacherClient teacherClient ;

    public RobotController(Context context) {
        this.context =context;
        preferenceManager = new PreferenceManager(context);
        serverIp = preferenceManager.getString(Constants.IPKEY);

        if (serverIp != null) {
            try {
                Log.d("TAG", "RobotController: " +serverIp);
                teacherClient = new TeacherClient(new URI("ws://"+serverIp+":8887"));


            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            try {
                teacherClient.connectBlocking();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            clientThread = ClientThread.getInstance();
//            clientThread.init(serverIp, null);
//
        }
    }


    public void  sendMessageForRobot(String message){
        Log.d("TAG", "sendMessageForRobot: ");
        if (teacherClient .isOpen()){
            message = "{\"messageKey\":" + message + "}";
            teacherClient.send(message);
            Log.d("TAG", "sendMessageForRobot message: " +message);
            Log.d("TAG", "sendMessageForRobot serverIp: "+serverIp);
        }
    }


    public void  sendMessageForStudent(String message){
        Log.d("TAG", "sendMessageForRobot: ");
        if (teacherClient .isOpen()){
            message = "{\"messageForStudentKey\":" + message + "}";
            teacherClient.send(message);
            Log.d("TAG", "sendMessageForRobot message: " +message);
            Log.d("TAG", "sendMessageForRobot serverIp: "+serverIp);
        }
    }
//    public void sendMessage(String message){
//        Log.d("TAG", "sendMessageToRobot serverIp: "+serverIp);
//        if (message!=null) {
//            clientThread.sendMessage( message);
//        }
//
//    }
//    public void sendMessageToRobot(String message){
//        Log.d("TAG", "sendMessageToRobot serverIp: "+serverIp);
//        if (message!=null) {
//            clientThread.sendMessageJson(Constants.MESSAGEKEY, message);
//        }
//
//    }
//    public void sendMessageToStudent(String message){
//        Log.d("TAG", "sendMessageToRobot serverIp: "+serverIp);
//        if (message!=null) {
//            clientThread.sendMessageJson(Constants.MESSAGE_FOR_STUDENT_KEY, message);
//        }
//
//    }

}
