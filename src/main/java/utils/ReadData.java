package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.Reporter;

public class ReadData {
	// get data from properties file
	public static String fromPropertyFile(String FilePath, String key) {
		FileInputStream fis = null;
		Properties properties = null;
		try {
			fis = new FileInputStream(new File(FilePath));
			properties = new Properties();
			properties.load(fis);

			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		return properties.getProperty(key);

	}

	// get cell data from excel
	public static String getCellDataFromExcel(String FilePath, String sheetName, int rowNo, int cellNo) {

		FileInputStream fis = null;
		Workbook workbook = null;
		try {
			fis = new FileInputStream(new File(FilePath));
			workbook = WorkbookFactory.create(fis);

			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return workbook.getSheet(sheetName).getRow(rowNo).getCell(cellNo).getStringCellValue();
	}

	// get row data from excel
	public static String[][] getRowDataFromExcel(String FilePath, String sheetName, int rowNo) {

		FileInputStream fis = null;
		Workbook workbook = null;
		try {
			fis = new FileInputStream(new File(FilePath));
			workbook = WorkbookFactory.create(fis);

			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sheet sheetinfo = workbook.getSheet(sheetName);
		int colCount = sheetinfo.getRow(rowNo).getPhysicalNumberOfCells() - 1;
		String data[][] = new String[1][colCount];

		for (int i = 0; i < colCount; i++) {
			data[0][i] = sheetinfo.getRow(rowNo).getCell(i + 1).toString();
		}
		return data;
	}

	// get multiple data from excel
	public static String[][] multiple_Data_From_Excel(String FilePath, String sheetName, String TestcaseID) {

		String data[][] = null;
		FileInputStream fis = null;
		Workbook workbook = null;
		String TestcaseName = TestcaseID;
		try {
			fis = new FileInputStream(new File(FilePath));
			workbook = WorkbookFactory.create(fis);

			Sheet sheet = workbook.getSheet(sheetName);

			List<Row> rowsWithSameTestcaseID = new ArrayList<Row>();
			int rowCount = 0;
			int colCount = 0;
			System.out.println(sheet);
			if (sheet == null) {
				fis.close();
				System.out.println("Invalid sheet Name: " + sheetName);
				Reporter.log("Invalid Sheet Name: " + sheetName, false);
				throw new RuntimeException("Sheet '" + sheetName + "' not found in the file.");
			} else {
				for (Row row : sheet) {
					Cell testcaseIdCell = row.getCell(0);

					if (testcaseIdCell != null && testcaseIdCell.getStringCellValue().equalsIgnoreCase(TestcaseName)) {
						rowsWithSameTestcaseID.add(row);
					}
				}

				rowCount = rowsWithSameTestcaseID.size();
				System.out.println(rowCount);
				if (rowCount != 0) {
					for (Row row : rowsWithSameTestcaseID) {
						colCount = row.getLastCellNum();
					}
					data = new String[rowsWithSameTestcaseID.size()][];
					int i = 0;
					for (Row row : rowsWithSameTestcaseID) {
						data[i] = new String[row.getLastCellNum()];
						int j = 0;
						for (Cell cell : row) {
							data[i][j] = cell.getStringCellValue();
							j++;
						}
						i++;
					}
					fis.close();
				} else {
					fis.close();
					System.out.println("Invalid TestcaseID: " + TestcaseID);
					Reporter.log("Value is: " + TestcaseID, false);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;

	}

	public static Map<String, String> ReadDataFromExcelToMap(String FilePath, String sheetName, String TestcaseID) {

		Map<String, String> dataMap = new LinkedHashMap<>();
		FileInputStream fis = null;
		Workbook workbook = null;

		try {
			fis = new FileInputStream(new File(FilePath));
			workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheet(sheetName);

			if (sheet == null) {
				System.out.println("Invalid sheet Name: " + sheetName);
				Reporter.log("Invalid Sheet Name: " + sheetName, false);
				fis.close();
				throw new RuntimeException("Sheet '" + sheetName + "' not found in the file.");
			} else {
				Row headerRow = sheet.getRow(0); // Keys from header
				int rowCount = sheet.getPhysicalNumberOfRows();

				for (int r = 1; r < rowCount; r++) { // Start from row 1 to skip header
					Row row = sheet.getRow(r);
					if (row != null) {
						Cell firstCell = row.getCell(0);
						if (firstCell != null && firstCell.getStringCellValue().equalsIgnoreCase(TestcaseID)) {
							int colCount = row.getLastCellNum();

							for (int c = 0; c < colCount; c++) {
								Cell keyCell = headerRow.getCell(c);
								Cell valueCell = row.getCell(c);

								String key = keyCell != null ? keyCell.toString() : "Column" + c;
								String value = valueCell != null ? valueCell.toString() : "";

								dataMap.put(key, value);
							}
							break; // Only first matching TestcaseID is needed
						}
					}
				}
				fis.close();

			}

			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataMap;
	}

}
