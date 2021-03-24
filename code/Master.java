import java.awt.*;

/**
 * Child class of Agent Decorator
 * If agent's worth is higher than 4000 it will display yellow badge on the top the agent image
 *
 * @author Yunus Emre Gök 2166460
 */

public class Master extends AgentDecorator {
    /**
     * Constructor of Master
     * @param agent that will be decorated
     */
    public Master( Agent agent) {
        super(agent);
    }

    /**
     * Agent's draw method will be called
     * Above the agent name, yellow badge will be displayed
     * @param g2d Graphics2D component the draw.
     */
    @Override
    public void draw(Graphics2D g2d) {
        agent.draw(g2d);
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(agent.getPosition().getIntX()+10+20+3, agent.getPosition().getIntY()-50,20,20);
    }



}