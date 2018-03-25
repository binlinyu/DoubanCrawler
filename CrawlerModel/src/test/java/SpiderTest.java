import org.junit.Test;

import spiders.Spider;

public class SpiderTest {

	@Test
	public void test() {
		Thread thread=new Thread(new Spider("https://book.douban.com/tag/编程","type=S", "GET"));
		thread.start();
	}

}
