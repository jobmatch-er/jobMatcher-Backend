package de.wecodeit.jobmatcher.registry;

import de.jakobniklas.util.Exceptions;
import de.jakobniklas.util.FormatUtil;
import de.wecodeit.jobmatcher.Request;

import java.util.ArrayList;
import java.util.List;

public class RequestRegistry
{
    private List<Request> registeredRequests = new ArrayList<>();

    public void register(Request request)
    {
        if(!registeredRequests.contains(request))
        {
            registeredRequests.add(request);
        }
        else
        {
            Exceptions.handle("Request already registered");
        }
    }

    public List<Request> getRegisteredRequests()
    {
        return registeredRequests;
    }

    public String handle(String request)
    {
        String[] inputedRequestSplit = request.split(" ");

        List<String> args = new ArrayList<>();

        for(Request thisRequest : registeredRequests)
        {
            String[] thisRequestSplit = thisRequest.getRequest().split(" ");

            if(inputedRequestSplit.length == inputedRequestSplit.length)
            {
                boolean reguestFullyParsed = true;

                for(int i = 0; i < inputedRequestSplit.length; i++)
                {
                    if(inputedRequestSplit[i].equalsIgnoreCase(thisRequestSplit[i]))
                    {
                        continue;
                    }
                    else if(inputedRequestSplit[i].startsWith("#int"))
                    {
                        if(FormatUtil.isNumeric(inputedRequestSplit[i]))
                        {
                            args.add(inputedRequestSplit[i]);

                            continue;
                        }
                        else
                        {
                            return "Required argument at '" + i + "' has to be a number! -> " + thisRequest.getRequest();
                        }
                    }
                    else if(inputedRequestSplit[i].startsWith("#string"))
                    {
                        if(FormatUtil.isText(inputedRequestSplit[i]))
                        {
                            args.add(inputedRequestSplit[i]);

                            continue;
                        }
                        else
                        {
                            return "Required argument at '" + i + "' has to be text! -> " + thisRequest.getRequest();
                        }
                    }
                    else if(inputedRequestSplit[i].startsWith("#boolean"))
                    {
                        if(FormatUtil.isBoolean(inputedRequestSplit[i]))
                        {
                            args.add(inputedRequestSplit[i]);

                            continue;
                        }
                        else
                        {
                            return "Required argument at '" + i + "' has to be true or false! -> " + thisRequest.getRequest();
                        }
                    }
                    else
                    {
                        reguestFullyParsed = false;

                        break;
                    }
                }

                if(reguestFullyParsed)
                {
                    return thisRequest.respond(args);
                }
            }
        }

        return "Request not found";
    }
}
