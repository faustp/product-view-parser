package com.bigmk.it.util;

import com.bigmk.it.parser.IParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by faust on 12/8/16.
 */
public final class ParserUtil {


    public static Document connect(String url) throws IOException {
        Document doc = null;
        int retryCount = IParser.RETRY_COUNT;
        while (retryCount > 0) {
            try {
                doc = Jsoup.connect(url).get();
                retryCount = 0;
            } catch (IOException e) {
                retryCount--;
                System.out.println("Connection failed -> " + url);
                System.out.println("Reconnecting to -> " + url + " [" + retryCount + "]");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if (doc == null) {
            throw new IOException("Please check your internet connection.");
        }
        return doc;
    }

}
