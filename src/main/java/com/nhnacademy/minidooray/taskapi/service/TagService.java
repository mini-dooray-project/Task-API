package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.TagRequest;
import com.nhnacademy.minidooray.taskapi.domain.TagResponse;
import java.util.List;

public interface TagService {

    List<TagResponse> getTags();

    TagResponse getTag(Long tagId);

    // todo#2
    List<TagResponse> getTagByProjectId(Long projectId);

    TagResponse createTag(TagRequest tagRequest);

    TagResponse updateTag(Long tagId, TagRequest tagRequest);

    void deleteTag(Long tagId);
}
