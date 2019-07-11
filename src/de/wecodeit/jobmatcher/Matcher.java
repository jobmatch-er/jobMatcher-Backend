package de.wecodeit.jobmatcher;

import de.jakobniklas.util.Exceptions;
import de.jakobniklas.util.Log;
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

            try
            {
                cityLocationCode = new JSONObject(getCityResponse).getJSONArray("data").getJSONObject(0).getInt("id");
            }
            catch(JSONException e)
            {
                Exceptions.handle(e);
            }

            URL getNearByCitiesURL = new URL("http://geodb-free-service.wirefreethought.com/v1/geo/cities/" + cityLocationCode + "/nearbyCities?limit=7&offset=0&radius=" + userToBeMatchedWith.getInt("workradius"));
            String getNearByCitiesResponse = "";

            Log.print(getNearByCitiesURL.toString());

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(getNearByCitiesURL.openStream(), "UTF-8")))
            {
                for(String line; (line = reader.readLine()) != null; )
                {
                    getNearByCitiesResponse += line;
                }
            }

            return new JSONObject(getNearByCitiesResponse).getJSONArray("data");
        }
        catch(Exception e)
        {
            Exceptions.handle(e);
        }

        return null;
    }
}
