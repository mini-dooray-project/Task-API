package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.TagRequest;
import com.nhnacademy.minidooray.taskapi.domain.TagResponse;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.Tag;
import com.nhnacademy.minidooray.taskapi.exception.ProjectNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.TagNotExistException;
import com.nhnacademy.minidooray.taskapi.repository.ProjectRepository;
import com.nhnacademy.minidooray.taskapi.repository.TagRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;

    public TagServiceImpl(TagRepository tagRepository, ProjectRepository projectRepository) {
        this.tagRepository = tagRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<TagResponse> getTags() {
        return tagRepository.findAllBy();
    }

    @Override
    public TagResponse getTag(Long tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new TagNotExistException();
        }
        return tagRepository.findByTagId(tagId);
    }

    @Override
    public List<TagResponse> getTagByProjectId(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ProjectNotExistException();
        }

        return tagRepository.findByProjectId(projectId);
    }

    @Override
    @Transactional
    public TagResponse createTag(TagRequest tagRequest) {
        Project project =
                projectRepository.findById(tagRequest.getProjectId()).orElseThrow(TagNotExistException::new);

        Tag tag = new Tag(project, tagRequest.getTagName());
        tagRepository.save(tag);
        return new TagResponse().entityToDto(tag);
    }

    @Override
    @Transactional
    public TagResponse updateTag(Long tagId, TagRequest tagRequest) {
        Project project = projectRepository.findById(tagRequest.getProjectId())
                .orElseThrow(ProjectNotExistException::new);

        Tag prevTag = tagRepository.findById(tagId).orElseThrow(TagNotExistException::new);

        Tag updatedTag = prevTag.updateTag(project, tagRequest.getTagName());

        return new TagResponse().entityToDto(updatedTag);
    }

    @Override
    @Transactional
    public void deleteTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(TagNotExistException::new);
        tagRepository.delete(tag);
    }
}
