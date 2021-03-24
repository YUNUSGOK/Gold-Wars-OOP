import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Basic agent is child class Agent class and display the image, worth and name of the agent.
 *
 * @author Yunus Emre Gök 2166460
 */
public class BasicAgent extends Agent {
    /**
     * Constructor of the BasicAgent.
     * @param agentCountry origin country that it will belong and will be positioned respect to it
     * @param name Agent name
     * @param filename Agent's image file name
     */
    public BasicAgent(Country agentCountry, String name , String filename) {
        super(agentCountry); //Parent class constructor call for positioning and origin country assignment
        this.name = name;
        this.filename = filename;
        this.imgPath = "../images/" + this.filename +".png";
        try {
            File file = new File(imgPath);
            img = ImageIO.read(file);
        }
        catch (IOException e) {
            System.out.println(imgPath);
        }
    }

    /**
     * What will be displayed and how it be displayed is determined in here.
     * Agent image will displayed with 100 unit diameter at the current position
     * Above the image, agent name wil be displayed in black.
     * Below the image, current state's name will be displayed.
     * Below the current state, current worth will be displayed.
     * @param g2d Graphics2D component the draw.
     */
    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawImage(img, position.getIntX(), position.getIntY(),
                100, 100
                ,null);
        g2d.drawString(name, position.getIntX()+10, position.getIntY()-5);
        g2d.setColor(Color.BLUE);
        g2d.drawString(state.getName(), position.getIntX()+20, position.getIntY()+120);
        g2d.setColor(Color.RED);
        g2d.drawString(String.format("%.0f ", getWorth()), position.getIntX()+20, position.getIntY()+135);


    }
}