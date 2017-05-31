package com.bigmk.it.parser;

import com.bigmk.it.util.ParserUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by faust on 12/8/16.
 */
public class VisitProductPage implements Callable<List<String>> {

    private String url;

    public VisitProductPage(String url) {
        this.url = url;
    }

    public List<String> call() throws Exception {
        List<String> productList = new ArrayList<String>();
        Document productPageRef = ParserUtil.connect(this.url);
        Elements productElements = productPageRef.select("#cate-list > ul > li");
        for (Element productElement : productElements) {
            productList.add(productElement.select("div.goodpic > a").attr("href"));
        }
        System.out.println("Fetching Product list -> " + productList);
        return productList;
    }
}
