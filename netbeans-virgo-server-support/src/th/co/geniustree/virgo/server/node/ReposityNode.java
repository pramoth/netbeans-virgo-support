/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server.node;

import javax.management.ObjectName;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import th.co.geniustree.virgo.server.VirgoServerInstanceImplementation;

/**
 *
 * @author pramoth
 */
public class ReposityNode extends AbstractNode {

    private final ObjectName objectName;
    private final Action refreshAction;

    ReposityNode(Action refreshAction, VirgoServerInstanceImplementation instance, ObjectName objectName) {
        super(Children.create(new RepositoryChildFactory(objectName, instance), true));
        this.objectName = objectName;
        setDisplayName(objectName.getKeyProperty("name"));
        this.refreshAction = refreshAction;
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{refreshAction};
    }
}
