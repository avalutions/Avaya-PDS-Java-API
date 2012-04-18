package com.avalutions.dialer.messaging;

import java.util.*;


public class DialerConnection {
	private DialerSocket socket;
	private List<MessageHandler> handlers = new ArrayList<MessageHandler>();

    private boolean handleError(DialerMessage response)
    {
        List<String> segments = new ArrayList<String>(response.getSegments());
        String[] details = segments.get(1).split(",");
        String[] data = Arrays.copyOf(details, details.length);

//        XElement message =
//            (from m in _messagesDocument.Descendants("message")
//             where (string)m.Attribute("code") == details[0]
//             select m).FirstOrDefault();
//
//        if (message != null)
//        {
//            response.Message = message.Descendants("source").Where(d => (string)d.Attribute("name") == response.Header.Name).Select(v => v.Value).FirstOrDefault();
//            if (string.IsNullOrEmpty(response.Message))
//                response.Message = message.Descendants("source").Where(d => (string)d.Attribute("name") == "default").Select(v => v.Value).FirstOrDefault();
//        }

        return false;
    }
    

    /// <summary>
    /// A notification was received from the dialer so handle which kind.
    /// </summary>
    /// <param name="response">The structured data received from the dialer</param>
    /// <returns>Whether the notification was handled or not</returns>
    private boolean handleNotification(DialerMessage response)
    {
        boolean handled = true;

        //The agent hung up the headset without logging off
        if (response.getHeader().getName() == "AGTHeadsetConnBroken")
        {
            //Bubble the message to the presentation layer
            onNewMessage("NOTICE: Headset connection broke.  Logoff process beginning.", false);
            //Start the logoff process
            //Agent.MoveToState(AgentState.LoggedOff, true);
        }
        //An information message was received from the supervisor.  This is not a system message delivery mechanism
        else if (response.getHeader().getName() == "AGTReceiveMessage")
        {
            //Bubble the message to the presentation layer
        	onNewMessage("NOTICE: New message from supervisor to follow.", false);
            //Pull out the actual message received
        	onNewMessage(response.Segments.ToList()[1], false);
        }
        //The job we are currently attached to is ending.  Begin detach process
        else if (response.getHeader().getName() == "AGTJobEnd")
        {
            //Bubble the message to the presentation layer
        	onNewMessage("NOTICE: The current job has ended.", false);
            //Start the detach process
            //Agent.MoveToState(AgentState.LoggedOn, true);
        }
        //System error received.  This is usually detramental
        else if (response.getHeader().getName() == "AGTSystemError")
        {
            //Pull out the message the dialer is sending
            String message = response.getSegments().get(1);

            //Bubble the message to the user
            onNewMessage("NOTICE: System error reported. Message to follow.", false);
            onNewMessage(message, true);

            //Check to see if it was a command that failed.
            Command pendingCommand = null;
            lock (((ICollection)_waitingCommands).SyncRoot)
                pendingCommand = _waitingCommands.FirstOrDefault(c => message.Contains(c.Header.Name));
            //If so, end the command
            if (pendingCommand != null)
                pendingCommand.Finished.Set();
        }
        else if (response.Header.Name == "AGTAutoReleaseLine")
        {
            OnNewMessage("The line has been disconnected.", false);
            Agent.MoveToState(AgentState.Disconnected);
        }
        //A new call was received in the format expected
        else if (response.Header.Name == "AGTCallNotify")
        {
            OnNewMessage(response);
            if (response.Segments[1] == "M00000")
                OnNewMessage("NOTICE: Call received", false);
        }
        //We dont have a method for handling the notification so indicate that
        else
            handled = false;

        //Return if we handled the notification or not
        return handled;
    }
	
	void messageReceived(String message) {
		DialerMessage response = DialerMessage.fromRaw(message);

        boolean handled = handleError(response);

        //If we were able to parse the response and we have enough data to pass around, process the response
        if (response != null && response.getSegments().size() >= 2)
        {
            //If it's a notification, handle the notification
            if (response.getHeader().getType() == MessageType.Notification)
                HandleNotification(response);
            //If it's a response to a command sent, handle it
            else if (response.Header.Type == Messages.MessageType.Response)
            {
                Command command = null;
                lock (((ICollection)_waitingCommands).SyncRoot)
                {
                    //If we have commands waiting, and one of them is the command we received a response for...
                    if (_waitingCommands.Count > 0 && _waitingCommands.Count(h => h.Header.Name == response.Header.Name) > 0)
                    {
                        //Set our local variable with the found command
                        command = _waitingCommands.FirstOrDefault(h => h.Header.Name == response.Header.Name);
                        //Remove it from the pending queue since we have received a response for it
                        _waitingCommands.Remove(command);
                    }
                }

                //If we have found a command to finish...
                if (command != null)
                {
                    //If we have nothing set for the response (usually a data message if its a command to do so
                    if (command.Response == null)
                    {
                        DialerMessage newResponse = null;
                        if (_socket._messageQueue != null)
                        {
                            //Check to see if we missed a data message (since, technically, we could receive a response before our data because we're using asychronous invocation
                            List<DialerMessage> missedResponses = new List<DialerMessage>();
                            _socket._messageQueue.ToList().Where(m => m.Contains(command.Header.Name)).ToList().ForEach(r => missedResponses.Add(DialerMessage.FromRaw(r)));
                            //If there is a data message found, set it as our response
                            newResponse = missedResponses.FirstOrDefault(r => r.Header.Type == Messages.MessageType.Data);
                        }

                        //If we didn't find a data message to set as our response....
                        if (newResponse == null)
                            //Just set it to our response message
                            command.Response = response;
                        //If we did find a data message...
                        else
                            //Set it as our response5
                            command.Response = newResponse;
                    }

                    //If the response is an error and it's been handled (populated our message), kick off a new status
                    if (response.IsError && handled)
                        OnNewMessage(response.Message, true);

                    //If the command has overridden the FinishedText, send that as a message
                    if (!string.IsNullOrEmpty(command.FinishedText))
                        OnNewMessage(command.FinishedText, false);

                    //Inidicate our command is finished
                    command.Finished.Set();
                }
            }
            //If we are dealing with a data message
            else if (response.Header.Type == Messages.MessageType.Data)
            {
                //Find the command it belongs to
                Command command = null;
                lock (((ICollection)_waitingCommands).SyncRoot)
                    command = _waitingCommands.FirstOrDefault(h => h.Header.Name == response.Header.Name);

                //And set the response to the data message
                if (command != null)
                    command.Response = response;
            }
            //If we are dealing with a pending message
            else if (response.Header.Type == Messages.MessageType.Pending)
            {
                //Find the command it belongs to
                Command command = null;
                lock (((ICollection)_waitingCommands).SyncRoot)
                    command = _waitingCommands.FirstOrDefault(h => h.Header.Name == response.Header.Name);

                //And if the command has overriden the PendingText, kick off a new status
                if (command != null && !string.IsNullOrEmpty(command.PendingText))
                    OnNewMessage(command.PendingText, false);
            }
            //Otherwise, if it's not been handled, and we can determine it's an error
            else if (response.IsError)
                //Kick off a new status with the raw message
                OnNewMessage(response);
        }
	}
}
