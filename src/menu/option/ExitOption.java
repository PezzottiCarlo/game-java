package menu.option;
import gameEngine.GameBoard;

/**
 * ExitOption is an option that exits the game.
 *
 * @author Matteo Arena
 * @author Carlo Pezzotti
 */
public class ExitOption extends Option {
    // ==================== Constructors ====================

    /**
     * Constructor.
     *
     * @param board The game board.
     */
    public ExitOption(GameBoard board){
        super(board);
    }
    //==================== public methods ====================

    /**
     * Execute the option, in this case ends the game.
     *
     * @param choice The choice of the player.
     */
    @Override
    public void execute(int choice){
        board.getGameLogic().gameOver();
    }

    /**
     * Get the name of the option.
     *
     * @return The name of the option.
     */
    @Override
    public String toString() {
        return "Exit";
    }
}
