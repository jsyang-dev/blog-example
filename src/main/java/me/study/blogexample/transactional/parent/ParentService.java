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
        childService.saveWithRequired(child, false);
        throw new RuntimeException();
    }

    @Transactional
    public void saveWithRequiredAndChildFailed(Parent parent, Child child) {
        parentRepository.save(parent);
        childService.saveWithRequired(child, true);
    }

    @Transactional
    public void saveWithRequiresNewAndParentFailed(Parent parent, Child child) {
        parentRepository.save(parent);
        childService.saveWithRequiresNew(child, false);
        throw new RuntimeException();
    }

    @Transactional
    public void saveWithRequiresNewAndChildFailed(Parent parent, Child child) {
        parentRepository.save(parent);
        childService.saveWithRequiresNew(child, true);
    }

    public void saveWithMandatoryAndNoTransaction(Parent parent, Child child) {
        parentRepository.save(parent);
        childService.saveWithMandatory(child);
    }

    @Transactional
    public void saveWithNeverAndTransaction(Parent parent, Child child) {
        parentRepository.save(parent);
        childService.saveWithNever(child);
    }

    @Transactional
    public void saveWithNestedAndParentFailed(Parent parent, Child child) {
        parentRepository.save(parent);
        childService.saveWithNested(child, false);
        throw new RuntimeException();
    }

    @Transactional
    public void saveWithNestedAndChildFailed(Parent parent, Child child) {
        parentRepository.save(parent);
        childService.saveWithNested(child, true);
    }
}
