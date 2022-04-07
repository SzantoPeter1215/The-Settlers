package model;

import gui.GameUIConstants;

public class Castle {
    public PlayerTurn OwnerPlayer;
    public int health;
    public Type TowerType;
    public Castle(PlayerTurn OwnerPlayer,int health,Type TowerType){
        this.OwnerPlayer = OwnerPlayer;
        this.health = health;
        this.TowerType = TowerType;
    }
    public static Type getCastlePlayer(PlayerTurn OwnerPlayer){
        if(OwnerPlayer==PlayerTurn.PLAYER1){
            return Type.PLAYER1_CASTLE;
        }
        else{
            return Type.PLAYER2_CASTLE;
        }
    }
    public static String castleImage(Type CastleType){
        if(Type.PLAYER1_CASTLE==CastleType){
            return GameUIConstants.Player1_Castle;
        }
        else{
            return GameUIConstants.Player2_Castle;
        }
    }
}
