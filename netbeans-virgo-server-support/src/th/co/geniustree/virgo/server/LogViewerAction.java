/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Executors;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import th.co.geniustree.virgo.server.api.Constants;
import th.co.geniustree.virgo.server.api.VirgoServerAttributes;

@ActionID(category = "Server", id = "th.co.geniustree.virgo.server.LogViewerAction")
@ActionRegistration(displayName = "#CTL_LogViewerAction")
@ActionReference(path = Constants.ACTION_VERGO_SERVER, position = 6000)
@Messages("CTL_LogViewerAction=View server log file.")
public final class LogViewerAction implements ActionListener {

    private final VirgoServerAttributes context;
    private final File virgoServerLogfile;

    public LogViewerAction(VirgoServerAttributes context) {
        this.context = context;
        String virgoRoot = (String) context.get(Constants.VIRGO_ROOT);
        virgoServerLogfile = new File(virgoRoot, "serviceability/logs/virgo-server/log.log");
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        final InputOutput io = IOProvider.getDefault().getIO("virgo-server/log.log", false);
        io.select();
        Executors.newCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                BufferedReader reader = null;
                try {
                    if (!virgoServerLogfile.exists()) {
                        return;
                    }
                    reader = new BufferedReader(new InputStreamReader(Files.newInputStream(virgoServerLogfile.toPath(), StandardOpenOption.READ)));
                    while (true) {
                        if (io.isClosed()) {
                            System.out.println("stop as expected...............");
                            break;
                        }
                        String line = reader.readLine();
                        if (line == null) {
                            Thread.sleep(1000);
                            continue;
                        }
                        io.getOut().append(line + "\r\n");
                    }
                } catch (Exception ex) {
                    Exceptions.printStackTrace(ex);
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                    }
                }
            }
        });

    }
}
