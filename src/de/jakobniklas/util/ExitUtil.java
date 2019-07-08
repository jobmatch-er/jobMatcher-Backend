package de.jakobniklas.util;

public class ExitUtil
{
    public static void exit(int errorCode)
    {
        Log.print("Exit", "Exiting with errorCode '" + errorCode + "'");

        System.exit(errorCode);
    }

    public static void exit()
    {
        exit(0);
    }
}
