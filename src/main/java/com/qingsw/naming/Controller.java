package com.qingsw.naming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Controller {

  @Autowired
  NamingService namingService;

  @GetMapping("/shijing")
  public ResponseEntity shijing(@RequestParam(name = "givenName") String givenName) throws IOException {
    return ResponseEntity.ok(namingService.naming(givenName));
  }
}
