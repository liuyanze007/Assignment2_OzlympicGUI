package Ozlympic;

import Dao.FileRW;
import Dao.SQLiteConnection;
import Exceptions.GameFullException;
import Exceptions.NoRefereeException;
import Exceptions.TooFewAthleteException;
import Exceptions.WrongTypeException;
import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    Label[] perLabels;
    Label[] playerLabels;
    Button[] addAthlete;
    Button[] addOfficials;

    //0,the game not start = the game is over 1, selected the game£¬2 the game start
    int state;
    int numAthletes;
    private List<Game> games;
    private List<Athlete> athletes;
    private List<Official> officials;

    private int personNum;
    private int personIndex;
    private int gameType;
    private int gameId;
    private Athlete[] players;
    private Official curOfficial;
    private int SqliteOrFile;//1 SQLite. 2 file


    public void init() {
        initArgs();
        readData();
        games = new ArrayList<Game>();
        personNum = athletes.size()+officials.size();
        personIndex = 0;
        showPerson();
    }

    private void readData() {
        if(SQLiteConnection.haveDB()){
            SqliteOrFile = 1;
            SQLiteConnection.readFromDB();
            athletes = SQLiteConnection.getAthletes();
            officials = SQLiteConnection.getOfficials();
            System.out.println("Read from SQLite successfully");
        }else{
            SqliteOrFile = 2;
            infoDialog("Did not find the database, read the data from the file");
            FileRW.readFile();
            athletes = FileRW.getAthletes();
            officials = FileRW.getOfficials();
            System.out.println("Read from participants.txt successfully");
        }
    }

    private void initArgs() {
        perLabels = new Label[5];
        perLabels[0] = per1 ;
        perLabels[1] = per2 ;
        perLabels[2] = per3 ;
        perLabels[3] = per4 ;
        perLabels[4] = per5 ;
        addAthlete = new Button[5];
        addAthlete[0] = add11;
        addAthlete[1] = add12;
        addAthlete[2] = add13;
        addAthlete[3] = add14;
        addAthlete[4] = add15;
        addOfficials = new Button[5];
        addOfficials[0] = add21;
        addOfficials[1] = add22;
        addOfficials[2] = add23;
        addOfficials[3] = add24;
        addOfficials[4] = add25;
        playerLabels = new Label[8];
        playerLabels[0] = No1;
        playerLabels[1] = No2;
        playerLabels[2] = No3;
        playerLabels[3] = No4;
        playerLabels[4] = No5;
        playerLabels[5] = No6;
        playerLabels[6] = No7;
        playerLabels[7] = No8;
        numAthletes = 0;
        gameType = 0;
        gameId = 1;
        players = new Athlete[8];
        curOfficial = null;
    }

    private void showPerson() {
        int j = personNum-personIndex<5?personNum-personIndex:5;
        for(int i=0;i<j;i++){
            if( (i+personIndex) < athletes.size()) {
                perLabels[i].setText((i+1) + ":" + athletes.get((i+personIndex)).getInfo());
            }else{
                perLabels[i].setText((i+1) + ":" + officials.get((i+personIndex)-athletes.size()).getInfo());
            }
        }
    }

    public void addAthletes(int index){
        if(state!=1){
            infoDialog("Please select the game first, then add the player");
            return;
        }
        try{
            addNewAthlete(index);
        }catch(GameFullException e){
            errorDialog("Up to 8 people will be involved ");
        } catch (WrongTypeException e) {
            errorDialog("Selected wrong person!");
        }
    }

    private void addNewAthlete(int index) throws GameFullException, WrongTypeException {
        if(numAthletes>=8){
            throw new GameFullException();
        }
        if((personIndex+index)>= personNum){
            return;
        }
        if((personIndex+index)>=athletes.size()){
            throw new WrongTypeException();
        }
        Athlete a = athletes.get(personIndex+index);
        for (int i = 0; i < numAthletes; i++) {
            if(players[i] == a){
                return;
            }
        }
        if( (gameType==1 && !(a instanceof Run)) ||
            (gameType==2 && !(a instanceof Swim)) ||
            (gameType==3 && !(a instanceof Cycle))){
            throw new WrongTypeException();
        }
        players[numAthletes] = a;
        playerLabels[numAthletes].setText(players[numAthletes].getID()+" "+players[numAthletes].getName());
        numAthletes ++;
    }

    public void addOfficials(int index){
        if(state!=1){
            infoDialog("Please select the game first, then add the officals");
            return;
        }
        try{
            addNewOfficial(index);
        } catch (WrongTypeException e) {
            errorDialog("The player is not an officals!");
        }

    }

    private void addNewOfficial(int index) throws WrongTypeException{
        if(curOfficial!=null){
            return;
        }
        if((personIndex+index)>= personNum){
            return;
        }
        if((personIndex+index)<athletes.size()){
            throw new WrongTypeException();
        }
        curOfficial = officials.get(personIndex+index-athletes.size());
        official.setText(curOfficial.getId()+" "+curOfficial.getName());
    }


    @FXML
    public void prevAction(ActionEvent actionEvent) {
        if(personIndex==0){
            return;
        }
        personIndex -= 5;
        if(personIndex<0){
            personIndex=0;
        }
        showPerson();
    }
    @FXML
    public void nextAction(ActionEvent actionEvent) {
        if(personNum<=5){
            return;
        }
        if(personIndex== personNum-5){
            return;
        }
        personIndex += 5;
        if(personIndex>personNum-5){
            personIndex=personNum-5;
        }
        showPerson();
    }
    @FXML
    public void startAction(ActionEvent actionEvent) {
        if(state==2){
            return;
        }
        if(state==0){
            infoDialog("Please select the game");
            return;
        }
        if(state==1){
            try {
                validGame();
            } catch (NoRefereeException e) {
                errorDialog("Please select the official");
                return;
            } catch (TooFewAthleteException e) {
                errorDialog("Not enough player");
                return;
            }
            state=2;
            playOneGame();
            state=0;
        }
    }

    private void validGame() throws NoRefereeException, TooFewAthleteException {
        if(official.getText().equals("")){
            throw new NoRefereeException();
        }
        if(numAthletes<4){
            throw new TooFewAthleteException();
        }
    }

    private void playOneGame() {
        Game game;
        String ID = (gameId<10?"0":"") + gameId;
        if(gameType==1){
            game = new Running("R"+ID);
        }else if(gameType==2){
            game = new Swimming("S"+ID);
        }else{
            game = new Cycling("C"+ID);
        }
        gameId++;
        game.setOffical(curOfficial);
        List<Athlete> tmp = new ArrayList<>();
        for (int i = 0; i < numAthletes; i++) {
            tmp.add(players[i]);
        }
        game.setAthletes(tmp);
        game.run();
        for (int i = 0; i < numAthletes; i++) {
            playerLabels[i].setText(game.getAthletes().get(i).toString());
        }
        String result = game.toString();
        if(SqliteOrFile==1){
            SQLiteConnection.writeToDB(game);
        }else {
            FileRW.writeFile(result);
        }
        history.appendText(result);
        games.add(game);
    }




    @FXML
    public void runAction(ActionEvent actionEvent) {
        chooseGame(1);
    }
    @FXML
    public void swimAction(ActionEvent actionEvent) {
        chooseGame(2);
    }
    @FXML
    public void cycleAction(ActionEvent actionEvent) {
        chooseGame(3);
    }

    private void chooseGame(int type){
        if(state!=0)
            return;
        gameType = type;
        String name;
        if(type==1){
            name = "running";
        }else if(type==2){
            name = "swimming";
        }else{
            name = "cycling";
        }
        for (int i = 0; i < 8; i++) {
            players[i] = null;
            playerLabels[i].setText("");
        }
        numAthletes = 0;
        curOfficial = null;
        official.setText("");
        infoDialog("Select the game:"+name+", Please select the official and players(8 players maximum), then click the start button to start the game");
        state = 1;
    }

    @FXML
    public void add11Action(ActionEvent actionEvent) {
        addAthletes(0);
    }
    @FXML
    public void add12Action(ActionEvent actionEvent) {
        addAthletes(1);
    }
    @FXML
    public void add13Action(ActionEvent actionEvent) {
        addAthletes(2);
    }
    @FXML
    public void add14Action(ActionEvent actionEvent) {
        addAthletes(3);
    }
    @FXML
    public void add15Action(ActionEvent actionEvent) {
        addAthletes(4);
    }
    @FXML
    public void add21Action(ActionEvent actionEvent) {
        addOfficials(0);
    }
    @FXML
    public void add22Action(ActionEvent actionEvent) {
        addOfficials(1);
    }
    @FXML
    public void add23Action(ActionEvent actionEvent) {
        addOfficials(2);
    }
    @FXML
    public void add24Action(ActionEvent actionEvent) {
        addOfficials(3);
    }
    @FXML
    public void add25Action(ActionEvent actionEvent) {
        addOfficials(4);
    }

    private void infoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notice");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void errorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    @FXML
    Button start;
    @FXML
    Button next;
    @FXML
    Button prev;
    @FXML
    Label per1;
    @FXML
    Label per2;
    @FXML
    Label per3;
    @FXML
    Label per4;
    @FXML
    Label per5;
    @FXML
    Label No1;
    @FXML
    Label No2;
    @FXML
    Label No3;
    @FXML
    Label No4;
    @FXML
    Label No5;
    @FXML
    Label No6;
    @FXML
    Label No7;
    @FXML
    Label No8;
    @FXML
    Label official;
    @FXML
    Button add11;
    @FXML
    Button add12;
    @FXML
    Button add13;
    @FXML
    Button add14;
    @FXML
    Button add15;
    @FXML
    Button add21;
    @FXML
    Button add22;
    @FXML
    Button add23;
    @FXML
    Button add24;
    @FXML
    Button add25;
    @FXML
    TextArea history;


}
