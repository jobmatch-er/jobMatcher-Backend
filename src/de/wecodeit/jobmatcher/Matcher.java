package de.wecodeit.jobmatcher;

import de.jakobniklas.util.Exceptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Matcher
{
    public static JSONArray getNearCities(JSONObject userToBeMatchedWith, Database database)
    {
        try
        {
            URL getCityURL = new URL("http://geodb-free-service.wirefreethought.com/v1/geo/cities?limit=5&offset=0&namePrefix=" + userToBeMatchedWith.getString("city").replaceAll(" ", "%20"));
            String getCityResponse = "";

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(getCityURL.openStream(), "UTF-8")))
            {
                for(String line; (line = reader.readLine()) != null; )
                {
                    getCityResponse += line;
                }
            }

            int cityLocationCode = -1;

            JSONObject userCity = null;

            try
            {
                cityLocationCode = new JSONObject(getCityResponse).getJSONArray("data").getJSONObject(0).getInt("id");

                userCity = new JSONObject(getCityResponse).getJSONArray("data").getJSONObject(0);
            }
            catch(JSONException e)
            {
                Exceptions.handle(e);
            }

            URL getNearByCitiesURL = new URL("http://geodb-free-service.wirefreethought.com/v1/geo/cities/" + cityLocationCode + "/nearbyCities?limit=10&offset=0&radius=" + userToBeMatchedWith.getInt("workradius"));
            String getNearByCitiesResponse = "";

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(getNearByCitiesURL.openStream(), "UTF-8")))
            {
                for(String line; (line = reader.readLine()) != null; )
                {
                    getNearByCitiesResponse += line;
                }
            }

            JSONArray returnVal = new JSONObject(getNearByCitiesResponse).getJSONArray("data");

            returnVal.put(userCity);

            //Log.print(returnVal.toString());

            return returnVal;
        }
        catch(Exception e)
        {
            Exceptions.handle(e);
        }

        return null;
    }

    public static int getCityCode(String cityname)
    {
        try
        {
            URL getCityURL = new URL("http://geodb-free-service.wirefreethought.com/v1/geo/cities?limit=5&offset=0&namePrefix=" + cityname.replaceAll(" ", "%20"));
            String getCityResponse = "";

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(getCityURL.openStream(), "UTF-8")))
            {
                for(String line; (line = reader.readLine()) != null; )
                {
                    getCityResponse += line;
                }
            }

            int cityLocationCode = -1;

            try
            {
                cityLocationCode = new JSONObject(getCityResponse).getJSONArray("data").getJSONObject(0).getInt("id");
            }
            catch(JSONException e)
            {
                Exceptions.handle(e);

                return cityLocationCode;
            }
        }
        catch(Exception e)
        {
            Exceptions.handle(e);
        }

        return -1;
    }
}
