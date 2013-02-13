/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Pramoth Suwanpech <pramoth@geniustree.co.th>
 */

@ActionID(category = "Server",id = "th.co.geniustree.virgo.server.StartCleanAction")
@ActionRegistration(displayName = "#CTL_StartCleanAction")
@ActionReference(path = Constants.ACTION_VERGO_SERVER,position = 2000)
@Messages("CTL_StartCleanAction=Start -clean")
public final class StartCleanAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
    }
}
