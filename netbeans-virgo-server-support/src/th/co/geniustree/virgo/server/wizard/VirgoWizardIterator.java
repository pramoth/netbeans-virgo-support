/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server.wizard;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.InstantiatingIterator;
import org.openide.util.ChangeSupport;
import th.co.geniustree.virgo.server.api.Constants;
import th.co.geniustree.virgo.server.VirgoServerInstanceProvider;
import th.co.geniustree.virgo.server.api.ServerInstanceProviderUtils;

/**
 *
 * @author Pramoth Suwanpech <pramoth@geniustree.co.th>
 */
public class VirgoWizardIterator implements InstantiatingIterator {

    private VirgoWizardPanel1 panel;
    private Set instances = new HashSet();
    private ChangeSupport changeSupport = new ChangeSupport(this);
    private WizardDescriptor wizard;

    @Override
    public Set instantiate() throws IOException {
        if (wizard.getValue() == WizardDescriptor.FINISH_OPTION) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put(Constants.DISPLAY_NAME, (String) wizard.getProperty("ServInstWizard_displayName"));
            param.put(Constants.VIRGO_ROOT, (String) wizard.getProperty(Constants.VIRGO_ROOT));
            param.put(Constants.JMX_PORT, (Integer) wizard.getProperty(Constants.JMX_PORT));
            param.put(Constants.USERNAME, (String) wizard.getProperty(Constants.USERNAME));
            param.put(Constants.PASSWORD, (String) wizard.getProperty(Constants.PASSWORD));
            VirgoServerInstanceProvider virgoProvider = (VirgoServerInstanceProvider) ServerInstanceProviderUtils.getVirgoServerInstanceProvider();
            virgoProvider.addNewServer(param);
            changeSupport.fireChange();
            return instances;
        }
        return Collections.EMPTY_SET;
    }

    @Override
    public void initialize(WizardDescriptor wizard) {
        this.wizard = wizard;
    }

    @Override
    public void uninitialize(WizardDescriptor wizard) {
    }

    @Override
    public WizardDescriptor.Panel current() {
        if (panel == null) {
            panel = new VirgoWizardPanel1();
        }
        return panel;
    }

    @Override
    public String name() {
        return "Wizard Iterater name.";
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public void nextPanel() {
    }

    @Override
    public void previousPanel() {
    }

    @Override
    public void addChangeListener(ChangeListener l) {
        changeSupport.addChangeListener(l);
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
        changeSupport.removeChangeListener(l);
    }
}
