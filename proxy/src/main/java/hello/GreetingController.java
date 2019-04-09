package hello;

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
    private static final String template = "Hello, %s!";

    private HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

//    @RequestMapping("/greeting")
//    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return new Greeting(String.format(template, name));
//    }

    @RequestMapping("/**")
    public String test(HttpServletRequest request) {
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
