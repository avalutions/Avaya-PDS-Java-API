package com.avalutions.dialer.messaging.commands;

import java.util.ArrayList;
import java.util.List;

import com.avalutions.dialer.messaging.DialerMessage;

public class ListScreens extends DialerMessage {

    private List<String> screens;
    
    public ListScreens() {
    	screens = new ArrayList<String>();
    }
    
    public List<String> getScreens() {
    	return screens;
    }

    @Override
    protected void parse(List<String> segments)
    {
        screens = new ArrayList<String>(segments);
    }
}
