package model;
import gui.GameUIConstants;

public class TrainingField {
        public PlayerTurn OwnerPlayer;
        public int health;
        public Type TowerType;
        public TrainingField(PlayerTurn OwnerPlayer,Type TowerType){
            this.OwnerPlayer = OwnerPlayer;
            this.TowerType = TowerType;
        }
}
