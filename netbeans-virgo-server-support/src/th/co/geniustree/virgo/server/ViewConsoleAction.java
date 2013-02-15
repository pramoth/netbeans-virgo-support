/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Server",
        id = "th.co.geniustree.virgo.server.ViewConsoleAction")
@ActionRegistration(
        displayName = "#CTL_ViewConsoleAction")
@Messages("CTL_ViewConsoleAction=View Admin Console")
public final class ViewConsoleAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
    }
}
