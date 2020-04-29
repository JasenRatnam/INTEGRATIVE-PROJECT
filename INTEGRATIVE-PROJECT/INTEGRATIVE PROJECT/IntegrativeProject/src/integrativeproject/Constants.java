/*
 * Jasen Ratnam
 * Program for Computer Science with Maths.
 * In Vanier College.
 */
package integrativeproject;

/**
 * Integrative project.
 * Constants using in this program.
 * @author Jasen Ratnam 1622549
 */
public class Constants 
{

    /**
     * Width of the animation pane.
     */
    public final static double paneWidth = ResistanceCalculation.pane.getWidth(); 

    /**
     * Height of the animation pane.
     */
    public final static double paneHeight = ResistanceCalculation.pane.getHeight();

    /**
     * Border assigned to place the circuit in the center.
     */
    public static final double border = 100.0;
    
    /**
     * The center horizontal line of the animation pane.
     */
    public static final double paneCenterY = (paneHeight/2);

    /**
     * The top horizontal line of the animation pane, for the top wire.
     */
    public static final double paneTopWireY = (paneCenterY - 25);

    /**
     * The bottom horizontal line of the animation pane, for the bottom wire.
     */
    public static final double paneBottomWireY = (paneCenterY + 25);
    
    /**
     * Height of the wire.
     */
    public static final double wireHeigth = 10.0;

    /**
     * Width of the wire to be determined.
     */
    public static double wireWidth;
    
    /**
     * Width of the resistor.
     */
    public static final double resistorWidth = 20.0;

    /**
     * Height of the resistor.
     */
    public static final double resistorHeigth = ((paneBottomWireY - paneTopWireY) + wireHeigth);
    
    /**
     * Height of the electron shot in the animation.
     */
    public static final double objectHeigth = 10;

    /**
     * Width of the electron shot in the animation.
     */
    public static final double objectwidth = 50;
    
    /**
     * The starting point of the label in the animation pane.
     */
    public static final double labelStartingPoint  = border + resistorWidth;
    
    /**
     * String to get the image of the wire.
     */
    public static final String wireImage  = "wire";

    /**
     * String to get the image of the resistor.
     */
    public static final String resistorImage  = "resistor";

    /**
     * String to get the image of the circuit.
     */
    public static final String electronShot = "circuit";
    
    /*
    * Int with max amount of points in the sine wave.
    */
    public static final int MAX_POINTS = 300;
    
    /*
    * Value of 2*pi
    */
    public static final double PI2 = 2*Math.PI;
    
    /**
     * Speed of sound.
     */
    public static final double v_sound = 343;
    
    /**
     * Method to find the width of the wire needed from the number of resistors wanted.
     * @param resistorsNumber the number of total resistance.
     * @return the width of the wire.
     */
    public static double getWireWidth(double resistorsNumber)
    {
        wireWidth = ((paneWidth - (2*border))/resistorsNumber); // calculte the width of the wire.
        return wireWidth;
    }
}
