package com.titanium.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static Cell cell;
	
	public static Object[][] getTableArray(String FilePath, String SheetName) throws Exception {
		String[][] tabArray = null;
		try {
			
			FileInputStream ExcelFile = new FileInputStream(FilePath);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int startRow = 1;
			int startCol = 0;
			int ci, cj;
			int totalRows = ExcelWSheet.getLastRowNum();
			int totalCols = ExcelWSheet.getRow(0).getLastCellNum();
			tabArray = new String[totalRows][totalCols];
			ci = 0;
			for(int i = startRow; i <= totalRows; i++, ci++) {
				cj = 0;
				for(int j = startCol; j < totalCols; j++, cj++){
					tabArray[ci][cj] = getCellDataDDT(i, j);
				}
			}
		}catch (FileNotFoundException e){
			throw new Exception("Class ExcelUtils | Method getTableArray | Exception desc: Excel not found: "+e.getMessage());
		}catch (IOException e){
			throw new Exception("Class ExcelUtils | Method getTableArray | Exception desc: Could not read the Excel sheet: "+e.getMessage());  
		} 
		return(tabArray);  
	} 
	
	@SuppressWarnings("deprecation")
	public static String getCellDataDDT(int RowNum, int ColNum) throws Exception { 
		try{ 
			cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			int dataType = cell.getCellType();
			return (dataType==0)?String.valueOf(cell.getNumericCellValue()) : (dataType == 3)? "" : cell.getStringCellValue();
		}catch (Exception e){
			throw new Exception("Class ExcelUtils | Method getCellDataDDT | Exception desc: " + e.getMessage());  
		}
	}
}
