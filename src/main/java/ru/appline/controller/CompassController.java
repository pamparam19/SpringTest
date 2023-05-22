package ru.appline.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@RestController
public class CompassController {

    private static final TreeMap<Double, String> worldSides = new TreeMap<>();

    @PostMapping(value = "/learnWorldSides", consumes = "application/json", produces = "plain/text")
    public String learnDegrees(@RequestBody Map<String, String> data){
        data.forEach((k,v) ->
                worldSides.put(Double.parseDouble(v.split("-")[0].trim()), k));
        if (!worldSides.containsKey(0.0)){worldSides.put(0.0, "North");}
        return "Данные успешно обработаны";
    }

    @GetMapping(value = "getSideByDegree", consumes = "application/json", produces = "application/json")
    public Map<String, String> getSide(@RequestBody Map<String, Double> data){
        Map<String,String> map = new HashMap<>();
        if (data.get("Degree") >= 360){
            map.put(String.valueOf(data.get("Degree")), "Некорректные данные");
            return map;
        } else {
            map.put("Side", worldSides.floorEntry(data.get("Degree")).getValue());
            return map;
        }
    }
}
