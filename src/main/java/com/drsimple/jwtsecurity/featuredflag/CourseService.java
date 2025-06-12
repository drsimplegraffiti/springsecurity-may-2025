package com.drsimple.jwtsecurity.featuredflag;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CourseService {

    private static final double DISCOUNT_PERCENT = 0.2;

    public CourseDtoResponse createCourse(CourseDtoRequest request, boolean applyDiscount) {
        double price = request.getPrice();

        if (applyDiscount) {
            price = price - (price * DISCOUNT_PERCENT);
        }

        return CourseDtoResponse.builder()
                .id(generateCourseId())
                .name(request.getName())
                .price(price)
                .build();
    }

    private int generateCourseId() {
        return new Random().nextInt(10000); // Dummy ID generator
    }
}
