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
    public void saveWithRequired(Child child, boolean exception) {
        childRepository.save(child);
        if (exception) {
            throw new RuntimeException();
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveWithRequiresNew(Child child, boolean exception) {
        childRepository.save(child);
        if (exception) {
            throw new RuntimeException();
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void saveWithMandatory(Child child) {
        childRepository.save(child);
    }

    @Transactional(propagation = Propagation.NEVER)
    public void saveWithNever(Child child) {
        childRepository.save(child);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void saveWithNested(Child child, boolean exception) {
        childRepository.save(child);
        if (exception) {
            throw new RuntimeException();
        }
    }
}
