package proxy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.ArrayList;

/**
 * 
 * 小型代理池，共10个可使用代理
 * ips：代理IP
 * ports：对应IP端口
 * status：对应代理是否可用状态
 *
 */
public class ProxiesPool {

	private ArrayList<String> ips;
	private ArrayList<Integer> ports;
	private ArrayList<Boolean> status;
	private static ProxiesPool pool=null;
	
	private ProxiesPool()  {
		ips=new ArrayList<>();
		ports=new ArrayList<>();
		status=new ArrayList<>();
		FileReader fr = null;
		BufferedReader br=null;
		String s=null;
		try {
			fr=new FileReader("proxies.txt");
			br=new BufferedReader(fr);
			while((s=br.readLine())!=null)  {
				ips.add(s.substring(0, s.indexOf(":")));
				ports.add(Integer.parseInt(s.substring(s.indexOf(":")+1)));
				status.add(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fr!=null)  {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(br!=null)  {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static ProxiesPool getInstance()  {
		if(pool==null)  {
			pool=new ProxiesPool();
		}
		return pool;
	}
	
	//获取代理，随机取一个代理，如果可用则返回，不可用再取一次；循环10次仍未取到表明没有代理可用
	public  synchronized Proxy getProxy(Type type)  {
		for(int i=0;i<10;i++)  {
			int j=(int) (Math.random()*ips.size());
			if(status.get(j))  {
				status.set(j, false);
				Proxy proxy=new Proxy(type, new InetSocketAddress(ips.get(j), ports.get(j)));
				return proxy;
			}
		}
		return null;
	}
	
	//获取user-agent
	public synchronized String getUserAgent()  {
		String[] agents={"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; AcooBrowser; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
				"Mozilla/5.0 (Windows; U; MSIE 9.0; Windows NT 9.0; en-US)",
				"Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.2pre) Gecko/20070215 K-Ninja/2.1.1",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN) AppleWebKit/523.15 (KHTML, like Gecko, Safari/419.3) Arora/0.3 (Change: 287 c9dfb30)",
				""};
		String string=agents[(int) (Math.random()*6)];
		if(null==string || string=="")  {
			return agents[0];
		} else {
			return string;
		}
	}
}
