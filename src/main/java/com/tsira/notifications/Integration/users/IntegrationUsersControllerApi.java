package com.tsira.notifications.Integration.users;

import com.tsira.notifications.Integration.users.dto.IntegrationUsersGetDto;
import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.common.paginationandsort.PageView;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Integration Users", description = "Operations related to integration users.")
public interface IntegrationUsersControllerApi {

    PageView<IntegrationUsersGetDto> getIntegrationUsers(
            @Valid PageAndSortCriteria pageAndSortCriteria,
            @RequestParam(required = false) String search );

    void activateUser(@PathVariable Long id);
}
