package com.avalutions.dialer.messaging.commands;

import java.util.ArrayList;
import java.util.List;

import com.avalutions.dialer.messaging.DialerMessage;

public class ListCallLists extends DialerMessage {

    private List<String> callLists;
    
    public List<String> getCallLists() {
    	return callLists;
    }

    @Override
    protected void parse(List<String> segments)
    {
        callLists = new ArrayList<String>(segments);
    }
}
