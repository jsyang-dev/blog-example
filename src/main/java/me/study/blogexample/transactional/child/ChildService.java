package me.study.blogexample.transactional.child;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChildService {

    private final ChildRepository childRepository;

    public ChildService(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveWithRequired(Child child) {
        childRepository.save(child);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveWithRequiredAndFailed(Child child) {
        childRepository.save(child);
        throw new RuntimeException();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveWithRequiredNew(Child child) {
        childRepository.save(child);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveWithRequiredNewAndFailed(Child child) {
        childRepository.save(child);
        throw new RuntimeException();
    }
}
