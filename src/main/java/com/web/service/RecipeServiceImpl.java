package com.web.service;

import com.web.dto.Recipe;
import com.web.dto.RecipeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

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
    }

    @Override
    public List<Recipe> fetchRecipes(int start, int end) {
        String url = String.format("http://openapi.foodsafetykorea.go.kr/api/%s/%s/%s/%d/%d",
                                    apiKey, serviceId, dataType, start, end);
        RecipeResponse response = restTemplate.getForObject(url, RecipeResponse.class);
        return response != null ? response.getCookRcp().getRecipes() : null;
    }

    @Override
    public int getTotalCount() {
        // API 요청을 통해 총 레시피 수를 가져오는 코드 추가
        String url = String.format("http://openapi.foodsafetykorea.go.kr/api/%s/%s/%s/1/1",
                                    apiKey, serviceId, dataType);
        RecipeResponse response = restTemplate.getForObject(url, RecipeResponse.class);
        return response != null ? response.getCookRcp().getTotalCount() : 0;
    }
}
