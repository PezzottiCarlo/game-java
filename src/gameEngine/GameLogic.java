package gameEngine;

import menu.Menu;
import menu.option.GenericOption;
import gameObjects.*;
import general.*;
import java.util.*;

/**
 * Logic for the game.
 *
 * @author Matteo Arena
 * @author Carlo Pezzotti
 */
public class GameLogic {

    // ==================== Attributes ===================

    public static final int NUMBER_OF_POTIONS = 3;
    /**
     * Default coins number.
     */
    public static final int NUMBER_OF_COINS = 10;

    /**
     * Default number of players.
     */
    public static final int NUMBER_OF_PLAYERS = 2;
    /**
     * Default number of gems.
     */
    public static final int NUMBER_OF_GEMS = 5;
    /**
     * Default number of rocks.
     */
    public static final int NUMBER_OF_ROCKS = 5;
    /**
     * Default number of trees.
     */
    public static final int NUMBER_OF_TREES = 7;

    /**
     * Flag true when the game is ended.
     */
    private boolean gameOver = false;

    /**
     * List with all the objects of the game.
     */
    private final List<GameObject> gameObjects;

    /**
     * Board width.
     */
    private final int sizeX;

    /**
     * Board height.
     */
    private final int sizeY;

    /**
     * Current player (the one who is playing).
     */
    private Player currentPlayer;

    private boolean useEmoji;

    // ==================== Constructors ====================

    /**
     * Constructor.
     *
     * @param sizeX Board width.
     * @param sizeY Board height.
     */
    public GameLogic(int sizeX, int sizeY) {
        this(sizeX, sizeY, false);
    }

    /**
     * Constructor.
     *
     * @param sizeX    Board width.
     * @param sizeY    Board height.
     * @param useEmoji True when the player wants to use emoji.
     */
    public GameLogic(int sizeX, int sizeY, boolean useEmoji) {
        this.useEmoji = useEmoji;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        gameObjects = new ArrayList<>();
        generateItems();
    }

    // ==================== Getters and Setters ====================

    /**
     * Get the full list of game objects.
     *
     * @return The list of game objects.
     */
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    /**
     * Get the current player.
     *
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Method that returns the winner
     * 
     * @return the winning player
     */
    public Player getWinner() {
        Player bestPlayer = null;
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Player) {
                if (bestPlayer == null) {
                    bestPlayer = (Player) gameObject;
                } else {
                    if (bestPlayer.getCoins() < ((Player) gameObject).getCoins()) {
                        bestPlayer = (Player) gameObject;
                    }
                }
            }
        }
        return bestPlayer;
    }

    // ==================== private methods ====================
    /**
     * Generate all the objects of the game.
     */
    private void generateItems() {
        generatePlayers();
        generate(ObjectName.Coin, NUMBER_OF_COINS);
        generate(ObjectName.Gem, NUMBER_OF_GEMS);
        generate(ObjectName.Rock, NUMBER_OF_ROCKS);
        generate(ObjectName.Potion, NUMBER_OF_POTIONS);
        generate(ObjectName.Tree, NUMBER_OF_TREES);
    }

    /**
     * Method that generates an object based on the objectname parameter
     * 
     * @param objectName the object name
     * @param quantity   the number of objects to generate
     */
    private void generate(ObjectName obj, int quantity) {
        for (int i = 0; i < quantity; i++) {
            switch (obj) {
                case Gem:
                    gameObjects.add(new Gem(getRandomFreeCell(), useEmoji));
                    break;
                case Potion:
                    gameObjects.add(new Potion(getRandomFreeCell(), useEmoji));
                    break;
                case Rock:
                    gameObjects.add(new Rock(getRandomFreeCell(), useEmoji));
                    break;
                case Tree:
                    gameObjects.add(new Tree(getRandomFreeCell(), useEmoji));
                    break;
                case Coin:
                    gameObjects.add(new Coin(getRandomFreeCell(), useEmoji));
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Method used to generate players.
     */
    private void generatePlayers() {
        currentPlayer = new Player(
                new Point(0, sizeY - 1),
                "Player0",
                "X ");
        gameObjects.add(currentPlayer);
        gameObjects.add(new Player(
                new Point(sizeX - 1, 0),
                "Player1",
                "Y "));
    }

    /**
     * Generate a random free cell.
     *
     * @return A random free cell.
     */
    private Point getRandomFreeCell() {
        int x;
        int y;
        do {
            x = (int) (Math.random() * sizeX);
            y = (int) (Math.random() * sizeY);
        } while (!isFreeCell(new Point(x, y)));
        return new Point(x, y);
    }

    /**
     * Check if a cell is free.
     *
     * @param point Cell to check.
     * @return true if the cell is free, false otherwise.
     */
    private boolean isFreeCell(Point point) {
        return getGameObjectAtPosition(point) == null;
    }

    /**
     * Check if the player is on an other object.
     *
     * @param player Player to check.
     * @return If the player is on an other object returns the object, otherwise
     *         null.
     */
    private GameObject isOver(Player player) {
        for (GameObject object : gameObjects) {
            if (!object.equals(player)) {
                if (object.getXPosition() == player.getXPosition()) {
                    if (object.getYPosition() == player.getYPosition()) {
                        return object;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Return content of the cell.
     * 
     * @param point Cell to check.
     * @return The content of the cell.
     */
    private GameObject getGameObjectAtPosition(Point point) {
        for (GameObject object : gameObjects) {
            if (object.getPosition().equals(point)) {
                return object;
            }
        }
        return null;
    }

    /**
     * Method used to ask the user if he wants to use a gem in case of fight
     * 
     * @param player the player who is using the gem
     * @return true if the player wants to use the gem, false otherwise
     */
    private boolean askForGem(Player player) {
        Menu menu = new Menu(true, player.getUsername() + " use gem to escape?");
        GenericOption useGem = new GenericOption("Yes use gem", 'y', () -> {
            player.setPosition(getRandomFreeCell());
            player.decrementGems();
        });
        GenericOption back = new GenericOption("Don't use gem", 'n', () -> {
        });
        menu.addMenu(useGem);
        menu.addMenu(back);
        return menu.ask() == 0;
    }

    /**
     * Method used to ask the user if he wants to use a potion in case of fight
     * 
     * @param player the player who is using the potion
     * @return true if the player wants to use the potion, false otherwise
     */
    private boolean askForPotion(Player player) {
        Menu menu = new Menu(true, player.getUsername() + " use potion to win?");
        GenericOption usePotion = new GenericOption("Yes use the potion", 'y', () -> {
            player.decrementPotions();
        });
        GenericOption back = new GenericOption("Don't use the potion", 'n', () -> {
        });
        menu.addMenu(usePotion);
        menu.addMenu(back);
        return menu.ask() == 0;
    }

    /**
     * Fight between two players.
     *
     * @param player1 Player 1.
     * @param player2 Player 2.
     * @throws InterruptedException If the thread is interrupted.
     */
    private void fight(Player player1, Player player2) throws InterruptedException {
        System.out.println("\n" + player1.getUsername() + " vs " + player2.getUsername());

        Player[] players = { player1, player2 };
        int[] playerThrows = new int[2];

        Player winner = null;
        Player loser = null;

        for (int i = 0; i < players.length; i++) {
            if (players[i].getGems() > 0) {
                if (askForGem(players[i])) {
                    return;
                }
            }

            if (players[i].getPotions() > 0) {
                if (askForPotion(players[i])) {
                    winner = players[i];
                    loser = players[(i + 1) % 2];
                    break;
                }
            }

            playerThrows[i] = throwPlayerDice(players[i]);
        }

        if (winner == null && loser == null) {
            if (playerThrows[0] > playerThrows[1]) {
                winner = players[0];
                loser = players[1];
            } else if (playerThrows[1] > playerThrows[0]) {
                winner = players[1];
                loser = players[0];
            } else {
                System.out.println("Draw!");
                fight(player1, player2);
                return;
            }
        }
        if (loser.getCoins() > 0) {
            winner.incrementCoins();
            loser.decrementCoins();
            movePlayerToInitialPosition(loser);
            System.out.println(winner.getUsername() + " wins the match!!");
        }else{
            System.out.println(winner.getUsername() + " wins the game!!");
            gameOver();
        }
        Thread.sleep(1000);
    }

    /**
     * Throws the dice for a player.
     *
     * @param player Player who wants to throw the dice.
     *
     * @return The result of the throw.
     */
    private int throwPlayerDice(Player player) {
        System.out.print(player.getUsername() + "'s result: ");
        return Dice.throwDice();
    }

    /**
     * Method used to check if a number is over another number, used to make the
     * 'Pacman' effect.
     *
     * @param n    Number to check.
     * @param size Size that we don't want to go over.
     * @return The new number (the same if it was already good).
     */
    private int checkPosition(int n, int size) {
        if (n >= 0)
            return n % size;
        else
            return size + n % size;
    }

    /**
     * Method used to move the player to his starting position.
     *
     * @param player player to move.
     */
    private void movePlayerToInitialPosition(Player player) {
        int playerIndex = gameObjects.indexOf(player);
        if (playerIndex == 0) {
            gameObjects.get(playerIndex).setPosition(new Point(0, sizeY - 1));
        } else if (playerIndex == 1) {
            gameObjects.get(playerIndex).setPosition(new Point(sizeX - 1, 0));
        }
    }

    /**
     * Method used to check whether an object with collisions is present at a
     * position
     * 
     * @param x the x position
     * @param y the y position
     * @return return the object otherwise null
     */
    private GameObject collideCheck(int x, int y) {
        Point tmp = new Point(x, y);
        GameObject object = getGameObjectAtPosition(tmp);
        if (object != null && object.canCollide())
            return object;
        return null;
    }

    // ==================== public methods ====================

    /**
     * Move the player passed as parameter.
     *
     * @param player    Player to move.
     * @param direction Direction to move (North, South, East or West).
     * @return returns the object it collides with if it collides otherwise null
     */
    public GameObject movePlayer(Player player, Direction direction) {
        int x = player.getXPosition();
        int y = player.getYPosition();
        GameObject obj = null;
        switch (direction) {
            case NORTH:
                obj = collideCheck(x, checkPosition(y - 1, sizeY));
                if (obj == null)
                    player.setYPosition(checkPosition(--y, sizeY));
                break;
            case SOUTH:
                obj = collideCheck(x, checkPosition(y + 1, sizeY));
                if (obj == null)
                    player.setYPosition(checkPosition(++y, sizeY));
                break;
            case EAST:
                obj = collideCheck(checkPosition(x + 1, sizeX), y);
                if (obj == null)
                    player.setXPosition(checkPosition(++x, sizeX));
                break;
            case WEST:
                obj = collideCheck(checkPosition(x - 1, sizeX), y);
                if (obj == null)
                    player.setXPosition(checkPosition(--x, sizeX));
                break;
        }
        if (obj != null)
            return obj;

        GameObject over = isOver(player);
        if (over != null) {
            overEvent(player, over);
        }
        return null;
    }

    /**
     * Method called when a player is over an object.
     * 
     * @param player     the player who is over the object
     * @param gameObject the object that the player is over
     */
    private void overEvent(Player player, GameObject gameObject) {
        if (gameObject instanceof Player) {
            try {
                fight(player, (Player) gameObject);
            } catch (InterruptedException e) {
                System.out.println("Error while fighting");
            }
        } else if (gameObject instanceof Coin) {
            player.incrementCoins();
            gameObjects.remove(gameObject);
        } else if (gameObject instanceof Potion) {
            player.incrementPotions();
            gameObjects.remove(gameObject);
        } else if (gameObject instanceof Gem) {
            player.incrementGems();
            gameObjects.remove(gameObject);
        }
    }

    /**
     * Set the game as over.
     */
    public void gameOver() {
        gameOver = true;
    }

    /**
     * Check if the game is over.
     *
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Play the next turn, showing the available options.
     */
    public void nextTurn() {
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Player) {
                if (!gameObject.equals(currentPlayer)) {
                    currentPlayer = (Player) gameObject;
                    return;
                }
            }
        }
    }
}
