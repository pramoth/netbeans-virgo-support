/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server.node;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.remote.JMXConnector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import th.co.geniustree.virgo.server.JmxConnectorHelper;
import th.co.geniustree.virgo.server.VirgoServerInstanceImplementation;
import th.co.geniustree.virgo.server.api.StopCommand;

/**
 *
 * @author pramoth
 */
public class RepositoryChildFactory extends ChildFactory<CompositeData> {

    private final VirgoServerInstanceImplementation instance;
    private final ObjectName objectName;
    private final Lookup.Result<StopCommand> lookupResult;
    private boolean stop = false;

    RepositoryChildFactory(ObjectName objectName, VirgoServerInstanceImplementation instance) {
        this.instance = instance;
        this.objectName = objectName;
        lookupResult = instance.getLookup().lookupResult(StopCommand.class);
        lookupResult.addLookupListener(new LookupListener() {
            @Override
            public void resultChanged(LookupEvent ev) {
                if (lookupResult.allInstances().isEmpty()) {
                    stop = true;
                } else {
                    stop = false;
                }
                refresh(false);
            }
        });
    }

    @Override
    protected boolean createKeys(List<CompositeData> toPopulate) {
        toPopulate.clear();
        if (stop) {
            return true;
        }
        JMXConnector createConnector = null;
        try {
            createConnector = JmxConnectorHelper.createConnector(instance.getAttr());
            MBeanServerConnection mBeanServerConnection = createConnector.getMBeanServerConnection();
            CompositeData[] attr = (CompositeData[]) mBeanServerConnection.getAttribute(objectName, "AllArtifactDescriptorSummaries");
            toPopulate.addAll(Arrays.asList(attr));
        } catch (Exception ex) {
            ex.printStackTrace();
            stop = true;
        } finally {
            JmxConnectorHelper.silentClose(createConnector);
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(CompositeData key) {
        return new RepositoryChildNode(instance,key);
    }
}
