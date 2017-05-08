package FinalAssignment.application;
//This is the clothing class for our final assignment. It accepts several
//parameters from the Database class in order to construct an object
//for each data entry. Through this object, we can get and set individual
//values for each piece of clothing. As well as print the raw data as 
//a string for testing purposes.
public class Clothing {
    
    private String name;
    private String type;
    private Double price;
    private Boolean available;
    private String serial;
    private String size;
    

    public Clothing(String name, String type, 
        Double price, Boolean available,
        String serial,String size){
        setName(name);
        setType(type);
        setPrice(price);
        setAvailable(available);
        setSerial(serial);
        setSize(size);
    }
    
    public String getName(){
        return name;       
    }
    public String getType(){
        return type;       
    }
    public Double getPrice(){
        return price;
    }
    public Boolean getAvailable(){
        return available;       
    }
    public String getSerial(){
        return serial;       
    }
    public String getSize(){
        return size;       
    }
    public void setName(String name){
        this.name = name;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setPrice(Double price){
        this.price = price;
    }
    public void setAvailable(Boolean available){
        this.available = available;
    }
    public void setSerial(String serial){
        this.serial = serial;
    }
    public void setSize(String size){
        this.size = size;
    }
    
    @Override
    public String toString(){
        String text = "";
        text += getName() + "\n" + getType() + "\n" +
                Double.toString(getPrice()) + "\n" +
                Boolean.toString(getAvailable()) + "\n" +
                getSerial() + "\n" + getSize();
        return text; 
    }
    
}
