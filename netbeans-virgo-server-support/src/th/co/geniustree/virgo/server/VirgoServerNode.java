/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import th.co.geniustree.virgo.server.api.Constants;
import java.util.List;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.Mutex;
import org.openide.util.Utilities;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Pramoth Suwanpech <pramoth@geniustree.co.th>
 */
public class VirgoServerNode extends AbstractNode {

    private final String NORMAL_ICON = "th/co/geniustree/virgo/server/resources/virgo.png";
    private final String STARTING_ICON = "th/co/geniustree/virgo/server/resources/virgostarting.png";
    private final String RUN_ICON = "th/co/geniustree/virgo/server/resources/virgorun.png";
    private final VirgoServerInstanceImplementation instance;

    public VirgoServerNode(VirgoServerInstanceImplementation instance) {
        super(Children.LEAF, instance.getLookup());
        this.instance = instance;
        setIconBaseWithExtension(NORMAL_ICON);
        setDisplayName(instance.getDisplayName());

    }

    @Override
    public Action[] getActions(boolean context) {
        List<? extends Action> actionsForPath = Utilities.actionsForPath(Constants.ACTION_VERGO_SERVER);
        return actionsForPath.toArray(new Action[]{});
    }

    public void starting() {
        setIconBaseWithExtension(STARTING_ICON);
        fireIconChange();

    }

    public void stoped() {
        setIconBaseWithExtension(NORMAL_ICON);
        fireIconChange();
    }

    public void started() {
        setIconBaseWithExtension(RUN_ICON);
        fireIconChange();
    }

    public void stoping() {
        setIconBaseWithExtension(STARTING_ICON);
        fireIconChange();
    }
}
