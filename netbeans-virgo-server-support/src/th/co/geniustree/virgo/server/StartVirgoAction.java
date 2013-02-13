/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.netbeans.api.extexecution.ExternalProcessBuilder;
import org.netbeans.api.server.ServerInstance;
import org.netbeans.api.server.properties.InstanceProperties;
import org.netbeans.api.server.properties.InstancePropertiesManager;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import static th.co.geniustree.virgo.server.wizard.VirgoWizardPanel1.*;

/**
 *
 * @author Pramoth Suwanpech <pramoth@geniustree.co.th>
 */

@ActionID(category = "Server", id = "th.co.geniustree.virgo.server.StartVirgoAction")
@ActionRegistration(displayName = "#CTL_StartVirgoAction")
@ActionReference(path = Constants.ACTION_VERGO_SERVER ,position = 1000)
@Messages("CTL_StartVirgoAction=Start")
public final class StartVirgoAction implements ActionListener {
    private final VirgoServerInstanceImplementation instance;

    public StartVirgoAction(VirgoServerInstanceImplementation instance) {
        this.instance = instance;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        Map<String, Object> props = instance.getInstanceProps();
        String virgoRoot = (String)props.get(Constants.VIRGO_ROOT);
        if (virgoRoot != null) {
            File virgoBinaryFolder = new File(virgoRoot,"bin");
            if(virgoBinaryFolder.exists()){
            ExecutionDescriptor descriptor = new ExecutionDescriptor().frontWindow(true).controllable(true);
            ExternalProcessBuilder processBuilder = new ExternalProcessBuilder("cmd.exe")
                    .addArgument("/c")
                    .addArgument("startup.bat")
                    .workingDirectory(virgoBinaryFolder);
            ExecutionService service = ExecutionService.newService(processBuilder, descriptor, "Virgo");
            Future<Integer> task = service.run();
            }else{
                System.err.println("Virgo root not exist.");
            }
        }else{
            System.err.println("Please config virgo root.");
        }

    }
}
