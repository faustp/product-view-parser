package com.bigmk.it;

import com.bigmk.it.export.CsvWriter;
import com.bigmk.it.parser.Parser;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by faust on 12/6/16.
 */
public class Application implements IApplication {

    public static void main(String args[]) {
        System.out.println(TITLE);
        int proc1 = DEFAULT_NUMBER_OF_THREAD;
        int proc2 = DEFAULT_NUMBER_OF_THREAD;
        int proc3 = DEFAULT_NUMBER_OF_THREAD;
        Parser parser = new Parser(URL);
        if (args.length == 3) {
            proc1 = Integer.valueOf(args[0].trim());
            proc2 = Integer.valueOf(args[1].trim());
            proc3 = Integer.valueOf(args[2].trim());
        }
        System.out.println("Thread Process :: " + "[proc1=" + proc1 + " ,proc2=" + proc2 + " ,proc3=" + proc3 + "]");
        CsvWriter csvWriter = null;
        try {
            csvWriter = new CsvWriter(parser.execute(proc1, proc2, proc3));
            csvWriter.execute();
            System.out.println("Done!");
        } catch (IOException e) {
            System.out.println("Your application Failed Successfully!!!");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
