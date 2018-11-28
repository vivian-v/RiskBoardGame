



import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;
import junit.framework.TestCase;
import riskboardgame.ReplayS3;


public class TestReplayS3 extends TestCase {

	ReplayS3 replay = new ReplayS3();

	
	@Test
    public void testGetBucketName() {
		assertEquals("riskdemo3replaynew", replay.getBucketName());
    }

	@Test
    public void testGetKey() {
		assertNotNull(replay.getKey());
    }
	
	@Test
    public void testCreateBucket() throws IOException {
		ArrayList<String> s = new ArrayList<String>();
		s.add("test");
		assertNotNull(replay.createFile(s));

    }
}