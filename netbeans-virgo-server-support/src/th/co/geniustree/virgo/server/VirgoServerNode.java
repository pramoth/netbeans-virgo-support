/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.virgo.server;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;

/**
 *
 * @author Pramoth Suwanpech <pramoth@geniustree.co.th>
 */
public class VirgoServerNode extends AbstractNode{

    public VirgoServerNode() {
        super(Children.LEAF);
        setIconBaseWithExtension("th/co/geniustree/virgo/server/virgo.gif");
        setDisplayName("Eclipse Virgo 3.6");
    }

}
