package canvas;

/***********************************************************************
 * Module:  CompositeWire.java
 * Author:  goran (updated)
 * Purpose: Defines a CompositeWire that holds a collection of Conductors
 *          (such as individual Wire objects) and draws them together.
 ***********************************************************************/

import java.awt.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class CompositeWire extends Conductor {
    // A collection of conductors (wires or even nested composite wires).
    private Collection<Conductor> conductors;

    public CompositeWire(Point point, Point endPoint, int width, int height) {
        super(point, endPoint, width, height);
    }
    
    public CompositeWire() {
        this.type = "CompositeWire";
    }
    
    /** Returns the collection of contained conductors. */
    public Collection<Conductor> getConductor() {
        if (conductors == null)
            conductors = new HashSet<Conductor>();
        return conductors;
    }
    
    /** Returns an iterator over the contained conductors. */
    public Iterator getIteratorConductor() {
        if (conductors == null)
            conductors = new HashSet<Conductor>();
        return conductors.iterator();
    }
    
    /** Sets the contained conductors. */
    public void setConductor(Collection<Conductor> newConductor) {
        removeAllConductor();
        for (Iterator iter = newConductor.iterator(); iter.hasNext();)
            addConductor((Conductor) iter.next());
    }
    
    /** Adds a conductor to the composite. */
    public void addConductor(Conductor newConductor) {
        if (newConductor == null)
            return;
        if (conductors == null)
            conductors = new HashSet<Conductor>();
        if (!conductors.contains(newConductor))
            conductors.add(newConductor);
    }
    
    /** Removes a conductor from the composite. */
    public void removeConductor(Conductor oldConductor) {
        if (oldConductor == null)
            return;
        if (conductors != null && conductors.contains(oldConductor))
            conductors.remove(oldConductor);
    }
    
    /** Removes all contained conductors. */
    public void removeAllConductor() {
        if (conductors != null)
            conductors.clear();
    }
    
    /**
     * Draws the CompositeWire by iterating over each contained conductor and invoking its draw method.
     */
    public void draw(Graphics g) {
        if (conductors != null) {
            for (Conductor conductor : conductors) {
                // We assume each conductor (Wire or CompositeWire) implements its own draw method.
                if (conductor instanceof Wire) {
                    ((Wire) conductor).draw(g);
                } else if (conductor instanceof CompositeWire) {
                    ((CompositeWire) conductor).draw(g);
                }
            }
        }
    }
}
