package com.avalutions.dialer.messaging.commands;

import java.util.ArrayList;
import java.util.List;

import com.avalutions.dialer.common.DataField;
import com.avalutions.dialer.common.Field;
import com.avalutions.dialer.messaging.DialerMessage;

public class ListCallFields extends DialerMessage {

    private List<Field> fields;
    
    public List<Field> getFields() {
    	return fields;
    }
    
    public ListCallFields() {
    	fields = new ArrayList<Field>();
    }

    @Override
    protected void parse(List<String> segments)
    {
        while (segments.size() > 0)
        {
            String[] raw = segments.get(0).split(",");
            DataField field = new DataField(raw[0], null, null, Integer.parseInt(raw[1]), null);
            fields.add(field);
        }
    }
}
