package com.avalutions.dialer.messaging;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DialerMessage implements Message {
    
    private String message = null;
    private List<String> segments;
    private MessageHeader header;
    
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getSegments() {
		return segments;
	}

	public void setSegments(List<String> segments) {
		this.segments = segments;
	}

	public MessageHeader getHeader() {
		return header;
	}

	public void setHeader(MessageHeader header) {
		this.header = header;
	}

	public boolean isError() {
        if (segments != null)
            return segments.get(1).startsWith("E");
        else
            return false;
    }
    
    protected void parse(List<String> segments) { }

    public static DialerMessage fromRaw(String data)
    {
        MessageHeader header = MessageHeader.FromString(data.substring(0, 55));
        DialerMessage result = null;
        String[] segments = data.substring(56).split(String.valueOf(MessageSeperator.Delimiter.getCharacter()));
        for(String segment : segments) {
        	segment = segment.replace(String.valueOf(MessageSeperator.Delimiter.getCharacter()), "");
        }
        
        String typeName = "com.avalutions.dialer.messaging." + header.getName().replace("AGT", "");
        Class c = null;
		try {
			c = Class.forName(typeName);
	        if (c != null && header.getType() == MessageType.Data)
	        {
	        	return (DialerMessage) c.newInstance();
	        }
        else
            result = new DialerMessage();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        result.setHeader(header);
        result.setMessage(data);
        List<String> sl = Arrays.asList(segments);
        result.setSegments(sl);
        result.parse(sl);
        return result;
    }

}
