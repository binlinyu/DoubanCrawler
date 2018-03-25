import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import entity.Book;
import results.CrawlerResults;

public class addResultsTest {

	@Test
	public void test() {
		Book book1=new Book("a", 9.1, 1000, "a", "a", "", "10");
		Book book2=new Book("a", 9.1, 1000, "a", "a", "", "10");
		Book book3=new Book("c", 9.6, 1000, "a", "a", "", "10");
		Book book4=new Book("d", 7.1, 300, "a", "a", "", "10");
		CrawlerResults.getInstance().addResults(book1);
		CrawlerResults.getInstance().addResults(book2);
		CrawlerResults.getInstance().addResults(book3);
		CrawlerResults.getInstance().addResults(book4);
		List<Book> list=CrawlerResults.getInstance().getResults();
		Iterator< Book> iterator=list.iterator();
		while(iterator.hasNext())  {
			System.out.println(iterator.next().getName());
		}
	}

}
