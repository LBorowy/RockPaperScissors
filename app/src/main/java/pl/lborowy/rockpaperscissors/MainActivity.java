package pl.lborowy.rockpaperscissors;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private Drawable rockDrawable;
    private Drawable paperDrawable;
    private Drawable scissorsDrawable;

    @BindView(R.id.myChoice)
    ImageView myActionImageView;

    @BindView(R.id.computerChoice)
    ImageView computerActionImageView;

    @BindView(R.id.scorePlayer)
    TextView scorePlayer;

    @BindView(R.id.scoreComputer)
    TextView scoreComputer;

    private ActionEnum myActionEnum;
    private ActionEnum computerActionEnum;
    private Random random;

    private int myScore;
    private int computerScore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        random = new Random();

        rockDrawable = ContextCompat.getDrawable(this, R.drawable.kamien);
        paperDrawable = ContextCompat.getDrawable(this, R.drawable.papier);
        scissorsDrawable = ContextCompat.getDrawable(this, R.drawable.nozyczki);
    }

    @OnClick(R.id.chooseRock)
    public void useRock() {
        myActionImageView.setImageDrawable(rockDrawable);
        myActionEnum = ActionEnum.ROCK;
        startGame();

    }

    @OnClick(R.id.choosePaper)
    public void usePaper() {
        myActionImageView.setImageDrawable(paperDrawable);
        myActionEnum = ActionEnum.PAPER;
        startGame();
    }

    @OnClick(R.id.chooseScissors)
    public void useScissors() {
        myActionImageView.setImageDrawable(scissorsDrawable);
        myActionEnum = ActionEnum.SCISSORS;
        startGame();
    }

    private void startGame() {
        computerActionEnum = getRandomAction();
        setComputerImage();
        GameResult gameResult = getGameResult();
        addScore(gameResult);
        checkScore();
    }

    private void addScore(GameResult gameResult) {
        if (gameResult.equals(GameResult.WIN)) {
            myScore++;
        }
        if (gameResult.equals(GameResult.LOSE)) {
            computerScore++;
        }
        refreshPointsText();
    }

    private GameResult getGameResult() {
        if (myActionEnum.equals(computerActionEnum)) {
            return GameResult.TIE;
        }
        if (isMyWin()) {
            return GameResult.WIN;
        }
        else {
            return GameResult.LOSE;
        }
    }

    private boolean isMyWin() {
        return (myActionEnum.equals(ActionEnum.SCISSORS) && computerActionEnum.equals(ActionEnum.PAPER)) ||
                (myActionEnum.equals(ActionEnum.PAPER) && computerActionEnum.equals(ActionEnum.ROCK)) ||
                (myActionEnum.equals(ActionEnum.ROCK) && computerActionEnum.equals(ActionEnum.SCISSORS));
    }

    private void setComputerImage() {
        Drawable computerDrawable = getDrawableForAction(computerActionEnum);
        computerActionImageView.setImageDrawable(computerDrawable);
    }

    private Drawable getDrawableForAction(ActionEnum computerActionEnum) {
        switch (computerActionEnum) {
            default:
            case ROCK:
                return rockDrawable;
            case PAPER:
                return paperDrawable;
            case SCISSORS:
                return scissorsDrawable;
        }
    }

    private ActionEnum getRandomAction() {
        int randomAction = random.nextInt(3);
        if (randomAction == 0) {
            return ActionEnum.ROCK;
        }
        if (randomAction == 1) {
            return ActionEnum.PAPER;
        }
        else {
            return ActionEnum.SCISSORS;
        }

    }

    private void checkScore() {
        if (myScore == 3) {
            showAlertDialog("You win! ;)");
        }
        else if (computerScore == 3) {
            showAlertDialog("You loose!");
        }
    }

    private void refreshPointsText() {
        scorePlayer.setText(String.format(Integer.toString(myScore)));
        scoreComputer.setText(String.format(Integer.toString(computerScore)));
    }

    private void showAlertDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("End of game")
                .setMessage(message)
                .setCancelable(false) // jesli klikniemy poza okno, to sie nie zamknie
                .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myScore = 0;
                        computerScore = 0;
                        refreshPointsText();
                    }
                })
                .setNeutralButton("Neutral", null)
                .setNegativeButton("Exit game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create();

        alertDialog.show();

    }

    enum ActionEnum {
        ROCK,
        PAPER,
        SCISSORS
    }

    enum GameResult {
        WIN,
        LOSE,
        TIE
    }
}
