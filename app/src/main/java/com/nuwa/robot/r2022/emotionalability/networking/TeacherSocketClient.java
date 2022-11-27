package com.nuwa.robot.r2022.emotionalability.networking;

import com.nuwa.robot.r2022.emotionalability.listener.OnMessageReciveListener;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class TeacherSocketClient extends WebSocketClient {

    public static final int CONNECTED = 1;
    public static final int DISCONNECTED = 2;
    public static final int RECONNECTING = 2;


    public static final String HANDSHAKE_MESSAGE = "HANDSHAKE_MESSAGE";
    private String serverUrl = "ws://localhost:8887";
    private String ID = "Teacher";
    public  int connectionState = 2;
    private int trialLimit = 5;
    private long pingPeriod = 10000; // 10s
    private boolean isConnectionOpened = false;
    private Thread connectionLiveThread;

    static List<IOnMessageListener> onMessageListeners = new ArrayList<>();


    int connectionTries = 0;
    int connectionTriesLimit = 3;
    URI serverURI ;
    private static TeacherSocketClient teacherSocketClient ;

    private static  TeacherSocketClient INSTANCE = null;

    private TeacherSocketClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }
    public  TeacherSocketClient(URI serverURI) {
        super(serverURI);
        this.serverURI = serverURI ;

    }
    public static TeacherSocketClient init(URI serverURI){
        return new TeacherSocketClient(serverURI) ;
    }


    public static TeacherSocketClient getInstance(URI serverUri) {
//        if (teacherSocketClient == null)
            teacherSocketClient = new TeacherSocketClient(serverUri);
        return teacherSocketClient;
    }

    public static void addIOnMessageListener(IOnMessageListener i){
        onMessageListeners.add(i);
    }
    public static void removeIOnMessageListener(IOnMessageListener i){
        onMessageListeners.remove(i);
    }



    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send("Hello, it is me. Mario :)");
        System.out.println("new connection opened");
        send(ID + ":;:" + HANDSHAKE_MESSAGE);

        connectionState = CONNECTED;
        isConnectionOpened = true;
        send("Message");


    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        connectionState = DISCONNECTED;
        if (connectionLiveThread != null) {
            connectionLiveThread.interrupt();
        }
        System.out.println("closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(String message) {
        connectionState = CONNECTED;

        if (message.contains(":;:")) {
            String[] arr = message.split(":;:");
            if (arr[0].equals(ID)) {
                // This is handshake message :)
                connectionState = CONNECTED;
                connectionTries--;
                System.out.println("CONNECTED");

            }
        }
//        onMessageReciveListenerList.forEach(onMessageReciveListener1 ->
//                onMessageReciveListener1.OnMessageRecive(message));

        onMessageListeners.forEach( i -> i.onMessage(message));



        System.out.println("received message: " + message);
        System.out.println("connectionState: " + connectionState);
    }

    @Override
    public void onMessage(ByteBuffer message) {
        onMessageListeners.forEach( i -> i.onMessage(message));

        System.out.println("received ByteBuffer");
    }

    static List<OnMessageReciveListener> onMessageReciveListenerList = new ArrayList<>();

    public static void addOnMessageReciveListener(OnMessageReciveListener onMessageReciveListener) {
        onMessageReciveListenerList.add(onMessageReciveListener);
    }

//    interface onMessageReciveLister {
//        void onMessage(ByteBuffer message);
//    }

    @Override
    public void onError(Exception ex) {
        onMessageListeners.forEach( i -> i.onError(ex));
        System.err.println("an error occurred:" + ex);
    }


    public int startConnection() {
        for (int i = 0; i <= trialLimit; i++) {
//            System.out.println("startConnection = " + connectionState);
            if (connectionState != CONNECTED) {

//                teacherSocketClient.close();


                teacherSocketClient = new TeacherSocketClient(serverURI);
                teacherSocketClient.connect();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
//                startConnectionLiveThread();
                System.out.println("CONNECTED __ __ __ ");
                return CONNECTED;
            }
        }
        if (connectionState == CONNECTED) {
//            startConnectionLiveThread();
            return CONNECTED;
        }
        return DISCONNECTED;
//        throw new TimeoutException();
    }


    private void startConnectionLiveThread() {
        connectionLiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        send(ID + ":;:" + HANDSHAKE_MESSAGE);
                        connectionTries++;

                        Thread.sleep(pingPeriod);
                        System.out.println("connectionTries= " + connectionTries);
                        if (connectionTries >= connectionTriesLimit) {
                            connectionState = DISCONNECTED;
                            System.out.println("DISCONNECTED");
                        } else if (connectionState > 1 && connectionTries < connectionTriesLimit) {
                            connectionState = RECONNECTING;
                            System.out.println("RECONNECTING");
                        }
                    } catch (Exception e) {
                        close();
                        e.printStackTrace();
                    }
                }
            }
        });
        connectionLiveThread.start();

    }

    public interface IOnMessageListener{
        void onMessage(String msg);
        void onMessage(ByteBuffer message);
        void onError(Exception ex) ;
    }


    public static void main(String[] args) throws URISyntaxException {
//
//        EmptyClient emptyClient = new EmptyClient(new URI("ws://localhost:8887"));
//
//            emptyClient.startConnection();

    }


}
