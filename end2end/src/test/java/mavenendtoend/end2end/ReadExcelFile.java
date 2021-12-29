package mavenendtoend.end2end;

import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelFile {

	
	public static Sheet MySheet;

	public static Sheet readExcel(String filePath, String fileName, String sheetName) throws IOException 
	{
		File file = new File(filePath + "/" + fileName);
		System.out.println(file);
		FileInputStream inputStream = new FileInputStream(file);
		Workbook MyWorkbook = null;
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
		System.out.println("Total row count"+rowCount);
		return MySheet;
	}
	
	public String cellValueString(int rowNum, int colNum, Sheet MySheet)
	{
		return MySheet.getRow(rowNum).getCell(colNum).getStringCellValue();
	}
	
	public int cellValueInt(int rowNum, int colNum, Sheet MySheet)
	{
		return (int) MySheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
	}
}
