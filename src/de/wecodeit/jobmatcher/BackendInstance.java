package de.wecodeit.jobmatcher;

import de.jakobniklas.util.Exceptions;
import de.jakobniklas.util.Log;
import de.wecodeit.jobmatcher.registry.RequestRegistry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

                return "{\"data\": " + queryResult + ", \"puid\": \"" + args.get(1) + "\"}";
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
                List<Employer> foundEmployers = new ArrayList<>();

                try
                {
                    //get user from db
                    JSONObject userToBeMatchedWith = ResultSetConverter.convert(database.executeQuery("SELECT * FROM `user` WHERE `email` = " + args.get(0))).getJSONObject(0);

                    //get cities near users city
                    JSONArray nearCities = Matcher.getNearCities(userToBeMatchedWith, database);

                    //for cities near user
                    for(int i = 0; i < nearCities.length(); i++)
                    {
                        //get all employers in the near city as JSONArray
                        String matchingCityEmployersStr = ResultSetConverter.convert(database.executeQuery("SELECT * FROM user WHERE city = '" + nearCities.getJSONObject(i).getString("name") + "' AND employerdata > 0")).toString();
                        JSONArray matchingCityEmployers = new JSONArray(matchingCityEmployersStr);

                        //for each employer in that city
                        for(int j = 0; j < matchingCityEmployers.length(); j++)
                        {
                            //add every possible employer to list
                            foundEmployers.add(j * i, new Employer(matchingCityEmployers.getJSONObject(j).toString()));

                            //check if the offered job matches the workarea of the user
                            if(matchingCityEmployers.getJSONObject(j * i).getInt("workarea") == userToBeMatchedWith.getInt("workarea"))
                            {
                                //increase employers score
                                foundEmployers.get(j * i).addToScore(10);

                                //chips of user and employer
                                JSONArray userChips = new JSONArray(userToBeMatchedWith.getString("chips"));
                                JSONArray employerchips = new JSONArray(matchingCityEmployers.getJSONObject(j * i).getString("chips"));

                                //for each userchip
                                for(int k = 0; k < userChips.length(); k++)
                                {
                                    //for employerchips
                                    for(int l = 0; l < employerchips.length(); l++)
                                    {
                                        if(userChips.getJSONObject(k).getString("name").equals(employerchips.getJSONObject(l).getString("name")))
                                        {
                                            foundEmployers.get(j * i).addToScore(1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                catch(SQLException e)
                {
                    Exceptions.handle(e);
                }

                for(Employer employer : foundEmployers)
                {
                    Log.print(employer.getScore() + "; " + employer.getJson());
                }

                Collections.sort(foundEmployers, Comparator.comparingInt(Employer::getScore));

                for(Employer employer : foundEmployers)
                {
                    Log.print(employer.getScore() + "; " + employer.getJson());
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