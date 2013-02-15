/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.maven;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Pramoth Suwanpech <pramoth@geniustree.co.th>
 */

@ActionID(category = "Project",id = "th.co.geniustree.virgo.maven.UndeployAction")
@ActionRegistration(displayName = "#CTL_UndeployAction")
@ActionReference(path = "Actions/Virgo/Undeploy", position = 0)
@Messages("CTL_UndeployAction=Undeploy")
public final class UndeployAction implements ActionListener {

    private final Project context;

    public UndeployAction(Project context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        // TODO use context
    }
}
