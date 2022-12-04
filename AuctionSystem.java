/* @author Tahseen Zaman
 *  ID : 114332480
 *  Recitation : 03
 */
import java.util.*;
import java.io.*;
public class AuctionSystem {
    public static void main(String[] args) { 
        Auction auction = new Auction();
        AuctionTable auctionTable = initializeAuctionTable();
        if (auctionTable == null) {
            auctionTable = new AuctionTable();
        }
        
        Scanner reader = new Scanner(System.in);
        System.out.print("Please select a username : ");
        String username = reader.nextLine();
        
        String menu = "    " + "(D) - Import Data from URL\n" + "    " +  "(A) - Create a New Auction\n" + "    " 
        +  "(B) - Bid on an Item\n"  + "    " + "(I) - Get Info an Auction\n" + "    " +  "(P) - Print All Auctions\n"
         + "    " +  "(R) - Remove Expired Auctions\n" + "    " + "(T) - Let Time Pass\n" + "    " +  "(Q) - Quit";
         
        String option = "";
        
        
        while (option != "Q"){
            
            System.out.println(menu);
            
            System.out.println("Please Select an option : ");
            option = reader.nextLine().toUpperCase();
            
            switch(option){
                
                case "D":  
                System.out.print("Please enter a URL : ");
                String url = reader.nextLine();
                System.out.println("\nLoading...");
                try{
                    auctionTable = AuctionTable.buildFromURL(url);
                    System.out.println("Auction data loaded successfully");

                }catch(IllegalArgumentException e){
                    System.out.println("Auction data can't load");
                }
                
                break;

                case "A" :
                System.out.println("Creating new Auction as " + username);
                System.out.println("Please enter an Auction ID: ");
                String id = reader.nextLine();
                System.out.println("Please enter an Auction time (hours):");
                int auctionTime = reader.nextInt();
                reader.nextLine();
                System.out.println("Please enter some Item Info:");
                String itemInfo = reader.nextLine();
                
                auction = new Auction(auctionTime, 0.0, id, username, "", itemInfo );
                auctionTable.putAuction(id, auction);
                System.out.println("Auction" + " " + id + " inserted into table.");
                
                break;
                
                case "B":
                System.out.println("Please enter an Auction ID : ");
                id = reader.nextLine();
                auction = auctionTable.get(id);
                if(auction == null){
                    System.out.println("The auction doesn't exist");
                }
                else if (auction.getTimeRemaining() == 0){
                    System.out.println("The bid is closed");
                }
                else{
                    System.out.println("Auction" + id + " is OPEN");
                    auction = auctionTable.get(id);

                    System.out.println("Current Bid : " + auction.getCurrentBid());

                    System.out.println("What would you like to bid?");
                    double bid = reader.nextDouble();
                    reader.nextLine();
                    try {
                        auction.newBid(username, bid);
                        System.out.println("Bid Accepted");
                    } catch (Exception e) {
                        System.out.println("Bid is not accpeted");
                    }
                }
               
                break;

                case "I": 
                System.out.println("Please enter an Auction ID: ");
                String auctionID = reader.nextLine();
                
                auction = auctionTable.getAuction(auctionID);

                if(auction == null){
                    System.out.println("Error message..");
                }

                System.out.println("Auction " + auction.getAuctionId() + " : ");
                System.out.println("    " + "Seller : " + auction.getSellerName());
                System.out.println("    " + "Buyer : " + auction.getTimeRemaining());
                System.out.println("    " + "Info : " + auction.getItemInfo());
                
                break;

                case "P": 
                auctionTable.printTable();
                
                break;


                case "R":  
                System.out.println("Removing expired auctions...");
                auctionTable.removeExpiredAuctions();
                System.out.println("All expired auctions removed.");

                break;
                
                case "T": 
                System.out.println("How many hours should pass :");
                int numHours = reader.nextInt();
                reader.nextLine();
                System.out.println("Time passing..");
                auctionTable.letTimePass(numHours);
                System.out.println("Auction times updated");

                break;
                
                case "Q": 
                saveData();
                System.out.println("Writing Auction Table to file...\n" + "Done!\n" + "Goodbye.");
                System.exit(0);

            }
        }
        
    }
    public static AuctionTable initializeAuctionTable(){
        try{
        FileInputStream file = new FileInputStream("auctionTable.obj");
        ObjectInputStream inStream = new ObjectInputStream(file);
        AuctionTable auctions;
        return auctions = (AuctionTable) inStream.readObject();
        }catch(Exception e){
            return null;
        }
    }
    
    public static void saveData(){
        try{
            FileOutputStream file = new FileOutputStream("auctionTable.obj");
            ObjectOutputStream outStream = new ObjectOutputStream(file);
            AuctionTable auctions = new AuctionTable();
            outStream.writeObject(auctions);
        }catch(Exception e){

        }
    }
 }
