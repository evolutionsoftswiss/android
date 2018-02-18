package android.evolutionsoft.ch.tictactoe.model;

import android.evolutionsoft.ch.tictactoe.treesearch.AlphaBetaSearch;

/**
 * @author EvolutionSoft
 */
public class ComputerPlayer extends Player implements Runnable {

	private AlphaBetaSearch alphaBetaSearch;
	private Game game;
	
	public ComputerPlayer(char color) {

		super(color);
	}
	
	void move(Game game) {

		this.game = game;

		Thread searchThread = new Thread(this);
		searchThread.start();

        try {

            searchThread.join();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }

        Move move = alphaBetaSearch.getBestMove();
		game.move(move);
	}

    @Override
    public void run() {

        alphaBetaSearch = new AlphaBetaSearch(game.getPlayground().getPosition());

        if (this.color == Player.FIRST_PLAYER) {

            alphaBetaSearch.searchMax();

        } else {

            alphaBetaSearch.searchMin();
        }
    }
}
