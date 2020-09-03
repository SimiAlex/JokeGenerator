package simialex.com.github.jokegenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class Utils 
{
    //retrieve JSON object from specified url(RESTfull API)
    public static JSONObject getJSON(String path)
    {
        URL url = null;
        HttpURLConnection httpConn = null;
        try
        {
            url = new URL(path);
            httpConn = (HttpURLConnection)(url.openConnection());
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("Accept", "application/json");
            httpConn.connect();

            if(httpConn.getResponseCode() == 200)
            {
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream())))
                {
                    String s;
                    StringBuilder sb = new StringBuilder();
                    
                    while((s = reader.readLine()) != null)
                    {
                        sb.append(s);
                    }
                    return new JSONObject(sb.toString());
                }
                catch(IOException | JSONException e)
                {
                    e.printStackTrace();
                    return null;
                }  
            }
            else
            {
                System.out.println(httpConn.getResponseCode());
                return null;
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    //get joke String from JSON
    public static String processJSON(JSONObject jo)
    {
        if (jo == null)
            return null;
        String joke;
        try
        {
            joke = jo.getString("joke");
            return joke;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}