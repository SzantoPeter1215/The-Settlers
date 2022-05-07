package main.java.hu.elte.fi.szofttech.pacman.model;

import main.java.hu.elte.fi.szofttech.pacman.gui.GameUIConstants;

public class Castle {
    public PlayerTurn OwnerPlayer;
    public int health;
    public Type TowerType;
    public int range;
    public int damage;
    public Castle(PlayerTurn OwnerPlayer,int health,Type TowerType, int range,int damage){
        this.OwnerPlayer = OwnerPlayer;
        this.health = health;
        this.TowerType = TowerType;
        this.range = range;
        this.damage = damage;
    }
    public Type getCastlePlayer(PlayerTurn OwnerPlayer){
        if(OwnerPlayer==PlayerTurn.PLAYER1){
            return Type.PLAYER1_CASTLE;
        }
        else{
            return Type.PLAYER2_CASTLE;
        }
    }
    public String castleImage(){
        if(OwnerPlayer==PlayerTurn.PLAYER1){
            return GameUIConstants.Player1_Castle;
        }
        else{
            return GameUIConstants.Player2_Castle;
        }
    }
    public void looseHealth(int d) {
        if(health-damage>=0){
            health -= damage;
        }
        else{
            health = 0;
        }
    }
}
