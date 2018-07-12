package westrosPackage;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HouseInformation{
	@FXML
	private StackPane root;
	@FXML
	private Label houseName;
	@FXML
	private static TextField seat, words, xCoord, yCoord, region;
	@FXML
	private Button addMember, editMember, deleteMember, save, cancel, runSlideshow, stopSlideshow;
	@FXML
	private VBox colorsContainer;
	@FXML
	static ListView<String> membersList;
	@FXML
	private ImageView sigil;
	
	static Stage primaryStage;
	static ArrayList<String> originalMembersList;
	public static boolean successfulShow = false;
	
	
	/**
	 * Constructor to display the house information
	 * @param theHouseName
	 */
	@SuppressWarnings("unchecked")
	public HouseInformation(String theHouseName){
		successfulShow = false;
		primaryStage = new Stage();
		originalMembersList = new ArrayList<String>();
		try {
			 root = FXMLLoader.load(NewHouse.class.getResource("/information.fxml"));
		} catch (IOException e) {e.printStackTrace();}
		ImageView bg = new ImageView(new Image(HouseInformation.class.getResourceAsStream("/Images/houseInformationBG.jpg")));
		root.getChildren().add(0,bg);
		Scene scene = new Scene(root, 600,500);
		bg.fitWidthProperty().bind(scene.widthProperty());
		bg.fitHeightProperty().bind(scene.heightProperty());
		houseName = (Label)root.lookup("#houseName");
		houseName.setText(theHouseName);		
		sigil = (ImageView) root.lookup("#sigil");
		colorsContainer = (VBox) root.lookup("#colorsContainer");
		xCoord = (TextField) root.lookup("#xCoord");
		yCoord = (TextField) root.lookup("#yCoord");
		membersList = (ListView<String>) root.lookup("#membersList");
		addMember = (Button) root.lookup("#addMember");
		editMember = (Button) root.lookup("#editMember");
		deleteMember = (Button) root.lookup("#deleteMember");
		save = (Button) root.lookup("#save");
		cancel = (Button) root.lookup("#cancel");
		runSlideshow = (Button) root.lookup("#runSlideshow");
		stopSlideshow = (Button) root.lookup("#stopSlideshow");
		seat  = (TextField) root.lookup("#seat");
		words = (TextField) root.lookup("#words");
		region = (TextField) root.lookup("#region");
		
		InformationHandler.populateHouseInfo(houseName, words, seat, region, xCoord, yCoord, sigil, colorsContainer);
		InformationHandler.membersListHandler(membersList, addMember,editMember, deleteMember);
		InformationHandler.cancelHandler(cancel, false);
		InformationHandler.saveInformationHandler(save,houseName);
		InformationHandler.runSlideShowHandler(runSlideshow);
		InformationHandler.stopSlideShowHandler(stopSlideshow);
		
		scene.getStylesheets().add(HouseInformation.class.getResource("/houseInformation.css").toExternalForm()); // add the cascade styling sheet
		primaryStage.setScene(scene);
		primaryStage.initModality( Modality.APPLICATION_MODAL);
		primaryStage.setTitle("House Information");
		primaryStage.getIcons().add(new Image(HouseInformation.class.getResourceAsStream("/Images/icon.png")));
		primaryStage.setResizable(false);
		primaryStage.show();
		
		primaryStage.setOnCloseRequest(e->{
			if(SlideShow.primaryStage!=null){
				 SlideShow.primaryStage.close();
				 SlideShow.primaryStage=null;
			}
		});	
		
	}
	
}

