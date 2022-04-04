package model;
import java.util.ArrayList;

public class Field {
    public ArrayList<Type> stuffOnTheField;
    public Field(){
        stuffOnTheField = new ArrayList<Type>();
    }
    public void add(Type thing){
        if(isTower(thing)&&isTowerOnTheField()) {
            stuffOnTheField.add(thing);
        }
        else {
            stuffOnTheField.add(thing);
        }
    }
    private boolean isTowerOnTheField(){
        if (stuffOnTheField.contains(Type.TOWER1)||stuffOnTheField.contains(Type.TOWER2)){
            return true;
        }
        return false;
    }
    private boolean isTower(Type thing){
        if(thing==Type.TOWER1||thing==Type.TOWER2){
            return  true;
        }
        return false;
    }
    public Type getMainType(){
        if(stuffOnTheField.size()>0) {
            return stuffOnTheField.get(0);
        }
        else{
            return Type.EMPTY;
        }
    }
    public void removeSoldier(){
        ArrayList<Integer> removingIndexes = new ArrayList<Integer>();
        for (int i = 0; i < stuffOnTheField.size(); i++) {
            if(stuffOnTheField.get(i)==Type.SOLDER1||stuffOnTheField.get(i)==Type.SOLDER2){
                removingIndexes.add(i);
            }
        }
        for (Integer removingIndex : removingIndexes) {
            stuffOnTheField.remove((int)removingIndex);
            //stuffOnTheField.clear();
        }

    }
}
