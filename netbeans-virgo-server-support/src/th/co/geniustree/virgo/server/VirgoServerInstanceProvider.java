/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.event.ChangeListener;
import org.netbeans.api.server.ServerInstance;
import org.netbeans.spi.server.ServerInstanceFactory;
import org.netbeans.spi.server.ServerInstanceProvider;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.ChangeSupport;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Pramoth Suwanpech <pramoth@geniustree.co.th>
 */
@ServiceProviders({
    @ServiceProvider(service = ServerInstanceProvider.class, path = "Servers"),
    @ServiceProvider(service = ServerInstanceProvider.class, path = Constants.VERGO_SERVER_REGISTER_PATH)
})
public class VirgoServerInstanceProvider implements ServerInstanceProvider {

    private ChangeSupport changeSupport = new ChangeSupport(this);
    private List<ServerInstance> instances = new ArrayList<ServerInstance>();
    private FileObject virgoConfigRoot;

    @Override
    public List<ServerInstance> getInstances() {
        FileObject serverConfigRoot = FileUtil.getConfigFile("Servers");
        try {
            virgoConfigRoot = FileUtil.createFolder(serverConfigRoot, "Virgo");
            FileObject[] childrens = virgoConfigRoot.getChildren();
            instances.clear();
            for (FileObject children : childrens) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put(Constants.VIRGO_ROOT, children.getAttribute(Constants.VIRGO_ROOT));
                param.put(Constants.DISPLAY_NAME, children.getAttribute(Constants.DISPLAY_NAME));
                param.put(Constants.JMS_PORT, children.getAttribute(Constants.JMS_PORT));
                param.put(Constants.USERNAME, children.getAttribute(Constants.USERNAME));
                param.put(Constants.PASSWORD, children.getAttribute(Constants.PASSWORD));
                ServerInstance instance = ServerInstanceFactory.createServerInstance(new VirgoServerInstanceImplementation(param, Constants.VIRGO_SERVER_NAME, (String) param.get(Constants.DISPLAY_NAME), true));
                instances.add(instance);
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return instances;
    }

    @Override
    public void addChangeListener(ChangeListener cl) {
        changeSupport.addChangeListener(cl);
    }

    @Override
    public void removeChangeListener(ChangeListener cl) {
        changeSupport.removeChangeListener(cl);
    }

    public void addNewServer(Map<String, Object> param) {
        try {
            int nextChildrensNum = virgoConfigRoot.getChildren().length + 1;
            FileObject instanceFile = virgoConfigRoot.createData("instance" + nextChildrensNum);
            instanceFile.setAttribute(Constants.VIRGO_ROOT, param.get(Constants.VIRGO_ROOT));
            instanceFile.setAttribute(Constants.DISPLAY_NAME, param.get(Constants.DISPLAY_NAME));
            instanceFile.setAttribute(Constants.JMS_PORT, param.get(Constants.JMS_PORT));
            instanceFile.setAttribute(Constants.USERNAME, param.get(Constants.USERNAME));
            instanceFile.setAttribute(Constants.PASSWORD, param.get(Constants.PASSWORD));
            ServerInstance instance = ServerInstanceFactory.createServerInstance(new VirgoServerInstanceImplementation(param, Constants.VIRGO_SERVER_NAME, (String) param.get(Constants.DISPLAY_NAME), true));
            changeSupport.fireChange();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
