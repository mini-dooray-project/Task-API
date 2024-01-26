package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.domain.CommentResponse;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
class CommentRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @MockBean
    CommentRepository commentRepository;


    @Test
    void findByTask_TaskId() {

//        given(this.commentRepository.findByTask_TaskId(1L))

        List<CommentResponse> list = commentRepository.findByTask_TaskId(1L);
    }
}