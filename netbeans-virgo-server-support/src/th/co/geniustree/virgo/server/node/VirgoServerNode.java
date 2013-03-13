/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server.node;

import th.co.geniustree.virgo.server.api.Constants;
import java.util.List;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import th.co.geniustree.virgo.server.VirgoServerInstanceImplementation;

/**
 *
 * @author Pramoth Suwanpech <pramoth@geniustree.co.th>
 */
public class VirgoServerNode extends AbstractNode {

    private final String NORMAL_ICON = "th/co/geniustree/virgo/server/resources/virgo.png";
    private final String STARTING_ICON = "th/co/geniustree/virgo/server/resources/virgostarting.png";
    private final String RUN_ICON = "th/co/geniustree/virgo/server/resources/virgorun.png";
    private VirgoServerInstanceImplementation instance;
    private RepositoryRootNodeFactory factory;

    public VirgoServerNode(VirgoServerInstanceImplementation instance) {
        this(new RepositoryRootNodeFactory(instance), instance.getLookup());
        setDisplayName(instance.getDisplayName());
    }

    private VirgoServerNode(RepositoryRootNodeFactory factory, Lookup lookup) {
        super(Children.create(factory, false), lookup);
        this.factory = factory;
        setIconBaseWithExtension(NORMAL_ICON);
        
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
