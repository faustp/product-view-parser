package com.bigmk.it.parser;

import com.bigmk.it.model.ProductInfo;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by faust on 12/6/16.
 */
public interface IParser {

    int RETRY_COUNT = 3;

    List<ProductInfo> execute(int proc1,int proc2, int proc3) throws IOException, ExecutionException, InterruptedException;
}
