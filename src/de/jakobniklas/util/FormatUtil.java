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
        if(!isNumeric(string) && !isBoolean(string))
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

    public static String convertToUTF8(String s)
    {
        String out = null;
        try
        {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        }
        catch(java.io.UnsupportedEncodingException e)
        {
            return null;
        }

        return out;
    }
}
