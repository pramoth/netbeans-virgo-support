/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server.node;

import java.util.Arrays;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.remote.JMXConnector;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import th.co.geniustree.virgo.server.JmxConnectorHelper;
import th.co.geniustree.virgo.server.VirgoServerInstanceImplementation;

/**
 *
 * @author pramoth
 */
public class RepositoryChildNode extends AbstractNode {

    private final CompositeData compositeData;
    private final VirgoServerInstanceImplementation instance;

    public RepositoryChildNode(VirgoServerInstanceImplementation instance, CompositeData compositeData) {
        super(Children.LEAF);
        this.compositeData = compositeData;
        this.instance = instance;
        String name = (String) compositeData.get("name");
        String version = (String) compositeData.get("version");
        String type = (String) compositeData.get("type");
        setDisplayName(name + ";version=" + version + ";type=" + type);
    }
}
