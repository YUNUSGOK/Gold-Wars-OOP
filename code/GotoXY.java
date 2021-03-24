/**
 *  Child class for State.
 *  A state where the IA picks a random position as its destination
 *  and moves there with a random speed.
 *
 *  @author Yunus Emre Gök 2166460
 */
public class GotoXY extends State {
    private double destinationX; //random destination x-axis
    private double destinationY; //random destination y-axis
    private double speed; // random speed of the movement

    /**
     * Constructor of the GotoXY
     * Destination and speed will be chosen randomly when instance is created.
     */
    public GotoXY()
    {
        name = "GoToXY";
        //x axis will be a random value between 0 and 1550 to move in the  screen
        destinationX = Common.getRandomGenerator().nextInt(1550);
        //y axis will be a random value between 100 and 600 to move in the screen
        destinationY = Common.getRandomGenerator().nextInt(500)+100;
        //Speed will be a random value between 0.6 and 3.6
        speed = 3*Common.getRandomGenerator().nextDouble()+0.6;
    }

    /**
     * Agent will move to randomly chosen destination point.
     * If distance becomes smaller than the speed, it will reach
     * the destination and state will be finished and stateFinished() method will be called to change state.
     * At every step, changeState()will be called for random state change.
     * @param agent which will move
     */
    @Override
    void move(Agent agent) {
        double distance = agent.getPosition().distanceTo(destinationX, destinationY);
        if(distance > speed) {
            double deltaX = speed * (destinationX - agent.getPosition().getX()) / distance;
            double deltaY = speed * (destinationY - agent.getPosition().getY()) / distance;
            Position newPosition = new Position(agent.getPosition().getX() + deltaX, agent.getPosition().getY() + deltaY);
            agent.setPosition(newPosition);
        }
        else {
            stateFinished(agent);
        }
        changeState(agent);

    }

}