public class Giocatore {
    private String username;
    private Point coordinate;
    private int coin;
    public static final char CHARACTER = 'X';

    public Giocatore(String username, Point coordinate) {
        setUsername(username);
        setCoordinate(coordinate);
    }

    public void setCoordinate(Point coordinate) {
        if(coordinate != null){
            this.coordinate = coordinate;
        }
    }

    public void setUsername(String username) {
        if(username.matches("[a-zA-Z0-9]+")){
            this.username = username;
        }
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        if (coin >= 0)
            this.coin = coin;
    }

    public String getUsername() {
        return username;
    }

    public Point getCoordinate() {
        return coordinate;
    }
}
