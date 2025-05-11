package com.drsimple.jwtsecurity.sample;


import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sample")
public class SampleController {


    //     When to Use @InitBinder:
//    Parsing non-standard date/time formats
//
//    Trimming input strings
//
//    Converting enums from custom string values
//
//    Registering custom validation logic
//    It configures how request parameters (like ?date=2025-05-10) are converted or bound to Java objects (like Date).
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @GetMapping("/example")
    public ResponseEntity<Map<String, String>> exampleEndpoint(@RequestParam("date") Date date) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Received date: " + date.toString());
        return ResponseEntity.ok(response);
    }
}
