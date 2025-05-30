package controllers;

import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/plug")
public class Plug_Controller {
    @GetMapping (produces = "application/json")
    public String getStaticJson() {
        try {
            Thread.sleep(1000 );
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "{\"login\":\"Login1\",\"status\":\"ok\"}";
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Map<String, String> postLogin(@RequestBody Map<String, String> input) {

        String login = input.get("login");
        String password = input.get("password");
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        Map<String, String> response = new HashMap<>();
        response.put("login", login);
        response.put("password", password);
        response.put("date", date);

        try {
            Thread.sleep(1000 + new Random().nextInt(1001));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
