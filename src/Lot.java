import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class Lot {
    // Outcomes of placing a bid.  Making constants available outside the class so others understand the return codes

    public final static int regularLot = 1;
    public final static int reservedLot = 2;
    public final static int duelLot = 3;
    public final static int LotNotAccepting = 0;
    public final static int BidNotAcceptable = 1;
    public final static int BidAcceptableNotWinning = 2;
    public final static int BidWinning = 3;

    // Context about this lot
    private int lotNumber = 0;
    private int winningBidder = 0;
    private int topBid = 0;
    private int reserveBid = 0;
    private int maxBidCount = 0;
    private int firstMinIncrement = 0;
    private int secondMinIncrement = 0;
    private int state = regularLot;

    // Context about the environment in which the lot belongs
    private Auction theAuction = null;      // The auction to which the lot belongs
    private List<Bidder> allBidders = null; // The set of all bidders

    // Helper arrays for the class
    private Map<Boolean, String> winString = null;

    public Lot( Auction auction, List<Bidder> allBidders, int lotNumber ) {
        if ((lotNumber > 0) && (auction != null)) {
            //Cache the information for the lot.
            this.lotNumber = lotNumber;
            this.theAuction = auction;
            this.allBidders = allBidders;

            // Load some generic naming that we'll use when someone wins or loses the lot bid.
            winString = new HashMap<Boolean, String>();
            winString.put( true, "winning" );
            winString.put( false, "losing" );
        }
    }

    public boolean regularLot(){
        this.state = regularLot;
        return true;
    }

    public boolean reserveLot(int reservedBid){
        this.state = reservedLot;
        this.reserveBid = reservedBid;;
        return true;
    }

    public boolean dualMinLot(int maxBidCount, int firstMinIncrement, int secMinIncrement){
        this.state = duelLot;
        this.maxBidCount = maxBidCount;
        this.firstMinIncrement = firstMinIncrement;
        this.secondMinIncrement = secMinIncrement;
        return true;
    }

    public int currentBid() {
        return topBid;
    }

    public int winningBidder() {
        return winningBidder;
    }

    public String winningBidString() {
        return lotNumber + "\t" + topBid + "\t" + winningBidder + "\n";
    }

    public int placeBid( int bid, int bidderId ) {
        int outcome = LotNotAccepting;

        if (bidderId > allBidders.size() || bidderId <= 0 || bid < 0) {
            return LotNotAccepting;
        }

        if (!allBidders.get(bidderId - 1).getRegion().equals(theAuction.getRegionName())) {
            if (allBidders.get(bidderId - 1).getRegion().length() < 1 || theAuction.getRegionName().length() < 1) {
                outcome = LotNotAccepting;
            }else{
                return BidNotAcceptable;
            }
        }

        if(!theAuction.auctionIsOpen()){
            return LotNotAccepting;
        }

        // Make sure the bidder is valid

        // TO DO:  Verify that the bidder ID is ok.  Let the code through for now

        // Proceed with the bid

        if(state == regularLot) {
            if (bid >= theAuction.getMinIncrement()) {
                outcome = BidNotAcceptable;
                if (bid > 0) {
                    // If the current winner is re-bidding then don't let them outbid themselves.

                    outcome = BidAcceptableNotWinning;
                    if (bidderId == winningBidder) {
                        if (bid > topBid) {
                            outcome = BidWinning;
                            topBid = bid;
                        }
                    } else {

                        // An acceptable bid must exceed the current bid by the minimum increment or more.

                        // TO DO: Add code to manage the minimum increment rather than just a bigger bid

                        if (topBid == 0 && bid - topBid >= theAuction.getMinIncrement()) {
                            outcome = BidWinning;
                            winningBidder = bidderId;
                            topBid = bid;
                        } else if (bid > topBid && bid - topBid >= theAuction.getMinIncrement()) {
                            outcome = BidWinning;
                            winningBidder = bidderId;
                            topBid = bid;
                        } else if (bid > topBid && bid - topBid < theAuction.getMinIncrement()) {
                            outcome = BidAcceptableNotWinning;
                        }
                    }
                }
            }else{
                outcome = BidAcceptableNotWinning;
            }
        }

        if(state == reservedLot){
            if(bid >= reserveBid && bid > topBid){
                outcome = BidWinning;
                winningBidder = bidderId;
                topBid = bid;
            }else{
                outcome = BidAcceptableNotWinning;
            }
        }

        if (state == duelLot) {
            if (topBid < maxBidCount){
                if(bid - topBid >= firstMinIncrement){
                    outcome = BidWinning;
                    winningBidder = bidderId;
                    topBid = bid;
                }else{
                    outcome = BidAcceptableNotWinning;
                }
            }else{
                if(bid - topBid >= secondMinIncrement){
                    outcome = BidWinning;
                    winningBidder = bidderId;
                    topBid = bid;
                }else{
                    outcome = BidAcceptableNotWinning;
                }
            }
        }
        return outcome;
    }

    public boolean isClosed() {
        return theAuction.auctionIsClosed();
    }
}
