package miniprojet.model;

/**
 * @ Axel LE BOT
 */
public class Tile {
    private int value;
    private int x, y;

    public Tile(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public Tile(Tile c) {
        this.value = c.getValue();
        this.x = c.getX();
        this.y = c.getY();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }


}
