package org.peterbjornx.pgl2.model;

import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import org.peterbjornx.pgl2.camera.Camera;
import org.peterbjornx.pgl2.camera.RenderControl;
import org.peterbjornx.pgl2.math.VectorMath;

import java.lang.reflect.Type;
import java.util.LinkedList;

/**
 * User: Peter
 * Date: 6/8/11
 * Time: 9:51 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 *
 * This class represents a Node in the SceneGraph
 * @author Peter Bosch (AKA Peterbjorn)
 */
public class Node {
    /* Start of tree fields */
    private LinkedList<Node> children = new LinkedList<Node>();
    protected Node parent = null;

    /* Start of World fields */
    private Vector3f position = new Vector3f(0,0,0);
    protected Quaternion rotation = new Quaternion(0,0,0,1);// Identity
    /* RenderControl fields (Ignored by logic unless node is the root node or a camera) */
    protected LinkedList<RenderControl> renderControls = new LinkedList<RenderControl>();

    /**
     * Renders this node and its children
     * @param cam The camera that is rendering this node.
     * When the camera parameter is null only Camera nodes will render,
     * this is used to have all active cameras render.
     */
    public void render(Camera cam){
        for (Node child : children)
            if (child != null)
                child.render(cam);
    }

    /**
     * Adds a child node.
     * @param child The child to add.
     */
    public void add(Node child){
        children.add(child);
        child.parent = this;
    }

    public void remove(Node child){
        children.remove(child);
        child.parent = null;
    }

    /**
     * Adds a render control
     * @param control The render control
     * Note: This only has effect on the root node and on Camera nodes
     */
    public void addRenderControl(RenderControl control){
        renderControls.add(control);
    }

    /**
     * Gets the root node
     * @return The root node
     */
    public Node getRootNode() {
        Node current = this;
        while (current.parent != null)
            current = current.parent;
        return current;
    }

    /**
     * Gets the absolute rotation.
     * @return The absolute rotation
     */
    public Quaternion getAbsoluteRotation(){
        if (parent == null)
            return rotation;
        return Quaternion.mul(parent.rotation,rotation,null);
    }

    /**
     * Gets the absolute position.
     * @return the absolute position.
     */
    public Vector3f getAbsolutePosition(){
        if (parent == null)
            return position;
        Vector3f rotatedLocal = VectorMath.quatRotate(parent.getAbsoluteRotation(),position);
        return Vector3f.add(parent.getAbsolutePosition(), rotatedLocal, null);
    }

    /**
     * Gets this node's parent
     * @return This node's parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Gets the rotation of this node relative to its parent.
     * @return The rotation of this node relative to its parent.
     */
    public Quaternion getRotation() {
        return rotation;
    }

    /**
     * Sets the rotation of this node relative to its parent.
     * @param rotation The rotation of this node relative to its parent.
     */
    public void setRotation(Quaternion rotation) {
        this.rotation = rotation;
    }

    /**
     * Gets the position of this node relative to its parent.
     * @return The position of this node relative to its parent.
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * Sets the position of this node relative to its parent.
     * @param position The position of this node relative to its parent.
     */
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     * Gets a list of this node's children
     * @return A list of this node's children
     */
    public LinkedList<Node> getChildren() {
        return children;
    }

    /**
     * Gets a list of this node's children that are of type T
     * @param t Child type
     * @return A list of this node's children that are of type T
     */
    public LinkedList<Node> getChildrenByType(Class t) {
        LinkedList<Node> _t_children = new LinkedList<Node>();
        for (Node n : children)
            if (n != null && n.getClass() == t)
                _t_children.add(n);
        return _t_children;
    }

}
