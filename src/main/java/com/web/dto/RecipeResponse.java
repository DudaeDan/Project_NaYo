package com.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RecipeResponse {
    @JsonProperty("COOKRCP01")
    private CookRcp cookRcp;

    public CookRcp getCookRcp() {
        return cookRcp;
    }

    public void setCookRcp(CookRcp cookRcp) {
        this.cookRcp = cookRcp;
    }

    public static class CookRcp {
        @JsonProperty("total_count")
        private int totalCount;

        @JsonProperty("row")
        private List<Recipe> recipes;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<Recipe> getRecipes() {
            return recipes;
        }

        public void setRecipes(List<Recipe> recipes) {
            this.recipes = recipes;
        }
    }
}
