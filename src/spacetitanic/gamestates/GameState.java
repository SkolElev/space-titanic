package spacetitanic.gamestates;

public enum GameState {

    START_MENU, PLAYING, GAME_OVER, KILLED, STATION, STATION_STORAGE, STATION_BUY_SHIP, STATION_UPGRADE_SHIP,
    STATION_BUY_UPGRADE;

    public static GameState state = START_MENU;

}
