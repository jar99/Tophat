package application.MBO;

import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MBOCtrl implements Initializable {

	// Links to your Singleton (NO TOUCHY!!)
	private MBOSingleton mySin = MBOSingleton.getInstance();

	private AnimationTimer updateAnimation;

	// NOTE: This is where you link to elements in your FXML file
	// Example:(fx:id="counter")
	// WARNING: Your fx:id and variable name Must Match!
	// Links to FXML elements
	//@FXML
	//private Label counter;
    @FXML
    private Tab schedulerTAB;

    @FXML
    private TableView<Operator> operatorsTV;

    @FXML
    private ScrollBar operatorsSB;

    @FXML
    private TextField operatorNameTF;

    @FXML
    private TextField operatorStartTimeTF;

    @FXML
    private TextField operatorEndTimeTF;

    @FXML
    private Button addOperatorBtn;

    @FXML
    private TableView<Train> trainsTV;

    @FXML
    private ScrollBar trainsSB;

    @FXML
    private TextField trainIDTF;

    @FXML
    private TextField trainStartTimeTF;

    @FXML
    private TextField trainEndTimeTF;

    @FXML
    private Button addTrainBtn;
    
    @FXML
    private Button deleteBtn;

    @FXML
    private Button createScheduleBtn;

    @FXML
    private ChoiceBox<String> lineCB;

    @FXML
    private TextField throughputTF;

    @FXML
    private Button setThroughputBtn;

    @FXML
    private MenuBar scheduleMB;

    @FXML
    private Menu fileMenu;

    @FXML
    private MenuItem loadScheduleMI;

    @FXML
    private MenuItem saveScheduleMI;

    @FXML
    private MenuItem exitMI;

    @FXML
    private Menu helpMenu;

    @FXML
    private TableView<?> scheduleTV;

    @FXML
    private ScrollBar schedulerSB;

    @FXML
    private Button editScheduleBtn;

    @FXML
    private Button sendScheduleBtn;

    @FXML
    private Tab controllerTAB;

    @FXML
    private ChoiceBox<?> trainCB;

    @FXML
    private Label locationLbl;

    @FXML
    private Label currentBlockLbl;

    @FXML
    private Label calculatedSpeedLbl;

    @FXML
    private Label calculatedSBDLbl;

    @FXML
    private Label suggestedSpeedLbl;

    @FXML
    private Label suggestedAuthorityLbl;

    private int greenThroughput = 0;
    private int redThroughput = 0;
    
	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
    
    
	// Control Functions
	public void buttonClicked() {
		// System.out.print("Click");
		mySin.increment();
	}

	// Starts the automatic update (NO TOUCHY!!)
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//Throughput 
		lineCB.getItems().addAll("Red", "Green");
		lineCB.setValue("Green");
		setThroughputBtn.setOnAction(e -> setThroughput());
		lineCB.setOnAction(e -> getThroughput());
		
		//*************Train Table*********************
		//id column
		TableColumn<Train, String> idColumn = new TableColumn<>("ID");
		idColumn.setMinWidth(75);
		idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
		
		//start time column
        TableColumn<Train, String> departTimeColumn = new TableColumn<>("Depart Time");
        departTimeColumn.setMinWidth(75);
        departTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departTime"));

        //leave time column
        TableColumn<Train, String> returnTimeColumn = new TableColumn<>("Return Time");
        returnTimeColumn.setMinWidth(75);
        returnTimeColumn.setCellValueFactory(new PropertyValueFactory<>("returnTime"));
        
		trainsTV.getColumns().addAll(idColumn, departTimeColumn, returnTimeColumn);
        
        addTrainBtn.setOnAction(e -> addTrain());
        
        //Operator Table 
		//name column
		TableColumn<Operator, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setMinWidth(75);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
		
		//start time column
		TableColumn<Operator, String> startTimeColumn = new TableColumn<>("Start Time");
		startTimeColumn.setMinWidth(75);
		startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
		
		//leave time column
		TableColumn<Operator, String> endTimeColumn = new TableColumn<>("End Time");
		endTimeColumn.setMinWidth(75);
		endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
		  
		operatorsTV.getColumns().addAll(nameColumn, startTimeColumn, endTimeColumn);
		  
		addOperatorBtn.setOnAction(e -> addOperator());
		
		//Delete Button
		deleteBtn.setOnAction(e -> deleteItem());
		
		//Create Schedule Button
		createScheduleBtn.setOnAction(e -> createSchedule());
		
		//Send Schedule Button
		sendScheduleBtn.setOnAction(e -> buildExampleSchedule());
		
        updateAnimation = new AnimationTimer() {

			@Override
			public void handle(long now) {
				update();
			}
		};
		updateAnimation.start();
		
		
	}

	private void buildExampleSchedule() {
		// TODO Auto-generated method stub
		FileWriter fileWriter = null;
		try
		{
			fileWriter = new FileWriter("justCreated.csv");
			fileWriter.append("Green,100");
			
			fileWriter.append("\n101,Thomas,0300");
			for(int i = 0; i < 150; i++)
			{
				fileWriter.append(",1");
			}
			fileWriter.append("\n102,ThomasEvilTwin,0400");
			for(int i = 0; i < 150; i++)
			{
				fileWriter.append(",1");
			}
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally {
			try
			{
				fileWriter.flush();
				fileWriter.close();
			}
			catch (Exception ex2)
			{
				ex2.printStackTrace();
			}
		}
		
		new Alert(AlertType.INFORMATION, "Schedule Sent", ButtonType.OK).showAndWait();
	}

	private void createSchedule() {
		if (scheduleValid())
		{
			checkForIssues();
		}
	}
	
	private ArrayList<Operator> availableOperators = new ArrayList<>();
	private ArrayList<Train> availableTrains = new ArrayList<>();
	private ArrayList<Issue> issues = new ArrayList<>();
	Issue issue;
	int currentTime;
	
	private void checkForIssues() {
		
		int trainsActive = 0;
		int operatorsActive = 0;
		int prevOverflow = 0;
		int currOverflow = 0;
		int runningDuration = getRunningDuration();
		//new Alert(AlertType.INFORMATION, "made it", ButtonType.OK).showAndWait();
		
		for (Operator operator : availableOperators)
		{
			operator.reset();
		}
		
		
		for (currentTime = 0; currentTime <= runningDuration; currentTime++) 
		{
			for (Operator operator : availableOperators)
			{
				if (operator.availableToWork(convertToMilTime(currentTime, startTime)))
				{
					operator.workOneMin();
					operatorsActive++;
				}
			}
			for (Train train : availableTrains)
			{
				if (train.isOperational(convertToMilTime(currentTime, startTime)))
					trainsActive++;
			}
			
			currOverflow = trainsActive - operatorsActive;
			System.out.println(currOverflow + " " + trainsActive + " " + operatorsActive);
			if (currOverflow != prevOverflow)
			{
				issue = new Issue(currOverflow, convertToMilTime(currentTime, startTime));
				issues.add(issue);
				prevOverflow = currOverflow;
			}
			else if (currOverflow == prevOverflow)
			{
				issue.increaseDuration();
			}
			
			operatorsActive = 0;
			trainsActive = 0;
			
		}
		
		//report any issues found when creating the schedule
		reportIssues();
		if (issueCount > 0)
		{
			Alert alert = new Alert(AlertType.INFORMATION, "Warning: There were " + issueCount + " issue(s) found and reported."  
					+  " You must fix these issues before continuing schedule creation.", ButtonType.OK);
			alert.showAndWait();
		}
		else
		{
			buildSchedule();
		}
	}

	private ArrayList<ScheduleLink> sLinks = new ArrayList<>();
	ScheduleLink sLink;
	
	
	private void buildSchedule() {
		new Alert(AlertType.INFORMATION, "Schedule Created", ButtonType.OK).showAndWait();
		sLinks.clear();
		int runningDuration = getRunningDuration();
		for (Operator operator : availableOperators)
		{
			operator.reset();
		}
		
		Operator freeOp = null;
		Train emptyTrain = null;
		for (currentTime = 0; currentTime <= runningDuration; currentTime++) 
		{
			freeOp = null;
			emptyTrain = null;
			for (Operator operator : availableOperators)
			{
				if (operator.availableToWork(convertToMilTime(currentTime, startTime)))
				{
					freeOp = operator;
					break;
				}
			}
		
			for (Train train : availableTrains)
			{
				if (train.isOperational(convertToMilTime(currentTime, startTime)) && !train.hasDriver())
				{
					emptyTrain = train;
					break;
				}
			}
			
			if (emptyTrain != null && freeOp != null)
			{
				sLink = new ScheduleLink(emptyTrain, freeOp);
				sLinks.add(sLink);
				emptyTrain.setHasDriver(true);
				freeOp.setOperatingTrain(true);
			}
			
			for (ScheduleLink link : sLinks)
			{
				if (link.getOperator().availableToWork(convertToMilTime(currentTime, startTime)) && link.getTrain().isOperational(convertToMilTime(currentTime, startTime)))
				{
					link.getOperator().workOneMin();
					link.increaseDuration();
				}
				else
				{
					link.getOperator().setOperatingTrain(false);
					link.getTrain().setHasDriver(false);
				}
			}
			
		}
		
		//new Alert(AlertType.INFORMATION, "made it 2 :)", ButtonType.OK).showAndWait();
		/*
		for (ScheduleLink link : sLinks)
		{
			new Alert(AlertType.INFORMATION, "" + link.getTrain().getID() + " " + link.getOperator().getName() + " " + link.getDuration(), ButtonType.OK).showAndWait();
		}
		*/
	}
	
	
	int issueCount = 0;
	private void reportIssues() {
		int excessTrains = 0;
		int issueDuration = 0;
		issueCount = 0;
		for (Issue issue : issues)
		{
			if (issue.getAmtOfExcessOperators() > 0)
			{
				excessTrains = issue.getAmtOfExcessOperators();
				issueDuration = issue.getConflictDuration();
				issueCount++;
				new Alert(AlertType.INFORMATION, "You are missing " + excessTrains + " operator(s)" + " between times " + String.valueOf(issue.getStartTime()) + " and " + String.valueOf(convertToMilTime(issue.getConflictDuration(), issue.getStartTime())), ButtonType.OK).showAndWait();
			}
		}
		issues = new ArrayList<>();
	}

	private int convertToMilTime(int t1, int t2) {
		int t1Hours;
		int t1Mins;
		
		t1Hours = Math.floorDiv(t1, 60);
		t1Mins = t1 % 60;
		
		if ((t1Mins + t2) % 100 > 59)
		{
			t1Hours++;
			t1Mins = (t1Mins - 60);
		}
		
		return (t1Hours * 100 + t1Mins + t2);
		
	}

	private int getRunningDuration() {
		int startTimeMin = 0;
		int endTimeMin = 0;
		int totalTimeMin = 0;
		int startTimeHours = 0;
		int endTimeHours = 0;
		int totalTimeHours = 0;
		int totalTime = 0;
		String startTimeStr = String.valueOf(startTime);
		String endTimeStr = String.valueOf(endTime);
		
		endTimeHours = Integer.parseInt(endTimeStr.substring(0, endTimeStr.length() - 2));
		startTimeHours = Integer.parseInt(startTimeStr.substring(0, startTimeStr.length() - 2));
		endTimeMin = Integer.parseInt(endTimeStr.substring(endTimeStr.length() - 2, endTimeStr.length()));
		startTimeMin = Integer.parseInt(startTimeStr.substring(startTimeStr.length() - 2, startTimeStr.length()));
		totalTimeHours = endTimeHours - startTimeHours; 
		totalTimeMin = endTimeMin - startTimeMin; 
		totalTime = totalTimeHours * 60 + totalTimeMin;
		
		System.out.println( endTimeHours + " " + startTimeHours + " " + endTimeMin + " " + startTimeMin + " "+ totalTime);
		
		return totalTime;
	}

	//Dummy values expected to change, planner should never want to make a schedule which 
	//starts 11 years in the future, nor make a schedule which ends in the past
	int startTime; 
	int endTime;
	
	private boolean scheduleValid() {
		availableOperators.clear();
		availableTrains.clear();
		startTime = 10000000;
		endTime = -1;
		
		//check: are any operators working more than 8 1/2 hours?
		//check: incorrect time formats for operators?
		boolean validTimes;
		
		availableOperators.clear();
		availableTrains.clear();
		
		for (Operator operator : operatorsTV.getItems())
		{
			validTimes = true;
			
			if (!correctTimeFormat(operator.getStartTime()))
			{
				validTimes = false;
				
				Alert alert = new Alert(AlertType.CONFIRMATION, "Warning: Operator \"" + operator.getName() + 
						"\" has an error in the format of their start time." 
						+ " Would you like to continue schedule creation anyway? \n\n" 
						+ "Tip: A time should only contain numbers between 0 and 9 , it"
						+ " should be in military time format, and if the schedule lasts "
						+ "longer than a day times should be input relative to midnight on"
						+ " the first day of operation. "
						+ "i.e. \"0000\" is midnight of day 1, \"2400\" is midnight of day 2, \"4800\" is midnight of day 3,"
						+ " and so on. For more help check the \"Help\" section."
						, ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				
				if (alert.getResult() == ButtonType.NO)
				{
					return false;
				}
			}
			
			if (!correctTimeFormat(operator.getEndTime()))
			{
				validTimes = false;
				
				Alert alert = new Alert(AlertType.CONFIRMATION, "Warning: Operator \"" + operator.getName() + 
						"\" has an error in the format of their end time." 
						+ " Would you like to continue schedule creation anyway? \n\n" 
						+ "Tip: A time should only contain numbers between 0 and 9 , it"
						+ " should be in military time format, and if the schedule lasts "
						+ "longer than a day times should be input relative to midnight on"
						+ " the first day of operation. "
						+ "i.e. \"0000\" is midnight of day 1, \"2400\" is midnight of day 2, \"4800\" is midnight of day 3,"
						+ " and so on. For more help check the \"Help\" section."
						, ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				
				if (alert.getResult() == ButtonType.NO)
				{
					return false;
				}
			}

			if (validTimes && (Integer.valueOf(operator.getEndTime()) - Integer.valueOf(operator.getStartTime())) > 830)
			{
				Alert alert = new Alert(AlertType.CONFIRMATION, "Warning: Operator \"" + operator.getName() + 
						"\" is set to work for longer than 8 1/2 hours " 
						+  " Would you like to continue schedule creation anyway? ", ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				
				if (alert.getResult() == ButtonType.NO)
				{
					return false;
				}
			}
			
			if (validTimes)
			{
				availableOperators.add(operator);
				if (Integer.valueOf(operator.getEndTime()) > endTime)
				{
					endTime = Integer.valueOf(operator.getEndTime());
				}
				if (Integer.valueOf(operator.getStartTime()) < startTime)
				{
					startTime = Integer.valueOf(operator.getStartTime());
				}
			}
				
		}
		
		//check: incorrect time formats for trains?
		for (Train train : trainsTV.getItems())
		{
			validTimes = true;
			
			if (!correctTimeFormat(train.getDepartTime()))
			{
				validTimes = false;
				
				Alert alert = new Alert(AlertType.CONFIRMATION, "Warning: Train \"" + train.getID() + 
						"\" has an error in the format of their depart time." 
						+ " Would you like to continue schedule creation anyway? \n\n" 
						+ "Tip: A time should only contain numbers between 0 and 9 , it"
						+ " should be in military time format, and if the schedule lasts "
						+ "longer than a day times should be input relative to midnight on"
						+ " the first day of operation. "
						+ "i.e. \"0000\" is midnight of day 1, \"2400\" is midnight of day 2, \"4800\" is midnight of day 3,"
						+ " and so on. For more help check the \"Help\" section."
						, ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				
				if (alert.getResult() == ButtonType.NO)
				{
					return false;
				}
			}
			
			if (!correctTimeFormat(train.getReturnTime()))
			{
				validTimes = false;
				
				Alert alert = new Alert(AlertType.CONFIRMATION, "Warning: Train \"" + train.getID() + 
						"\" has an error in the format of their return time." 
						+ " Would you like to continue schedule creation anyway? \n\n" 
						+ "Tip: A time should only contain numbers between 0 and 9 , it"
						+ " should be in military time format, and if the schedule lasts "
						+ "longer than a day times should be input relative to midnight on"
						+ " the first day of operation. "
						+ "i.e. \"0000\" is midnight of day 1, \"2400\" is midnight of day 2, \"4800\" is midnight of day 3,"
						+ " and so on. For more help check the \"Help\" section."
						, ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				
				if (alert.getResult() == ButtonType.NO)
				{
					return false;
				}
			}
			
			if (validTimes)
			{
				availableTrains.add(train);
				if (Integer.valueOf(train.getReturnTime()) > endTime)
				{
					endTime = Integer.valueOf(train.getReturnTime());
				}
				if (Integer.valueOf(train.getDepartTime()) < startTime)
				{
					startTime = Integer.valueOf(train.getDepartTime());
				}
			}
			
		}

		return true;
	}

	private boolean correctTimeFormat(String s) {
		if (s.isEmpty()) {
		    return false;
		}
		
		//check if there is a non_digit input
		char c;
		for (int i = 0; i < s.length(); i++) {
			c = s.charAt(i);
		    if (c < '0' || c > '9') {
		    	return false;
		    }
		}
		
		//check if user entered minute value > 59
		if (Integer.valueOf(s) % 100 > 59)
			return false;
		
		return true;
	}

	private void deleteItem() {
		ObservableList<Train> trainsSelected, allTrains;
		ObservableList<Operator> operatorsSelected, allOperators;
		allTrains = trainsTV.getItems();
		allOperators = operatorsTV.getItems();
		trainsSelected = trainsTV.getSelectionModel().getSelectedItems();
		operatorsSelected = operatorsTV.getSelectionModel().getSelectedItems();
		trainsSelected.forEach(allTrains::remove);
		operatorsSelected.forEach(allOperators::remove);
	}

	private void addOperator() {
		if (!operatorNameTF.getText().isEmpty() && !operatorStartTimeTF.getText().isEmpty() && !operatorEndTimeTF.getText().isEmpty())
		{
			Operator operator = new Operator(operatorNameTF.getText(), operatorStartTimeTF.getText(), operatorEndTimeTF.getText());
			operatorsTV.getItems().add(operator);
			operatorNameTF.clear();
			operatorStartTimeTF.clear();
			operatorEndTimeTF.clear();
		}
		else
		{
			new Alert(AlertType.INFORMATION, "You must fill in all fields before adding an operator", ButtonType.OK).showAndWait();
		}
	}

	private void addTrain() {
		if (!trainIDTF.getText().isEmpty() && !trainStartTimeTF.getText().isEmpty() && !trainEndTimeTF.getText().isEmpty())
		{
			Train train = new Train(trainIDTF.getText(), trainStartTimeTF.getText(), trainEndTimeTF.getText());
			trainsTV.getItems().add(train);
			trainIDTF.clear();
			trainStartTimeTF.clear();
			trainEndTimeTF.clear();
		}
		else
		{
			new Alert(AlertType.INFORMATION, "You must fill in all fields before adding an train", ButtonType.OK).showAndWait();
		}
	}
		

	private void getThroughput() {
		if (lineCB.getValue() == "Green")
		{
			throughputTF.setText(String.valueOf(greenThroughput));
		}
		if (lineCB.getValue() == "Red")
		{
			throughputTF.setText(String.valueOf(redThroughput));
		}
	}

	private void setThroughput() {
		if (lineCB.getValue() == "Green")
		{
			greenThroughput = Integer.valueOf(throughputTF.getText());
			new Alert(AlertType.INFORMATION, "Green line throughput has been set to " + String.valueOf(greenThroughput), ButtonType.OK).showAndWait();
		}
		else if (lineCB.getValue() == "Red")
		{
			redThroughput = Integer.valueOf(throughputTF.getText());
			new Alert(AlertType.INFORMATION, "Red line throughput has been set to " + String.valueOf(redThroughput), ButtonType.OK).showAndWait();
		}
	}

	// NOTE: This is where you get new information from your singleton
	// You can read/change fx elements linked above
	
	// WARNING: This assumes your singleton is updating its information
	private void update() {
		//int count = mySin.getCount();
		//counter.setText(Integer.toString(count));
		locationLbl.setText(Double.toString(mySin.getLatitude()) + ", " +  Double.toString(mySin.getLongitude()));
		
		
	}
}
