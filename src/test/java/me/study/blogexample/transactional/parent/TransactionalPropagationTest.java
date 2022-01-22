package me.study.blogexample.transactional.parent;

import me.study.blogexample.transactional.child.Child;
import me.study.blogexample.transactional.child.ChildRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@DisplayName("@Transactional propagation 테스트")
class TransactionalPropagationTest {

    @Autowired
    private ParentService parentService;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    private Parent parent;
    private Child child;

    @BeforeEach
    void setUp() {
        parent = Parent.builder()
                .name("아버지")
                .age(40)
                .build();
        child = Child.builder()
                .name("아들")
                .age(10)
                .build();
    }

    @AfterEach
    void tearDown() {
        parentRepository.deleteAll();
        childRepository.deleteAll();
    }

    @Test
    @DisplayName("propagation이 REQUIRED, 부모가 문제인 경우 둘다 롤백된다.")
    void saveWithRequiredAndParentFailed() {
        // when
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> parentService.saveWithRequiredAndParentFailed(parent, child));

        // then
        assertThat(parentRepository.count()).isZero();
        assertThat(childRepository.count()).isZero();
    }

    @Test
    @DisplayName("propagation이 REQUIRED, 자식이 문제인 경우 둘다 롤백된다.")
    void saveWithRequiredAndChildFailed() {
        // when
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> parentService.saveWithRequiredAndChildFailed(parent, child));

        // then
        assertThat(parentRepository.count()).isZero();
        assertThat(childRepository.count()).isZero();
    }

    @Test
    @DisplayName("propagation이 REQUIRED_NEW, 부모가 문제인 경우 부모만 롤백된다.")
    void saveWithRequiredNewAndParentFailed() {
        // when
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> parentService.saveWithRequiredNewAndParentFailed(parent, child));

        // then
        assertThat(parentRepository.count()).isZero();
        assertThat(childRepository.count()).isOne();
    }

    @Test
    @DisplayName("propagation이 REQUIRED_NEW, 자식이 문제인 경우 자식만 롤백된다.")
    void saveWithRequiredNewAndChildFailed() {
        // when
        parentService.saveWithRequiredNewAndChildFailed(parent, child);

        // then
        assertThat(parentRepository.count()).isOne();
        assertThat(childRepository.count()).isZero();
    }
}
