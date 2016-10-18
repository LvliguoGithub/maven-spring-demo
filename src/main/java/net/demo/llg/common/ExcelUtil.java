package net.demo.llg.common;

import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * ExcelUtil.java
 *
 * abstract: Excel工具类，可处理2007
 *
 * history:
 * 
 * mis_llg 2016年6月16日 初始化
 */
public class ExcelUtil {

	public static XSSFCellStyle cellStyle;
	public static XSSFCellStyle cellStyleGray;
	public static XSSFCellStyle cellStyleDate;
	public static XSSFCellStyle cellStyleSimpleDate;

	/**
	 * 初始化单元格样式（简易）
	 *
	 * @param wb
	 */
	public static void initCellStyleSimple(XSSFWorkbook wb) {
		cellStyle = createSimpleCellStyle(wb, IndexedColors.WHITE.getIndex());

		cellStyleGray = createSimpleCellStyle(wb, IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyleGray.setAlignment(XSSFCellStyle.ALIGN_CENTER);

		cellStyleDate = createSimpleCellStyle(wb, IndexedColors.WHITE.getIndex());
		XSSFDataFormat format = wb.createDataFormat();
		cellStyleDate.setDataFormat(format.getFormat("yyyy-MM-dd hh:mm:ss"));

		cellStyleSimpleDate = createSimpleCellStyle(wb, IndexedColors.WHITE.getIndex());
		cellStyleSimpleDate.setDataFormat(format.getFormat("yyyy-MM-dd"));
	}

	/**
	 * 根据字体和大小创建字体
	 *
	 * @param wb
	 * @param fontName
	 * @param fontSize
	 * @return
	 */
	public static XSSFFont createCellFont(XSSFWorkbook wb, String fontName, short fontSize) {
		XSSFFont font = wb.createFont();
		font.setFontName(fontName);
		font.setFontHeightInPoints(fontSize);
		return font;
	}

	/**
	 * 根据多种属性创建字体
	 *
	 * @param wb
	 * @param fontName
	 * @param fontSize
	 * @param bold
	 * @param color
	 * @param underline
	 * @return
	 */
	public static XSSFFont createCellFont(XSSFWorkbook wb, String fontName, short fontSize, boolean bold, short color,
			byte underline) {
		XSSFFont font = wb.createFont();
		font.setFontName(fontName);
		font.setFontHeightInPoints(fontSize);
		font.setBoldweight((short) 1);
		font.setColor(color);
		font.setUnderline(underline);
		return font;
	}

	/**
	 * 根据背景颜色创建单元格样式
	 *
	 * @param wb
	 * @param backgroudColor
	 * @return
	 */
	public static XSSFCellStyle createSimpleCellStyle(XSSFWorkbook wb, short backgroudColor) {
		XSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("Times New Roman");
		XSSFCellStyle style = wb.createCellStyle();
		style.setFont(font);
		if (backgroudColor != 64) {
			style.setFillForegroundColor(backgroudColor);
			style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		}
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setWrapText(true);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		return style;
	}

	/**
	 * 根据多种属性创建单元格样式
	 *
	 * @param wb
	 * @param font
	 * @param backgroudColor
	 * @param border
	 *            是否加边框，默认加四个细边框
	 * @param a
	 * @return
	 */
	public static XSSFCellStyle createCellStyle(XSSFWorkbook wb, XSSFFont font, short backgroudColor, boolean border,
			short a) {
		XSSFCellStyle style = wb.createCellStyle();
		style.setFont(font);
		if (backgroudColor != 64) {
			style.setFillForegroundColor(backgroudColor);
			style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		}
		if (border) {
			style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			style.setBorderRight(XSSFCellStyle.BORDER_THIN);
			style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		}
		style.setWrapText(true);
		style.setAlignment(a);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		return style;
	}

	/**
	 * 合并单元格并合并边框
	 *
	 * @param sheet
	 * @param row1
	 *            开始行
	 * @param row2
	 *            结束行
	 * @param col1
	 *            开始列
	 * @param col2
	 *            结束列
	 * @param cs
	 *            合并后的样式
	 */
	public static void mergeCells(XSSFSheet sheet, int row1, int row2, int col1, int col2, XSSFCellStyle cs) {
		if (row1 == row2 && col1 == col2) {
			return;
		}
		sheet.addMergedRegion(new CellRangeAddress(row1, row2, col1, col2));
		if (cs != null) {
			for (int i = row1; i <= row2; i++) {
				XSSFRow row = sheet.getRow(i);
				for (int j = col1; j <= col2; j++) {
					XSSFCell cell = row.getCell(j);
					cell.setCellStyle(cs);
				}
			}
		}
	}

	/**
	 * 创建列名称（一行）
	 * 
	 * @param sheet
	 * @param startLineNum
	 * @param headers
	 * @return
	 */
	public static XSSFRow buildThead(XSSFSheet sheet, int startLineNum, String[] headers) {
		XSSFRow row = sheet.createRow(startLineNum);
		for (int i = 0; i < headers.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(cellStyleGray);
		}
		return row;
	}

	/**
	 * 创建一行并设置值
	 * 
	 * @param row
	 * @param values
	 */
	public static XSSFRow buildRowValue(XSSFSheet sheet, int index, String[] values) {
		if (values != null && values.length > 0) {
			XSSFRow row = sheet.createRow(index);
			for (int i = 0; i < values.length; i++) {
				buildCellValue(row, i, values[i]);
			}
			return row;
		} else {
			throw new RuntimeException("导出数据内容为空");
		}
	}

	/**
	 * 构造cell的值(String)
	 * 
	 * @param row
	 * @param index
	 * @param value
	 */
	public static XSSFCell buildCellValue(XSSFRow row, int index, String value) {
		XSSFCell cell = row.createCell(index);
		cell.setCellValue(value);
		cell.setCellStyle(cellStyle);
		return cell;
	}

	/**
	 * 构造图片
	 * 
	 * @return
	 */
	public static Picture buildPicture(XSSFSheet sheet, Drawing drawing, byte[] bytes, String mime, int rowNum,
			int colNum, int rowHeight) {
		XSSFWorkbook wb = sheet.getWorkbook();
		ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, colNum, rowNum, colNum + 1, rowNum + 1);
		anchor.setAnchorType(2);

		int pictureIdx = 0;
		if ("PNG".equalsIgnoreCase(mime)) {
			pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
		} else if ("JPEG".equalsIgnoreCase(mime) || "JPG".equalsIgnoreCase(mime)) {
			pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
		} else {
			return null;
		}
		Picture pict = drawing.createPicture(anchor, pictureIdx);

		sheet.getRow(rowNum).setHeight((short) (rowHeight * (1440 / 95)));
		return pict;
	}

	/**
	 * 调整列宽
	 * 
	 * @param sheet
	 * @param colWidths
	 */
	public static void adjustColWidth(XSSFSheet sheet, int[] colWidths) {
		for (int i = 0; i < colWidths.length; i++) {
			if (colWidths[i] == 0) {
				sheet.autoSizeColumn(i);
			} else {
				sheet.setColumnWidth(i, colWidths[i]);
			}
		}
	}

}
