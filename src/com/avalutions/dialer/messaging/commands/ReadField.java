package com.avalutions.dialer.messaging.commands;

import java.util.List;

import com.avalutions.dialer.common.DataField;
import com.avalutions.dialer.messaging.DialerMessage;

public class ReadField extends DialerMessage {
    private DataField field;
    
    public DataField getField() {
    	return field;
    }

    @Override
    protected void parse(List<String> segments)
    {
        if (segments.size() > 0)
        {
            String[] paramaters = segments.get(0).split(",");
            field = new DataField(paramaters[0], null, null, Integer.valueOf(paramaters[2]), paramaters[1], paramaters[3]);
        }
    }
}
