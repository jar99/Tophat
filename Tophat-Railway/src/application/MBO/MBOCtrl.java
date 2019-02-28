package application.MBO;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

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
    private ListView<?> operatorsLV;

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
    private ListView<?> trainsLV;

    @FXML
    private ScrollBar trainsSB;

    @FXML
    private TextField trainNameTF;

    @FXML
    private TextField trainStartTimeTF;

    @FXML
    private TextField trainEndTimeTF;

    @FXML
    private Button addTrainBtn;

    @FXML
    private Button createScheduleBtn;

    @FXML
    private ChoiceBox<?> lineCB;

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
    private ListView<?> schedulerLV;

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
		//int count = mySin.getCount();
		//counter.setText(Integer.toString(count));
		locationLbl.setText(Double.toString(mySin.getLatitude()) + ", " +  Double.toString(mySin.getLongitude()));

	}
}
