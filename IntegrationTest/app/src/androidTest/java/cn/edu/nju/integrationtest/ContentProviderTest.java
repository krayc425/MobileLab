package cn.edu.nju.integrationtest;

import android.content.ContentValues;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ContentProviderTest extends ProviderTestCase2<ExampleProvider> {
    private MockContentResolver mMockResolver;
    public ContentProviderTest() {
        super(ExampleProvider.class, ExampleContract.AUTHRITY);
    }

    @Test
    public void testInsert() {
        ContentValues values = new ContentValues();
        values.put(ExampleContract.ExampleTable.NAME, "Dummy");
        values.put(ExampleContract.ExampleTable.AGE, 18);
        values.put(ExampleContract.ExampleTable.GENDER, "male");
        Uri uri = mMockResolver.insert(ExampleContract.ExampleTable.FULL_CONTENT_URI, values);
        assertNotNull(uri);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        setContext(InstrumentationRegistry.getTargetContext());
        super.setUp();
        mMockResolver = getMockContentResolver();
    }

}
