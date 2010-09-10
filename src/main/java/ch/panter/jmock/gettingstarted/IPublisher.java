package ch.panter.jmock.gettingstarted;

public interface IPublisher {

    public abstract void add(ISubscriber subscriber);

    public abstract void publish(String message);

}