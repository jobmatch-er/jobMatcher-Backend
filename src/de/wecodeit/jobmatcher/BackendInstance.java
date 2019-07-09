package de.wecodeit.jobmatcher;

import de.wecodeit.jobmatcher.registry.RequestRegistry;

import java.util.List;

public class BackendInstance extends Thread
{
    private RequestRegistry requestRegistry;
    private APIConnection apiConnection;
    private Database user;

    public BackendInstance()
    {
        this.requestRegistry = new RequestRegistry();
        this.apiConnection = new APIConnection(requestRegistry, 54345);

        requestRegistry.register(new Request()
        {
            @Override
            public String getRequest()
            {
                return "login ";
            }

            @Override
            public String respond(List<String> args)
            {
                return "hello world";
            }
        });

        requestRegistry.register(new Request()
        {
            @Override
            public String getRequest()
            {
                return "get oauthtoken #string(username) #int(hashodePw)";
            }

            @Override
            public String respond(List<String> args)
            {


                return "";
            }
        });
    }

    public void run()
    {
        this.apiConnection.start();

        while(true)
        {

        }
    }
}