package gameObjects;

import general.*;

/**
 * Rock class.
 */
public class Rock extends GameObject {

    /**
     * Character representing the rock.
     */
    public static final String ROCK_CHARACTER = "@ ";

    public static final String ROCK_EMOJI = "🪨 ";

    /**
     * Color rappresentation of the rock.
     */
    public static final Color WALL_COLOR = new Color(Color.ANSI_RED_BACKGROUND);
    /**
     * Constructor.
     * 
     * @param position
     * The initial position of the game object.
     */
    public Rock(Point position, boolean useEmoji) {
        super(position, (useEmoji? ROCK_EMOJI : ROCK_CHARACTER), WALL_COLOR,true);
    }

    /**
     * method used to return the name of the object
     * @return the name of the object
     */
    @Override
    public ObjectName getType() {
        return ObjectName.Rock;
    }
    
}
