package FinalAssignment.application;
//This is the database class. This class is integral in the operation of
//the proram. It accepts a String as an argument which represents the string
//file that will be read. The database reads the text file and stores its data
//in an arraylist comprised of Clothing objects, as shown by the Clothing class.
//There is also a method designed to grab the entire arraylist and return it
//so that our fxml documents may interact with this class.
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;

public class Database {
    
    private ArrayList<Clothing> clothes = new ArrayList<>();;
    private String url = "";
    
    public Database(){
        
    }
    public Database(String url) throws Exception{
        ReadToDatabase("url");     
    }
    
    public void ReadToDatabase(String url) throws Exception{
        this.url = url;
        BufferedReader in = 
                new BufferedReader(new FileReader(url));
        for(String line = in.readLine(); line!=null; line = in.readLine()){
            String[] items = new String[6];
            items[0] = line;
            for(int x = 1; x < 6; x++){
                items[x] = in.readLine();
            }
            clothes.add(new Clothing(items[0], items[1],
                    Double.parseDouble(items[2]), Boolean.parseBoolean(items[3]),
                    items[4], items[5]));
        }
        in.close();
    }
    //This class writes data to the text file after the user has edited and 
    //viewed what content they desired. It performs several checks to ensure
    //that the data being passed through is valid.
    public void WriteFromDatabase(String name, Boolean a, Double n, String s) throws Exception{
        for(int x = 0; x < clothes.size(); x++){
            if (name.equals(clothes.get(x).getName()))
                clothes.get(x).setAvailable(a);
        }
        
        for(int x = 0; x < clothes.size(); x++){
            if (name.equals(clothes.get(x).getName()))
                try{
                    String doub = String.format("%.2f", n);
                    clothes.get(x).setPrice(Double.parseDouble(doub));               
                } catch(Exception e) {
                    System.out.println("Invalid Double, Printing old price");
                }
        }
        
        String serial = "";
        for(int x = 0; x < clothes.size(); x++){
            if (name.equals(clothes.get(x).getName()))
                for(int y = 0; y < 9; y++){
                    if((Character.isDigit(s.charAt(y)))){
                        serial = serial + s.charAt(y);
                    }
                    if((Character.isLetter(s.charAt(y)))){
                        serial = serial + Character.toUpperCase(s.charAt(y));
                    }
                }
            if (serial.length() == 9){
                clothes.get(x).setSerial(s); 
                x = clothes.size() + 1;
            }
            else
                System.out.println("Invalid String, Printing old serial");
        }
        
        BufferedWriter out = 
                new BufferedWriter(new FileWriter(this.url));
        for (int y = 0; y < clothes.size(); y++){
            out.write(clothes.get(y).getName());
            out.newLine();
            out.write(clothes.get(y).getType());
            out.newLine();
            out.write(Double.toString(clothes.get(y).getPrice()));
            out.newLine();
            out.write(Boolean.toString(clothes.get(y).getAvailable()));
            out.newLine();
            out.write(clothes.get(y).getSerial());
            out.newLine();
            out.write(clothes.get(y).getSize());
            out.newLine();
        }  
        out.close();
    }
    //This method is a toString designed for testing purposes. 
    @Override
    public String toString(){
        String list = "";
        for (int i = 0; i < clothes.size(); ++i) {
            list += clothes.get(i).getName() + "\n";
        }
        return list;
    }
    public ArrayList grabList(){
        return clothes;
    }
}
