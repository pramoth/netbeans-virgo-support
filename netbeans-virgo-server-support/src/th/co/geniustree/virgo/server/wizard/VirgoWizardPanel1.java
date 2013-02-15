/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server.wizard;

import javax.swing.event.ChangeListener;
import org.netbeans.api.server.properties.InstanceProperties;
import org.netbeans.api.server.properties.InstancePropertiesManager;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.ChangeSupport;
import org.openide.util.HelpCtx;
import th.co.geniustree.virgo.server.api.Constants;

public class VirgoWizardPanel1 implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    public static final String NETBEANS_VIRGO_SERVER = "netbeans.virgo.server";
    private boolean valid = true;
    private ChangeSupport changSupport = new ChangeSupport(this);
    /**
     * The visual component that displays this panel. If you need to access the component from this class, just use getComponent().
     */
    private VirgoVisualPanel1 component;
    private Integer jmxPort;
    private String password;

    public VirgoWizardPanel1() {
        if (component == null) {
            component = new VirgoVisualPanel1();
        }
    }

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public VirgoVisualPanel1 getComponent() {

        return component;
    }

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx("help.key.here");
    }

    @Override
    public boolean isValid() {

        return valid;
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
    }

    public void setValid(boolean valid) {
        changSupport.fireChange();
        this.valid = valid;
    }

    @Override
    public void addChangeListener(ChangeListener l) {
        changSupport.addChangeListener(l);
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
        changSupport.removeChangeListener(l);
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        // use wiz.getProperty to retrieve previous panel state
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        if (isValid()) {
            if (component.getVirgoRootFile() != null) {
                wiz.putProperty(Constants.VIRGO_ROOT, component.getVirgoRootFile().getAbsolutePath());
            }
            wiz.putProperty(Constants.JMX_PORT, jmxPort);
            wiz.putProperty(Constants.USERNAME, component.getUserName().getText());
            wiz.putProperty(Constants.PASSWORD, password);
        }
    }

    @Override
    public void validate() throws WizardValidationException {
        if (component.getVirgoRootFile() == null) {
            setValid(false);
            throw new WizardValidationException(null, "Must specify Virgo installation root.", null);
        }
        try {
            jmxPort = Integer.parseInt(component.getJmxPort().getText());
        } catch (Exception e) {
            setValid(false);
            throw new WizardValidationException(null, "JMX port incorrect.", null);
        }
        if (component.getUserName().getText() == null || component.getUserName().getText().isEmpty()) {
            setValid(false);
            throw new WizardValidationException(component.getUserName(), "Please specify username.", null);
        }
        password = new String(component.getPassword().getPassword());
        if (password.isEmpty()) {
            setValid(false);
            throw new WizardValidationException(null, "Please specify password.", null);
        }
        setValid(true);
    }
}
