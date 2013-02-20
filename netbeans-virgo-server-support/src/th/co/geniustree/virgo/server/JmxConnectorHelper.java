/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import java.io.File;
import java.io.IOException;
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

    public static JMXConnector createConnector(VirgoServerAttributes attr) throws IOException {
        String virgoRoot = (String) attr.get(Constants.VIRGO_ROOT);
        Integer port = (Integer) attr.get(Constants.JMX_PORT);
        String user = (String) attr.get(Constants.USERNAME);
        String password = (String) attr.get(Constants.PASSWORD);
        Logger.getLogger(JmxConnectorHelper.class.getName()).log(Level.INFO,"{0}",System.getSecurityManager());
        setTrustStore(new File(virgoRoot));
        Logger.getLogger(JmxConnectorHelper.class.getName()).log(Level.INFO, "javax.net.ssl.trustStore={0}", System.getProperty("javax.net.ssl.trustStore"));
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi://localhost:" + port + "/jndi/rmi://localhost:" + port + "/jmxrmi");
        // define the user credentials
        Map<String, Object> envMap = new HashMap<String, Object>();
        envMap.put("jmx.remote.credentials", new String[]{user, password});
        envMap.put(Context.SECURITY_PRINCIPAL, user);
        envMap.put(Context.SECURITY_CREDENTIALS, password);
        // get a connector and establish a connection
        return JMXConnectorFactory.connect(url, envMap);
    }

    private static void setTrustStore(File virgoRoot) throws IOException {
        File truststoreLocation = null;
        String trustStoreSystemProperty = System.getProperty("javax.net.ssl.trustStore");
        if (trustStoreSystemProperty == null || trustStoreSystemProperty.isEmpty() || !new File(trustStoreSystemProperty).exists() || !new File(trustStoreSystemProperty).isFile()) {
            // next check the truststore location setting
            if (truststoreLocation == null) {
                // if non of the checks before apply fall back
                truststoreLocation = new File(virgoRoot, "configuration/keystore");
                if (!truststoreLocation.exists()) {
                    truststoreLocation = new File(virgoRoot, "config/keystore");
                    if (!truststoreLocation.exists()) {
                        throw new IOException("Cannot find a keystore file");
                    }
                }
            }
            // set path to the truststore location
            System.setProperty("javax.net.ssl.trustStore", truststoreLocation.getAbsolutePath());
        }
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
