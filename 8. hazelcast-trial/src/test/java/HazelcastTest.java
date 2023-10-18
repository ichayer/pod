import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.test.TestHazelcastFactory;
import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HazelcastTest {
    private TestHazelcastFactory hazelcastFactory;
    private HazelcastInstance member, client;

    @Before
    public void setUp() {
        hazelcastFactory = new TestHazelcastFactory();
        GroupConfig groupConfig = new
                GroupConfig().setName("gX").setPassword("gX-pass");
        Config config = new Config().setGroupConfig(groupConfig);
        member = hazelcastFactory.newHazelcastInstance(config);
        ClientConfig clientConfig = new ClientConfig().setGroupConfig(groupConfig);
        client = hazelcastFactory.newHazelcastClient(clientConfig);
    }
    @Test
    public void simpleTest() {
        String mapName = "testMap";
        IMap<Integer, String> testMapFromMember = member.getMap(mapName);
        testMapFromMember.set(1, "test1");
        IMap<Integer, String> testMap = client.getMap(mapName);
        String value = testMap.get(1);
        Assert.assertEquals("test1", value);
    }

    @After
    public void tearDown() {
        hazelcastFactory.shutdownAll();
    }
}
