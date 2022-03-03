package Menu;
import GameEngine.GameLogic;

public class ExitOption extends Option {
    private GameLogic logic;

    public ExitOption(GameLogic logic){
        this.logic = logic;
    }
    @Override
    public void execute(int choice) {
        logic.gameOver();
    }
    @Override
    public String toString() {
        return "Exit";
    }
}
