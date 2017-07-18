/*
 * Table to be practised with.
 */
package datafly;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author adenugad
 */
public class PrivateTable {
    //make stuff private at some point
    int noOfColumns;
    int noOfRows;
    TableRow topRow;//contain table heading //create get methods later
    TableRow quasiIden;//quasi-Identifiers
    ArrayList<TableRow> tableRows = new ArrayList<>(); //list of all rows in table
    //ArrayList<TableColumn> tableColumn = new ArrayList<>();//list of all columns in table
     
     PrivateTable(){
         
     }
        
     PrivateTable(int noOfColumns, int noOfRows){
         this.noOfColumns = noOfColumns;
         this.noOfRows = noOfRows;
         
        for(int i = 0; i< noOfRows; i++){
            tableRows.add(new TableRow());
        }
    }
     
     public TableRow getTopRow(){
         return topRow;
     }
     
     public TableRow getQuasiIden(){
         return quasiIden;
     }
     
     public ArrayList<TableRow> getTableRows(){
         return tableRows;
     }
     
     public void setRowHeadings(String data){
         topRow = new TableRow(data, 0);//I'm gonna hardcode this for my test table,
                                //this should be modified to get the input from the user 
                                //or from some database 
        topRow.rowNumber = 0;
     }
     
     /*set table values from file inputs*/
     public void setTableValues(String fileLocation) throws FileNotFoundException{
         File inputFile = new File(fileLocation);
         //System.out.println("Exists: " + inputFile.exists());
         //System.out.println("Can Read: " + inputFile.canRead());
         Scanner infile = new Scanner(inputFile);
         int x  = 1;//measure current row in table
         while(infile.hasNextLine()){
             String line  = infile.nextLine();
             tableRows.add(new TableRow(line, x));
             x++;
         }
     }
     
     /*set table values from database  inputs*/
     public void setTableValues(Connection conn) throws FileNotFoundException, SQLException{
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(
                 "SELECT race,ph.dob,p.id,sex,allele1,allele2 "
                 + "FROM production_genotype "
                 + "inner JOIN production_patient p ON production_genotype.patient_id = p.id "
                 + "inner JOIN production_patientphi ph ON p.id = ph.id");
         ResultSetMetaData metadata = rs.getMetaData();
         int columnCount = metadata.getColumnCount();
         
         int x  = 1;//measure current row in table
         while(rs.next()){
             String line = "";
             for(int i = 1; i <= columnCount; i++){
              line = line + rs.getString(i) + ",";
             }
             tableRows.add(new TableRow(line, x));
             x++;
         }
     }
     
     public String getValue(int col, int row){
         if(row == 0){
             return (String)topRow.getData().get(col);
         }
         else if((row > noOfRows) || (col > noOfColumns))
            return "Problem!!!!";
         else
             return (String)tableRows.get(row).getData().get(col);
     }
     
     public void setQuasi(String data){
         quasiIden = new TableRow();
         quasiIden.addData(data);
     }
     
     //make a copy method at some point
     public PrivateTable copy(){
         PrivateTable newTable = new PrivateTable();
         newTable.noOfColumns = noOfColumns;
         newTable.noOfRows = noOfRows;
         newTable.quasiIden = quasiIden.copy();
         newTable.topRow = topRow.copy();
         for(int i = 0; i < tableRows.size(); i++){
            newTable.tableRows.add(tableRows.get(i).copy());
         }
         
         return newTable;
     }
     
     //need method to display formatted table
     //Prints out table in a readable format
     public void printFormat(){
         topRow.rowPrint();
         System.out.println();
         for(int i = 0; i < tableRows.size(); i++){
             tableRows.get(i).rowPrint();
         }
         System.out.println("Size Of Released Table: " + tableRows.size());
     }
}
