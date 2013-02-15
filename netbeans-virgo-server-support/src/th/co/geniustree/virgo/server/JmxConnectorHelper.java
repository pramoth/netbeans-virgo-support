/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;

/**
 *
 * @author pramoth
 */
public class JmxConnectorHelper {

    public static JMXConnector createConnector(String virgoRoot, Integer port, String user, String password) throws IOException {
        setTrustStore(new File(virgoRoot));
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
        if (trustStoreSystemProperty == null || !new File(trustStoreSystemProperty).exists()
                || !new File(trustStoreSystemProperty).isFile()) {
            // next check the truststore location setting
            if (truststoreLocation == null || !truststoreLocation.exists() || !truststoreLocation.isFile()) {
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
}
