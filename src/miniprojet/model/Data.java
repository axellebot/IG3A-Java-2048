package miniprojet.model;

import miniprojet.Observable;
import miniprojet.Observer;

import java.util.ArrayList;
import java.util.List;

public class Data implements Observable {

    private List<Record> recordList;
    private List<Observer> observerList;

    public Data() {
        this.recordList = new ArrayList<>();
        this.observerList = new ArrayList<>();
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    public void addRecord(Record record) {
        recordList.add(record);
    }

    public Record removeRecord(int index) {
        return recordList.remove(index);
    }

    public boolean removeRecord(Record record) {
        return recordList.remove(record);
    }

    static public Data getInstance() {
        Data data = new Data();
        return data;
    }

    @Override
    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer obs : observerList) {
            obs.update();
        }
    }

    @Override
    public String toString() {
        String tmp = "";
        for (Record record : recordList) {
            tmp += record.toString() + '\n';
        }
        return tmp;
    }
}
