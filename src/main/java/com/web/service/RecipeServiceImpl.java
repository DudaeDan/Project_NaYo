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

    // API 키를 application.properties에서 가져옴
    @Value("${api.foodsafety.key}")
    private String apiKey;

    // 서비스 ID를 application.properties에서 가져옴
    @Value("${api.foodsafety.serviceId}")
    private String serviceId;

    // 데이터 타입을 application.properties에서 가져옴
    @Value("${api.foodsafety.dataType}")
    private String dataType;

    // RestTemplate을 주입받아 초기화
    @Autowired
    public RecipeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        // RestTemplate에 JSON 변환기를 설정
        this.restTemplate.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));
    }

    // 주어진 범위의 레시피 목록을 가져오는 메서드
    @Override
    public List<Recipe> fetchRecipes(int start, int end) {
        // API 요청 URL을 구성
        String url = String.format("http://openapi.foodsafetykorea.go.kr/api/%s/%s/%s/%d/%d",
                                    apiKey, serviceId, dataType, start, end);
        logger.info("Fetching recipes from URL: {}", url);

        // API 요청을 보내고 응답을 RecipeResponse 객체로 받음
        RecipeResponse response = restTemplate.getForObject(url, RecipeResponse.class);

        // 응답에서 레시피 목록을 추출
        List<Recipe> recipes = response != null ? response.getCookRcp().getRecipes() : null;
        if (recipes != null) {
            // 레시피 목록을 로그에 출력
            for (Recipe recipe : recipes) {
                logger.info("Recipe ID: {}, Name: {}", recipe.getRCP_SEQ(), recipe.getRCP_NM());
            }
        } else {
            logger.warn("No recipes found in the response.");
        }
        return recipes;
    } 

    // 총 레시피 개수를 가져오는 메서드
    @Override
    public int getTotalCount() {
        // API 요청 URL을 구성
        String url = String.format("http://openapi.foodsafetykorea.go.kr/api/%s/%s/%s/1/1",
                                    apiKey, serviceId, dataType);
        logger.info("Fetching total count from URL: {}", url);

        // API 요청을 보내고 응답을 RecipeResponse 객체로 받음
        RecipeResponse response = restTemplate.getForObject(url, RecipeResponse.class);
        // 응답에서 총 레시피 개수를 추출
        return response != null ? response.getCookRcp().getTotalCount() : 0;
    }

    // 레시피 이름으로 특정 레시피를 가져오는 메서드
    @Override
    public Recipe getRecipeByName(String name) {
        // API 요청 URL을 구성
        String url = String.format("http://openapi.foodsafetykorea.go.kr/api/%s/%s/%s/1/1000",
                                    apiKey, serviceId, dataType);
        logger.info("Fetching recipe by Name from URL: {}", url);

        // API 요청을 보내고 응답을 RecipeResponse 객체로 받음
        RecipeResponse response = restTemplate.getForObject(url, RecipeResponse.class);
        // 응답에서 레시피 목록을 순회하며 이름이 일치하는 레시피를 찾음
        if (response != null && response.getCookRcp().getRecipes() != null) {
            for (Recipe recipe : response.getCookRcp().getRecipes()) {
                if (recipe.getRCP_NM().equals(name)) {
                    return recipe;
                }
            }
        }
        return null; // 일치하는 레시피가 없으면 null 반환
    }
}
