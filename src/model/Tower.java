package model;

import gui.GameUIConstants;

public class Tower {
    public PlayerTurn OwnerPlayer;
    public int health;
    public Type TowerType;
    public int range;
    public int damage;
    public Tower(PlayerTurn OwnerPlayer,int health,Type TowerType, int towerRange, int damage){
        this.health = health;
        this.OwnerPlayer = OwnerPlayer;
        this.TowerType = TowerType;
        this.range = towerRange;
        this.damage = damage;
    }
    public static Type playerTowerType(PlayerTurn playerTurn,Type towerType){
        if(playerTurn == PlayerTurn.PLAYER1&&towerType==Type.TOWER1){
            return Type.PLAYER1_TOWER1;
        }
        else if(playerTurn == PlayerTurn.PLAYER1&&towerType==Type.TOWER2){
            return Type.PLAYER1_TOWER2;
        }
        else if(playerTurn == PlayerTurn.PLAYER2&&towerType==Type.TOWER1){
            return Type.PLAYER2_TOWER1;
        }
        else if(playerTurn == PlayerTurn.PLAYER2&&towerType==Type.TOWER2){
            return Type.PLAYER2_TOWER2;
        }
        return Type.EMPTY;
    }
    public static String towerImage(Type playerTower){
        if(playerTower==Type.PLAYER1_TOWER1){
            return GameUIConstants.Player1Tower1;
        }
        else if(playerTower==Type.PLAYER1_TOWER2){
            return GameUIConstants.Player1Tower2;
        }
        else if(playerTower==Type.PLAYER2_TOWER1){
            return GameUIConstants.Player2Tower1;
        }
        else{
            return GameUIConstants.Player2Tower2;
        }
    }
}
