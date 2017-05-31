package com.bigmk.it.parser;

import com.bigmk.it.model.ProductInfo;
import com.bigmk.it.util.ParserUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by faust on 12/8/16.
 */
public class FetchProductInfo implements Callable<List<ProductInfo>> {

    private String urlProduct;

    public FetchProductInfo(String urlProduct) {
        this.urlProduct = urlProduct;
    }

    public List<ProductInfo> call() throws Exception {
        List<ProductInfo> productInfos = new ArrayList<ProductInfo>();
        Document productPageRef = ParserUtil.connect(this.urlProduct);
        String productName = productPageRef.select("body > div.wrapper.clearfix.cover-page-details > div > div.summary > h2").text();
        int productViews = Integer.valueOf(productPageRef.select("body > div.wrapper.clearfix.cover-page-details > div > div.summary > ul.u_infoname > li").get(2).text());
        ProductInfo productInfo = new ProductInfo(productName, productViews);
        productInfos.add(productInfo);
        System.out.println("Fetching Product Info -> " + productInfo);
        return productInfos;
    }
}
