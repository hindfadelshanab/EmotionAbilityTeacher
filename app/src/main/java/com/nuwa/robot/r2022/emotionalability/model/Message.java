package com.nuwa.robot.r2022.emotionalability.model;

public class Message {

    private String key ;
    private String value ;
    private int messageId ;

    public Message() {
    }

    public Message( int messageId ,String key, String value ) {
        this.key = key;
        this.value = value;
        this.messageId=messageId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
