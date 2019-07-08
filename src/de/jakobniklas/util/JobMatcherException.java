package de.jakobniklas.util;

/**
 * Class called to handle any exceptions caused by the framework
 * <br><br>
 * Extends {@link Exception Exception}
 *
 * @author Jakob-Niklas See
 * @see de.jakobniklas.util.Exceptions Exceptions
 * @see #JobMatcherException()
 * @see #JobMatcherException(String)
 */
public class JobMatcherException extends Exception
{
    /**
     * Generated uuid
     */
    private static final long serialVersionUID = -3978677442306412756L;

    /**
     * Constructor of the exception. Is calling the {@code super(message);} of {@link Exception Exception}
     *
     * @param message Message to be displayed when showing the exception in the {@link System#out System.out} stream
     *                (console)
     *
     * @see #JobMatcherException()
     */
    public JobMatcherException(String message)
    {
        super(message);
    }

    /**
     * Constructor of the exception. Is calling the {@code super();} of {@link Exception Exception}
     *
     * <br><br> Won't show any message besides the stacktrace
     *
     * @see #JobMatcherException(String)
     */
    public JobMatcherException()
    {
        super();
    }
}
