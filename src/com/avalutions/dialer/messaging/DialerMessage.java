package com.avalutions.dialer.messaging;

import java.util.List;

public class DialerMessage {
    
    private String _message = null;
    private List<String> _segments;

    //MessageHeader Header { get; set; }
    public String getMessage() {
        return _message;
    }
    
    public boolean getIsError() {
        if (_segments != null)
            return _segments.get(1).startsWith("E");
        else
            return false;
    }
    
    public void setIsError(boolean isError) {
    }
    
    protected void parse(List<String> segments) { }

    public static DialerMessage fromRaw(String data)
    {
        MessageHeader header = MessageHeader.FromString(data.Substring(0, 55));
        DialerMessage result = null;
        String[] segments = data.Substring(56).Split((char)MessageSeperator.Delimiter);
        Array.ForEach(segments, a => a = a.Replace(((char)MessageSeperator.Delimiter).ToString(), ""));
        string typeName = "Dialer.Communication.Messages." + header.Name.Replace("AGT", "");
        if (Type.GetType(typeName) != null && header.Type == MessageType.Data)
        {
            ConstructorInfo ci = Type.GetType(typeName).GetConstructor(Type.EmptyTypes);
            result = ci.Invoke(null) as DialerMessage;
        }
        else
            result = new DialerMessage();
        result.Header = header;
        result.Message = data;
        result.Segments = segments.ToList();
        result.Parse(segments.Skip(2).ToList());
        return result;
    }

}
