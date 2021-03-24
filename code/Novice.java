import java.awt.*;


/**
 * Child class of Agent Decorator
 * If agent's worth is higher than 2000 it will display white badget on the top the agent image
 *
 * @author Yunus Emre Gök 2166460
 */

public class Novice extends AgentDecorator {
    /**
     * Constructor of Novice
     * @param agent that will be decorated
     */
    public Novice( Agent agent) {
        super(agent);

    }

    /**
     * Agent's draw method will be called
     * Above the agent name, white badge will be displayed
     * @param g2d Graphics2D component the draw.
     */
    @Override
    public void draw(Graphics2D g2d) {

        agent.draw(g2d);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(agent.getPosition().getIntX()+10, agent.getPosition().getIntY()-50,20,20);
    }


}