package miniprojet.model;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard {
    private List<Person> personList;

    public Leaderboard() {
        this.personList = new ArrayList<>();
    }

    public void loadData() {
        personList.clear();
        addPerson(new Person("Jane Doe", 2048, 12));
        addPerson(new Person("John Smith", 2048, 12));
        addPerson(new Person("Kathy Green", 2048, 12));
    }

    public void addPerson(Person p) {
        personList.add(p);
    }

    public void removePerson(Person p) {
        personList.remove(p);
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public Person searchPerson(String nickname) {
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getNickname().compareTo(nickname) == 0) {
                return personList.get(i);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String _tmp = "";
        for (Person p : personList) {
            _tmp += p.toString() + '\n';
        }
        return _tmp;
    }
}
