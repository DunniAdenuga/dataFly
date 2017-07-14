
package datafly;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.tree.TreeNode;

/**
 *
 * @author adenugad
 */
public class DGHNode implements TreeNode {
    ArrayList<DGHNode> children = new ArrayList<>();
    DGHNode parent = null;
    String data = "";
    int level = Integer.MIN_VALUE;
    
    public DGHNode(){
        
    }
    
    public DGHNode(String data){
        this.data = data;
    }
    
    public void setLevel(int i){
        level = i;
    }
    
    public DGHNode(String data, DGHNode parent, ArrayList<DGHNode> children){
        this.data = data;
        this.parent = parent.copy();//have to define copy method
        //this.children = children;
        for(int i = 0; i < children.size();i++){
            this.children.add(children.get(i).copy());
        }
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        for(int i = 0; i < children.size(); i++){
            if(children.get(i) == node){
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public Enumeration children() {
        return (Enumeration)children.iterator();
    }
    
    public ArrayList<DGHNode> getChildren(){
        return children;
    }
    /**
     * I won't be using this method
     * @return 
     */
    @Override
    public boolean getAllowsChildren() {
        return true;
    }
    
    public void setData(String inData){
        data = inData;
    }
    public String getData(){
        return data ;
    }
    
    public void setParent(String inData){
        parent = new DGHNode(inData);
    }
    
    public void setParent(DGHNode parent){
        this.parent = parent;
    }
    
    public DGHNode copy(){
        DGHNode newNode = new DGHNode();
        newNode.setData(data);
        newNode.setParent(parent.getData());
        for(int i = 0; i < children.size(); i++){
            newNode.children.add(i, children.get(i));
        }
        return newNode;
    }
    
    public boolean isNull(){
        return data.equals("");
    }
    
    public boolean hasChild(DGHNode node){
        for(int i = 0; i < children.size(); i++){
            if(children.get(i).getData().equalsIgnoreCase(node.getData())){
                return true;
            }
        }
        return false;
    }
}
