package main.java.hu.elte.fi.szofttech.pacman.model;

public class TrainingField {
        public PlayerTurn OwnerPlayer;
        public Type TowerType;
        public int x;
        public int y;
        public TrainingField(PlayerTurn OwnerPlayer,Type TowerType,int x, int y){
            this.OwnerPlayer = OwnerPlayer;
            this.TowerType = TowerType;
            this.x = x;
            this.y = y;
        }
}
