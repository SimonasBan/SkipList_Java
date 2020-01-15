package projektas_skiplist;




import Utils.Ks;
import Utils.Parsable;

import java.time.LocalDate;
import java.util.*;


public final class Book implements Parsable<Book> {

    // bendri duomenys visai klasei
    final static private int minYear = 2000;

    private static final double minPrice = 5.0;
    private static final double maxPrice = 30.0;
    
    private static final int currentYear = LocalDate.now().getYear();
    private static final String idCode = "KN";   //  ***** nauja
    private static int serNr = 100;               //  ***** nauja

    private final int bookRegNr;

    // individualūs duomenys
    private String author = "";
    private String bookName = "";
    private int year = -1;
    private int bumberOfCopies = -1;
    private double price = -1.0;
    

    public Book() {
        bookRegNr = serNr++;
    }

    public Book(String author, String bookName,
            int year, int bumberOfCopies, double price) {
        bookRegNr = serNr++;
        this.author = author;
        this.bookName = bookName;
        this.year = year;
        this.bumberOfCopies = bumberOfCopies;
        this.price = price;
        validate();
    }

    public Book(String data) {
        bookRegNr = serNr++;
        this.parse(data);
        validate();
    }
    
    public Book(Builder builder) {
        bookRegNr = serNr++;
        this.author = author;
        this.bookName = bookName;
        this.year = year;
        this.bumberOfCopies = bumberOfCopies;
        this.price = price;
        validate();
    }
    
    public Book create(String dataString) {
        return new Book(dataString);
    }

    @Override
    public final void parse(String data) {
        try {   // ed - tai elementarūs duomenys, atskirti tarpais

            Scanner scanner = new Scanner(data);
            author = scanner.next();
            bookName = scanner.next();
            year = scanner.nextInt();
            setNumberOfCopies(scanner.nextInt());
            setPrice(scanner.nextDouble());
        } catch (InputMismatchException e) {
            Ks.ern("Blogas duomenų formatas apie knygą -> " + data);
        } catch (NoSuchElementException e) {
            Ks.ern("Trūksta duomenų apie knygą -> " + data);
        }
    }

    public String validate() {
        String error = "";
        //int currentYear = LocalDate.now().getYear();
        if (year < minYear || year > currentYear) {
            error = "Netinkami išleidimo metai, turi būti ["
                    + minYear + ":" + currentYear + "]";
        }
        if (price < minPrice || price > maxPrice) {
            error += " Kaina už leistinų ribų [" + minPrice
                    + ":" + maxPrice + "]";
        }
        return error;
    }

    @Override
    public String toString() {  // surenkama visa reikalinga informacija
        return String.format("%s  %-8s %-8s", bookRegNr,
                author, bookName);
    }

    public String getAuthor() {
        return author;
    }

    public String getBookName() {
        return bookName;
    }

    public int getYear() {
        return year;
    }

    public int getNumberOfCopies() {
        return bumberOfCopies;
    }
    
    public void setNumberOfCopies(int number) {
        this.bumberOfCopies = number;
    }

    public double getPrice() {
        return price;
    }
    

    // keisti bus galima tik kainą - kiti parametrai pastovūs
    public void setPrice(double price) {
        this.price = price;
    }

    public int getBookRegNr() {  //** nauja.
        return bookRegNr;
    }
    
    @Override
    public int compareTo(Book otherBook) {

        if(getBookRegNr() > otherBook.getBookRegNr()){
            return 1;
        }
        if(getBookRegNr() < otherBook.getBookRegNr()){
            return -1;
        }
        return 0;
               
    }
    
    public static Comparator<Book> byAuthor = (Book b1, Book b2) -> b1.author.compareTo(b2.author);
    
    public static Comparator<Book> byPrice = (Book b1, Book b2) -> {
        // didėjanti tvarka, pradedant nuo mažiausios
        if (b1.price < b2.price) {
            return -1;
        }
        if (b1.price > b2.price) {
            return +1;
        }
        return 0;
    };

    public static Comparator<Book> byYearPrice = (Book b1, Book b2) -> {
        // metai mažėjančia tvarka, esant vienodiems lyginama kaina
        if (b1.year > b2.year) {
            return +1;
        }
        if (b1.year < b2.year) {
            return -1;
        }
        if (b1.price > b2.price) {
            return +1;
        }
        if (b1.price < b2.price) {
            return -1;
        }
        return 0;
    };
    



    // metodas main = tiesiog paprastas pirminis automobilių išbandymas
    public static void main(String... args) {
        // suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
        Locale.setDefault(new Locale("LT"));
        Book a1 = new Book("GeorgeOrwell", "1984", 2002, 1000, 15);
        Book a2 = new Book("UmbertoEco", "TheNameOfTheRose", 1999, 500, 4);
        Book a3 = new Book();
        Book a4 = new Book();
        a3.parse("GeorgeOrwell AnimalFarm 2000 500 10");
        Ks.oun(a1);
        Ks.oun(a2);
        Ks.oun(a3);
        Ks.oun(a4);
    }
    
    
    public static class Builder {

        private final static Random RANDOM = new Random(1949);  // Atsitiktinių generatorius
        private final static String[][] BOOKS = { // galimų automobilių markių ir jų modelių masyvas
            {"GeorgeOrwell", "1984", "AnimalFam"},
            {"RayBradbury", "TheMartianChronicles", "Fahrenheit451", "DandelionWine"},
            {"UmbertoEco", "TheNameOfTheRose", "HowToWriteAThesis"},
            {"NeilPostman", "AmusingOurselvesToDeath"}
        };

        private String author = "";
        private String bookName = "";
        private int year = -1;
        private int bumberOfCopies = -1;
        private double price = -1.0;

        public Book build() {
            return new Book(this);
        }

        public Book buildRandom() {
            int ma = RANDOM.nextInt(BOOKS.length);        // markės indeksas  0..
            int mo = RANDOM.nextInt(BOOKS[ma].length - 1) + 1;// modelio indeksas 1..
            return new Book(BOOKS[ma][0],
                    BOOKS[ma][mo],
                    1984 + RANDOM.nextInt(20),
                    1000 + RANDOM.nextInt(222000),
                    2.1 + RANDOM.nextDouble() * 88000);
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder bookName(String bookName) {
            this.bookName = bookName;
            return this;
        }

        public Builder bumberOfCopies(int bumberOfCopies) {
            this.bumberOfCopies = bumberOfCopies;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }
    }
    
    
    
    
}
