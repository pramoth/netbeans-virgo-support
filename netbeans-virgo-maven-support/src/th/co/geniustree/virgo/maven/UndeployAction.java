/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.maven;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import th.co.geniustree.virgo.server.api.Deployer;

/**
 *
 * @author Pramoth Suwanpech <pramoth@geniustree.co.th>
 */

@ActionID(category = "Project",id = "th.co.geniustree.virgo.maven.UndeployAction")
@ActionRegistration(displayName = "#CTL_UndeployAction")
@ActionReference(path = "Actions/Virgo/Undeploy", position = 0)
@Messages("CTL_UndeployAction=Undeploy")
public final class UndeployAction extends DeployActionBase  {

    public UndeployAction(Project context) {
        super(context);
    }

    @Override
    public void doOperation(Deployer deployer, File finalFile,String symbolicName, String bundleVersion, boolean recover) throws Exception {
        deployer.undeploy(symbolicName, bundleVersion);
    }
}
