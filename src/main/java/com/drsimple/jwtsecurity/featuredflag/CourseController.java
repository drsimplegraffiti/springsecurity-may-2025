package com.drsimple.jwtsecurity.featuredflag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.togglz.core.Feature;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final FeatureManager featureManager;

    @PostMapping("/apply-discount")
    public ResponseEntity<CourseDtoResponse> createCourse(@RequestBody CourseDtoRequest request) {
        boolean applyDiscount = featureManager.isActive(MyFeatures.DISCOUNT_APPLIED);
        CourseDtoResponse response = courseService.createCourse(request, applyDiscount);
        return ResponseEntity.ok(response);
    }
}