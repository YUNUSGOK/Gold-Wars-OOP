/**
 *  Base class for Rest, Shake, GotoXY, and ChaseClosest.
 *  An Agent has a state that it changes at random.
 *  State determines and controls the movement of the Agent
 *
 *  @author Yunus Emre Gök 2166460
 */
public abstract class State {
    protected String name;  //State name

    /**
     * Changes position of the Agent depending on the state type
     * @param agent
     */
    abstract void move(Agent agent);

    /**
     * @return State name
     */
    public String getName(){return name;}

    /**
     * Random change state action will be triggered in this method.
     * When next random integer becomes 0. State will be over and
     * state finished method will be called to change state
     * @param agent which its state will be changed
     */
    protected void changeState(Agent agent)
    {
        if(Common.getRandomGenerator().nextInt(200)==0)
        {
            stateFinished(agent);
        }

    }

    /**
     * State will change to between Rest, Shake, GotoXY, and ChaseClosest randomly.
     * This method will be called when changeState condition is satisfied or GoToXY
     * state reaches its destination.
     * @param agent
     */
    protected void stateFinished(Agent agent){
        int stateDecider = Common.getRandomGenerator().nextInt(4);
        if(stateDecider==0)
        {
            agent.setState(new Rest());
        }
        else if(stateDecider==1)
        {
            agent.setState(new Shake());
        }
        else if(stateDecider==2)
        {
            agent.setState(new ChaseClosest());
        }
        else if(stateDecider==3)
        {
            agent.setState(new GotoXY());
        }
    }
}