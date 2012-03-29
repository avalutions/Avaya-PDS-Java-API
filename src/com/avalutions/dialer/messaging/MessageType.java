package com.avalutions.dialer.messaging;

public enum MessageType {
    Command((char)67),
    Pending((char)80),
    Data((char)68),
    Response((char)82),
    Notification((char)78);
    
    private char _code;
    
    public char getCode() {
        return _code;
    }
    
    private MessageType(char code) {
        _code = code;
    }
}
