package com.fdm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdm.dal.AccountRepository;
import com.fdm.dal.ActorRepository;
import com.fdm.model.Account;
import com.fdm.model.PlayerCharacter;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class EditPlayerController {
	@Autowired
	ActorRepository aRepo;

	@PostMapping("/edit")
	public PlayerCharacter postLogin(@RequestBody PlayerCharacter pc) {

		System.out.println("/edit called with player character:" + pc);
		if (pc.equals(null)) {
			return null;
		} else {
			pc = aRepo.save(pc);

			return pc;
		}
	}

}
