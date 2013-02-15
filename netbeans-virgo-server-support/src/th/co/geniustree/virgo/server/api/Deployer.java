/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server.api;

import java.io.File;
import java.io.IOException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import th.co.geniustree.virgo.server.JmxConnectorHelper;

/**
 *
 * @author pramoth
 */
public class Deployer {

    private final VirgoServerAttributes attr;

    public Deployer(VirgoServerAttributes attr) {
        this.attr = attr;
    }

    public void deploy(File file, boolean whatIsName) throws Exception {
        System.out.println("call deploy to path " + file.toURI());
        String virgoRoot = (String) attr.get(Constants.VIRGO_ROOT);
        Integer port = (Integer) attr.get(Constants.JMX_PORT);
        String user = (String) attr.get(Constants.USERNAME);
        String password = (String) attr.get(Constants.PASSWORD);
        JMXConnector connector = JmxConnectorHelper.createConnector(virgoRoot, port, user, password);
        MBeanServerConnection mBeanServerConnection = connector.getMBeanServerConnection();
        ObjectName name = new ObjectName(Constants.MBEAN_DEPLOYER);
        Object[] params = {file.toURI().toString(), true};
        System.out.println("deploy => " + file.toURI().toString());
        String[] signature = {"java.lang.String", "boolean"};
        // invoke the deploy method of the Deployer MBean
        mBeanServerConnection.invoke(name, "deploy", params, signature);
    }

    public void undeploy() {
    }

    public void refresh() {
    }
}
