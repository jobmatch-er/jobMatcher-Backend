package de.jakobniklas.util;

/**
 * Class to handle any {@link Exception Exception} occurring
 *
 * @author Jakob-Niklas See
 * @see #handle(Exception)
 * @see #handle(String)
 */
public class Exceptions
{
    /**
     * Handles exception occurring (accepting {@link Exception Exception} object) by printing the stacktrace to {@link
     * System#out}
     *
     * @param e {@link Exception Exception} object
     *
     * @return void
     */
    public static void handle(Exception e)
    {
        e.printStackTrace();
    }

    /**
     * Handles exception occurring by accepting a message and throwing a new {@link
     * de.jakobniklas.util.JobMatcherException ScrapeNodeException} and {@link de.jakobniklas.util.Log logging} with the
     * parameter 'Exception'
     *
     * @param message message for the {@link de.jakobniklas.util.JobMatcherException ScrapeNodeException}
     *
     * @return void
     */
    public static void handle(String message)
    {
        Log.print("Exception", message);
        Exception e = new JobMatcherException(message);
        handle(e);
    }
}
