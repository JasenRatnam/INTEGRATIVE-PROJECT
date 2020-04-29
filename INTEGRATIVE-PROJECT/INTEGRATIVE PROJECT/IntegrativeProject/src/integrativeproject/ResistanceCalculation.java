/*
 * Jasen Ratnam
 * Program for Computer Science with Maths.
 * In Vanier College.
 */
package integrativeproject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * Integrative project.
 * Resistors in a parallel circuit.
 * Take numbers of resistors, value of resistors and the voltage of the circuit.
 * Gives the current at every resistor, the total current, and the total resistance.
 * @author Jasen Ratnam 1622549
 */
public class ResistanceCalculation 
{

    /**
     * Boolean used to Debug the code in case of errors.
     * Activated if true.
     */
    private static boolean DEBUG = false; 

    /**
     * initialize the last time frame.
     */
    private static double lastFrameTime;
    
    /**
     * initalize the starting time of the program.
     */
    private static long initialTime;
    
    /**
     * Format to round of numbers to two digits after the decimal.
     */
    private static final DecimalFormat df2 = new DecimalFormat(".##");
    /**
     * Int to save the number of electrical current flow icon from the wires removed from pane.
     */
    private static int  numCurrentIconDestroyed = 0;
    /**
     * Int to save the number of electrical current flow from the the middle resistors icon removed from pane.
     */
    private static int  numCurrentIconMiddleDestroyed = 0;

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
     * Int to save the number of resistors in the circuit.
     */
    public static int resistorsNumber = 0;        // Initialze the Int to save the Int format of reeistors numbers.

    /**
     * Int to save the voltage value of the circuit.
     */
    private static int voltage = 0;              // Initialize the Int to save the Int format of the voltage value.
    
    /**
     * String to save the message to be displayed in the FXML label.
     */
    private static String message = "";         // Initislize the String to save the messages.
    
    /**
     * List that contains the double value of the resistors in the circuit.
     */
    private static List<Double> resistorsValue;
    
    /**
     * FXML controller that is used to change the label
     */
    private static FXMLDocumentController controller;
    
    /**
     * Double to store the total resistance of the circuit.
     */
    private static double totalResistance = 0.0;
    
    /**
     * List that contains the double value of the current of each resistor in the circuit.
     */
    private static List<Double> currentValue;
    
    /**
     * Double that contains the total current value of the circuit.
     */
    private static double totalCurrentValue = 0.0; 
    
    /**
     * Set that will hold the data of resistance.
     */
    private static XYChart.Series<String, Number> resistenceSet;
    
    /**
     *  Set that will hold the data for the current of the circuit.
     */
    private static XYChart.Series<String, Number> currentSet;
    
    /**
     * The graph that will be in the FXML file.
     */
    private static BarChart<String,Number> ResistanceChart;
    
    /**
     * The X-axis of the graph.
     */
    private static CategoryAxis x;
    
    /**
     * THe Y-axis of the graph
     */
    private static NumberAxis y;
    
    /**
     * The anchor pane where all the animations will happen
     */
    public static AnchorPane pane;
    
    /**
     * Create an arrayList to hold the electron shots in the wires
     */
    private static ArrayList<GameObject> circuitShot;
    /**
     * Create an arrayList to hold the electron shots in the middle resistors.
     */
    private static ArrayList<GameObject> circuitShotMiddle;
    
    
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
            ResistanceCalculation.controller.setMessageLabel(message);
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
        
    /**
     * Method that acts when the button HELP is clicked by the user.
     * Prints a message that helps the user use the program.
     * @param controller The controller for the FXML file that contains the label to be modified.
     */
    public static void Help(FXMLDocumentController controller)
    {
        // Message to be labeled in the FXML.
        // Message tells the user what to do.
        message = "Enter the number of resistors wanted, the values for each resistors and the voltage value of the circuit. The program wiil show the total resistance and total current.";
        SetMessage(controller,message); // Sets the message on the label in the FXML file.
    }
     
    /**
     * Method to get the number of resistors wanted by the user in Int format.
     * @param resistorsNumberTextField TextField that contains the number of resistors that is wanted by the user.
     * @param controller The controller for the FXML file that contains the label to be modified.
     * @return The number of resistors.
     */
    private static int getResistorsNumber(TextField resistorsNumberTextField)
    {
        String resistorsNumberString = resistorsNumberTextField.getText();                       // Gets the String format of the textfield with the number of resistors.
        message = "Please enter a single integer between 1 & 10  for the number of resistors.";  // Message to display in case of an error.
        
        try
        { 
            resistorsNumber = Integer.parseInt(resistorsNumberString);  // Save the number of resistors in the Int format from the String format.
        } 
        catch(NumberFormatException | NullPointerException e) 
        { 
            SetMessage(controller, message);   // If an error is catched than change the message to the error message.
            error = true;                      // Set the error boolean to true since an error is caught.
            
            if(DEBUG == true) // Print text to help debug code if DEBUG is true.
            {
                System.out.println("ERROR: getResistorsNumber(): AN error happened in this method."); // Print error message.
                Logger.getLogger(ResistanceCalculation.class.getName()).log(Level.SEVERE, null, e);   // Print error log.
            }
        }
    
        if(resistorsNumber > 10 || resistorsNumber < 1) // Check if value is out of bounds.
        {
            SetMessage(controller, message); // If an error is catched than change the message to the error message.
            error = true;                    // Set the error boolean to true since an error is caught.
        }
        
        if(DEBUG == true) // Print text to help debug code if DEBUG is true. Sends the resistor values in int an string to see if conversion worked.
        {
            System.out.println("getResistorsNumber(): Number of resistors(String): " + resistorsNumberString);  // String Value.
            System.out.println("getResistorsNumber(): Number of resistors(int): " + resistorsNumber);           // Int Value.
        }
        
        return resistorsNumber;
    }
    
    /**
     * Method to get the Voltage value of th circuit given by the user in Int format.
     * @param voltageTextField TextField that contains the number of resistors that is wanted by the user.
     * @param controller The controller for the FXML file that contains the label to be modified.
     * @return The voltage value.
     */
    private static int getVoltageValue(TextField voltageTextField)
    {
        String number = voltageTextField.getText();		      // Gets the String format of the textfield with the voltage value.
        message = "Please enter a value of voltage greater than 1.";  // Message to display in case of an error.
        
        try
        { 
            voltage = Integer.parseInt(number);                     // Save the number of resistors in the Int format from the String format.
        } 
        catch(NumberFormatException | NullPointerException e) 
        { 
            SetMessage(controller, message);                       // If an error is catched than change the message to the error message.
            error = true;                                          // Set the error boolean to true since an error is caught.
            
            if(DEBUG == true) // Print text to help debug code if DEBUG is true.
            {
                
                System.out.println("ERROR: getVoltageValue(): AN error happened in this method.");   // Print error has happened.
                Logger.getLogger(ResistanceCalculation.class.getName()).log(Level.SEVERE, null, e);  // Print error log.
            }
        }
    
        if(voltage < 1) // Check if value is out of bounds.
        {
            SetMessage(controller, message);                       // If an error is catched than change the message to the error message.
            error = true;                                          // Set the error boolean to true since an error is caught.
        }
        
        if(DEBUG == true) // Print text to help debug code if DEBUG is true
        {
            System.out.println("getVoltageValue(): Voltage value(String): " + number); // String value.
            System.out.println("getVoltageValue(): Voltage value(int): " + voltage);   // Int value.
        }
        
        return voltage;
    }
    
    /**
     * Method to get the the value of all resistors in the circuit given by the user in Double format.
     * @param resistorsValueTextField   TextField that contains the value of resistors that is given by the user.
     * @param controller The controller for the FXML file that contains the label to be modified.
     * @return The value of resistors.
     */
    private static List<Double> getresistorsValue(TextField resistorsValueTextField)
    {
        String value = resistorsValueTextField.getText();  // Gets the String format of the textfield with the the resistors values.
        Scanner scanner = new Scanner(value);              // Add a scanner to scan the string for resistors values.
        resistorsValue = new ArrayList<>();                // Initialize the Double List for the resitors values.
        message = "Please enter the same number of register values as number of register selected."; // Message in case of an error.
        
        if(DEBUG == true)  // Print text to help debug code if DEBUG is true
        {
            System.out.println("getresistorsValue() : Resistor values(String): " + value);  // Prints the String value of the resitors values.
        }
        
        int index = 1; // Index used to help show wich resistor is being processed.
        double resistorValue = 0; // Temporary double to store the value of the current resistors.
        while (scanner.hasNextDouble()) // Loop all values of resistors in the String.
        {
            try
            { 
                resistorValue = scanner.nextDouble(); // Value of resistor. 
                resistorsValue.add(resistorValue);    // Add to the value to the double list.
            } 
            catch(NumberFormatException | NullPointerException e) 
            { 
                SetMessage(controller, message);                       // If an error is catched than change the message to the error message.
                error = true;                                          // Set the error boolean to true since an error is caught.
                
                if(DEBUG == true) // Print text to help debug code if DEBUG is true.
                {
                    System.out.println("ERROR: getresistorsValue(): AN error happened in this method."); // Print error message.
                    Logger.getLogger(ResistanceCalculation.class.getName()).log(Level.SEVERE, null, e);  // Print error log.
                }
            }
            if(DEBUG == true) // Print text to help debug code if DEBUG is true.
            {
                System.out.println("getresistorsValue() : Resistor value of resistor #" + index + " : " + resistorValue); // Print the value of the resitor being processed.
                index++;  // Increment the index.
            }  
        }	
        
        // Error if the number of resistors and the number of the resistors values do not match. 
        if(resistorsValue.size() != resistorsNumber) 
        {
            SetMessage(controller, message);                       // If an error is catched than change the message to the error message.
            error = true;                                          // Set the error boolean to true since an error is caught.
        }
        
        if(DEBUG == true) // Print text to help debug code if DEBUG is true.
        {
            System.out.println("getresistorsValue() : Value of resistors (List<double>): " + resistorsValue); // Print the double list of resitor values.
        }
        
        return resistorsValue;
    }   
    
    /**
     * Method to to get the total resistance of the circuit.
     * @return The total resistance value of the circuit
     */
    private static double getTotalResistance()
    {
        //1/Rt = 1/R1 + 1/R2 +... + 1/Rn
        
        double oneOverTotalResistance = 0;  // Temporary double to store the value of on over the value of a resistor.      
        
        if(error == false) // Do this only if no error happened or it fucks every thing up.
        {
            for(int i = 0; i < resistorsNumber; i++)  // Loop all ressitors values to get the one over total ressitence value
            {
                if(DEBUG == true) // Print text to help debug code if DEBUG is true.
                {
                    System.out.println("getTotalResistance(): Value of one over resitor #" + (i+1) + ": " + (1/(resistorsValue.get(i)))); // Print the one over resistor value for each resistor.
                }

                oneOverTotalResistance += (1/(resistorsValue.get(i))); // Get one over total resistance.
            }

        totalResistance = (1/oneOverTotalResistance); // Get the total resitance value.
        }
                
        if(DEBUG == true) // Print text to help debug code if DEBUG is true.
        {
            System.out.println("getTotalResistance(): Value of one over total resitor: " + oneOverTotalResistance); // Print the one over total resistance.
            System.out.println("getTotalResistance(): Value of total resistance: " + totalResistance);              // Print the total ressitance.
        }
        
        return totalResistance;
    }   
    
    /**
     * Method that gets the current value of each resistor and puts it in a list.
     * @return a List with current value of each resistor.
     */
    private static List<Double> getCurrentValues()
    {
        //Current flowing in Ii = VS ÷ Ri = 
        
        if(error == false) // Do this only if no error happened or it fucks every thing up.
        {
             currentValue = new ArrayList<>();                 // Initialize the Double List for the resitors values.

            for(int i = 0; i < resistorsNumber; i++)          // Loop to get the current value of each resistor. Goea to evry resistor.
            {
                currentValue.add(voltage/resistorsValue.get(i));  // Add the current value of each resistor to the double list.

                if(DEBUG == true) // Print text to help debug code if DEBUG is true.
                {
                    System.out.println("getCurrentValues(): Value of current at resistor #" + (i+1) + ": " + currentValue.get(i)); // Print the current value of each resistor.
                }
            }
        }
       

        if(DEBUG == true) // Print text to help debug code if DEBUG is true.
        {
            System.out.println("getCurrentValues(): List of values of all current for all resistors(List<double>): " + currentValue); // Print the list of the curent value for every resistor,
        }
       
       return currentValue;
    }
    
    /**
     * Method that gets the total current of the circuit. 
     * @return The total current of the circuit.
     */
    private static double getTotalCurrent()
    {
        //Current flowing in It = I1 + I2 + ... + In    
        totalCurrentValue = 0;
        
        if(error == false) // Do this only if no error happened or it fucks every thing up.
        {
             for(int i = 0; i < resistorsNumber; i++)    // Loop that goes trough every resistor and adds up the current value for every resistor.
            {
               totalCurrentValue += currentValue.get(i); // add upp the current of the vresistors.
            }
        }
       

        if(DEBUG == true) // Print text to help debug code if DEBUG is true.
        {
            System.out.println("getTotalCurrent(): Total value of current in circuit: " + totalCurrentValue); // prints the total double current value of the circuit.
        }
        
        return totalCurrentValue;
    }
    
    /**
     * Methods that gives a set with the resistance data.
     * @return The set of resistance data.
     */
    private static void getResistenceBarChart ()
    {
        resistenceSet = new XYChart.Series<>(); // Initialize the set.
        
        resistenceSet.setName("Resistance"); // Set the name of the Set.

        for(int i = 0; i < resistorsNumber; i++) // Loop through every ressitor and add it to the set.
        {
            resistenceSet.getData().add(new XYChart.Data("R" + (i+1),resistorsValue.get(i))); // Add every resistor to the set.
        }

        resistenceSet.getData().add(new XYChart.Data("RT",totalResistance)); // Add the total resistance to the set.
    }
    
    /**
     * Method that makes a set that holds all the data of the current of the circuit.
     * @return The set with the data of the current of the circuit.
     */
    private static void getCurrentBarChart ()
    {
        currentSet = new XYChart.Series<>();  // Initialize the set.
        currentSet.setName("Current");
        for(int i = 0; i < resistorsNumber; i++) // Set the name of the Set.
        {
            currentSet.getData().add(new XYChart.Data("R" + (i+1),currentValue.get(i))); // Add the current of every resistor to the set.
        }

        currentSet.getData().add(new XYChart.Data("RT",totalCurrentValue)); // Add the total current to the set.
    }
    
    /**
     *  Method that sets up the graph and adds the data to it.
     */
    private static void enterDataInChart ()
    {
        ResistanceChart.getData().clear(); // Clear the graph from any previous data.
        ResistanceChart.layout();          // ReLayout it, to make it look empty again.
        
        getResistenceBarChart();           // Get resistanse data set.
        getCurrentBarChart();              // Get current data set.
        
        if(error == false) // If no error has happen previously than set the axis and add data.
        {
            x.setLabel("Resistor number");                    // Set the X-axis.
            y.setLabel("Value of resistence & current");                // Set the Y-axis.  
            ResistanceChart.getData().addAll(resistenceSet);  // Add the resistance set.
            ResistanceChart.getData().addAll(currentSet);     // Add the current set.
            ResistanceChart.setLegendSide(Side.TOP);          // Add a legend to the graph.
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
    
    /**
     * Method to reset the program.
     * @param pane to be reset
     * @param ResistanceChart to be cleared.
     * @param resistorsNumberTextField to be cleared.
     * @param resistorsValueTextField to be cleared.
     * @param voltageTextField to be cleared
     */
    public static void Reset(AnchorPane pane, BarChart<String,Number> ResistanceChart, TextField resistorsNumberTextField, TextField resistorsValueTextField, TextField voltageTextField)
    {
        reseted = true;                          // Set the reset boolean to true.
        pane.getChildren().clear();              // clear the pane.
        resistorsNumberTextField.setText("");    // Clear all TextFields.
        resistorsValueTextField.setText("");
        voltageTextField.setText("");
        ResistanceChart.getData().clear();      // Clear graph data.
        ResistanceChart.layout();               // Clear graph layout.
        SetMessage(controller, "Reseted");
    }
    
    /**
     * Method that is called when the start button is clicked.
     * Creates the animation.
     * Creates the graph.
     * Catches any error.
     * Sends an appropriate message.
     * @param                          pane where the animations will happen.
     * @param resistorsNumberTextField textField that contains the number of resistors.
     * @param resistorsValueTextField  textField that contains the values of the resistors.
     * @param voltageTextField         textField that contains the voltage value of the circuit.
     * @param ResistanceChart          Barchart that will contain the data.
     * @param x                        the X-axis. 
     * @param y                        the Y-axis.
     * @param controller               Controller that controls the message label.
     */
    public static void animation(AnchorPane pane, TextField resistorsNumberTextField, TextField resistorsValueTextField, TextField voltageTextField,BarChart<String,Number> ResistanceChart,CategoryAxis x, NumberAxis y, FXMLDocumentController controller)
    { 
        pane.getChildren().clear();   // Clear the animation pane from any previous animation to start. 
        pane.layout();                // Clear pane layout.
        
        ResistanceChart.getData().clear();      // Clear graph data.
        ResistanceChart.layout();               // Clear graph layout.
        
        SetMessage(controller, "");
        
        error = false;    // Boolean for when an error is caught anywhere in the program. Reseted at the start of the progrma.
        paused = false;   // Boolesn for pausing tha naimation with a button in the FXML controller. Reseted at the start of the progrma.
        reseted = false;  // Boolean to reset the program to the base. Reseted at the start of the progrma.
        
        if(resistorsNumberTextField.getText().equals("") || resistorsValueTextField.getText().equals("") || voltageTextField.getText().equals(""))
        {
            message = "Please enter values in the textfields to start.";
            SetMessage(controller, message);
            error = true;
        }
        
        ResistanceCalculation.pane = pane;                                                     // Initialize the pane to have the animation.
        ResistanceCalculation.controller = controller;                                         // Initialize the controller to controll the message label.
        ResistanceCalculation.ResistanceChart = ResistanceChart;                               // Initialize the bar chart.
        ResistanceCalculation.x = x;                                                           // Initialize the X-axis.
        ResistanceCalculation.y = y;                                                           // Initialize the Y-axis.
       
        if(error == false) // Do the animation only if no error has been found previously.
        {
            ResistanceCalculation.resistorsNumber = getResistorsNumber(resistorsNumberTextField);  // Get the number of resistors in the Int format.
            ResistanceCalculation.resistorsValue = getresistorsValue(resistorsValueTextField);     // Get the value of each resistor in double format in a lsit.
            ResistanceCalculation.voltage = getVoltageValue(voltageTextField);                     // Get the value of the voltage required for the circuit in the Int format.
        
            ResistanceCalculation.currentValue = getCurrentValues();                               // Get the value of current at every resistor in the double format.
            ResistanceCalculation.totalResistance = getTotalResistance();                          // Get the total resistance of the circuit. 
            ResistanceCalculation.totalCurrentValue = getTotalCurrent();                           // Get the total current of the circuit.
        }
        
        if(error == false) // Do the animation only if no error has been found previously.
        {
            enterDataInChart();                                                                    // Enter data of resistance and current in the chart.

            lastFrameTime = 0.0f;                                        // initialize the time of the last frame.
            initialTime = System.nanoTime();                             // initialize the starting time of the the program.

            AssetManager.preloadAllAssets();                             // Pre load all the assets from the asset manager class.

            pane.setBackground(AssetManager.getBackgroundImage());       // Set the background of the animation pane to the image from the asset manager.

            circuitShot = new ArrayList<>();                             // initialize the arrayList to hold the electron shots in the wire.
            circuitShotMiddle = new ArrayList<>();                       // initialize the arrayList to hold the electron shots in hte resistors.


            for(int i = 0; i < (resistorsNumber); i++)                   // Loop to add all wires and resistors in the animation pane. Loop as many times as number of resistors.
            {
                // Place top wires i times for number of resistors at y = paneTopWireY and X = labelStartingPoint + wirewidth*i.
                GameObject topWire = new GameObject( new Vector_2D(Constants.labelStartingPoint + (Constants.getWireWidth(resistorsNumber)*i), Constants.paneTopWireY),  // X & Y location of label.
                                                     new Vector_2D(0.0,0.0),  // NO velocity.
                                                     new Vector_2D(0.0,0.0),  // NO accelaration.
                                                     Constants.wireHeigth,    // Height of wire.
                                                     Constants.getWireWidth(resistorsNumber),     // Width of wire .  
                                                     Constants.wireImage);    // Image of wire.

                    addToPane(topWire.getRectangle()); // add top wire to pane.

                // Place bottom wires i times for number of resistors at y = paneBottomWireY and X = labelStartingPoint + wirewidth*i.    
                GameObject bottomWire = new GameObject( new Vector_2D( Constants.labelStartingPoint + (Constants.getWireWidth(resistorsNumber)*i),  Constants.paneBottomWireY), // X & Y location of label.
                                                        new Vector_2D(0.0,0.0),  // NO velocity.
                                                        new Vector_2D(0.0,0.0),  // NO accelaration.
                                                        Constants.wireHeigth,    // Heigth of wire.
                                                        Constants.getWireWidth(resistorsNumber),     // Width of wire.
                                                        Constants.wireImage);    // Image of wire

                    addToPane(bottomWire.getRectangle()); // add bottom wire to pan.

                // Add all resitors to the animation pane. At Y = the top wire. and X= border + wirewifth*(i+1)
                GameObject resistor = new GameObject( new Vector_2D(Constants.border + (Constants.getWireWidth(resistorsNumber)*(i+1)), Constants.paneTopWireY), // X & Y location of label.
                                                      new Vector_2D(0.0,0.0),    // NO velocity.
                                                      new Vector_2D(0.0,0.0),    // NO accelerration.
                                                      Constants.resistorHeigth,  // Height of resistor.
                                                      Constants.resistorWidth,   // Width of resistor.
                                                      Constants.resistorImage);  // Image of resistor.

                    addToPane(resistor.getRectangle()); // Add resitor to the animation pane.

                // Add Label with the resistor value of each resitor below each resistor.    
                Label resistorLabel = new Label("R" + (i+1) + ":\n" + df2.format(resistorsValue.get(i)) + "\u03A9"); // Create the text in the label. Resitors value.
                resistorLabel.setTextFill(Color.WHITE);                                                              // Set the color of the label to white.
                resistorLabel.setLayoutX(Constants.border + (Constants.getWireWidth(resistorsNumber)*(i+1)));                            // Place the label at the X cordinate of each resitor.
                resistorLabel.setLayoutY(Constants.paneBottomWireY + Constants.resistorWidth);                       // Place the label at the bottom of the resitors.

                addToPane(resistorLabel);                                                                            // Add the labels to the pane.

                // Add Label with the current value of each resitor on top of each resistor.    
                Label currentLabel = new Label("C" + (i+1) + ":\n" + df2.format(currentValue.get(i)) + "A");         // Create the text in the label. Current values.
                currentLabel.setTextFill(Color.WHITE);                                                               // Set the color of the label to white.
                currentLabel.setLayoutX(Constants.border + (Constants.getWireWidth(resistorsNumber)*(i+1)));                             // Place the label at the X cordinate of each resitor.
                currentLabel.setLayoutY(Constants.paneTopWireY - (2*Constants.resistorWidth));                       // Place the label at the bottom of the resitors. *2 because of text requires 2 lines.

                addToPane(currentLabel);                                                                             // Add the labels to the pane.
            }

            // Add label with total resistance of the circuit at the Y location on in the front of the first bottom wire.
            Label totalResistorLabel = new Label("RT:\n" + df2.format(totalResistance) + "\u03A9");                  // Create the text in the label. The total resitance value in the circuit.
            totalResistorLabel.setTextFill(Color.WHITE);                                                             // Set the color of the label to white.
            totalResistorLabel.setLayoutX(Constants.border - Constants.resistorWidth);                               // Place the label at the X cordinate of the start of the bottom wire.
            totalResistorLabel.setLayoutY(Constants.paneBottomWireY);                                                // Place the label at the bottom wire.

            addToPane(totalResistorLabel);                                                                           // Add total resistance label to the pane.

            Label totalCurrentLabel = new Label("CT:\n" + df2.format(totalCurrentValue) + "A");                      // Create the text in the label. The total current value in the circuit.
            totalCurrentLabel.setTextFill(Color.WHITE);                                                              // Set the color of the label to white.
            totalCurrentLabel.setLayoutX(Constants.border - Constants.resistorWidth);                                // Place the label at the X cordinate of the start of the top wire.
            totalCurrentLabel.setLayoutY(Constants.paneTopWireY);                                                    // Place the label at the top wire.

            addToPane(totalCurrentLabel);                                                                            // Add total current label to the pane.

            new AnimationTimer() // Animation timer for the animation to happen.
            {
               @Override
                public void handle(long now) {
                    // Time calculation                
                    double currentTime = (now - initialTime) / 1000000000.0;    // initialie the current time/ Get the current time.
                    double  frameDeltaTime = currentTime - lastFrameTime;       // Get the delta time between the lastframe time and the current time.
                    lastFrameTime = currentTime;                                // Make the last fram time equal to the current time for next loop.

                    if ((int)currentTime > (1 + circuitShot.size() + numCurrentIconDestroyed))   //spawn in electron shots whenever the current time is bigger than the set requirement.
                    {
                        GameObject circuit = new GameObject( new Vector_2D(Constants.border,Constants.paneTopWireY),  // initial point of electron in the circuit.
                                             new Vector_2D(250.0,0.0),  // Velocity of 250 in the x direction.
                                             new Vector_2D(0.0,0.0),    // No acceleration.
                                             Constants.objectHeigth,    // Set the heigth of the electron.
                                             Constants.objectwidth,     // set the width of the electron.
                                             Constants.electronShot);   // Set the image of the elctron.
                        circuitShot.add(circuit);                       // Add electron to the arraylist with the elctrons in the wires.
                        
                        addToPane(circuit.getRectangle());              // Add the elctron to the pane.
                    }

                    // Create a loop with the iterator of the arrayList containing the electrons in the wires.
                    for (Iterator<GameObject> iterator = circuitShot.iterator(); iterator.hasNext(); ) 
                    {
                        // Temporaly save the shot in this.
                        GameObject circuit1 = iterator.next();
                        // Check the current shot for the following:
                        if (circuit1.getPosition().getX() > (Constants.paneWidth - Constants.border - Constants.resistorWidth)) // if it is located at the end of the top wire is done
                        {
                             circuit1.switchVelocity("-y", 100); // Then change the direction to the -Y direction with a velocity of 100.
                        }

                        if (circuit1.getPosition().getY() > Constants.paneBottomWireY) // If it is located at the bottom wire and is going towards the bottom.
                        {
                             circuit1.switchVelocity("-x", 100); // Then change the direction to the -X direction with a velocity of 100.
                        }

                        if (circuit1.getPosition().getX() < Constants.border)   // If it is located before the circuit bottome wire.
                        {
                            pane.getChildren().remove(circuit1.getRectangle()); // Then remove it from the pane.
                            iterator.remove();                                  // remove it from the iterator.
                            circuitShot.remove(circuit1);                       // remove it from the arrayList.
                            numCurrentIconDestroyed += 1;                       // Add one the number of shots destroyed.
                        }
                    }

                    if ((int)currentTime > (circuitShotMiddle.size() + numCurrentIconMiddleDestroyed + 10)) //spawn in electron shots whenever the current time is bigger than the set requirement.
                    {
                        for(int i = 0; i < resistorsNumber-1; i++) // Loop trough every resistor and add electron sshots to every single one of them. Excepet the last one.
                        {
                            GameObject circuitx = new GameObject( new Vector_2D(Constants.border + (Constants.getWireWidth(resistorsNumber)*(i+1)) - Constants.resistorWidth,Constants.paneTopWireY), // Initial point of the elctron shots at every resitor with i.
                                                                  new Vector_2D(0.0,100.0), // Set velocity to 100.
                                                                  new Vector_2D(0.0,0.0),   // No acceleration.
                                                                  Constants.objectHeigth,   // set heigth of electron
                                                                  Constants.objectwidth,    // Set width of electron.
                                                                  Constants.electronShot);  // Set image of elcetron
                                    circuitShotMiddle.add(circuitx);                        // Add shot to the arraylist of the shots in the resistors.
                                    
                                    addToPane(circuitx.getRectangle()); // Add shot to the pane.
                        }
                    }

                    // Create a loop with the iterator of the arrayList containing the electrons in the wires.
                    for (Iterator<GameObject> iterator = circuitShotMiddle.iterator(); iterator.hasNext(); ) 
                    {
                         // Temporaly save the shot in this.
                        GameObject circuit1 = iterator.next();

                        if (circuit1.getPosition().getY() > Constants.paneBottomWireY) // the Current shot is going lower than the bottom wire. 
                        {
                            pane.getChildren().remove(circuit1.getRectangle()); //then remove it from the pane.
                            iterator.remove();                                  // remove it from the iterator
                            circuitShotMiddle.remove(circuit1);                 // remove it form the arraylist.
                            numCurrentIconMiddleDestroyed += 1;                 // Add one to the number fo shots destroyed.
                        }
                    }
    
                    // Update every element from the arrayList of shots in the wires.
                    for (GameObject obj : circuitShot)
                    {
                        obj.update(frameDeltaTime);
                    }

                    // Update every element from the arrayList of shots in the Resistors.
                    for (GameObject obj : circuitShotMiddle)
                    {
                        obj.update(frameDeltaTime);
                    }

                    if(paused == true || error == true) // If paused is clicked or if error caught
                    {
                        this.stop(); //then stop the animation.
                        SetMessage(controller, "");
                    }

                    if(reseted == true) // If reset is clicked 
                    {
                        this.stop(); // Then stop the animation
                        Reset(pane, ResistanceChart, resistorsNumberTextField, resistorsValueTextField, voltageTextField); // And call the reset method.
                    }
                }
            }.start(); // Start the animation.
        }
    }
    
}
