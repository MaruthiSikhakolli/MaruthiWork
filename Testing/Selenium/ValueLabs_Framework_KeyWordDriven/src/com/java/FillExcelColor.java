package com.java;

import java.io.*;
import com.java.SeleniumDriverTest;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.util.HSSFColor;

@SuppressWarnings("deprecation")
public class FillExcelColor {

	short pass = 0;
	short fail = 0;
	int cellCount = 0;

	FillExcelColor() {

		pass = HSSFColor.GREEN.index;
		fail = HSSFColor.RED.index;
	}

	public HSSFRow createRow() {
		HSSFRow row = SeleniumDriverTest.sheet.createRow(SeleniumDriverTest.sheet.getPhysicalNumberOfRows() + 1);
		HSSFCell myCell = row.createCell(cellCount);
		myCell.setCellValue(SeleniumDriverTest.tc_name_curr);
		return row;
	}

	public void flush() {
		try {
			FileOutputStream out = new FileOutputStream(SeleniumDriverTest.AdtestResultPath);
			SeleniumDriverTest.workbook.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeFailCell(HSSFRow myRow, String message) {
		cellCount++;
		HSSFCell myCell = myRow.createCell(cellCount);
		HSSFCellStyle style = myCell.getCellStyle();
		style.setFillForegroundColor(fail);
		myCell.setCellStyle(style);
		myCell.setCellValue(message);
	}

	public void writePassCell(HSSFRow myRow, String message) {
		cellCount++;
		HSSFCell myCell = myRow.createCell(cellCount);
		HSSFCellStyle style = myCell.getCellStyle();
		style.setFillForegroundColor(pass);
		myCell.setCellStyle(style);
		myCell.setCellValue(message);
	}
}