package mavenendtoend.end2end;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelFile {

	public static Workbook MyWorkbook;
	public static Sheet MySheet;
	public static FileInputStream inputStream;
	public static FileOutputStream outputStream;

	public static Sheet readExcel(String filePath, String fileName, String sheetName) throws IOException {
		File file = new File(filePath + "/" + fileName);
		System.out.println(file);
		inputStream = new FileInputStream(file);
		//Workbook MyWorkbook = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		if (fileExtensionName.equals(".xlsx")) {

			MyWorkbook = new XSSFWorkbook(inputStream);

		}

		else if (fileExtensionName.equals(".xls")) {

			MyWorkbook = new HSSFWorkbook(inputStream);

		}

		Sheet MySheet = MyWorkbook.getSheet(sheetName);
		int rowCount = MySheet.getLastRowNum() - MySheet.getFirstRowNum();
		System.out.println(MySheet.getRow(1).getCell(1).getStringCellValue());
		System.out.println("Total row count" + rowCount);
		inputStream.close();
		//MyWorkbook.close();
		return MySheet;
	}

	public String cellValueString(int rowNum, int colNum, Sheet MySheet) {
		return MySheet.getRow(rowNum).getCell(colNum).getStringCellValue();
	}

	public int cellValueInt(int rowNum, int colNum, Sheet MySheet) {
		return (int) MySheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
	}

	@SuppressWarnings("null")
	public static void writeExcel(String filePath, String fileName, String sheetName,int rowNum, int colNum,String Value)
	{	
		
		try {
			Sheet MySheet = readExcel(filePath, fileName, sheetName);
			File file = new File(filePath + "/" + fileName);
		    outputStream = new FileOutputStream(file);
		    		    
		   // Workbook MyWorkbook = null;
		    			
			MySheet.getRow(rowNum).createCell(colNum).setCellValue(Value);
		    //inputStream.close();
			MyWorkbook.write(outputStream);
		    outputStream.close();
		    System.out.println("Excel written successfully..");

		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
}

//	public void writeCellValueString(int rowNum, int colNum, Sheet MySheet,String Value)
//	{
//		 MySheet.getRow(rowNum).getCell(colNum).setCellValue(Value);
//		 
	}
