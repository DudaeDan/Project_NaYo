package com.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Recipe {
    @JsonProperty("RCP_PARTS_DTLS")
    private String RCP_PARTS_DTLS;
    
    @JsonProperty("RCP_WAY2")
    private String RCP_WAY2;
    
    @JsonProperty("MANUAL_IMG20")
    private String MANUAL_IMG20;
    
    @JsonProperty("MANUAL20")
    private String MANUAL20;
    
    @JsonProperty("RCP_SEQ")
    private String RCP_SEQ;
    
    @JsonProperty("INFO_NA")
    private String INFO_NA;
    
    @JsonProperty("INFO_WGT")
    private String INFO_WGT;
    
    @JsonProperty("INFO_PRO")
    private String INFO_PRO;
    
    @JsonProperty("MANUAL_IMG13")
    private String MANUAL_IMG13;
    
    @JsonProperty("MANUAL_IMG14")
    private String MANUAL_IMG14;
    
    @JsonProperty("MANUAL_IMG15")
    private String MANUAL_IMG15;
    
    @JsonProperty("MANUAL_IMG16")
    private String MANUAL_IMG16;
    
    @JsonProperty("MANUAL_IMG10")
    private String MANUAL_IMG10;
    
    @JsonProperty("MANUAL_IMG11")
    private String MANUAL_IMG11;
    
    @JsonProperty("MANUAL_IMG12")
    private String MANUAL_IMG12;
    
    @JsonProperty("MANUAL_IMG17")
    private String MANUAL_IMG17;
    
    @JsonProperty("MANUAL_IMG18")
    private String MANUAL_IMG18;
    
    @JsonProperty("MANUAL_IMG19")
    private String MANUAL_IMG19;
    
    @JsonProperty("INFO_FAT")
    private String INFO_FAT;
    
    @JsonProperty("HASH_TAG")
    private String HASH_TAG;
    
    @JsonProperty("MANUAL_IMG02")
    private String MANUAL_IMG02;
    
    @JsonProperty("MANUAL_IMG03")
    private String MANUAL_IMG03;
    
    @JsonProperty("RCP_PAT2")
    private String RCP_PAT2;
    
    @JsonProperty("MANUAL_IMG04")
    private String MANUAL_IMG04;
    
    @JsonProperty("MANUAL_IMG05")
    private String MANUAL_IMG05;
    
    @JsonProperty("MANUAL_IMG01")
    private String MANUAL_IMG01;
    
    @JsonProperty("MANUAL01")
    private String MANUAL01;
    
    @JsonProperty("ATT_FILE_NO_MK")
    private String ATT_FILE_NO_MK;
    
    @JsonProperty("MANUAL_IMG06")
    private String MANUAL_IMG06;
    
    @JsonProperty("MANUAL_IMG07")
    private String MANUAL_IMG07;
    
    @JsonProperty("MANUAL_IMG08")
    private String MANUAL_IMG08;
    
    @JsonProperty("MANUAL_IMG09")
    private String MANUAL_IMG09;
    
    @JsonProperty("MANUAL08")
    private String MANUAL08;
    
    @JsonProperty("MANUAL09")
    private String MANUAL09;
    
    @JsonProperty("MANUAL06")
    private String MANUAL06;
    
    @JsonProperty("MANUAL07")
    private String MANUAL07;
    
    @JsonProperty("MANUAL04")
    private String MANUAL04;
    
    @JsonProperty("MANUAL05")
    private String MANUAL05;
    
    @JsonProperty("MANUAL02")
    private String MANUAL02;
    
    @JsonProperty("MANUAL03")
    private String MANUAL03;
    
    @JsonProperty("MANUAL11")
    private String MANUAL11;
    
    @JsonProperty("MANUAL12")
    private String MANUAL12;
    
    @JsonProperty("MANUAL10")
    private String MANUAL10;
    
    @JsonProperty("INFO_CAR")
    private String INFO_CAR;
    
    @JsonProperty("MANUAL19")
    private String MANUAL19;
    
    @JsonProperty("RCP_NA_TIP")
    private String RCP_NA_TIP;
    
    @JsonProperty("INFO_ENG")
    private String INFO_ENG;
    
    @JsonProperty("MANUAL17")
    private String MANUAL17;
    
    @JsonProperty("MANUAL18")
    private String MANUAL18;
    
    @JsonProperty("RCP_NM")
    private String RCP_NM;
    
    @JsonProperty("MANUAL15")
    private String MANUAL15;
    
    @JsonProperty("MANUAL16")
    private String MANUAL16;
    
    @JsonProperty("MANUAL13")
    private String MANUAL13;
    
    @JsonProperty("MANUAL14")
    private String MANUAL14;

    private String encodedName;

    // 개수 확인용
    public String getManual(int step) {
        switch (step) {
            case 1: return MANUAL01;
            case 2: return MANUAL02;
            case 3: return MANUAL03;
            case 4: return MANUAL04;
            case 5: return MANUAL05;
            case 6: return MANUAL06;
            case 7: return MANUAL07;
            case 8: return MANUAL08;
            case 9: return MANUAL09;
            case 10: return MANUAL10;
            case 11: return MANUAL11;
            case 12: return MANUAL12;
            case 13: return MANUAL13;
            case 14: return MANUAL14;
            case 15: return MANUAL15;
            case 16: return MANUAL16;
            case 17: return MANUAL17;
            case 18: return MANUAL18;
            case 19: return MANUAL19;
            case 20: return MANUAL20;
            default: return null;
        }
    }

    public String getManualImg(int step) {
        switch (step) {
            case 1: return MANUAL_IMG01;
            case 2: return MANUAL_IMG02;
            case 3: return MANUAL_IMG03;
            case 4: return MANUAL_IMG04;
            case 5: return MANUAL_IMG05;
            case 6: return MANUAL_IMG06;
            case 7: return MANUAL_IMG07;
            case 8: return MANUAL_IMG08;
            case 9: return MANUAL_IMG09;
            case 10: return MANUAL_IMG10;
            case 11: return MANUAL_IMG11;
            case 12: return MANUAL_IMG12;
            case 13: return MANUAL_IMG13;
            case 14: return MANUAL_IMG14;
            case 15: return MANUAL_IMG15;
            case 16: return MANUAL_IMG16;
            case 17: return MANUAL_IMG17;
            case 18: return MANUAL_IMG18;
            case 19: return MANUAL_IMG19;
            case 20: return MANUAL_IMG20;
            default: return null;
        }
    }
}
