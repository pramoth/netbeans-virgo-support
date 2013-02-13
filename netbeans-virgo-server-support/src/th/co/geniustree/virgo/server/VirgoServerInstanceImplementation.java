/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.netbeans.api.server.properties.InstanceProperties;
import org.netbeans.spi.server.ServerInstanceImplementation;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Pramoth Suwanpech <pramoth@geniustree.co.th>
 */
public class VirgoServerInstanceImplementation implements ServerInstanceImplementation, Lookup.Provider {

    private final String serverName;
    private final String instanceName;
    private final boolean removable;
    private JPanel customizer;
    private final Map<String, Object> instanceProps;

    public VirgoServerInstanceImplementation(Map<String, Object> instanceProps, String serverName, String instanceName, boolean removable) {
        this.serverName = serverName;
        this.instanceName = instanceName;
        this.removable = removable;
        this.instanceProps = instanceProps;
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
        return new VirgoServerNode(this);
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
        return Lookups.fixed(new StartCommand());
    }

    public Map<String, Object> getInstanceProps() {
        return instanceProps;
    }
}
