package com.drsimple.jwtsecurity.config;


import com.drsimple.jwtsecurity.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class AppController {

    public final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping
    public ResponseEntity<?> getAppInfo(){
        AppInfo appInfo = appService.printAppInfo();
        return ResponseEntity.ok(ApiResponse.success(appInfo));
    }
}
