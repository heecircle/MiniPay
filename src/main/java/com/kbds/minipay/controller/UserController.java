package com.kbds.minipay.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kbds.minipay.dto.user.UserCreateRequest;
import com.kbds.minipay.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/create")
	public Long createUser(@RequestBody UserCreateRequest userCreateRequest) {
		return userService.userCreate(userCreateRequest);
	}
}
