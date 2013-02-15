/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server.api;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.netbeans.api.extexecution.ExternalProcessBuilder;
import org.openide.util.Exceptions;
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
                checkServerStatus();
            } else {
                System.err.println("Virgo root not exist.");
            }
        } else {
            System.err.println("Please config virgo root.");
        }
    }

    private void checkServerStatus() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                String virgoRoot = (String) instance.getAttr().get(Constants.VIRGO_ROOT);
                Integer port = (Integer) instance.getAttr().get(Constants.JMX_PORT);
                String user = (String) instance.getAttr().get(Constants.USERNAME);
                String password = (String) instance.getAttr().get(Constants.PASSWORD);
                while (true) {
                    try {
                        Thread.sleep(2000);
                        JMXConnector createConnector = JmxConnectorHelper.createConnector(virgoRoot, port, user, password);
                        MBeanServerConnection mBeanServerConnection = createConnector.getMBeanServerConnection();
                        Object attribute = mBeanServerConnection.getAttribute(new ObjectName("org.eclipse.virgo.kernel:type=KernelStatus"), "Status");
                        if ("STARTED".equals(attribute)) {
                            instance.started();
                            silentClose(createConnector);
                            break;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            private void silentClose(JMXConnector createConnector) {
                try {
                    createConnector.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
