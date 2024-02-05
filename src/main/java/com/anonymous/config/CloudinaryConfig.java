package com.anonymous.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    public static final String CLOUD_NAME = "dvda6cmt8";
    public static final String API_KEY = "235853855945823";
    public static final String API_SECRET = "NHlRYDpBxTUzFHumTgNpU5bv0es";

    @Bean
    public Cloudinary getCloudinary() {
        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", CLOUD_NAME,
                        "api_key", API_KEY,
                        "api_secret", API_SECRET
                )
        );
    }

}
