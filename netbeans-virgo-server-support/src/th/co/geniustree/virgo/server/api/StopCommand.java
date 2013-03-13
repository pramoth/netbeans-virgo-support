/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server.api;

import java.io.File;
import org.netbeans.api.extexecution.ExternalProcessBuilder;
import org.netbeans.api.progress.ProgressUtils;
import th.co.geniustree.virgo.server.VirgoServerInstanceImplementation;

/**
 *
 * @author pramoth
 */
public class StopCommand {

    private final VirgoServerInstanceImplementation instance;

    public StopCommand(VirgoServerInstanceImplementation instance) {
        this.instance = instance;
    }

    public void stop() {
        instance.stoping();
        String virgoRoot = (String) instance.getAttr().get(Constants.VIRGO_ROOT);
        if (virgoRoot != null) {
            File virgoBinaryFolder = new File(virgoRoot, "bin");
            if (virgoBinaryFolder.exists()) {
                final ExternalProcessBuilder processBuilder = new ExternalProcessBuilder("cmd.exe")
                        .addArgument("/c")
                        .addArgument("shutdown.bat")
                        .workingDirectory(virgoBinaryFolder);

                ProgressUtils.showProgressDialogAndRun(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            processBuilder.call().waitFor();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }finally{
                            instance.stoped();
                        }
                    }
                }, "Stop virgo.");
            } else {
                System.err.println("Virgo root not exist.");
            }
        } else {
            System.err.println("Please config virgo root.");
        }
    }
}
