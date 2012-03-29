package com.avalutions.dialer.messaging;

public class MessageHeader {
    private String _name;
    public MessageType _type;
    public String _client;
    public String _procID;
    public String _invokeID;
    public int _numberOfSegments;

    @Override
    public String toString()
    {
        return String.Format("%1$#20s%s%120}{3,-6}{4,-4}{5,-4}",
                               _name,
                               _type.getCode(),
                               Client,
                               ProcID,
                               InvokeID,
                               NumberOfSegments);
    }

    public static MessageHeader FromString(String headerText)
    {
        MessageHeader result = null;
        Match match = Regex.Match(headerText, @"(?<Name>[A-Za-z\s]{20})(?<Type>[CDPRN])(?<Client>[A-Za-z\s]{20})(?<Proc>[\w\s]{6})(?<Invoke>[\w\s]{4})(?<Seg>[0-9\s]{4})");
        if (match.Success)
        {
            result = new MessageHeader();
            if (match.Groups["Name"].Success)
                result.Name = match.Groups["Name"].Value.Trim();
            if (match.Groups["Type"].Success)
                result.Type = (MessageType)((int)match.Groups["Type"].Value[0]);
            if (match.Groups["Client"].Success)
                result.Client = match.Groups["Client"].Value.Trim();
            if (match.Groups["Proc"].Success)
                result.ProcID = match.Groups["Proc"].Value.Trim();
            if (match.Groups["Invoke"].Success)
                result.InvokeID = match.Groups["Invoke"].Value.Trim();
            if (match.Groups["Seg"].Success)
                result.NumberOfSegments = int.Parse(match.Groups["Seg"].Value.Trim());
        }
        return result;
    }
}
