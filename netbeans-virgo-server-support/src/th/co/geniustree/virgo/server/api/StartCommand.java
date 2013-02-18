/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server.api;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.netbeans.api.extexecution.ExternalProcessBuilder;
import th.co.geniustree.virgo.server.JmxConnectorHelper;
import th.co.geniustree.virgo.server.VirgoServerInstanceImplementation;

/**
 *
 * @author Pramoth Suwanpech <pramoth@geniustree.co.th>
 */
public class StartCommand {

    private final VirgoServerInstanceImplementation instance;

    public StartCommand(VirgoServerInstanceImplementation instance) {
        this.instance = instance;
    }

    public void start(boolean clean) {
        instance.starting();
        String virgoRoot = (String) instance.getAttr().get(Constants.VIRGO_ROOT);
        if (virgoRoot != null) {
            File virgoBinaryFolder = new File(virgoRoot, "bin");
            if (virgoBinaryFolder.exists()) {
                doStart(virgoBinaryFolder, clean);
                checkServerStatus();
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Virgo root not exist.");
            }
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Please config virgo root.");
        }
    }

    public JMXConnector startAndWait(boolean clean) {
        instance.starting();
        String virgoRoot = (String) instance.getAttr().get(Constants.VIRGO_ROOT);
        if (virgoRoot != null) {
            File virgoBinaryFolder = new File(virgoRoot, "bin");
            if (virgoBinaryFolder.exists()) {
                doStart(virgoBinaryFolder, clean);
                return checkServerStatusAndWait();
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Virgo root not exist.");
            }
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Please config virgo root.");
        }
        return null;
    }

    private void checkServerStatus() {
        Executors.newCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(2000);
                        JMXConnector createConnector = JmxConnectorHelper.createConnector(instance.getAttr());
                        MBeanServerConnection mBeanServerConnection = createConnector.getMBeanServerConnection();
                        Object attribute = mBeanServerConnection.getAttribute(new ObjectName("org.eclipse.virgo.kernel:type=ArtifactModel,artifact-type=bundle,name=org.eclipse.virgo.management.console,version=3.6.0.RELEASE,region=org.eclipse.virgo.region.user"), "State");
                        if ("ACTIVE".equals(attribute)) {
                            instance.started();
                            JmxConnectorHelper.silentClose(createConnector);
                            break;
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
                    }
                }
            }
        });
    }

    private JMXConnector checkServerStatusAndWait() {
        Future<JMXConnector> future = Executors.newCachedThreadPool().submit(new Callable<JMXConnector>() {
            @Override
            public JMXConnector call() {
                while (true) {
                    JMXConnector createConnector = null;
                    try {
                        Thread.sleep(2000);
                        createConnector = JmxConnectorHelper.createConnector(instance.getAttr());
                        MBeanServerConnection mBeanServerConnection = createConnector.getMBeanServerConnection();
                        Object attribute = mBeanServerConnection.getAttribute(new ObjectName("org.eclipse.virgo.kernel:type=ArtifactModel,artifact-type=bundle,name=org.eclipse.virgo.management.console,version=3.6.0.RELEASE,region=org.eclipse.virgo.region.user"), "State");
                        if ("ACTIVE".equals(attribute)) {
                            instance.started();
                            return createConnector;
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Can't call JMX reason = {0}",ex.getMessage());
                        JmxConnectorHelper.silentClose(createConnector);
                    }
                }
            }
        });
        try {
            return future.get(120, TimeUnit.SECONDS);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Can't start Virgo.", ex);
            return null;
        }
    }

    private void doStart(File virgoBinaryFolder, boolean clean) {
        final ExecutionDescriptor descriptor = new ExecutionDescriptor().frontWindow(true).controllable(true);
        ExternalProcessBuilder processBuilder = new ExternalProcessBuilder("cmd.exe")
                .addArgument("/c")
                .addArgument("startup.bat")
                .workingDirectory(virgoBinaryFolder);
        if (clean) {
            processBuilder = processBuilder.addArgument("-clean");
        }
        final ExecutionService service = ExecutionService.newService(processBuilder, descriptor, "Virgo");
        Future<Integer> task = service.run();
    }
}
