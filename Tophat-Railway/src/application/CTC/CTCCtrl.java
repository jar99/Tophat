package application.CTC;
import java.util.stream.IntStream;
import java.io.*;
import java.net.URL;
import java.util.*;

import application.TrackModel.TrackLine;
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
import application.TrackModel.*;
public class CTCCtrl implements Initializable {

	// Links to your Singleton (NO TOUCHY!!)
	private CTCSingleton mySin = CTCSingleton.getInstance();
	

	private AnimationTimer updateAnimation;

	// NOTE: This is where you link to elements in your FXML file
	// Example:(fx:id="counter")
	// WARNING: Your fx:id and variable name Must Match!
	// Links to FXML elements
    @FXML
    private Label clockLabel;

	@FXML
	private TextField TrainIDTextField;
	@FXML
	private TextField SpeedTextField;
	@FXML
    private TextField DepartureTimeText;
	@FXML
	private ChoiceBox<String> LineChoiceBox;
	@FXML
	private ChoiceBox<String> SwitchChoiceBox;
	@FXML
	private ChoiceBox<String> DestinationChoiceBox;
	@FXML
	private ChoiceBox<String> ImportScheduleChioceBox;
	@FXML
	private ChoiceBox<String> DepartureStationChoiceBox1;
	@FXML
	private ChoiceBox<String> ModifyChoiceBox;
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
    private TextField SpeedModify;
	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
	// Control Functions
    private boolean stopupdate=false;
    private HashMap <String,String> Departurestorage=new HashMap <String,String>();
    private HashMap <String,Integer> Departureindex=new HashMap<String,Integer>();
    public void StraightClicked() {
    	TrackControllerInterface TCInterface=TrackControllerSingleton.getInstance();
    	String switchID=SwitchChoiceBox.getSelectionModel().getSelectedItem();
    	TCInterface.manuallySetSwitch(Integer.parseInt(switchID.split(":")[1]),true);
    	return;
    }
    public void DivergeClicked() {
    	TrackControllerInterface TCInterface=TrackControllerSingleton.getInstance();
    	String switchID=SwitchChoiceBox.getSelectionModel().getSelectedItem();
    	TCInterface.manuallySetSwitch(Integer.parseInt(switchID.split(":")[1]),false);
    	return;
    }
    public void ButtonPauseClicked() {
    	ClockSingleton aClock=ClockSingleton.getInstance();
    	aClock.setRatio(0);
    }
    public void JIASU() {
    	ClockSingleton aClock=ClockSingleton.getInstance();
    	aClock.setRatio(aClock.getRatio()*10);
    }
    public void JIANSU(){
    	ClockSingleton aClock=ClockSingleton.getInstance();
    	aClock.setRatio(aClock.getRatio()/10);
    }
    public void ButtonResumeClicked() {
    	ClockSingleton aClock=ClockSingleton.getInstance();
    	aClock.setRatio(1);
    }
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
		String departure;
		if (Departurestorage.containsKey(TrainIDTextField.getCharacters().toString())) {
			departure=Departurestorage.get(TrainIDTextField.getCharacters().toString());
		}
		else {
			Departurestorage.put(TrainIDTextField.getCharacters().toString(),"yard");
			departure="yard";
		}
		String line=LineChoiceBox.getSelectionModel().getSelectedItem();
		String destination=DestinationChoiceBox.getSelectionModel().getSelectedItem();
		String departuretime=DepartureTimeText.getCharacters().toString();
		String[] origintime=departuretime.split(":");
		int mytime=Integer.parseInt(origintime[0])*3600+Integer.parseInt(origintime[1])*60;
		String[] routine=mySin.getStations();
		Integer[] blocks = Arrays.stream(mySin.getBlocks()).boxed().toArray( Integer[]::new );
		Integer[] distance = Arrays.stream(mySin.getDistance()).boxed().toArray( Integer[]::new );
		int s;
		if (Departureindex.containsKey(TrainIDTextField.getCharacters().toString())) {
			s=Departureindex.get(TrainIDTextField.getCharacters().toString());
		}
		else {
			Departureindex.put(TrainIDTextField.getCharacters().toString(), 150);
			s=150;
		}
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
		mySin.addSchedule(Integer.parseInt(TrainIDTextField.getCharacters().toString()),line,myRoute,myDistance,mytime,Integer.parseInt(SpeedTextField.getCharacters().toString()));//TODO convert time String (here is 0) into an Int
		mySin.ModifyTrain(Integer.parseInt(TrainIDTextField.getCharacters().toString()),mySin.viewSchedule().get(Integer.valueOf(TrainIDTextField.getCharacters().toString())).getAuthority(),Integer.parseInt(SpeedTextField.getCharacters().toString()));
		ObservableList<String> ScheduleString = FXCollections.observableArrayList(mySin.tolist());
		ScheduleListView.setItems(ScheduleString);
		ObservableList<String> TrainString = FXCollections.observableArrayList(mySin.tolistTrains());
		ManagementListView.setItems(TrainString);
		Departurestorage.put(TrainIDTextField.getCharacters().toString(), destination);
		Departureindex.put(TrainIDTextField.getCharacters().toString(), e);

	}
	public void ModifyClicked() {
		if (mySin.addTrain(IDModify.getCharacters().toString(),SpeedModify.getCharacters().toString())){
			System.out.print("Successful");//TODO Change this into UI
		}
		String departure;
		if (Departurestorage.containsKey(IDModify.getCharacters().toString())) {
			departure=Departurestorage.get(IDModify.getCharacters().toString());
		}
		else {
			Departurestorage.put(TrainIDTextField.getCharacters().toString(),"yard");
			departure="yard";
		}
		String line=mySin.viewLine(Integer.parseInt(IDModify.getCharacters().toString()));
		String destination=ModifyChoiceBox.getSelectionModel().getSelectedItem();
		ClockSingleton tmpClock=ClockSingleton.getInstance();		
		int mytime=tmpClock.getCurrentTimeSeconds();
		String[] routine=mySin.getStations();
		Integer[] blocks = Arrays.stream(mySin.getBlocks()).boxed().toArray( Integer[]::new );
		Integer[] distance = Arrays.stream(mySin.getDistance()).boxed().toArray( Integer[]::new );
		int s;
		if (Departureindex.containsKey(IDModify.getCharacters().toString())) {
			s=Departureindex.get(IDModify.getCharacters().toString());
		}
		else {
			Departureindex.put(IDModify.getCharacters().toString(), 150);
			s=150;
		}
		int e=ModifyChoiceBox.getSelectionModel().getSelectedIndex();
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
		int tmpauthority=mySin.viewtrains().get(Integer.parseInt(IDModify.getCharacters().toString())).getAuthority();
		mySin.addSchedule(Integer.parseInt(IDModify.getCharacters().toString()),line,myRoute,myDistance,mytime,Integer.parseInt(SpeedModify.getCharacters().toString()));//TODO convert time String (here is 0) into an Int
		mySin.ModifyTrain(Integer.parseInt(IDModify.getCharacters().toString()),mySin.viewSchedule().get(Integer.valueOf(IDModify.getCharacters().toString())).getAuthority(),Integer.parseInt(SpeedModify.getCharacters().toString()));
		ObservableList<String> ScheduleString = FXCollections.observableArrayList(mySin.tolist());
		ScheduleListView.setItems(ScheduleString);
		ObservableList<String> TrainString = FXCollections.observableArrayList(mySin.tolistTrains());
		ManagementListView.setItems(TrainString);
		Departurestorage.put(IDModify.getCharacters().toString(),destination);
		Departureindex.put(IDModify.getCharacters().toString(), e);

	}
	public void ImportClicked(){
		//TODO import Schedule file
		String scheduleName=ImportScheduleChioceBox.getSelectionModel().getSelectedItem();
		
		try{
		File scheduleFile = new File(scheduleName);
		Scanner scan=new Scanner(scheduleFile);
		scan.nextLine();
		while (true) {
		String[] forSchedule=scan.nextLine().split(",");
		String[] tmp1=mySin.getStations();
		int mytime=-1;
		mySin.addTrain(forSchedule[0], "20");
		for(int i=0;i<150;i++) {
			String departure;
			if (forSchedule[(i+63)%150+3].equals("1")) {
				
				if (Departurestorage.containsKey(forSchedule[0])) {
					departure=Departurestorage.get(forSchedule[0]);
				}
			
				else {
					Departurestorage.put(forSchedule[0],"yard");
					departure="yard";
				}
			String destination=tmp1[150-1-(i+63)%150];
			if(mytime==-1) {
				mytime=Integer.parseInt(forSchedule[2])/100*3600+(Integer.parseInt(forSchedule[2])-Integer.parseInt(forSchedule[2])/100*100)*60;
			}
			else {
				int[] tmptime=mySin.viewSchedule().get(Integer.parseInt(forSchedule[0])).getLeaveTime();
				mytime=tmptime[tmptime.length-1];
			}
			String[] routine=mySin.getStations();
			Integer[] blocks = Arrays.stream(mySin.getBlocks()).boxed().toArray( Integer[]::new );
			Integer[] distance = Arrays.stream(mySin.getDistance()).boxed().toArray( Integer[]::new );
			int s=0;
			if (Departureindex.containsKey(forSchedule[0])) {
				s=Departureindex.get(forSchedule[0]);
			}
			else {
				Departureindex.put(forSchedule[0], 150);
				s=150;
			}
			int e=150-1-(i+63)%150;
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
				for (int j=0;j<routine.length;j++){
					reverseRoutine[j]=routine[routine.length-1-j];
					reverseBlocks[j]=blocks[blocks.length-1-j];
					reverseDistance[j]=distance[distance.length-1-j];
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
			int tmpauthority=mySin.viewtrains().get(Integer.parseInt(forSchedule[0])).getAuthority();
			mySin.addSchedule(Integer.parseInt(forSchedule[0]),"Green",myRoute,myDistance,mytime,20);
			mySin.ModifyTrain(Integer.parseInt(forSchedule[0]),mySin.viewSchedule().get(Integer.valueOf(forSchedule[0])).getAuthority(),20);
			ObservableList<String> ScheduleString = FXCollections.observableArrayList(mySin.tolist());
			ScheduleListView.setItems(ScheduleString);
			ObservableList<String> TrainString = FXCollections.observableArrayList(mySin.tolistTrains());
			ManagementListView.setItems(TrainString);
			Departurestorage.put(forSchedule[0],destination);
			Departureindex.put(forSchedule[0], e);
			}	
			}
			if (!scan.hasNextLine()) {
				break;
			}
		}

		
		}catch(Exception e) {System.out.println("No such file");System.out.println(e);}
		
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
		Set<String> filenames=new TreeSet<String>();
		File folder = new File(".");
		for (File fileEntry:folder.listFiles()) {
			if (fileEntry.isDirectory()) continue;
			if (fileEntry.getName().contains(".csv")) {
				filenames.add(fileEntry.getName());
			}
		}
		HashMap<String, TrackLine> track1 = mySin.viewTrack();
		if (!track1.isEmpty()) {
			TrackModelInterface aTest = TrackModelSingleton.getInstance();
			//System.out.println("*************"+aTest.getTotalBoarders("green"));
		}
		
		ClockSingleton aClock=ClockSingleton.getInstance();
		clockLabel.setText(aClock.getCurrentTimeString());
		String[] routine=mySin.getStations();
		String[] sections=mySin.getSections();
		String[] realstations=mySin.getOnlyStations();
		if (!stopupdate) {
			ModifyChoiceBox.setItems(FXCollections.observableArrayList(routine));
			//TODO load info from trackmodel
			LineChoiceBox.setItems(FXCollections.observableArrayList("Green", "Red"));
			DestinationChoiceBox.setItems(FXCollections.observableArrayList(routine));
			//TODO load info from trackmodel
			//String[] Schedulename={"schedule1", "FIXME","schedule3"};
			ImportScheduleChioceBox.setItems(FXCollections.observableArrayList(filenames));
			
			
			DepartureStationChoiceBox1.setItems(FXCollections.observableArrayList(sections));
			String[] switches=mySin.switchstuff();
			SwitchChoiceBox.setItems(FXCollections.observableArrayList(switches));
			
			
			


		}
		String[] mapstring=new String[routine.length];
		for (int i=0;i<mapstring.length;i++) {
			TrackControllerInterface TCInterface=TrackControllerSingleton.getInstance();
			HashMap<String, TrackLine> track = mySin.viewTrack();
			boolean isOccupied=false;
			for(String key:track.keySet()) {
				TrackLine tmp=track.get(key);
				if (i<=(tmp.getBlocks().size())&&i!=0)
					isOccupied=TCInterface.getOccupancyCTC(tmp.getLineName(),i);
			}		
			if (isOccupied)
				mapstring[routine.length-1-i]=routine[routine.length-1-i]+" Occupied";
			else
				mapstring[routine.length-1-i]=routine[routine.length-1-i]+" Not Occupied";
		}
		MapListView.setItems(FXCollections.observableArrayList(mapstring));
		mySin.switchstuff();
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
			//System.out.println(tmp2.toString());
			for (int i=0;i<tmp2.getLeaveTime().length-1;i++){
				if (tmp2.getLeaveTime()[i]==myTime){
					String Block=tmp2.getStation()[i+1];
					int n=Integer.parseInt(Block.split(" ")[1]);
					TCInterface.sendTrainToBlock(tmp2.getID(),n,tmp2.getspdprint());
				}
			}
			if (myTime==tmp2.getLeaveTime()[tmp2.getLeaveTime().length-1]){
				TCInterface.sendTrainToBlock(tmp2.getID(),-1,tmp2.getspdprint());
			}
		}

	}
}



