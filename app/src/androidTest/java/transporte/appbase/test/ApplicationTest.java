package transporte.appbase.test;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    @SmallTest
    public void test_revisar_GPS(){
        Context cxt = this.getContext();
        LocationManager locationManager = (LocationManager)cxt.getSystemService(Context.LOCATION_SERVICE);
        assertNotNull(locationManager.getProvider(LocationManager.GPS_PROVIDER).getName());
    }

}