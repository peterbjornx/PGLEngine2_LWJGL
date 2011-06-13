package org.peterbjornx.pgl2.util;

import java.nio.ByteBuffer;

/**
 * User: Peter
 * Date: 6/13/11
 * Time: 1:55 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 * Provides routines necessary only for making PGLEngine portable
 * @author Peter Bosch (AKA Peterbjorn)
 */
public class PortabilityUtils {

    /**
     * Write a float to a ByteBuffer in Little Endian ordering
     * @param data The ByteBuffer to write to
     * @param v The value to write
     */
    public static void putFloatLE(ByteBuffer data, float v) {
        int f_b = Float.floatToRawIntBits(v);
        int f_l = Integer.reverseBytes(f_b);
        data.putInt(f_l);
    }

    /**
     * Write an integer to a ByteBuffer in Little Endian ordering
     * @param data The ByteBuffer to write to
     * @param v The value to write
     */
    public static void putIntLE(ByteBuffer data, int v) {
        int v_l = Integer.reverseBytes(v);
        data.putInt(v_l);
    }
}
