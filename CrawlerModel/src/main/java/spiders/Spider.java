package spiders;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import entity.Book;
import proxy.ProxiesPool;
import results.CrawlerResults;

public class Spider implements Runnable {
	
	private String urlStr;   //URL地址
	private String param;  //参数
	private static int INDEX=0; //抓取豆瓣的第index页

	public Spider(String urlStr, String param, String method) {
		super();
		this.urlStr = urlStr;
		this.param = param;
	}

	public void run() {
		System.out.println(Thread.currentThread().getName()+"start..");
		int index=0;
		Proxy proxy=ProxiesPool.getInstance().getProxy(Proxy.Type.SOCKS);
		String userAgent=ProxiesPool.getInstance().getUserAgent();
		while (INDEX<=1000) {
			synchronized (String.class) {
				index=INDEX;
				INDEX=INDEX+20;
			}
			if (null==proxy) {
				System.out.println("failed to get proxy");
				break;
			}
			Set<String> bookUrlList=getBookUrl(index,proxy);
			if(bookUrlList==null || bookUrlList.size()<=0)  {
				System.out.println("to last page");
				break;
			}
			Iterator<String> it=bookUrlList.iterator();
			while(it.hasNext())  {
				Document doc;
				String url=it.next();
		        System.out.println(Thread.currentThread().getName()+" request:"+url);
				try {
					doc = Jsoup.connect(url).proxy(proxy).header("User-Agent", userAgent).timeout(5000).get();
					String ratingSum = doc.getElementsByClass("rating_sum").select("a").select("span").html();
					//评价人数小于1000的不予考虑
					System.out.println(ratingSum);
					if(null!=ratingSum && Integer.parseInt(ratingSum)<1000)  {
						continue;
					}
					Elements titleElement = doc.getElementsByClass("subject clearfix").select("a");
					Elements scoreElement = doc.select("strong");
					Elements authorElement = doc.getElementById("info").select("a");
					Element pressElement = doc.getElementById("info");
					String title = titleElement.attr("title");
					String score = scoreElement.html();
					String author = authorElement.html();
					String press = pressElement.text();
					if (press.indexOf("出版社:") > -1) {
						press = pressElement.text().split("出版社:")[1].split(" ")[1];
					} else {
						press = "";
					}
					String date = pressElement.text();
					if (date.indexOf("出版年:") > -1) {
						date = pressElement.text().split("出版年:")[1].split(" ")[1];
					} else {
						date = "";
					}
					String price = pressElement.text();
					if (price.indexOf("定价:") > -1) {
						price = pressElement.text().split("定价:")[1].split(" ")[1];
						if (price.equals("CNY")) {
							price = pressElement.text().split("定价:")[1].split(" ")[2];
						}
					} else {
						price = "";
					}
					Book book=new Book(title, Double.parseDouble(score), Integer.parseInt(ratingSum), author, press, date, price);
					CrawlerResults.getInstance().addResults(book);
					Thread.sleep(10000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				 //睡眠2s，防止IP被封
		        try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private Set<String> getBookUrl(int index,Proxy proxy)  {
		Set<String> set=new HashSet<>();
		URL url = null;  
        HttpURLConnection connection = null;  
        System.out.println(Thread.currentThread().getName()+" request:"+urlStr+"?"+"start="+index+"&"+param);
        String regex = "https://book.douban.com/subject/[0-9]+/";  //url匹配规则
        Pattern p = Pattern.compile(regex);
        try {  
            /*建立连接*/    
            url = new URL(urlStr+"?"+"start="+INDEX+"&"+param); 
            connection = (HttpURLConnection) url.openConnection(proxy);  
            connection.setRequestProperty("Charset", "UTF-8");// 设置编码  
            connection.setReadTimeout(5000);
            //读取数据
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));              
            String line = "";  
            while ((line = reader.readLine()) != null) {
            	int n=0; //用于判断是否在Set集合中有相同元素
                Matcher matcher=p.matcher(line);  
                if(matcher.find())  {
                	String s=matcher.group();
                	Iterator<String> it=set.iterator();
                	while(it.hasNext())  {
                		if(s.equals(it.next()))  
                			break;
                		else 
                			n++;
                	}
                	if(n>=set.size())  {
                		set.add(matcher.group());
                	}
                }
            }  
            reader.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (connection != null) {  
                connection.disconnect();  
            }  
        }
        return set;
	}

}
