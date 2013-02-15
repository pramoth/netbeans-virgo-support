/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import th.co.geniustree.virgo.server.api.StartCommand;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.netbeans.spi.server.ServerInstanceImplementation;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;
import th.co.geniustree.virgo.server.api.Deployer;
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
    private  VirgoServerNode virgoServerNode;
    private final InstanceContent content = new InstanceContent();
    private final AbstractLookup dynamicLookup;
    private final ProxyLookup lookup;
    private final StartCommand startCommand;
    private final StopCommand stopCommand;

    public VirgoServerInstanceImplementation(VirgoServerAttributes attr, String serverName, String instanceName, boolean removable) {
        this.serverName = serverName;
        this.instanceName = instanceName;
        this.removable = removable;
        this.attr = attr;
        startCommand = new StartCommand(this);
        stopCommand = new StopCommand(this);
        dynamicLookup = new AbstractLookup(content);
        content.add(startCommand);
        content.add(stopCommand);
        lookup = new ProxyLookup(dynamicLookup,Lookups.fixed(attr,new Deployer(attr)));
        virgoServerNode = new VirgoServerNode(this);
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
        return new AbstractNode(Children.LEAF) {
            @Override
            public String getDisplayName() {
                return instanceName;
            }
        };
    }

    @Override
    public JComponent getCustomizer() {
        synchronized (this) {
            if (customizer == null) {
                customizer = new JPanel();
                customizer.add(new JLabel(instanceName));
            }
            return customizer;
        }
    }

    @Override
    public void remove() {
        System.out.println("remove call");
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
        content.add(stopCommand);
    }

    public void stoped() {
        virgoServerNode.stoped();
        content.add(startCommand);
        content.add(stopCommand);
    }

    public void stoping() {
        virgoServerNode.stoping();
        content.remove(startCommand);
        content.remove(stopCommand);
    }
}
