/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.server.api;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author pramoth
 */
public class VirgoServerAttributes {
    private Map<String,Object> param = new HashMap<String,Object>();
    public void put(String attrName,Object value){
        param.put(attrName, value);
    }
    public Object get(String attrName){
        return param.get(attrName);
    }

    @Override
    public String toString() {
        return param.toString();
    }
    
}
