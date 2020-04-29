package integrativeproject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

/*
 * @author Steven Diep
 */
public class Kinematics_Controller implements Initializable {


    @FXML
    private Button start_button;
    @FXML
    private Label info_label;
    @FXML
    private TextField input_1;
    @FXML
    private TextField input_2;
    @FXML
    private TextField height_input;
    @FXML
    private Button done_button;
    @FXML
    private Button pause_button;
    @FXML
    private Button reset_button;
    @FXML
    private Button help_button;
    @FXML
    private Label input_infos;
    @FXML
    private BarChart<?,?> chart_2;
    @FXML
    private Label velocity;
    @FXML
    private Label height;
    @FXML
    AnchorPane pane;
    
    private boolean reseted;
    
    private boolean paused;
    
    private boolean played;

    private double lastFrameTime = 0.0;
    
    private boolean error = true;
    
    private ArrayList<AnimationObject> circleList = new ArrayList<AnimationObject>();

    // ADD PANE FOR THE BALL
    public void addToPane(Node node)
    {
        pane.getChildren().add(node);
    }
   
    
    @FXML
    private void start_animation() throws InterruptedException 
    {
        circleList.clear();
        pane.getChildren().clear();
        paused = false;
        reseted = false;
        
        // IF THE USER DOESN'T ENTER AN INTEGER, THE PROGRAM WILL QUIT
        boolean inputAccepted = false;
        while (!inputAccepted) 
        {
            try {
                Integer.valueOf(Integer.parseInt(input_1.getText()));
                Integer.valueOf(Integer.parseInt(input_2.getText()));
                Integer.valueOf(Integer.parseInt(height_input.getText()));
                inputAccepted = true;
            } 
            catch (NumberFormatException e) 
            {
                error = true;
            }
        }
        
        
        if(input_1.getText().isEmpty()|| input_2.getText().isEmpty() || height_input.getText().isEmpty()) // IF THE USER DOESN'T ENTER ANY VALUES 
        {
            error = true;
            info_label.setText("Error: Please enter inputs");
        }
        else if(Integer.parseInt(input_1.getText()) > 10 || Integer.parseInt(input_2.getText()) > 400 || Integer.parseInt(height_input.getText()) > 125) //IF THE USER GOES ABOVE THE MAXUMUM VALUES
        {
            error = true;
            info_label.setText("Your input is bigger than the maximum");
        }       
        else 
        {      
             
            info_label.setText("");
            input_infos.setText("Mass input: " + input_1.getText() + "\n" + "Velocity input: " + input_2.getText() + "\n" + "Height input: " + height_input.getText());
            
            // FORMULA FOR KINETIC
            int m = Integer.parseInt(input_1.getText());
            int v = Integer.parseInt(input_2.getText());
            int k = (m * (v * v)) / 2 ;
            String result = String.valueOf(k);
            
            // FORMULA FOR POTENTIAL
            int h = Integer.parseInt(height_input.getText());
            int g = 10;
            int u = m * g * h; 
            String result_2 = String.valueOf(u);

            
            // GRAPH 
            XYChart.Series series = new XYChart.Series();
            XYChart.Series series_2 = new XYChart.Series();
            
            // DATA FOR KINETIC
            double point_max_k = k;
            double point_at_0 = 0;
            double velocity_after_time = v / 5;
            double velocity_2 = point_at_0 + velocity_after_time;
            double velocity_3 = velocity_2 + velocity_after_time;
            double velocity_4 = velocity_3 + velocity_after_time;
            double velocity_5 = velocity_4 + velocity_after_time;
            double velocity_6 = velocity_5 + velocity_after_time;
            
            double point_2_K = (m * (velocity_2 * velocity_2)) / 2;
            double point_3_K = (m * (velocity_3 * velocity_3)) / 2;
            double point_4_K = (m * (velocity_4 * velocity_4)) / 2;
            double point_5_K = (m * (velocity_5 * velocity_5)) / 2;
            double point_6_K = (m * (velocity_6 * velocity_6)) / 2;
            
            // DATA FOR POTENTIAL
            double point_max_u = u;
            double height_after_time = h / 5;
            double height_2 = h - height_after_time;
            double height_3 = height_2 - height_after_time;
            double height_4 = height_3 - height_after_time;
            double height_5 = height_4 - height_after_time;
            double height_6 = height_5 - height_after_time;
            double height_7 = height_6 - height_after_time;
            double height_8 = 0.0;
            
            double point_2_E = m * g * height_2;
            double point_3_E = m * g * height_3;
            double point_4_E = m * g * height_4;
            double point_5_E = m * g * height_5;
            double point_6_E = m * g * height_6;
            double point_7_E = m * g * height_7;
            
            // ADD THE INPUTS TO THE SERIES
            series.getData().add(new XYChart.Data("0", point_at_0));
            series_2.getData().add(new XYChart.Data("1", point_max_u));      
            series.getData().add(new XYChart.Data("2", point_2_K));
            series_2.getData().add(new XYChart.Data("3", point_2_E));
            series.getData().add(new XYChart.Data("4", point_3_K));
            series_2.getData().add(new XYChart.Data("5", point_3_E));
            series.getData().add(new XYChart.Data("6", point_4_K));
            series_2.getData().add(new XYChart.Data("7", point_4_E));
            series.getData().add(new XYChart.Data("8", point_5_K));
            series_2.getData().add(new XYChart.Data("9", point_5_E));
            series.getData().add(new XYChart.Data("10", point_6_K));
            series_2.getData().add(new XYChart.Data("11", point_6_E));

            
            // ADD THE SERIES TO THE CHARTS
            chart_2.getData().add(series);  
            chart_2.getData().add(series_2);
            

            // VELOCITY VALUES
            velocity.setText(Double.toString(point_at_0) + " m/s" + "\n"  
                    + Double.toString(velocity_2) + " m/s" + "\n"  
                    + Double.toString(velocity_3) + " m/s" + "\n"   
                    + Double.toString(velocity_4) + " m/s" + "\n" 
                    + Double.toString(velocity_5) + " m/s" + "\n"   
                    + Double.toString(velocity_6) + " m/s" + "\n"  
                    + Double.toString(v) + " m/s" );
            
            // HEIGHT VALUES
            height.setText(Double.toString(h) + " m" + "\n" 
                    + Double.toString(height_2) + " m" + "\n" 
                    + Double.toString(height_3) + " m" + "\n" 
                    + Double.toString(height_4) + " m" + "\n"  
                    + Double.toString(height_5) + " m" + "\n"  
                    + Double.toString(height_6)+ " m" + "\n"  
                    + Double.toString(0) + " m" + "\n");

            
        // ANIMATION PART
        lastFrameTime = 0.0f;
        long initialTime = System.nanoTime();
        
        new AnimationTimer()
        {
            @Override
            public void handle(long now) {
                // TIME CALCULATION              
                double currentTime = (now - initialTime) / 1000000000.0;
                double  frameDeltaTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;

                // ADD 1 CIRCLE
                if (1 > circleList.size())
                {   
                    AnimationObject circle = new AnimationObject( new Vector_2D(50, 50), 
                                                        new Vector_2D(0.0,v),
                                                        new Vector_2D(0.0, 9.8),
                                                        20);
                    circleList.add(circle);
                    addToPane(circle.getCircle()); 
                }
                
                if (circleList.get(0).getPosition().getY() >= 305)
                {
                    this.stop();
                }
                
                // RESET THE ANIMATION
                if(reseted == true)
                {
                    this.stop();
                    pane.getChildren().clear();
                }
                
                if(reseted == false)
                {
                    // UPDATE EXISTING CIRCLE
                    for (AnimationObject obj : circleList)
                    {
                        if(paused == false)
                        {
                            obj.update(frameDeltaTime); 
                        }
                    }
                }
            }
        }.start();
        
        }
    } 

    // SWITCH BACK TO THE MAIN MENU
    @FXML
    private void done_main_menu() throws IOException
    { 
        SwitchWindow(done_button, "FXMLDocument.fxml"); // GO BACK TO MAIN MENU
    } 
    
    // METHOD THE HELP BUTTON
    @FXML
    private void gives_help() 
    {
        info_label.setText("The maximum mass is 10kg, the maximum velocity is 400m/s and the maximum height is 125m ");
    }
    
    // METHOD FOR THE PAUSE BUTTON
    @FXML
    private void pause_animation()
    {
        paused = true;
    }
    // RESET EVERYTHING BACK TO DEFAULT VALUES
    @FXML
    private void reset_everything() 
    { 
        reseted = true;
        input_1.setText("");
        input_2.setText("");
        height_input.setText("");
        info_label.setText("");
        input_infos.setText("Mass input: " + "\n" + "Velocity input: " + "\n" + "Height input: ");
        velocity.setText("");
        height.setText("");
        chart_2.getData().clear(); 
        circleList.remove(0);
        pane.getChildren().clear();
        // RESET BACK BOOLEAN TO FALSE
        paused = false;
        played = false;
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
