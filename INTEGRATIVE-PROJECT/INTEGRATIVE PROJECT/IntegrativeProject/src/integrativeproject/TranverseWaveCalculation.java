
package integrativeproject;

import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * @author Jasen Ratnam 1622549
 */
public class TranverseWaveCalculation 
{
    /**
     * Boolean when user decides to pause the animations.
     * Paused when true.
     */
    public static boolean paused = false;

    /**
     * Boolean for errors found in the program.
     * True if error are founds. 
     */
    private static boolean error = false;

    /**
     * Boolean when user decides to reset the animations.
     * Reseted when true.
     */
    private static boolean reseted = false;
    
    public static boolean DEBUG = false;
    
     /**
     * String to save the message to be displayed in the FXML label.
     */
    private static String message = "";         
    
    /**
     * Format to round of numbers to two digits after the decimal.
     */
    private static final DecimalFormat df2 = new DecimalFormat(".##");
    
    /**
     * The anchor pane where all the animations will happen
     */
    public static AnchorPane pane;
    
    /**
     * FXML controller that is used to change the label
     */
    private static FXMLDocumentController controller;
    
    public static LineChart<Number,Number> TranverseChart;
    
    public static NumberAxis xAxis;
    
    public static NumberAxis yAxis;
    
    public static XYChart.Series<Number, Number> sineSet;
    
    public static String function = "";
    
    private static List<Point2D> points = new ArrayList<Point2D>();
    
    private static Transition animation1;
    
    public static double amplitude = 0;
    
    public static double wavelength = 0;
    
    public static double frequency = 0;
    
    public static double angularFrequency = 0;
    
    public static double waveNumber = 0;
    
    public static double phi = 0;
    
    /**
     * Method to set a message on the messageLabel in the FXML file.
     * @param controller The controller for the FXML file that contains the label to be modified.
     * @param message Message to set on the label in the FXML file.
     */
    private static void SetMessage(FXMLDocumentController controller,String message)
     {
        try
        {
            // Get the setMessage() method from the controller and change the message of the label.
            TranverseWaveCalculation.controller.setMessageLabel(message);
        } 
        catch (Exception ex)
        {
            error = true; // Set the error boolean to true, because an error has been found.
            
            if(DEBUG == true) // Print text to help debug code if DEBUG is true.
            {
                System.out.println("ERROR: SetMessage(): AN error happened in this method.");        // Print error message.
                Logger.getLogger(ResistanceCalculation.class.getName()).log(Level.SEVERE, null, ex); // Print error log.
            }
        }
    }
    
    private static void SetFunction(FXMLDocumentController controller,String message)
     {
        try
        {
            controller.setFunctionLabel(message);
        } 
        catch (Exception ex)
        {
            error = true; // Set the error boolean to true, because an error has been found.
            
            if(DEBUG == true) // Print text to help debug code if DEBUG is true.
            {
                System.out.println("ERROR: SetFunction(): AN error happened in this method.");        // Print error message.
                Logger.getLogger(ResistanceCalculation.class.getName()).log(Level.SEVERE, null, ex); // Print error log.
            }
        }
    }
    
    /**
     * Method that acts when the button HELP is clicked by the user.
     * Prints a message that helps the user use the program.
     * @param controller The controller for the FXML file that contains the label to be modified.
     */
    public static void Help(FXMLDocumentController controller)
    {
        // Message to be labeled in the FXML.
        // Message tells the user what to do.
        message = "Enter the amplitude, the wavelength, the frequency and the phi wanted. The program wiil show the wave function, a graph at an instance and a animation of the wave.";
        SetMessage(controller,message); // Sets the message on the label in the FXML file.
    }
    
    //y(x,t) = Asin(kx^2+ωt)
    public static void getAmplitudeValue(TextField amplitudeValue)
    {
        String amplitudeString = amplitudeValue.getText();
        
        message = "Please enter an double amplitude value between 10 & 170";  // Message to display in case of an error.
        
        try
        {
            amplitude = Math.abs(Double.parseDouble(amplitudeString));
        }
        
        catch(NumberFormatException | NullPointerException e) 
        { 
            SetMessage(controller, message);   // If an error is catched than change the message to the error message.
            error = true;                      // Set the error boolean to true since an error is caught.
            
            if(DEBUG == true) // Print text to help debug code if DEBUG is true.
            {
                System.out.println("ERROR: getAmplitudeValue(): AN error happened in this method."); // Print error message.
                Logger.getLogger(ResistanceCalculation.class.getName()).log(Level.SEVERE, null, e);   // Print error log.
            }
        }
        
        if(amplitude > 170 || amplitude < 10) // Check if value is out of bounds.
        {
            SetMessage(controller, message); // If an error is catched than change the message to the error message.
            error = true;                    // Set the error boolean to true since an error is caught.
        }
        
        if(DEBUG == true)
        {
            System.out.println("getAmplitudeValue(): Value of amplitude (String): " + amplitudeString);
            System.out.println("getAmplitudeValue(): Value of amplitude (double): " + amplitude);
        }
    }
    
    public static void getWavelengthValue(TextField wavelengthValue)
    {
        String wavelengthString = wavelengthValue.getText();
        
        message = "Please enter an double wavelength value between 100 & 800";  // Message to display in case of an error.
        
        try
        {
            wavelength = Double.parseDouble(wavelengthString);
        }	
        
        catch(NumberFormatException | NullPointerException e) 
        { 
            SetMessage(controller, message);   // If an error is catched than change the message to the error message.
            error = true;                      // Set the error boolean to true since an error is caught.
            
            if(DEBUG == true) // Print text to help debug code if DEBUG is true.
            {
                System.out.println("ERROR: getWavelengthValue(): AN error happened in this method."); // Print error message.
                Logger.getLogger(ResistanceCalculation.class.getName()).log(Level.SEVERE, null, e);   // Print error log.
            }
        }
        
        if(wavelength > 800 || wavelength < 100) // Check if value is out of bounds.
        {
            SetMessage(controller, message); // If an error is catched than change the message to the error message.
            error = true;                    // Set the error boolean to true since an error is caught.
        }
        
        if(DEBUG == true)
        {
            System.out.println("getWavelengthValue(): Value of wavelength (String): " + wavelengthString);
            System.out.println("getWavelengthValue(): Value of wavelength (double): " + wavelength);            
        }
    }
    
    public static void getFrequencyValue(TextField frequencyValue)
    {
        String frequencyString = frequencyValue.getText();
        
        message = "Please enter an double frequency value between 1 & 30";  // Message to display in case of an error.

        try
        {
            frequency = Double.parseDouble(frequencyString);
        }
        
        catch(NumberFormatException | NullPointerException e) 
        { 
            SetMessage(controller, message);   // If an error is catched than change the message to the error message.
            error = true;                      // Set the error boolean to true since an error is caught.
            
            if(DEBUG == true) // Print text to help debug code if DEBUG is true.
            {
                System.out.println("ERROR: getFrequencyValue(): AN error happened in this method."); // Print error message.
                Logger.getLogger(ResistanceCalculation.class.getName()).log(Level.SEVERE, null, e);   // Print error log.
            }
        }
        
        if(frequency > 30 || frequency < 1) // Check if value is out of bounds.
        {
            SetMessage(controller, message); // If an error is catched than change the message to the error message.
            error = true;                    // Set the error boolean to true since an error is caught.
        }
        
        if(DEBUG == true)
        {
            System.out.println("getFrequencyValue(): Value of frequency (String): " + frequencyString);
            System.out.println("getFrequencyValue(): Value of frequency (double): " + frequency);
        }
    }
    
    public static void getPhiValue(TextField phiValue)
    {
        String phiString = phiValue.getText();	
        
        message = "Please enter an double phi value.";  // Message to display in case of an error.

        try
        {
            phi = Double.parseDouble(phiString);
        }	
        
        catch(NumberFormatException | NullPointerException e) 
        { 
            SetMessage(controller, message);   // If an error is catched than change the message to the error message.
            error = true;                      // Set the error boolean to true since an error is caught.
            
            if(DEBUG == true) // Print text to help debug code if DEBUG is true.
            {
                System.out.println("ERROR: getPhiValue(): AN error happened in this method."); // Print error message.
                Logger.getLogger(ResistanceCalculation.class.getName()).log(Level.SEVERE, null, e);   // Print error log.
            }
        }
        
        if(DEBUG == true)
        {
            System.out.println("getPhiValue(): Value of frequency (String): " + phiString);
            System.out.println("getPhiValue(): Value of frequency (double): " + phi);
        }
    }

    public static void getAngularFrequencyValue()
    {
        angularFrequency = (Constants.PI2*frequency);
        
        if(DEBUG == true)
        {
            System.out.println("getAngularFrequencyValue(): Value of angular frequency (double): " + angularFrequency);
        }
    }
    
    public static void getWaveNumber()
    {
        waveNumber = (Constants.PI2/wavelength);
        
        if(DEBUG == true)
        {
            System.out.println("getWaveNumber(): Value of wave number (double): " + waveNumber);
        }
    }
    
    public static void getTranverseWaveFunction()
    {
        function = "f(x,t) = " + amplitude + "[m] " + "sin(" + df2.format(waveNumber) + "[rad/m]x  - " + df2.format(angularFrequency) + "rad/s]t + " + phi + "[rad])";
        
        SetFunction(controller, function);
       
        if(DEBUG == true)
        {
            System.out.println("Wave FUNCTION is: " + function); 
        }
    }
    
    public static void SineCurve()
    {
      points.clear();
      for (int i = 0; i < Constants.MAX_POINTS; i++)
      {
        double cycleX = (i * waveNumber);
        double sineResult = amplitude*Math.sin(angularFrequency + cycleX + phi);
        
        points.add(new Point2D.Double(cycleX, sineResult));
      }
    }
    
    private static void getSinePointsChart ()
    {
        sineSet = new XYChart.Series();
        sineSet.setName(function);
        
        SineCurve();  // Calculate points.
        
        for(int i = 0; i < points.size(); i++) 
        {
            sineSet.getData().add(new XYChart.Data(points.get(i).getX(),points.get(i).getY())); 
        }
    }
    
    public static void enterDataInChart () throws InterruptedException
    {
        TranverseChart.getData().clear(); // Clear the graph from any previous data.
        TranverseChart.layout();          // ReLayout it, to make it look empty again.
        
        getSinePointsChart();

        if(error == false) // If no error has happen previously than set the axis and add data.
        {
            xAxis.setLabel("x");
            yAxis.setLabel("f(x,t)");
            
            TranverseChart.setTitle("Tranverse wave");
            TranverseChart.getData().addAll(sineSet);
            TranverseChart.setLegendSide(Side.TOP); 
        }
    }
    
    /**
     * Method that adds objects to the pane of animation.
     * @param node Object to be added.
     */
    public static void addToPane(Node node)
    {
        pane.getChildren().add(node); // Add object to pane.
    }
    
    public static void Reset(AnchorPane pane, LineChart<Number,Number> TranverseChart, TextField TextField1, TextField TextField2, TextField TextField3, TextField TextField4)
    {
        reseted = true;                          // Set the reset boolean to true.
        pane.getChildren().clear();              // clear the pane.
        TextField1.setText("");    // Clear all TextFields.
        TextField2.setText("");
        TextField3.setText("");
        TextField4.setText("");
        TranverseChart.getData().clear();      // Clear graph data.
        TranverseChart.layout();               // Clear graph layout.
        SetFunction(controller, "");
        SetMessage(controller, "Reseted");
    }
    
    public static void paused()
    {
        paused = true;
        animation1.pause();
        SetMessage(controller, "Paused");
    }
    
    public static void animation(AnchorPane pane, TextField TextField1, TextField TextField2, TextField TextField3,  TextField TextField4,LineChart<Number,Number> TranverseChart,NumberAxis xAxis, NumberAxis yAxis, FXMLDocumentController controller) throws InterruptedException
    { 
        pane.getChildren().clear();   // Clear the animation pane from any previous animation to start. 
        pane.layout();                // Clear pane layout.
        
        TranverseChart.getData().clear();      // Clear graph data.
        TranverseChart.layout();   
         
        SetFunction(controller, "");
        SetMessage(controller, "Playing");
        
        error = false;    // Boolean for when an error is caught anywhere in the program. Reseted at the start of the progrma.
        paused = false;   // Boolesn for pausing tha naimation with a button in the FXML controller. Reseted at the start of the progrma.
        reseted = false;  // Boolean to reset the program to the base. Reseted at the start of the progrma.
             
        TranverseWaveCalculation.pane = pane;  
        TranverseWaveCalculation.xAxis = xAxis;                                                           // Initialize the X-axis.
        TranverseWaveCalculation.yAxis = yAxis; 
        TranverseWaveCalculation.TranverseChart = TranverseChart;
        TranverseWaveCalculation.controller = controller;
        
        if(TextField1.getText().equals("") || TextField2.getText().equals("") || TextField3.getText().equals("") || TextField4.getText().equals("") )
        {
            message = "Please enter values in the textfields to start.";
            SetMessage(controller, message);
            error = true;
        }
              
        if(error == false) 
        {
            getAmplitudeValue(TextField1);
            getWavelengthValue(TextField2);
            getFrequencyValue(TextField3);
            getPhiValue(TextField4);
            
            getAngularFrequencyValue();
            getWaveNumber();
        }
        
        if(error == false) // Do the animation only if no error has been found previously.
        {
            getTranverseWaveFunction();
            
            enterDataInChart(); 

            AssetManager.preloadAllAssets(); // Pre load all the assets from the asset manager class.


            DoubleUnaryOperator newFunction = j -> -Math.sin(angularFrequency + waveNumber*j + phi) * amplitude + 200;
            Plot plot = new Plot(newFunction, 
                                 pane.getWidth() - Constants.border, 
                                 pane.getHeight(), 
                                 Constants.border, 
                                 pane.getWidth() - Constants.border);

            addToPane(plot.getPlot());

            Circle circle = new Circle(10);
            addToPane(circle);

            animation1 = plot.createTransition(Duration.seconds(frequency), 
                                               circle.centerXProperty(), 
                                                circle.centerYProperty());

            animation1.setCycleCount(Animation.INDEFINITE);
            animation1.setAutoReverse(true);
            animation1.play();
            
            
            if(paused == true || error == true) // If paused is clicked or if error caught
            {
                animation1.pause(); //then stop the animation.
                SetMessage(controller, "Paused");
            }

            if(reseted == true) // If reset is clicked 
            {System.out.println("OL");
                animation1.stop(); // Then stop the animation
                Reset(pane, TranverseChart, TextField1, TextField2, TextField3, TextField4); // And call the reset method.
            }
        } 

          
    }
    
     
    public static class Plot
    {
        private final DoubleUnaryOperator function;
        private final double startX;
        private final double endX;
        private final Canvas plot;
        
        public Plot(DoubleUnaryOperator function, double width, double height, double startX, double endX) 
        {
            this.function = function;
            this.startX = startX;
            this.endX = endX;
            this.plot = new Canvas(width, height);
            
            GraphicsContext graphic = plot.getGraphicsContext2D();
            graphic.moveTo(startX, function.applyAsDouble(startX));
            
            for (double x = startX; x < endX; x++) 
            {
                graphic.lineTo(x, function.applyAsDouble(x));
            }
            
            graphic.stroke();
       }
        
       public Canvas getPlot() 
       {
           return plot;
       }
       
       public Transition createTransition(Duration cycleDuration, DoubleProperty x, DoubleProperty y) 
       {
           return new Transition() 
           {
               {
                   setCycleDuration(cycleDuration);
               }
               
               @Override
               protected void interpolate(double frac) 
               {
                   double xValue = (1 - frac) * startX + frac * endX;
                   double yValue = function.applyAsDouble(xValue);
                   x.set(xValue);
                   y.set(yValue);
               }
           };
       }
	    
	    
    }
}


