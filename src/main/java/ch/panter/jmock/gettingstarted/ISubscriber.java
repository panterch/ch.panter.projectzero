/**
 * 
 */
package ch.panter.jmock.gettingstarted;

/**
 * In this simple example we are going to write a mock object test for a 
 * publish/subscribe message system. A Publisher sends messages to zero or 
 * more Subscribers. We want to test the Publisher, which involves testing 
 * its interactions with its Subscribers.
 * 
 * @author bseelige
 *
 */
public interface ISubscriber {
    

    /**
     * Let subscriber receive a message
     * @param message
     */
    public abstract void receive(String message);

}
