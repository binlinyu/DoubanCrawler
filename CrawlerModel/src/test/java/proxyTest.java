import java.net.Proxy;

import org.junit.Test;

import proxy.ProxiesPool;

public class proxyTest {

	@Test
	public void test() {
		for (int i = 0; i < 15; i++) {
			Proxy proxy=ProxiesPool.getInstance().getProxy(Proxy.Type.HTTP);
			if(proxy!=null)  {
				System.out.println("get proxy");
			} else {
				System.out.println("fail to get");
			}
		}
		
	}

}
