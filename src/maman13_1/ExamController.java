package maman13_1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;

public class ExamController {

    @FXML
    private Text questionText;

    @FXML
    private ToggleGroup answerGroup;

    @FXML
    private Button btn;

    private Exam exam;

    @FXML
    void buttonPressed(ActionEvent event) {
        if (exam == null) {
            // New exam
            startExam();
            return;
        }

        // Answer question
        RadioButton selectedAnswer = (RadioButton) answerGroup.getSelectedToggle();
        if (selectedAnswer == null) {
            return;
        }
        exam.answerQuestion(selectedAnswer.getText());

        //Display next question or end exam
        if (exam.isCompleted()) {
            endExam();
        } else {
            displayNextQuestion();
        }
    }

    private void startExam() {
        try {
            exam = new Exam("exam.txt");
            btn.setText("Next question");
            displayNextQuestion();
        } catch (FileNotFoundException e) {
            questionText.setText("Error: exam.txt not found");
            disableAnswerButtons();
        }
    }

    private void endExam() {
        questionText.setText("Your grade is: " + exam.getGrade());
        btn.setText("Start new exam");
        disableAnswerButtons();
        exam = null;
    }

    private void displayNextQuestion() {
        Question question = exam.getNextQuestion();
        questionText.setText(question.getQuestion());
        for (int i = 0; i < question.getAnswers().size(); i++) {
            RadioButton answerButton = (RadioButton) answerGroup.getToggles().get(i);
            answerButton.setText(question.getAnswers().get(i));
            answerButton.setDisable(false);
            answerButton.setSelected(false);
        }
    }

    private void disableAnswerButtons() {
        for (Toggle toggle : answerGroup.getToggles()) {
            RadioButton radioButton = (RadioButton) toggle;
            radioButton.setText("");
            radioButton.setSelected(false);
            radioButton.setDisable(true);
        }
    }

}
