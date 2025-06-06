package api.v1.controllers;

import api.v1.models.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/plug")
public class Plug_Controller {


	@GetMapping(produces = "application/json")
	public ResponseEntity<String> getJson() {
		response_time();
		String json = "{\"login\":\"Login1\",\"status\":\"ok\"}";
		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<User> postJson(@Valid @RequestBody User user) {
		response_time();
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		user.setDate(date);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	public void response_time(){
		try {
			Thread.sleep(1000 + new Random().nextInt(1001));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
