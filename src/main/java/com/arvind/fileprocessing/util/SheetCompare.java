package com.arvind.fileprocessing.util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class SheetCompare {

	public void sheetComparision(String filepath1, String filepath2) {

		try {
			FileInputStream beeline = new FileInputStream(new File(filepath1));
			FileInputStream esasheet = new FileInputStream(new File(filepath2));

			XSSFWorkbook workbook1 = new XSSFWorkbook(beeline);
			XSSFWorkbook workbook2 = new XSSFWorkbook(esasheet);

			XSSFSheet sheet1 = workbook1.getSheetAt(0);
			XSSFSheet sheet2 = workbook2.getSheetAt(0);

			if (compareTwoSheets(sheet1, sheet2)) {
				
				System.out.println("\nTwo Excelsheets are Equal");
			} else {
				System.out.println("\nTwo Excelsheets are Not Equal");
			}
			beeline.close();
			esasheet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean compareTwoSheets(XSSFSheet sheet1, XSSFSheet sheet2) {
		int firstRow1 = sheet1.getFirstRowNum();
		int lastRow1 = sheet1.getLastRowNum();
		boolean equalSheets = true;
		for (int i = firstRow1; i <= lastRow1; i++) {
			System.out.print("___________________________");
			System.out.println("\nComparing Row " + i);
			System.out.println("___________________________");
			XSSFRow row1 = sheet1.getRow(i);
			XSSFRow row2 = sheet2.getRow(i);
			if (!compareTwoRows(row1, row2)) {
				equalSheets = false;
				System.out.println(" Row " + i + " | Not Equal");
			} else {
				System.out.println(" Row " + i + " | Equal ");
			}
		}
		return equalSheets;

	}

	public static boolean compareTwoRows(XSSFRow row1, XSSFRow row2) {
		if ((row1 == null) && (row2 == null)) {
			return true;
		} else if ((row1 == null) || (row2 == null)) {
			return false;
		}
		int firstCell1 = row1.getFirstCellNum();
		int lastCell1 = row1.getLastCellNum();
		boolean equalRows = true;

		for (int i = firstCell1; i <= lastCell1; i++) {
			XSSFCell cell1 = row1.getCell(i);
			XSSFCell cell2 = row2.getCell(i);
			if (!compareTwoCells(cell1, cell2)) {
				equalRows = false;
				System.err.println("Cell " + i + " | Not Equal ");
			} else {
				System.out.println("Cell " + i + " | Equal ");
			}
		}
		return equalRows;
	}

	public static boolean compareTwoCells(XSSFCell cell1, XSSFCell cell2) {
		if ((cell1 == null) && (cell2 == null)) {
			return true;
		} else if ((cell1 == null) || (cell2 == null)) {
			return false;
		}

		boolean equalCells = false;
		if (cell1.getCellType() == cell2.getCellType()) {
			if (cell1.getCellStyle().equals(cell2.getCellStyle())) {
				// Compare cells based on its type
				switch (cell1.getCellType()) {
				case FORMULA:
					if (cell1.getCellFormula().equals(cell2.getCellFormula())) {
						equalCells = true;
					}
					break;
				case NUMERIC:
					if (cell1.getNumericCellValue() == cell2.getNumericCellValue()) {
						equalCells = true;
					}
					break;
				case STRING:
					if (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
						equalCells = true;
					}
					break;
				case BLANK:
					if (cell2.getCellType() == cell1.getCellType().BLANK) {
						equalCells = true;
					}
					break;
				case BOOLEAN:
					if (cell1.getBooleanCellValue() == cell2.getBooleanCellValue()) {
						equalCells = true;
					}
					break;
				case ERROR:
					if (cell1.getErrorCellValue() == cell2.getErrorCellValue()) {
						equalCells = true;
					}
					break;
				default:
					if (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
						equalCells = true;
					}
					break;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
		return equalCells;
	}
}