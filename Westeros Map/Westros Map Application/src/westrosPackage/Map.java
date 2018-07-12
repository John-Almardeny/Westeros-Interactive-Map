package westrosPackage;

import java.util.ArrayList;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * This class to control the map and maintain it
 * basically the zoom in and out operations 
 * @author Yahya Almardeny
 * @version 10/04/2017
 */
public class Map{
	static double zoomRatio= 100, xOffset=0, yOffset=0, imgWidth=0, imgHeight=0, xOffsetR0=0, yOffsetR0=0,xOffsetR1=0, yOffsetR1=0,
				  xOffsetR2=0, yOffsetR2=0, xOffsetR3=0, yOffsetR3=0, xOffsetR4=0, yOffsetR4=0, xOffsetR5=0, yOffsetR5=0, xOffsetR6=0,
				  yOffsetR6=0, xOffsetR7=0, yOffsetR7=0, xOffsetR8=0, yOffsetR8=0, xOffsetR9=0, yOffsetR9=0, xOffsetR10=0, yOffsetR10=0, 
				  xOffsetR11=0, yOffsetR11=0,xOffsetR12=0, yOffsetR12=0, xOffsetR13=0, yOffsetR13=0, xOffsetR14=0, yOffsetR14=0, xOffsetR15=0, 
				  yOffsetR15=0, xOffsetR16=0, yOffsetR16=0,	xOffsetR17=0, yOffsetR17=0, xOffsetR18=0, yOffsetR18=0, xOffsetR19=0, yOffsetR19=0, 
				  xOffsetR20=0, yOffsetR20=0, xOffsetR21=0, yOffsetR21=0,xOffsetR22=0, yOffsetR22=0, xOffsetR23=0, yOffsetR23=0, xOffsetR24=0, 
				  yOffsetR24=0, xOffsetR25=0, yOffsetR25=0, xOffsetR26=0, yOffsetR26=0,xOffsetR27=0, yOffsetR27=0,
				  deltaX0=0.0, deltaY0=0.0, deltaX1=0.0, deltaY1=0.0,deltaX2=0.0, deltaY2=0.0,deltaX3=0.0, deltaY3=0.0,deltaX4=0.0, deltaY4=0.0
			      ,deltaX5=0.0, deltaY5=0.0,deltaX6=0.0, deltaY6=0.0,deltaX7=0.0, deltaY7=0.0,deltaX8=0.0, deltaY8=0.0,deltaX9=0.0, deltaY9=0.0
			     ,deltaX10=0.0, deltaY10=0.0,deltaX11=0.0, deltaY11=0.0,deltaX12=0.0, deltaY12=0.0,deltaX13=0.0, deltaY13=0.0,deltaX14=0.0, deltaY14=0.0,
				  deltaX15=0.0, deltaY15=0.0,deltaX16=0.0, deltaY16=0.0,deltaX17=0.0, deltaY17=0.0,deltaX18=0.0, deltaY18=0.0,deltaX19=0.0, deltaY19=0.0
				  ,deltaX20=0.0, deltaY20=0.0,deltaX21=0.0, deltaY21=0.0,deltaX22=0.0, deltaY22=0.0,deltaX23=0.0, deltaY23=0.0,deltaX24=0.0, deltaY24=0.0
				  ,deltaX25=0.0, deltaY25=0.0,deltaX26=0.0, deltaY26=0.0,deltaX27=0.0, deltaY27=0.0;
	static ArrayList<Double> xOffsetMarker = new ArrayList<Double>();
	static ArrayList<Double> yOffsetMarker = new ArrayList<Double>();
	static VBox zoomCtrl;
	static boolean moved=false, zoomed=false;
	static double[] mapCoords = new double[4];

	
	
	/**
	 * to make the map zoommable and maintain it  
	 * @param primaryStage
	 */
    public static void zoomHandler(Stage primaryStage){
    	zoomCtrl = new VBox();
    	zoomCtrl.setPrefSize(30, 40);
    	zoomCtrl.setManaged(false);
    	Button zoomIn = zoomBtn("+", zoomCtrl);
    	Button zoomOut = zoomBtn("-", zoomCtrl);
    	zoomCtrl.getChildren().addAll(zoomIn,zoomOut);
    	    
        Regions.root.getChildren().add(zoomCtrl);
        Regions.root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        
        Regions.imageview.setFitHeight(primaryStage.getScene().getHeight());
        Regions.imageview.setFitWidth(primaryStage.getScene().getWidth());
        imgWidth = Regions.imageview.getFitWidth();
        imgHeight = Regions.imageview.getFitHeight();
        zoomCtrl.setTranslateX(primaryStage.getScene().getWidth()-35);
        zoomCtrl.setTranslateY(primaryStage.getScene().getHeight()-70);	
        zoomOut.setDisable(true);
        
        
        zoomInHandler(primaryStage, Regions.imageview, zoomIn, zoomOut);
        zoomOutHandler(primaryStage, Regions.imageview, zoomIn, zoomOut);
        
    	  	
    	primaryStage.getScene().widthProperty().addListener(e->{
    		zoomCtrl.setTranslateX(primaryStage.getScene().getWidth()-35);
            Regions.imageview.setFitWidth(primaryStage.getScene().getWidth());
            imgWidth = Regions.imageview.getFitWidth();
            Regions.imageview.setX(0.0);
    	});
    	
    	primaryStage.getScene().heightProperty().addListener(e->{
    		 zoomCtrl.setTranslateY(primaryStage.getScene().getHeight()-65);
    		 Regions.imageview.setFitHeight(primaryStage.getScene().getHeight());
    		 imgHeight = Regions.imageview.getFitHeight();
    	});
    	
    	
    	Regions.imageview.fitWidthProperty().addListener(e->{
    		if(Regions.imageview.getFitWidth()==primaryStage.getScene().getWidth()){
    			Regions.imageview.setX(0.0);
    			primaryStage.getScene().setCursor(Cursor.DEFAULT);
    			for(ImageView marker : Regions.addedHousesMarkers.keySet()){
    				double x = Regions.addedHousesMarkers.get(marker)[0];
    				marker.translateXProperty().bind(Regions.scene.widthProperty().divide(600/x));
				}
    		}
    		else{
    			if(moved){
    				for(ImageView marker : Regions.addedHousesMarkers.keySet()){
        				double x = Regions.addedHousesMarkers.get(marker)[0];
        				marker.translateXProperty().bind(Regions.imageview.fitWidthProperty().divide(600/x).add(mapCoords[0]));
    				}
    			}
    			else{
    				for(ImageView marker : Regions.addedHousesMarkers.keySet()){
    				double x = Regions.addedHousesMarkers.get(marker)[0];
    				marker.translateXProperty().bind(Regions.imageview.fitWidthProperty().divide(600/x));
    				}
    			}
    		}
    	});
    	
    	Regions.imageview.fitHeightProperty().addListener(e->{
    		if(Regions.imageview.getFitHeight()==primaryStage.getScene().getHeight()){
    		   Regions.imageview.setY(0.0);
    		   primaryStage.getScene().setCursor(Cursor.DEFAULT);
    		   for(ImageView marker : Regions.addedHousesMarkers.keySet()){
	   				double y = Regions.addedHousesMarkers.get(marker)[1];
	   				marker.translateYProperty().bind(Regions.scene.heightProperty().divide(700/y));
   			}
    		}
    		else{
    			if(moved){
    				for(ImageView marker : Regions.addedHousesMarkers.keySet()){
	    				double y = Regions.addedHousesMarkers.get(marker)[1];
	    				marker.translateYProperty().bind(Regions.imageview.fitHeightProperty().divide(700/y).add(mapCoords[1]));
	    			}
    			}
    			else{
	    			for(ImageView marker : Regions.addedHousesMarkers.keySet()){
	    				double y = Regions.addedHousesMarkers.get(marker)[1];
	    				marker.translateYProperty().bind(Regions.imageview.fitHeightProperty().divide(700/y));
	    			}
    			}
    		}
    	});
    	
        moveImage(primaryStage, Regions.imageview);
        bindRegionsToMap(primaryStage, Regions.imageview);
        fillSeatsWithSigils();
	

    }
    
    
   
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
   
    /**
     * to move the map
     * @param primaryStage
     * @param img
     */
    static private void moveImage(Stage primaryStage, ImageView img){
    	primaryStage.getScene().setOnMousePressed(event -> {
    		if (img.getFitWidth()>primaryStage.getScene().getWidth()||
    			img.getFitHeight()>primaryStage.getScene().getHeight()){
    			int i=0;
    			for(ImageView marker : Regions.addedHousesMarkers.keySet()){
    				xOffsetMarker.set(i,marker.getTranslateX() - event.getX());
        			yOffsetMarker.set(i,marker.getTranslateY() - event.getY());
        			i++;
    			}
    				xOffset = (img.getX() - event.getX());
				    yOffset = (img.getY() - event.getY());
				    xOffsetR0 = (Regions.gift.getTranslateX() - event.getX());
				    yOffsetR0 = (Regions.gift.getTranslateY() - event.getY());
				    xOffsetR1 = (Regions.castleBlack.getTranslateX() - event.getX());
				    yOffsetR1 = (Regions.castleBlack.getTranslateY() - event.getY());
				    xOffsetR2 = (Regions.north.getTranslateX() - event.getX());
				    yOffsetR2 = (Regions.north.getTranslateY() - event.getY());
				    xOffsetR3 = (Regions.winterfell.getTranslateX() - event.getX());
				    yOffsetR3 = (Regions.winterfell.getTranslateY() - event.getY());
				    xOffsetR4 = (Regions.riverlands.getTranslateX() - event.getX());
				    yOffsetR4 = (Regions.riverlands.getTranslateY() - event.getY());
				    xOffsetR5 = (Regions.twins.getTranslateX() - event.getX());
				    yOffsetR5 = (Regions.twins.getTranslateY() - event.getY());
				    xOffsetR6 = (Regions.vale.getTranslateX() - event.getX());
				    yOffsetR6 = (Regions.vale.getTranslateY() - event.getY());
				    xOffsetR7 = (Regions.eyrie.getTranslateX() - event.getX());
				    yOffsetR7 = (Regions.eyrie.getTranslateY() - event.getY());
				    xOffsetR8 = (Regions.westerlands.getTranslateX() - event.getX());
				    yOffsetR8 = (Regions.westerlands.getTranslateY() - event.getY());
				    xOffsetR9 = (Regions.casterlyRock.getTranslateX() - event.getX());
				    yOffsetR9 = (Regions.casterlyRock.getTranslateY() - event.getY());
				    xOffsetR10 = (Regions.dragonStone.getTranslateX() - event.getX());
				    yOffsetR10= (Regions.dragonStone.getTranslateY() - event.getY());
				    xOffsetR11 = (Regions.reach.getTranslateX() - event.getX());
				    yOffsetR11 = (Regions.reach.getTranslateY() - event.getY());
				    xOffsetR12 = (Regions.highgarden.getTranslateX() - event.getX());
				    yOffsetR12 = (Regions.highgarden.getTranslateY() - event.getY());
				    xOffsetR13 = (Regions.crownlands.getTranslateX() - event.getX());
				    yOffsetR13 = (Regions.crownlands.getTranslateY() - event.getY());
				    xOffsetR14 = (Regions.crownlands1.getTranslateX() - event.getX());
				    yOffsetR14 = (Regions.crownlands1.getTranslateY() - event.getY());
				    xOffsetR15= (Regions.stormlands.getTranslateX() - event.getX());
				    yOffsetR15 = (Regions.stormlands.getTranslateY() - event.getY());
				    xOffsetR16 = (Regions.stormEnd.getTranslateX() - event.getX());
				    yOffsetR16 = (Regions.stormEnd.getTranslateY() - event.getY());
				    xOffsetR17 = (Regions.dorne.getTranslateX() - event.getX());
				    yOffsetR17 = (Regions.dorne.getTranslateY() - event.getY());
				    xOffsetR18 = (Regions.sunspear.getTranslateX() - event.getX());
				    yOffsetR18= (Regions.sunspear.getTranslateY() - event.getY());
				    xOffsetR19 = (Regions.harrenhal.getTranslateX() - event.getX());
				    yOffsetR19 = (Regions.harrenhal.getTranslateY() - event.getY());
				    xOffsetR20 = (Regions.ironIslands1.getTranslateX() - event.getX());
				    yOffsetR20 = (Regions.ironIslands1.getTranslateY() - event.getY());
				    xOffsetR21 = (Regions.ironIslands2.getTranslateX() - event.getX());
				    yOffsetR21 = (Regions.ironIslands2.getTranslateY() - event.getY());
				    xOffsetR22 = (Regions.ironIslands3.getTranslateX() - event.getX());
				    yOffsetR22 = (Regions.ironIslands3.getTranslateY() - event.getY());
				    xOffsetR23 = (Regions.ironIslands4.getTranslateX() - event.getX());
				    yOffsetR23 = (Regions.ironIslands4.getTranslateY() - event.getY());
				    xOffsetR24 = (Regions.ironIslands5.getTranslateX() - event.getX());
				    yOffsetR24 = (Regions.ironIslands5.getTranslateY() - event.getY());
				    xOffsetR25 = (Regions.ironIslands6.getTranslateX() - event.getX());
				    yOffsetR25 = (Regions.ironIslands6.getTranslateY() - event.getY());
				    xOffsetR26 = (Regions.ironIslands7.getTranslateX() - event.getX());
				    yOffsetR26 = (Regions.ironIslands7.getTranslateY() - event.getY());
				    xOffsetR27 = (Regions.pyke.getTranslateX() - event.getX());
				    yOffsetR27 = (Regions.pyke.getTranslateY() - event.getY());
				    primaryStage.getScene().setCursor(Cursor.OPEN_HAND);
    		 }
    	 });
    
    	primaryStage.getScene().setOnMouseDragged(event -> {
   		  	if (img.getFitWidth()>primaryStage.getScene().getWidth()||
   		  		img.getFitHeight()>primaryStage.getScene().getHeight()){
			   		img.setX(event.getX() + xOffset);
			   		img.setY(event.getY() + yOffset);
			   		Regions.gift.setTranslateX( event.getX() + xOffsetR0);
			   		Regions.gift.setTranslateY( event.getY() + yOffsetR0);
			   		Regions.castleBlack.setTranslateX( event.getX() + xOffsetR1);
			   		Regions.castleBlack.setTranslateY( event.getY() + yOffsetR1);
			   		Regions.north.setTranslateX( event.getX() + xOffsetR2);
			   		Regions.north.setTranslateY( event.getY() + yOffsetR2);
			   		Regions.winterfell.setTranslateX( event.getX() + xOffsetR3);
			   		Regions.winterfell.setTranslateY( event.getY() + yOffsetR3);
			   		Regions.riverlands.setTranslateX( event.getX() + xOffsetR4);
			   		Regions.riverlands.setTranslateY( event.getY() + yOffsetR4);
			   		Regions.twins.setTranslateX( event.getX() + xOffsetR5);
			   		Regions.twins.setTranslateY( event.getY() + yOffsetR5);
			   		Regions.vale.setTranslateX( event.getX() + xOffsetR6);
			   		Regions.vale.setTranslateY( event.getY() + yOffsetR6);
			   		Regions.eyrie.setTranslateX( event.getX() + xOffsetR7);
			   		Regions.eyrie.setTranslateY( event.getY() + yOffsetR7);
			   		Regions.westerlands.setTranslateX( event.getX() + xOffsetR8);
			   		Regions.westerlands.setTranslateY( event.getY() + yOffsetR8);
			   		Regions.casterlyRock.setTranslateX( event.getX() + xOffsetR9);
			   		Regions.casterlyRock.setTranslateY( event.getY() + yOffsetR9);
			   		Regions.dragonStone.setTranslateX( event.getX() + xOffsetR10);
			   		Regions.dragonStone.setTranslateY( event.getY() + yOffsetR10);
			   		Regions.reach.setTranslateX( event.getX() + xOffsetR11);
			   		Regions.reach.setTranslateY( event.getY() + yOffsetR11);
			   		Regions.highgarden.setTranslateX( event.getX() + xOffsetR12);
			   		Regions.highgarden.setTranslateY( event.getY() + yOffsetR12);
			   		Regions.crownlands.setTranslateX( event.getX() + xOffsetR13);
			   		Regions.crownlands.setTranslateY( event.getY() + yOffsetR13);
			   		Regions.crownlands1.setTranslateX( event.getX() + xOffsetR14);
			   		Regions.crownlands1.setTranslateY( event.getY() + yOffsetR14);
			   		Regions.stormlands.setTranslateX( event.getX() + xOffsetR15);
			   		Regions.stormlands.setTranslateY( event.getY() + yOffsetR15);
			   		Regions.stormEnd.setTranslateX( event.getX() + xOffsetR16);
			   		Regions.stormEnd.setTranslateY( event.getY() + yOffsetR16);
			   		Regions.dorne.setTranslateX( event.getX() + xOffsetR17);
			   		Regions.dorne.setTranslateY( event.getY() + yOffsetR17);
			   		Regions.sunspear.setTranslateX( event.getX() + xOffsetR18);
			   		Regions.sunspear.setTranslateY( event.getY() + yOffsetR18);
			   		Regions.harrenhal.setTranslateX( event.getX() + xOffsetR19);
			   		Regions.harrenhal.setTranslateY( event.getY() + yOffsetR19);
			   		Regions.ironIslands1.setTranslateX( event.getX() + xOffsetR20);
			   		Regions.ironIslands1.setTranslateY( event.getY() + yOffsetR20);
			   		Regions.ironIslands2.setTranslateX( event.getX() + xOffsetR21);
			   		Regions.ironIslands2.setTranslateY( event.getY() + yOffsetR21);
			   		Regions.ironIslands3.setTranslateX( event.getX() + xOffsetR22);
			   		Regions.ironIslands3.setTranslateY( event.getY() + yOffsetR22);
			   		Regions.ironIslands4.setTranslateX( event.getX() + xOffsetR23);
			   		Regions.ironIslands4.setTranslateY( event.getY() + yOffsetR23);
			   		Regions.ironIslands5.setTranslateX( event.getX() + xOffsetR24);
			   		Regions.ironIslands5.setTranslateY( event.getY() + yOffsetR24);
			   		Regions.ironIslands6.setTranslateX( event.getX() + xOffsetR25);
			   		Regions.ironIslands6.setTranslateY( event.getY() + yOffsetR25);
			   		Regions.ironIslands7.setTranslateX( event.getX() + xOffsetR26);
			   		Regions.ironIslands7.setTranslateY( event.getY() + yOffsetR26);
			   		Regions.pyke.setTranslateX( event.getX() + xOffsetR27);
			   		Regions.pyke.setTranslateY( event.getY() + yOffsetR27);
			        primaryStage.getScene().setCursor(Cursor.CLOSED_HAND);
			        mapCoords[0] = img.getX();
			        mapCoords[1] = img.getY();
			        mapCoords[2] = img.getFitWidth()+ img.getX();
			        mapCoords[3] = img.getFitHeight()+ img.getY();
			        int i=0;
			        	for(ImageView marker : Regions.addedHousesMarkers.keySet()){
			        	marker.translateXProperty().unbind();
			        	marker.translateYProperty().unbind();
			        	marker.setTranslateX(event.getX() + xOffsetMarker.get(i));
			        	marker.setTranslateY(event.getY() + yOffsetMarker.get(i));
			        	i++;
	    			}
			        moved=true;
    	  	} 
       });
	}
		   
	
   
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    
    /**
     * to create Zoom Controller Buttons
     * @param text
     * @param parent
     * @return btn
     */
    static private Button zoomBtn(String text, Region parent){
    	Button btn = new Button(text);
    	btn.setFocusTraversable(false);
    	btn.setCursor(Cursor.HAND);
    	btn.setMinWidth(parent.getPrefWidth());
    	return btn;
    }
 
   
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
      
    /**
     * zoom in handler
     * @param primaryStage
     * @param imageView
     * @param zoomIn
     * @param zoomOut
     */
    static private void zoomInHandler(Stage primaryStage, ImageView imageView, Button zoomIn, Button zoomOut){
    	zoomIn.setOnAction(e->{
            if(imgWidth<2000&&imgHeight<1500){
           	primaryStage.setResizable(false);
       		imageView.setFitWidth(imgWidth +=zoomRatio);
               imageView.setFitHeight(imgHeight += zoomRatio);
               zoomOut.setDisable(false);
               zoomed=true;
               if(moved){
	               Regions.gift.setTranslateX(((deltaX0+=Regions.moveX0)*zoomRatio)+Regions.deltaX0+imageView.getX());
	               Regions.gift.setTranslateY(((deltaY0+=Regions.moveY0)*zoomRatio)+Regions.deltaY0+ imageView.getY());
	               Regions.castleBlack.setTranslateX(((deltaX1+=Regions.moveX1)*zoomRatio)+Regions.deltaX1+imageView.getX());
                   Regions.castleBlack.setTranslateY(((deltaY1+=Regions.moveY1)*zoomRatio)+Regions.deltaY1+ imageView.getY());
                   Regions.north.setTranslateX(((deltaX2+=Regions.moveX2)*zoomRatio)+Regions.deltaX2+imageView.getX());
                   Regions.north.setTranslateY(((deltaY2+=Regions.moveY2)*zoomRatio)+Regions.deltaY2+ imageView.getY());  
                   Regions.winterfell.setTranslateX((deltaX3+=Regions.moveX3)*zoomRatio+Regions.deltaX3+imageView.getX());
                   Regions.winterfell.setTranslateY((deltaY3+=Regions.moveY3)*zoomRatio+Regions.deltaY3+ imageView.getY());            
                   Regions.riverlands.setTranslateX((deltaX4+=Regions.moveX4)*zoomRatio+Regions.deltaX4+imageView.getX());
                   Regions.riverlands.setTranslateY((deltaY4+=Regions.moveY4)*zoomRatio+Regions.deltaY4+ imageView.getY());          
                   Regions.twins.setTranslateX((deltaX5+=Regions.moveX5)*zoomRatio+Regions.deltaX5+imageView.getX());
                   Regions.twins.setTranslateY((deltaY5+=Regions.moveY5)*zoomRatio+Regions.deltaY5+ imageView.getY());           
                   Regions.vale.setTranslateX((deltaX6+=Regions.moveX6)*zoomRatio+Regions.deltaX6+imageView.getX());
                   Regions.vale.setTranslateY((deltaY6+=Regions.moveY6)*zoomRatio+Regions.deltaY6+ imageView.getY());          
                   Regions.eyrie.setTranslateX((deltaX7+=Regions.moveX7)*zoomRatio+Regions.deltaX7+imageView.getX());
                   Regions.eyrie.setTranslateY((deltaY7+=Regions.moveY7)*zoomRatio+Regions.deltaY7+ imageView.getY());         
                   Regions.westerlands.setTranslateX((deltaX8+=Regions.moveX8)*zoomRatio+Regions.deltaX8+imageView.getX());
                   Regions.westerlands.setTranslateY((deltaY8+=Regions.moveY8)*zoomRatio+Regions.deltaY8+ imageView.getY());            
                   Regions.casterlyRock.setTranslateX((deltaX9+=Regions.moveX9)*zoomRatio+Regions.deltaX9+imageView.getX());
                   Regions.casterlyRock.setTranslateY((deltaY9+=Regions.moveY9)*zoomRatio+Regions.deltaY9+ imageView.getY());            
                   Regions.dragonStone.setTranslateX((deltaX10+=Regions.moveX10)*zoomRatio+Regions.deltaX10+imageView.getX());
                   Regions.dragonStone.setTranslateY((deltaY10+=Regions.moveY10)*zoomRatio+Regions.deltaY10+ imageView.getY());           
                   Regions.reach.setTranslateX((deltaX11+=Regions.moveX11)*zoomRatio+Regions.deltaX11+imageView.getX());
                   Regions.reach.setTranslateY((deltaY11+=Regions.moveY11)*zoomRatio+Regions.deltaY11+ imageView.getY());            
                   Regions.highgarden.setTranslateX((deltaX12+=Regions.moveX12)*zoomRatio+Regions.deltaX12+imageView.getX());
                   Regions.highgarden.setTranslateY((deltaY12+=Regions.moveY12)*zoomRatio+Regions.deltaY12+ imageView.getY());           
                   Regions.crownlands.setTranslateX((deltaX13+=Regions.moveX13)*zoomRatio+Regions.deltaX13+imageView.getX());
                   Regions.crownlands.setTranslateY((deltaY13+=Regions.moveY13)*zoomRatio+Regions.deltaY13+ imageView.getY());            
                   Regions.crownlands1.setTranslateX((deltaX14+=Regions.moveX14)*zoomRatio+Regions.deltaX14+imageView.getX());
                   Regions.crownlands1.setTranslateY((deltaY14+=Regions.moveY14)*zoomRatio+Regions.deltaY14+ imageView.getY());           
                   Regions.stormlands.setTranslateX((deltaX15+=Regions.moveX15)*zoomRatio+Regions.deltaX15+imageView.getX());
                   Regions.stormlands.setTranslateY((deltaY15+=Regions.moveY15)*zoomRatio+Regions.deltaY15+ imageView.getY());           
                   Regions.stormEnd.setTranslateX((deltaX16+=Regions.moveX16)*zoomRatio+Regions.deltaX16+imageView.getX());
                   Regions.stormEnd.setTranslateY((deltaY16+=Regions.moveY16)*zoomRatio+Regions.deltaY16+ imageView.getY());            
                   Regions.dorne.setTranslateX((deltaX17+=Regions.moveX17)*zoomRatio+Regions.deltaX17+imageView.getX());
                   Regions.dorne.setTranslateY((deltaY17+=Regions.moveY17)*zoomRatio+Regions.deltaY17+ imageView.getY());            
                   Regions.sunspear.setTranslateX((deltaX18+=Regions.moveX18)*zoomRatio+Regions.deltaX18+imageView.getX());
                   Regions.sunspear.setTranslateY((deltaY18+=Regions.moveY18)*zoomRatio+Regions.deltaY18+ imageView.getY());            
                   Regions.harrenhal.setTranslateX((deltaX19+=Regions.moveX19)*zoomRatio+Regions.deltaX19+imageView.getX());
                   Regions.harrenhal.setTranslateY((deltaY19+=Regions.moveY19)*zoomRatio+Regions.deltaY19+ imageView.getY());            
                   Regions.ironIslands1.setTranslateX((deltaX20+=Regions.moveX20)*zoomRatio+Regions.deltaX20+imageView.getX());
                   Regions.ironIslands1.setTranslateY((deltaY20+=Regions.moveY20)*zoomRatio+Regions.deltaY20+ imageView.getY());           
                   Regions.ironIslands2.setTranslateX((deltaX21+=Regions.moveX21)*zoomRatio+Regions.deltaX21+imageView.getX());
                   Regions.ironIslands2.setTranslateY((deltaY21+=Regions.moveY21)*zoomRatio+Regions.deltaY21+ imageView.getY());          
                   Regions.ironIslands3.setTranslateX((deltaX22+=Regions.moveX22)*zoomRatio+Regions.deltaX22+imageView.getX());
                   Regions.ironIslands3.setTranslateY((deltaY22+=Regions.moveY22)*zoomRatio+Regions.deltaY22+ imageView.getY());           
                   Regions.ironIslands4.setTranslateX((deltaX23+=Regions.moveX23)*zoomRatio+Regions.deltaX23+imageView.getX());
                   Regions.ironIslands4.setTranslateY((deltaY23+=Regions.moveY23)*zoomRatio+Regions.deltaY23+ imageView.getY());           
                   Regions.ironIslands5.setTranslateX((deltaX24+=Regions.moveX24)*zoomRatio+Regions.deltaX24+imageView.getX());
                   Regions.ironIslands5.setTranslateY((deltaY24+=Regions.moveY24)*zoomRatio+Regions.deltaY24+ imageView.getY());            
                   Regions.ironIslands6.setTranslateX((deltaX25+=Regions.moveX25)*zoomRatio+Regions.deltaX25+imageView.getX());
                   Regions.ironIslands6.setTranslateY((deltaY25+=Regions.moveY25)*zoomRatio+Regions.deltaY25+ imageView.getY());         
                   Regions.ironIslands7.setTranslateX((deltaX26+=Regions.moveX26)*zoomRatio+Regions.deltaX26+imageView.getX());
                   Regions.ironIslands7.setTranslateY((deltaY26+=Regions.moveY26)*zoomRatio+Regions.deltaY26+ imageView.getY());        
                   Regions.pyke.setTranslateX((deltaX27+=Regions.moveX27)*zoomRatio+Regions.deltaX27+imageView.getX());
                   Regions.pyke.setTranslateY((deltaY27+=Regions.moveY27)*zoomRatio+Regions.deltaY27+ imageView.getY());
                 
                   
               }
               else{
   	            Regions.gift.setTranslateX(((deltaX0+=Regions.moveX0)*zoomRatio+Regions.deltaX0));
   	            Regions.gift.setTranslateY(((deltaY0+=Regions.moveY0)*zoomRatio+Regions.deltaY0));
   	            Regions.castleBlack.setTranslateX((deltaX1+=Regions.moveX1)*zoomRatio+Regions.deltaX1);
   	            Regions.castleBlack.setTranslateY((deltaY1+=Regions.moveY1)*zoomRatio+Regions.deltaY1);
   	            Regions.north.setTranslateX((deltaX2+=Regions.moveX2)*zoomRatio+Regions.deltaX2);
   	            Regions.north.setTranslateY((deltaY2+=Regions.moveY2)*zoomRatio+Regions.deltaY2); 
   	            Regions.winterfell.setTranslateX((deltaX3+=Regions.moveX3)*zoomRatio+Regions.deltaX3);
   	            Regions.winterfell.setTranslateY((deltaY3+=Regions.moveY3)*zoomRatio+Regions.deltaY3);            
   	            Regions.riverlands.setTranslateX((deltaX4+=Regions.moveX4)*zoomRatio+Regions.deltaX4);
   	            Regions.riverlands.setTranslateY((deltaY4+=Regions.moveY4)*zoomRatio+Regions.deltaY4);          
   	            Regions.twins.setTranslateX((deltaX5+=Regions.moveX5)*zoomRatio+Regions.deltaX5);
   	            Regions.twins.setTranslateY((deltaY5+=Regions.moveY5)*zoomRatio+Regions.deltaY5);           
   	            Regions.vale.setTranslateX((deltaX6+=Regions.moveX6)*zoomRatio+Regions.deltaX6);
   	            Regions.vale.setTranslateY((deltaY6+=Regions.moveY6)*zoomRatio+Regions.deltaY6);          
   	            Regions.eyrie.setTranslateX((deltaX7+=Regions.moveX7)*zoomRatio+Regions.deltaX7);
   	            Regions.eyrie.setTranslateY((deltaY7+=Regions.moveY7)*zoomRatio+Regions.deltaY7);         
   	            Regions.westerlands.setTranslateX((deltaX8+=Regions.moveX8)*zoomRatio+Regions.deltaX8);
   	            Regions.westerlands.setTranslateY((deltaY8+=Regions.moveY8)*zoomRatio+Regions.deltaY8);            
   	            Regions.casterlyRock.setTranslateX((deltaX9+=Regions.moveX9)*zoomRatio+Regions.deltaX9);
   	            Regions.casterlyRock.setTranslateY((deltaY9+=Regions.moveY9)*zoomRatio+Regions.deltaY9);            
   	            Regions.dragonStone.setTranslateX((deltaX10+=Regions.moveX10)*zoomRatio+Regions.deltaX10);
   	            Regions.dragonStone.setTranslateY((deltaY10+=Regions.moveY10)*zoomRatio+Regions.deltaY10);           
   	            Regions.reach.setTranslateX((deltaX11+=Regions.moveX11)*zoomRatio+Regions.deltaX11);
   	            Regions.reach.setTranslateY((deltaY11+=Regions.moveY11)*zoomRatio+Regions.deltaY11);            
   	            Regions.highgarden.setTranslateX((deltaX12+=Regions.moveX12)*zoomRatio+Regions.deltaX12);
   	            Regions.highgarden.setTranslateY((deltaY12+=Regions.moveY12)*zoomRatio+Regions.deltaY12);           
   	            Regions.crownlands.setTranslateX((deltaX13+=Regions.moveX13)*zoomRatio+Regions.deltaX13);
   	            Regions.crownlands.setTranslateY((deltaY13+=Regions.moveY13)*zoomRatio+Regions.deltaY13);            
   	            Regions.crownlands1.setTranslateX((deltaX14+=Regions.moveX14)*zoomRatio+Regions.deltaX14);
   	            Regions.crownlands1.setTranslateY((deltaY14+=Regions.moveY14)*zoomRatio+Regions.deltaY14);           
   	            Regions.stormlands.setTranslateX((deltaX15+=Regions.moveX15)*zoomRatio+Regions.deltaX15);
   	            Regions.stormlands.setTranslateY((deltaY15+=Regions.moveY15)*zoomRatio+Regions.deltaY15);           
   	            Regions.stormEnd.setTranslateX((deltaX16+=Regions.moveX16)*zoomRatio+Regions.deltaX16);
   	            Regions.stormEnd.setTranslateY((deltaY16+=Regions.moveY16)*zoomRatio+Regions.deltaY16);            
   	            Regions.dorne.setTranslateX((deltaX17+=Regions.moveX17)*zoomRatio+Regions.deltaX17);
   	            Regions.dorne.setTranslateY((deltaY17+=Regions.moveY17)*zoomRatio+Regions.deltaY17);            
   	            Regions.sunspear.setTranslateX((deltaX18+=Regions.moveX18)*zoomRatio+Regions.deltaX18);
   	            Regions.sunspear.setTranslateY((deltaY18+=Regions.moveY18)*zoomRatio+Regions.deltaY18);            
   	            Regions.harrenhal.setTranslateX((deltaX19+=Regions.moveX19)*zoomRatio+Regions.deltaX19);
   	            Regions.harrenhal.setTranslateY((deltaY19+=Regions.moveY19)*zoomRatio+Regions.deltaY19);            
   	            Regions.ironIslands1.setTranslateX((deltaX20+=Regions.moveX20)*zoomRatio+Regions.deltaX20);
   	            Regions.ironIslands1.setTranslateY((deltaY20+=Regions.moveY20)*zoomRatio+Regions.deltaY20);           
   	            Regions.ironIslands2.setTranslateX((deltaX21+=Regions.moveX21)*zoomRatio+Regions.deltaX21);
   	            Regions.ironIslands2.setTranslateY((deltaY21+=Regions.moveY21)*zoomRatio+Regions.deltaY21);          
   	            Regions.ironIslands3.setTranslateX((deltaX22+=Regions.moveX22)*zoomRatio+Regions.deltaX22);
   	            Regions.ironIslands3.setTranslateY((deltaY22+=Regions.moveY22)*zoomRatio+Regions.deltaY22);           
   	            Regions.ironIslands4.setTranslateX((deltaX23+=Regions.moveX23)*zoomRatio+Regions.deltaX23);
   	            Regions.ironIslands4.setTranslateY((deltaY23+=Regions.moveY23)*zoomRatio+Regions.deltaY23);           
   	            Regions.ironIslands5.setTranslateX((deltaX24+=Regions.moveX24)*zoomRatio+Regions.deltaX24);
   	            Regions.ironIslands5.setTranslateY((deltaY24+=Regions.moveY24)*zoomRatio+Regions.deltaY24);            
   	            Regions.ironIslands6.setTranslateX((deltaX25+=Regions.moveX25)*zoomRatio+Regions.deltaX25);
   	            Regions.ironIslands6.setTranslateY((deltaY25+=Regions.moveY25)*zoomRatio+Regions.deltaY25);         
   	            Regions.ironIslands7.setTranslateX((deltaX26+=Regions.moveX26)*zoomRatio+Regions.deltaX26);
   	            Regions.ironIslands7.setTranslateY((deltaY26+=Regions.moveY26)*zoomRatio+Regions.deltaY26);        
   	            Regions.pyke.setTranslateX((deltaX27+=Regions.moveX27)*zoomRatio+Regions.deltaX27);
   	            Regions.pyke.setTranslateY((deltaY27+=Regions.moveY27)*zoomRatio+Regions.deltaY27);
               }  
            }
            else{zoomIn.setDisable(true);}
       	});
       	
    }
    
   
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    /**
     *  zoom out handler
     * @param primaryStage
     * @param imageView
     * @param zoomIn
     * @param zoomOut
     */
    static private void zoomOutHandler(Stage primaryStage, ImageView imageView, Button zoomIn, Button zoomOut){
    	zoomOut.setOnAction(e->{
    		if (imgWidth>primaryStage.getScene().getWidth() || imgHeight>primaryStage.getScene().getHeight()){
    			 imageView.setFitWidth(imgWidth -= zoomRatio);
    			 imageView.setFitHeight(imgHeight -= zoomRatio);
    			 zoomIn.setDisable(false);
    			 if(moved){
    				Regions.gift.setTranslateX(((deltaX0-=Regions.moveX0)*zoomRatio)+Regions.deltaX0 +imageView.getX());
    				Regions.castleBlack.setTranslateX(((deltaX1-=Regions.moveX1)*zoomRatio)+Regions.deltaX1 +imageView.getX());
	    	        Regions.north.setTranslateX((deltaX2-=Regions.moveX2)*zoomRatio+Regions.deltaX2 +imageView.getX());
	    	        Regions.winterfell.setTranslateX((deltaX3-=Regions.moveX3)*zoomRatio+Regions.deltaX3+imageView.getX());
	    	        Regions.riverlands.setTranslateX((deltaX4-=Regions.moveX4)*zoomRatio+Regions.deltaX4+imageView.getX());
	    	        Regions.twins.setTranslateX((deltaX5-=Regions.moveX5)*zoomRatio+Regions.deltaX5+imageView.getX());
	    	        Regions.vale.setTranslateX((deltaX6-=Regions.moveX6)*zoomRatio+Regions.deltaX6+imageView.getX());
	    	        Regions.eyrie.setTranslateX((deltaX7-=Regions.moveX7)*zoomRatio+Regions.deltaX7+imageView.getX());
	    	        Regions.westerlands.setTranslateX((deltaX8-=Regions.moveX8)*zoomRatio+Regions.deltaX8+imageView.getX());
	    	        Regions.casterlyRock.setTranslateX((deltaX9-=Regions.moveX9)*zoomRatio+Regions.deltaX9+imageView.getX());
	    	        Regions.dragonStone.setTranslateX((deltaX10-=Regions.moveX10)*zoomRatio+Regions.deltaX10+imageView.getX());
	    	        Regions.reach.setTranslateX((deltaX11-=Regions.moveX11)*zoomRatio+Regions.deltaX11+imageView.getX());
	    	        Regions.highgarden.setTranslateX((deltaX12-=Regions.moveX12)*zoomRatio+Regions.deltaX12+imageView.getX());
	    	        Regions.crownlands.setTranslateX((deltaX13-=Regions.moveX13)*zoomRatio+Regions.deltaX13+imageView.getX());
	    	        Regions.crownlands1.setTranslateX((deltaX14-=Regions.moveX14)*zoomRatio+Regions.deltaX14+imageView.getX());
	    	        Regions.stormlands.setTranslateX((deltaX15-=Regions.moveX15)*zoomRatio+Regions.deltaX15+imageView.getX());
	    	        Regions.stormEnd.setTranslateX((deltaX16-=Regions.moveX16)*zoomRatio+Regions.deltaX16+imageView.getX());
	    	        Regions.dorne.setTranslateX((deltaX17-=Regions.moveX17)*zoomRatio+Regions.deltaX17+imageView.getX());
	    	        Regions.sunspear.setTranslateX((deltaX18-=Regions.moveX18)*zoomRatio+Regions.deltaX18+imageView.getX());
	    	        Regions.harrenhal.setTranslateX((deltaX19-=Regions.moveX19)*zoomRatio+Regions.deltaX19+imageView.getX());
	    	        Regions.ironIslands1.setTranslateX((deltaX20-=Regions.moveX20)*zoomRatio+Regions.deltaX20+imageView.getX());
	    	        Regions.ironIslands2.setTranslateX((deltaX21-=Regions.moveX21)*zoomRatio+Regions.deltaX21+imageView.getX());
	    	        Regions.ironIslands3.setTranslateX((deltaX22-=Regions.moveX22)*zoomRatio+Regions.deltaX22+imageView.getX());
	    	        Regions.ironIslands4.setTranslateX((deltaX23-=Regions.moveX23)*zoomRatio+Regions.deltaX23+imageView.getX());
	    	        Regions.ironIslands5.setTranslateX((deltaX24-=Regions.moveX24)*zoomRatio+Regions.deltaX24+imageView.getX());
	    	        Regions.ironIslands6.setTranslateX((deltaX25-=Regions.moveX25)*zoomRatio+Regions.deltaX25+imageView.getX());
	    	        Regions.ironIslands7.setTranslateX((deltaX26-=Regions.moveX26)*zoomRatio+Regions.deltaX26+imageView.getX());
	    	        Regions.pyke.setTranslateX((deltaX27-=Regions.moveX27)*zoomRatio+Regions.deltaX27+imageView.getX());
	    	        Regions.gift.setTranslateY(((deltaY0-=Regions.moveY0)*zoomRatio)+Regions.deltaY0+imageView.getY());
	    			Regions.castleBlack.setTranslateY(((deltaY1-=Regions.moveY1)*zoomRatio)+Regions.deltaY1+imageView.getY());
	    			Regions.north.setTranslateY((deltaY2-=Regions.moveY2)*zoomRatio+Regions.deltaY2+imageView.getY());
	    			Regions.winterfell.setTranslateY((deltaY3-=Regions.moveY3)*zoomRatio+Regions.deltaY3+imageView.getY());
	    			Regions.riverlands.setTranslateY((deltaY4-=Regions.moveY4)*zoomRatio+Regions.deltaY4+imageView.getY());
	   				Regions.twins.setTranslateY((deltaY5-=Regions.moveY5)*zoomRatio+Regions.deltaY5+imageView.getY());
	   				Regions.vale.setTranslateY((deltaY6-=Regions.moveY6)*zoomRatio+Regions.deltaY6+imageView.getY());
	   				Regions.eyrie.setTranslateY((deltaY7-=Regions.moveY7)*zoomRatio+Regions.deltaY7+imageView.getY());
	   				Regions.westerlands.setTranslateY((deltaY8-=Regions.moveY8)*zoomRatio+Regions.deltaY8+imageView.getY());
	   				Regions.casterlyRock.setTranslateY((deltaY9-=Regions.moveY9)*zoomRatio+Regions.deltaY9+imageView.getY());
	    			Regions.dragonStone.setTranslateY((deltaY10-=Regions.moveY10)*zoomRatio+Regions.deltaY10+imageView.getY());
	    			Regions.reach.setTranslateY((deltaY11-=Regions.moveY11)*zoomRatio+Regions.deltaY11+imageView.getY());
	    			Regions.highgarden.setTranslateY((deltaY12-=Regions.moveY12)*zoomRatio+Regions.deltaY12+imageView.getY());
	   				Regions.crownlands.setTranslateY((deltaY13-=Regions.moveY13)*zoomRatio+Regions.deltaY13+imageView.getY());
	   				Regions.crownlands1.setTranslateY((deltaY14-=Regions.moveY14)*zoomRatio+Regions.deltaY14+imageView.getY());
	   				Regions.stormlands.setTranslateY((deltaY15-=Regions.moveY15)*zoomRatio+Regions.deltaY15+imageView.getY());
	   				Regions.stormEnd.setTranslateY((deltaY16-=Regions.moveY16)*zoomRatio+Regions.deltaY16+imageView.getY());
	   				Regions.dorne.setTranslateY((deltaY17-=Regions.moveY17)*zoomRatio+Regions.deltaY17+imageView.getY());
	    			Regions.sunspear.setTranslateY((deltaY18-=Regions.moveY18)*zoomRatio+Regions.deltaY18+imageView.getY());
	    			Regions.harrenhal.setTranslateY((deltaY19-=Regions.moveY19)*zoomRatio+Regions.deltaY19+imageView.getY());
	    			Regions.ironIslands1.setTranslateY((deltaY20-=Regions.moveY20)*zoomRatio+Regions.deltaY20+imageView.getY());
	   				Regions.ironIslands2.setTranslateY((deltaY21-=Regions.moveY21)*zoomRatio+Regions.deltaY21+imageView.getY());
	   				Regions.ironIslands3.setTranslateY((deltaY22-=Regions.moveY22)*zoomRatio+Regions.deltaY22+imageView.getY());
	   				Regions.ironIslands4.setTranslateY((deltaY23-=Regions.moveY23)*zoomRatio+Regions.deltaY23+imageView.getY());
	   				Regions.ironIslands5.setTranslateY((deltaY24-=Regions.moveY24)*zoomRatio+Regions.deltaY24+imageView.getY());
	    			Regions.ironIslands6.setTranslateY((deltaY25-=Regions.moveY25)*zoomRatio+Regions.deltaY25+imageView.getY());
	    			Regions.ironIslands7.setTranslateY((deltaY26-=Regions.moveY26)*zoomRatio+Regions.deltaY26+imageView.getY());
	    			Regions.pyke.setTranslateY((deltaY27-=Regions.moveY27)*zoomRatio+Regions.deltaY27+imageView.getY()); 
    			 }
    			 else{
    				 Regions.gift.setTranslateX(((deltaX0-=Regions.moveX0)*zoomRatio)+Regions.deltaX0);
	    			 Regions.castleBlack.setTranslateX(((deltaX1-=Regions.moveX1)*zoomRatio)+Regions.deltaX1);
	    	         Regions.north.setTranslateX((deltaX2-=Regions.moveX2)*zoomRatio+Regions.deltaX2);
	    	         Regions.winterfell.setTranslateX((deltaX3-=Regions.moveX3)*zoomRatio+Regions.deltaX3);
	    	         Regions.riverlands.setTranslateX((deltaX4-=Regions.moveX4)*zoomRatio+Regions.deltaX4);
	    	         Regions.twins.setTranslateX((deltaX5-=Regions.moveX5)*zoomRatio+Regions.deltaX5);
	    	         Regions.vale.setTranslateX((deltaX6-=Regions.moveX6)*zoomRatio+Regions.deltaX6);
	    	         Regions.eyrie.setTranslateX((deltaX7-=Regions.moveX7)*zoomRatio+Regions.deltaX7);
	    	         Regions.westerlands.setTranslateX((deltaX8-=Regions.moveX8)*zoomRatio+Regions.deltaX8);
	    	         Regions.casterlyRock.setTranslateX((deltaX9-=Regions.moveX9)*zoomRatio+Regions.deltaX9);
	    	         Regions.dragonStone.setTranslateX((deltaX10-=Regions.moveX10)*zoomRatio+Regions.deltaX10);
	    	         Regions.reach.setTranslateX((deltaX11-=Regions.moveX11)*zoomRatio+Regions.deltaX11);
	    	         Regions.highgarden.setTranslateX((deltaX12-=Regions.moveX12)*zoomRatio+Regions.deltaX12);
	    	         Regions.crownlands.setTranslateX((deltaX13-=Regions.moveX13)*zoomRatio+Regions.deltaX13);
	    	         Regions.crownlands1.setTranslateX((deltaX14-=Regions.moveX14)*zoomRatio+Regions.deltaX14);
	    	         Regions.stormlands.setTranslateX((deltaX15-=Regions.moveX15)*zoomRatio+Regions.deltaX15);
	    	         Regions.stormEnd.setTranslateX((deltaX16-=Regions.moveX16)*zoomRatio+Regions.deltaX16);
	    	         Regions.dorne.setTranslateX((deltaX17-=Regions.moveX17)*zoomRatio+Regions.deltaX17);
	    	         Regions.sunspear.setTranslateX((deltaX18-=Regions.moveX18)*zoomRatio+Regions.deltaX18);
	    	         Regions.harrenhal.setTranslateX((deltaX19-=Regions.moveX19)*zoomRatio+Regions.deltaX19);
	    	         Regions.ironIslands1.setTranslateX((deltaX20-=Regions.moveX20)*zoomRatio+Regions.deltaX20);
	    	         Regions.ironIslands2.setTranslateX((deltaX21-=Regions.moveX21)*zoomRatio+Regions.deltaX21);
	    	         Regions.ironIslands3.setTranslateX((deltaX22-=Regions.moveX22)*zoomRatio+Regions.deltaX22);
	    	         Regions.ironIslands4.setTranslateX((deltaX23-=Regions.moveX23)*zoomRatio+Regions.deltaX23);
	    	         Regions.ironIslands5.setTranslateX((deltaX24-=Regions.moveX24)*zoomRatio+Regions.deltaX24);
	    	         Regions.ironIslands6.setTranslateX((deltaX25-=Regions.moveX25)*zoomRatio+Regions.deltaX25);
	    	         Regions.ironIslands7.setTranslateX((deltaX26-=Regions.moveX26)*zoomRatio+Regions.deltaX26);
	    	         Regions.pyke.setTranslateX((deltaX27-=Regions.moveX27)*zoomRatio+Regions.deltaX27);
	    	        Regions.gift.setTranslateY(((deltaY0-=Regions.moveY0)*zoomRatio)+Regions.deltaY0+yOffsetR0);
	    			Regions.castleBlack.setTranslateY(((deltaY1-=Regions.moveY1)*zoomRatio)+Regions.deltaY1);
	    			Regions.north.setTranslateY((deltaY2-=Regions.moveY2)*zoomRatio+Regions.deltaY2);
	    			Regions.winterfell.setTranslateY((deltaY3-=Regions.moveY3)*zoomRatio+Regions.deltaY3);
	    			Regions.riverlands.setTranslateY((deltaY4-=Regions.moveY4)*zoomRatio+Regions.deltaY4);
	   				Regions.twins.setTranslateY((deltaY5-=Regions.moveY5)*zoomRatio+Regions.deltaY5);
	   				Regions.vale.setTranslateY((deltaY6-=Regions.moveY6)*zoomRatio+Regions.deltaY6);
	   				Regions.eyrie.setTranslateY((deltaY7-=Regions.moveY7)*zoomRatio+Regions.deltaY7);
	   				Regions.westerlands.setTranslateY((deltaY8-=Regions.moveY8)*zoomRatio+Regions.deltaY8);
	   				Regions.casterlyRock.setTranslateY((deltaY9-=Regions.moveY9)*zoomRatio+Regions.deltaY9);
	    			Regions.dragonStone.setTranslateY((deltaY10-=Regions.moveY10)*zoomRatio+Regions.deltaY10);
	    			Regions.reach.setTranslateY((deltaY11-=Regions.moveY11)*zoomRatio+Regions.deltaY11);
	    			Regions.highgarden.setTranslateY((deltaY12-=Regions.moveY12)*zoomRatio+Regions.deltaY12);
	   				Regions.crownlands.setTranslateY((deltaY13-=Regions.moveY13)*zoomRatio+Regions.deltaY13);
	   				Regions.crownlands1.setTranslateY((deltaY14-=Regions.moveY14)*zoomRatio+Regions.deltaY14);
	   				Regions.stormlands.setTranslateY((deltaY15-=Regions.moveY15)*zoomRatio+Regions.deltaY15);
	   				Regions.stormEnd.setTranslateY((deltaY16-=Regions.moveY16)*zoomRatio+Regions.deltaY16);
	   				Regions.dorne.setTranslateY((deltaY17-=Regions.moveY17)*zoomRatio+Regions.deltaY17);
	    			Regions.sunspear.setTranslateY((deltaY18-=Regions.moveY18)*zoomRatio+Regions.deltaY18);
	    			Regions.harrenhal.setTranslateY((deltaY19-=Regions.moveY19)*zoomRatio+Regions.deltaY19);
	    			Regions.ironIslands1.setTranslateY((deltaY20-=Regions.moveY20)*zoomRatio+Regions.deltaY20);
	   				Regions.ironIslands2.setTranslateY((deltaY21-=Regions.moveY21)*zoomRatio+Regions.deltaY21);
	   				Regions.ironIslands3.setTranslateY((deltaY22-=Regions.moveY22)*zoomRatio+Regions.deltaY22);
	   				Regions.ironIslands4.setTranslateY((deltaY23-=Regions.moveY23)*zoomRatio+Regions.deltaY23);
	   				Regions.ironIslands5.setTranslateY((deltaY24-=Regions.moveY24)*zoomRatio+Regions.deltaY24);
	    			Regions.ironIslands6.setTranslateY((deltaY25-=Regions.moveY25)*zoomRatio+Regions.deltaY25);
	    			Regions.ironIslands7.setTranslateY((deltaY26-=Regions.moveY26)*zoomRatio+Regions.deltaY26);
	    			Regions.pyke.setTranslateY((deltaY27-=Regions.moveY27)*zoomRatio+Regions.deltaY27); 
    			 }
    		}
    		else{zoomOut.setDisable(true); moved=false; zoomed=false; primaryStage.setResizable(true); }
    	});
    }
    
   
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    

    static private void bindRegionsToMap(Stage primaryStage, ImageView imageView){
    	Regions.gift.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()));
    	Regions.gift.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()));
    	Regions.castleBlack.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()).add(1.0));
    	Regions.castleBlack.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()).add(0.5));
    	Regions.north.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()));
    	Regions.north.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()));
    	Regions.winterfell.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()).add(1.0));
    	Regions.winterfell.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()).add(0.5));
    	Regions.riverlands.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()));
    	Regions.riverlands.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()));
    	Regions.twins.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()).add(1.0));
    	Regions.twins.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()).add(0.5));
    	Regions.vale.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()));
    	Regions.vale.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()));
    	Regions.eyrie.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()).add(1.0));
    	Regions.eyrie.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()).add(0.5));
    	Regions.westerlands.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()));
    	Regions.westerlands.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()));
    	Regions.casterlyRock.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()).add(0.7));
    	Regions.casterlyRock.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()).add(0.5));
    	Regions.dragonStone.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()).add(1.0));
    	Regions.dragonStone.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()).add(0.5));
    	Regions.reach.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()));
    	Regions.reach.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()));
    	Regions.highgarden.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()).add(1.0));
    	Regions.highgarden.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()).add(0.5));
    	Regions.crownlands.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()));
    	Regions.crownlands.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()));
    	Regions.crownlands1.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()));
    	Regions.crownlands1.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()));
    	Regions.stormlands.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()));
    	Regions.stormlands.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()));
    	Regions.stormEnd.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()).add(1.0));
    	Regions.stormEnd.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()).add(0.5));
    	Regions.dorne.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()));
    	Regions.dorne.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()));
    	Regions.sunspear.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()).add(1.0));
    	Regions.sunspear.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()).add(0.5));
    	Regions.harrenhal.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()).add(1.0));
    	Regions.harrenhal.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()).add(0.5));
    	Regions.ironIslands1.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()));
    	Regions.ironIslands1.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()));
    	Regions.ironIslands2.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()));
    	Regions.ironIslands2.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()));
    	Regions.ironIslands3.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()));
    	Regions.ironIslands3.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()));
    	Regions.ironIslands4.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()));
    	Regions.ironIslands4.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()));
    	Regions.ironIslands5.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()));
    	Regions.ironIslands5.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()));
    	Regions.ironIslands6.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()));
    	Regions.ironIslands6.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()));
    	Regions.ironIslands7.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()));
    	Regions.ironIslands7.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()));
    	Regions.pyke.scaleXProperty().bind(imageView.fitWidthProperty().divide(primaryStage.getScene().getWidth()).add(1.0));
    	Regions.pyke.scaleYProperty().bind(imageView.fitHeightProperty().divide(primaryStage.getScene().getHeight()).add(0.5));
    }
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    private static void fillSeatsWithSigils(){
	    Regions.fillImage(Regions.sunspear, new Image(Map.class.getResourceAsStream("/Images/MartellSigil.png")));
	   	Regions.fillImage(Regions.castleBlack, new Image(Map.class.getResourceAsStream("/Images/NightsWatchSigil.png")));
	   	Regions.fillImage(Regions.harrenhal, new Image(Map.class.getResourceAsStream("/Images/HoareSigil.png")));
	   	Regions.fillImage(Regions.dragonStone, new Image(Map.class.getResourceAsStream("/Images/TargaryenSigil.png")));
	   	Regions.fillImage(Regions.pyke, new Image(Map.class.getResourceAsStream("/Images/GreyjoySigil.png")));
    	Regions.fillImage(Regions.winterfell, new Image(Map.class.getResourceAsStream("/Images/StarkSigil.png")));
    	Regions.fillImage(Regions.twins, new Image(Map.class.getResourceAsStream("/Images/FreySigil.png")));
	   	Regions.fillImage(Regions.stormEnd, new Image(Map.class.getResourceAsStream("/Images/DurrandonSigil.png")));
	   	Regions.fillImage(Regions.eyrie, new Image(Map.class.getResourceAsStream("/Images/ArrynSigil.png")));
	   	Regions.fillImage(Regions.casterlyRock, new Image(Map.class.getResourceAsStream("/Images/LannisterSigil.png")));
	   	Regions.fillImage(Regions.highgarden, new Image(Map.class.getResourceAsStream("/Images/TyrellSigil.png")));

		Regions.setEffectToRegion(Regions.sunspear, Color.RED);
		Regions.setEffectToRegion(Regions.castleBlack, Color.BLACK);
		Regions.setEffectToRegion(Regions.harrenhal, Color.CYAN);
		Regions.setEffectToRegion(Regions.dragonStone, Color.MEDIUMVIOLETRED);
		Regions.setEffectToRegion(Regions.pyke, Color.GOLD);
		Regions.setEffectToRegion(Regions.winterfell, Color.GREEN);
		Regions.setEffectToRegion(Regions.twins, Color.BLUE);
		Regions.setEffectToRegion(Regions.stormEnd, Color.BLACK);
		Regions.setEffectToRegion(Regions.eyrie, Color.BLUEVIOLET);
		Regions.setEffectToRegion(Regions.casterlyRock, Color.RED);
		Regions.setEffectToRegion(Regions.highgarden, Color.DARKGREEN);
		
    }
    
    
	

}
    
  