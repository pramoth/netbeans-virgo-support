/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server.node;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import th.co.geniustree.virgo.server.VirgoServerInstanceImplementation;

/**
 *
 * @author pramoth
 */
public class RepositoryRootNode extends AbstractNode {

    public RepositoryRootNode(VirgoServerInstanceImplementation instance, String key) {
        super(Children.create(new RepositoryNodeFactory(instance), false));
        setDisplayName(key);
    }
}
