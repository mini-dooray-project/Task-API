package com.nhnacademy.minidooray.taskapi.controller;


import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberResponse;
import com.nhnacademy.minidooray.taskapi.exception.ValidationException;
import com.nhnacademy.minidooray.taskapi.service.ProjectMemberService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class ProjectMemberRestController {

    private final ProjectMemberService projectMemberService;

    public ProjectMemberRestController(ProjectMemberService projectMemberService) {
        this.projectMemberService = projectMemberService;
    }

    @GetMapping("/projects/{projectId}")
    public List<ProjectMemberResponse> getMembersByProjectId(@PathVariable Long projectId) {
        return projectMemberService.getMembersByProject(projectId);
    }

    @GetMapping("/{memberId}")
    public List<ProjectMemberResponse> getMembersByMemberId(@PathVariable String memberId) {
        return projectMemberService.getMemberByMemberId(memberId);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectMemberResponse createMember(@Valid @RequestBody ProjectMemberRegisterRequest memberRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        return projectMemberService.createMember(memberRequest);
    }

    @PutMapping("/{memberId}/projects/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectMemberResponse updateMember(@PathVariable String memberId, @PathVariable Long projectId,
                                                              @RequestBody ProjectMemberModifyRequest memberModifyRequest) {
        return projectMemberService.updateMember(memberId, projectId, memberModifyRequest);
    }
}
