package miniprojet.data;

import java.util.List;

public class Data {
    public List<Record> recordList;

    public List<Record> getRecordList() {
        return recordList;
    }

    public void addRecord(Record record){
        recordList.add(record);
    }

    public Record removeRecord(int index){
        return recordList.remove(index);
    }

    public boolean removeRecord(Record record){
        return recordList.remove(record);
    }

}
