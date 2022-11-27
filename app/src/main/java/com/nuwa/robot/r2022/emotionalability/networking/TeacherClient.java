package com.nuwa.robot.r2022.emotionalability.networking;

import com.nuwa.robot.r2022.emotionalability.listener.OnMessageReciveListener;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;

public class TeacherClient extends WebSocketClient {

    private OnMessageReciveListener onMessageReciveListener;
    public TeacherClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }


    public TeacherClient(URI serverURI) {
        super(serverURI);
    }
    public TeacherClient(URI serverURI ,OnMessageReciveListener onMessageReciveListener) {
        super(serverURI);
        this.onMessageReciveListener =onMessageReciveListener;
    }

    public TeacherClient(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }


    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send("Hello, it is me. Mario :)");
        System.out.println("opened connection");
        // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
    }

    @Override
    public void onMessage(String message) {
        System.out.println("received: " + message);

        onMessageReciveListener.OnMessageRecive(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // The close codes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println(
                "Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: "
                        + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
        // if the error is fatal then onClose will be called additionally
    }

//    public static void main(String[] args) throws URISyntaxException {
//        ExampleClient c = new ExampleClient(new URI(
//                "ws://localhost:8887")); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
//        c.connect();
//    }

}