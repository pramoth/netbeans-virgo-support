/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import th.co.geniustree.virgo.server.api.Constants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import th.co.geniustree.virgo.server.api.StartCommand;

/**
 *
 * @author Pramoth Suwanpech <pramoth@geniustree.co.th>
 */

@ActionID(category = "Server", id = "th.co.geniustree.virgo.server.StartVirgoAction")
@ActionRegistration(displayName = "#CTL_StartVirgoAction")
@ActionReference(path = Constants.ACTION_VERGO_SERVER ,position = 1000)
@Messages("CTL_StartVirgoAction=Start")
public final class StartVirgoAction implements ActionListener {
    private  StartCommand instance;

    public StartVirgoAction(StartCommand instance) {
        this.instance = instance;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        instance.start(false);
    }
}
