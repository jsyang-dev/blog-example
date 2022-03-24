package me.study.blogexample.transactional;

import me.study.blogexample.transactional.child.Child;
import me.study.blogexample.transactional.child.ChildRepository;
import me.study.blogexample.transactional.parent.Parent;
import me.study.blogexample.transactional.parent.ParentRepository;
import me.study.blogexample.transactional.parent.ParentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.IllegalTransactionStateException;

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
    @DisplayName("propagation이 REQUIRED, 부모가 문제인 경우 둘 다 Rollback 된다.")
    void saveWithRequiredAndParentFailed() {
        // when
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> parentService.saveWithRequiredAndParentFailed(parent, child));

        // then
        assertThat(parentRepository.count()).isZero();
        assertThat(childRepository.count()).isZero();
    }

    @Test
    @DisplayName("propagation이 REQUIRED, 자식이 문제인 경우 둘 다 Rollback 된다.")
    void saveWithRequiredAndChildFailed() {
        // when
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> parentService.saveWithRequiredAndChildFailed(parent, child));

        // then
        assertThat(parentRepository.count()).isZero();
        assertThat(childRepository.count()).isZero();
    }

    @Test
    @DisplayName("propagation이 REQUIRES_NEW, 부모가 문제인 경우 부모만 Rollback 된다.")
    void saveWithRequiresNewAndParentFailed() {
        // when
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> parentService.saveWithRequiresNewAndParentFailed(parent, child));

        // then
        assertThat(parentRepository.count()).isZero();
        assertThat(childRepository.count()).isOne();
    }

    @Test
    @DisplayName("propagation이 REQUIRES_NEW, 자식이 문제인 경우 둘 다 Rollback 된다.")
    void saveWithRequiresNewAndChildFailed() {
        // when
        try {
            parentService.saveWithRequiresNewAndChildFailed(parent, child);
        } catch (Exception e) {
        }

        // then
        assertThat(parentRepository.count()).isZero();
        assertThat(childRepository.count()).isZero();
    }

    @Test
    @DisplayName("propagation이 MANDATORY, 이미 시작한 트랜잭션이 없으면 예외를 발생한다.")
    void saveWithMandatoryAndNoTransaction() {
        // when
        assertThatExceptionOfType(IllegalTransactionStateException.class)
                .isThrownBy(() -> parentService.saveWithMandatoryAndNoTransaction(parent, child));

        // then
        assertThat(parentRepository.count()).isOne();
        assertThat(childRepository.count()).isZero();
    }

    @Test
    @DisplayName("propagation이 NEVER, 이미 시작한 트랜잭션이 있으면 예외를 발생한다.")
    void saveWithNeverAndTransaction() {
        // when
        assertThatExceptionOfType(IllegalTransactionStateException.class)
                .isThrownBy(() -> parentService.saveWithNeverAndTransaction(parent, child));

        // then
        assertThat(parentRepository.count()).isZero();
        assertThat(childRepository.count()).isZero();
    }

    @Test
    @DisplayName("propagation이 NESTED, 부모가 문제인 경우 둘 다 Rollback 된다.")
    void saveWithNestedNewAndParentFailed() {
        // when
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> parentService.saveWithNestedAndParentFailed(parent, child));

        // then
        assertThat(parentRepository.count()).isZero();
        assertThat(childRepository.count()).isZero();
    }

    @Test
    @DisplayName("propagation이 NESTED, 자식이 문제인 경우 둘 다 Rollback 된다.")
    void saveWithNestedNewAndChildFailed() {
        // when
        try {
            parentService.saveWithNestedAndChildFailed(parent, child);
        } catch (Exception e) {
        }

        // then
        assertThat(parentRepository.count()).isZero();
        assertThat(childRepository.count()).isZero();
    }
}
