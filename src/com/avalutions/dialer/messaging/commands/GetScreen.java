package com.avalutions.dialer.messaging.commands;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.avalutions.dialer.common.DataField;
import com.avalutions.dialer.common.Field;
import com.avalutions.dialer.common.LabelField;
import com.avalutions.dialer.common.Screen;
import com.avalutions.dialer.messaging.DialerMessage;

public class GetScreen extends DialerMessage {

    private final Pattern fieldRegex = Pattern.compile(
                  "(F)\\,\\s*([0-9]+)\\,\\s*([0-9]+)\\,\\s" +
                  "*([0-9]+)\\,\\s*\\\"(\\w+)\\:([10])" +
                  "\\:([10])\\:([CNDT$])\\:(" +
                  ".*)\\:([01])\\:([01])\\\"");
    private final Pattern labelRegex = Pattern.compile(
                  "(L)\\,\\s*([0-9]+)\\,\\s*([0-9]+)\\,\\s" +
                  "*([0-9]+)\\,\\s*\\\"(.*)\\\"");

    private Screen screen;
    
    public Screen getScreen() {
    	return screen;
    }

    @Override
    protected void parse(List<String> segments)
    {
        screen = new Screen(segments.get(0));

        for (int i = 1; i < segments.size(); i++)
        {
            Field field = BuildField(segments.get(i));
            if (field != null)
                screen.addField(field);
        }
    }

    private Field BuildField(String rawField)
    {
        Matcher fieldMatch = fieldRegex.matcher(rawField);
        if (fieldMatch.matches())
        {
        	DataField field = new DataField(fieldMatch.group(5),
                Integer.valueOf(fieldMatch.group(2)),
                Integer.valueOf(fieldMatch.group(3)),
                Integer.valueOf(fieldMatch.group(4)),
                fieldMatch.group(1));
        	if(fieldMatch.group(9) != null) {
        		for(String value : fieldMatch.group(9).split(", ")) {
        			field.addPossibleValue(value);
        		}
        	}
            return field;
        }
        else
        {
            Matcher labelMatch = labelRegex.matcher(rawField);
            if (labelMatch.matches())
            {
                LabelField field = new LabelField(labelMatch.group(5),
                        Integer.valueOf(fieldMatch.group(2)),
                        Integer.valueOf(fieldMatch.group(3)),
                        Integer.valueOf(fieldMatch.group(4)));
                return field;
            }
        }
        return null;
    }
}
