package sample.controllers;

import com.sun.deploy.util.StringUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import sample.ScreenManager;
import sample.models.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static sample.Main.socketHandler;

public class CreateKahootController {

    @FXML public ListView questionList;
    @FXML private TextArea question;
    @FXML private TextField answerA;
    @FXML private TextField answerB;
    @FXML private TextField answerC;
    @FXML private TextField answerD;
    @FXML private TextField timeForQuestion;
    @FXML private ToggleGroup answer;
    @FXML private Button saveQuestion;
    @FXML private Button saveKahoot;
    @FXML private Button loadKahoot;

    private ArrayList<Question> questions = new ArrayList<>();
    int selected = -1;

    private ScreenManager screenManager = new ScreenManager();

    @FXML
    public void AddNewQuestion(ActionEvent actionEvent) throws IOException {
        RadioButton selectedToggle = (RadioButton) answer.getSelectedToggle();
        String correctAnswer = selectedToggle.getText();
        Question q = new Question(question.getText(),answerA.getText(),answerB.getText(),answerC.getText(),answerD.getText(),correctAnswer,Integer.parseInt(timeForQuestion.getText()));
        if (selected == -1)
            questions.add(q);
        else
            questions.set(selected,q);
        questionList.setItems(FXCollections.observableArrayList(questions));
        clearInputs();
        saveQuestion.setText("Add Question");
    }

    @FXML
    public void selectQuestion(MouseEvent mouseEvent) {
        Question selectedQuestion = (Question) questionList.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            selected = questionList.getItems().indexOf(selectedQuestion);
            question.setText(selectedQuestion.getQuestion());
            answerA.setText(selectedQuestion.getAnswerA());
            answerB.setText(selectedQuestion.getAnswerB());
            answerC.setText(selectedQuestion.getAnswerC());
            answerD.setText(selectedQuestion.getAnswerD());
            answer.getToggles().stream().filter(toggle -> toggle.toString().split("'")[1].equals(selectedQuestion.getCorrectAnswer())).forEach(toggle -> toggle.setSelected(true));
            timeForQuestion.setText(Integer.toString(selectedQuestion.getTimeForAnswer()));
            saveQuestion.setText("Modify Question");
        }
    }

    @FXML
    public void clear(ActionEvent actionEvent) {
        clearInputs();
    }

    private void clearInputs() {
        selected = -1;
        question.clear();
        answerA.clear();
        answerB.clear();
        answerC.clear();
        answerD.clear();
        answer.selectToggle(null);
        timeForQuestion.clear();
        saveQuestion.setText("Add Question");
    }

    @FXML public void sendKahoot(ActionEvent actionEvent) {
        List<String> parsedQuestions = questions.stream().map(question -> question.getQuestion() + "#" + question.getAnswerA() + "#"+ question.getAnswerB() + "#"+ question.getAnswerC() + "#"+ question.getAnswerD() + "#"+ question.getCorrectAnswer() + "#"+ question.getTimeForAnswer()).collect(Collectors.toList());
        socketHandler.sendMessage("SEND_KAHOOT", StringUtils.join(parsedQuestions, "|"));
        screenManager.setScreen("hostLobby", actionEvent);
    }

    @FXML
    public void saveKahoot(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose location to save kahoot");
        File selectedFile = null;
        while (selectedFile == null) {
            selectedFile = chooser.showSaveDialog(null);
        }
        File file = new File(String.valueOf(selectedFile));
        PrintWriter outFile = null;
        try {
            outFile = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (Question question : questions) {
            outFile.println(question.getQuestion() + "|" + question.getAnswerA()+ "|" +question.getAnswerB()+ "|" + question.getAnswerC()+ "|" + question.getAnswerD() + "|" +question.getCorrectAnswer()+ "|" + question.getTimeForAnswer());
        }
        outFile.close();
    }

    @FXML
    public void loadKahoot(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose kahhot to be loaded");
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line;
                this.questions.clear();
                while ((line = bufferedReader.readLine()) != null) {
                    String [] data = line.split("\\|");
                    this.questions.add(new Question(data[0],data[1],data[2],data[3],data[4],data[5],Integer.parseInt(data[6])));
                }
                questionList.setItems(FXCollections.observableArrayList(questions));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
