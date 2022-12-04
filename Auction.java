/* @author Tahseen Zaman
 *  ID : 114332480
 *  Recitation : 03
 */

public class Auction {
    private int timeRemaining;
    private double currentBid;
    private String auctionID;
    private String sellerName;
    private String buyerName;
    private String itemInfo;

    public Auction(){}
    
    public Auction(int timeRemaining, double currentBid, String auctionID, String sellerName, String buyerName, String itemInfo){
        this.timeRemaining = timeRemaining;
        this.currentBid = currentBid;
        this.auctionID = auctionID;
        this.sellerName = sellerName;
        this.buyerName = buyerName;
        this.itemInfo = itemInfo;
    }
    /* @ return
         It returns the remaining time of the auction as an integer value.
     */
    public int getTimeRemaining(){
        return timeRemaining;
    }
    /* @ return
         It returns the current Bid of the auction as a double value.
     */
    public double getCurrentBid(){
        return currentBid;
    }
    /* @ return
     */  
    public String getAuctionId(){
        return auctionID;
    }
    /* @ return
         It returns the seller name of the auction as a String value.
    */
    public String getSellerName(){
        return sellerName;
    }
    /* @ return
         It returns the buyer name of the auction as a String value.
     */
    public String getBuyerName(){
        return buyerName;
    }
    /* @ return
         It returns the item info of the auction as a String value.
     */
    public String getItemInfo(){
        return itemInfo;
    }
    /* It decreases the time remaining for this auction by the specified amount. If time is greater than the current
     remaining time for the auction, then the time remaining is set to 0. In addition, the reamining time has been 
     decremented by the indicated amount and is greater than or equal to 0.
     */
    public void decrementTimeRemaining(int time){
        
        if(time > timeRemaining){
            timeRemaining = 0;
        }
        else{
            timeRemaining -= time;
        }
    }
    /* It makes a new bid on this auction. If bid amount is larger than currentBid, then the value of currentBid
       is replaced by bid amount and buyerName is replaced by bidderName. It's also throw an Exception when the
       remaining time is 0 and when the bid amount is less than or equal to the currentBid.
     */
    public void newBid(String bidderName, double bidAmt) throws ClosedAuctionException, NotAEligibleBidException{
        
        if(timeRemaining == 0){
            throw new ClosedAuctionException("The auction is closed..");
        }
        if(bidAmt <= currentBid){ 
            throw new NotAEligibleBidException("Bid amount must be higher to be eligible..");
        } 
        if(bidAmt > currentBid){
            currentBid = bidAmt;
            buyerName = bidderName;
        }
    }
    // This method returns all the members of auction in a formatted way.
    public String toString(){
        return String.format("%-13s %-15s %-25s %-25s %-13s %-90s", auctionID, currentBid, sellerName, buyerName, timeRemaining, itemInfo);
    }
}
