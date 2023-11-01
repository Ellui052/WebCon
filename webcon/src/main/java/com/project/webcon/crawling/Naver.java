package com.project.webcon.crawling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Naver {
    public static void main(String[] args) {
        try {
            String num = getNum();
            String URL = "https://comic.naver.com/webtoon/list?titleId=" + num;
            Document doc = Jsoup.connect(URL).get();


            String platform = getContentByTag(doc, "meta", 3);
            String title = getContentByTag(doc, "meta", 5);
            String img = getContentByTag(doc, "meta", 6);
            String toonInfo = getContentByTag(doc, "meta", 7);

            String INFO_URL = "https://search.naver.com/search.naver?where=nexearch&sm=top_sug.pre&fbm=0&acr=1&acq=%EA%B7%B8+%EA%B8%B0%EC%82%AC%EA%B0%80+%E3%84%B9&qdt=0&ie=utf8&query=" + title;

            Document info_doc = Jsoup.connect(INFO_URL).get();

            HashMap<String, String> info = new HashMap<>();
            Elements info_groups = info_doc.getElementsByClass("info_group");

            String target[] = "작가,글,그림,원작,연재,장르".split(",");
            ArrayList<String> arr = new ArrayList<>(Arrays.asList(target));

            for (Element el1 : info_groups) {
                try {
                    Element el2 = el1.getElementsByTag("dt").get(0);
                    Element el3 = el1.getElementsByTag("dd").get(0);
                    if (arr.contains(el2.text())) {
                        info.put(el2.text(), el3.text());
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

            List<String> infoList = getInfoByTag(info_doc, "dd");

            platform = "naver";
            String day = ""   ;
            switch (info.get("연재")) {
                case "월요웹툰":
                    day = "mon";
                    break;
                case "화요웹툰":
                    day = "tue";
                    break;
                case "수요웹툰":
                    day = "wed";
                    break;
                case "목요웹툰":
                    day = "thu";
                    break;
                case "금요웹툰":
                    day = "fri";
                    break;
                case "토요웹툰":
                    day = "sat";
                    break;
                case "일요웹툰":
                    day = "sun";
                    break;
            }

            String author = info.get("작가") == null ? info.get("글") + ", " + info.get("그림") : info.get("작가");

            insertDB(num, platform, title, img, toonInfo, author, day, info.get("장르"));
        } catch (Exception e) {
            System.out.println("정보 입력중 오류가 발생했어요 !");
            e.printStackTrace();
        }
    }

    private static void insertDB(String num, String platform, String title, String img, String info, String authors, String day, String genre) {
        String jdbcURL = "jdbc:mysql://localhost:3306/webcon?serverTimezone=UTC";
        String username = "root";
        String password = "1234";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            String insertQuery = "INSERT INTO webtoon VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, num);
                preparedStatement.setString(2, platform);
                preparedStatement.setString(3, title);
                preparedStatement.setString(4, img);
                preparedStatement.setString(5, info);
                preparedStatement.setString(6, authors);
                preparedStatement.setString(7, day);
                preparedStatement.setString(8, genre);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println(title + "의 정보가 업로드 되었습니다.");
                } else {
                    System.out.println(title + "의 정보가 업로드 되지 않았습니다.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getNum() {
        String num = "702608";
        return num;
    }
    private static String getContentByTag(Document doc, String val, int i) {
        Elements elements = doc.getElementsByTag(val);
        if (i >= 0 && i < elements.size()) {
            Element element = elements.get(i);
            return element.attr("content");
        }
        return null;
    }

    private static List<String> getInfoByTag(Document doc, String val) {
        Elements elements = doc.getElementsByTag(val);
        List<String> result = new ArrayList<>();

        for (Element element : elements) {
            result.add(element.text());
        }

        return result;
    }
}