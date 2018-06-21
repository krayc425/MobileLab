package cn.edu.nju.integrationtest;

import android.content.Intent;
import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ServiceTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public final ServiceTestRule mServiceRule = new ServiceTestRule();

    @Test
    public void useAppContext() throws TimeoutException, InterruptedException {
        // Create the service Intent.
        Intent serviceIntent =
                new Intent(InstrumentationRegistry.getTargetContext(), LocalService.class);
        // Data can be passed to the service via the Intent.
        serviceIntent.putExtra(LocalService.SEED_KEY, 42L);

        // Bind the service and grab a reference to the binder.
        IBinder binder = mServiceRule.bindService(serviceIntent);

        // Get the reference to the service, or you can call public methods on the binder directly.
        LocalService service = ((LocalService.LocalBinder) binder).getService();

        // Verify that the service is working correctly.
        assertThat(service.getRandomInt(), is(any(Integer.class)));
    }
}
