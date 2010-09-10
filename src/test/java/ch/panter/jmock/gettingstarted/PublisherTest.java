/**
 * 
 */
package ch.panter.jmock.gettingstarted;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.panter.jmock.gettingstarted.IPublisher;
import ch.panter.jmock.gettingstarted.ISubscriber;
import ch.panter.jmock.gettingstarted.PublisherImpl;

/**
 * Demonstration of some fuctions of the jmock library.
 * <p>
 * Have a look at
 * <a href="http://www.jmock.org/cookbook.html"> the
 * jmock cookbook</a> for a lot more examples.
 * </p>
 * 
 * @author bseelige
 *
 */
@RunWith(JMock.class)
public class PublisherTest {
    
    
    /** jmock context */
    Mockery context;
    /** instance under test */
    IPublisher instance;
    
    @Before
    public void setUp() {
        context = new JUnit4Mockery();
        instance = new PublisherImpl();
    }
    
    /**
     * In an outline, a jmock 2 test looks like
     * the following.
     */
    @Test
    public void jmockTemplate() {
        // set up
        // ... set up code ...
                  
        // define expectations
        context.checking(new Expectations() {{
            // ... expectations go here ...
            
            // An expectations block can contain any number 
            // of expectations. Each expectation has the 
            // following structure:
            /*
            invocation-count (mock-object).method(argument-constraints);
            inSequence(sequence-name);
            when(state-machine.is(state-name));
            will(action);
            then(state-machine.is(new-state-name));
            */
            // Except for the invocation count and the mock 
            // object, all clauses are optional. You can give 
            // an expectation as many inSequence, when, will 
            // and then clauses as you wish.
        }});
        
        // execute
        // ... code being tested ...
        
        // after execution junit assertations
        // ... assertions ...
    }
    
    /**
     * Creates a simple mock for the ISubscriber interface.
     * <p>
     * After the code under test has finished our test must 
     * verify that the mock Subscriber was called as expected. 
     * If the expected calls were not made, the test will fail. 
     * The MockObjectTestCase does this automatically. You 
     * don't have to explicitly verify the mock objects 
     * in your tests. The JMock test runner does this 
     * automatically. You don't have to explicitly verify 
     * the mock objects in your tests.
     * </p>
     *
     */
    @Test
    public void oneSubscriberReceivesAMessage() {
        // set up
        final ISubscriber subscriber = context.mock(ISubscriber.class);
        instance.add(subscriber);
        final String message = "message";
        
        // expectations
        // first bracet creates an anonymous inner class
        // second braced is the static initializer of the class
        context.checking(new Expectations() {{
            // one and all the other invocation count methods
            // return an instance of the same class as it's
            // argument
            one (subscriber).receive("message");
        }});
        
        // execute
        instance.publish(message);
    }
    
    /**
     * Sequences are used to define expectations that must 
     * occur in the order in which they appear in the test 
     * code. A test can create more than one sequence and an 
     * expectation can be part of more than once sequence 
     * at a time.
     */
    @Test
    public void oneSubscriberReceivesASequnce() {
        // set up
        final ISubscriber subscriber = context.mock(ISubscriber.class);
        instance.add(subscriber);
        
        final Sequence seq = context.sequence("msgseq");
        // double braces again, see
        // http://www.c2.com/cgi/wiki?DoubleBraceInitialization
        final List<String> messages = new ArrayList<String>()
        {{
            add("message1"); add("message2"); add("message3");
        }};
        
        // expectations
        // first bracet creates an anonymous inner class
        // second braced is the static initializer of the class
        context.checking(new Expectations() {{
            // one and all the other invocation count methods
            // return an instance of the same class as it's
            // argument
            one (subscriber).receive(messages.get(0));
            inSequence(seq);
            one (subscriber).receive(messages.get(1));
            inSequence(seq);
            one (subscriber).receive(messages.get(2));
            inSequence(seq);
        }});
        
        // execute
        for (String message : messages) {
            instance.publish(message);
        }
    }
    
    /**
     * The "invocation count" of an expectation defines the 
     * minimum and maximum number of times that the expected 
     * method is allowed to be invoked. It is specified before 
     * the mock object in the expectation.
     * 
     * <ul>
     * <li>one: The invocation is expected once and once only.</li>
     * <li>exactly(n).of: The invocation is expected exactly n times.</li>
     * <li>atLeast(n).of: The invocation is expected at least n times.</li>
     * <li>atMost(n).of: The invocation is expected at most n times.</li>
     * <li>between(min, max).of: The invocation is expected at least min times and at most max times.</li>
     * <li>allowing: The invocation is allowed any number of times but does not have to happen.</li>
     * <li>ignoring: The same as allowing. Allowing or ignoring should be chosen to make the test code clearly express intent.</li>
     * <li>never: The invocation is not expected at all. This is used to make tests more explicit and so easier to understand.</li>
     * </ul>
     *
     */
    @Test
    public void expectingMoreThanOnce() {
        // set up
        final ISubscriber subscriber = context.mock(ISubscriber.class);
        instance.add(subscriber);
        
        final String message = "message";
        
        // expectations
        context.checking(new Expectations() {{
            // one and all the other invocation count methods
            // return an instance of the same class as it's
            // argument
            exactly(10).of (subscriber).receive(message);
            
            //some alternatives:
            //allowing (subscriber).receive(message);
            //atLeast(5).of (subscriber).receive(message);
            //between(5, 15).of (subscriber).receive(message);
            //never (subscriber).receive(message);
        }});
        
        // execute
        for (int i = 0; i<10 ; i++) {
            instance.publish(message);   
        }
    }
    
    /**
     * Most of the time expectations specify literal parameter 
     * values that are compared for equality against the 
     * actual parameters of invoked methods (see above).
     * <p>
     * Sometimes, however, you will need to define looser 
     * constraints over parameter values to clearly express 
     * the intent of the test or to ignore parameters (or 
     * parts of parameters) that are not relevant to the 
     * behaviour being tested
     * </p>
     * <p>
     * Loose parameter constraints are defined by specifying 
     * matchers  for each parameter. Matchers are created by 
     * factory methods, such as lessThan, equal and 
     * stringContaining in the example above, to ensure 
     * that the expectation is easy to read. The result 
     * of each factory method must be wrapped by a call to 
     * the with method.
     * </p>
     * <p>
     * Matchers can be combined to tighten or loosen the 
     * specification if necessary. The set of matchers 
     * is extensible1 so you can write new matchers to 
     * cover unusual testing scenario.
     * </p>
     * @see<a href="http://www.jmock.org/custom-matchers.html">
     * "http://www.jmock.org/custom-matchers.html"</a>
     */
    @Test
    public void usingMatchers() {
        // set up
        final ISubscriber subscriber = context.mock(ISubscriber.class);
        instance.add(subscriber);
        
        final String message = "message";
        
        // expectations
        context.checking(new Expectations() {{
            // test for exact match
            one (subscriber).receive(message);
            // alternative test for exact match
            one (subscriber).receive(with(equal(message)));
            // test for object identity
            one (subscriber).receive(with(same(message)));
            // test for not null
            one (subscriber).receive(with(aNonNull(String.class)));
            // "ignoring" the parameter
            one (subscriber).receive(with(any(String.class)));
            // combined matcher
            one (subscriber).receive(with(not(aNull(String.class))));
            // these are Hamcrest matchers, see
            // http://code.google.com/p/hamcrest/wiki/Tutorial
            // test for substring
            one (subscriber).receive(with(containsString("mess")));

        }});
        
        
        // execute
        for (int i=0; i<7; i++) {
            instance.publish(message);
        }
    }
 
}
