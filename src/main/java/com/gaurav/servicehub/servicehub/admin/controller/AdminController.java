package com.gaurav.servicehub.servicehub.admin.controller;

import com.gaurav.servicehub.servicehub.admin.service.AdminService;
import com.gaurav.servicehub.servicehub.common.constants.ApiPaths;
import com.gaurav.servicehub.servicehub.common.dto.ApiResponse;
import com.gaurav.servicehub.servicehub.common.util.ApiResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ApiPaths.ADMIN)
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PutMapping("/providers/{providerId}/approve")
    public ResponseEntity<ApiResponse<Void>> approveProvider(
            @PathVariable UUID providerId
    ) {

        adminService.approveProvider(providerId);

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        null,
                        "Provider approved successfully"
                )
        );
    }
}