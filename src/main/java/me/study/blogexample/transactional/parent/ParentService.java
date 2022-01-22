package me.study.blogexample.transactional.parent;

import lombok.extern.slf4j.Slf4j;
import me.study.blogexample.transactional.child.Child;
import me.study.blogexample.transactional.child.ChildService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ParentService {

    private final ParentRepository parentRepository;
    private final ChildService childService;

    public ParentService(ParentRepository parentRepository, ChildService childService) {
        this.parentRepository = parentRepository;
        this.childService = childService;
    }

    @Transactional
    public void saveWithRequiredAndParentFailed(Parent parent, Child child) {
        parentRepository.save(parent);
        childService.saveWithRequired(child);
        throw new RuntimeException();
    }

    @Transactional
    public void saveWithRequiredAndChildFailed(Parent parent, Child child) {
        parentRepository.save(parent);
        childService.saveWithRequiredAndFailed(child);
    }

    @Transactional
    public void saveWithRequiredNewAndParentFailed(Parent parent, Child child) {
        parentRepository.save(parent);
        childService.saveWithRequiredNew(child);
        throw new RuntimeException();
    }

    @Transactional
    public void saveWithRequiredNewAndChildFailed(Parent parent, Child child) {
        parentRepository.save(parent);
        try {
            childService.saveWithRequiredNewAndFailed(child);
        } catch (Exception e) {
            log.warn("자식 호출 실패");
        }
    }
}
