package wenjing.xdtic.test;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import wenjing.xdtic.model.User;

public class RestClient {

    public static void main(String[] args) throws Exception {
        testPostJson();
    }

    public static void testPostJson() {
        User user = new User();
        user.setUsername("Michael");
        user.setPassword("abcedfg");

//        List<HttpMessageConverter> messageConverters = new ArrayList<>(1);
//        HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
//        messageConverters.add(messageConverter);
        String URL = "http://localhost:8080/xdtic/user/test_json";

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> entity = template.postForEntity(URL, user, String.class);
        System.out.println("status: " + entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    public static void testQuery() {
        RestTemplate template = new RestTemplate();

        String url = "http://localhost:8080/xdtic/user/1";
        ResponseEntity<String> response = template.getForEntity(url, String.class);
        System.out.println(response.getBody());
    }

    public static void testUpdate() {
        RestTemplate template = new RestTemplate();

        String url = "http://localhost:8080/xdtic/user/update_passwd";
        Map<String, Object> params = new HashMap<>();

        params.put("id", 1);
        params.put("old_password", "123456");
        params.put("new_password", "abcd");
        params.put("email", "24256230812@qq.com");

        ResponseEntity<String> response = template.getForEntity(url, String.class, params);
        System.out.println(response.getBody());
//        params.put("id", 1);
//        params.put("username", "文静宋");
//        params.put("password", "123456");
//        params.put("nickname", "wenjing");
//        params.put("email", "24256230812@qq.com");
//        params.put("sex", "女");
//        params.put("profile", "西安电子科技大学计算机学院研究生");
        //   template.postForLocation(url, null, params);
        //template.put(url, null, params);

    }
}
