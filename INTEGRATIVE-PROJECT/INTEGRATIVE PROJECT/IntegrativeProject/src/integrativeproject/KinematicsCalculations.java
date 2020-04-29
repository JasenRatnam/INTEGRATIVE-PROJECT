/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrativeproject;


import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/*
@author Salvatore Bruzzese 1649908
*/
public class KinematicsCalculations
{
  
         
    /**
     * Boolean used to Debug the code in case of errors.
     * Activated if true.
     */
    private static boolean DEBUG = true; 
    
    /**
    * String to save the message to be displayed in the FXML label.
    */
    private static String message = "";         // Initislize the String to save the messages.
    
    
    
    private static final CategoryAxis xAxis_position = new CategoryAxis();
    private static final NumberAxis yAxis_position = new NumberAxis();
    private static final CategoryAxis xAxis_velocity = new CategoryAxis();
    private static final NumberAxis yAxis_velocity = new NumberAxis();
    private static double lastFrameTime = 0.0;
    private static double distance;
    private static double final_vel;
    private static XYChart.Series<String,Number> positionsSet;
    private static XYChart.Series<String,Number> velocitySet;
    private static double init_velocityValue;
    private static double accelerationValue;
    private static LineChart<String,Number> positionsChart;
    private static LineChart<String,Number> velocityChart;
    private static double time;
    private static ArrayList<AnimationObject> car = new ArrayList<>();

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
    
    private static AnchorPane pane;
    
     /**
     * FXML controller that is used to change the label
     */
    private static FXMLDocumentController controller;
    
    
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
            controller.setMessageLabel(message);
        } 
        catch (Exception ex)
        {
            error = true; // Set the error boolean to true, because an error has been found.
            if(DEBUG == true) // Print text to help debug code if DEBUG is true.
            {
                System.out.println("ERROR: SetMessage(): AN error happened in this method.");        // Print error message.
                Logger.getLogger(KinematicsCalculations.class.getName()).log(Level.SEVERE, null, ex); // Print error log.
            }
        }
    }
    
    public static double kinematics_d(double v_i, double a, double t)
    {
        double d = v_i * t + 0.5*a*Math.pow(t, 2.0);
        
        return d;
    }
    
    public static double kinematics_v(double d, double v_i, double a)
    {
        double v_f = v_i + 2.0*a*d;
        
        return Math.sqrt(v_f);
    }
 
    /**
     * Get initial velocity.
     * @param tf 
     */
    private static void setInitialVelocityValue(TextField tf)
    {
        init_velocityValue = getTextFieldValue(tf);
        
        // eror message;
        message = "Initial velocity must be between 25 & 250";
        
        if(init_velocityValue <= 25 && init_velocityValue > 250 )
        {
            error = true;
            init_velocityValue = 0.0;
            SetMessage(controller, message);
        }
        
         if(DEBUG == true) // Print text to help debug code if DEBUG is true. Sends the resistor values in int an string to see if conversion worked.
        {
            System.out.println("setInitialVelocityValue(): Value of velocity: " + init_velocityValue);  
        
        }
    }
    
    private static void setAccelerationValue(TextField tf)
    {
        accelerationValue = getTextFieldValue(tf);
        
        // eror message;
        message = "Initial acceleration must be between 0 & 50";
        
        if(accelerationValue <= 0 && accelerationValue > 50)
        {
            error = true;
            accelerationValue = 0.0;
            SetMessage(controller, message);
        }
        
        if(DEBUG == true) // Print text to help debug code if DEBUG is true. Sends the resistor values in int an string to see if conversion worked.
        {
            System.out.println("setAccelerationValue(): Value of acceleration: " + accelerationValue);  
        }
    }
    
    /**
     * Get the charts.
     */
    private static void getKinematicsCharts()
    {
        positionsSet = new XYChart.Series<>();
        velocitySet = new XYChart.Series<>();
        
        distance = kinematics_d(init_velocityValue,accelerationValue , time);
        positionsSet.getData().add(new XYChart.Data<String,Number>(time+"",distance)); 

        final_vel =kinematics_v(distance, init_velocityValue , accelerationValue);
        velocitySet.getData().add(new XYChart.Data<String,Number>(time+"", final_vel)); 
    }
    
    // Set charts.
    private static void setKinematicsCharts(LineChart lc1, LineChart lc2)
    {
        positionsChart=lc1;
        velocityChart=lc2;
    }
    
    // Enter data in chart.
    private static void enterDataInChart(LineChart lc1, LineChart lc2)
    {
        try
        {
            yAxis_position.setAutoRanging(false);
            yAxis_position.setLowerBound(0);
            yAxis_position.setUpperBound(500);
            yAxis_position.setTickUnit(1);

            yAxis_velocity.setAutoRanging(false);
            yAxis_velocity.setLowerBound(0);
            yAxis_velocity.setUpperBound(500);
            yAxis_velocity.setTickUnit(1);

            lc1 = new LineChart<String,Number>(xAxis_position, yAxis_position);
            lc2 = new LineChart<String,Number>(xAxis_velocity, yAxis_velocity);

            lc1.getData().clear(); // Clear the graph from any previous data.
            lc1.layout();          // ReLayout it, to make it look empty again.

            lc2.getData().clear(); // Clear the graph from any previous data.
            lc2.layout();          // ReLayout it, to make it look empty again.

            getKinematicsCharts();           

            positionsChart.getData().add(positionsSet); 
            velocityChart.getData().add(velocitySet);     
        }
        catch(Exception e)
        {
            error = true;
            
            if(e instanceof NullPointerException)
            {
                SetMessage(controller,"Please ensure all required textfields are filled");
            }
            if(e instanceof NumberFormatException)
            {
                SetMessage(controller,"Please ensure all inputs are numbers");
            }
            if(e instanceof RuntimeException)
            {
                SetMessage(controller,"Please ensure all inputs are numbers");
            }
            if(e instanceof IllegalArgumentException)
            {
                SetMessage(controller,"Please ensure all inputs are numbers");
            }
        }
    }
    
    public static void Reset(AnchorPane pane, LineChart top_chart, LineChart bottom_chart, TextField top_tf, TextField mid_tf)
    {
        reseted = true;                          // Set the reset boolean to true.
        pane.getChildren().clear();              // clear the pane.
        top_tf.setText("");                     // Clear all TextFields.
        mid_tf.setText("");
        
        distance = 0;
        final_vel = 0;
        time =0;
        
        positionsSet.getData().clear();
        velocitySet.getData().clear();
        
        top_chart.getData().clear();      // Clear graph data.
        top_chart.layout();               // Clear graph layout.
        
        bottom_chart.getData().clear();      // Clear graph data.
        bottom_chart.layout();               // Clear graph layout.
    }
    
    public static void Pause()
    {
        paused = true;
    }
    
    public static void Help(FXMLDocumentController cont)
    {
        controller = cont;
        
        SetMessage(cont,"Enter the initial velocity, and the acceleration, then press start ");
    }

    
    public static void animation(AnchorPane pane, LineChart lc1, LineChart lc2, TextField top_tf, TextField mid_tf,FXMLDocumentController controller)
    { 
        KinematicsCalculations.controller = controller;
        // Clear pane
        pane.getChildren().clear();  
        pane.layout();                
        // Preload assets
        AssetManager.preloadAllAssets();
        
        // Initialize values
        distance = 0;
        final_vel = 0;
        
        time = 0;
        
        error = false;    
        paused = false;   
        reseted = false;
        
        KinematicsCalculations.pane = pane; 
        
        
        lastFrameTime = 0.0f;
        long initialTime = System.nanoTime();
        
        try
        {
        AnimationObject racecar = new AnimationObject(new Vector_2D(0,190), new Vector_2D((getTextFieldValue(top_tf)*(pane.getWidth()/400)),0), new Vector_2D(getTextFieldValue(mid_tf)*2.5075,0), 50.0);
        racecar.getCircle().setFill(AssetManager.getRaceCarImg());
        
        car.add(racecar);
        
        addToPane(racecar.getCircle());
        
        AnimationObject finishline = new AnimationObject(new Vector_2D(990,140), new Vector_2D(0,0), new Vector_2D(0,0), 100);
        finishline.getCircle().setFill(AssetManager.getFinishLineImg());
        
        addToPane(finishline.getCircle());


        pane.setBackground(AssetManager.getRaceTrackImg());
        
      
            setInitialVelocityValue(top_tf);
            setAccelerationValue(mid_tf);
            setKinematicsCharts(lc1, lc2);
        }
      catch(Exception e)
        {
            error = true;
            
            if(e instanceof NullPointerException)
            {
                SetMessage(controller,"Please ensure all required textfields are filled");
            }
            if(e instanceof NumberFormatException)
            {
                SetMessage(controller,"Please ensure all inputs are numbers");
            }
            if(e instanceof RuntimeException)
            {
                SetMessage(controller,"Please ensure all inputs are numbers");
            }
            if(e instanceof IllegalArgumentException)
            {
                SetMessage(controller,"Please ensure all inputs are numbers");
            }
        }
        
        // Get charts.
        getKinematicsCharts();
        
        lc1.setLegendVisible(false);
        lc2.setLegendVisible(false);

        new AnimationTimer()
        {
            @Override
            public void handle(long now) 
            {
                double currentTime = (now - initialTime) / 1000000000.0;
                double  frameDeltaTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;
                
                time = currentTime;
                

                if(distance < 400)
                {
                    try
                    {
                        enterDataInChart(positionsChart, velocityChart);
                    }
                        
                    catch(Exception e)
                    {
                        error = true;
                        
                        if(e instanceof NullPointerException)
                        {
                            SetMessage(controller,"Please ensure all required textfields are filled");
                        }
                        if(e instanceof NumberFormatException)
                        {
                            SetMessage(controller,"Please ensure all inputs are numbers");
                        }
                        if(e instanceof RuntimeException)
                        {
                            SetMessage(controller,"Please ensure all inputs are numbers");
                        }
                        if(e instanceof IllegalArgumentException)
                        {
                            SetMessage(controller,"Please ensure all inputs are numbers");
                        }
                        else
                        {
                            SetMessage(controller,"");
                        }
                    }
                }

          
                if(paused == true || error == true) // If paused is clicked or if error caught
                {
                    this.stop(); //then stop the animation.
                }

                if(reseted == true) // If reset is clicked 
                {
                    this.stop(); // Then stop the animation
                    Reset(pane, lc1, lc2, top_tf, mid_tf); // And call the reset method.
                }
                
                for(AnimationObject obj : car)
                {
                    obj.update(frameDeltaTime);
                } 
            }
        }.start();
    }
    
    private static void addToPane(Node node)
    {
        pane.getChildren().add(node); // Add object to pane.
    }

    /**
     * Get double value of a textField.
     * @param tf TextFied
     * @return Double value.
     */
    private static double getTextFieldValue(TextField tf)
    {
        // Initialize double value
        double tf_dbl = 0;
                
        // Get text value in string.
        String tf_str = tf.getText();
        
        // error message.
        message = "Please enter a Double value in the textField";
        
        try
        {
            tf_dbl = Double.parseDouble(tf_str);
        }
        
        catch(NumberFormatException | NullPointerException e) 
        { 
            SetMessage(controller, message);   // If an error is catched than change the message to the error message.
            error = true;                      // Set the error boolean to true since an error is caught.
            
            if(DEBUG == true) // Print text to help debug code if DEBUG is true.
            {
                System.out.println("ERROR: getTextField(): AN error happened in this method."); // Print error message.
                Logger.getLogger(DopplerCalculations.class.getName()).log(Level.SEVERE, null, e);   // Print error log.
            }
        }
        
        return tf_dbl;
    }
    
}
