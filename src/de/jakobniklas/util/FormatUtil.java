package de.jakobniklas.util;

public class FormatUtil
{
    public static boolean isNumeric(String string)
    {
        try
        {
            Double.parseDouble(string);

            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }

    public static boolean isText(String string)
    {
        if(!isNumeric(string))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isBoolean(String string)
    {
        if(string.equals("true") || string.equals("false"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
