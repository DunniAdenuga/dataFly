/*
procedure DFS(G,v):
2      label v as discovered
3      for all edges from v to w in G.adjacentEdges(v) do
4          if vertex w is not labeled as discovered then
5              recursively call DFS(G,w)
*/
package datafly;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author adenugad
 */
public class DGHTree 
{
    DGHNode root = new DGHNode();//which should always be set to {****}
    //ArrayList<DGHNode> children = new ArrayList<>();//add every possible child in the tree
    DGHNode current = new DGHNode();
    private int height = 0;
    private double weight = 0;
    private String label = "";
    
    public DGHTree(){
        
    }
    public DGHTree(DGHNode root){
        this.root = root;
        height++;
    }
//    public DGHTree(String rootData){
//        this.root = new DGHNode(rootData);
//    }
    public DGHTree(String file) throws FileNotFoundException{
        readIn(file);
    }
    
    public void setWeight(double x){
        weight = x;
    }
    
    public double getWeight(){
        return weight;
    }
    
    public void setLabel(String x){
        label = x;
    }
    
    public String getLabel(){
        return label;
    }
    /**
     * Read in the hierarchy into the tree
     * @param infile 
     * @throws java.io.FileNotFoundException 
     */
    private void readIn(String infile) throws FileNotFoundException{
        File file = new File(infile);
        /*System.out.println("File Exists: "+file.exists());
        System.out.println("File Can Read: "+file.canRead()); */
        Scanner scan = new Scanner(file);
        String[] rootLine = scan.nextLine().split(",");
        /*for(int y = 0; y < rootLine.length; y++){
        System.out.print(rootLine[y] + " ");
        }*/
        //System.out.println();
        root.setData(rootLine[0]);
        for(int i = 1; i < rootLine.length; i++){
                DGHNode child = new DGHNode(rootLine[i]);
                child.setParent(root);
                root.children.add(child);
            }
        
        while(scan.hasNextLine()){
            String[] line = scan.nextLine().split(",");
            String parent = line[0];
            //System.out.println("Parent: " +parent);
            for(int i = 1; i < line.length; i++){
            //assuming a potential parent used is a child on the tree already except root
                search(root, parent);
                /*System.out.println(current.isNull());
                System.out.println("current-data: " +current.data);*/
                DGHNode child = new DGHNode(line[i]);
                child.setParent(current);
                current.children.add(child);
                //System.out.println("current-data: " +current.data);
            }
        }
    scan.close();    
    }
    /*
    procedure DFS(G,v):
    2      label v as discovered
    3      for all edges from v to w in G.adjacentEdges(v) do
    4          if vertex w is not labeled as discovered then
    5              recursively call DFS(G,w)
    */
    
    public void search(DGHNode treeRoot, String data){
        
        for(int i = 0; i < treeRoot.children.size(); i++){
            search(treeRoot.children.get(i), data);
        }
        if(treeRoot.getData().equalsIgnoreCase(data)){
            //System.out.println("I got here! " + treeRoot.data);
            //System.out.println(treeRoot.isNull());
            //newNode = treeRoot.copy();
            current = treeRoot;
        }
        
    }
    
    public String getGeneralization(String data){
        search(root, data);
        if(data.equalsIgnoreCase(root.getData())){
            return root.getData();
        }
        //System.out.println("data & current.parent.getData(): " + data + " & " + current.parent.getData());
        //System.out.println();
        return current.parent.getData();
    }
    
    public void printTree(DGHNode roots, int j){
        System.out.print(roots.data + " " + j + "," + " level - " +roots.level + " :");
        j++;
        for(int i = 0; i < roots.children.size(); i++){
            printTree(roots.children.get(i), j);
        }
        //System.out.println();
    }
    
    public void setDGHNodeLevels(DGHNode roots, int j){
        roots.setLevel(j);
        j--;
        for(int i = 0; i < roots.children.size(); i++){
            setDGHNodeLevels(roots.children.get(i), j);
        }
    }
    
    /**
     * call a method that makes sure the length of each value in values is the same
     * @param values
     * @return 
     */
    
    /**
     * Height of Tree
     * @return 
     */
    public int getHeight(){    
        return height;
    }
    public void setHeight(){
        DGHNode node = root;
        while(node.isLeaf() == false){
            height++;
            int max = node.children.get(0).getChildCount();
            node = node.children.get(0);
            for(int i = 0; i < node.children.size(); i++){
                if(node.children.get(i).getChildCount() > max){
                    max = node.children.get(i).getChildCount();
                    node = node.children.get(i);
                }
            }
            
        }
    }
    
    
    public DGHTree createDGHTree(ArrayList<String> values) throws FileNotFoundException{
        /*I will call a method that makes sure the length of each value in values is the same */
        //ArrayList<String> newValues = rectifyValueLength(values);
        DGHTree tree = new DGHTree();
        ArrayList<DGHNode> valueNodes = new ArrayList<>();
        //ArrayList<ArrayList> valueNodeParents = new ArrayList<>();
        
            for(int i = 0; i < values.size(); i++){
                valueNodes.add(new DGHNode(values.get(i)));
            }
            for(int j = 0; j < valueNodes.size(); j++){
                setAllParents(valueNodes.get(j), valueNodes.get(j).getData().length()-1);
            }
            /*for(int i = 0; i < valueNodes.size(); i++){
            valueNodeParents.add(new ArrayList<DGHNode>());
            }*/
            String dghFile = "/Users/adenugad/NetBeansProjects/kAnonAlgorithms/src/datafly/createdDGHTree.txt";
            File infile = new File(dghFile);
        PrintWriter writer = new PrintWriter(infile); 
            for(int i = 0; i < valueNodes.size(); i++){
                DGHNode test = valueNodes.get(i);
                while(test != null){
                    //System.out.print(test.data + ",");
                    writer.print(test.data + ",");
                    test = test.parent;
                }
                //System.out.println();
                writer.println();
            }
            writer.close();
            
            Scanner scanner = new Scanner(infile);            
            String[] rootLine = scanner.nextLine().split(",");
            /*for(int y = 0; y < rootLine.length; y++){
            System.out.print(rootLine[y] + " ");
            }*/
            //System.out.println();
            //set Up Root
            tree.root.setData(rootLine[rootLine.length-1]);
            DGHNode child = new DGHNode(rootLine[rootLine.length-2]);
            child.setParent(tree.root);
            tree.root.children.add(child);
            /*for(int i = rootLine.length-1; i >= 0; i--){
                    DGHNode child = new DGHNode(rootLine[rootLine.length-1]);
                    child.setParent(root);
                    root.children.add(child);
                }*/
            for(int i = rootLine.length - 2; i > 0; i--){
                tree.search(tree.root, rootLine[i]);
                DGHNode testChild = new DGHNode(rootLine[i-1]);
                testChild.setParent(tree.current);
                tree.current.children.add(testChild);
            }
        
            while(scanner.hasNextLine()){
                String[] line = scanner.nextLine().split(",");
                for(int i = line.length-1; i > 0; i--){
                    tree.search(tree.root, line[i]);
                    DGHNode testChild = new DGHNode(line[i-1]);
                    testChild.setParent(tree.current);
                    if(tree.current.hasChild(testChild) == false)
                    {
                    tree.current.children.add(testChild);
                    }
                }
            }
        
       scanner.close();
       return tree;  
    }
    /**
     * Date must be in format (YYYY-MM-DD)
     * @param dates - dates to be generalized
     * @return Generalization tree
     * @throws java.io.FileNotFoundException
     */
    public DGHTree createDGHTreeForDate(ArrayList<String> dates) throws FileNotFoundException{
        DGHTree tree = new DGHTree();
        ArrayList<DGHNode> dateNodes = new ArrayList<>();

           for(int i = 0; i < dates.size(); i++){
                dateNodes.add(new DGHNode(dates.get(i)));
            }
            for(int j = 0; j < dateNodes.size(); j++){
                setAllParentsForDates(dateNodes.get(j), 3);//3 for each and **** for root 
            }
            
            String dghFile = "/Users/adenugad/NetBeansProjects/kAnonAlgorithms/src/datafly/createdDGHTree.txt";
            File infile = new File(dghFile);
            PrintWriter writer = new PrintWriter(infile); 
            for(int i = 0; i < dateNodes.size(); i++){
                DGHNode test = dateNodes.get(i);
                while(test != null){
                    //System.out.print(test.data + ",");
                    writer.print(test.data + ",");
                    test = test.parent;
                }
                //System.out.println();
                writer.println();
            }
            writer.close();
            
            Scanner scanner = new Scanner(infile);            
            String[] rootLine = scanner.nextLine().split(",");
            
            //set Up Root
            tree.root.setData(rootLine[rootLine.length-1]);
            DGHNode child = new DGHNode(rootLine[rootLine.length-2]);
            child.setParent(tree.root);
            tree.root.children.add(child);
          
            for(int i = rootLine.length - 2; i > 0; i--){
                tree.search(tree.root, rootLine[i]);
                DGHNode testChild = new DGHNode(rootLine[i-1]);
                testChild.setParent(tree.current);
                tree.current.children.add(testChild);
            }
        
            while(scanner.hasNextLine()){
                String[] line = scanner.nextLine().split(",");
                for(int i = line.length-1; i > 0; i--){
                    tree.search(tree.root, line[i]);
                    DGHNode testChild = new DGHNode(line[i-1]);
                    testChild.setParent(tree.current);
                    if(tree.current.hasChild(testChild) == false)
                    {
                    tree.current.children.add(testChild);
                    }
                }
            }
        
       scanner.close();
       return tree;  
    }
    
    public DGHTree createRangesDatesDGHTrees(ArrayList<String> dates){
        //form year ranges so i only first 4
        ArrayList<Integer> years = new ArrayList<>();
        ArrayList<String> yearRanges = new ArrayList<>();
        for(int i = 0; i < dates.size();i++){
            String year = dates.get(i).substring(0, 4);
            if(years.contains(Integer.parseInt(year)) == false){
                years.add(Integer.parseInt(year));
            }
        }
        //find min and max
        int min = years.get(0), max = years.get(0);
        //int minValueColumn = 0, maxValueColumn = 0;
        for(int i = 1; i < years.size(); i++){
            if(years.get(i) > max){
                max = years.get(i);
                //maxValueColumn = i;
            }
            if(years.get(i)< min){
                min = years.get(i);
                //minValueColumn = i;
            }
        }
        int noOfRanges = (max - min)/5;
        if(((max - min)%5) != 0){
            noOfRanges ++;
        }
        
        for(int i = 0; i < noOfRanges; i++){
            String range = String.valueOf(min) + "-" + String.valueOf(min+4);
            yearRanges.add(range);
            //System.out.println("noOfRange " + i + " : "+ range);
            min = min + 5;
        }
        //add ranges to DGHTree, we starting from the root
        DGHTree tree = new DGHTree(new DGHNode("****"));
        //System.out.println("Year Range size - " + yearRanges.size());
        for(int i = 0; i < yearRanges.size(); i++){
            DGHNode node = new DGHNode(yearRanges.get(i));
           // System.out.println("noOfRange " + i + " : "+ node.getData());
            node.setParent(tree.root);
            tree.root.children.add(node);
        }
        //add individual years as children to year ranges
        for(int i = 0; i < tree.root.children.size(); i++){
            //System.out.println("Tree Root Children - " + tree.root.children.size());
            for(int j = 0; j < years.size(); j++){
                String range2 = tree.root.children.get(i).getData();
                //System.out.println("range - " + range2);
                if(years.get(j) >= Integer.parseInt(range2.substring(0,4))){
                    if(years.get(j) <= Integer.parseInt(range2.substring(5))){
                        DGHNode node = new DGHNode(String.valueOf(years.get(j)));
                        node.setParent(new DGHNode(range2));
                        tree.root.children.get(i).children.add(node);
                    }
                }
            }
        }
        //add year/mon as children to individual years - cancel duplication
        for(int i = 0; i < tree.root.children.size(); i++){
            for(int j = 0; j < tree.root.children.get(i).getChildCount(); j++){
                for(int k = 0; k < dates.size(); k++){
                    if(Integer.parseInt(dates.get(k).substring(0, 4)) == 
                            Integer.parseInt(tree.root.children.get(i).children.get(j).getData())){
                        DGHNode node = new DGHNode(dates.get(k).substring(0, 7));
                        if(tree.root.children.get(i).children.get(j).hasChild(node) == false)
                        {
                        node.setParent(tree.root.children.get(i).children.get(j));
                        tree.root.children.get(i).children.get(j).children.add(node);
                        }
                    }
                }
            }
        }
        //add full dates(year - mon - day) as children to year-mon
        for(int i = 0; i < tree.root.children.size(); i++){
            for(int j = 0; j < tree.root.children.get(i).getChildCount(); j++){
                for(int k = 0; k < tree.root.children.get(i).children.get(j).getChildCount(); k++){
                    for(int l = 0; l < dates.size(); l++){
                        if(dates.get(l).substring(0, 7).equalsIgnoreCase(tree.root.children.get(i).children.get(j).children.get(k).getData())){
                            DGHNode node = new DGHNode(dates.get(l));
                            node.setParent(tree.root.children.get(i).children.get(j).children.get(k).getData());
                            tree.root.children.get(i).children.get(j).children.get(k).children.add(node);
                        }
                    }
                }
            }
        }
        
    return tree;    
    }
    
    
    public void setAllParents(DGHNode node, int i){
        //for(int i = 0; i < node.getData().length(); i++){
        if(i >= 0){
            node.setParent(modifyString(node.getData(), i));
            i--;
            node.parent.children.add(node);
            setAllParents(node.parent, i);
        }
        //}
    }
    
    public void setAllParentsForDates(DGHNode node, int i){
        //for(int i = 0; i < node.getData().length(); i++){
        if(i > 0){
            node.setParent(modifyToDate(node.getData()));
            i--;
            node.parent.children.add(node);
            setAllParentsForDates(node.parent, i);
        }
        //}
    }
    
    //public void getRoot()
    
   
    
    public String modifyString(String string, int level){
        String result = string.substring(0, level);
        if(level == 0){
            result = "";
        }
        for(int i = result.length(); i < string.length(); i++){
            result = result + "*";
            //System.out.println("result-" + result);
        }
        return result;
    }
    
    public String modifyToDate(String string){
        if(string.length() == 4){
            return "****";
        }
        return string.substring(0, string.length()-3);
    }
            
    /*
    For testing purposes
    */
    public static void main(String[] args) throws FileNotFoundException {
        /*DGHTree tree = new DGHTree();
        tree.readIn("/Users/adenugad/NetBeansProjects/kAnonAlgorithms/src/datafly/dghtest.txt");
        tree.printTree(tree.root, 0);
        System.out.println();
        System.out.println(tree.getGeneralization("cough"));*/
        DGHTree tree = new DGHTree();
        DGHTree tree2;
        //new ArrayList<Integer>(Arrays.asList(1,2,3,5,8,13,21));
        ArrayList<String> values = new ArrayList<>(Arrays.asList("1995-10-30", "1983-11-25", "1995-12-03","2000-07-16", "1986-11-25","1997-02-01", "2000-07-09", "2010-09-07","2011-05-05"));
        tree2 = tree.createRangesDatesDGHTrees(values);
        tree2.setHeight();
        System.out.println("Tree Height1: " + tree2.getHeight());
        tree2.setDGHNodeLevels(tree2.root, tree2.getHeight()-1);
        System.out.println("Tree Height2: " + tree2.getHeight());
        tree2.printTree(tree2.root, 0);
        /*System.out.println();
        System.out.println("1*** generalization: " + tree2.getGeneralization("1***"));
        System.out.println("1997 generalization: " + tree2.getGeneralization("1997"));
        System.out.println("1983 generalization: " + tree2.getGeneralization("1983"));
        System.out.println("1986 generalization: " + tree2.getGeneralization("1986"));
        System.out.println("2007 generalization: " + tree2.getGeneralization("2007"));
        System.out.println("2002 generalization: " + tree2.getGeneralization("2002"));
        System.out.println("2010 generalization: " + tree2.getGeneralization("2010"));
        System.out.println("2*** generalization: " + tree2.getGeneralization("2***"));*/
        //print 19** children
        System.out.println();
        System.out.println("2000-07-09: " + tree2.getGeneralization("2000-07-09"));
        System.out.println("2000-07-16: " + tree2.getGeneralization("2000-07-16"));
        System.out.println("2000-07: " + tree2.getGeneralization("2000-07"));
        System.out.println("2000: " + tree2.getGeneralization("2000"));


        //System.out.println();
        tree2.search(tree2.root, "2000");
        for(int i = 0; i < tree2.current.getChildCount(); i++){
            System.out.println("child " + i + " -" + tree2.current.children.get(i).getData());
        }
        System.out.println("Tree Height: " + tree2.getHeight());
    }
}
