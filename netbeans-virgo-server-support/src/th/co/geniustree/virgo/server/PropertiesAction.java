/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.netbeans.api.server.CommonServerUIs;
import org.netbeans.api.server.ServerInstance;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import th.co.geniustree.virgo.server.api.Constants;

@ActionID(category = "Server", id = "th.co.geniustree.virgo.server.PropertiesAction")
@ActionRegistration(displayName = "#CTL_PropertiesAction")
@ActionReference(path = Constants.ACTION_VERGO_SERVER, position = 10000)
@Messages("CTL_PropertiesAction=Properties")
public final class PropertiesAction implements ActionListener {

    private final ServerInstance context;

    public PropertiesAction(ServerInstance context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        CommonServerUIs.showCustomizer(context);
    }
}
