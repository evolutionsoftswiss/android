package android.evolutionsoft.ch.tictactoe.model;

import java.util.Observable;

/**
 * @author EvolutionSoft
 */
public class Game extends Observable {
    
	private Player playerX;
	private Player playerO;
	private char turn;
	private Playground playground;

	private boolean gameOver = false;
	
	public Game(Player firstPlayer, Player secondPlayer) {

		this.playerX = firstPlayer.getColor() == Player.FIRST_PLAYER ? firstPlayer : secondPlayer;
		this.playerO = secondPlayer.getColor() == Player.SECOND_PLAYER ? secondPlayer : firstPlayer;
		this.turn = Player.FIRST_PLAYER;
		this.playground = new Playground();
	}
	
	Playground getPlayground(){

		return this.playground;
	}
	
	public Player getCurrentPlayer(){

		return (this.turn == Player.FIRST_PLAYER) ? playerX : playerO;
	}

	public void switchPlayers() {

	    Player tempPlayer = playerX;

	    this.playerX = playerO;
	    this.playerX.setColor(Player.FIRST_PLAYER);

	    this.playerO = tempPlayer;
	    this.playerO.setColor(Player.SECOND_PLAYER);
    }
	
	public boolean gameOver(){

		return this.gameOver;
	}
	
	public void start() {

		if (this.playerX instanceof ComputerPlayer)
			((ComputerPlayer) this.playerX).move(this);
	}
	
	public void newGame() {

		this.gameOver = false;
		this.turn = Player.FIRST_PLAYER;
		this.playground.reset();
	}
	
	public void move(Move move) {

		this.playground.enterMove(move.getRow(), move.getColumn(), getCurrentPlayer().getColor());

		this.setChanged();
		this.notifyObservers(move);

		this.checkState();
		this.swapTurn();

		if (this.getCurrentPlayer() instanceof ComputerPlayer && !this.gameOver) {

            ((ComputerPlayer) this.getCurrentPlayer()).move(this);
        }
	}
	
	private void swapTurn(){

		turn = (turn == Player.FIRST_PLAYER) ? Player.SECOND_PLAYER : Player.FIRST_PLAYER;
	}
	
	private void checkState() {

		if (playground.hasWon(turn)) {

			this.gameOver = true;
		    this.setChanged();
		    this.notifyObservers((turn == Player.FIRST_PLAYER) ? playerX : playerO);

		} else if (!playground.hasEmptyFieldsLeft()) {

			this.gameOver = true;
			this.setChanged();
			this.notifyObservers("draw");
		}
	}
}
