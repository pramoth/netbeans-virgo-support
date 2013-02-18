/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server.api;

import java.awt.EventQueue;
import org.netbeans.api.server.CommonServerUIs;
import org.netbeans.spi.server.ServerInstanceProvider;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author pramoth
 */
public class ServerInstanceProviderUtils {

    public static ServerInstanceProvider getVirgoServerInstanceProvider() {
        Lookup forPath = Lookups.forPath(Constants.VERGO_SERVER_REGISTER_PATH);
        ServerInstanceProvider virgoProvider = forPath.lookup(ServerInstanceProvider.class);
        return virgoProvider;
    }

    public static void openWizard() {
        if (EventQueue.isDispatchThread()) {
            CommonServerUIs.showAddServerInstanceWizard();
        } else {
            try {
                EventQueue.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        CommonServerUIs.showAddServerInstanceWizard();
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
