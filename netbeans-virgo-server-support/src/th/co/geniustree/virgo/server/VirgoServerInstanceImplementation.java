/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.netbeans.api.server.ServerInstance;
import org.netbeans.spi.server.ServerInstanceImplementation;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Pramoth Suwanpech <pramoth@geniustree.co.th>
 */
public class VirgoServerInstanceImplementation implements ServerInstanceImplementation {

    private final String serverName;
    private final String instanceName;
    private final boolean removable;
    private ServerInstance serverInstance;
    private final VirgoServerInstanceProvider provider;
    private JPanel customizer;

    public VirgoServerInstanceImplementation(VirgoServerInstanceProvider provider, String serverName, String instanceName, boolean removable) {
        this.provider = provider;
        this.serverName = serverName;
        this.instanceName = instanceName;
        this.removable = removable;
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
        return new VirgoServerNode();
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
}
