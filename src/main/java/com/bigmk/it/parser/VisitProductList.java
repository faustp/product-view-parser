package com.bigmk.it.parser;

import com.bigmk.it.util.ParserUtil;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by faust on 12/8/16.
 */
public class VisitProductList implements Callable<List<String>> {

    private String url;
    private String referencePage;

    public VisitProductList(String url,String referencePage) {
        this.url = url;
        this.referencePage=referencePage;
    }

    public List<String> call() throws Exception {
        Document document = ParserUtil.connect(this.url.concat(referencePage));
        String strPageNumber =document.select("#cate-list > div > span").text().replaceAll("\\stotal:.*", "");
        int totalPageNumber=0;
        if(strPageNumber.contains("/")){
            totalPageNumber=Integer.valueOf(strPageNumber.split("/")[1]);
        }
        List<String> productPages = new ArrayList<String>();
        productPages.add(this.url.concat(referencePage));
        if (totalPageNumber > 1) {
            for (int index = 2; index <= totalPageNumber; index++) {
                productPages.add(this.url.concat(referencePage.concat("?page=" + index)));
            }
        }
        System.out.println("Fetching Product Pages -> " + productPages);
        return productPages;
    }
}
