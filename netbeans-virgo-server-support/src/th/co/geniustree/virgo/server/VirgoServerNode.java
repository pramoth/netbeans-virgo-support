/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import java.util.List;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Pramoth Suwanpech <pramoth@geniustree.co.th>
 */
public class VirgoServerNode extends AbstractNode {

    private final VirgoServerInstanceImplementation instance;

    public VirgoServerNode(VirgoServerInstanceImplementation instance) {
        super(Children.LEAF,Lookups.fixed(instance));
        this.instance = instance;
        setIconBaseWithExtension("th/co/geniustree/virgo/server/virgo.gif");
        setDisplayName(instance.getDisplayName());

    }

    @Override
    public Action[] getActions(boolean context) {
        List<? extends Action> actionsForPath = Utilities.actionsForPath(Constants.ACTION_VERGO_SERVER);
        return actionsForPath.toArray(new Action[]{});
    }
}
