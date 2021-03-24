import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * Countries generate buy and sell orders at random. A country
 * has a name, some gold, some cash, and a dynamic worth (cash + gold x
 * current gold price).
 * Extends entity and it has a position.
 *
 * @author Yunus Emre Gök 2166460
 */
public class Country extends Entity {

    private double goldAmount = 50; //Country gold amount (default value 50)
    private double cashAmount = 10000;//Country cash amount (default value 10000)
    private String name; // Country name
    private Agent agent; //Agent that steals orders for the  country.

    private BufferedImage img = null;  // Image represents the country
    // Order factories that will produce orders and country will not now factories' types and what they produce
    private OrderFactory firstFactory;
    private OrderFactory secondFactory;
    private Font font = new Font("Verdana", Font.BOLD ,20);
    public Vector<Order> orders = new Vector<Order>();// Orders that the country produce.
    double worthLimit = 2000; // agent gains badge every 2000 worth. First limit is 2000

    /**
     * Constructor of the country
     * @param x is the position x-axis
     * @param y is the position y-axis
     * @param name Country name
     * @param firstFactory // Order factory
     * @param secondFactory // Order factory
     */
    public Country(double x, double y, String name, OrderFactory firstFactory, OrderFactory secondFactory) {
        super(x, y);
        this.name= name;

        this.firstFactory = firstFactory;
        this.secondFactory = secondFactory;
        String  imgPath = "../images/" + name.toLowerCase() +".jpg";
        try {
            File file = new File(imgPath);
            img = ImageIO.read(new File(imgPath));
        } catch (IOException e) {
            System.out.println("img");
        }

    }

    //setter
    public void setAgent(Agent agent)
    {
        this.agent = agent;
    }

    // getters
    public double getGoldAmount() { return goldAmount; }
    public double getCashAmount() { return cashAmount; }
    public double getWorth() { return goldAmount * Common.getGoldPrice().getCurrentPrice() + cashAmount; }
    public String getName() { return name; }
    public String getInitials() { return name.toUpperCase().substring(0,2); }
    public Agent getAgent(){ return agent;}

    /**
     * What will be displayed and how it be displayed is determined in here.
     * Country image will displayed with 150 unit diameter at the current position
     * Below the image, country name wil be displayed in black.
     * Below the country name , gold amount will be displayed in yellow .
     * Below the gold amount, cash amount will be displayed  in green.
     * Below the cash amount, worth will be displayed  in blue.
     * @param g2d Graphics2D component the draw.
     */
    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawImage(img, position.getIntX(),
                position.getIntY(), 150,
                150,null);
        g2d.drawString(name, position.getIntX()+35, position.getIntY()+170);
        g2d.setColor(Color.YELLOW);
        g2d.drawString(String.format("%.0f gold", goldAmount), position.getIntX()+20, position.getIntY()+190);
        g2d.setColor(Color.GREEN);
        g2d.drawString(String.format("%.0f cash", cashAmount), position.getIntX()+20, position.getIntY()+210);
        g2d.setColor(Color.BLUE);
        g2d.drawString(String.format("Worth: %.0f ", getWorth()), position.getIntX()+20, position.getIntY()+230);
    }

    /**
     * At randomly country produces an order and does not know the type of the order.
     *
     */
    @Override
    public void step() {
        if(Common.getRandomGenerator().nextInt(100) == 0) {

            if (Common.getRandomGenerator().nextInt(2) == 1) {
                orders.add(firstFactory.produceOrder(this));

            } else {
                orders.add(secondFactory.produceOrder(this));

            }
        }
    }

    /**
     * At every step , Country will step it's orders
     */
    public void stepOrders(){
        for(int j=0; j<orders.size(); j++)
        {
            orders.get(j).step();
        }
    }
    /**
     * At every step , Country will step it's agent.
     * Agent will be decorated to gain badge at every 2000 worth up to 6000
     */
    public void stepAgent()
    {

        agent.step();

        if(agent.getWorth() >= worthLimit && agent.getWorth()< 4000)
        {
            //White badge -> Novice Agent with 2000 worth
            agent = new Novice(agent);
            worthLimit = 4000;
        }
        if(agent.getWorth() >= worthLimit && agent.getWorth()< 6000)
        {
            //Yellow badge -> Master Agent with 4000 worth
            agent = new Master(agent);
            worthLimit = 6000;
        }
        if( agent.getWorth() >= worthLimit )
        {
            //Red badge -> Expert Agent with 4000 worth
            agent = new Expert(agent);
            worthLimit = Integer.MAX_VALUE;// Agent will not be decorated after 6000
        }
    }
    /**
     * Method draws every order
     */
    public void drawOrders(Graphics2D g)
    {
        int n = orders.size();
        for(int j=0; j < n ; j++)
        {
            orders.get(j).draw(g);
        }
    }

    /**
     * Method deletes given order from order list
     * @param orderToBeDeleted
     */
    public void deleteOrder(Order orderToBeDeleted){
        orders.remove(orderToBeDeleted);
    }

    /**
     * When order reaches the upper line, this method will be called.
     * Depending on the order, cash amount and gold amount will be updated.
     * If order is buy order, gold amount will increase and cash amount will decrease
     * If order is sell order, gold amount will decrease and cash amount will increase
     * When order is delivered and processed, it will be deleted from the country order list
     * @param orderToBeDelivered
     */
    public void processDeliveredOrder(Order orderToBeDelivered)
    {
        double goldChange = orderToBeDelivered.goldChange();
        double nextGoldAmount= goldAmount+goldChange;
        double nextCashAmount = cashAmount-Common.getGoldPrice().getCurrentPrice()*goldChange ;
        if(nextGoldAmount>=0 && nextCashAmount>=0 ){
            goldAmount = nextGoldAmount;
            cashAmount = nextCashAmount;
        }
        deleteOrder(orderToBeDelivered);
    }
    /**
     * When order is stolen by an agent, this method will be called.
     * Depending on the order, cash amount and gold amount will be updated.
     * Country checks is there enough gold or cash to be stolen.
     * When there is not enough gold or cash order could not be stolen
     * If order is buy order, gold amount cash amount will decrease.
     * If order is sell order, gold amount will decrease.
     * When order is stolen and processed, it will be deleted from the country order list
     * @param orderToBeStolen
     * @return boolean value for whether the order can be stolen or not
     */
    public boolean processStolenOrder(Order orderToBeStolen){
        double goldChange = orderToBeStolen.goldChange();
        double nextCashAmount;
        double nextGoldAmount;
        boolean isStolen = false;
        if(goldChange > 0) //Buy Order
        {
            nextCashAmount = cashAmount - Common.getGoldPrice().getCurrentPrice() * orderToBeStolen.getOrderAmount() ;
            if(nextCashAmount >= 0){
                cashAmount = nextCashAmount;
                isStolen = true;
            }
        }
        else if(goldChange < 0)//Sell Order
        {
            nextGoldAmount = goldAmount - orderToBeStolen.getOrderAmount();
            if(nextGoldAmount >= 0){
                goldAmount = nextGoldAmount;
                isStolen = true;
            }
        }

        deleteOrder(orderToBeStolen);
        return isStolen;
    }

    /**
     * When the country's agent steal an order from other order, this method will be called.
     * Depending on the order, cash amount and gold amount will be updated.
     * If order is buy order, gold amount cash amount will increase.
     * If order is sell order, gold amount will increase.
     * @param orderToBeStolen
     */
    public void processAgentSteal(Order orderToBeStolen){
        double goldChange = orderToBeStolen.goldChange();
        if(goldChange > 0)
        {
            cashAmount += orderToBeStolen.getOrderAmount() * Common.getGoldPrice().getCurrentPrice();
        }
        else if(goldChange < 0)
        {
            goldAmount+= orderToBeStolen.getOrderAmount();
        }
    }
}