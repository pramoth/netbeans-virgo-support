/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.maven;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import javax.swing.SwingWorker;
import org.apache.maven.project.MavenProject;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.progress.ProgressUtils;
import org.netbeans.api.project.Project;
import org.netbeans.api.server.ServerInstance;
import org.netbeans.modules.maven.api.NbMavenProject;
import org.netbeans.spi.server.ServerInstanceProvider;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.Lookups;
import th.co.geniustree.virgo.server.api.Deployer;

@ActionID(category = "Project", id = "th.co.geniustree.virgo.maven.DeployAction")
@ActionRegistration(displayName = "#CTL_DeployAction")
@ActionReference(path = "Actions/Virgo/Deploy")
@Messages("CTL_DeployAction=Deploy")
public final class DeployAction implements ActionListener {

    private final Project context;

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
        NbMavenProject nbMavenProject = context.getLookup().lookup(NbMavenProject.class);
        MavenProject mavenProject = nbMavenProject.getMavenProject();
        File outputDirectory = mavenProject.getBasedir();
        System.out.println(outputDirectory);
        String finalFileName = "target/" + mavenProject.getBuild().getFinalName() + ".jar";
        final File finalFile = new File(outputDirectory, finalFileName);
        Lookup forPath = Lookups.forPath(th.co.geniustree.virgo.server.api.Constants.VERGO_SERVER_REGISTER_PATH);
        ServerInstanceProvider virgoProvider = forPath.lookup(ServerInstanceProvider.class);
        if (virgoProvider != null) {
            List<ServerInstance> instances = virgoProvider.getInstances();
            if (instances.size() > 0) {
                final Deployer deployer = instances.get(0).getLookup().lookup(Deployer.class);
                try {
                    final ProgressHandle handle = ProgressHandleFactory.createHandle("Deploy "+mavenProject.getBuild().getFinalName());
                    SwingWorker worker = new SwingWorker<Object, Object>() {

                        @Override
                        protected Object doInBackground() throws Exception {
                            handle.start();
                            deployer.deploy(finalFile, true);
                            return null;
                        }

                        @Override
                        protected void done() {
                            try {
                                get();
                                handle.finish();
                            } catch (InterruptedException ex) {
                                Exceptions.printStackTrace(ex);
                            } catch (ExecutionException ex) {
                                Exceptions.printStackTrace(ex);
                            }
                        }
                        
                    };
                    worker.execute();
                } catch (Exception ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }
}
