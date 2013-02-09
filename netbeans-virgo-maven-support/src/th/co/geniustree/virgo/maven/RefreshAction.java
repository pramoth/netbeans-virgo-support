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

@ActionID(category = "Project",id = "th.co.geniustree.virgo.maven.RefreshAction")
@ActionRegistration(displayName = "#CTL_RefreshAction")
@ActionReference(path = "Actions/Virgo/Refresh")
@Messages("CTL_RefreshAction=Refresh")
public final class RefreshAction implements ActionListener {

    private final Project context;

    public RefreshAction(Project context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        System.out.println("refresh"+context);
    }
}
