/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.virgo.server;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeListener;
import org.netbeans.api.server.ServerInstance;
import org.netbeans.spi.server.ServerInstanceFactory;
import org.netbeans.spi.server.ServerInstanceProvider;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Pramoth Suwanpech <pramoth@geniustree.co.th>
 */
@ServiceProvider(service=ServerInstanceProvider.class, path="Servers")
public class VirgoServerInstanceProvider implements ServerInstanceProvider {

    @Override
    public List<ServerInstance> getInstances() {
        List<ServerInstance> instances = new ArrayList<ServerInstance>();
        ServerInstance instance = ServerInstanceFactory.createServerInstance(new VirgoServerInstanceImplementation(this, "ServerName", "Instancename", true));
        instances.add(instance);
        return instances;
    }

    @Override
    public void addChangeListener(ChangeListener cl) {
        
    }

    @Override
    public void removeChangeListener(ChangeListener cl) {
        
    }

}
