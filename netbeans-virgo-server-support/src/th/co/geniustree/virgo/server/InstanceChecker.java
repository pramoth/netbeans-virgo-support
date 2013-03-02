/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import th.co.geniustree.virgo.server.api.Constants;

/**
 *
 * @author pramoth
 */
public class InstanceChecker implements Runnable {

    private final VirgoServerInstanceImplementation instance;
    private int tryNumber;

    public InstanceChecker(VirgoServerInstanceImplementation instance) {
        this(instance, 1);
    }

    public InstanceChecker(VirgoServerInstanceImplementation instance, int tryNumber) {
        this.instance = instance;
        this.tryNumber = tryNumber;
    }

    @Override
    public void run() {
        while (true) {
            JMXConnector createConnector = null;
            try {
                Thread.sleep(3000);
                if (tryNumber < 1) {
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Can't check server status.");
                    instance.stoped();
                    break;
                }
                createConnector = JmxConnectorHelper.createConnector(instance.getAttr());
                MBeanServerConnection mBeanServerConnection = createConnector.getMBeanServerConnection();
                Object mBeaninstance = mBeanServerConnection.getObjectInstance(new ObjectName(Constants.MBEAN_DEPLOYER));
                if (mBeaninstance != null) {
                    instance.started();
                    JmxConnectorHelper.silentClose(createConnector);
                    break;
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Can't call JMX reason = {0} Thread={1}", new Object[]{ex.getMessage(), Thread.currentThread()});
                JmxConnectorHelper.silentClose(createConnector);
            } finally {
                tryNumber--;
            }
        }
    }
}
