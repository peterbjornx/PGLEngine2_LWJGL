package org.peterbjornx.pgl2.buffer.impl;

import com.sun.deploy.util.BufferUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GLContext;
import org.peterbjornx.pgl2.buffer.ElementBuffer;
import org.peterbjornx.pgl2.util.PglException;
import org.peterbjornx.pgl2.util.ServerMemoryManager;
import org.peterbjornx.pgl2.util.memory.DirectBufferManager;

import java.nio.ByteBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/13/11
 * Time: 5:25 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public class ElementArray implements ElementBuffer{
    private ByteBuffer elementBuffer;
    private boolean dataTransferred = false;
    private int drawMode;
    private int polygonCount = 0;

    /**
     * Creates a new element array
     * @param maxSize Maximal amount of polygons this buffer may store
     * @param drawMode The OpenGL drawing mode to use
     */
    public ElementArray(int maxSize,int drawMode) {
        this.drawMode = drawMode;
        this.elementBuffer = DirectBufferManager.alloc(maxSize * sizeof() * 4);
    }

    private int sizeof() {
        switch (drawMode){
            case GL_POINTS:
                return 1;
            case GL_LINES:
                return 2;
            case GL_TRIANGLES:
                return 3;
            case GL_QUADS:
                return 4;
        }
        return 1;
    }

    /**
     * Activates this buffer
     */
    public void bind() {
        if (!dataTransferred){
            dataTransferred = true;
            elementBuffer.flip();
        }
    }

    /**
     * Enables this buffer's capabilities
     */
    public void enable() {
    }

    /**
     * Disables this buffer's capabilities
     */
    public void disable() {
    }

    /**
     * Renders the contents of this element buffer.
     * Note: requires that the element and geometry buffers have been bound and enabled
     */
    public void draw(){
        glDrawElements(drawMode, elementBuffer.asIntBuffer());
    }

    /**
     * Add an polygon (element) to this buffer
     * @param points The vertex indices describing this polygon
     * @throws PglException When this buffer is already uploaded<BR/> to the server or when the polygon index count doesn't match current drawing mode
     */
    public void addPolygon(List<Integer> points) throws PglException {
        if (dataTransferred)
            throw new PglException("Tried to modify uploaded element buffer");
        if (points.size() != sizeof())
            throw new PglException("Tried to add polygon of different element count as draw mode");
        polygonCount++;
        while(true){
            if (points.isEmpty())
                return;
            elementBuffer.putInt(points.remove(0));
        }
    }

    /**
     * Called by the garbage collector on an object when garbage collection
     * determines that there are no more references to the object.
     * A subclass overrides the <code>finalize</code> method to dispose of
     * system resources or to perform other cleanup.
     * <p/>
     * The general contract of <tt>finalize</tt> is that it is invoked
     * if and when the Java<font size="-2"><sup>TM</sup></font> virtual
     * machine has determined that there is no longer any
     * means by which this object can be accessed by any thread that has
     * not yet died, except as a result of an action taken by the
     * finalization of some other object or class which is ready to be
     * finalized. The <tt>finalize</tt> method may take any action, including
     * making this object available again to other threads; the usual purpose
     * of <tt>finalize</tt>, however, is to perform cleanup actions before
     * the object is irrevocably discarded. For example, the finalize method
     * for an object that represents an input/output connection might perform
     * explicit I/O transactions to break the connection before the object is
     * permanently discarded.
     * <p/>
     * The <tt>finalize</tt> method of class <tt>Object</tt> performs no
     * special action; it simply returns normally. Subclasses of
     * <tt>Object</tt> may override this definition.
     * <p/>
     * The Java programming language does not guarantee which thread will
     * invoke the <tt>finalize</tt> method for any given object. It is
     * guaranteed, however, that the thread that invokes finalize will not
     * be holding any user-visible synchronization locks when finalize is
     * invoked. If an uncaught exception is thrown by the finalize method,
     * the exception is ignored and finalization of that object terminates.
     * <p/>
     * After the <tt>finalize</tt> method has been invoked for an object, no
     * further action is taken until the Java virtual machine has again
     * determined that there is no longer any means by which this object can
     * be accessed by any thread that has not yet died, including possible
     * actions by other objects or classes which are ready to be finalized,
     * at which point the object may be discarded.
     * <p/>
     * The <tt>finalize</tt> method is never invoked more than once by a Java
     * virtual machine for any given object.
     * <p/>
     * Any exception thrown by the <code>finalize</code> method causes
     * the finalization of this object to be halted, but is otherwise
     * ignored.
     *
     * @throws Throwable the <code>Exception</code> raised by this method
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (elementBuffer != null)
            DirectBufferManager.free(elementBuffer);
    }
}
