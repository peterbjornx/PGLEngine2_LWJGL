package org.peterbjornx.pgl2.util.memory;

import org.lwjgl.BufferUtils;

import javax.print.attribute.Size2DSyntax;
import java.nio.ByteBuffer;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/21/11
 * Time: 6:14 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public class DirectBufferManager {
    private static HashMap<Integer,ByteBuffer> bufferPool = new HashMap<Integer, ByteBuffer>();
    private static int memoryInPool = 0;
    private static int memoryInUse = 0;

    public static ByteBuffer alloc(int size){
        ByteBuffer buffer = bufferPool.remove(size);
        if (buffer == null) {
            buffer = BufferUtils.createByteBuffer(size);
            memoryInUse  += size;
        } else {
            memoryInPool -= size;
            memoryInUse  += size;
        }
        return buffer;
    }

    public static void free(ByteBuffer buffer){
        buffer.clear();
        int capacity = buffer.capacity();
        bufferPool.put(capacity, buffer);
        memoryInUse  -= capacity;
        memoryInPool += capacity;
    }

    public static int getMemoryInPool() {
        return memoryInPool;
    }

    public static int getMemoryInUse() {
        return memoryInUse;
    }
}
