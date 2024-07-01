package com.web.service;

import com.web.dto.Recipe;
import com.web.dto.RecipeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static final Logger logger = LoggerFactory.getLogger(RecipeServiceImpl.class);
    
    private final RestTemplate restTemplate;

    @Value("${api.foodsafety.key}")
    private String apiKey;

    @Value("${api.foodsafety.serviceId}")
    private String serviceId;

    @Value("${api.foodsafety.dataType}")
    private String dataType;

    @Autowired
    public RecipeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.restTemplate.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));
    }

    @Override
    public List<Recipe> fetchRecipes(int start, int end) {
        String url = String.format("http://openapi.foodsafetykorea.go.kr/api/%s/%s/%s/%d/%d",
                                    apiKey, serviceId, dataType, start, end);
        logger.info("Fetching recipes from URL: {}", url);

        RecipeResponse response = restTemplate.getForObject(url, RecipeResponse.class);

        List<Recipe> recipes = response != null ? response.getCookRcp().getRecipes() : null;
        if (recipes != null) {
            for (Recipe recipe : recipes) {
                logger.info("Recipe ID: {}, Name: {}", recipe.getRCP_SEQ(), recipe.getRCP_NM());
            }
        } else {
            logger.warn("No recipes found in the response.");
        }
        return recipes;
    }

    @Override
    public int getTotalCount() {
        String url = String.format("http://openapi.foodsafetykorea.go.kr/api/%s/%s/%s/1/1",
                                    apiKey, serviceId, dataType);
        logger.info("Fetching total count from URL: {}", url);

        RecipeResponse response = restTemplate.getForObject(url, RecipeResponse.class);
        return response != null ? response.getCookRcp().getTotalCount() : 0;
    }

//    @Override
//    public Recipe getRecipeById(String id) {
//        String url = String.format("http://openapi.foodsafetykorea.go.kr/api/%s/%s/%s/%s/%s",
//                                    apiKey, serviceId, dataType, id, id);
//        logger.info("Fetching recipe by ID from URL: {}", url);
//
//        RecipeResponse response = restTemplate.getForObject(url, RecipeResponse.class);
//        if (response != null && response.getCookRcp().getRecipes() != null && !response.getCookRcp().getRecipes().isEmpty()) {
//            return response.getCookRcp().getRecipes().get(0);
//        }
//        return null;
//    }

    @Override
    public Recipe getRecipeByName(String name) {
        String url = String.format("http://openapi.foodsafetykorea.go.kr/api/%s/%s/%s/1/1000",
                                    apiKey, serviceId, dataType);
        logger.info("Fetching recipe by Name from URL: {}", url);

        RecipeResponse response = restTemplate.getForObject(url, RecipeResponse.class);
        if (response != null && response.getCookRcp().getRecipes() != null) {
            for (Recipe recipe : response.getCookRcp().getRecipes()) {
                if (recipe.getRCP_NM().equals(name)) {
                    return recipe;
                }
            }
        }
        return null;
    }
}
