/**
 *  Child class for State.
 *  A state where the IA picks the closest order as its
 *  destination and chases it with a random speed.
 *
 *  @author Yunus Emre Gök 2166460
 */
public class ChaseClosest extends State {
    Order closestOrder; //Closest order that will be chased.
    private double destinationX; // order's x-axis
    private double destinationY; // order's y-axis
    private double speed; // random speed of the movement

    public ChaseClosest(){
        this.name = "ChaseClosest";
        //Speed will be a random value between 0.6 and 3.6
        speed = 3*Common.getRandomGenerator().nextDouble()+0.6;

    }
    /**
     * Agent will move to the closest order
     * At every step, it finds the closest order of the agent and chase it.
     * At every step, changeState()will be called for random state change.
     * @param agent which will move
     */
    @Override
    void move(Agent agent) {
        //find the closest order to the agent
        closestOrder = agent.findClosestOrder();

        if(closestOrder !=null) { //if there is order, agent chases it.
            /* Agent will try to move its center to the order. Since its radius is 50 unit,
            * destination will be 50 unit shifted from orders position
            * */
            destinationX = closestOrder.getPosition().getX()-50;
            destinationY = closestOrder.getPosition().getY()-50;
            double distance = agent.getPosition().distanceTo(destinationX, destinationY);
            if (distance > speed) {
                double deltaX = speed * (destinationX - agent.getPosition().getX()) / distance;
                double deltaY = speed * (destinationY - agent.getPosition().getY()) / distance;
                Position newPosition = new Position(agent.getPosition().getX() + deltaX, agent.getPosition().getY() + deltaY);
                agent.setPosition(newPosition);
            }

        }
        changeState(agent);
    }


}