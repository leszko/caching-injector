package hello;

import com.hazelcast.config.Config;
import com.hazelcast.config.KubernetesConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.HttpURLConnection;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(kubernetesConfig());

    private static Config kubernetesConfig() {
        Config config = new Config();
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getKubernetesConfig().setEnabled(true);
        return config;
    }

    @RequestMapping("/**")
    public String proxy(HttpServletRequest request) {
        String url = "http://localhost:80" + request.getServletPath() + "?" + request.getQueryString();
        IMap<String, String> map = hazelcastInstance.getMap("cache");
        if (map.containsKey(url)) {
            return map.get(url);
        } else {
           String response =  RestClient.create(url).get();
           map.put(url, response);
           return response;
        }
    }
}
