package com.baranskagata.optometry.controller;

import com.baranskagata.optometry.dto.AppUserOptometrist;
import com.baranskagata.optometry.dao.Optometrist;
import com.baranskagata.optometry.dao.Work;
import com.baranskagata.optometry.service.OptometristService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping
    ResponseEntity<List<AppUserOptometrist>> getAllOptometrists(Pageable pageable) {
        return ResponseEntity.ok().body(optometristService.getAllOptometrists());
    }

    @GetMapping("{optometristNumber}")
    ResponseEntity<AppUserOptometrist> getOptometristByOptometristNumber(@PathVariable String optometristNumber) {
        return ResponseEntity.ok().body(optometristService.getOptometristByOptometristNumber(optometristNumber));
    }

    @PutMapping("{appUserId}")
    ResponseEntity<AppUserOptometrist> updateAppUserOptometrist(@PathVariable Long appUserId, @RequestBody AppUserOptometrist userOptometristData) {
        return ResponseEntity.ok().body(optometristService.updateAppUserOptometrist(appUserId, userOptometristData));
    }

    @PutMapping("{optometristId}/optometristNumber")
    ResponseEntity<Optometrist> updateOptometrist(@PathVariable Long optometristId, @RequestBody Optometrist optometristData) {
        return ResponseEntity.ok().body(optometristService.updateOptometrist(optometristId, optometristData));
    }

    @PostMapping("{appUserId}/works")
    ResponseEntity<List<Work>> addWorkByNameToOptometrist(@PathVariable Long appUserId, @RequestParam String name) {
        return ResponseEntity.ok().body(optometristService.addWork(appUserId, name));
    }

    @DeleteMapping("{appUserId}/works/{workName}")
    ResponseEntity<HttpStatus> removeWorkByNameFromOptometrist(@PathVariable Long appUserId, @PathVariable String workName) {
        optometristService.removeWork(appUserId, workName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{appUserId}/works")
    ResponseEntity<List<Work>> getOptometristWorks(@PathVariable Long appUserId){
        return ResponseEntity.ok().body(optometristService.getAllWorks(appUserId));
    }
    

}
