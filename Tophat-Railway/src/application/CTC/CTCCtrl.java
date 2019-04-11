package application.CTC;
import java.util.stream.IntStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.*;
import application.TrackModel.TrackModelSingleton;
import javafx.collections.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
public class CTCCtrl implements Initializable {

	// Links to your Singleton (NO TOUCHY!!)
	private CTCSingleton mySin = CTCSingleton.getInstance();
	

	private AnimationTimer updateAnimation;

	// NOTE: This is where you link to elements in your FXML file
	// Example:(fx:id="counter")
	// WARNING: Your fx:id and variable name Must Match!
	// Links to FXML elements
	@FXML
	private TextField TrainIDTextField;
	@FXML
	private TextField SpeedTextField;
	@FXML
    private TextField DepartureTimeText;
	@FXML
	private ChoiceBox<String> DepartureStationChoiceBox;
	@FXML
	private ChoiceBox<String> LineChoiceBox;
	@FXML
	private ChoiceBox<String> DestinationChoiceBox;
	@FXML
	private ChoiceBox<String> ImportScheduleChioceBox;
    @FXML
	private ListView<String> ScheduleListView;
	@FXML
	private ListView<String> ManagementListView;
	@FXML
    private TextField IDModify;
    @FXML
    private TextField AuthorityModify;
    @FXML
    private TextField SpeedModify;
	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
	// Control Functions
	public void ButtonCreateTrainClicked() {
		if (mySin.addTrain(TrainIDTextField.getCharacters().toString(),SpeedTextField.getCharacters().toString())){
			System.out.print("Successful");//TODO Change this into UI
		}
		String departure=DepartureStationChoiceBox.getSelectionModel().getSelectedItem();
		String line=LineChoiceBox.getSelectionModel().getSelectedItem();
		String destination=DestinationChoiceBox.getSelectionModel().getSelectedItem();
		String departuretime=DepartureTimeText.getCharacters().toString();
		String[] routine=mySin.getStations();
		Integer[] blocks = Arrays.stream(mySin.getBlocks()).boxed().toArray( Integer[]::new );
		Integer[] distance = Arrays.stream(mySin.getDistance()).boxed().toArray( Integer[]::new );
		int s=DepartureStationChoiceBox.getSelectionModel().getSelectedIndex();
		int e=DestinationChoiceBox.getSelectionModel().getSelectedIndex();
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		ArrayList<Integer> list3 = new ArrayList<Integer>();

		if (s<e){
			list.addAll(Arrays.asList(Arrays.copyOfRange(routine,s,e+1)));
			list2.addAll(Arrays.asList(Arrays.copyOfRange(blocks,s,e)));
			list3.addAll(Arrays.asList(Arrays.copyOfRange(distance,s,e)));

		}
		else{
			String[] reverseRoutine=new String[routine.length];
			Integer[] reverseBlocks=new Integer[blocks.length];
			Integer[] reverseDistance=new Integer[blocks.length];
			for (int i=0;i<routine.length;i++){
				reverseRoutine[i]=routine[routine.length-1-i];
				reverseBlocks[i]=blocks[blocks.length-1-i];
				reverseDistance[i]=distance[distance.length-1-i];
			}
			
			s=routine.length-s-1;
			e=routine.length-e-1;
			list.addAll(Arrays.asList(Arrays.copyOfRange(reverseRoutine,s,e+1)));
			list2.addAll(Arrays.asList(Arrays.copyOfRange(reverseBlocks,s,e)));
			list3.addAll(Arrays.asList(Arrays.copyOfRange(reverseDistance,s,e)));
		}
		Object[] objectList = list.toArray();
		Object[] objectList2 = list2.toArray();
		Object[] objectList3 = list3.toArray();
		String[] myRoute=Arrays.copyOf(objectList,objectList.length,String[].class);
		Integer[] myBlocks=Arrays.copyOf(objectList2,objectList2.length,Integer[].class);
		Integer[] myDistance=Arrays.copyOf(objectList3,objectList3.length,Integer[].class);
		//int[] intArray = Arrays.stream(myBlocks).mapToInt(Integer::intValue).toArray();
		int tmpauthority=mySin.viewtrains().get(Integer.parseInt(TrainIDTextField.getCharacters().toString())).getAuthority();
		mySin.ModifyTrain(Integer.parseInt(TrainIDTextField.getCharacters().toString()),IntStream.of(Arrays.stream(myBlocks).mapToInt(Integer::intValue).toArray()).sum()+1+tmpauthority,Integer.parseInt(SpeedTextField.getCharacters().toString()));
		mySin.addSchedule(Integer.parseInt(TrainIDTextField.getCharacters().toString()),line,myRoute,myDistance,0,Integer.parseInt(SpeedTextField.getCharacters().toString()));//TODO convert time String (here is 0) into an Int
		ObservableList<String> ScheduleString = FXCollections.observableArrayList(mySin.tolist());
		ScheduleListView.setItems(ScheduleString);
		ObservableList<String> TrainString = FXCollections.observableArrayList(mySin.tolistTrains());
		ManagementListView.setItems(TrainString);

	}
	public void ModifyClicked() {
		String ID=IDModify.getCharacters().toString();
		mySin.ModifyTrain(Integer.valueOf(ID),Integer.parseInt(AuthorityModify.getCharacters().toString()),Integer.parseInt(SpeedModify.getCharacters().toString()));
		ObservableList<String> TrainString = FXCollections.observableArrayList(mySin.tolistTrains());
		ManagementListView.setItems(TrainString);
	}
	public void ImportClicked(){
		//TODO import Schedule file
		mySin.addTrain("1", "80");
		mySin.addTrain("2", "80");
		mySin.addTrain("3", "80");
		mySin.ModifyTrain(1, 3, 80);
		mySin.ModifyTrain(2, 3, 80);
		mySin.ModifyTrain(3, 3, 80);
		String[] tmp1={"B0","B1 FIXME"};
		Integer[] tmp2=new Integer[2];
		tmp2[0]=1000;
		tmp2[1]=1000;
		mySin.addSchedule(1, "Green", tmp1,tmp2, 0, 80);
		mySin.addSchedule(2, "Green", tmp1,tmp2, 30*60, 80);
		mySin.addSchedule(3, "Green", tmp1,tmp2, 60*60, 80);
		tmp1[0]="B1 FIXME";
		tmp1[1]="B2 StationA";
		mySin.addSchedule(1, "Green", tmp1,tmp2, 0, 80);
		mySin.addSchedule(2, "Green", tmp1,tmp2, 30*60, 80);
		tmp1[0]="B1 FIXME";
		tmp1[1]="B3";
		mySin.addSchedule(3, "Green", tmp1,tmp2, 60*60, 80);
		tmp1[0]="B2 StationA";
		mySin.addSchedule(1, "Green", tmp1,tmp2, 0, 80);
		ObservableList<String> ScheduleString = FXCollections.observableArrayList(mySin.tolist());
		ScheduleListView.setItems(ScheduleString);




		//TODO update the current schedule
	}
	// Starts the automatic update (NO TOUCHY!!)
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String[] routine=mySin.getStations();
		DepartureStationChoiceBox.setItems(FXCollections.observableArrayList(routine));
		//TODO load info from trackmodel
		LineChoiceBox.setItems(FXCollections.observableArrayList("Green", "Red"));
		DestinationChoiceBox.setItems(FXCollections.observableArrayList(routine));
		//TODO load info from trackmodel
		String[] Schedulename={"schedule1", "FIXME","schedule3"};
		ImportScheduleChioceBox.setItems(FXCollections.observableArrayList(Schedulename));
		//TODO load info from MBO
		updateAnimation = new AnimationTimer() {

			@Override
			public void handle(long now) {
				update();
			}
		};
		updateAnimation.start();

	}

	// NOTE: This is where you get new information from your singleton
	// You can read/change fx elements linked above
	// WARNING: This assumes your singleton is updating its information
	private void update() {
		//DepartureStationChoiceBox.setItems(FXCollections.observableArrayList("STATIONA", "STATIONB"));
		ObservableList<String> TrainString = FXCollections.observableArrayList(mySin.tolistTrains());
		ManagementListView.setItems(TrainString);


	}
}
