package model;
import java.util.ArrayList;

public class Field {
    public ArrayList<Type> stuffOnTheField;
    public Field(){
        stuffOnTheField = new ArrayList<Type>();
    }
    public void add(Type thing){
        if(isTower(thing)&&!isTowerOnTheField()) {
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
}
