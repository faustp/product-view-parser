package com.bigmk.it.parser;

import com.bigmk.it.model.ProductInfo;
import com.bigmk.it.util.ParserUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by faust on 12/6/16.
 */
public class Parser implements IParser {

    SingletonProductList singletonProductList=SingletonProductList.getInstance();
    private final String URL;
    private List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();

    public Parser(String URL) {
        this.URL = URL;
    }

    public List<ProductInfo> execute(int proc1,int proc2, int proc3) throws IOException, ExecutionException, InterruptedException {

        try {
            doc = ParserUtil.connect(this.URL);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Exiting application...");
            System.exit(0);
        }
        Elements menuElements = doc.select("#menu_1895");
        Elements LiElements = menuElements.select("li");
        List<String> productList = new ArrayList<String>();
        for (Element LiElement : LiElements) {
            Elements DdElements = LiElement.select("dd");
            for (Element DdElement : DdElements) {
                Elements hrefElements = DdElement.select("a");
                for (Element hrefElement : hrefElements) {
                    productList.add(hrefElement.attr("href"));
                }
            }
        }
        List<String> productPages = visitProductList(productList,proc1);
        List<String> products = visitProductPage(productPages,proc2);
        return getProductInfo(products,proc3);
    }

    private List<String> visitProductList(List<String> productList,int nThread) throws IOException, ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(nThread);
        List<String> productPages = new ArrayList<String>();
        for (String product : productList) {
            Callable<List<String>> callable = new VisitProductList(this.URL, product);
            Future<List<String>> future = pool.submit(callable);
            productPages.addAll(future.get());
        }
        pool.shutdown();
        return productPages;
    }

    private List<String> visitProductPage(List<String> productPages,int nThread) throws IOException, ExecutionException, InterruptedException {
        List<String> productList = new ArrayList<String>();
        ExecutorService pool = Executors.newFixedThreadPool(nThread);
        for (String productPage : productPages) {
            Callable<List<String>> callable = new VisitProductPage(productPage);
            Future<List<String>> future = pool.submit(callable);
            productList.addAll(future.get());
        }
        pool.shutdown();
        return productList;
    }

    private List<ProductInfo> getProductInfo(List<String> productList,int nThread) throws IOException, ExecutionException, InterruptedException {
        List<ProductInfo> productInfos = new ArrayList<ProductInfo>();
        ExecutorService pool = Executors.newFixedThreadPool(nThread);
        for (String product : productList) {
            Callable<List<ProductInfo>> callable = new FetchProductInfo(this.URL.concat(product));
            Future<List<ProductInfo>> future = pool.submit(callable);
            productInfos.addAll(future.get());
        }
        pool.shutdown();
        return productInfos;
    }
}
