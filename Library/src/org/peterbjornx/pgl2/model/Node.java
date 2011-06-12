package org.peterbjornx.pgl2.model;

import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import org.peterbjornx.pgl2.camera.Camera;
import org.peterbjornx.pgl2.camera.RenderControl;
import org.peterbjornx.pgl2.math.VectorMath;

import java.util.LinkedList;

/**
 * User: Peter
 * Date: 6/8/11
 * Time: 9:51 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 *
 * This class represents a Node in the SceneGraph
 */
public class Node {
    /* Start of tree fields */
    private LinkedList<Node> children = new LinkedList<Node>();
    private Node parent = null;
    /* Start of World fields */
    private Vector3f position = new Vector3f(0,0,0);
    protected Quaternion rotation = new Quaternion(0,0,0,1);// Identity
    /* RenderControl fields (Ignored by logic unless node is the root node or a camera) */
    protected LinkedList<RenderControl> renderControls = new LinkedList<RenderControl>();

    public void render(Camera cam){
        for (Node child : children)
            if (child != null)
                child.render(cam);
    }

    public void add(Node child){
        children.add(child);
        child.parent = this;
    }

    public void addRenderControl(RenderControl control){
        renderControls.add(control);
    }

    public Node getRootNode() {
        Node current = this;
        while (current.parent != null)
            current = current.parent;
        return current;
    }

    public Quaternion getAbsoluteRotation(){
        if (parent == null)
            return rotation;
        return Quaternion.mul(parent.rotation,rotation,null);
    }

    public Vector3f getAbsolutePosition(){
        if (parent == null)
            return position;
        Vector3f rotatedLocal = VectorMath.quatRotate(parent.getAbsoluteRotation(),position);
        return Vector3f.add(parent.getAbsolutePosition(),rotatedLocal,null);
    }

    public Node getParent() {
        return parent;
    }

    public Quaternion getRotation() {
        return rotation;
    }

    public void setRotation(Quaternion rotation) {
        this.rotation = rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public LinkedList<Node> getChildren() {
        return children;
    }

}
