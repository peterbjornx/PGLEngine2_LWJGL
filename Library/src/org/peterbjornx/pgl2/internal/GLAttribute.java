package org.peterbjornx.pgl2.internal;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/9/11
 * Time: 8:08 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public class GLAttribute {
    private int id;
    private Object value;

    public GLAttribute(Object value, int id) {
        this.value = value;
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public float getValueF(){
        return (Float) value;
    }

    public int getValueI(){
        return (Integer) value;
    }

    public byte getValueB(){
        return (Byte) value;
    }

}
