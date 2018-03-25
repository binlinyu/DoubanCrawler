package results;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import entity.Book;

public class Excel {
	private List<Book> list;
	private HSSFWorkbook hwb=null;
	private HSSFSheet hf=null;
	private HSSFRow hr=null;
	private HSSFCell hc=null;
	private HSSFCellStyle tileStyle=null;
	private HSSFFont font=null;
	private Book book=null;

	public Excel(List<Book> list) {
		super();
		this.list = list;
	}
	
	public HSSFWorkbook getExcel()  {
		try {
			hwb=new HSSFWorkbook();
			hf=hwb.createSheet();
			hr=hf.createRow(0);
			tileStyle=hwb.createCellStyle();
			font=hwb.createFont();
			font.setBold(true);
			font.setFontName("楷体");
			tileStyle.setFont(font);
			hc=hr.createCell(0);
			hc.setCellValue("序号");
			hc.setCellStyle(tileStyle);
			hc=hr.createCell(1);
			hc.setCellValue("书名");
			hc.setCellStyle(tileStyle);
			hc=hr.createCell(2);
			hc.setCellValue("评分");
			hc.setCellStyle(tileStyle);
			hc=hr.createCell(3);
			hc.setCellValue("评价人数");
			hc.setCellStyle(tileStyle);
			hc=hr.createCell(4);
			hc.setCellValue("作者");
			hc.setCellStyle(tileStyle);
			hc=hr.createCell(5);
			hc.setCellValue("出版社");
			hc.setCellStyle(tileStyle);
			hc=hr.createCell(6);
			hc.setCellValue("出版日期");
			hc.setCellStyle(tileStyle);
			hc=hr.createCell(7);
			hc.setCellValue("价格");
			hc.setCellStyle(tileStyle);
			Iterator<Book> it=list.iterator();
			int i=1;
			while (it.hasNext()) {
				book=it.next();
				hr=hf.createRow(i);
				hc=hr.createCell(0);
				hc.setCellValue(i);
				hc=hr.createCell(1);
				hc.setCellValue(book.getName());
				hc=hr.createCell(2);
				hc.setCellValue(book.getScore());
				hc=hr.createCell(3);
				hc.setCellValue(book.getNum());
				hc=hr.createCell(4);
				hc.setCellValue(book.getAuthor());
				hc=hr.createCell(5);
				hc.setCellValue(book.getPress());
				hc=hr.createCell(6);
				hc.setCellValue(book.getDate());
				hc=hr.createCell(7);
				hc.setCellValue(book.getPrice());
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(hwb!=null)  {
				try {
					hwb.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} 
		return hwb;
	}
	
	
}
