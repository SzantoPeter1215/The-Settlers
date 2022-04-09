package model;
import model.soldier.Soldier;

import java.security.PublicKey;
import java.util.ArrayList;

public class Field {
    //public ArrayList<Type> stuffOnTheField;
    public ArrayList<Soldier> soldiersOnTheField;
    public ArrayList<Tower> towersOnTheField;
    public ArrayList<Castle> castlesOnTheField;

    public boolean isHill;
    public boolean isWater;

    public Field(){
        soldiersOnTheField = new ArrayList<Soldier>();
        towersOnTheField = new ArrayList<Tower>();
        castlesOnTheField = new ArrayList<Castle>();

        isHill = false;
        isWater = false;
    }
    public boolean addSoldier(Soldier soldier){
        if(isTowerOnTheField()||isCastleOnTheField()){
            return false;
        }
        soldiersOnTheField.add(soldier);
        return true;
    }
    public boolean addTower(Tower tower){
        if(isEmpty()){
            towersOnTheField.add(tower);
            return true;
        }
        return  false;
    }
    public boolean addCaslte(Castle castle){
        if(isEmpty()){
            castlesOnTheField.add(castle);
            return true;
        }
        return  false;
    }

    public boolean addHill() {
        if(isEmpty()){
            this.isHill = true;
            return true;
        }
        return  false;
    }

    public boolean addWater() {
        if(isEmpty()){
            this.isWater = true;
            return true;
        }
        return  false;
    }

    public boolean getHill() {
        return this.isHill;
    }

    public boolean getWater() {
        return this.isWater;
    }

    public boolean isEmpty(){
        return soldiersOnTheField.size()==0&&towersOnTheField.size()==0;
    }
    public boolean hasRange(){
        return isTowerOnTheField() || isCastleOnTheField();
    }
    public boolean isTowerOnTheField(){
        if (towersOnTheField.size()>0){
            return true;
        }
        return false;
    }
    public boolean isCastleOnTheField(){
        if(castlesOnTheField.size()>0){
            return true;
        }
        return false;
    }
    /*public Type getMainType(){//it's used when you are sure that you have castle,tower1 or tower2
        if(stuffOnTheField.size()>0) {
            return stuffOnTheField.get(0);
        }
        else{
            return Type.EMPTY;
        }
    }*/
    public boolean isPlayer1Tower1Field(){
        for (Tower tower:
             towersOnTheField) {
            if(tower.TowerType==Type.PLAYER1_TOWER1) return true;
        }
        return false;
    }
    public boolean isPlayer1Tower2Field(){
        for (Tower tower:
                towersOnTheField) {
            if(tower.TowerType==Type.PLAYER1_TOWER2) return true;
        }
        return false;
    }
    public boolean isPlayer2Tower1Field(){
        for (Tower tower:
                towersOnTheField) {
            if(tower.TowerType==Type.PLAYER2_TOWER1) return true;
        }
        return false;
    }
    public boolean isPlayer2Tower2Field(){
        for (Tower tower:
                towersOnTheField) {
            if(tower.TowerType==Type.PLAYER2_TOWER2) return true;
        }
        return false;
    }
    public boolean hasMoreThingOnTheField(){
        return soldiersOnTheField.size()>1;
    }
    public void removeSoldier(){
        soldiersOnTheField.clear();
    }
    public Tower getTowerOnTheField(){
        if(isTowerOnTheField()){
            return towersOnTheField.get(0);
        }
        else{
            return null;
        }
    }
    public Castle getCastleOnTheField(){
        if(isCastleOnTheField()){
            return castlesOnTheField.get(0);
        }
        return null;
    }
    public int CountOfTheSoldier(){
        return soldiersOnTheField.size();
    }
    public Soldier getFirstSoldier(){
        if(CountOfTheSoldier()>0){
            return soldiersOnTheField.get(0);
        }
        return null;
    }
}
