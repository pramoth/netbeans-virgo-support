/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server.api;

import javax.swing.event.ChangeListener;
import org.openide.util.ChangeSupport;

/**
 *
 * @author pramoth
 */
public class ServerStatus {

    private ChangeSupport changeSupport = new ChangeSupport(this);

    public enum Status {

        STARTING, STARTED, STOPED
    }
    private Status status = Status.STOPED;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
        changeSupport.fireChange();
    }

    public void addChangeListener(ChangeListener l) {
        changeSupport.addChangeListener(l);
    }

    public void removeChangeListener(ChangeListener l) {
        changeSupport.removeChangeListener(l);
    }
}
