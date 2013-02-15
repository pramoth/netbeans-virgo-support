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
import th.co.geniustree.virgo.server.api.StopCommand;

/**
 *
 * @author Pramoth Suwanpech <pramoth@geniustree.co.th>
 */
@ActionID(category = "Server", id = "th.co.geniustree.virgo.server.StopVirgoAction")
@ActionRegistration(displayName = "#CTL_StopVirgoAction")
@ActionReference(path = Constants.ACTION_VERGO_SERVER, position = 3000)
@Messages("CTL_StopVirgoAction=Stop")
public final class StopVirgoAction implements ActionListener {

    private StopCommand instance;

    public StopVirgoAction(StopCommand instance) {
        this.instance = instance;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        instance.stop();
    }
}
