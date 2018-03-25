import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import entity.Book;
import results.CrawlerResults;
import results.Excel;

public class ExcelTest {

	@Test
	public void test() {
		Book book1=new Book("a", 9.1, 1000, "a", "a", "", "10");
		Book book2=new Book("a", 9.1, 1000, "a", "a", "", "10");
		Book book3=new Book("c", 9.6, 1000, "a", "a", "", "10");
		Book book4=new Book("d", 7.1, 1000, "a", "a", "", "10");
		CrawlerResults.getInstance().addResults(book1);
		CrawlerResults.getInstance().addResults(book2);
		CrawlerResults.getInstance().addResults(book3);
		CrawlerResults.getInstance().addResults(book4);
		List<Book> list=CrawlerResults.getInstance().getResults();
		HSSFWorkbook wb=new Excel(list).getExcel();
		if(wb!=null)  {
        	FileOutputStream fout=null;
        	try    
            {    
                fout = new FileOutputStream("result.xlsx");    
                wb.write(fout);   
            }    
            catch (Exception e)    
            {    
                e.printStackTrace();    
            }  finally  {
            	try {
            		if(fout!=null)  {
						fout.close();
            		}
				} catch (IOException e) {
					e.printStackTrace();
				}    
            }
        } 
	}

}
