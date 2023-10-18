import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;

import java.util.Collections;

public class Server {


    // Add the following flags to the vm before running the server:
    // --add-opens java.management/sun.management=ALL-UNNAMED
    // --add-opens jdk.management/com.sun.management.internal=ALL-UNNAMED

    // Tomcat 9 Image (docker) specified in .idea/workspace.xml is necessary
    public static void main(String[] args) {

        // Config
        Config config = new Config();

        // Group config
        GroupConfig groupConfig = new GroupConfig().setName("g0").setPassword("g0-pass");
        config.setGroupConfig(groupConfig);

        // Network config
        MulticastConfig multicastConfig = new MulticastConfig();

        JoinConfig joinConfig = new JoinConfig()
                .setMulticastConfig(multicastConfig);
        InterfacesConfig interfacesConfig = new InterfacesConfig()
                .setInterfaces(Collections.singletonList("192.168.1.*"))
                .setEnabled(true);
        NetworkConfig networkConfig = new NetworkConfig()
                .setInterfaces(interfacesConfig)
                .setJoin(joinConfig);
        config.setNetworkConfig(networkConfig);

        // Management Center Config
        ManagementCenterConfig managementCenterConfig = new ManagementCenterConfig()
                .setUrl("http://localhost:8080/mancenter-3.8.5/")
                .setEnabled(true);
        config.setManagementCenterConfig(managementCenterConfig);

        // Start cluster
        Hazelcast.newHazelcastInstance(config);
    }
}
