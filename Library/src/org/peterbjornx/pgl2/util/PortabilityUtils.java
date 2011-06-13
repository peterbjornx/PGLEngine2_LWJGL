package org.peterbjornx.pgl2.util;

import java.nio.ByteBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/13/11
 * Time: 1:55 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public class PortabilityUtils {
    public static void putFloatLE(ByteBuffer data, float v) {
        int f_b = Float.floatToRawIntBits(v);
        int f_l = Integer.reverseBytes(f_b);
        data.putInt(f_l);
    }

    public static void putIntLE(ByteBuffer data, int f_b) {
        int f_l = Integer.reverseBytes(f_b);
        data.putInt(f_l);
    }
}
