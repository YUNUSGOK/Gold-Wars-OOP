/**
 *  Child class for State.
 *  A state where the IA stays still.
 *
 *  @author Yunus Emre Gök 2166460
 */
public class Rest extends State {

    public Rest(){
        name = "Rest";
    }

    /**
     * Agent position does not change.
     * This method calls changeState() for random state change.
     * @param agent which will move
     */
    @Override
    void move(Agent agent) {
        changeState(agent);
    }
    // TODO
}