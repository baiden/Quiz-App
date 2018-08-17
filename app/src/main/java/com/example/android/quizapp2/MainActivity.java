package com.example.android.quizapp2;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    int totalQuizScore; //Total Quiz Score
    int eachQuizScore = 20; // Score for each question
    int bonusQuizScore = 20; // Score for bonus question

    int questionOneResults = 0;
    int questionTwoResults = 0;
    int questionThreeResults = 0;
    int questionFourResults = 0;
    int questionFiveResults = 0;

    //Question 1
    private RadioGroup questionOneRadioGroup;
    private boolean checked1;

    //Question 2
    @BindView(R.id.bankuCheckBox) CheckBox withBanku;
    @BindView(R.id.luwonboCheckBox) CheckBox withLuwonbo;
    @BindView(R.id.irioCheckBox) CheckBox withIrio;
    @BindView(R.id.ebaCheckBox) CheckBox withEba;
    private boolean isBanku;
    private boolean isLuwonbo;
    private boolean isIrio;
    private boolean isEba;

    //Question 3
    @BindView(R.id.nigeriaQ3CheckBox) CheckBox withNigeriaQ3;
    @BindView(R.id.ghanaQ3CheckBox) CheckBox withGhanaQ3;
    @BindView(R.id.tunisiaCheckBox) CheckBox withTunisia;
    @BindView(R.id.wakandaCheckBox) CheckBox withWakanda;
    private boolean isNigeriaQ3;
    private boolean isGhanaQ3;
    private boolean isTunisia;
    private boolean isWakanda;

    //Question 4
    @BindView(R.id.question_4EditTextID) EditText questionFourEditText;

    //Bonus Question
    @BindView(R.id.nigeriaCheckBox) CheckBox withNigeria;
    @BindView(R.id.ghanaCheckBox) CheckBox withGhana;
    private boolean isNigeria;
    private boolean isGhana;

    //User's name
    @BindView(R.id.name_textInputEditText) TextInputEditText name_textInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Sets the screen to fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ButterKnife.bind(this);
    }

    //Submits the user's name and scores
    public void submitResults(View view) {

        //Question 2's Checkboxes
        isBanku = withBanku.isChecked();
        isLuwonbo = withLuwonbo.isChecked();
        isIrio = withIrio.isChecked();
        isEba = withEba.isChecked();

        //Question 3's Checkboxes
        isNigeriaQ3 = withNigeriaQ3.isChecked();
        isGhanaQ3 = withGhanaQ3.isChecked();
        isTunisia = withTunisia.isChecked();
        isWakanda = withWakanda.isChecked();

        //Question 4's EditText
        String questionFourInput = questionFourEditText.getText().toString();

        //Question 5's Checkboxes
        withNigeria = (CheckBox)findViewById(R.id.nigeriaCheckBox);
        withGhana = (CheckBox)findViewById(R.id.ghanaCheckBox);
        isNigeria = withNigeria.isChecked();
        isGhana = withGhana.isChecked();

        /* ***** Validates Question 1 ****/
        if (checked1){
            /* *********** Validates Question 2 ******************************/
            if (!isBanku && !isLuwonbo && !isIrio && !isEba)
            {
                Toast.makeText(this, "Question 2 can't be left unanswered!", Toast.LENGTH_SHORT).show();
                resetScoreValues();
            }
            else {

                /* *********** Validates Question 3 ******************************/
                if (!isNigeriaQ3 && !isGhanaQ3 && !isTunisia && !isWakanda)
                {
                    Toast.makeText(this, "Question 3 can't be left unanswered!", Toast.LENGTH_SHORT).show();
                    resetScoreValues();
                }
                else {

                    /* *********** Validates Question 4 ******************************/
                    if (questionFourInput.length()==0){
                        questionFourEditText.requestFocus();
                        questionFourEditText.setError("FIELD CANNOT BE EMPTY!");
                        resetScoreValues();
                    }
                    else if(!questionFourInput.matches("[a-zA-Z ]+")){
                        questionFourEditText.requestFocus();
                        questionFourEditText.setError("ENTER ONLY ALPHABETICAL CHARACTERS!");
                        resetScoreValues();
                    }
                    else {

                        /* *********** Validates Question 5 ******************************/
                        if (!isNigeria && !isGhana)
                        {
                            Toast.makeText(this, "Bonus Question can't be left unanswered!", Toast.LENGTH_SHORT).show();
                            resetScoreValues();
                        }
                        else {

                            String name = name_textInputEditText.getText().toString();

                            /* ************ Validates User's name *****************/
                            if (name.length()==0){
                                name_textInputEditText.requestFocus();
                                name_textInputEditText.setError("FIELD CANNOT BE EMPTY!");
                                resetScoreValues();
                            }
                            else if(!name.matches("[a-zA-Z ]+")){
                                name_textInputEditText.requestFocus();
                                name_textInputEditText.setError("ENTER ONLY ALPHABETICAL CHARACTERS!");
                                resetScoreValues();
                            }
                            else {

                                totalQuizScore = computeTotalQuizScore(isBanku, isLuwonbo, isIrio, isEba, isNigeriaQ3, isGhanaQ3, isTunisia, isWakanda,
                                        questionFourInput, isNigeria, isGhana);
                                questionTwoResults = getQuestionTwoScore(isBanku, isLuwonbo, isIrio, isEba);
                                questionThreeResults = getQuestionThreeScore(isNigeriaQ3, isGhanaQ3, isTunisia, isWakanda);
                                questionFourResults = getQuestionFourScore(questionFourInput);
                                questionFiveResults = getQuestionFiveScore(isNigeria, isGhana);

                            if (totalQuizScore >= 60 ){

                                String scoreMessage = createScoreSummary(name, totalQuizScore);
                                if (totalQuizScore >= 80){
                                    Toast.makeText(this, scoreMessage + " Bravo! :)", Toast.LENGTH_LONG).show();
                                    disableSubmitButton();
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=rW6M8D41ZWU")));
                                }
                                else {
                                    Toast.makeText(this, scoreMessage + " Not bad, better luck next time ;)", Toast.LENGTH_LONG).show();
                                    disableSubmitButton();
                                }
                            }
                            if (totalQuizScore <= 59){
                                String scoreMessage = createScoreSummary(name, totalQuizScore);
                                Toast.makeText(this, scoreMessage + " Try again :(", Toast.LENGTH_LONG).show();
                                disableSubmitButton();
                            }

                            }
                        }

                    }
                }

            }
        }
        else {
            Toast.makeText(this, "Question 1 can't be left unanswered!", Toast.LENGTH_SHORT).show();
            resetScoreValues();
        }

    }


    /**
     * Computes the Total Quiz Score
     *
     *
     * @return text summary of total Quiz score
     */
    private int computeTotalQuizScore(boolean addBanku, boolean addLuwonbo, boolean addIrio, boolean addEba, boolean addNigeriaQ3,
                                      boolean addGhanaQ3, boolean addTunisia, boolean addWakanda, String questionFourInput, boolean addNigeria, boolean addGhana) {

            totalQuizScore += questionOneResults;
            totalQuizScore += getQuestionTwoScore(addBanku, addLuwonbo, addIrio, addEba);
            totalQuizScore += getQuestionThreeScore(addNigeriaQ3, addGhanaQ3, addTunisia, addWakanda);
            totalQuizScore += getQuestionFourScore(questionFourInput);
            totalQuizScore += getQuestionFiveScore(addNigeria, addGhana);
        return totalQuizScore;
    }



    /**
     * Creates a summary of your score
     *
     * @param name of the user
     * @param addTotalQuizScore contains the total Quiz Score
     * @return text summary of quiz scores
     */
    public String createScoreSummary(String name, int addTotalQuizScore) {
        String scoreMessage = "Hi " + name + "!";
        scoreMessage += "\nYou scored: " + addTotalQuizScore + "%";
        return scoreMessage;
    }


    /* ****************** Question 1 results ****************************/
    public void onCheckedQuestion1(View view) {
        checked1 = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.yellowRiceOneRadioButton:
                if (checked1) {
                    questionOneResults = 0; //Wrong answer, hence a penalty of 10 marks is deducted
                    break;
                }
            case R.id.jollofRiceOneRadioButton:
                if (checked1) {
                    questionOneResults = eachQuizScore; //Correct answer
                    break;
                }
            case R.id.wheatRiceOneRadioButton:
                if (checked1) {
                    questionOneResults = 0; //Wrong answer, hence a penalty of 10 marks is deducted
                    break;
                }

            case R.id.gumboOneRadioButton:
                if (checked1){
                    questionOneResults = 0; //Wrong answer, hence a penalty of 10 marks is deducted
                    break;
                }
        }
    }
    /* ****************************** End of Question 1 ***********************************/


    /* ****************************** Beginning of Question 2 ***********************************/
    /**
     * Gets Question Two's Score
     *
     * @param addBanku    adds 10 marks to Question 2's score
     * @param addLuwonbo adds 10 marks to Question 2's score
     * @param addIrio  sets Question 2's score to 0
     * @param addEba      adds 10 marks to Question 2's score
     * @return text summary of Question 2's score
     */
    public int getQuestionTwoScore(boolean addBanku, boolean addLuwonbo, boolean addIrio, boolean addEba) {
        int eachScore = (eachQuizScore / 2);
        if (addBanku) {
            questionTwoResults += eachScore; //Correct answer
        }
        if (addLuwonbo) {
            questionTwoResults += -eachScore; //Wrong answer, hence a penalty of 10 marks is deducted
        }
        if (addIrio) {
            questionTwoResults += -eachScore; //Wrong answer, hence a penalty of 10 marks is deducted
        }
        if (addEba) {
            questionTwoResults += eachScore; //Correct answer
        }
        if (questionTwoResults < 0){
            questionTwoResults = 0;
        }

        return questionTwoResults;
    }

   /* ****************************** End of Question 2 ***********************************/




    /* ****************************** Beginning of Question 3 ***********************************/
    /**
     * Gets Question Three's Score
     *
     * @param addNigeriaQ3    adds 10 marks to Question 3's score
     * @param addGhanaQ3  adds 10 marks to Question 3's score
     * @param addTunisia deducts 10 marks from Question 3's score
     * @param addWakanda  deducts 10 marks from Question 3's score
     * @return text summary of Question 3's score
     */
    public int getQuestionThreeScore(boolean addNigeriaQ3, boolean addGhanaQ3, boolean addTunisia, boolean addWakanda) {
        int forEachScore = (eachQuizScore / 2);
        if (addNigeriaQ3) {
            questionThreeResults += forEachScore; //Correct answer
        }
        if (addGhanaQ3) {
            questionThreeResults += forEachScore; //Correct answer
        }
        if (addTunisia) {
            questionThreeResults += -forEachScore; //Wrong answer, hence a penalty of 10 marks is deducted
        }
        if (addWakanda) {
            questionThreeResults += -forEachScore; //Wrong answer, hence a penalty of 10 marks is deducted
        }

        if (questionThreeResults < 0){
            questionThreeResults = 0;
        }
        return questionThreeResults;
    }
    /* ****************************** End of Question 3 ***********************************/




    /* ****************************** Beginning of Question 4 ***********************************/
    /**
     * Gets Question Four's Score
     *
     * @param questionFourInput adds 20 marks to Question 4's score
     * @return text summary of Question 4's score
     */
    public int getQuestionFourScore(String questionFourInput) {
        if (questionFourInput.equalsIgnoreCase("South Africa")) {
            questionFourResults += eachQuizScore; //Correct answer
        }
        else {
            questionFourResults = 0; //Wrong answer
        }
        return questionFourResults;
    }
    /* ****************************** End of Question 4 ***********************************/



    /* ****************************** Beginning of Bonus Question ***********************************/
    /**
     * Gets Bonus Question's Score
     *
     * @param addNigeria adds 20 marks to Bonus Question's score if selected
     * @param addGhana adds 20 marks to Bonus Question's score if selected
     * @return text summary of Bonus Question's score
     */
    public int getQuestionFiveScore(boolean addNigeria, boolean addGhana){
        /* NB: Either option or both of them is correct because everyone is right in his/her own eyes :D
         Gave the liberty of selecting both options as some people may want that */

        if (addNigeria){
            questionFiveResults = bonusQuizScore;
        }
        if (addGhana){
            questionFiveResults = bonusQuizScore;
        }

        if (addNigeria && addGhana){
            questionFiveResults = bonusQuizScore;
        }
        return questionFiveResults;
    }
    /* ****************************** End of Bonus Question ***********************************/


    /* ******* Resets the values whenever a question is skipped so that it doesn't affect the final score ****************/
    public void resetScoreValues(){
        questionTwoResults = 0;
        questionThreeResults = 0;
        questionFourResults = 0;
        questionFiveResults = 0;
    }

    public void tryAgain(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void disableSubmitButton(){
        Button submitBtn = (Button)findViewById(R.id.submitResults);
        submitBtn.setEnabled(false);
        submitBtn.setBackgroundResource(R.color.colorDanger);
        submitBtn.setText(R.string.resultsSubmitted);
    }

}