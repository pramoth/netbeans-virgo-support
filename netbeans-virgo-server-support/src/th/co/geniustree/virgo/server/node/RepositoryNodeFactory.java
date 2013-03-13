/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server.node;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import th.co.geniustree.virgo.server.VirgoServerInstanceImplementation;

/**
 *
 * @author pramoth
 */
public class RepositoryNodeFactory extends ChildFactory<ObjectName> {
    private final VirgoServerInstanceImplementation instance;

    RepositoryNodeFactory(VirgoServerInstanceImplementation instance) {
        this.instance = instance;
    }

    @Override
    protected boolean createKeys(List<ObjectName> toPopulate) {
        if (toPopulate.isEmpty()) {
            try {
                toPopulate.add(new ObjectName("org.eclipse.virgo.kernel:type=Repository,name=ext"));
                // TODO
                //hosted-repository available after this method call.
                //toPopulate.add(new ObjectName("org.eclipse.virgo.kernel:type=Repository,name=hosted-repository"));
                toPopulate.add(new ObjectName("org.eclipse.virgo.kernel:type=Repository,name=usr"));
            } catch (MalformedObjectNameException malformedObjectNameException) {
                malformedObjectNameException.printStackTrace();
            }
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(ObjectName key) {
        Action refreshAction = new AbstractAction("Refresh") {

            @Override
            public void actionPerformed(ActionEvent e) {
                refresh(false);
            }
        };
        return new ReposityNode(refreshAction,instance,key); 
    }
    
}
