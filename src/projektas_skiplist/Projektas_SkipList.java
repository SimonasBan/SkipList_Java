/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektas_skiplist;

import Utils.Ks;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 *
 * @author Simonas
 */
public class Projektas_SkipList {
    
    
     private void execute(){
         //Random generatorFloat = new Random();
        
        //int[] indexes = new int[1000];
        //SL<Float> skipList = new SL<>();
        //float[] skipListTest = new float[32000];

//        for (int i = 0; i < 4000; i++) {
//            float obj = generatorFloat.nextFloat();
//            skipList.add(i, obj);
//            skipListTest[i] = obj;
//        }
//        
//        
//        int k = 4000 - 2000;
//        for (int i = 0; i < indexes.length; i++) {
//            //indexes[i] = generator.nextInt(k);
//            indexes[i] = i;
////            k++;
//        }
//        
//        
//        for (int i : indexes) {
//            skipList.getByKey(i);
//        }
        

        //-------------------------
        BooksGenerator generator = new BooksGenerator();
        Book[] bookArray = generator.generateShuffle(50, 1);
        
        Book b1 = new Book("GeorgeOrwell", "1984", 2002, 1000, 15);
        Book b2 = new Book("GeorgeOrwell AnimalFarm 2000 500 10");
        Book b3 = new Book("NeilPostman AmusingOurselvesToDeath 2006 1000 25");
        Book b4 = new Book("RayBradbury TheMartianChronicles 2001 9000 22");
        Book b5 = new Book("AntoineDeSaint-Exupéry TheLittlePrince 1995 3000 12");
        Book b6 = new Book("J.D.Salinger TheCatcherInTheRye 2005 1001 24");
        
        
        SL<Book> BooksSL = new SL<Book>();
        
        Ks.oun("Knygų sąrašas");
        for (int i = 0; i < bookArray.length; i++) {
            BooksSL.add(bookArray[i].getBookRegNr(), bookArray[i]);
            Ks.oun(bookArray[i]);
        }

        Ks.oun("SkipList.Print()");
        BooksSL.Print();
        
        Ks.oun("KeyOf");
        Ks.oun(BooksSL.keyOf(bookArray[10]));
        
        Ks.oun("Contains: " + bookArray[2]);
        Ks.oun(BooksSL.contains(bookArray[2]));
        Ks.oun("remove: " + bookArray[1]);
        Ks.oun(BooksSL.remove(bookArray[1]));
        Ks.oun("remove: " + bookArray[3]);
        Ks.oun(BooksSL.remove(bookArray[3]));
        
        Ks.oun("SkipList.Print()");
        BooksSL.Print();    
        
        Ks.oun("GetByIndex");
        Ks.oun(BooksSL.getByIndex(10));
        
        //Ks.oun(BooksSL.contains(bookArray[2]));
        
        Ks.oun("ToArray");
        Object[] array = BooksSL.toArray();
        
        for (Object o : array) {
            Ks.oun(o);
        }
        
        Ks.oun("Clear");
        BooksSL.clear();
        
        Ks.oun("SkipList.Print()");
        BooksSL.Print();   
       
        Ks.oun("add: " + b6);
        BooksSL.add(b1.getBookRegNr(), b1);
        BooksSL.add(b2.getBookRegNr(), b2);
        BooksSL.add(b3.getBookRegNr(), b3);
        BooksSL.add(b4.getBookRegNr(), b4);
        
        
        Ks.oun("SkipList.Print()");
        BooksSL.Print();   
        
        Ks.oun("Set");
        Ks.oun(BooksSL.set(b2.getBookRegNr(), b5));
        
        Ks.oun("SkipList.Print()");
        BooksSL.Print();  
        
//        SL<String> stringSL = new SL<>();
//        
//        String[] words = ReadFile(10, stringSL);
//        
//        stringSL.Print();
//        
//        Ks.oun(stringSL.containsAmount(words[0]));

//        String a = "Antanas";
//        char c = a.charAt(0);
//        int b = (int)c;
//        Ks.oun(b);
        
        //x
         
     }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        new Projektas_SkipList().execute();
        
        
    }
    
    void  print(){
        
    }
    
    public String[] ReadFile(int k, SL<String> list) {
        String [] words = new String[k];
        try (BufferedReader br = Files.newBufferedReader(Paths.get("data\\test.txt"))) {

            String line;
            int nr = 0;
            while ((line = br.readLine()) != null && nr < k) {
                int keyChar = line.charAt(0);
                list.add((int)keyChar, line);
                words[nr] = line;
                nr++;
            }
            return words;
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return null;
    }
    
}
