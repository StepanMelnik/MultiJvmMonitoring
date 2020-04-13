package com.sme.monitoring.jvm.core.util;

/**
 * Useful utilities to work with threads.
 */
public final class ThreadUtil
{
    private ThreadUtil()
    {
    }

    /**
     * Thread sleep.
     * 
     * @param interval The interval to sleep thread.
     */
    @SuppressWarnings("static-access")
    public static void sleep(long interval)
    {
        try
        {
            Thread.currentThread().sleep(interval);
        }
        catch (InterruptedException e)
        {
            // do nothing.
        }
    }

    /**
     * Lock main thread.
     */
    public static void lockToFinishMainThread()
    {
        try
        {
            Object lock = new Object();
            synchronized (lock)
            {
                while (true)
                {
                    lock.wait();
                }
            }
        }
        catch (InterruptedException ex)
        {
            // do nothing.
        }
    }
}
