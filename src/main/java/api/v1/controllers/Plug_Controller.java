package api.v1.controllers;

import api.v1.dao.DataBaseWorker;
import api.v1.models.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/plug")
public class Plug_Controller {

	private final DataBaseWorker dbw;
	@Autowired
	public Plug_Controller(DataBaseWorker dbw){
		this.dbw = dbw;
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<String> getJson(@RequestParam String login) {
		response_time();
		try {
			User user = dbw.select(login);
			if (user == null){
				throw new RuntimeException("User " + login + " not found");
			}

			String json = "{\"login\":\"" + user.getLogin() + "\",\"status\":\"ok\"}";
			return new ResponseEntity<>(json, HttpStatus.OK);
		}
		catch (Exception e){
			String json = "{\"login\":\"" + login + "\",\"status\":\"" + e.getMessage() + "\"}";
			return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> postJson(@Valid @RequestBody User user) {
		response_time();
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		user.setDate(date);
		dbw.insert(user);
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
