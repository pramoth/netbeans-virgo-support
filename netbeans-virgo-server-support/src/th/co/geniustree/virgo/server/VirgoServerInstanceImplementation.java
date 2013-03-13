/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import th.co.geniustree.virgo.server.node.VirgoServerNode;
import java.util.concurrent.Executors;
import th.co.geniustree.virgo.server.api.StartCommand;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.netbeans.api.server.ServerInstance;
import org.netbeans.spi.server.ServerInstanceImplementation;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;
import th.co.geniustree.virgo.server.api.Deployer;
import th.co.geniustree.virgo.server.api.ServerInstanceProviderUtils;
import th.co.geniustree.virgo.server.api.StopCommand;
import th.co.geniustree.virgo.server.api.VirgoServerAttributes;

/**
 *
 * @author Pramoth Suwanpech <pramoth@geniustree.co.th>
 */
public class VirgoServerInstanceImplementation implements ServerInstanceImplementation, Lookup.Provider {

    private final String serverName;
    private final String instanceName;
    private final boolean removable;
    private JPanel customizer;
    private final VirgoServerAttributes attr;
    private VirgoServerNode virgoServerNode;
    private final InstanceContent content = new InstanceContent();
    private final AbstractLookup dynamicLookup;
    private final ProxyLookup lookup;
    private final StartCommand startCommand;
    private final StopCommand stopCommand;
    private final VirgoServerBasicNode virgoServerBasicNode;

    public VirgoServerInstanceImplementation(VirgoServerAttributes attr, String serverName, String instanceName, boolean removable) {
        this.serverName = serverName;
        this.instanceName = instanceName;
        this.removable = removable;
        this.attr = attr;
        startCommand = new StartCommand(this);
        stopCommand = new StopCommand(this);
        dynamicLookup = new AbstractLookup(content);
        content.add(startCommand);
        lookup = new ProxyLookup(dynamicLookup, Lookups.fixed(attr, new Deployer(this)));
        virgoServerNode = new VirgoServerNode(this);
        virgoServerBasicNode = new VirgoServerBasicNode(this);
        checkServerStatus();
    }
//TODO this methos is Code dup with StartCommand. redesign it.

    private void checkServerStatus() {
        Executors.newCachedThreadPool().execute(new InstanceChecker(this));
    }

    @Override
    public String getDisplayName() {
        return instanceName;
    }

    @Override
    public String getServerDisplayName() {
        return serverName;
    }

    @Override
    public Node getFullNode() {
        return virgoServerNode;
    }

    @Override
    public Node getBasicNode() {
        return virgoServerBasicNode;
    }

    @Override
    public JComponent getCustomizer() {
        synchronized (this) {
            if (customizer == null) {
                customizer = new VirgoInstanceCustomizer(this);
            }
            return customizer;
        }
    }

    @Override
    public void remove() {
        VirgoServerInstanceProvider virgoServerInstanceProvider = (VirgoServerInstanceProvider) ServerInstanceProviderUtils.getVirgoServerInstanceProvider();
        virgoServerInstanceProvider.remove(getLookup().lookup(ServerInstance.class));
    }

    @Override
    public boolean isRemovable() {
        return removable;
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    public VirgoServerAttributes getAttr() {
        return attr;
    }

    public VirgoServerNode getVirgoServerNode() {
        return virgoServerNode;
    }

    public void starting() {
        virgoServerNode.starting();
        content.remove(startCommand);
        content.remove(stopCommand);
    }

    public void started() {
        virgoServerNode.started();
        content.remove(startCommand);
        content.add(stopCommand);
    }

    public void stoped() {
        virgoServerNode.stoped();
        content.add(startCommand);
        content.remove(stopCommand);
    }

    public void stoping() {
        virgoServerNode.stoping();
        content.remove(startCommand);
        content.remove(stopCommand);
    }

    void addServerInstanceToLookup(ServerInstance instance) {
        content.add(instance);
    }
}
