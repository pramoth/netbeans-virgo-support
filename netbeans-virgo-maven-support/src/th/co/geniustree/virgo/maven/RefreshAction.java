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

@ActionID(category = "Project", id = "th.co.geniustree.virgo.maven.RefreshAction")
@ActionRegistration(displayName = "#CTL_RefreshAction")
@ActionReference(path = "Actions/Virgo/Refresh")
@Messages("CTL_RefreshAction=Refresh")
public final class RefreshAction extends DeployActionBase {

    public RefreshAction(Project context) {
        super(context);
    }

    @Override
    public void doOperation(Deployer deployer, File finalFile,String synbolicName, String bundleVersion, boolean recover) throws Exception {
        deployer.refresh(finalFile, bundleVersion);
    }
}
