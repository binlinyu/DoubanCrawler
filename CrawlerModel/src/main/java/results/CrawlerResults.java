package results;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import entity.Book;

public class CrawlerResults {
	
	private LinkedList<Book> results=null;
	private static CrawlerResults cr=null;
	
	private CrawlerResults()  {
		results=new LinkedList<Book>();
	}
	
	public static CrawlerResults getInstance()  {
		if(null==cr)  {
			cr=new CrawlerResults();
		}
		return cr;
	}
	
	public synchronized LinkedList<Book> getResults()  {
		return results;
	}
	
	public synchronized void addResults(Book book)  {
		Iterator<Book> it=results.iterator();
		int i=0;
		while(it.hasNext())  {
			if (it.next().getName().equals(book.getName())) {
				break;
			}
			i++;
		}
		if(i>=results.size())  {
			results.add(book);
			System.out.println("add:"+book.getName());
			Collections.sort(results);
			if(results.size()>40)  {
				results.removeLast();
			}
		}
	}
}
