package com.prajjawal.Trading_Platform.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping
  public String home() {
    return "Welcome to IntelliCryptoTrade, an AI powered Trading Platform";
  }
}
