/*
 * Jasen Ratnam
 * Program for Computer Science with Maths.
 * In Vanier College.
 */
package integrativeproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Integrative project.
 * FXML controller for main menu and resistance FXML.
 * Controls the action of buttons.
 * @author Jasen Ratnam, SalvatoreBruzzese, Stephen Diep
 */
public class FXMLDocumentController implements Initializable 
{

    /**
     * Pane that will contain the animation.
     */
    @FXML
    protected AnchorPane animationPane;
    
    //Menu Items for user to choose from
    
    /**
     * Button to go to the projection motion animation
    */
    @FXML
    private Button Energy_butt;
    
    /**
     * Button to go to the Kinematic animation
    */
    @FXML
    private Button Kinematic_butt;
    
    /**
     * Button to go to the Resistance in a parallel circuit.
    */
    @FXML
    private Button Resistence_butt;
    
    /**
     * Button to go to the dipole moment animation
    */
    @FXML
    private Button Dipole_butt;
    
    /**
     * Button to go to the transverse wave animation
    */
    @FXML
    private Button Transverse_butt;
    
    /**
     * Button to go to the Doppler effect animation
    */
    @FXML
    private Button Doppler_butt;
    
    /**
     * Button to go back to the main menu.
    */
    @FXML 
    private Button Done;
    
    /**
     * Button to start the animation.
    */
    @FXML
    private Button Start;
    
    /**
     * Button to go to pause the animation
    */
    @FXML
    private Button Pause;
    
    /**
     * Button to go to reset the animation
    */
    @FXML
    private Button Reset;
    
    /**
     * Button to go to help the user during the program.
    */
    @FXML
    private Button Help;
    
    /**
     * Label to send a message to the user.
     */
    @FXML
    public Label messageLabel;
    
    /**
     * Label to send the function to the user.
     */
    @FXML
    public Label functionLabel;
    
    /**
     * TextField to hold the number of resistors.
    */
    @FXML
    private TextField TextField1;
    
    /**
     * TextField to hold the value of resistors.
    */
    @FXML
    private TextField TextField2;
    
    /**
     * TextField to hold the value of voltage.
    */
    @FXML
    private TextField TextField3;
    
    /**
     * TextField to hold the value of voltage.
    */
    @FXML
    private TextField TextField4;
    
    /**
     * Bar chart in the FXML
    */
    @FXML
    private BarChart<String,Number> ResistanceChart;
    
    /**
     * X-axis of the graph.
    */
    @FXML
    private CategoryAxis x;
    
    /**
     * Y-axis of the graph.
    */
    @FXML
    private NumberAxis y;
    
    @FXML
    private LineChart<Number,Number> TranverseChart;
    
    /**
     * X-axis of the graph.
    */
    @FXML
    private NumberAxis tranverseX;
    
    /**
     * Y-axis of the graph.
    */
    @FXML
    private NumberAxis tranverseY;
    
    /**
     * Kinematics line chart 1 on the top.
     */
    @FXML
    private LineChart<Double, Double> top_chart_kinematics;
    
    /**
     * Kinematics line chart 2 on the bottom.
     */
    @FXML
    private LineChart<Double, Double> bottom_chart_kinematics;
    
    /**
     * Doppler line chart top line chart
     */
    @FXML
    private LineChart<Double, Double> top_chart_doppler;
    
    /**
     * Doppler bottom line chart
     */
    @FXML
    private LineChart<Double, Double> bottom_chart_doppler;
    
    // Booleans to choose what function to do.
    
    private static boolean energy = false;
    
    private static boolean kinematics = false;
    
    private static boolean resistence = false;
    
    private static boolean dipole = false;
    
    private static boolean transverse = false;
    
    private static boolean doppler = false;
    
    
    /**
     * Action when projectile button is clicked.
    */
    
    @FXML
    private void openEnergy() throws IOException
    {
        SwitchWindow(Energy_butt, "kinematics_.fxml");  // Switch window
        energy = true;
    }
   
    /**
     * Action when kinematics button is clicked.
    */
    @FXML
    private void openKinematics() throws IOException
    {
        SwitchWindow(Kinematic_butt, "FXMLKinematics.fxml");  // Switch window
        kinematics = true;
    }
    
    /**
     * Action when resistance button is clicked.
    */
    @FXML
    private void openResistence() throws IOException
    {
        SwitchWindow(Resistence_butt, "FXML2.fxml");  // Switch window
        resistence = true;
    }
    
    /**
     * Action when dipole button is clicked.
    */
    @FXML
    private void openDipole() throws IOException
    {
        SwitchWindow(Dipole_butt, "dipole.fxml");  // Switch window
        dipole = true;
    }
    
    /**
     * Action when transverse button is clicked.
    */
    @FXML
    private void openTransverse() throws IOException
    {
        SwitchWindow(Transverse_butt, "FXMLTransverse.fxml");  // Switch window
        transverse = true;
    }
    
    /**
     * Action when Doppler button is clicked.
    */
    @FXML
    private void openDoppler() throws IOException
    {
        SwitchWindow(Doppler_butt, "FXMLDoppler.fxml");  // Switch window
        doppler = true;
    }
    
    /**
     * Action when done button is clicked in animation FXML.
    */
    @FXML
    private void Done() throws IOException
    {
        // Reset all booleans to choose new function.
        energy = false;
        kinematics = false;
        resistence = false;
        dipole = false;
        transverse = false;
        doppler = false;
        
        SwitchWindow(Done, "FXMLDocument.fxml");  // Switch window
    }
    
    /**
     * Action when start button is clicked in animation FXML.
     */
    @FXML
    private void Start() throws InterruptedException
    {
        // If resistence was clicked.
        if(resistence == true)
        {
            ResistanceCalculation.animation(animationPane, TextField1,TextField2, TextField3,ResistanceChart, x, y,this);
        } 
        
        // If tranverse was clicked.
        if(transverse == true)
        {
            TranverseWaveCalculation.animation(animationPane, TextField1, TextField2, TextField3, TextField4, TranverseChart, tranverseX, tranverseY, this);
        }
        
        // If kinematics was clicked.
        if(kinematics == true)
        {
            KinematicsCalculations.animation(animationPane, top_chart_kinematics, bottom_chart_kinematics, TextField1, TextField2, this);
        }
        
        // If doppler was clicked.
        if(doppler == true)
        {
            DopplerCalculations.animation(animationPane, top_chart_doppler, bottom_chart_doppler, TextField1, TextField2, TextField3, this);
        }
    }
    
    /**
     * Action when pause button is clicked in animation FXML.
     */
    @FXML
    private void Pause() throws IOException
    {
        // If tranverse was clicked
        if(transverse == true)
        {
            TranverseWaveCalculation.paused(); // Set paused to true/ pauses the animation.
        }
        
        // If resistance was clicked.
        if(resistence == true)
        {
            ResistanceCalculation.paused = true; // Set paused to true/ pauses the animation.
        } 
        
        // If kinematics was clicked.
        if(kinematics == true)
        {
            KinematicsCalculations.Pause();
        }
        
        // If doppler was clicked.
        if(doppler == true)
        {
            DopplerCalculations.Pause();
        }
    }
    
    /**
     * Action when reset button is clicked in animation FXML.
     */
    @FXML
    private void Reset() throws IOException
    {
        // If tranverse was clciked.
        if(transverse == true)
        {
            TranverseWaveCalculation.Reset(animationPane, TranverseChart, TextField1, TextField2, TextField3, TextField4);
        } // Reset the animation, clear everything.
        
        // If reesistance was clicked.
        if(resistence == true)
        {
            ResistanceCalculation.Reset(animationPane, ResistanceChart, TextField1, TextField2, TextField3);
        } // Reset the animation, clear everything.
        
        // If kinematics was clicked.
        if(kinematics == true)
        {
            KinematicsCalculations.Reset(animationPane, top_chart_kinematics, bottom_chart_kinematics, TextField1, TextField2);
        }
        
        // If doppler was clicked.
        if(doppler == true)
        {
            DopplerCalculations.Reset(animationPane, top_chart_doppler, bottom_chart_doppler, TextField1, TextField2 ,TextField3);
        }
    }
    
    /**
     * Action when help button is clicked in animation FXML.
     */
    @FXML
    private void Help() throws IOException
    {
        // If tranverse was clicked.
        if(transverse == true)
        {
            TranverseWaveCalculation.Help(this);
        }
        
        // If resitance was clicked.
        if(resistence == true)
        {
            ResistanceCalculation.Help(this);
        } // Gives help message in FXML/ Tells what to do to the user.
    
        // If kinematics was clicked.
        if(kinematics == true)
        {
            KinematicsCalculations.Help(this);
        }
        
        // If doppler was clicked.
        if(doppler == true)
        {
            DopplerCalculations.Help(this);
        }
    }
        
    /**
     * Method to set the message on the FXML
     * @param message to be set in the FXML
     */
    public void setMessageLabel(String message)
    {
        messageLabel.setText(message); // change the text in a label.
    }
    
     /**
     * Method to set the function on the FXML
     * @param message to be set in the FXML
     */
    public void setFunctionLabel(String message)
    {
        functionLabel.setText(message); // change the text in a label.
    }
    
    /**
     * Method to switch from different FXML's files. 
     */
    private void SwitchWindow(Button butt, String fxml) throws IOException 
    {
        Stage stage;                             // Initialize the stage.
        Parent root;                             // Initialie the root.
        
        //get reference to the button's stage         
        stage =(Stage) butt.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource(fxml));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);                 // Set the scene.
        stage.show();                          // Show the scene.
    } 
    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
       
    }
    
}
