package com.arvind.fileprocessing.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class SheetGenerate {

	public void sheetCreater(String filepath) {
		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Employee Data");

		// This data needs to be written (Object[])
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		data.put("1", new Object[] { "ID", "NAME", "LASTNAME" ,"EMAIL_ID","TOTAL_HOURS"});
		data.put("2", new Object[] { "2108921", "Arvind", "Sisodiya","arvindsis35@gmail.com",120 });
		data.put("3", new Object[] { "2108922", "Rohit", "Parihaar","rohit.parihaar@gmail.com",100  });
		data.put("4", new Object[] { "2108923", "Mohit", "Jaiswal","mohit.jaish@gmail.com",79  });
		data.put("5", new Object[] {"2108924", "Aman", "Palya","aman.palya12@gmail.com",50});

		// Iterate over data and write to sheet
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
			}
		}
		try {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File(filepath));
			workbook.write(out);
			out.close();
			System.out.println(filepath+" written successfully on disk.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
