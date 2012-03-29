package com.avalutions.dialer.messaging;

public interface IMessage {
    
    boolean getIsError();
    void setIsError(boolean isError);
    String getMessage();
    void setMessage(String message);

}
