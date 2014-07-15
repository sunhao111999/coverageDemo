package example.calculator;

import android.test.ActivityInstrumentationTestCase2;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.pressBack;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class HomeActivityTest extends ActivityInstrumentationTestCase2<HomeActivity>
{
    public HomeActivityTest()
    {
        super(HomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        getActivity();
    }

    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

//    public void testCheck()
//    {
//        onView(withId(R.id.TextView01)).check(matches(withText(containsString("0.00"))));
//    }

    public void testCheck() throws InterruptedException
    {

        onView(withId(R.id.EditText01)).perform(typeText("20"), closeSoftKeyboard());
        onView(withId(R.id.EditText02)).perform(typeText("11"), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.Button01)).perform(click());
        onView(withId(R.id.TextView01)).check(matches(withText(containsString("220"))));
    }
}
