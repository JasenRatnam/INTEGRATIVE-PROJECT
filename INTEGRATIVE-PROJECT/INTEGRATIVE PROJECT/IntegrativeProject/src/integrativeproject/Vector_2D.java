/*
 * Jasen Ratnam
 * Program for Computer Science with Maths.
 * In Vanier College.
 */
package integrativeproject;

/**
 * Integrative project.
 * Used to make and modify objects.
 * @author Jasen Ratnam 1622549
 */
public class Vector_2D 
{
    private double x;
    private double y;

    /**
     * constructor.
     * Construct an object.
     * @param x 
     * @param y
     */
    public Vector_2D(double x, double y) 
    {
        this.x = x;
        this.y = y;
    }

    // Accessors and Mutators:

    /**
     * Gets the x value of the object
     * @return x value
     */
    public double getX(){ return x; }

    /**
     * Gets the y value of the object.
     * @return y value
     */
    public double getY(){ return y; }
    
    /**
     * Set the x value of the object.
     * @param value to be set as X.
     */
    public void  setX(double value)
    { 
        x = value; 
    }

    /**
     * Set the y value of the object.
     * @param value to be set as Y.
     */
    public void  setY(double value)
    {
        y = value; 
    }
    
    // Operations:

    /**
     * Add a vector to this vector.
     * @param other vector to be added.
     * @return the new vector.
     */
    public Vector_2D add(Vector_2D other) 
    {
        return new Vector_2D(x + other.x, y + other.y);
    }

    /**
     * Remove a vector from this vector.
     * @param other vector to be removed.
     * @return the new vector.
     */
    public Vector_2D sub(Vector_2D other) 
    {
        return new Vector_2D(x - other.x, y - other.y);
    }

    /**
     * Multiple a vector to this vector.
     * @param other vector to be multiplied.
     * @return the new vector.
     */
    public Vector_2D mul(Vector_2D other) 
    {
        return new Vector_2D(x * other.x, y * other.y);
    }
    
    /**
     * Scalar multiplication of a vector.
     * @param multiplier scalar multiplier.
     * @return the new vector.
     */
    public Vector_2D mul(double multiplier)
    {
        return new Vector_2D(x * multiplier, y * multiplier);
    }
    
    /**
     * get the magnitude of the vector.
     * @return the magnitude of the vector.
     */
    public double magnitude()
    {
        return Math.sqrt(x*x + y*y);        
    }

    /**
     * Set the magnitude of the vector.
     * @param m the magnitude to be set.
     */
    public void setMagnitude(double m)
    {
        double original = magnitude();
        double ratio = m / original;
        
        x *= ratio;
        y *= ratio;
    }
    
    /**
     * Method to switch the velocity of the vector.
     * @param direction the new direction of the vector.
     * @param velcoity  the new velocity of the vector.
     */
    public void switchVelocityDirection(String direction,double velcoity)
    {
        if(direction.equalsIgnoreCase("-y")) // If new direction is - y.
        {
            setY(velcoity); // set new velocity in the new direction.
            setX(0.0);
        }
        
        else if(direction.equalsIgnoreCase("y")) // If new direction is y.
        {
            setY(-velcoity); // set new velocity in the new direction.
            setX(0.0);
        }
        
        else if(direction.equalsIgnoreCase("-x")) // If new direction is - x.
        {
            setY(0.0);
            setX(-velcoity);  // set new velocity in the new direction.
        }
        
        else if(direction.equalsIgnoreCase("x")) // If new direction is x.
        {
            setY(0.0);
            setX(velcoity);  // set new velocity in the new direction.
        }
    }
    
    /**
     * Get the distance between 2 vectors.
     * @param v1 first vector
     * @param v2 second vector
     * @return the distance between v1 and v2.
     */
    public static double distance(Vector_2D v1, Vector_2D v2)
    {
        double dx = v1.x - v2.x;
        double dy = v1.y - v2.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
}
