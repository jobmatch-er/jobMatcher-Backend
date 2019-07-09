package de.wecodeit.jobmatcher;

public class Main
{
    public static void main(String[] args)
    {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "off");

        BackendInstance jobMatcher = new BackendInstance();
        jobMatcher.start();
    }
}
