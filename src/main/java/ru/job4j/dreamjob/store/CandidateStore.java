package ru.job4j.dreamjob.store;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class CandidateStore {


    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private final AtomicInteger idCount = new AtomicInteger();

    private CandidateStore() {
        candidates.put(idCount.incrementAndGet(), new Candidate(idCount.get(), "Фролово Андрей Николаевич",
                "Ищу вакансию на должность младшего Java разработчика"));
        candidates.put(idCount.incrementAndGet(), new Candidate(idCount.get(), "Middle Java Job",
                "Ищу вакансию на должность ведущего Java разработчика"));
        candidates.put(idCount.incrementAndGet(), new Candidate(idCount.get(), "Senior Java Job",
                "Ищу вакансию на должность старшего Java разработчика"));
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void add(Candidate candidate) {
        candidate.setId(idCount.incrementAndGet());
        candidate.setCreated(LocalDate.now());
        candidates.put(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void update(Candidate candidate) {
        candidates.replace(candidate.getId(), candidate);
    }
}
