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

/**
 *
 * @author Salvatore Bruzzese 1649908
 */
public class DopplerCalculations
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
    
    /**
     * Anchor pane where animation happens.
     */
    private static AnchorPane doppler_pane;
    
    /**
     * Initialize last frame time.
     */
    private static double lastFrameTime = 0.0;

    /**
     * Velocity of source.
     */
    private static double source_velocityValue;
    
    /**
     * Velocity of observer.
     */
    private static double observer_velocityValue;
    
    /**
     * Initial frequency.
     */
    private static double init_frequencyValue;
    
    /**
     * X-axis of position chart.
     */
    private static final CategoryAxis xAxis_position = new CategoryAxis();
    
    /**
     * Y-axis of position chart.
     */
    private static final NumberAxis yAxis_position = new NumberAxis();
    
    /**
     * X-axis of frequency chart.
     */
    private static final CategoryAxis xAxis_frequency = new CategoryAxis();
    
    /**
     * Y-axis of frequency chart.
     */
    private static final NumberAxis yAxis_frequency = new NumberAxis();
    
    /**
     * Data set for positions.
     */
    private static XYChart.Series<String,Number> positionsSet = new XYChart.Series<String,Number>();
    
    /**
     * Data set for frequencies.
     */
    private static XYChart.Series<String,Number> frequencySet;
    
    /**
     * Line chart for positions.
     */
    private static LineChart<String,Number> positionsChart;
    
    /**
     * Line chart for frequency.
     */
    private static LineChart<String,Number> frequencyChart;
    
    /**
     * ArrayList containing the cars.
     */
    private static ArrayList<AnimationObject> car = new ArrayList<>();
   
    /**
     * Position of object.
     */
    private static double position;
    
    /**
     * Frequency of object.
     */
    private static double frequency;
    
    /**
     * Time of animation.
     */
    private static double time;
    
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
            DopplerCalculations.controller.setMessageLabel(message);
        } 
        catch (Exception ex)
        {
            error = true; // Set the error boolean to true, because an error has been found.
            if(DEBUG == true) // Print text to help debug code if DEBUG is true.
            {
                System.out.println("ERROR: SetMessage(): AN error happened in this method.");        // Print error message.
                Logger.getLogger(DopplerCalculations.class.getName()).log(Level.SEVERE, null, ex); // Print error log.
            }
        }
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
    
    /**
     * get velocity of source.
     * @param tf TextField.
     */
    private static void setSourceVelocityValue(TextField tf)
    {
        // get velocity.
        source_velocityValue = getTextFieldValue(tf);
        
        // eror message;
        message = "The Source velocity is out of range 0-1000";
        
        // Check range.
        if(source_velocityValue <= 0 || source_velocityValue > 1000 || error == true)
        {
            error = true;
            source_velocityValue = 0.0;
            SetMessage(controller, message);
        }
        
        if(DEBUG == true) // Print text to help debug code if DEBUG is true. Sends the resistor values in int an string to see if conversion worked.
        {
            System.out.println("setSourceVelocityValue(): Value of velocity: " + source_velocityValue);  
        }
    }
    
    /**
     * Get velocity of observer.
     * @param tf TextField.
     */
    private static void setObserverVelocityValue(TextField tf)
    {
        // Get velcoity
        observer_velocityValue = getTextFieldValue(tf);
        
        // Error message.
        message = "The observer velocity is out of range 0-1000";
        
        
        // Check range. 
        if(observer_velocityValue <= 0 || observer_velocityValue > 1000 || error == true)
        {
            error = true;
            observer_velocityValue = 0.0;
            SetMessage(controller, message);
        }   

        if(DEBUG == true) // Print text to help debug code if DEBUG is true. Sends the resistor values in int an string to see if conversion worked.
        {
            System.out.println("setObserverVelocityValue(): Value of velocity: " + observer_velocityValue);  
        }        
    }
    
    /**
     * Get initial frequency value.
     * @param tf textField.
     */
    private static void setInitialFrequencyValue(TextField tf)
    {
        // Initial frequency value.
        init_frequencyValue = getTextFieldValue(tf);
        
        // Error message
        message = "The frequency is out of range 0-1000";
        
        // Check range.
        if(init_frequencyValue <= 0 || init_frequencyValue > 1000 || error == true)
        {
            error = true;
            init_frequencyValue = 0.0;
            SetMessage(controller, message);
        }
        
        if(DEBUG == true) // Print text to help debug code if DEBUG is true. Sends the resistor values in int an string to see if conversion worked.
        {
            System.out.println("setInitialFrequencyValue(): Value of frequency: " + init_frequencyValue);  
        }  
    }
    
    /**
     * Set the new frequency 
     * @param obvs_pos position of observer.
     * @param source_pos position of source
     * @return new frequency.
     */
    private static double setFrequency(double obvs_pos, double source_pos)
    {
        // initailize new frequency.
        double f = 0;
        
        // Do formula.
        if(obvs_pos < source_pos)
        {
           f = dopplerHeadOn(init_frequencyValue, source_velocityValue, observer_velocityValue);
        }
        if(obvs_pos == source_pos)
        {
           f = init_frequencyValue;
        }
        if(obvs_pos > source_pos)
        {
            f = dopplerMoveApart(init_frequencyValue, source_velocityValue, observer_velocityValue);
        }
        
        return f; // New frequency.
    }
    
    /**
     * Get charts for dopier effect.
     */
    private static void getDopplerCharts()
    {
        // Get data.
        double obvs_pos = KinematicsCalculations.kinematics_d(observer_velocityValue, 0, time);
        double source_pos = 400 - KinematicsCalculations.kinematics_d(source_velocityValue, 0, time);
        double freq_prime = setFrequency(obvs_pos, source_pos);
        
        position = obvs_pos;
        frequency = freq_prime;
        
        // Create charts.
        positionsSet = new XYChart.Series<>();
        frequencySet = new XYChart.Series<>();
        
        // Clear datas.
        positionsSet.getData().clear();
        frequencySet.getData().clear();
        
        // Add data in charts.
        frequencySet.getData().add(new XYChart.Data<>(time + "", frequency));
        positionsSet.getData().add(new XYChart.Data<>(time + "", position));        
    }
    
    /**
     * MEthod to set charts
     * @param lc1 chart 1
     * @param lc2 chart 2
     */
    private static void setDopplerCharts(LineChart lc1, LineChart lc2)
    {
        frequencyChart=lc1;
        positionsChart=lc2;
    }
    
    /**
     * Enter data in the charts.
     * @param lc1 chart 1.
     * @param lc2 chart 2.
     */
    private static void enterDataInChart(LineChart lc1, LineChart lc2)
    {
        message = "Please ensure all required textfields are filled";
       
        try
        {
            // Create Charts.
            lc1 = new LineChart<>(xAxis_frequency, yAxis_frequency);
            lc2 = new LineChart<>(xAxis_position, yAxis_position);

            lc1.getData().clear(); // Clear the graph from any previous data.
            lc1.layout();          // ReLayout it, to make it look empty again.

            lc2.getData().clear(); // Clear the graph from any previous data.
            lc2.layout();          // ReLayout it, to make it look empty again.

            // Get datas.
            getDopplerCharts();           

            // Add datas
            positionsChart.getData().add(positionsSet);  
            frequencyChart.getData().add(frequencySet);   
            
        }
        
        catch(Exception e) // catch errors.
        {
            error = true;
            
            if(e instanceof NullPointerException)
            {
                SetMessage(controller, message);
            }
            if(e instanceof NumberFormatException)
            {
                SetMessage(controller, message);
            }
            if(e instanceof RuntimeException)
            {
                SetMessage(controller, message);
            }
            if(e instanceof IllegalArgumentException)
            {
              SetMessage(controller, message);
            }
        }
    }
    
    /**
     * Method that is called when the start button is clicked.
     * Creates the animation.
     * Creates the graph.
     * Catches any error.
     * Sends an appropriate message.
     * @param pane animation pane
     * @param lc1 chart 1 
     * @param lc2 chart 2
     * @param top_tf first textfield
     * @param mid_tf second textfield
     * @param bot_tf third textfield
     * @param controller controller of FXML.
     */
    public static void animation(AnchorPane pane, LineChart lc1, LineChart lc2, TextField top_tf, TextField mid_tf, TextField bot_tf ,FXMLDocumentController controller)
    { 
        // error message.
        message = "Please ensure all required textfields are filled";
        
        // Initialize values.
        DopplerCalculations.controller = controller;
        DopplerCalculations.doppler_pane = pane;
        
        // Clear pane
        pane.getChildren().clear();  
        pane.layout();                
        
        // Preload assets.
        AssetManager.preloadAllAssets();
        
        // Reset all booleans.
        error = false;    
        paused = false;   
        reseted = false;
        
        // Reset values.
        position = 0;
        time = 0;
        lastFrameTime = 0.0f;
        
        // Get system time.
        long initialTime = System.nanoTime();
        
        try
        {
            // Set background.
            pane.setBackground(AssetManager.getRaceTrackImg());

            // Get vlaues.
            setObserverVelocityValue(top_tf);
            setSourceVelocityValue(mid_tf);
            setInitialFrequencyValue(bot_tf);
            
            // Set charts.
            setDopplerCharts(lc1, lc2);
            lc1.setLegendVisible(false);
            lc2.setLegendVisible(false);

            // Add ambulance.
            AnimationObject ambulance = new AnimationObject(new Vector_2D(doppler_pane.getWidth()-20, 100), new Vector_2D(getTextFieldValue(top_tf)*-1*(pane.getWidth()/400),0), new Vector_2D(0, 0), 55);
            ambulance.getCircle().setFill(AssetManager.getAmbulanceImage());
            car.add(ambulance);
            addToPane(ambulance.getCircle());
            
            // Add race car.
            AnimationObject racecar = new AnimationObject(new Vector_2D(0, 190), new Vector_2D(getTextFieldValue(mid_tf)*(pane.getWidth()/400),0), new Vector_2D(0, 0), 50);
            racecar.getCircle().setFill(AssetManager.getRaceCarImg());
            car.add(racecar);
            addToPane(racecar.getCircle());
        }
        
        catch(Exception e) // Catch errors
        {
            error = true;
            
            if(e instanceof NullPointerException)
            {
                SetMessage(controller,message);
            }
            if(e instanceof NumberFormatException)
            {
                SetMessage(controller,message);
            }
            if(e instanceof RuntimeException)
            {
                SetMessage(controller,message);
            }
            if(e instanceof IllegalArgumentException)
            {
                SetMessage(controller,message);
            }
        }
   
        new AnimationTimer() // Animation timer for the animation to happen.
        {
            @Override
            public void handle(long now) 
            {
                // Time calcu;ation.
                double currentTime = (now - initialTime) / 1000000000.0;
                double  frameDeltaTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;
                
                time = currentTime;

                if(position < 400) 
                {
                    try // enter live data.
                    {
                       enterDataInChart(frequencyChart, positionsChart);
                    }
                    catch(Exception e) // catch errors.
                    {
                        error = true;
                        
                        if(e instanceof IllegalArgumentException)
                        {
                            SetMessage(controller,"There was a problem...");
                        }
                        if(e instanceof NullPointerException)
                        {
                            SetMessage(controller,"Please ensure all required textfields are filled");
                        }
                        if(e instanceof NumberFormatException)
                        {
                            SetMessage(controller,"Please ensure all inputs are numbers");
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
                    Reset(pane, lc1, lc2, top_tf, mid_tf, bot_tf); // And call the reset method.
                }
                
                for(AnimationObject obj : car)
                {
                        obj.update(frameDeltaTime);
                } 
                
            }
        }.start();
        
    }
    
    private static double dopplerHeadOn(double initial_freq, double v_s, double v_o)
    {   
        // Doppler function when approaching.
        double f_prime = ((Constants.v_sound + v_o)/(Constants.v_sound - v_s)) * initial_freq;
        return f_prime;
    }
    
    private static double dopplerMoveApart(double initial_freq, double v_s, double v_o)
    {
        // Doppler function when moving away.
        double f_prime = ((Constants.v_sound - v_o)/(Constants.v_sound + v_s))*initial_freq;
        return f_prime;
    }
    
    /**
     * Method that adds objects to the pane of animation.
     * @param node Object to be added.
     */
    private static void addToPane(Node node)
    {
        doppler_pane.getChildren().add(node); // Add object to pane.
    }
    
    /**
     * Method to reset the program.
     * @param pane to be reset
     * @param top_chart to be cleared.
     * @param bottom_chart to be cleared.
     * @param top_tf to be cleared.
     * @param mid_tf to be cleared.
     * @param bot_tf to be cleared
     */
    public static void Reset(AnchorPane pane, LineChart top_chart, LineChart bottom_chart, TextField top_tf, TextField mid_tf, TextField bot_tf)
    {
        reseted = true;   // Set the reset boolean to true.
        
        pane.getChildren().clear();              // clear the pane.
        top_tf.setText("");    // Clear all TextFields.
        mid_tf.setText("");
        bot_tf.setText("");
        
        // Clear graphs.
        positionsSet.getData().clear();
        frequencySet.getData().clear();
        
        top_chart.getData().clear();      // Clear graph data.
        top_chart.layout();               // Clear graph layout.
        
        bottom_chart.getData().clear();      // Clear graph data.
        bottom_chart.layout();               // Clear graph layout.
        
        // Reset values.
        position = 0;
        frequency = 0;
        time = 0;
        
        // Reset timers.
        yAxis_position.setAutoRanging(false);
        yAxis_position.setLowerBound(0);
        yAxis_position.setUpperBound(500);
        yAxis_position.setTickUnit(1);

        yAxis_frequency.setAutoRanging(false);
        yAxis_frequency.setLowerBound(0);
        yAxis_frequency.setUpperBound(500);
        yAxis_frequency.setTickUnit(1);

    }
    
    /**
     * Set true if pause is clicked.
     */
    public static void Pause()
    {
        paused = true;
    }
     
    /**
     * If help button is clicked.
     * @param cont 
     */
    public static void Help(FXMLDocumentController cont)
    {
        controller = cont;
        SetMessage(cont,"Enter the observer velocity, a source velocity, and initial frequency, in range(0-1000) then press start");
    }
}
