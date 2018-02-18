package android.evolutionsoft.ch.tictactoe.activity;

import android.evolutionsoft.ch.tictactoe.R;
import android.evolutionsoft.ch.tictactoe.model.ComputerPlayer;
import android.evolutionsoft.ch.tictactoe.model.Game;
import android.evolutionsoft.ch.tictactoe.model.HumanPlayer;
import android.evolutionsoft.ch.tictactoe.model.Move;
import android.evolutionsoft.ch.tictactoe.model.Player;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class GameActivity extends AppCompatActivity implements Observer {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        game = new Game(new ComputerPlayer(Player.SECOND_PLAYER),
                new HumanPlayer(Player.FIRST_PLAYER));

        FloatingActionButton restartButton = findViewById(R.id.floatingActionButton2);
        restartButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                reset();
            }
        });

        ToggleButton switchPlayerButton = findViewById(R.id.toggleButton);
        switchPlayerButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                game.switchPlayers();
            }
        });

        game.addObserver(this);

        game.newGame();
    }

    public void fieldClick(View view) {

        Player currentPlayer = game.getCurrentPlayer();
        if (currentPlayer instanceof HumanPlayer) {

            TextView fieldView = findViewById(view.getId());
            char fieldContent = fieldView.getText().charAt(0);

            if (fieldContent == ' ' && !game.gameOver()) {

                Move newMove;
                char currentPlayerColor = currentPlayer.getColor();

                switch (fieldView.getId()) {

                    case R.id.textView0:
                        newMove = new Move(0, 0, currentPlayerColor);
                        break;
                    case R.id.textView1:
                        newMove = new Move(0, 1, currentPlayerColor);
                        break;
                    case R.id.textView2:
                        newMove = new Move(0, 2, currentPlayerColor);
                        break;
                    case R.id.textView3:
                        newMove = new Move(1, 0, currentPlayerColor);
                        break;
                    case R.id.textView4:
                        newMove = new Move(1, 1, currentPlayerColor);
                        break;
                    case R.id.textView5:
                        newMove = new Move(1, 2, currentPlayerColor);
                        break;
                    case R.id.textView6:
                        newMove = new Move(2, 0, currentPlayerColor);
                        break;
                    case R.id.textView7:
                        newMove = new Move(2, 1, currentPlayerColor);
                        break;
                    case R.id.textView8:
                        newMove = new Move(2, 2, currentPlayerColor);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid Field");
                }

                game.move(newMove);
            }
        }
    }

    @Override
    public void update(Observable observable, Object argument) {

        if (argument instanceof Move) {

            disableToggleButton();

            Move move = (Move) argument;
            TextView fieldView = this.getFieldView(move.getRow(), move.getColumn());
            char moveColor = move.getColor();
            fieldView.setText(String.valueOf(moveColor));
            if (moveColor == Player.FIRST_PLAYER) {

                fieldView.setTextColor(Color.BLUE);

            } else {

                fieldView.setTextColor(Color.RED);
            }

        } else if (argument instanceof String &&
                argument.equals("draw")) {

            updateStatusText("Draw");
            enableToggleButton();

        } else if (argument instanceof Player) {

            String newText = " CPU wins";

            if (argument instanceof HumanPlayer) {
                newText = "You win";
            }

            updateStatusText(newText);
            enableToggleButton();
        }
    }

    private void reset() {

        game.newGame();
        this.resetView();
        enableToggleButton();
        game.start();
    }

    private void resetView() {

    for (int n = 0; n < 3; n++) {

            for (int m = 0; m < 3; m++) {

                this.getFieldView(n, m).setText(" ");
            }
        }

        updateStatusText("");
    }

    protected void enableToggleButton() {

        ToggleButton switchPlayerButton = findViewById(R.id.toggleButton);

        switchPlayerButton.setEnabled(true);
    }

    protected void disableToggleButton() {

        ToggleButton switchPlayerButton = findViewById(R.id.toggleButton);

        switchPlayerButton.setEnabled(false);
    }

    protected void updateStatusText(String text) {

        TextView statusTextView = findViewById(R.id.textView9);

        statusTextView.setText(text);
    }

    protected TextView getFieldView(int row, int column) {

        if (row == 0) {

            return getFirstRowFieldView(column);
        }
        if (row == 1) {

            return getMiddleRowFieldView(column);
        }

        return getLastRowFieldView(column);
    }

    protected TextView getFirstRowFieldView(int column) {

        if (column == 0) {

            return findViewById(R.id.textView0);
        }
        if (column == 1) {

            return findViewById(R.id.textView1);
        }

        return findViewById(R.id.textView2);
    }

    protected TextView getMiddleRowFieldView(int column) {

        if (column == 0) {

            return findViewById(R.id.textView3);
        }
        if (column == 1) {

            return findViewById(R.id.textView4);
        }

        return findViewById(R.id.textView5);
    }

    protected TextView getLastRowFieldView(int column) {

        if (column == 0) {

            return findViewById(R.id.textView6);
        }
        if (column == 1) {

            return findViewById(R.id.textView7);
        }

        return findViewById(R.id.textView8);
    }
}
