package api.v1.controllers;

import api.v1.dto.UserDTO;
import api.v1.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/plug")
public class Plug_Controller {

	@GetMapping(produces = "application/json")
	public ResponseEntity<UserDTO> getJson(@RequestParam String login) {
		response_time();
		if ("Login1".equalsIgnoreCase(login)) {
			String status = HttpStatus.OK.getReasonPhrase();
			UserDTO userDTO = new UserDTO(login, status);
			return new ResponseEntity<>(userDTO, HttpStatus.OK);
		} else {
			String status = HttpStatus.NOT_FOUND.getReasonPhrase();
			UserDTO userDTO = new UserDTO(login, status);
			return new ResponseEntity<>(userDTO, HttpStatus.NOT_FOUND);
		}
	}


//	@GetMapping(produces = "application/json")
//	public ResponseEntity<UserDTO> getJson() {
//		response_time();
//		User user = new User("Login1", "Password1");
//		UserDTO userDTO = new UserDTO(user.getLogin(), "OK");
//		return new ResponseEntity<>(userDTO, HttpStatus.OK);
//	}

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<UserDTO> postJson(@Valid @RequestBody User user) {
		response_time();
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		UserDTO userDTO = new UserDTO(user.getLogin(), user.getPassword(), date);
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}

	public void response_time(){
		try {
			Thread.sleep(1000 + new Random().nextInt(1001));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
