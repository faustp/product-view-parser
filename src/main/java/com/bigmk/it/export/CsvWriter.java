package com.bigmk.it.export;

import com.bigmk.it.model.ProductInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by faust on 12/6/16.
 */
public class CsvWriter {

    private static final String FILE_LOCATION=System.getProperty("user.dir").
            concat(File.separator).concat("ProductViews.xlsx");
    private List<ProductInfo> productInfoList;

    public CsvWriter(List<ProductInfo> productInfoList) {
        this.productInfoList = productInfoList;
    }

    public void execute() throws IOException {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("ProductViews");
        int irow=0;
        Row row = sheet.createRow(irow);
        Cell productLabel=row.createCell(irow);
        productLabel.setCellValue("Product Name");
        Cell productViewslabel = row.createCell(1);
        productViewslabel.setCellValue("Views");
        irow+=1;
        System.out.println("Writing to excel...");
        if(productInfoList!=null) {
            for (ProductInfo productInfo : productInfoList) {
                row = sheet.createRow(irow);
                Cell productName = row.createCell(0);
                productName.setCellValue(productInfo.getName());
                Cell productViews = row.createCell(1);
                productViews.setCellValue(productInfo.getViews());
                irow++;

            }
        }
        sheet.autoSizeColumn(0);
        book.write(new FileOutputStream(FILE_LOCATION));
        book.close();
    }
}
