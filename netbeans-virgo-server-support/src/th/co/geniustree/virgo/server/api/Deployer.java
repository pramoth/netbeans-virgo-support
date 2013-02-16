/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server.api;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.swing.SwingUtilities;
import th.co.geniustree.virgo.server.JmxConnectorHelper;
import th.co.geniustree.virgo.server.VirgoServerInstanceImplementation;

/**
 *
 * @author pramoth
 */
public class Deployer {

    private final VirgoServerInstanceImplementation instance;

    public Deployer(VirgoServerInstanceImplementation instance) {
        this.instance = instance;
    }

    public void deploy(File file, boolean whatIsName) throws Exception {
        if (SwingUtilities.isEventDispatchThread()) {
            throw new IllegalStateException("Ca'nt call in EDT.");
        }
        System.out.println("call deploy to path " + file.toURI());
        JMXConnector connector = null;
        try {
            connector = JmxConnectorHelper.createConnector(instance.getAttr());
        } catch (IOException iOException) {
            StartCommand startCommand = instance.getLookup().lookup(StartCommand.class);
            if (startCommand != null) {
                connector = startCommand.startAndWait(true);
            }
        }
        if (connector != null) {
            try {
                MBeanServerConnection mBeanServerConnection = connector.getMBeanServerConnection();
                ObjectName name = new ObjectName(Constants.MBEAN_DEPLOYER);
                Object[] params = {file.toURI().toString(), true};
                System.out.println("deploy => " + file.toURI().toString());
                String[] signature = {"java.lang.String", "boolean"};
                // invoke the deploy method of the Deployer MBean
                mBeanServerConnection.invoke(name, "deploy", params, signature);
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Can't connect Virgo JMX.",ex);
                instance.stoped();
            }finally{
                JmxConnectorHelper.silentClose(connector);
            }
        }else{
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Can't connect Virgo JMX.");
        }
    }

    public void undeploy() {
    }

    public void refresh() {
    }
}
