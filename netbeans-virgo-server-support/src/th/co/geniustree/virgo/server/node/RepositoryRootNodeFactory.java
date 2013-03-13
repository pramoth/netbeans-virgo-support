/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server.node;

import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import th.co.geniustree.virgo.server.VirgoServerInstanceImplementation;

/**
 *
 * @author pramoth
 */
public class RepositoryRootNodeFactory extends ChildFactory<String> {
    private final VirgoServerInstanceImplementation instance;

    RepositoryRootNodeFactory(VirgoServerInstanceImplementation instance) {
        this.instance = instance;
    }

    @Override
    protected boolean createKeys(List<String> toPopulate) {
        if (!toPopulate.contains("Repository")) {
            toPopulate.add("Repository");
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(String key) {
        return new RepositoryRootNode(instance,key);
    }
}
