/**
 * 
 */
package ch.panter.jmock.gettingstarted;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import ch.panter.jmock.gettingstarted.ISubscriber;

/**
 * @author bseelige
 *
 */
public class PublisherImpl implements IPublisher {
    
    private Collection<ISubscriber>subscribers;

    /**
     * 
     */
    public PublisherImpl() {
        super();
        this.subscribers = new ArrayList<ISubscriber>();
    }

    /* (non-Javadoc)
     * @see ch.fhzh.jmock.gettingstarted.IPublisher#add(ch.fhzh.jmock.gettingstarted.ISubscriber)
     */
    public void add(ISubscriber subscriber) {
        this.subscribers.add(subscriber);
    }

    /* (non-Javadoc)
     * @see ch.fhzh.jmock.gettingstarted.IPublisher#publish(java.lang.String)
     */
    public void publish(String message) {
        for (ISubscriber subscriber : subscribers) {
            subscriber.receive(message);    
        }
    }

}
