import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

class LotNewImplementationTest {

    @Test
    void testRegularLot(){
        HashMap<Integer, Lot> lots = new HashMap<>();
        ArrayList<Bidder> bidders = new ArrayList<>();

        Auction auction1 = new Auction(lots, bidders, "FirstAuction", 1, 10, 5, "canada");
        auction1.openAuction();
        Lot lot1 = lots.get(1);
        lot1.regularLot();
        Bidder bidder = new Bidder(lots,"name",1,"canada");
        bidders.add(bidder);
        //test bidder and auction in same region and bid in regular lot
        assertEquals(2,lot1.placeBid(1,1));
        assertEquals(3,lot1.placeBid(20,1));
        assertEquals(3,lot1.placeBid(30,1));
        assertEquals(3,lot1.placeBid(40,1));
        assertEquals(3,lot1.placeBid(50,1));
    }

    @Test
    void testReserveLot(){
        HashMap<Integer, Lot> lots = new HashMap<>();
        ArrayList<Bidder> bidders = new ArrayList<>();

        Auction auction1 = new Auction(lots, bidders, "FirstAuction", 1, 10, 5, "canada");
        Bidder bidder = new Bidder(lots,"name",1,"canada");
        bidders.add(bidder);
        auction1.openAuction();
        Lot lot1 = new Lot(auction1,bidders,1);
        lot1.reserveLot(5);
        //test bid less than reserved bid and return acceptable but not winning
        assertEquals(2,lot1.placeBid(4,1));
        assertEquals(3,lot1.placeBid(5,1));
        assertEquals(3,lot1.placeBid(6,1));
        assertEquals(3,lot1.placeBid(7,1));
    }

    @Test
    void testDualMinLot(){
        HashMap<Integer, Lot> lots = new HashMap<>();
        ArrayList<Bidder> bidders = new ArrayList<>();

        Auction auction1 = new Auction(lots, bidders, "FirstAuction", 1, 10, 5, "canada");
        Bidder bidder = new Bidder(lots,"name",1,"canada");
        bidders.add(bidder);
        auction1.openAuction();
        Lot lot1 = lots.get(1);
        lot1.dualMinLot(10,5,10);
        assertEquals(3,lot1.placeBid(5,1));
        assertEquals(3,lot1.placeBid(10,1));
        assertEquals(2,lot1.placeBid(15,1));
        assertEquals(3,lot1.placeBid(20,1));
        assertEquals(2,lot1.placeBid(25,1));
    }

    @Test
    void testBidderWithRegion(){
        HashMap<Integer, Lot> lots = new HashMap<>();
        ArrayList<Bidder> bidders = new ArrayList<>();

        Auction auction1 = new Auction(lots, bidders, "FirstAuction", 1, 10, 5, "Canada");
        Bidder bidder1 = new Bidder(lots,"name1",1,"Canada");
        Bidder bidder2 = new Bidder(lots,"name2",2,"USA");
        Bidder bidder3 = new Bidder(lots,"name3",3,"India");
        Bidder bidder4 = new Bidder(lots,"name4",4,"Canada");
        Lot lot1 = lots.get(1);
        lot1.regularLot();
        bidders.add(bidder1);
        bidders.add(bidder2);
        bidders.add(bidder3);
        bidders.add(bidder4);
        auction1.openAuction();
        //place bid with bidder in same country with auction
        assertEquals(3,lot1.placeBid(5,1));
        assertEquals(3,lot1.placeBid(10,4));
        //place bid with bidder in different country with auction
        assertEquals(1,lot1.placeBid(15,2));
        assertEquals(1,lot1.placeBid(20,3));
    }

    @Test
    void testAuctionWithRegion(){
        HashMap<Integer, Lot> lots = new HashMap<>();
        ArrayList<Bidder> bidders = new ArrayList<>();

        Auction auction1 = new Auction(lots, bidders, "FirstAuction", 1, 5, 5, "Canada");
        Auction auction2 = new Auction(lots, bidders, "FirstAuction", 5, 10, 5, "USA");
        Auction auction3 = new Auction(lots, bidders, "FirstAuction", 10, 15, 5, "India");
        auction1.openAuction();
        auction2.openAuction();
        auction3.openAuction();
        Lot lot1 = lots.get(1);
        Lot lot8 = lots.get(8);
        Lot lot12 = lots.get(12);
        Bidder bidder1 = new Bidder(lots,"name1",1,"Canada");
        Bidder bidder2 = new Bidder(lots,"name2",2,"USA");
        Bidder bidder3 = new Bidder(lots,"name3",3,"India");
        bidders.add(bidder1);
        bidders.add(bidder2);
        bidders.add(bidder3);
        //place bid with bidder and auction in same region
        assertEquals(3,lot1.placeBid(5,1));
        assertEquals(3,lot8.placeBid(10,2));
        assertEquals(3,lot12.placeBid(10,3));
        //place bid with bidder and auction in different region
        assertEquals(1,lot1.placeBid(5,2));
        assertEquals(1,lot8.placeBid(10,3));
        assertEquals(1,lot12.placeBid(10,1));
    }

    @Test
    void testGetRegionOfBidders(){
        HashMap<Integer, Lot> lots = new HashMap<>();
        Bidder bidder1 = new Bidder(lots,"name1",1,"Canada");
        Bidder bidder2 = new Bidder(lots,"name2",2,"USA");
        Bidder bidder3 = new Bidder(lots,"name3",3,"India");
        //rest for getRegion() returns the correct region for bidder
        assertEquals(bidder1.getRegion(), "Canada");
        assertEquals(bidder2.getRegion(), "USA");
        assertEquals(bidder3.getRegion(), "India");
    }

    @Test
    void testGetRegionOfAuction(){
        HashMap<Integer, Lot> lots = new HashMap<>();
        ArrayList<Bidder> bidders = new ArrayList<>();
        Auction auction1 = new Auction(lots, bidders, "FirstAuction", 1, 5, 5, "Canada");
        Auction auction2 = new Auction(lots, bidders, "FirstAuction", 5, 10, 5, "USA");
        Auction auction3 = new Auction(lots, bidders, "FirstAuction", 10, 15, 5, "India");
        //rest for getRegionName() returns the correct region for auction
        assertEquals(auction1.getRegionName(),"Canada");
        assertEquals(auction2.getRegionName(),"USA");
        assertEquals(auction3.getRegionName(),"India");
    }

    @Test
    void testBidderOpenAuction(){
        HashMap<Integer, Lot> lots = new HashMap<>();
        ArrayList<Bidder> bidders = new ArrayList<>();
        ArrayList<Auction> auctions = new ArrayList<>();  // all the auctions
        Auction auction1 = new Auction(lots, bidders, "FirstAuction", 1, 5, 5, "Canada");
        Auction auction2 = new Auction(lots, bidders, "FirstAuction", 5, 10, 5, "Canada");
        Auction auction3 = new Auction(lots, bidders, "FirstAuction", 10, 15, 5, "Canada");
        auction1.openAuction();
        auction2.openAuction();
        auction3.openAuction();
        auctions.add(auction1);
        auctions.add(auction2);
        auctions.add(auction3);

        Bidder bidder1 = new Bidder(lots,"name1",1,"Canada");
        ArrayList<Auction> newAuction = bidder1.openAuction(auctions);
        //test the result auction contains same region name with bidder
        assertEquals("Canada",newAuction.get(0).getRegionName());
        assertEquals("Canada",newAuction.get(1).getRegionName());
        assertEquals("Canada",newAuction.get(2).getRegionName());
    }
}