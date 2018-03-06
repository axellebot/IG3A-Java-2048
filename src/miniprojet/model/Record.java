package miniprojet.model;

public class Record {
    private String nickname;
    private int moveCounter;
    private long timeCounter;
    private int maxValue;

    public Record(String nickname, int moveCounter, long timeCounter, int maxValue) {
        this.nickname = nickname;
        this.moveCounter = moveCounter;
        this.timeCounter = timeCounter;
        this.maxValue = maxValue;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

    public long getTimeCounter() {
        return timeCounter;
    }

    public void setTimeCounter(long timeCounter) {
        this.timeCounter = timeCounter;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public String toString() {
        return "Record{" +
                "nickname='" + nickname + '\'' +
                ", moveCounter=" + moveCounter +
                ", timeCounter=" + timeCounter +
                ", maxValue=" + maxValue +
                '}';
    }
}
