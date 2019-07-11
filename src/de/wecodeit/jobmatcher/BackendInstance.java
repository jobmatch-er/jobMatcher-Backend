package de.wecodeit.jobmatcher;

import de.jakobniklas.util.Exceptions;
import de.wecodeit.jobmatcher.registry.RequestRegistry;
import org.json.JSONArray;
import org.json.JSONObject;

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
                return "query #string(querry) #string(puid)";
            }

            @Override
            public String respond(List<String> args)
            {
                String queryResult = null;

                try
                {
                    queryResult = ResultSetConverter.convert(database.executeQuery(args.get(0).replaceAll("_/", " "))).toString();
                    queryResult = queryResult.substring(1).substring(0, queryResult.length() - 2);
                }
                catch(SQLException e)
                {
                    Exceptions.handle(e);
                }

                return new JSONObject().put("data", queryResult).put("puid", args.get(1)).toString();
            }
        });

        requestRegistry.register(new Request()
        {
            @Override
            public String getRequest()
            {
                return "match #string(username) #string(puid)";
            }

            @Override
            public String respond(List<String> args)
            {
                try
                {
                    JSONObject userToBeMatchedWith = ResultSetConverter.convert(database.executeQuery("SELECT * FROM `user` WHERE `email` = " + args.get(0) + "")).getJSONObject(0);
                    JSONArray nearCities = Matcher.getNearCities(userToBeMatchedWith, database);


                }
                catch(SQLException e)
                {
                    Exceptions.handle(e);
                }

                return new JSONObject().put("data", "null").put("puid", args.get(1)).toString();
            }
        });

        requestRegistry.register(new Request()
        {
            @Override
            public String getRequest()
            {
                return "command #string(sql) #string(puid)";
            }

            @Override
            public String respond(List<String> args)
            {
                database.execute(args.get(0).replaceAll("_/", " "));

                return new JSONObject().put("data", "null").put("puid", args.get(1)).toString();
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