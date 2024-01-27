package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.ProjectStatusResponse;
import java.util.List;

public interface ProjectStatusService {
    List<ProjectStatusResponse> getProjectStatuses();
}
