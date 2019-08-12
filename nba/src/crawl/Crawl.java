package crawl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawl {
	public static String[] getList() {
		// 파일 객체 생성
		File file = new File("C:\\0.encore\\01.java\\nba\\stats.txt");
		// 입력 스트림 생성
		FileReader filereader = null;
		try {
			filereader = new FileReader(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 입력 버퍼 생성
		BufferedReader bufReader = new BufferedReader(filereader);
		String line = "";

		try {
			line = bufReader.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] e = line.split("\t");
		return e;
	}

	public static void main(String[] args) {
		System.out.println(Arrays.toString(getList()));
		Document doc = null;
		Document doc2 = null;
		try {
			String[] first = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
			BufferedWriter fw = new BufferedWriter(new FileWriter("C:\\\\0.encore\\\\01.java\\\\nba\\\\PlayerStats.txt", true));
			String address = "https://www.basketball-reference.com";
			for(String f : first) {
				try {
					doc = Jsoup.connect("https://www.basketball-reference.com/players/"+f+"/").get();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println(doc.title());
				Elements newsHeadlines = doc.select("#players > tbody > tr");
				int index = 0;
				
				for (Element headline : newsHeadlines) {
					String Address2 = null;
					try {
	//				Address2= Address+headline.select(" th >a").attr("href");
						doc2 = Jsoup.connect("https://www.basketball-reference.com" + headline.select(" th >a").attr("href"))
								.get();
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println(doc2.select("div > h1"));
					Elements stats = doc2.select("table > tbody > tr");
					if(stats.size()>5) {
						for (int i = 0; i<stats.size(); i++) {
							Elements stats2 = stats.get(i).select("td");
							try {
								if(doc2.select("div > h1").text().charAt(0)!='b' && doc2.select("div > h1").text().charAt(1) !='a' && stats2.get(2).text().equals("NBA") ) {
									fw.write(doc2.select("div > h1").text());
									for (Element e : stats2) {
										fw.write("\t"+e.text());
//										System.out.println(stats2.get(2).text());
									}
									fw.write("\n");
								}
							} catch (Exception e) {
								
							}
						}
					}
	//			System.out.println(stats.get(0).select("td").get(12).text());
	//			for (Element e : stats) {
	//				System.out.println(e);
	//			}
				}
			}
			fw.flush();
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
