/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.virgo.server.wizard;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.InstantiatingIterator;

/**
 *
 * @author Pramoth Suwanpech <pramoth@geniustree.co.th>
 */
public class VirgoWizardIterator implements InstantiatingIterator{
private VirgoWizardPanel1 panel;
    @Override
    public Set instantiate() throws IOException {
        return Collections.EMPTY_SET;
    }

    @Override
    public void initialize(WizardDescriptor wizard) {
        
    }

    @Override
    public void uninitialize(WizardDescriptor wizard) {
        
    }

    @Override
    public WizardDescriptor.Panel current() {
        if(panel==null){
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
        
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
        
    }

}
