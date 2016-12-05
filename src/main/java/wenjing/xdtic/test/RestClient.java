package wenjing.xdtic.test;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import wenjing.xdtic.model.User;

public class RestClient {

    public static void main(String[] args) throws Exception {
        String url = "http://localhost:9090/xdtic/fn/project/post";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("uid", "3");
        params.add("tag", "Web");
        params.add("title", "xdtic项目池");
        params.add("promassage", "发布自己的项目；\n参与他人的项目。");
        params.add("prowant", "Web 前端一名");
        params.add("concat", "18079430525");

        testPost(url, params);
    }

    public static void testPost(String url, MultiValueMap<String, String> params) {
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> respEntity = template.postForEntity(url, params, String.class);
        printEntity(respEntity);
    }

    public static <T> void printEntity(ResponseEntity<T> entity) {
        T body = entity.getBody();
        HttpStatus status = entity.getStatusCode();
        int statusValue = entity.getStatusCodeValue();
        System.out.format("status: %d %s\nbody: %s\n", statusValue, status, body.toString());
    }

    public static void testLogin(String username, String password) {
        String URL = "http://localhost:8080/xdtic/fn/valid/user";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> entity = template.postForEntity(URL, params, String.class);
        System.out.println("code: " + entity.getStatusCode());
        System.out.println("body: " + entity.getBody());
    }

    public static void testPostJson() {
        User user = new User();
        user.setUsername("Michael");
        user.setPassword("abcedfg");

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

    }
}
