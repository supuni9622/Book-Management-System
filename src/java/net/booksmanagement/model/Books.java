 
package net.booksmanagement.model;

public class Books {
    
    protected int id;
    protected String name;
    protected String price;
    protected String author;
    
    public Books() {}
    
    public Books (int id ,String name, String price, String author ){
        super();
        this.id = id;
        this.name = name;
        this.price= price;
        this.author = author;
    }
    
    public Books (String name, String price, String author ){
        super();
        this.name = name;
        this.price= price;
        this.author = author;
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id; 
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name; 
    }
    
    public String getPrice(){
        return price;
    }
    
    public void setPrice(String price){
        this.price = price; 
    }
    
    public String getAuthor(){
        return author;
    }
    
    public void setAuthor(String author){
        this.author = author; 
    }
    
}
