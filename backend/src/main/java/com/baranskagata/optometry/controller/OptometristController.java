package com.baranskagata.optometry.controller;

import com.baranskagata.optometry.entity.Optometrist;
import com.baranskagata.optometry.entity.Work;
import com.baranskagata.optometry.service.OptometristService;
import com.baranskagata.optometry.service.WorkingPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/optometrists")
public class OptometristController {
    private final OptometristService optometristService;
    private final WorkingPlanService workingPlanService;

    @GetMapping
    ResponseEntity<Page<Optometrist>> getAllOptometrists(Pageable pageable, @RequestParam String sortBy){
        return ResponseEntity.ok().body(optometristService.getAllOptometrists(pageable,sortBy));
    }

    @GetMapping("{id}")
    ResponseEntity<Optometrist> getOptometristById(@PathVariable Long id){
        return ResponseEntity.ok().body(optometristService.getOptometristById(id));
    }

    @PutMapping("{id}")
    ResponseEntity<Optometrist> updateOptometrist(@PathVariable Long id, @RequestBody Optometrist optometristData){
        return ResponseEntity.ok().body(optometristService.updateOptometrist(id,optometristData));
    }

    @PostMapping("works")
    ResponseEntity<List<Work>> addWorkByIdToOptometrist(@RequestParam Long workId, @RequestParam Long optometristId){
        return ResponseEntity.ok().body(optometristService.addWork(optometristId, workId));
    }

    @DeleteMapping("works")
    ResponseEntity<HttpStatus> deleteWorkByIdFromOptometrist(@RequestParam Long workId, @RequestParam Long optometristId){
        return ResponseEntity.noContent().build();
    }
    /*


    List<Work> getWorkByOptometristId(Long optometristId);



     */

}
