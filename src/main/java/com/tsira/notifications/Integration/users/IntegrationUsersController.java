package com.tsira.notifications.Integration.users;

import com.tsira.notifications.Integration.users.dto.IntegrationUsersGetDto;
import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.common.paginationandsort.PageView;
import com.tsira.notifications.security.user.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("admin/integration-users")
@RequiredArgsConstructor
public class IntegrationUsersController implements IntegrationUsersControllerApi{

    private final AdminUserService service;

    @Override
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PageView<IntegrationUsersGetDto> getIntegrationUsers(
            @Valid PageAndSortCriteria pageAndSortCriteria,
            @RequestParam(required = false) String search ) {
        return PageView.of(service.getIntegrationUsers(pageAndSortCriteria, search));
    }

    @Override
    @PatchMapping("{id}/activate-user")
    @PreAuthorize("hasRole('ADMIN')")
    public void activateUser(@PathVariable Long id) {
        service.activateUser(id);
    }
}
