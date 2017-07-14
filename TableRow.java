/*
 * 
 */
package datafly;

import java.util.ArrayList;

/**
 *
 * @author adenugad
 */
public class TableRow {
    int rowNumber; //number of row in the table
    //int noOfColumns;
    ArrayList<String> data = new ArrayList<>();
    public TableRow(){
        
    }
    public TableRow(String data, int rowNumber){
        String[] stringArray = data.split(",");
        for (String stringArray1 : stringArray) {
            this.data.add(stringArray1);
        }
        this.rowNumber = rowNumber;
    }
    //assuming row is representated as ... , ... , ... , ... ,
    public void addData(String data){
        String[] stringArray = data.split(",");
        for (String stringArray1 : stringArray) {
            this.data.add(stringArray1);
        }
    }
    
    public ArrayList<String> getData(){
        return data;
    }
    /**use this to check equality of table
     * @param row - row, the calling row wants to be compared to
     * @return boolean */
    public boolean checkEquality(TableRow row){
        if(data.size() != row.data.size())
            return false;
        else
        {
            for(int i = 0; i < data.size(); i++){
                if((data.get(i).equalsIgnoreCase(row.data.get(i))) == false)
                    return false;
            }
            return true;
        }
        
    }
    
    public TableRow copy(){
        TableRow newTableRow = new TableRow();
        newTableRow.rowNumber = rowNumber;
        for(int i = 0; i < data.size(); i++){
            newTableRow.data.add(data.get(i));
        }
        return newTableRow;
    }
    
    public void rowPrint(){
        for(int i = 0; i < data.size(); i++ ){
            System.out.printf("%15s", data.get(i));
        }
        System.out.println();
    }
    
    /*public boolean seeIfSequenceIsInRow(ArrayList<String> array){
        
    }*/
  
}
