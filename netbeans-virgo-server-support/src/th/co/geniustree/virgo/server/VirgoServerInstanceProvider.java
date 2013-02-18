/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import th.co.geniustree.virgo.server.api.Constants;
import java.io.IOException;
import java.util.ArrayList;
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
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import th.co.geniustree.virgo.server.api.StopCommand;
import th.co.geniustree.virgo.server.api.VirgoServerAttributes;

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

    public VirgoServerInstanceProvider() {
        FileObject serverConfigRoot = FileUtil.getConfigFile("Servers");
        try {
            virgoConfigRoot = FileUtil.createFolder(serverConfigRoot, "Virgo");
            FileObject[] childrens = virgoConfigRoot.getChildren();
            instances.clear();
            for (FileObject children : childrens) {
                createServerInstance(children);
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public List<ServerInstance> getInstances() {
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
            instanceFile.setAttribute(Constants.JMX_PORT, param.get(Constants.JMX_PORT));
            instanceFile.setAttribute(Constants.USERNAME, param.get(Constants.USERNAME));
            instanceFile.setAttribute(Constants.PASSWORD, param.get(Constants.PASSWORD));
            createServerInstance(instanceFile);
            changeSupport.fireChange();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private void createServerInstance(FileObject instanceFile) {
        VirgoServerAttributes attr = new VirgoServerAttributes();
        attr.put(Constants.INSTANCE_FILE_NAME, instanceFile.getName());
        attr.put(Constants.VIRGO_ROOT, instanceFile.getAttribute(Constants.VIRGO_ROOT));
        attr.put(Constants.DISPLAY_NAME, instanceFile.getAttribute(Constants.DISPLAY_NAME));
        attr.put(Constants.JMX_PORT, instanceFile.getAttribute(Constants.JMX_PORT));
        attr.put(Constants.USERNAME, instanceFile.getAttribute(Constants.USERNAME));
        attr.put(Constants.PASSWORD, instanceFile.getAttribute(Constants.PASSWORD));
        VirgoServerInstanceImplementation virgoServerInstanceImplementation = new VirgoServerInstanceImplementation(attr, Constants.VIRGO_SERVER_NAME, (String) attr.get(Constants.DISPLAY_NAME), true);
        ServerInstance instance = ServerInstanceFactory.createServerInstance(virgoServerInstanceImplementation);
        virgoServerInstanceImplementation.addServerInstanceToLookup(instance);
        instances.add(instance);
    }

    public void remove(ServerInstance instance) {
        VirgoServerAttributes attr = instance.getLookup().lookup(VirgoServerAttributes.class);
        if (attr != null) {
            FileObject fileObject = virgoConfigRoot.getFileObject((String) attr.get(Constants.INSTANCE_FILE_NAME));
            try {
                fileObject.delete();
                instances.remove(instance);
                changeSupport.fireChange();
                StopCommand stopCommand = instance.getLookup().lookup(StopCommand.class);
                if (stopCommand != null) {
                    stopCommand.stop();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }
}
