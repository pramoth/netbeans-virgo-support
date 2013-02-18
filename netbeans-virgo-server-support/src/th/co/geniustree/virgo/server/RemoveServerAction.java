/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.netbeans.api.server.ServerInstance;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import th.co.geniustree.virgo.server.api.Constants;
import th.co.geniustree.virgo.server.api.ServerInstanceProviderUtils;

@ActionID(category = "Server", id = "th.co.geniustree.virgo.server.RemoveServerAction")
@ActionRegistration(displayName = "#CTL_RemoveServerAction")
@ActionReference(path = Constants.ACTION_VERGO_SERVER, position = 4000)
@Messages("CTL_RemoveServerAction=Remove")
public final class RemoveServerAction implements ActionListener {

    private final ServerInstance context;

    public RemoveServerAction(ServerInstance context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        VirgoServerInstanceProvider virgoServerInstanceProvider = (VirgoServerInstanceProvider) ServerInstanceProviderUtils.getVirgoServerInstanceProvider();
        virgoServerInstanceProvider.remove(context);
    }
}
