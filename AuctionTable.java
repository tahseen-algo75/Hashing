/* @author Tahseen Zaman
 *  ID : 114332480
 *  Recitation : 03
 */

import java.io.*;
import java.util.*;
import big.data.*;

public class AuctionTable extends HashMap<String, Auction> implements Serializable {
    
    
    /* @param URL
     *  It uses the BigData library to construct an AuctionTable from a remote data source.
     *  In the method, the URL represents a data source which can be connected to using the 
     *  BigData library. And returns the AuctionTable constructed from the remote data source.
     *  Morever, it throws an IllegalArgumentException if the URL does not represent a valid datasource
     */
    public static AuctionTable buildFromURL(String URL) /* throws IllegalArgumentException */ {
        
        AuctionTable auctionTable = new AuctionTable();
        Auction auction = new Auction();

        DataSource ds = DataSource.connect(URL).load();
        String [] sellerName = ds.fetchStringArray("listing/seller_info/seller_name");
        String [] currentBid = ds.fetchStringArray("listing/auction_info/current_bid");
        String [] timeLeft = ds.fetchStringArray("listing/auction_info/time_left");
        String [] idNum = ds.fetchStringArray("listing/auction_info/id_num");
        String [] bidderName = ds.fetchStringArray("listing/auction_info/high_bidder/bidder_name");
        String [] memory = ds.fetchStringArray("listing/item_info/memory");
        String [] hardDrive = ds.fetchStringArray("listing/item_info/hard_drive");
        String [] cpu = ds.fetchStringArray("listing/item_info/cpu");
        
        double [] currentBidParsed = new double[currentBid.length];
        int [] timeLeftParsed = new int[timeLeft.length];
        String [] itemInfo = new String[cpu.length];

        for(int i = 0; i < currentBid.length; i++){
            currentBidParsed[i] = Double.parseDouble(currentBid[i].replace(",", "").replace("$", ""));
            int hours = 0;
            int dayIndex = timeLeft[i].indexOf("d");
            if (dayIndex != -1) {
                hours += Integer.parseInt(timeLeft[i].substring(0, dayIndex - 1)) * 24;
                timeLeft[i] = timeLeft[i].substring(dayIndex + 1);
                
            }
            int hourIndex = timeLeft[i].indexOf("h");
            if (hourIndex != -1) {
                hours += Integer.parseInt(timeLeft[i].substring(timeLeft[i].indexOf(" "), hourIndex).trim());
            }
            timeLeftParsed[i] = hours;

        }

        for(int i = 0; i< cpu.length; i++){
            itemInfo [i] = cpu[i] + " - " + memory[i] + " - " + hardDrive[i];
        }

        for(int i = 0; i < sellerName.length; i++){
            auction = new Auction(timeLeftParsed[i], currentBidParsed[i], idNum[i], sellerName[i], bidderName[i], itemInfo[i]);
            auctionTable.put(idNum[i], auction);
        }
        return auctionTable;
    }
    /* @param auctionID, auction
         This method manually posts an auction, and add it into the table.
         It's also throw an IllegalArgumentException if the given auctionId is already
        in the table. 
    */
    public void putAuction(String auctionID, Auction auction) throws IllegalArgumentException{
        if(this.get(auctionID) != null)
            throw new IllegalArgumentException("The given auctionID is already stored in the table");
            this.put(auctionID, auction);
        }
    /* @param auctionID
       This method get the information of an Auction that contains the given ID as key
    */
    public Auction getAuction(String auctionID){
        return this.get(auctionID);
    }
    
    /* @param numHours
     * This method simulates the passing of time. Decrease the timeRemaining of all Auction objects
     *  by the amount specified. The value cannot go below 0.
     */
    public void letTimePass(int numHours) throws IllegalArgumentException{
        
        Auction auction = new Auction();
        if(numHours <= 0){
            throw new IllegalArgumentException("The number of hours can't be negative");
        }
        else{
            for(int i = 0; i < this.size(); i++){
                this.get(auction).decrementTimeRemaining(numHours);
            }
        }
    }
    
    // This method Iterates over all Auction objects in the table and removes them 
    // if they are expired
    public void removeExpiredAuctions(){
        
        Auction auction = new Auction();
        for(int i = 0; i<this.size(); i++){
            if(this.get(auction).getTimeRemaining() == 0){
                this.remove(auction);
            }
        }
    }
    
    // This method prints the AuctionTable in tabular form.
    public void printTable(){

        System.out.println(String.format("%-13s %-15s %-25s %-25s %-13s %-90s", "Auction ID",
        "Bid","Seller", "Buyer", "Time", "Item Info"));
        
        for(Auction auctionToPrint: this.values()){
            System.out.println(auctionToPrint.toString());
    
        }
        
    }
}








