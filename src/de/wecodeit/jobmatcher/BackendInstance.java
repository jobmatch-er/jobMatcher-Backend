package de.wecodeit.jobmatcher;

import de.jakobniklas.util.Exceptions;
import de.wecodeit.jobmatcher.registry.RequestRegistry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BackendInstance extends Thread
{
    private RequestRegistry requestRegistry;
    private APIConnection apiConnection;
    private Database database;

    public BackendInstance()
    {
        this.requestRegistry = new RequestRegistry();
        this.apiConnection = new APIConnection(requestRegistry, 54345);
        this.database = new Database("Praktikumsql1", "ldXwbcmrJg", "v22018085331772527.happysrv.de");

        requestRegistry.register(new Request()
        {
            @Override
            public String getRequest()
            {
                return "#int(respond_id) query #string(querry)";
            }

            @Override
            public String respond(List<String> args)
            {
                String query = new String(args.get(1).replaceAll("_/", " "));

                ResultSet resultSet = database.executeQuery(query);

                try
                {
                    if(!resultSet.first())
                    {
                        return args.get(0) + " query {}";
                    }
                    else
                    {
                        return args.get(0) + " query " + ResultSetConverter.convert(resultSet).toString();
                    }
                }
                catch(SQLException e)
                {
                    Exceptions.handle(e);

                    return args.get(0) + " internalerror";
                }
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