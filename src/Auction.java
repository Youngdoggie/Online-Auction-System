import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Auction {

    // Constants used by this class
    private static final int NewAuction = 1;
    private static final int OpenAuction = 2;
    private static final int ClosedAuction = 3;

    // Context about this auction
    private String auctionName = null;
    private int lotStart = 0;
    private int lotEnd = 0;
    private int minIncrement = 0;
    private int state = NewAuction;
    private String region = null;

    // Context surrounding this auction
    private Map<Integer, Lot> lotSet = null;    // All the lots available
    private List<Bidder> bidderSet = null;      // All the bidders in the system

    // Helper variables for the class
    private Map<Integer, String> naming = null;

    public Auction( Map<Integer, Lot> auctionLots, List<Bidder> allBidders, String auctionName, int firstLotNumber, int lastLotNumber, int minBidIncrement , String region) {

        try {
            this.auctionName = auctionName;
            this.lotStart = firstLotNumber;
            this.lotEnd = lastLotNumber;
            this.minIncrement = minBidIncrement;
            this.state = NewAuction;
            this.lotSet = auctionLots;
            this.bidderSet = allBidders;
            this.region = region;
            naming = new HashMap<Integer, String>();
            naming.put(NewAuction, "new");
            naming.put(OpenAuction, "open");
            naming.put(ClosedAuction, "closed");

            // Make the lots for the auction

            for (int i = lotStart; i <= lotEnd; i++) {
                Lot newLot = new Lot(this, allBidders, i);
                lotSet.put(i, newLot);
            }

        }catch (NullPointerException e){
            throw new NullPointerException("data is null");
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException("index out of bound");
        }

    }

    public boolean openAuction( ) {

        if (state == NewAuction) {
            state = OpenAuction;
            return true;
        }else{
            return false;
        }
    }

    public boolean closeAuction( ) {

        if(state == OpenAuction){
            state = ClosedAuction;
            return true;
        }else{
            return false;
        }
    }

    public String winningBids( ) {
        String bids = null;

        bids = "";
        for(int lot = lotStart; lot <= lotEnd; lot++ ) {
            bids += lotSet.get(lot).winningBidString();
        }

        return bids;
    }

    public int getMinIncrement( ) {
        return minIncrement;
    }

    public String getStatus() {
        String status = "";
        int bids = 0;

        // Find out all the bids

        for(int lot = lotStart; lot <= lotEnd; lot++ ) {
            bids += lotSet.get(lot).currentBid();
        }

        // Make the return string.

        status = auctionName + "\t" + naming.get(state) + "\t" + bids + "\n";

        return status;
    }

    public int auctionBidTotal() {
        int bids = 0;

        // Find out all the bids

        for(int lot = lotStart; lot <= lotEnd; lot++ ) {
            bids += lotSet.get(lot).currentBid();
        }

        return bids;
    }

    public boolean auctionIsOpen() {
        return state == OpenAuction;
    }

    public boolean auctionIsClosed() { return state == ClosedAuction; }

    public String getAuctionName() { return auctionName; }

    public String getRegionName() { return region; }

    public int getMinLot() {
        return lotStart;
    }

    public int getMaxLot() {
        return lotEnd;
    }

    public Map<Integer,Lot> getLot(){
        return lotSet;
    }
}
