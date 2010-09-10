/**
 * 
 */
package ch.panter.junit.gettingstarted;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

//NEW static import for assert methods
import static org.junit.Assert.*;

/**
* Demo showing some new features in junit 4. Look for NEW comments
* 
* @author bseelige
* 
*/
//NEW: does not have to extend test case
public class Junit4Test {

    /** our instance under test */
    private String instance;

    // NEW methods w/ the annotation Before are executed
    // are called before each @Test method (like
    // setUp() in older junit versions
    @Before
    public void init() {
        this.instance = "junit4";
    }

    // NEW methods w/ the annotation After are executed
    // are called before each @Test method (like
    // tearDown() in older junit versions
    @After
    public void clear() {
        this.instance = null;
    }

    // NEW methods w/ the annotation BeforeClass are executed
    // after executing all tests
    // Note: this method must be static
    @BeforeClass
    public static void beforeEverything() {
    }

    // NEW methods w/ the annotation AfterClass are executed
    // after executing all tests
    // Note: this method must be static
    @AfterClass
    public static void cleanUp() {
    }

    // NEW test methods are found w/ the new annotation Test
    // instead of the signature void testMethod
    @Test
    public void stringEquals() {
        assertNotNull(this.instance);
        assertEquals("junit4", this.instance);
    }
    
    // NEW if you expect an exception, you can define that
    // as parameter to the test annotation
    @Test(expected=ArithmeticException.class) 
      public void divideByZero() {
        int n = 2 / 0;
    }
    
    // NEW if a test is not ready yet, you can annotate that
    // (or you can simply remove the Test annotations...)
    @Ignore
    @Test 
      public void wouldFail() {
        fail();
    }

}

