package com.avalutions.dialer.messaging;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageHeader {
    private String name;
    private MessageType type;
    private String client;
    private String procID;
    private String invokeID;
    private int numberOfSegments;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getProcID() {
		return procID;
	}

	public void setProcID(String procID) {
		this.procID = procID;
	}

	public String getInvokeID() {
		return invokeID;
	}

	public void setInvokeID(String invokeID) {
		this.invokeID = invokeID;
	}

	public int getNumberOfSegments() {
		return numberOfSegments;
	}

	public void setNumberOfSegments(int numberOfSegments) {
		this.numberOfSegments = numberOfSegments;
	}

	@Override
    public String toString()
    {
        return String.format("%1$-20s%s%1$-20%1$-6%1$-4%1$-4",
                               name,
                               type.getCode(),
                               client,
                               procID,
                               invokeID,
                               numberOfSegments);
    }

    public static MessageHeader FromString(String headerText)
    {
        MessageHeader result = null;
        final Pattern pattern = Pattern.compile("([A-Za-z\\s]{20})([CDPRN])(?<Client>[A-Za-z\\s]{20})(?<Proc>[\\w\\s]{6})(?<Invoke>[\\w\\s]{4})(?<Seg>[0-9\\s]{4})");
        Matcher match = pattern.matcher(headerText);
        if (match.matches())
        {
            result = new MessageHeader();
            result.setName(match.group(0).trim());
            ///TODO: convert character to type
            //result.setType(MessageType.)(match.group(0));
            result.setClient(match.group(2).trim());
            result.setProcID(match.group(3).trim());
            result.setInvokeID(match.group(4).trim());
            result.setNumberOfSegments(Integer.valueOf(match.group(5).trim()));
        }
        return result;
    }
}
