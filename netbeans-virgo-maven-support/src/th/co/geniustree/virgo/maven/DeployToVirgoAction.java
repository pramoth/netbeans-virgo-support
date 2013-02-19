/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.maven;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.netbeans.api.project.Project;
import org.netbeans.modules.maven.api.NbMavenProject;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.DynamicMenuContent;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.util.actions.NodeAction;

@ActionID(category = "Project", id = "virgo.server.support.DeployToVirgoAction")
@ActionRegistration(displayName = "#CTL_DeployToVirgoAction")
@ActionReference(path = "Projects/Actions/Virgo")
@Messages("CTL_DeployToVirgoAction=Virgo")
public final class DeployToVirgoAction extends NodeAction {

    private JMenu root = new JMenu("Virgo");
    private JMenuItem deploy = new JMenuItem();
    private JMenuItem undeploy = new JMenuItem();
    private JMenuItem refresh = new JMenuItem();

    public DeployToVirgoAction() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        assert false;
    }

    @Override
    public JMenuItem getPopupPresenter() {
        if (root.getMenuComponents().length == 0) {
            root.add(deploy);
            deploy.setAction(Utilities.actionsForPath("Actions/Virgo/Deploy").get(0));
            root.add(undeploy);
            undeploy.setAction(Utilities.actionsForPath("Actions/Virgo/Undeploy").get(0));
            root.add(refresh);
            refresh.setAction(Utilities.actionsForPath("Actions/Virgo/Refresh").get(0));
        }
        return root;
    }

    @Override
    protected void performAction(Node[] activatedNodes) {
        Project p = activatedNodes[0].getLookup().lookup(Project.class);
        Collection<? extends Object> lookupAll = p.getLookup().lookupAll(Object.class);
        for (Object x : lookupAll) {
            System.out.println(x.getClass());
        }
    }

    @Override
    protected boolean enable(Node[] activatedNodes) {
        if (activatedNodes.length == 1) {
            Project p = activatedNodes[0].getLookup().lookup(Project.class);
            boolean visible = false;
            if (p != null) {
                NbMavenProject mvnProject = p.getLookup().lookup(NbMavenProject.class);
                visible = mvnProject != null;
                root.setVisible(visible);
            }
            return visible;

        } else {
            root.setVisible(false);
            return false;
        }
    }

    @Override
    public String getName() {
        return "XXX";
    }

    @Override
    public HelpCtx getHelpCtx() {
        return null;
    }

    private static final class ContextAction extends AbstractAction {

        private final Project p;

        public ContextAction(Lookup context) {
            p = context.lookup(Project.class);
            NbMavenProject mvnProject = p.getLookup().lookup(NbMavenProject.class);
            setEnabled(mvnProject != null);
            putValue(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
            putValue(NAME, "&Deploy to virgo");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Collection<? extends Object> lookupAll = p.getLookup().lookupAll(Object.class);
            for (Object x : lookupAll) {
                System.out.println(x.getClass());
            }
        }
    }
}
