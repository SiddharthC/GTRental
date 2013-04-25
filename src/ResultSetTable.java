import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
 
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/*****************************************************
 * Generates a JTable that is used to display a 
 * SQL ResultSet. This is the key class that allows
 * for the report tables generation from the database.
 * 
 * @author ShreyyasV, Mark Owens, Siddharth Choudhary
 *
 *****************************************************/
 
/**
 * A JTable used to display a SQL ResultSet.
 *
 */
@SuppressWarnings("serial")
public class ResultSetTable extends JTable{
 
  private final DefaultTableModel dataModel;
 
  public ResultSetTable()
  {
	  super();
	  dataModel = new DefaultTableModel();
	  setModel(dataModel);
  }
  
  public ResultSetTable(ResultSet rs)
                       throws SQLException{
 
    super();
    dataModel = new DefaultTableModel();
    setModel(dataModel);
 
    try {
      //create an array of column names
      ResultSetMetaData mdata = rs.getMetaData();
      int colCount = mdata.getColumnCount();
      String[] colNames = new String[colCount];
      for (int i = 1; i <= colCount; i++) {
        colNames[i - 1] = mdata.getColumnLabel(i);
      }
      dataModel.setColumnIdentifiers(colNames);
 
      //now populate the data
      while (rs.next()) {
        String[] rowData = new String[colCount];
        for (int i = 1; i <= colCount; i++) {
          rowData[i - 1] = rs.getString(i);
        }
        dataModel.addRow(rowData);
      }
    }
    finally{
      try {
        rs.close();
      }
      catch (SQLException ignore) {
      }
    }
  }
}