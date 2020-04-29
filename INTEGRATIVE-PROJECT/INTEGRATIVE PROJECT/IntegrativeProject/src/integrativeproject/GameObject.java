/*
 * Jasen Ratnam
 * Program for Computer Science with Maths.
 * In Vanier College.
 */
package integrativeproject;

import javafx.scene.shape.Rectangle;

/**
 * Integrative project.
 * Used to create an object.
 * @author Jasen Ratnam 1622549
 */
public class GameObject 
{

    /**
     * Rectangle object to make objects.
     */
    protected Rectangle rectangle;

    /**
     * position vector of the object
     */
    protected Vector_2D position;

    /**
     * velocity vector of the object.
     */
    protected Vector_2D velocity;

    /**
     * acceleration vector of the object.
     */
    protected Vector_2D acceleration;

    /**
     * the string containing the name of the image for the object.
     */
    protected String object;

    /**
     * Height of the object.
     */
    protected double height;

    /**
     * Width of the object.
     */
    protected double width;
    
    /**
     * Constructor of gameObject.
     * @param position of the object.
     * @param velocity of the object
     * @param acceleration of the object
     * @param height of the object.
     * @param width of the object
     * @param object image of object.
     */
    public GameObject(Vector_2D position, Vector_2D velocity, Vector_2D acceleration, double height, double width, String object)
    {
        this.position = position;              // initialize all variables.
        this.velocity = velocity;
        this.acceleration = acceleration; 
        this.object = object;
        this.height = height;
        this.width = width;
        
        rectangle = new Rectangle();             // initalize the rectangle.
        rectangle.setLayoutX(position.getX());   // Set the layout of the rectnagle.
        rectangle.setLayoutY(position.getY());
        rectangle.setHeight(height);             // Set the heigth and width of the object.
        rectangle.setWidth(width);
        
        if(object.equalsIgnoreCase("wire")) // If image of wire wanted
        {
            rectangle.setFill(AssetManager.getHorizontalWireImage());
        }
        
        if(object.equalsIgnoreCase("resistor")) // If image of resistor wanted
        {
            rectangle.setFill(AssetManager.getVerticalResistorImage());
        }
        
        if(object.equalsIgnoreCase("circuit")) // If image of circuit electron wanted
        {
            rectangle.setFill(AssetManager.getCircuitShotImage());
        }
    }
    
    /**
     * get the position of the gameObject.
     * @return the position vector.
     */
    public Vector_2D getPosition()
    {
        return position;
    }
    
    /**
     * Set the position of the gameObject.
     * @param x position
     * @param y position
     */
    public void setPosition(double x, double y)
    {
        position.setX(x);
        position.setY(y);
        rectangle.setLayoutX(position.getX());
        rectangle.setLayoutY(position.getY());        
    }
    
    /**
     * switch the velocity of the game object.
     * @param direction new direction of the object
     * @param velocity2 new velocity of the object.
     */
    public void switchVelocity(String direction, double velocity2)
    {   
        velocity.switchVelocityDirection(direction,velocity2); // Call method from Vector_2D
    }
    
    /**
     * Get the rectangle of the object.
     * @return the rectangle.
     */
    public Rectangle getRectangle()
    {
        return rectangle;
    }
    
    /**
     * update the gameObject to make it move.
     * @param dt the frame delta time
     */
    public void update(double dt)
    {
        // Euler Integration
        // Update velocity
        Vector_2D frameAcceleration = acceleration.mul(dt);
        velocity = velocity.add(frameAcceleration);  // New velocity

        // Update position
        position = position.add(velocity.mul(dt));   // New velocity.
        rectangle.setLayoutX(position.getX());
        rectangle.setLayoutY(position.getY());
    }
}
