package westrosPackage;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

/**
 * This Class handles the information
 * for both classes (NewHouse and HouseInformation)
 * @author Yahya Almardeny
 * @version 16/04/2017
 */
public class InformationHandler {

 private static File source, dest;
 private static boolean changed = false;
 final private static File programFolder = new File(System.getProperty("user.home") + File.separator 
		 									+ "Westeros Map" + File.separator + "Images" + File.separator);
 
 	/**
 	 * to populate the regions in the comboBox
 	 * @param regions
 	 */
	public static void populateRegions(ComboBox<String> regions){
		regions.getItems().addAll("The North", "The Vale" , "Iron Islands" ,
						"Crownlands" , "Westerlands","The Reach" , "Stormlands" ,"Dorne", "Riverlands", "The Gift");
		if(Main.selectedRegion!=null){ // just a try to predict the region that will be chosen
			regions.setValue(Main.selectedRegion);
			Main.selectedRegion = null;
		}	
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * to populate the colors list in the colors listView
	 * @param colorsList
	 */
	public static void populateColors(ListView<Rectangle> colorsList){
		String [] colors = {"BLACK","RED", "BLUE", "GREEN", "BROWN", "WHITE", //available colors
							"LIME","GRAY","ORANGE","PURPLE","GOLD", "CRIMSON",
							"AQUA" ,"SILVER","DARKRED", "DARKGREEN","BEIGE",
							"DARKGRAY", "DARKBLUE", "DARKORANGE"};
		for(String color: colors){
			Rectangle rec = new Rectangle(1,20);
			rec.setFill(Color.valueOf(color));
			rec.setId(color);
			colorsList.getItems().add(rec);
			rec.widthProperty().bind(colorsList.widthProperty().subtract(29)); // fit it in the listVew Box
		}
		
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * to handle the selections from the colors listView
	 * and add it to the chosen colors listView
	 * @param source
	 * @param dest
	 * @param btn
	 */
	public static void colorsSelectionHandler(ListView<Rectangle> source , ListView<Rectangle> dest, Button btn){
		btn.setOnAction(e->{
			if(source.getItems().size()>0){
				Rectangle selectedColor = source.getSelectionModel().getSelectedItem();
				if(selectedColor!=null){
					dest.getItems().add(selectedColor);
					source.getItems().remove(selectedColor);
					source.getSelectionModel().clearSelection();
				}
			}
		});
	}
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * to handle the addition, editing and deletion of
	 * members in the listView of members
	 * @param membersList
	 * @param addMember
	 * @param editMember
	 * @param deleteMember
	 */
	public static void membersListHandler(ListView<String> membersList, Button addMember, Button editMember, Button deleteMember){
		addMember.setOnAction(e->{
			String name = textDialogHandler("Add Member", "", "Member Name", "");
			if(name!=null && !name.replaceAll(" ", "").isEmpty()){
				membersList.getItems().add(name);
				changed = true; // to allow the action on the save button
			}	
		});
		
		editMember.setOnAction(e->{
			String selectedName = membersList.getSelectionModel().getSelectedItem();
			if(selectedName!=null){
				String name = textDialogHandler("Edit Member", "","Member Name",selectedName);
				if(name!=null && !name.replaceAll(" ", "").isEmpty()){
					membersList.getItems().set(membersList.getItems().indexOf(selectedName), name);
					changed = true;
				}
			}
		});
		
		deleteMember.setOnAction(e->{
			String selectedName = membersList.getSelectionModel().getSelectedItem();
			if(selectedName!=null){
				membersList.getItems().remove(selectedName);
				changed = true;
			}
		});
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * dialog box for adding, editing and deleting members
	 * in the members listView
	 * @param title
	 * @param header
	 * @param content
	 * @param memberName
	 * @return
	 */
	private static String textDialogHandler(String title, String header, String content, String memberName){
		TextInputDialog dialog = new TextInputDialog(memberName);
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(content);
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    return result.get();
		}
		return null;
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * handle the information after clicking on save button
	 * particularly for NewHouse Class
	 * @param save
	 * @param houseName
	 * @param xCoordV
	 * @param yCoordV
	 * @param obj
	 */
	public static void saveInformationHandler(Button save,TextField houseName, TextField xCoordV, TextField yCoordV, Object...obj){
		save.setOnAction(e->{
			if(!validInformation(obj)){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Please Complete all fields");
				alert.showAndWait();
			}
			else if(repeatedPrimaryKey(houseName,xCoordV, yCoordV)){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("The House or the Location already exists, choose different one");
				alert.showAndWait();
			}
			else{
				String house = "\""+ NewHouse.houseName.getText() +"\",";
				String region = "\""+ NewHouse.regions.getSelectionModel().getSelectedItem() +"\",";
				String seat =  "\""+ NewHouse.seat.getText() +"\",";
			    String words =  "\""+ NewHouse.words.getText() +"\",";
				String sigil =  "\""+ dest.getAbsolutePath().replace("\\", "\\\\") +"\"";
		
				int houseTable=0, colorTable=0, memberTable=0, coordinatesTabel=0;
				String insertStatement = "INSERT INTO `House` VALUES (" + house + region + seat + words + sigil + ");";
				houseTable = Database.insert(insertStatement);
				for(String member : NewHouse.membersList.getItems()){
					insertStatement = "INSERT INTO `Member` (memberName, houseName) VALUES (\"" + member +"\"," 
										+ house.substring(0, house.length()-1) + ");";
					memberTable = Database.insert(insertStatement);
				}
			    String x =  "\""+ NewHouse.xCoord.getText() +"\",";
				String y =  "\""+ NewHouse.yCoord.getText() +"\",";			
				for(Rectangle color : NewHouse.addedColors.getItems()){
					insertStatement = "INSERT INTO `Color` VALUES (\"" + color.getId() +"\"," + house.substring(0, house.length()-1) + ");";
					colorTable = Database.insert(insertStatement);
				}
				insertStatement = "INSERT INTO `Coordinates` VALUES ("+x  + y +  house.substring(0, house.length()-1) + ");" ;
				coordinatesTabel = Database.insert(insertStatement );
				
				if(houseTable>0&&colorTable>0&&memberTable>0&&coordinatesTabel>0){
					double xCoord = Double.valueOf(NewHouse.xCoord.getText());
					double yCoord = Double.valueOf(NewHouse.yCoord.getText());
					Main.marker.setImage(new Image("file:"+dest.getAbsolutePath()));
					double w =20, h=25; 
					if(Regions.scene.getWidth()>600){w+=(20*(600/Regions.scene.getWidth()));}
					else if(Regions.scene.getWidth()<600){w-=(20*(Regions.scene.getWidth()/600));}
					if(Regions.scene.getHeight()>700){h+=(10*(700/Regions.scene.getHeight()));}
					else if(Regions.scene.getHeight()<700){h-=(10*(Regions.scene.getHeight()/700));}
					Main.marker.setFitWidth(w); Main.marker.setFitHeight(h);
					Main.marker.toFront();
					Main.marker.setId(house.substring(1, house.length()-2));
					if(Map.moved){
						Main.marker.translateXProperty().bind(Regions.imageview.fitWidthProperty().divide(600/xCoord).add(Map.mapCoords[0]));
						Main.marker.translateYProperty().bind(Regions.imageview.fitHeightProperty().divide(700/yCoord).add(Map.mapCoords[1]));
					}
					else if(Map.zoomed){
						Main.marker.translateXProperty().bind(Regions.imageview.fitWidthProperty().divide(600/xCoord));
						Main.marker.translateYProperty().bind(Regions.imageview.fitHeightProperty().divide(700/yCoord));
					}
					Regions.addedHousesMarkers.put(Main.marker, new Double[]{xCoord, yCoord});
					Main.marker.setOnMouseClicked(mc->{
						if(mc.getButton() == MouseButton.PRIMARY){
							new HouseInformation(Main.marker.getId());
						}
					});
					NewHouse.successfulAdd=true;
					Map.xOffsetMarker.add(0.0);
					Map.yOffsetMarker.add(0.0);
					Main.populateHierarchicalMenu(Regions.root);
					Main.marker.setCursor(Cursor.HAND);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.initModality(Modality.APPLICATION_MODAL);
					alert.setContentText("New House has been added successfully!");
					Optional<ButtonType> result = alert.showAndWait();
					if(result != null){NewHouse.primaryStage.close();}
				}
			}
		});
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * handle the information after clicking on save button
	 * particularly for HouseInformation Class
	 * @param save
	 * @param houseName
	 */
	public static void saveInformationHandler(Button save,Label houseName){
		save.setOnAction(e->{
			String deleteStatement = "DELETE FROM `Member` WHERE houseName=\"" + houseName.getText() + "\";";
			int deleteMemberTable = 0;
			deleteMemberTable = Database.delete(deleteStatement);
			int memberTable = 0;
			String insertStatement;
			if (HouseInformation.membersList.getItems().size()>0){
				for(String member : HouseInformation.membersList.getItems()){
					insertStatement = "INSERT INTO `Member` (memberName, houseName) VALUES (\"" + member +"\",\"" 
																					+ houseName.getText() + "\");";
					memberTable = Database.insert(insertStatement);
				}
			}
						
			if(memberTable>0 && deleteMemberTable>0 && changed){
				HouseInformation.successfulShow=true;
				Main.populateHierarchicalMenu(Regions.root);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.setContentText("House has been Updated Successfully!");
				Optional<ButtonType> result = alert.showAndWait();
				if(result != null){
					changed=false;
					HouseInformation.primaryStage.close();
				}
			}	
		});
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * fetch data from database and display it in the HouseInformation Class
	 * @param houseName
	 * @param words
	 * @param seat
	 * @param region
	 * @param xCoord
	 * @param yCoord
	 * @param sigil
	 * @param colorsContainer
	 */
	public static void populateHouseInfo(Label houseName, TextField words, TextField seat, TextField region, 
										TextField xCoord, TextField yCoord, ImageView sigil, VBox colorsContainer){
		
		String houseInfo = "SELECT * FROM `House` WHERE name=" + "\"" + houseName.getText() + "\";";
		String membersInfo = "SELECT memberName FROM `Member` WHERE houseName=" + "\"" + houseName.getText() + "\";";
		String colorsInfo = "SELECT color FROM `Color` WHERE houseName=" + "\"" + houseName.getText() + "\";";
		String coordinatesInfo = "SELECT x , y FROM `Coordinates` WHERE houseName=" + "\"" + houseName.getText() + "\";";
		
		ResultSet hInfo = Database.read(houseInfo);
		ResultSet mInfo = Database.read(membersInfo);
		ResultSet cInfo = Database.read(colorsInfo);
		ResultSet coInfo = Database.read(coordinatesInfo);
		try {
			while(hInfo.next()){
				region.setText(hInfo.getString("region"));
				seat.setText(hInfo.getString("seat"));
				words.setText(hInfo.getString("motto"));
				String dir = hInfo.getString("sigil");
				if(dir.startsWith("/Images/")){
					sigil.setImage(new Image(InformationHandler.class.getResourceAsStream(dir)));
				}
				else{
					sigil.setImage(new Image("file:" + dir));
				}
				
			}
			while(mInfo.next()){
				HouseInformation.originalMembersList.add(mInfo.getString("memberName"));
			}
			HouseInformation.membersList.getItems().addAll(HouseInformation.originalMembersList);
			while(cInfo.next()){
				Rectangle color = new Rectangle(80,20);
				color.setFill(Color.valueOf(cInfo.getString("color")));
				color.setEffect(new DropShadow(BlurType.GAUSSIAN , Color.WHITE , 1, 1 , 0 , 0 ));
				colorsContainer.getChildren().add(color);
				VBox.setMargin(color, new Insets(6,0,0,0));
			}
			if(coInfo.getFetchSize()>0){
				while(coInfo.next()){
					xCoord.setText(coInfo.getString("x"));
					yCoord.setText(coInfo.getString("y"));
				}
			}
			else{
				xCoord.setText(String.valueOf(Regions.xCoordSeat));
				yCoord.setText(String.valueOf(Regions.yCoordSeat));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * handle the browse image process
	 * @param browse
	 * @param dir
	 */
	public static void browseHandler(Button browse, Label dir){
		browse.setOnAction(e->{
			FileChooser fileChooser = new FileChooser();
		    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image","*.jpg", "*.png","*.gif","*.jpeg");
		    fileChooser.getExtensionFilters().add(extFilter);
		    source = fileChooser.showOpenDialog(browse.getScene().getWindow());
		    if(source!=null){
		    	dir.setText(source.getPath().substring(0, 30));
		    }
		});
		
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * check for repeated primary keys in the database comming from
	 * the NewHouse Class and inserted by the user
	 * @param houseName
	 * @param xCoord
	 * @param yCoord
	 * @return boolean
	 */
	public static boolean repeatedPrimaryKey(TextField houseName, TextField xCoord, TextField yCoord){
		ResultSet houses = Database.read("SELECT name FROM `House`");
		ResultSet coordinates = Database.read("SELECT x,y FROM `Coordinates`");
		try {
			while(houses.next()){
				String name = houses.getString("name");
				if(name.equalsIgnoreCase(houseName.getText())){
					return true;
				}
			}
			while(coordinates.next()){
				String x = coordinates.getString("x");
				String y = coordinates.getString("y");
				if(x.equalsIgnoreCase(xCoord.getText()) && y.equalsIgnoreCase(yCoord.getText())){
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * check if the user filled all fields on screen and
	 * chose the sigil image for the NewHouse Class
	 * @param obj
	 * @return
	 */
	public static boolean validInformation(Object...obj){
		boolean valid=true;
		for(Object o : obj){
			if(o instanceof String){
				if(((String) o).isEmpty()){
					valid=false;
				}
			}
			else if(o instanceof ListView){
				if(((ListView<?>) o).getItems().size()==0){
					valid=false;
				}
			}	
		}
		try {
			if(!programFolder.exists()){programFolder.mkdirs();}
			dest = new File(System.getProperty("user.home")+File.separator+"Westeros Map"+
									File.separator+"Images"+File.separator +source.getName());
			
			Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
		} catch (Exception io) {
			valid = false;
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Could not load the image!");
			alert.showAndWait();
		}
		return valid;
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * handle clicking on cancel button
	 * @param cancel
	 * @param newHouse
	 */
	 public static void cancelHandler(Button cancel, boolean newHouse){
		 if(newHouse){
			 cancel.setOnAction(e->{
				 NewHouse.primaryStage.close();
				 Regions.root.getChildren().remove(Main.marker);
			 });
			 NewHouse.primaryStage.setOnCloseRequest(e->{
				 Regions.root.getChildren().remove(Main.marker);
			 });
	 	}
	 	else{
	 		cancel.setOnAction(e->{
				 HouseInformation.primaryStage.close();
			 });
	 	}
	 }
		
	 
		
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	 
		
	 /**
	  * to run/start slide show from HouseInformation Class
	  * @param runSlideshow
	  */
	 public static void runSlideShowHandler(Button runSlideshow){
		 runSlideshow.setOnAction(e->{
			 if(SlideShow.primaryStage==null){
				 new SlideShow();
			 }
		 });
	 }
	 
		
		
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	 
		
	 /**
	  * to stop/exit slide show from HouseInformation Class
	  * @param stopSlideshow
	  */
	 public static void stopSlideShowHandler(Button stopSlideshow){
		 stopSlideshow.setOnAction(e->{
			 if(SlideShow.primaryStage!=null){
				 SlideShow.primaryStage.close();
				 SlideShow.primaryStage=null;
			 }
		 });
	 }

	 
}
