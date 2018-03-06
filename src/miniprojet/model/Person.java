package miniprojet.model;

import java.io.Serializable;

public class Person implements Serializable {
    public String nickname;
    public int score;
    public int time;

    public Person(String nickname, int score, int time) {
        this.nickname = nickname;
        this.score = score;
        this.time = time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Person{" +
                "nickname='" + nickname + '\'' +
                ", score=" + score +
                ", time=" + time +
                '}';
    }
}
