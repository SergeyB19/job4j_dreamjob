package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateDBStore;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CandidateStoreTest {
    @Test
    public void whenCreateCandidate() {
        CandidateDBStore store = new CandidateDBStore((new Main().loadPool()));
        Candidate candidate = new Candidate(0, "John",
                "description", LocalDate.now(), new byte[]{0});
        store.add(candidate);
        Candidate postInDb = store.findById(candidate.getId());
        assertThat(postInDb.getName()).isEqualTo(candidate.getName());
    }

    @Test
    public void whenUpdateCandidate() {
        CandidateDBStore store = new CandidateDBStore((new Main().loadPool()));
        Candidate candidate = new Candidate(0, "John",
                "description", LocalDate.now(), new byte[]{0});
        store.add(candidate);
        candidate.setName("Bob");
        store.update(candidate);
        Candidate postInDb = store.findById(candidate.getId());
        assertThat(postInDb.getName()).isEqualTo(candidate.getName());
    }

    @Test
    public void whenAddTwoCandidatesThenGetAll() {
        CandidateDBStore store = new CandidateDBStore((new Main().loadPool()));
        Candidate candidate1 =  new Candidate(0, "John",
                "description", LocalDate.now(), new byte[]{0});
        Candidate candidate2 =  new Candidate(0, "Bob",
                "description", LocalDate.now(), new byte[]{0});
        store.add(candidate1);
        store.add(candidate2);
        List<Candidate> candidates = store.findAll();
        assertThat(candidates).contains(candidate1, candidate2);
    }
}