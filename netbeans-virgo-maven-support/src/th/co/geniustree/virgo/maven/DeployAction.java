/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.maven;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;
import org.apache.maven.project.MavenProject;
import org.netbeans.api.project.Project;
import org.netbeans.modules.maven.api.NbMavenProject;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "Project", id = "th.co.geniustree.virgo.maven.DeployAction")
@ActionRegistration(displayName = "#CTL_DeployAction")
@ActionReference(path = "Actions/Virgo/Deploy")
@Messages("CTL_DeployAction=Deploy")
public final class DeployAction implements ActionListener {

    private final Project context;
    private File virgoRoot = new File("G:/dev/virgo-tomcat-server-3.6.0.RELEASE");
    private File truststoreLocation;

    public DeployAction(Project context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        try {
            deploy();
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private void deploy() throws IOException, MalformedObjectNameException, InstanceNotFoundException, MBeanException, ReflectionException {
        setTrustStore();
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi://localhost:9875/jndi/rmi://localhost:9875/jmxrmi");
        // define the user credentials
        Map<String, Object> envMap = new HashMap<String, Object>();
        envMap.put("jmx.remote.credentials", new String[]{"admin", "springsource"});
        envMap.put(Context.SECURITY_PRINCIPAL, "admin");
        envMap.put(Context.SECURITY_CREDENTIALS, "springsource");
        // get a connector and establish a connection
        JMXConnector connector = JMXConnectorFactory.connect(url, envMap);
        MBeanServerConnection connection = connector.getMBeanServerConnection();
        NbMavenProject nbMavenProject = context.getLookup().lookup(NbMavenProject.class);
        MavenProject mavenProject = nbMavenProject.getMavenProject();
        File outputDirectory = mavenProject.getBasedir();
        System.out.println(outputDirectory);
        String finalFileName = "target/"+mavenProject.getBuild().getFinalName()+".jar";
        File finalFile = new File(outputDirectory, finalFileName);
        ObjectName name = new ObjectName(Constants.MBEAN_DEPLOYER);
        Object[] params = {finalFile.toURI().toString(), true};
        System.out.println("deploy => "+finalFile.toURI().toString());
        String[] signature = {"java.lang.String", "boolean"};
        // invoke the deploy method of the Deployer MBean
        connection.invoke(name, "deploy", params, signature);
    }

    private void setTrustStore() throws IOException {
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
