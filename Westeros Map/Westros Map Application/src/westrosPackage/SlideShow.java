package westrosPackage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.animation.Animation.Status;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SlideShow {
	@FXML
	private BorderPane root;
	@FXML
	private static StackPane topContainer, sigilContainer;
	@FXML
	private ImageView previous, next;
	@FXML
	private static CheckBox autoCB;
	
	public static Stage primaryStage = null;
	private static ArrayList<VBox> topComponents;
	private static ArrayList<ImageView> sigils;
	public static int currentSlide=0, totalNoOfHouses=0;
	final private Duration duration = new Duration(3000); // time between slides
	private static ParallelTransition slideShow;
	
	
	/**
	 * constructor of class SlideShow
	 */
	public SlideShow(){
		primaryStage = new Stage();
		topComponents = new ArrayList<VBox>();
		sigils = new ArrayList<ImageView>();
		currentSlide=0; totalNoOfHouses=0;
		slideShow = null;
		try {
			 root = FXMLLoader.load(SlideShow.class.getResource("/slideshow.fxml"));
		} catch (IOException e) {e.printStackTrace();}
		root.setStyle("-fx-background-color:  linear-gradient(#686868 0%, #232723 25%, #232723 75%, #232723 100%),"
																				+"linear-gradient(#020b02, #3a3a3a)"); // color background 
		topContainer = (StackPane) root.lookup("#topContainer");
		sigilContainer = (StackPane) root.lookup("#sigilContainer");
		previous = (ImageView) root.lookup("#previous");
		next = (ImageView) root.lookup("#next");
		autoCB = (CheckBox) root.lookup("#autoCB");
		Scene scene = new Scene(root, 700,600);
		primaryStage.setScene(scene);
		primaryStage.setMinHeight(600);
		primaryStage.setMinWidth(700);
		primaryStage.setTitle("SlideShow");
		primaryStage.getIcons().add(new Image(SlideShow.class.getResourceAsStream("/Images/icon.png")));
	
		primaryStage.setOnCloseRequest(e->{
			primaryStage = null;
		});
		
		nextSlideHandler();
		previousSlideHandler();
		populateSlidesInfo();
		createSlideShow();
		auto();
		
		primaryStage.show();

	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * to go to and handle the go to next slide 
	 */
	public void nextSlideHandler(){
		next.setImage(new Image(SlideShow.class.getResourceAsStream("/Images/next.jpg")));
		next.setCursor(Cursor.HAND);
		next.setOnMouseClicked(e->{
			currentSlide();
			if(currentSlide==totalNoOfHouses-1){
				   topComponents.get(currentSlide).setOpacity(0.0);
				   sigils.get(currentSlide).setOpacity(0.0);
				   currentSlide=0;
				   topComponents.get(currentSlide).setOpacity(1.0);
				   sigils.get(currentSlide).setOpacity(1.0);  
			}
			else{
				topComponents.get(currentSlide).setOpacity(0.0);
				sigils.get(currentSlide).setOpacity(0.0);
				currentSlide++;
				topComponents.get(currentSlide).setOpacity(1.0);
				sigils.get(currentSlide).setOpacity(1.0);
			}
			
		});		
	}

	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * to go and handle the go to previous slide
	 */
	public void previousSlideHandler(){
		previous.setImage(new Image(SlideShow.class.getResourceAsStream("/Images/previous.jpg")));
		previous.setCursor(Cursor.HAND);
		previous.setOnMouseClicked(e->{
			currentSlide();
			if(currentSlide==0){
				   topComponents.get(currentSlide).setOpacity(0.0);
				   sigils.get(currentSlide).setOpacity(0.0);
				   currentSlide=totalNoOfHouses-1;
				   topComponents.get(currentSlide).setOpacity(1.0);
				   sigils.get(currentSlide).setOpacity(1.0);  
			}
			else{
				topComponents.get(currentSlide).setOpacity(0.0);
				sigils.get(currentSlide).setOpacity(0.0);
				currentSlide--;
				topComponents.get(currentSlide).setOpacity(1.0);
				sigils.get(currentSlide).setOpacity(1.0);
			}
		});
		
	}
	


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/*
	 * stop slide show and get the current slide
	 */
	public static void currentSlide(){
		if(slideShow.getStatus().equals(Status.RUNNING)){
			slideShow.stop();	
			autoCB.setSelected(false);
			int i=0;
			for(Node component: topContainer.getChildren()){
				if(component instanceof VBox){
					if(component.getOpacity()>0.0){
						currentSlide=i;
						break;
					}
				}
				i++;
			}
		}
	}
	


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * to start and handle the auto slide show
	 */
	public void auto(){
		autoCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if(arg1){ // from true to false (untick check box)
					currentSlide();
				}
				else if(arg2){ //from false to true (tick check box / select)
					if(slideShow.getStatus().equals(Status.RUNNING)){
						slideShow.stop();
					}
					for(int i=0; i<totalNoOfHouses;i++){
						topComponents.get(i).setOpacity(0.0);
						sigils.get(i).setOpacity(0.0);
					}
					slideShow.play();}
			}
		});
	}



	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * fetch the info from database and fill them in the scene components
	 * then hide the components
	 */
	public static void populateSlidesInfo(){

		String selectQuery = "SELECT name, motto, sigil FROM `House`;";
		ResultSet info = Database.read(selectQuery);
		try {
			while(info.next()){
				String name = info.getString("name");
				String words = info.getString("motto");
				VBox topComponent = new VBox();
				Label houseNameLabel = new Label();
				houseNameLabel.setFont(Font.font("Aharoni",30));
				houseNameLabel.setTextFill(Color.BROWN);
				houseNameLabel.setEffect(new DropShadow(BlurType.GAUSSIAN , Color.WHITE , 5, 5 , 0 , 0 ));
				houseNameLabel.setText(name);
				Label motto = new Label();
				motto.setFont(Font.font("Aharoni",20));
				motto.setTextFill(Color.WHITE);
				motto.setEffect(new DropShadow(BlurType.GAUSSIAN , Color.BLACK , 1, 1 , 0 , 0 ));
				motto.setText(words);
				topComponent.getChildren().addAll(houseNameLabel,motto);
				VBox.setMargin(houseNameLabel, new Insets(15,10,0,0));
				VBox.setMargin(motto, new Insets(15));
				topComponent.setAlignment(Pos.CENTER);
				topComponent.setOpacity(0);
				topComponents.add(topComponent);
				String sigil =info.getString("sigil");
				ImageView sigilImg = new ImageView();
				if (sigil.startsWith("/Images/")){sigilImg.setImage(new Image(SlideShow.class.getResourceAsStream(sigil)));}
				else{sigilImg.setImage(new Image("file:"+sigil));}
				sigilImg.setOpacity(0);
				sigils.add(sigilImg);
				sigilImg.setFitWidth(350);
				sigilImg.setFitHeight(400);
			}
			
			topContainer.getChildren().addAll(topComponents);
			sigilContainer.getChildren().addAll(sigils);
			totalNoOfHouses=topComponents.size();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * create slide show which consists of two sequential transitions,
	 * (one for topComponents (house name and motto) and the second for the
	 * sigils), inside a parallel transition
	 */
	public void createSlideShow(){
		slideShow = new ParallelTransition();
		slideShow.getChildren().addAll(
        		componentTransition(sigils),
        		componentTransition(topComponents)
        );
		slideShow.setCycleCount(Timeline.INDEFINITE);
		slideShow.play();
	}
	


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * create a return a sequential transition
	 * @param target
	 * @return slideshow
	 */
	public SequentialTransition componentTransition(ArrayList<?> target){
		SequentialTransition slideshow = new SequentialTransition();
		if(target.get(0) instanceof ImageView){
			for (ImageView sigil : sigils) {
		        SequentialTransition sequentialTransition = new SequentialTransition();
		        FadeTransition fadeIn = getFadeTransition(sigil, 0.0, 1.0, 2000);
		        PauseTransition stayOn = new PauseTransition(duration);
		        FadeTransition fadeOut = getFadeTransition(sigil, 1.0, 0.0, 2000);
		        sequentialTransition.getChildren().addAll(fadeIn, stayOn, fadeOut);
		        slideshow.getChildren().add(sequentialTransition);

		      }
		}
		else if (target.get(0) instanceof VBox){
			for (VBox vb : topComponents) {
		        SequentialTransition sequentialTransition = new SequentialTransition();
		        FadeTransition fadeIn = getFadeTransition(vb, 0.0, 1.0, 2000);
		        PauseTransition stayOn = new PauseTransition(duration);
		        FadeTransition fadeOut = getFadeTransition(vb, 1.0, 0.0, 2000);
		        sequentialTransition.getChildren().addAll(fadeIn, stayOn, fadeOut);
		        slideshow.getChildren().add(sequentialTransition);

		      }
		}
	       
		return slideshow;	
	}


	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * to create a get a custom fade transition (fade in or out)
	 * @param component
	 * @param fromValue
	 * @param toValue
	 * @param durationInMilliseconds
	 * @return ft
	 */
	private FadeTransition getFadeTransition(Object component, double fromValue, double toValue, int durationInMilliseconds) {
		FadeTransition fadeTransition = null;
		if (component instanceof ImageView){
			fadeTransition = new FadeTransition(Duration.millis(durationInMilliseconds), (ImageView)component);
		}
		else if (component instanceof VBox){
			fadeTransition = new FadeTransition(Duration.millis(durationInMilliseconds), (VBox)component);
		}
		fadeTransition.setFromValue(fromValue);
		fadeTransition.setToValue(toValue);
	
	    return fadeTransition;
	}

	
}
