package org.peterbjornx.pgl2.util;

/**
 * User: Peter
 * Date: 6/13/11
 * Time: 1:49 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 * Describes an exception raised by PGLEngine
 * @author Peter Bosch (AKA Peterbjorn)
 */
public class PglException extends Exception {
    private String message;

    /**
     * Create a new PglException with the given message
     * @param message Message describing the event that caused this exception
     */
    public PglException(String message){
        this.message = message;
    }

    /**
     * Gets this events message
     * @return Message describing the event that caused this exception
     */
    public String getMessage() {
        return message;
    }
}
