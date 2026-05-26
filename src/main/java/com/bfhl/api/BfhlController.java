package com.bfhl.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class BfhlController {

    private final BfhlService bfhlService;

    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    @PostMapping("/bfhl")
    public ResponseEntity<?> processData(@RequestBody BfhlRequest request) {
        try {
            if (request.getData() == null || request.getData().isEmpty()) {
                BfhlResponse errorResponse = BfhlResponse.builder()
                        .isSuccess(false)
                        .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            BfhlResponse response = bfhlService.processData(request.getData());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            BfhlResponse errorResponse = BfhlResponse.builder()
                    .isSuccess(false)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Collections.singletonMap("status", "ok"));
    }
}
