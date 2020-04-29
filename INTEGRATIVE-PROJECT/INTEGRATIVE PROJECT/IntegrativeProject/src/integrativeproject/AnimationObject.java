package integrativeproject;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

/**
 * GameObject method with a circle instead of a rectangle.
 * @author Salvatore
 */
public class AnimationObject 
{
    /**
     * Circle object to make objects.
     */
    protected Circle circle;
    
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
     * Anchor pane where the animation will happen.
     */
    protected AnchorPane p;
    
    /**
     * Constructor of animationObject.
     * Construct object.
     * @param position of the object.
     * @param velocity of the object.
     * @param acceleration of the object.
     * @param radius  of the object.
     */
    public AnimationObject(Vector_2D position, Vector_2D velocity, Vector_2D acceleration, double radius)
    {
        this.position = position;            // initialize all variables.
        this.velocity = velocity;
        this.acceleration = acceleration; 
        
        circle = new Circle(0.0, 0.0, radius);  // Create object.
        circle.setLayoutX(position.getX());     // Set layout in the pane.
        circle.setLayoutY(position.getY());
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
     * Get the circle of the object.
     * @return the circle.
     */
    public Circle getCircle()
    {
        return circle;
    }
    
    /**
     * Set the velocity of the gameObject.
     * @param vel velocity of object.
     */
    public void setVelocity(Vector_2D vel)
    {
        velocity = vel;
    }
    
    /**
     * update the animationObject to make it move.
     * @param dt the frame delta time
     */
    public void update(double dt)
    {
        // Euler Integration
        // Update velocity
        Vector_2D frameAcceleration = acceleration.mul(dt);
        velocity = velocity.add(frameAcceleration);

        // Update position
        position = position.add(velocity.mul(dt));
        circle.setLayoutX(position.getX());
        circle.setLayoutY(position.getY());
    }
    
    /**
     * update the animationObject to make it move.
     * @param dt the frame delta time
     */
    public void update2(double dt)
    {
        // Euler Integration
        // Update velocity
        Vector_2D frameAcceleration = acceleration.mul(dt);
        velocity = velocity.add(frameAcceleration);

        // Update position
        position = position.add(velocity.mul(dt));
        circle.setLayoutX(position.getX());
        circle.setLayoutY(position.getY());
        
        // MAKE THE BALL STOP WHEN IT REACHES THE GROUND
        AnchorPane pane = (AnchorPane)circle.getParent();
        if (position.getX() > pane.getWidth() - circle.getRadius())
        {
            double absVelocityX = Math.abs(velocity.getX());
            absVelocityX *= 0;
            velocity.setX(-absVelocityX);
            double absAccelerationX = Math.abs(acceleration.getX());
            absAccelerationX *= 0;
            acceleration.setX(-absAccelerationX);
        }
    }
    
    /**
     * update the animationObject to make it move.
     * @param dt the frame delta time
     */
    public void update3(double dt)
    {
        // Euler Integration
        // Update velocity
        Vector_2D frameAcceleration = acceleration.mul(dt);
        velocity = velocity.add(frameAcceleration);

        // Update position
        position = position.add(velocity.mul(dt));
        circle.setLayoutX(position.getX());
        circle.setLayoutY(position.getY());
        
        // MAKE THE BALL STOP WHEN IT REACHES THE GROUND
        AnchorPane pane = (AnchorPane)circle.getParent();
        if (position.getX() < 10)
        {
            double absVelocityX = Math.abs(velocity.getX());
            absVelocityX *= 0;
            velocity.setX(-absVelocityX);
            double absAccelerationX = Math.abs(acceleration.getX());
            absAccelerationX *= 0;
            acceleration.setX(-absAccelerationX);
        }
    }
}
