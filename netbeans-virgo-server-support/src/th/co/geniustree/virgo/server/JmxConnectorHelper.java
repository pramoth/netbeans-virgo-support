/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;
import th.co.geniustree.virgo.server.api.Constants;
import th.co.geniustree.virgo.server.api.VirgoServerAttributes;

/**
 *
 * @author pramoth
 */
public class JmxConnectorHelper {

    public static synchronized JMXConnector createConnector(VirgoServerAttributes attr) throws Exception {
        Integer port = (Integer) attr.get(Constants.JMX_PORT);
        String user = (String) attr.get(Constants.USERNAME);
        String password = (String) attr.get(Constants.PASSWORD);
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi://localhost:" + port + "/jndi/rmi://localhost:" + port + "/jmxrmi");
        // define the user credentials
        Map<String, Object> envMap = new HashMap<String, Object>();
        envMap.put("jmx.remote.credentials", new String[]{user, password});
        envMap.put(Context.SECURITY_PRINCIPAL, user);
        envMap.put(Context.SECURITY_CREDENTIALS, password);
        // get a connector and establish a connection
        return JMXConnectorFactory.connect(url, envMap);
    }

    public static void silentClose(JMXConnector createConnector) {
        if (createConnector != null) {
            try {
                createConnector.close();
            } catch (Exception ex) {
                Logger.getLogger(JmxConnectorHelper.class.getName()).log(Level.INFO, ex.getMessage(), ex);
            }
        }
    }
}
