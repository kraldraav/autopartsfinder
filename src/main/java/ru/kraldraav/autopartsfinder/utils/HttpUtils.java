package ru.kraldraav.autopartsfinder.utils;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

@Log4j2
public class HttpUtils {
    public static String getHtmlPage(String requestUrl){
        var client = new OkHttpClient();
        var request = new Request.Builder().url(requestUrl).build();

        try{
            var response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException exception){
            log.error("Unable to receive HTML body! %s".formatted(exception.getMessage()));
        }

        return null;
    }

    @SneakyThrows
    public static Document getHtmlDocument(String requestUrl){
        return Jsoup.connect(requestUrl).userAgent("Mozilla/5.0").get();
    }
    public static Document getDocument(String htmlPage){
        return Jsoup.parse(htmlPage);
    }
}
