package de.friedenhagen.android.mittagstischka.retrievers;

/**
 * Thrown when there were problems contacting the remote API server, either because of a network error, or the
 * server returned a bad status code.
 */
public class ApiException extends Exception {
    
    /**
     * ApiException with detailMessage and throwable.
     * @param detailMessage message
     * @param throwable throwable
     */
    public ApiException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    /**
     * ApiException with detailMessage.
     * @param detailMessage message
     */
    public ApiException(String detailMessage) {
        super(detailMessage);
    }
}