package de.wecodeit.jobmatcher;

import de.jakobniklas.util.Log;

public class Main
{
    public static void main(String[] args)
    {
        Log.print("Meme", "skateMINZIG skateTACO");
        Log.print("JobMatch.er - Backend", "Started");

        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "off");

        BackendInstance jobMatcher = new BackendInstance();
        jobMatcher.start();
    }
}