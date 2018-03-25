package MAIN;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import entity.Book;
import results.CrawlerResults;
import results.Excel;
import spiders.Spider;

public class Crawler {

	public static void main(String[] args) {
		ExecutorService threadPool=Executors.newFixedThreadPool(5);
		Spider[] spriders = new Spider[2];
		//5个线程，每个线程读一页
		for(int i=0;i<5;i++)  {
			spriders[i]=new Spider("https://book.douban.com/tag/编程","type=S", "GET");
			threadPool.submit(spriders[i]);	
		}
		threadPool.shutdown();
		while(!threadPool.isTerminated())  {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		List<Book> list=CrawlerResults.getInstance().getResults();
		//将结果生成Excel表
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
