import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Base class of the BasicAgent. Agent steals orders for its country.
 * Extends entity and it has a position
 *
 * @author Yunus Emre Gök 2166460
 */
public abstract class Agent extends Entity {
    protected Country agentCountry; //origin country of the agent
    protected String name; // Agent Name
    protected double goldAmount = 0; // Stolen gold amount
    protected double cashAmount = 0; // Stolen cash amount
    protected BufferedImage img = null; // Image represents agent from ../images/
    String imgPath; // image path
    String filename; // file name of the image
    protected State state; //Agent state for movement
    protected Font font = new Font("Arial Black", Font.PLAIN ,15); // Font for initials, worth and state draw

    /**
     * Constructor for Agent. Origin country will be assigned
     * Position will be 250 unit  above the origin country.
     * @param agentCountry is the origin country of the agent.
     */
    public Agent(Country agentCountry) {
        super(agentCountry.position.getX()+30, agentCountry.position.getY()-250);
        this.agentCountry = agentCountry;
        this.state = new Rest();
    }


    /**
     * Agents steal cash and gold from other countries. Worth depends on the stolen cash and gold.
     * @return sum of the cash amount and the gold amount x current gold price
     */
    public double getWorth() {
        return goldAmount * Common.getGoldPrice().getCurrentPrice() + cashAmount;
    }

    //setters
    public void setPosition(Position newPosition){  this.position = newPosition;    }
    public void setState(State newState){ this.state = newState; }

    /**
     * At every step agent moves depending on its state and checks all orders to steal
     */
    @Override
    public void step() {
        state.move(this);
        checkAllOrdersToStole();
    }

    /**
     * at every step agent checks all orders of other countries. If an order intersect with the agent,
     * agent check from the country the steal. If the origin country of the order has enough gold or cash depending
     * on the order, agent steals and brings to it's origin country.
     */
    private void checkAllOrdersToStole(){
        Country country; // country that agent currently checks its orders
        Order tmpOrder; // order that agent currently checks
        boolean isStolen; // condition that order that agent can steal from the country
        for(int i=0 ; i< 5 ;i++)// for all countries
        {
            country = Common.countries[i];
            if(country == agentCountry)//agent will not steal from the origin country
                continue;
            for(int j = 0; j<country.orders.size(); j++)//for all orders
            {
                tmpOrder =  country.orders.get(j);
                if(intersect(tmpOrder)) // is order near enough?
                {
                    isStolen = country.processStolenOrder(tmpOrder); //checks country to steal
                    j--;
                    if(isStolen){
                        processStolenOrder(tmpOrder);//updates agents cash or gold amount
                        agentCountry.processAgentSteal(tmpOrder);//updates origin country's cash or gold amount
                    }
                }
            }
        }
    }

    /**
     * Function checks whether order intersect with the agent.
     *
     * @param order that will be checked
     * @return boolean value for whether order intersect.
     */
    public boolean intersect(Order order)
    {
        /*
        *agentCenter depends on the draw function. Image will be drawn with 100 unit diameter.
        *Therefore center is is 50 unit right and 50 unit below from the current position.
        *Order has 20 unit diameter and center is 10 unit right and 10 below from the order's current position.
        */
        Position agentCenter = new Position(position.getX()+50, position.getY()+50);
        double distanceToOrder= agentCenter.distanceTo(order.position.getX()+10,order.position.getY()+10);
        /*
        returns True if order's center is in the agent.
         */
        return distanceToOrder <= 50;
    }

    /**
     * Updates cash and gold amount depending on the order.
     * If it is a buy order, cash amount will increase.
     * If it is a sell order, gold amount will increase.
     * @param orderToBeStolen is the order that is stolen
     */
    public void processStolenOrder(Order orderToBeStolen)
    {
        double goldChange = orderToBeStolen.goldChange();
        if(goldChange > 0) {
            //Buy order
            cashAmount +=  Common.getGoldPrice().getCurrentPrice() * orderToBeStolen.getOrderAmount();
        }
        else if(goldChange < 0)
        {
            //Sell order
            goldAmount += orderToBeStolen.getOrderAmount();
        }
    }

    /**
     * Function checks every orders from other countries and returns the closest
     * @return orders that is closest to the agent
     */
    public Order findClosestOrder()
    {
        Country country; // country that agent currently checks its orders
        Order tmpOrder; // order that agent currently checks
        double tmpDistance;// current distance to order from the agent
        Order closestOrder = null; // closest order that will be return
        double minDistance= Integer.MAX_VALUE; // minimum distance of the orders
        for(int i=0 ; i< 5 ;i++)// for all countries
        {
            country = Common.countries[i];
            if(country == agentCountry)// does not check origin country orders
                continue;
            for(int j = 0; j<country.orders.size(); j++)
            {
                tmpOrder =  country.orders.get(j);
                tmpDistance = this.getPosition().distanceTo(tmpOrder.getPosition().getX(),tmpOrder.getPosition().getY());
                if(tmpDistance < minDistance){
                    //Current order is closer than the previous closest one
                    minDistance = tmpDistance;
                    closestOrder = tmpOrder;
                }

            }
        }
        return closestOrder;
    }

}