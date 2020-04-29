
package integrativeproject;
/**
 * Integrative project.
 * Used as a timer for animations on kinematics and Doppler.
 * @author Salvatore
 */
public class StopWatch
{
    /**
     * Current time of system.
     */
    private static final long start = System.currentTimeMillis();
    
    /**
     * Time elapsed.
     */
    private static double elapsedSeconds = 0;
    
    /**
     * Time elapsed calculation.
     * @return  elapsed time.
     */
    public static double timerDouble()
    {
        while(elapsedSeconds<40)
        {
            double seconds = elapsedSeconds;
            long elapsedTime = System.currentTimeMillis() - start;
            elapsedSeconds = elapsedTime / 1000.0;

            if(seconds != elapsedSeconds)
            {  
                return elapsedSeconds;
            }
        }
        return 0;
    }
    
     /**
      * Time elapsed calculation.
      * @return  elapsed time.
      */
    public static double timerInt()
    {
        while(elapsedSeconds<20)
        {
            double seconds = elapsedSeconds;
            long elapsedTime = System.currentTimeMillis() - start;
            elapsedSeconds = elapsedTime / 1000;

            if(seconds != elapsedSeconds)
            {  
                return elapsedSeconds;
            }
        }
        return 0;
    }
}
