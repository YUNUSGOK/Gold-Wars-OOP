import com.sun.org.apache.xpath.internal.operations.Or;

import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class Common {
    private static final String title = "Gold Wars";
    private static final int windowWidth = 1650;
    private static final int windowHeight = 1000;

    private static final GoldPrice goldPrice = new GoldPrice(588, 62);

    private static final Random randomGenerator = new Random(1234);
    private static final int upperLineY = 100;


    public static Country[] countries ;
    public static Agent[] agents;


    static  {
        // TODO: Here, you can initialize the fields you have declared
        countries = new Country[5];
        agents= new BasicAgent[5];
        countries[0] = new Country(150,700,"USA",new BuyOrderFactory(),new SellOrderFactory());
        countries[1] = new Country(450,700,"israel",new BuyOrderFactory(),new SellOrderFactory());
        countries[2] = new Country(750,700,"Turkey",new BuyOrderFactory(),new SellOrderFactory());
        countries[3] = new Country(1050,700,"Russia",new BuyOrderFactory(),new SellOrderFactory());
        countries[4] = new Country(1350,700,"China",new BuyOrderFactory(),new SellOrderFactory());
        agents[0]= new BasicAgent(countries[0],"CIA" , "cia");
        agents[1]= new BasicAgent(countries[1],"Mossad" , "mossad");
        agents[2]= new BasicAgent(countries[2],"MIT" , "mit");
        agents[3]= new BasicAgent(countries[3],"SVR" , "svr");
        agents[4]= new BasicAgent(countries[4],"MSS" , "mss");
        for (int i= 0; i<5; i++)
        {

            countries[i].setAgent(agents[i]);
        }

    }

    // getters
    public static String getTitle() { return title; }
    public static int getWindowWidth() { return windowWidth; }
    public static int getWindowHeight() { return windowHeight; }

    // getter
    public static GoldPrice getGoldPrice() { return goldPrice; }

    // getters
    public static Random getRandomGenerator() { return randomGenerator; }
    public static int getUpperLineY() { return upperLineY; }


    public static void stepAllEntities() {
        int goldChangeRate = 200;
        //Gold Price will change randomly
        if (randomGenerator.nextInt(goldChangeRate) == 0) {
            goldPrice.step();
        }
        //Country will call step() method for itself, agent and orders
        for(int i=0; i<5;i++)
        {
            countries[i].step();
            countries[i].stepOrders();
            countries[i].stepAgent();
        }

    }
}