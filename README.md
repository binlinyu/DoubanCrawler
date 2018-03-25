# DoubanCrawler
简介：本仓库目的是拉取豆瓣网“编程”方面的书籍信息（网址：https://book.douban.com/tag/编程?type=S），获取评价人数超过1000且评分最高的40本图书。

思想：在“编程”书籍首页获取所有图书的网址，然后依次访问每个图书网页，把评价人数超过1000的存入集合，最后按评分排序。
      为加快爬取速度，又不被封IP，采用多线程+使用代理的方式。

主要类说明：entity.Book.java:图书信息实体类,实现Comparable接口，按评分排序。
	proxy.ProxyPool.java:代理池类，使用单例模式，从proxies.txt文件获取可用代理。
	spiders.Spider.java:实现Runnable接口，可爬取每页图书信息,多线程共享静态变量INDEX，实现对图书的多页面同时访问。