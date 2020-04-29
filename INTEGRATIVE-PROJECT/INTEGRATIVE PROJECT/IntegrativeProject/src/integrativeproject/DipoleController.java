package integrativeproject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author stevendiep
 */
public class DipoleController implements Initializable
{
    @FXML
    private Button start_button;
    @FXML
    private Label message_label;
    @FXML
    private TextField distance;
    @FXML
    private TextField magnitude;
    @FXML
    private Button done_button;
    @FXML
    private Button pause_button;
    @FXML
    private Button reset_button;
    @FXML
    private Button help_button;
    @FXML
    private Button play_button;
    @FXML
    private Label input_label;
    @FXML
    private LineChart<?,?>chart_1;
    @FXML
    AnchorPane pane;
    @FXML
    AnchorPane pane_2;
    
    private boolean reseted;
    
    private boolean paused;
    
    private boolean played;

    private double lastFrameTime = 0.0;
    
    private ArrayList<AnimationObject> circleList;
    
    private ArrayList<AnimationObject> circleList_2;

    
    // ADD FIRST PANE FOR BALL # 1
    public void addToPane(Node node)
    {
        pane.getChildren().add(node);
    }
    
    // ADD SECOND PANE FOR BALL # 2
    public void addToPane2(Node node)
    {
        pane_2.getChildren().add(node);
    }
    
    
    
    @FXML
    private void start_animation() 
    {
        paused = false;
        reseted = false;
        played = false;
        
        // IF USER INPUT SOMETHING OTHER THAN AN INTEGER, PROGRAM WILL QUIT
        boolean inputAccepted = false;
        while (!inputAccepted) 
        {
            try 
            {
                Integer.valueOf(Integer.parseInt(distance.getText()));
                Integer.valueOf(Integer.parseInt(magnitude.getText()));
                inputAccepted = true;
            } 
            
            catch (NumberFormatException e) 
            {
                System.exit(0);
            }
        }
        
        
        if(distance.getText().equals("") || magnitude.getText().equals("")) // IF THE USER DOESN'T ENTER ANY VALUES
        {
            message_label.setText("Error: Please enter inputs");
        }
        else if(Integer.parseInt(distance.getText()) > 50 || Integer.parseInt(magnitude.getText()) > 150) // IF THE VALUES GO ABOVE MAXIMUM
        {
            message_label.setText("Error: Please enter smaller values");
        }
        else
        {
 
            message_label.setText("");
            input_label.setText("Distance: " + distance.getText() + "\n" + "Magnitude: " + magnitude.getText());
        
        
        // FORMULA FOR DIPOLE MOMENT
        double d = Double.parseDouble(distance.getText()); 
        double q = Double.parseDouble(magnitude.getText()); 
        double p = d * q;
        
        // DATA FOR THE GRAPH
        double q_1 = q + 1;
        double q_2 = q + 2;
        double q_3 = q + 3;
        double q_4 = q + 4;
        
        double p_1 = d * q_1;
        double p_2 = d * q_2;
        double p_3 = d * q_3;
        double p_4 = d * q_4;
        
        // GRAPH 
        XYChart.Series series = new XYChart.Series();
        
        series.getData().add(new XYChart.Data("0", p));
        series.getData().add(new XYChart.Data("1", p_1));
        series.getData().add(new XYChart.Data("2", p_2));
        series.getData().add(new XYChart.Data("3", p_3));
        series.getData().add(new XYChart.Data("4", p_4));
        
        // ADD THE SERIES TO THE CHARTS
        chart_1.getData().add(series);  
        
      
        // ANIMATION PART
        lastFrameTime = 0.0f;
        long initialTime = System.nanoTime();

        circleList = new ArrayList<AnimationObject>();
        circleList_2 = new ArrayList<AnimationObject>();
        
        new AnimationTimer()
        {
            @Override
            public void handle(long now) {
                // TIME CALCULATION              
                double currentTime = (now - initialTime) / 1000000000.0;
                double  frameDeltaTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;

                // ADD 2 CIRCLES
                if (1 > circleList.size())
                {                       
                    AnimationObject circle = new AnimationObject( new Vector_2D(100, 100), 
                                                        new Vector_2D(10.0,0.0),
                                                        new Vector_2D(0.0, 0.0),
                                                        20);
                    AnimationObject circle2 = new AnimationObject( new Vector_2D(175, 100), 
                                                        new Vector_2D(-10.0,0.0),
                                                        new Vector_2D(0.0,0.0),
                                                        20);
                    circleList.add(circle);
                    circleList_2.add(circle2);
                    addToPane(circle.getCircle());  
                    addToPane2(circle2.getCircle());
                
                // RESET ANIMATION
                if(reseted == true)
                {
                    this.stop();
                    pane.getChildren().clear();
                    pane_2.getChildren().clear();
                }
                    
                    
                }
                
                if(reseted == false)
                {
                // UPDATE EXISTING CIRCLE
                for (AnimationObject obj : circleList)
                {
                    if(paused == true)
                    {
                        if(played == true)
                    {
                       obj.update2(frameDeltaTime);
                    } 
                    }
                    else if(paused == false)
                    {
                       obj.update2(frameDeltaTime); 
                    }

                }
                
                for (AnimationObject obj : circleList_2)
                {
                    if(paused == true)
                    {
                        if(played == true)
                    {
                        obj.update3(frameDeltaTime); 
                    }
                    }
                    else if(paused == false)
                    {
                        obj.update3(frameDeltaTime); 
                    }
                }
                }
            }
            
        }.start();
        }
    }
    
    // RESET EVERYTHING BACK TO DEFAULT VALUES
    @FXML
    private void reset_everything() 
    { 
        distance.setText("");
        magnitude.setText("");
        message_label.setText("");
        input_label.setText("input 1 input: " + "\n" + "input 2 input: ");
        chart_1.getData().clear();
        reseted = true;
        circleList.remove(0);
        circleList_2.remove(0);
        pane.getChildren().clear();
        pane_2.getChildren().clear();
        // RESET BACK BOOLEAN TO FALSE
        paused = false;
        played = false;
    }
    
    // METHOD FOR THE PLAY BUTTON
    @FXML
    public void play_animation()
    {
        played = true;
    }
    
    // METHOD FOR THE PAUSE BUTTON
    @FXML
    public void pause_animation()
    {
        paused = true;
    }
    
    // SWITCH BACK TO THE MAIN MENU
    @FXML
    private void done_main_menu() throws IOException
    { 
        SwitchWindow(done_button, "FXMLDocument.fxml");
    } 
    
    // METHOD FOR THE HELP BUTTON
    @FXML
    private void help_button()
    {
        message_label.setText("The maximum distance is 50m and the maximum magnitude is 150");
    }
    
    private void SwitchWindow(Button butt, String fxml) throws IOException 
    {
        Stage stage; 
        Parent root;
        
        //get reference to the button's stage         
        stage=(Stage) butt.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource(fxml));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } 
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       
    }    
    
}
