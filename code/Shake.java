/**
 *  Child class for State.
 *  A state where the IA makes random dispositions in horizontal
 * and vertical directions.
 *
 *  @author Yunus Emre Gök 2166460
 */
public class Shake extends State {

    public Shake(){
        this.name = "Shake";
    }

    /**
     * Agent makes random dispositions in horizontal
     * and vertical directions.
     * Dispositions will be a random value between 5 and -5 on both
     * x-axis and y- axis
     * this method calls changeState() for random state change.
     * @param agent which will move
     */
    @Override
    void move(Agent agent) {
        double deltaX = 0.15*(Common.getRandomGenerator().nextInt(11)-5);
        double deltaY = 0.15*(Common.getRandomGenerator().nextInt(11)-5);
        Position newPosition = new Position(agent.getPosition().getX()+deltaX, agent.getPosition().getY()+deltaY);
        agent.setPosition(newPosition);

        changeState(agent);

    }
    // TODO
}