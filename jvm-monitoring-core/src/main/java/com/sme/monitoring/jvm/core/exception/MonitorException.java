package com.sme.monitoring.jvm.core.exception;

/**
 * The common monitoring exception.
 */
public class MonitorException extends RuntimeException
{
    public MonitorException(String message)
    {
        super(message);
    }

    public MonitorException(Throwable cause)
    {
        super(cause);
    }

    public MonitorException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public MonitorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
