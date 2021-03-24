/**
 * Base class for Novice, Master, and Expert
 * Has an agent to decorate depending on the worth
 *
 * @author Yunus Emre Gök 2166460
 */

public abstract class AgentDecorator extends Agent {
    protected Agent agent; // agent that will be decorated

    /**
     * Constructor for the Decorator
     * @param agent that will be assigned to decorator agent
     */
    public AgentDecorator(Agent agent) {
        super(agent.agentCountry);
        this.agent = agent;
    }

    /**
     * decorated agent object's step method will be called
     */
    @Override
    public void step() {
        agent.step();
    }
    /**
     * decorated agent object's getWorth method will be called
     * @return  decorated agent's worth
     */
    @Override
    public double getWorth() {
        return agent.getWorth();
    }

    /**
     * decorated agent object's getWorth method will be called
     * @param newPosition decorated agent's position new position
     */
    @Override
    public void setPosition(Position newPosition) {
        agent.setPosition(newPosition);
    }
    /**
     * decorated agent object's getWorth method will be called
     * @return decorated agent's position
     */
    @Override
    public Position getPosition() {
        return agent.getPosition();
    }
}