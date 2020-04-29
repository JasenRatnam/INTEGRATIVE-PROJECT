/*
 * Jasen Ratnam
 * Program for Computer Science with Maths.
 * In Vanier College.
 */
package integrativeproject;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.ImagePattern;

/**
 * Integrative project.
 * Used to create an object.
 * @author Jasen Ratnam 1622549
 */
public class AssetManager
{
    /**
     * The background of the pane.
     */
    private static Background backgroundImage = null;
    
    /**
     * image for a wire.
     */
    private static ImagePattern horizontalWire = null;
    
    /**
     * Image for a resistor.
     */
    private static ImagePattern verticalResistor = null;
    
    /**
     * Image for a electron shot.
     */
    private static ImagePattern circuitShot = null;
    
    /**
     * background image of a race track.
     */
    private static  Background racetrack_img;
    
    /**
     * Image of race car.
     */
    private static ImagePattern racecar_img;
    
    /**
     * Image of finish line.
     */
    private static  ImagePattern finishline_img;
    
    /**
     * Image of ambulance.
     */
    private static  ImagePattern ambulance_img;
    
    /**
     * Get the file path from the string path.
     */
    private static  String fileURL(String relativePath)
    {
        return new File(relativePath).toURI().toString();  // file path.
    }
    
    /**
     * Preload all the assets from the files.
     */
    public static  void preloadAllAssets()
    {
        // Preload all images
        
        // The backgorund image.
        Image background = new Image(fileURL("./assets/images/background.png")); 
        backgroundImage = new Background(
                            new BackgroundImage(background, 
                                                BackgroundRepeat.NO_REPEAT, 
                                                BackgroundRepeat.NO_REPEAT, 
                                                BackgroundPosition.DEFAULT,
                                                BackgroundSize.DEFAULT));
        
        // The race track background image.
        Image racetrack = new Image(fileURL("./assets/images/street.png"));
        racetrack_img = new Background(
                                    new BackgroundImage(racetrack,
                                                        BackgroundRepeat.NO_REPEAT,
                                                        BackgroundRepeat.NO_REPEAT,
                                                        BackgroundPosition.DEFAULT,
                                                        BackgroundSize.DEFAULT));
        
        
        // The wire image.
        horizontalWire = new ImagePattern(new Image(fileURL("./assets/images/HorizontalWire.png")));
        // The resistor image.
        verticalResistor = new ImagePattern(new Image(fileURL("./assets/images/verticalResistor.png")));
        // The elsectron shot image.
        circuitShot = new ImagePattern(new Image(fileURL("./assets/images/circuitShot.png")));
        // The race car image.
        racecar_img = new ImagePattern(new Image(fileURL("./assets/images/racecar.png")));
        // The finish line image.
        finishline_img = new ImagePattern(new Image(fileURL("./assets/images/finishline.png")));
        // The ambulance image.
        ambulance_img = new ImagePattern(new Image(fileURL("./assets/images/ambulance.png")));
    }
    
    /**
     * Get the image for the background image.
     * @return
     */
    public static Background getBackgroundImage()
    {
        return backgroundImage;        
    }
    
    /**
     * Get the image for the race track.
     * @return image.
     */
    public static Background getRaceTrackImg()
    {
        return racetrack_img;
    }
    
    /**
     * Get the image for the electron shot.
     * @return image.
     */
    public static  ImagePattern getCircuitShotImage()
    {
        return circuitShot;        
    }
    
    /**
     * Get the image for the ambulance.
     * @return image.
     */
    public static ImagePattern getAmbulanceImage()
    {
        return ambulance_img;
    }
    
    /**
     * Get the image for the wire.
     * @return
     */
    public static ImagePattern getHorizontalWireImage()
    {
        return horizontalWire;
    }
    
    /**
     * Get the image for the race car..
     * @return
     */
    public static ImagePattern getRaceCarImg()
    {
        return racecar_img;
    }
    
    /**
     * Get the image for the finish line.
     * @return
     */
    public static ImagePattern getFinishLineImg()
    {
        return finishline_img;
    }
    
    /**
     * Get the image for the resistors.
     * @return
     */
    public static ImagePattern getVerticalResistorImage()
    {
        return verticalResistor;
    }
}
