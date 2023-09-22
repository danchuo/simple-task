package com.danchuo.stringunique.controller;


import com.danchuo.stringunique.constraint.LatinStringConstraint;
import com.danchuo.stringunique.service.UniqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Validated
@RestController
public class UniqueController {

    @Autowired
    private UniqueService uniqueService;


    @GetMapping("/calculate-unique")
    @ResponseBody
    public Map<Character, Integer> getNodes(@RequestParam @LatinStringConstraint String input) {
        return uniqueService.getUnique(input);
    }
}
