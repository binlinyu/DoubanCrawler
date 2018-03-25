package entity;

public class Book implements Comparable<Book>{

	private String name;  //书名
	private double score;  //评分
	private int num;  //评价人数
	private String author;  //作者
	private String press;  //出版社
	private String date; //出版日期
	private String price;  //价格
	
	public Book(String name, double score, int num, String author, String press, String date, String price) {
		super();
		this.name = name;
		this.score = score;
		this.num = num;
		this.author = author;
		this.press = press;
		this.date = date;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public int compareTo(Book o) {
		if(score>o.getScore())  {
			return -1;
		} else {
			return 1;
		}
	}
	
	
}
