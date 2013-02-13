/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import java.io.File;
import java.util.concurrent.Future;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.netbeans.api.extexecution.ExternalProcessBuilder;
import org.netbeans.api.server.properties.InstanceProperties;
import org.netbeans.api.server.properties.InstancePropertiesManager;
import static th.co.geniustree.virgo.server.wizard.VirgoWizardPanel1.NETBEANS_VIRGO_SERVER;

/**
 *
 * @author Pramoth Suwanpech <pramoth@geniustree.co.th>
 */
public class StartCommand {
    public void start(String[] args){
        InstancePropertiesManager manager = InstancePropertiesManager.getInstance();
        InstanceProperties virgoRootProp = manager.createProperties(NETBEANS_VIRGO_SERVER);
        String virgoRoot = "C:\\virgo-tomcat-server-3.6.0.RELEASE\\bin";//virgoRootProp.getString("virgoRoot",null);
        
        if (virgoRoot != null) {
            File virgoRootFile = new File(virgoRoot);
            if(virgoRootFile.exists()){
            ExecutionDescriptor descriptor = new ExecutionDescriptor().frontWindow(true).controllable(true);
            ExternalProcessBuilder processBuilder = new ExternalProcessBuilder("cmd.exe")
                    .addArgument("/c")
                    .addArgument("startup.bat")
                    .workingDirectory(virgoRootFile);
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
