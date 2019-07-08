package de.jakobniklas.util.actions;

/**
 * Abstract class to be implemented by any in a {@link de.jakobniklas.util.Queue Queue} performable action
 *
 * @author Jakob-Niklas See
 * @see #perform()
 */
public abstract class Action
{
    /**
     * Abstract method which is being called by the {@link de.jakobniklas.util.Queue#iterate() queue-iterator} method
     */
    public abstract void perform();
}
