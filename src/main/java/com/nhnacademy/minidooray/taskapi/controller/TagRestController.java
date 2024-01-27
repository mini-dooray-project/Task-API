package com.nhnacademy.minidooray.taskapi.controller;

import com.nhnacademy.minidooray.taskapi.domain.DeleteResponse;
import com.nhnacademy.minidooray.taskapi.domain.TagRequest;
import com.nhnacademy.minidooray.taskapi.domain.TagResponse;
import com.nhnacademy.minidooray.taskapi.exception.ValidationException;
import com.nhnacademy.minidooray.taskapi.service.TagService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tags")
public class TagRestController {

    private final TagService tagService;

    public TagRestController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagResponse> getTags() {
        return tagService.getTags();
    }

    @GetMapping("/{tagId}")
    public TagResponse getTag(@PathVariable Long tagId) {
        return tagService.getTag(tagId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagResponse createTag(@Valid@RequestBody TagRequest tagRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        return tagService.createTag(tagRequest);
    }

    @PutMapping("/{tagId}")
    @ResponseStatus(HttpStatus.OK)
    public TagResponse updateTag(@PathVariable Long tagId, @Valid @RequestBody TagRequest tagRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        return tagService.updateTag(tagId, tagRequest);
    }

    @DeleteMapping("/{tagId}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteResponse deleteTag(@PathVariable Long tagId) {
        tagService.deleteTag(tagId);
        return new DeleteResponse("OK");
    }
}
