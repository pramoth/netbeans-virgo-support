/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.maven;

import java.io.File;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import th.co.geniustree.virgo.server.api.Deployer;

@ActionID(category = "Project", id = "th.co.geniustree.virgo.maven.DeployAction")
@ActionRegistration(displayName = "#CTL_DeployAction")
@ActionReference(path = "Actions/Virgo/Deploy")
@Messages("CTL_DeployAction=Deploy")
public final class DeployAction extends DeployActionBase {

    public DeployAction(Project context) {
        super(context);
    }

    @Override
    public  void doOperation(Deployer deployer, File finalFile,String synbolicName, String bundleVersion, boolean recover)throws Exception {
            deployer.deploy(finalFile, recover);
    }
}
