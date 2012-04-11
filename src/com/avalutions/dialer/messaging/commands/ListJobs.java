package com.avalutions.dialer.messaging.commands;

import java.util.ArrayList;
import java.util.List;

import com.avalutions.dialer.common.CallType;
import com.avalutions.dialer.common.Job;
import com.avalutions.dialer.common.JobStatus;
import com.avalutions.dialer.messaging.DialerMessage;

public class ListJobs extends DialerMessage {

    private List<Job> jobs;
    
    public ListJobs() {
    	jobs = new ArrayList<Job>();
    }

    @Override
    protected void parse(List<String> segments)
    {
        for (String s : segments)
        {
            String[] parts = s.split(",");
            jobs.add(new Job(parts[1], 
            		CallType.Inbound,   //TODO: Parse enum from character i.e. parts[0].charAt(0) 
            		JobStatus.Active)); //TODO: Parse enum from character i.e. parts[2].charAt(0)
        }
    }
}
