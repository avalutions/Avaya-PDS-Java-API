package com.avalutions.dialer.messaging.commands;

import java.util.ArrayList;
import java.util.List;

import com.avalutions.dialer.common.Disposition;
import com.avalutions.dialer.messaging.DialerMessage;

public class ListKeys extends DialerMessage {
    public List<Disposition> codes;
    
    public ListKeys() {
    	codes = new ArrayList<Disposition>();
    }

    @Override
    protected void parse(List<String> segments)
    {
        for (String s : segments)
        {
            if (s != null && s != "")
            {
                String[] code = s.split(",");
                if (code.length == 3 && code[0] != null && code[1] != null)
                {
                    codes.add(new Disposition(Integer.valueOf(code[0]), code[1], code[2]));
                }
            }
        }
    }
}
