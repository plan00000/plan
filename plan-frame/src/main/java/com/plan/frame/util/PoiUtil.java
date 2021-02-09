package com.plan.frame.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PoiUtil {

	private Sheet sheet; // 表格类实例
    Map<String,String> resultMap = new HashMap<String,String>();

	public void loadExcel2(MultipartFile file) {
		InputStream inStream = null;
		try {
			inStream =file.getInputStream();
			Workbook workBook = WorkbookFactory.create(inStream);

			sheet = workBook.getSheetAt(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取excel文件，创建表格实例
	 * 
	 * @param filePath
	 */
	public void loadExcel(String filePath) {
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(new File(filePath));
			Workbook workBook = WorkbookFactory.create(inStream);

			sheet = workBook.getSheetAt(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellValue(Cell cell) {
		String cellValue = "";
		DataFormatter formatter = new DataFormatter();
		if (cell != null) {
			// 判断单元格数据的类型，不同类型调用不同的方法
			switch (cell.getCellType()) {
			// 数值类型
			case Cell.CELL_TYPE_NUMERIC:
				// 进一步判断 ，单元格格式是日期格式
				if (DateUtil.isCellDateFormatted(cell)) {
					cellValue = formatter.formatCellValue(cell);
				} else {
					// 数值
					double value = cell.getNumericCellValue();
					int intValue = (int) value;
					cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
				}
				break;
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			// 判断单元格是公式格式，需要做一种特殊处理来得到相应的值
			case Cell.CELL_TYPE_FORMULA: {
				try {
					cellValue = String.valueOf(cell.getNumericCellValue());
				} catch (IllegalStateException e) {
					cellValue = String.valueOf(cell.getRichStringCellValue());
				}
			}
				break;
			case Cell.CELL_TYPE_BLANK:
				cellValue = "";
				break;
			case Cell.CELL_TYPE_ERROR:
				cellValue = "";
				break;
			default:
				cellValue = cell.toString().trim();
				break;
			}
		}
		return cellValue.trim();
	}

	/**
	 * 初始化表格中的每一行，并得到每一个单元格的值
	 */
	public Map<String,String> init() {
		int rowNum = sheet.getLastRowNum() + 1;
		resultMap.put("totalRowNum", rowNum+"");
		for (int i = 0; i < rowNum; i++) {
			Row row = sheet.getRow(i);
			if(row==null){
				continue;
			}
			// 每有新的一行，创建一个新的LinkedList对象
			for (int j = 0; j < row.getLastCellNum(); j++) {
				Cell cell = row.getCell(j);
				// 获取单元格的值
				String str = getCellValue(cell);
				// 将得到的值放入链表中
				resultMap.put(getCharByNum(j)+(i+1), str);
			}
		}
		return resultMap;
	}
	
	
	
//	/**
//	 * 获得execl的列的字母（只到26列）
//	 * @param index
//	 * @return
//	 */
//	public String getLetter(int index){
//		String[] letter = new String[]{"A","B","C","D","E","F","G",
//				"H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
//		return letter[index];
//	}
//	
	/**
	 * 获得execl的列的字母(没有列限制)
	 * @param number
	 * @return
	 */
	public static String getCharByNum(int number) {
        int index = number / 26 - 1;
        if (index < 0) {
            return (char) (65 + number % 26) + "";
        } else if (index >= 0) {
            return (char) (65 + index) + "" + (char) (65 + number % 26) + "";
        }
        return "@";
    }

	public static String getCellName(int colIndex, int rowIndex) {
		return getCharByNum(colIndex)+(rowIndex+1);
	}
	
	public static void main(String[] args) {
		PoiUtil poi = new PoiUtil();
		poi.loadExcel("d:/运营生产日报-定稿.xlsx");
		poi.init();
	}
	
	
}
