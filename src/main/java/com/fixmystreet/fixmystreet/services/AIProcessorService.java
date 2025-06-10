package com.fixmystreet.fixmystreet.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIProcessorService {



    public String CategorizeReport(String context){

        return "New category";
    }

    public List<String> setKeywords(String context){

        return  List.of("pothole", "urgent", "dangerous");
    }

    public String rewriteDescription(String title, String description){

        return "sfsf";
    }

}
