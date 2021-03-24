import java.awt.*;


/**
 * Base class of the BuyOrder and SellOrder. Agent sells orders for its country.
 * Extends entity and it has a position.
 * An order is produced by a country and initial position will be top the origin country.
 * Order will move towards to line which is under the Gold price's line in y = 100
 * When order reaches the line, it will be executed.
 *
 * @author Yunus Emre Gök 2166460
 */
public abstract class Order extends Entity {
    private final double speed;
    protected final int  orderAmount;
    protected final Country originCountry;
    private final Position destination;// which point of the line it will be move
    protected Position path; // replacement amount depending on the speed and destination
    protected Font font = new Font("Verdana", Font.BOLD, 15);

    /**
     * Constructor for Order. Origin country will be assigned
     * Position will be assigned respect to country
     * @param country is the origin country of the order.
     */
    public Order( Country country) {
        super(country.position.getIntX()+65, country.position.getIntY()-20);
        this.originCountry = country;
        int xAxis, yAxis;
        double distance;
        //order amount will be a random value between 1 and 5
        orderAmount = Common.getRandomGenerator().nextInt(5)+1;
        //Speed will be a random value between 2 and 5
        speed = Common.getRandomGenerator().nextInt(4)+2;
        //xAxis is the destination's x-axis which will be a random value between 0 and 1650 which is the screen width
        xAxis = Common.getRandomGenerator().nextInt(1650);
        //yAxis is the destination's y-axis which will the line below gold price
        yAxis = 100;
        destination = new Position(xAxis, yAxis );
        //distance from current position ton destionation
        distance = this.position.distanceTo(destination.getX(),destination.getY());
        path = new Position(0,0);
        path.setX(speed*(destination.getX()-position.getX())/distance);
        path.setY(speed*(destination.getY()-position.getY())/distance);
    }


    public int getOrderAmount() { return orderAmount;}

    /**
     * At every step order moves towards to destination point on the destination line.
     * If it gets near the line, order will be delivered
     */
    public void step() {

        position.setX(getPosition().getX() + path.getX());
        position.setY(getPosition().getY() + path.getY());
        if(position.getY()<130)
        {
            deliverOrder();
        }


    }

    /**
     * Abstract method to execute the order when its delivered to the destination line
     */
    public abstract void deliverOrder();

    /**
     * Abstract method that returns depending on the order type
     * @return if its a buy order, it return +orderAmount
     * @return if its a sell order, it return -orderAmount
     */
    public abstract int goldChange();


}