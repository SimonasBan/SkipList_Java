/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektas_skiplist;

import Utils.Ks;
import java.util.Locale;
import java.util.LinkedList;

/**
 *
 * @author Simonas
 */
public class Benchmark {
    
    int memUsed = 0;
    private BooksGenerator booksGenerator = new BooksGenerator();
    SL<Book> BooksSL = new SL<Book>();
    LinkedList<Book> BooksLinked = new LinkedList<>();
    private int[] keys;
    
    private Book[] booksArray;
    
    private void calculateCurrentMemUsed(){
        System.gc();
        System.gc();
        System.gc();
        long memTotal = Runtime.getRuntime().totalMemory();
        long memFree  = Runtime.getRuntime().freeMemory();
        memUsed = (int) (memTotal - memFree);
    }
    
    private void memDifference(String text){
        System.gc();
        System.gc();
        System.gc();
        long memTotal = Runtime.getRuntime().totalMemory();
        long memFree  = Runtime.getRuntime().freeMemory();
        int memUsed1 = (int) (memTotal - memFree);
        System.out.println(String.format(text + " užima =\t%,6d ",
                (memUsed1 - memUsed)));
        memUsed = memUsed1;

    }
    
    
    private void execute(){
//        //booksArray = booksGenerator.generateShuffle(50, 40, 1
        System.gc();
        System.gc();
        System.gc();
        System.gc();
        System.gc();
        memDifference("tuščia");
        memDifference("tuščia");
        memDifference("tuščia");
        memDifference("tuščia");
        memDifference("tuščia");
        memDifference("tuščia");
     
        booksArray = booksGenerator.generateShuffle(4000, 1);
      
        memDifference("booksArray");
        memDifference("booksArray");
        memDifference("booksArray");
        memDifference("booksArray");
        memDifference("booksArray");
        memDifference("booksArray");
        keys = new int[booksArray.length];
//        
//        
//        keys = new String[booksArray.length];
//        
//        memDifference("keys");
//        memDifference("keys");
//        memDifference("keys");
//        
//        
//        
//        
//        for (int i = 1; i < 501; i++) {
//            String temp = "B" + i;
//            keys[i] = temp;
//        }
//        
        int count = 0;
        for (Book b : booksArray) {
            BooksSL.add(count, b);
            keys[count] = count;
            count++;
        }
        memDifference("SkipList");
        
        for (Book b : booksArray) {
            BooksLinked.add(b);
        }
        
        memDifference("LinkedList");
        
        double[] benchesSLContainsValue = skipListContainsValue();
        double[] benchesLinked = linkedListContainsValue();
        printBench(benchesSLContainsValue, benchesLinked, "ContainsValue");
        
        
        
        
//        
//        double[] ContainsValueOaObj = mapOaValueObj();
//        double[] ContainsValueUtilsObj = mapUtilValueObj();
//        benchContainsValueObj(ContainsValueOaObj, ContainsValueUtilsObj);
//        
//        double[] ContainsKeyOaObj = mapOaKeyObj();
//        double[] ContainsKeyUtilsObj = mapKeyObj();
//        benchContainsKeyObj(ContainsKeyOaObj, ContainsKeyUtilsObj);
//        
//        
//        stringHash = ReadFile(stringHashSize);
//        
//        double[] ContainsValueOaString = mapOaValueString();
//        double[] ContainsValueUtilsString = mapUtilValueString();
//        benchContainsValueString(ContainsValueOaString, ContainsValueUtilsString, stringHashSize);
//        
//        
//        double[] ContainsKeyOaString = mapOaKeyString();
//        double[] ContainsKeyUtils = mapKeyString();
//        benchContainsKeyString(ContainsKeyOaString, ContainsKeyUtils, stringHashSize);
    }
    
    //Kadangi booksArray jau sumaisytas ir taip, o skiplist yra rikiuotas, tai
    //contains tikrinamas su nariais is ivairiu saraso vietu
    private double [] skipListContainsValue(){


        double[] benches = new double[BooksSL.getSize()/4];

        for (int i = 0; i < BooksSL.getSize()/4; i++) {
            benches[i] = benchSLContains(BooksSL, booksArray[i]);
        }


        return benches;
    }
    
    private double [] linkedListContainsValue(){


        double[] benches = new double[BooksLinked.size()/4];

        for (int i = 0; i < BooksLinked.size()/4; i++) {
            benches[i] = benchLinkedListContains(BooksLinked, booksArray[i]);
        }


        return benches;
    }
    
    private void printBench(double[] skipList, double [] linkedList, String name){
        Ks.oun(name + " greitaveika");
        Ks.oun("     SkipList             LinkedList           Skirtumas      objekto vieta sąraše");
        
        for (int i = 0; i < skipList.length; i++) {
            Ks.ouf(String.format("%20.10f %20.10f %20.10f    ", skipList[i], linkedList[i], (skipList[i]-linkedList[i])) + booksArray[i].getBookRegNr() + "\n");
        }
        
        Ks.ouf("\n");
        Ks.oun("    SkipList average     LinkedList average");
        Ks.ouf("%20.10f %20.10f", getAverage(skipList), getAverage(linkedList));
        
        Ks.ouf("\n");
        Ks.ouf("\n");
    }
    
    private double getAverage(double [] bench){
        double sum = 0;
        double average = 0;
        for (double t : bench) {
            sum += t;
        }
        average = sum / bench.length;
        return average;
    }
    
    
    
    
    private double benchSLContains(SL<Book> list, Book book){
        long t0 = System.nanoTime();
        list.contains(book);
        long t1 = System.nanoTime();
        
        double bench = (t1 - t0) / 1e9;
        return bench;
    }
    
    
    private double benchLinkedListContains(LinkedList<Book> list, Book book){
        long t0 = System.nanoTime();
        list.contains(book);
        long t1 = System.nanoTime();
        
        double bench = (t1 - t0) / 1e9;
        return bench;
    }
    
    
    
    
    public static void main(String[] args) {
        
        Locale.setDefault(new Locale("LT"));
        new Benchmark().execute();
    }
    
}
