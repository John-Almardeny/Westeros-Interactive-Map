package westrosPackage;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * To Add New House to Map
 * @author Yahya Almardeny
 * @version 15/04/2017
 */
public class NewHouse{
	@FXML
	private StackPane root;
	@FXML
	static TextField houseName, seat, words, xCoord, yCoord;
	@FXML
	static ComboBox<String> regions;
	@FXML
	private Label dir;
	@FXML
	private Button browse, addColor, removeColor, addMember, editMember, deleteMember, save, cancel;
	@FXML
	private ListView<Rectangle> colorsList;
	@FXML
	static ListView<Rectangle> addedColors;
	@FXML
	static ListView<String> membersList;
	
	static Stage primaryStage;
	public static boolean successfulAdd = false;
	
	
		
	/**
	 * The Constructor to create a new house 
	 * in a given coordinates on the map
	 * @param x
	 * @param y
	 */
	@SuppressWarnings("unchecked")
	public NewHouse(double x, double y){
		successfulAdd = false;
		primaryStage = new Stage();
		try {
			 root = FXMLLoader.load(NewHouse.class.getResource("/addHouse.fxml"));
		} catch (IOException e) {e.printStackTrace();}
		ImageView bg = new ImageView(new Image(NewHouse.class.getResourceAsStream("/Images/addHouseBG.jpg")));
		root.getChildren().add(0,bg);
		Scene scene = new Scene(root, 600,400);
		bg.fitWidthProperty().bind(scene.widthProperty());
		bg.fitHeightProperty().bind(scene.heightProperty());
		xCoord = (TextField) root.lookup("#xCoord");
		xCoord.setText(String.valueOf(x));
		yCoord = (TextField) root.lookup("#yCoord");
		yCoord.setText(String.valueOf(y));
		regions = (ComboBox<String>) root.lookup("#regions");
		colorsList = (ListView<Rectangle>) root.lookup("#colorsList");
		addedColors = (ListView<Rectangle>) root.lookup("#addedColors");
		addColor = (Button) root.lookup("#addColor");
		removeColor = (Button) root.lookup("#removeColor");
		membersList = (ListView<String>) root.lookup("#membersList");
		addMember = (Button) root.lookup("#addMember");
		editMember = (Button) root.lookup("#editMember");
		deleteMember = (Button) root.lookup("#deleteMember");
		dir = (Label) root.lookup("#dir");
		browse = (Button) root.lookup("#browse");
		save = (Button) root.lookup("#save");
		cancel = (Button) root.lookup("#cancel");
		houseName = (TextField) root.lookup("#houseName");
		seat  = (TextField) root.lookup("#seat");
		words = (TextField) root.lookup("#words");
		xCoord = (TextField) root.lookup("#xCoord");
		xCoord = (TextField) root.lookup("#xCoord");
		
		InformationHandler.populateRegions(regions);
		InformationHandler.populateColors(colorsList);
		InformationHandler.colorsSelectionHandler(colorsList, addedColors, addColor);
		InformationHandler.colorsSelectionHandler(addedColors, colorsList,  removeColor);
		InformationHandler.membersListHandler(membersList, addMember,editMember, deleteMember);
		InformationHandler.browseHandler(browse, dir);
		InformationHandler.cancelHandler(cancel, true);
		InformationHandler.saveInformationHandler(save,houseName, seat, words,dir,addedColors, membersList);
		
		primaryStage.setScene(scene);
		primaryStage.initModality( Modality.APPLICATION_MODAL);
		primaryStage.setTitle("Add New House");
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image(NewHouse.class.getResourceAsStream("/Images/icon.png")));
		primaryStage.show();			
	}
	
}

