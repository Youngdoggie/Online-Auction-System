import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class InputValidation {

    @Test
    void createAuction(){
        ArrayList<Bidder> bidderList = new ArrayList<>();
        HashMap<Integer, Lot> lotList = new HashMap<>();
        Auction auction1 = new Auction( lotList, bidderList, "first", -1, 3, 10 , "");
        assertEquals(-1, auction1.getMinLot());
    }

    @Test
    void createBidder() {
        HashMap<Integer, Lot> lotList = null;

        Bidder bidder1 = new Bidder(lotList, null, 5, null);
        assertEquals(5,bidder1.getBidderId());

        Bidder bidder2 = new Bidder( lotList, "", 6 , "canada");
        assertEquals(6,bidder2.getBidderId());

    }

    @Test
    void placeBid() {
        ArrayList<Bidder> bidderList = new ArrayList<>();
        HashMap<Integer, Lot> lotList = new HashMap<>();

        // Load a basic auction to ensure the failure isn't from having no auction data

        Auction auction1 = new Auction( lotList, bidderList, "first", 1, 3, 10 , "canada");
        Auction auction2 = new Auction( lotList, bidderList, "second", 4, 7, 20 , "canada");

        Bidder bidder1 = new Bidder( lotList, "Alice ", 1 , "canada");
        bidderList.add( bidder1);

        // Make bids with bad parameters.  Only one parameter is bad at a time.

        assertEquals( 0, lotList.get(1).placeBid( -1, 100 ) );
        assertEquals( 0, lotList.get(1).placeBid(  0, 100 ) );
        assertEquals( 0, lotList.get(1).placeBid(  3, 100 ) );
        assertEquals( 0, lotList.get(1).placeBid(  bidder1.getBidderId(), -1 ) );
        assertEquals( 0, lotList.get(1).placeBid(  bidder1.getBidderId(), 0 ) );
    }
}