import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class BoundaryTests {
    @Test
    void createAuction() {
        ArrayList<Bidder> bidderList = new ArrayList<>();
        HashMap<Integer, Lot> lotList = new HashMap<>();

        Auction auction1 = null;
        Auction auction2 = null;
        Auction auction3 = null;
        Auction auction4 = null;

        // Single letter auction name
        auction1 = new Auction( lotList, bidderList,"a", 10, 15, 5, "canada");
        assertEquals("a",auction1.getAuctionName());

        // Longer auction names
        // Range of lots
        auction2 = new Auction( lotList, bidderList,"test1", 1, 2, 5, "canada");
        assertEquals("test1",auction2.getAuctionName());
        assertEquals(1 ,auction2.getMinLot());
        assertEquals(2 ,auction2.getMaxLot());

        // Single lot
        auction3 = new Auction(lotList, bidderList, "test2", 3, 3, 5, "canada");
        assertEquals("test2",auction3.getAuctionName());
        assertEquals(3 ,auction3.getMinLot());
        assertEquals(3 ,auction3.getMaxLot());

        // Minimum bid increment
        auction4 = new Auction(lotList, bidderList,"test3", 5, 8, 1, "canada");
        assertEquals(1 ,auction4.getMinIncrement());

    }

    @Test
    void createBidder() {
        HashMap<Integer, Lot> lotList = new HashMap<>();
        Bidder bidder1 = null;

        bidder1 = new Bidder(lotList, "z", 12 , "canada");
        assertEquals(12, bidder1.getBidderId());
        assertEquals("canada", bidder1.getRegion());
    }
}