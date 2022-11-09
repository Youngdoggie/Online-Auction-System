import java.util.ArrayList;
import java.util.HashMap;

public class Bidder {
    // Context about this bidder
    private int bidderNumber = 0;
    private String bidderName = null;
    private String region = null;

    // Context that surrounds this bidder
    private HashMap<Integer, Lot> lotSet = null;

    // State of readiness of the class

    public Bidder( HashMap<Integer, Lot> lots, String bidderName, int bidderId , String region) {

        try {
            this.bidderNumber = bidderId;
            this.bidderName = bidderName;
            this.lotSet = lots;
            this.region = region;
        }
        catch (NullPointerException e){
            throw new NullPointerException("Name or region is null");
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException("ID is invalid");
        }
    }

    public int getBidderId( ) {
        return bidderNumber;
    }

    public String getRegion(){
        return region;
    }

    public String feesOwed() {
        String owed = "";
        int won = 0;
        int cost = 0;

        for( HashMap.Entry<Integer, Lot> entry : lotSet.entrySet() ){
            Lot theLot = entry.getValue();
            if (theLot.isClosed() && (theLot.winningBidder() == bidderNumber)) {
                // Add this lot to what's reported.
                won++;
                cost += theLot.currentBid();
            }
        }

        owed = bidderName + "\t" + won + "\t" + cost + "\n";

        return owed;
    }

    public ArrayList<Auction> openAuction(ArrayList<Auction> auctions){
        ArrayList<Auction> permittedAuction = new ArrayList<>();
        for (Auction auction : auctions) {
            if (auction.auctionIsOpen()) {
                if (auction.getRegionName().equalsIgnoreCase(getRegion())) {
                    permittedAuction.add(auction);
                }
            }
        }

        return permittedAuction;
    }
}
