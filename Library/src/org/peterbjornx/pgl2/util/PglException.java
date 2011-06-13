package org.peterbjornx.pgl2.util;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/13/11
 * Time: 1:49 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public class PglException extends Exception {
    private String message;

    public PglException(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
