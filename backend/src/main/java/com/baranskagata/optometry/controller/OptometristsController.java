package com.baranskagata.optometry.controller;

import com.baranskagata.optometry.dto.AppUserOptometrist;
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
public class OptometristsController {
    private final OptometristService optometristService;
    private final WorkingPlanService workingPlanService;

    @GetMapping
    ResponseEntity<Page<AppUserOptometrist>> getAllOptometrists(Pageable pageable) {
        return ResponseEntity.ok().body(optometristService.getAllOptometrists(pageable));
    }

    @GetMapping("{optometristNumber}")
    ResponseEntity<AppUserOptometrist> getOptometristByOptometristNumber(@PathVariable String optometristNumber) {
        return ResponseEntity.ok().body(optometristService.getOptometristByOptometristNumber(optometristNumber));
    }

    @PutMapping("{id}")
    ResponseEntity<Optometrist> updateOptometrist(@PathVariable Long id, @RequestBody Optometrist optometristData) {
        return ResponseEntity.ok().body(optometristService.updateOptometrist(id, optometristData));
    }

    @PostMapping("/works")
    ResponseEntity<List<Work>> addWorkByNameToOptometrist(@RequestParam Long appUserId, @RequestParam String name) {
        return ResponseEntity.ok().body(optometristService.addWork(appUserId, name));
    }

    @DeleteMapping("{appUserId}/works/{workName}")
    ResponseEntity<HttpStatus> removeWorkByNameFromOptometrist(@PathVariable Long appUserId, @PathVariable String workName) {
        optometristService.removeWork(appUserId, workName);
        return ResponseEntity.noContent().build();
    }
    /*


    List<Work> getWorkByOptometristId(Long optometristId);



     */

}
