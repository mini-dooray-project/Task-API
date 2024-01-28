package com.nhnacademy.minidooray.taskapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.minidooray.taskapi.domain.TagRequest;
import com.nhnacademy.minidooray.taskapi.domain.TagResponse;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.Tag;
import com.nhnacademy.minidooray.taskapi.exception.ProjectNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.TagNotExistException;
import com.nhnacademy.minidooray.taskapi.repository.ProjectRepository;
import com.nhnacademy.minidooray.taskapi.repository.TagRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Test
    void getTags() {
        when(tagRepository.findAllBy()).thenReturn(List.of(new TagResponse()));

        List<TagResponse> tags = tagService.getTags();

        assertEquals(1, tags.size());
        verify(tagRepository, times(1)).findAllBy();
    }

    @Test
    void getTag() {
        Long tagId = 1L;

        when(tagRepository.existsById(anyLong())).thenReturn(true);
        when(tagRepository.findByTagId(anyLong())).thenReturn(new TagResponse());

        TagResponse tag = tagService.getTag(tagId);

        assertNotNull(tag);
        verify(tagRepository, times(1)).existsById(anyLong());
        verify(tagRepository, times(1)).findByTagId(anyLong());
    }

    @Test
    void getTag_thenThrowTagNotExistException() {
        Long tagId = 1L;

        when(tagRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(TagNotExistException.class, () -> tagService.getTag(tagId));
    }

    @Test
    void getTagByProjectId() {
        Long projectId = 1L;

        when(projectRepository.existsById(anyLong())).thenReturn(true);
        when(tagRepository.findByProjectId(anyLong())).thenReturn(List.of(new TagResponse()));

        List<TagResponse> tagByProjectId = tagService.getTagByProjectId(projectId);

        assertEquals(1, tagByProjectId.size());
        verify(projectRepository, times(1)).existsById(anyLong());
        verify(tagRepository, times(1)).findByProjectId(anyLong());
    }

    @Test
    void getTag_thenThrowProjectNotExistException() {
        Long projectId = 1L;

        when(projectRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ProjectNotExistException.class, () -> tagService.getTagByProjectId(projectId));
    }

    @Test
    void createTag() {
        TagRequest tagRequest = getTagRequest();

        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(new Project()));

        TagResponse tag = tagService.createTag(tagRequest);

        assertNotNull(tag);
        assertEquals(tagRequest.getTagName(), tag.getTagName());
        verify(projectRepository, times(1)).findById(anyLong());
        verify(tagRepository, times(1)).save(any(Tag.class));
    }

    @Test
    void updateTag() {
        Long tagId = 1L;
        TagRequest tagRequest = getTagRequest();

        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(new Project()));
        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(new Tag()));

        TagResponse tag = tagService.updateTag(tagId, tagRequest);

        assertNotNull(tag);
        assertEquals(tag.getTagName(), tagRequest.getTagName());
        verify(projectRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteTag() {
        Long tagId = 1L;

        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(new Tag()));
        doNothing().when(tagRepository).delete(any());

        tagService.deleteTag(tagId);

        verify(tagRepository, times(1)).findById(anyLong());
        verify(tagRepository, times(1)).delete(any(Tag.class));
    }

    private TagRequest getTagRequest() {
        return new TagRequest(1L, "name");
    }
}