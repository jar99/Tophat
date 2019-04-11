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
import application.CTC.Schedule;
import application.ClockSingleton;
import application.TrackController.*;
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
	private ChoiceBox<String> DepartureStationChoiceBox1;
    @FXML
	private ListView<String> ScheduleListView;
	@FXML
	private ListView<String> ManagementListView;
	@FXML
	private ListView<String> ScheduleListView1;
	@FXML
	private ListView<String> MapListView;
	@FXML
    private TextField IDModify;
    @FXML
    private TextField AuthorityModify;
    @FXML
    private TextField SpeedModify;
	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
	// Control Functions
    private boolean stopupdate=false;
    public void ButtonOpenClicked() {
    	int ID=DepartureStationChoiceBox1.getSelectionModel().getSelectedIndex();
    	mySin.openSection(mySin.getifSectionClose().length-ID-1);
    }
    public void ButtonCloseClicked() {
    	int ID=DepartureStationChoiceBox1.getSelectionModel().getSelectedIndex();
    	mySin.closeSection(mySin.getifSectionClose().length-ID-1);
    }
	public void ButtonCreateTrainClicked() {
		if (mySin.addTrain(TrainIDTextField.getCharacters().toString(),SpeedTextField.getCharacters().toString())){
			System.out.print("Successful");//TODO Change this into UI
		}
		String departure=DepartureStationChoiceBox.getSelectionModel().getSelectedItem();
		String line=LineChoiceBox.getSelectionModel().getSelectedItem();
		String destination=DestinationChoiceBox.getSelectionModel().getSelectedItem();
		String departuretime=DepartureTimeText.getCharacters().toString();
		String[] origintime=departuretime.split(":");
		int mytime=Integer.parseInt(origintime[0])*3600+Integer.parseInt(origintime[1])*60;
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
		mySin.ModifyTrain(Integer.parseInt(TrainIDTextField.getCharacters().toString()),IntStream.of(Arrays.stream(myBlocks).mapToInt(Integer::intValue).toArray()).sum()+tmpauthority,1+(int)(Integer.parseInt(SpeedTextField.getCharacters().toString())*0.448));
		mySin.addSchedule(Integer.parseInt(TrainIDTextField.getCharacters().toString()),line,myRoute,myDistance,mytime,1+(int)(Integer.parseInt(SpeedTextField.getCharacters().toString())*0.448));//TODO convert time String (here is 0) into an Int
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
		String[] tmp1={"Block 1 ","Block2 PIONEER","Block 3 "};
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
		String[] routine=mySin.getStations();
		String[] sections=mySin.getSections();
		String[] realstations=mySin.getOnlyStations();
		if (!stopupdate) {
			DepartureStationChoiceBox.setItems(FXCollections.observableArrayList(routine));
			//TODO load info from trackmodel
			LineChoiceBox.setItems(FXCollections.observableArrayList("Green", "Red"));
			DestinationChoiceBox.setItems(FXCollections.observableArrayList(routine));
			//TODO load info from trackmodel
			String[] Schedulename={"schedule1", "FIXME","schedule3"};
			ImportScheduleChioceBox.setItems(FXCollections.observableArrayList(Schedulename));
			DepartureStationChoiceBox1.setItems(FXCollections.observableArrayList(sections));

		}
		String[] mapstring=new String[routine.length];
		for (int i=0;i<mapstring.length;i++) {
			mapstring[i]=routine[i]+" Not Occupied";
		}
		MapListView.setItems(FXCollections.observableArrayList(mapstring));
		if (realstations!=null)
		ScheduleListView1.setItems(FXCollections.observableArrayList(realstations));
		if (!routine[0].equals("")) stopupdate=true;
		//TODO load info from MBO
		ClockSingleton myClock=ClockSingleton.getInstance();
		int myTime=myClock.getCurrentTimeHours()*60*60+myClock.getCurrentTimeMinutes()*60+myClock.getCurrentTimeSeconds();
		//DepartureStationChoiceBox.setItems(FXCollections.observableArrayList("STATIONA", "STATIONB"));
		ObservableList<String> TrainString = FXCollections.observableArrayList(mySin.tolistTrains());
		ManagementListView.setItems(TrainString);
		HashMap<Integer,Schedule> tmp=new HashMap<Integer,Schedule>();
		tmp=mySin.viewSchedule();
		for (Integer key:tmp.keySet()){
			TrackControllerInterface TCInterface=TrackControllerSingleton.getInstance();
			Schedule tmp2=tmp.get(key);
			for (int i=0;i<tmp2.getLeaveTime().length-1;i++){
				if (tmp2.getLeaveTime()[i]==myTime){
					
					String Block=tmp2.getStation()[i];
					int n=-1;
					for (int m=0;m<mySin.getStations().length;m++){
						if(mySin.getStations()[m].equals(Block)){
							n=m;
							break;
						}
					}
					TCInterface.sendTrainToBlock(tmp2.getID(),n,tmp2.getSpeed());
				}
			}
			if (myTime==tmp2.getLeaveTime()[tmp2.getLeaveTime().length-1]){
				TCInterface.sendTrainToBlock(tmp2.getID(),-1,tmp2.getSpeed());
			}
		}

	}
}