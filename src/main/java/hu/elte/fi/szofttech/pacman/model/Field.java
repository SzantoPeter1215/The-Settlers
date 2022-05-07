package main.java.hu.elte.fi.szofttech.pacman.model;
import main.java.hu.elte.fi.szofttech.pacman.model.soldier.Soldier;

import java.util.ArrayList;
import java.util.List;

public class Field {
    //public ArrayList<Type> stuffOnTheField;
    public ArrayList<Soldier> soldiersOnTheField;
    public ArrayList<Tower> towersOnTheField;
    public ArrayList<Castle> castlesOnTheField;
    public ArrayList<TrainingField> trainingFields;

    public boolean isHill;
    public boolean isWater;
    public int x;
    public int y;

    public Field(int x, int y){
        soldiersOnTheField = new ArrayList<Soldier>();
        towersOnTheField = new ArrayList<Tower>();
        castlesOnTheField = new ArrayList<Castle>();
        trainingFields = new ArrayList<>();
        this.x = x;
        this.y = y;

        isHill = false;
        isWater = false;
    }
    public boolean addSoldier(Soldier soldier){
        if(isTowerOnTheField()&&!isTrainingFieldOnThisField()){
            return false;
        }
        soldiersOnTheField.add(soldier);
        return true;
    }
    public List<Soldier> getSoliders(){
        if(soldiersOnTheField.size()>0){
            return soldiersOnTheField;
        }
        return null;
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
    public TrainingField addTrainingField(Type traininFieldType){
        PlayerTurn playerTurn = traininFieldType==Type.PLAYER1_TrainingField ? PlayerTurn.PLAYER1 : PlayerTurn.PLAYER2;
        TrainingField trainingField = new TrainingField(playerTurn,traininFieldType,x,y);
        trainingFields.add(trainingField);
        return trainingField;
    }
    public boolean isTrainingFieldOnThisField(){
        return trainingFields.size()>0;
    }
    public boolean isPlayer1TrainingFiled(){
        for (int i = 0; i < trainingFields.size(); i++) {
            if(trainingFields.get(i).TowerType==Type.PLAYER1_TrainingField){
                return true;
            }
        }
        return false;
    }
    public boolean isPlayer2TrainingFiled(){
        for (int i = 0; i < trainingFields.size(); i++) {
            if(trainingFields.get(i).TowerType==Type.PLAYER2_TrainingField){
                return true;
            }
        }
        return false;
    }
    public TrainingField getTrainingField(){
        return trainingFields.get(0);
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
        return soldiersOnTheField.size()==0&&towersOnTheField.size()==0&&trainingFields.size()==0;
    }
    public boolean hasRange(){
        return isTowerOnTheField() || isCastleOnTheField();
    }
    public boolean isTowerOnTheField(){
        return towersOnTheField.size() > 0;
    }
    public boolean isCastleOnTheField(){
        return castlesOnTheField.size() > 0;
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
        if(soldiersOnTheField.size()>0) {
            soldiersOnTheField.clear();
        }
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
