/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server;

import th.co.geniustree.virgo.server.node.VirgoServerNode;
import javax.swing.Action;

/**
 *
 * @author pramoth
 */
public class VirgoServerBasicNode extends VirgoServerNode {

    public VirgoServerBasicNode(VirgoServerInstanceImplementation instance) {
        super(instance);
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[0];
    }
}
