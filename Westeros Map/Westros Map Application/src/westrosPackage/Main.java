package westrosPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

/**
 * The main class for the application
 * @author Yahya Almardeny
 * @version 10/04/2017
 */
public class Main extends Application{
	static MenuBar mb;
	static Menu regionsMenu;
	static List<Menu> theRegions;
	static boolean dbConnection = true;
	static String selectedRegion = null;
	public static ImageView marker;
	
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		Image map = new Image(Main.class.getResourceAsStream("/Images/map.jpg"));
		Scene scene = new Scene(Regions.root, 600,700);
		primaryStage.setScene(scene);
		
		new Regions(primaryStage, map); //Initialize and create Regions
		
		Map.zoomHandler(primaryStage); // handle the zoom in and out of the map
			
		
		//Regions Coloring Handlers
		regionHandler(scene, Regions.gift, Regions.castleBlack, Color.BLACK, Color.BLACK,Color.WHITE, Color.BLACK);		
		regionHandler(scene, Regions.stormlands,Regions.stormEnd, Color.YELLOW, Color.YELLOW, Color.BLACK, Color.BLACK);		
		regionHandler(scene, Regions.reach, Regions.highgarden, Color.DARKGREEN, Color.DARKGREEN,Color.BLACK, Color.BLACK);	
		regionHandler(scene, Regions.north, Regions.winterfell, Color.LIGHTGRAY, Color.LIGHTGRAY,Color.BLACK, Color.GREEN);
		regionHandler(scene, Regions.westerlands, Regions.casterlyRock, Color.DARKRED, Color.DARKRED,Color.WHITE, Color.RED);
		regionHandler(scene, Regions.riverlands, Regions.twins, Color.DARKBLUE, Color.CYAN,Color.BLACK, Color.BLUE);
		regionHandler(scene, Regions.vale, Regions.eyrie, Color.SKYBLUE, Color.SKYBLUE,Color.BLACK, Color.BLUEVIOLET);
		regionHandler(scene,Regions.dorne, Regions.sunspear, Color.DARKORANGE, Color.DARKORANGE,Color.BLACK, Color.RED);
		ArrayList<SVGPath> regions = new ArrayList<SVGPath>();
		regions = new ArrayList<SVGPath>();
		Collections.addAll(regions, Regions.crownlands, Regions.crownlands1);
		regionHandler(scene, regions, Regions.dragonStone, Color.GREY, Color.CRIMSON,Color.BLACK, Color.MEDIUMVIOLETRED);
		regions = new ArrayList<SVGPath>();
		Collections.addAll(regions, Regions.ironIslands1, Regions.ironIslands2,
					Regions.ironIslands3,Regions.ironIslands4,Regions.ironIslands5,Regions.ironIslands6,Regions.ironIslands7);
		regionHandler(scene, regions, Regions.pyke, Color.GOLD, Color.GOLD,Color.WHITE, Color.GOLD);
		
		
		//Establish Connection to Database
		try{
			new Database ("root", "", "westerosdb", "localhost");
			populateHierarchicalMenu(Regions.root);
			initializeAddedHouses();
			mouseRightClickHandler(scene);
		}
		catch(Exception e){
			dbConnection = false;
		}

		primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/Images/icon.png")));
		primaryStage.setTitle("Westeros Interactive Map");
		primaryStage.show();
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * when close disconnect with database
	 */
	public void stop(){
		if(dbConnection)
			Database.close();
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	
	private void mouseRightClickHandler(Scene scene){
		scene.setOnMouseClicked(e->{
    		if (e.getButton() == MouseButton.SECONDARY) {
    			if(Map.moved){
	    			if(e.getX()>Map.mapCoords[0] && e.getY()>Map.mapCoords[1]
	    					&& e.getX()<Map.mapCoords[2] && e.getY()<Map.mapCoords[3]){
	    				newHouseDialog(e);
	    			}
    			}
    			else{newHouseDialog(e);}
    		}
    	});
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	private void regionHandler(Scene scene, ArrayList<SVGPath> regions, SVGPath seat, Color regionEnter, 
												Color regionEnterGlow, Color seatEnterGlow, Color seatExitGlow){
		for(SVGPath region: regions){
			region.setOnMouseEntered(e->{
				selectedRegion = new String(region.getId());
				Regions.colorRegion(region, regionEnter);
				Regions.setEffectToRegion(region, regionEnterGlow);
				Regions.setEffectToRegion(seat, seatEnterGlow);
				scene.setCursor(Cursor.DEFAULT);
				for(SVGPath region1: regions){
					Regions.colorRegion(region1, regionEnter);
					Regions.setEffectToRegion(region1, regionEnterGlow);
					Regions.setEffectToRegion(seat, seatEnterGlow);
					seat.toFront();
					scene.setCursor(Cursor.DEFAULT);
				}
			});
			region.setOnMouseExited(e->{
				Regions.colorRegion(region, Color.TRANSPARENT);
				Regions.setEffectToRegion(seat, seatExitGlow);
				if(Map.zoomed){
					scene.setCursor(Cursor.OPEN_HAND);
				}
				for(SVGPath region1: regions){
					Regions.colorRegion(region1, Color.TRANSPARENT);
					Regions.setEffectToRegion(seat, seatExitGlow);
					if(Map.zoomed){
						scene.setCursor(Cursor.OPEN_HAND);
					}
				}
			});
		}
		
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	private void regionHandler(Scene scene, SVGPath region, SVGPath seat, Color regionEnter, 
									Color regionEnterGlow,Color seatEnterGlow, Color seatExitGlow){
		region.setOnMouseEntered(e->{
			selectedRegion = new String(region.getId());
			Regions.colorRegion(region, regionEnter);
			Regions.setEffectToRegion(region, regionEnterGlow);
			Regions.setEffectToRegion(seat, seatEnterGlow);
			scene.setCursor(Cursor.DEFAULT);
		});
		
		region.setOnMouseExited(e->{
			Regions.colorRegion(region, Color.TRANSPARENT);
			Regions.setEffectToRegion(seat, seatExitGlow);
			if(Map.zoomed){
			scene.setCursor(Cursor.OPEN_HAND);
			}
		});
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public static void populateHierarchicalMenu(StackPane root){
		Pane topContainer = new Pane();
		try{root.getChildren().remove(topContainer);}catch(Exception e){}
		topContainer.setManaged(false);
		mb = new MenuBar();
		mb.setPrefWidth(root.getWidth());
		theRegions = new ArrayList<Menu>(Arrays.asList(addRegionMenu("The Gift"), addRegionMenu("The North"),
				 addRegionMenu("Riverlands"),  addRegionMenu("The Vale"),  addRegionMenu("Iron Islands"),
				 addRegionMenu("Westerlands"),  addRegionMenu("Crownlands"),  addRegionMenu("The Reach"),
				 addRegionMenu("Stormlands"),  addRegionMenu("Dorne")));
		addHousesToRegionMenu(theRegions);
		regionsMenu = new Menu("Regions");
		regionsMenu.getItems().addAll(theRegions);
		mb.getMenus().add(regionsMenu);
		topContainer.getChildren().add(mb);
		mb.setOnMouseEntered(e->{
		   mb.setCursor(Cursor.HAND);
		});
	    root.getChildren().add(topContainer); 
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	private static Menu addRegionMenu(String region){
		Menu r = new Menu(region);
		r.setId(region);
		return r;
	}

		
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	private static void addHousesToRegionMenu(List<Menu> theRegions){
		for(Menu region: theRegions){
			ResultSet houses = Database.read("SELECT name FROM `House` WHERE region =\""+ region.getId()+"\";");
			try {
				while(houses.next()){
					String houseName = houses.getString("name");
					Menu house = new Menu(houseName);
					house.setId(houseName);
					house.setOnAction(e->{
						new HouseInformation(houseName);
					});
					ResultSet members = Database.read("SELECT memberName FROM `Member` WHERE houseName =\""+ houseName+"\";");
					while(members.next()){
						String memberName = members.getString("memberName");
						MenuItem member = new MenuItem(memberName);
						member.setId(memberName);
						house.getItems().add(member);
					}
					region.getItems().add(house);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	private static void newHouseDialog(MouseEvent e){
		NewHouse.successfulAdd = false;
		marker = Regions.marker(new Image(Main.class.getResourceAsStream("/Images/mapMarker.png")), 
											        20, 25, e.getX()-5, e.getY()-10, "temp");
		Regions.root.getChildren().add(marker);
		final ContextMenu contextMenu = new ContextMenu();
		MenuItem newHouse = new MenuItem("New House");
		newHouse.setOnAction(a->{
			double x=0, y=0;
			double zoomRatioX = 600/Regions.imageview.getFitWidth();
			double zoomRatioY = 700/Regions.imageview.getFitHeight();
			if(Map.moved){
				x=(e.getX() - Map.mapCoords[0])*zoomRatioX;
				y=(e.getY() - Map.mapCoords[1])*zoomRatioY;
			}
			else{
				x=(e.getX()*zoomRatioX);
				y=(e.getY()*zoomRatioY);
			}
			new NewHouse(x-5,y-10);  
		});
		contextMenu.getItems().add(newHouse);
		contextMenu.show(Regions.root, e.getScreenX(), e.getScreenY());
		Regions.root.setOnMousePressed(p->{
			contextMenu.hide();
			if(!NewHouse.successfulAdd){
				Regions.root.getChildren().remove(marker);
			}
		});
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	private static void initializeAddedHouses(){
		ResultSet newHouses = Database.read("SELECT x,y,houseName,sigil FROM `Coordinates`"
				+ "JOIN `House` ON House.name = Coordinates.houseName;");
		
		try {
			while(newHouses.next()){
				double x = Double.valueOf(newHouses.getString("x"));
				double y = Double.valueOf(newHouses.getString("y"));
				String houseName = newHouses.getString("houseName");
				String sigil = newHouses.getString("sigil");
				ImageView marker1 = Regions.marker(new Image("file:"+sigil), 20.0, 25.0, x, y, houseName);
				marker1.toFront();
				Map.xOffsetMarker.add(0.0);
				Map.yOffsetMarker.add(0.0);
				Regions.root.getChildren().add(marker1);
				marker1.setOnMouseClicked(e->{
					if(e.getButton() == MouseButton.PRIMARY){
						new HouseInformation(marker1.getId());
					}
				});
				marker1.setCursor(Cursor.HAND);
				Regions.addedHousesMarkers.put(marker1, new Double[]{x,y});
				
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Could not load the Houses' Markers!");
			alert.showAndWait();
		}
	}

	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public static void main(String[] args){
		launch();
	}

}
