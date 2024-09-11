package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryCandidateRepository implements CandidateRepository {

    private static final MemoryCandidateRepository INSTANCE = new MemoryCandidateRepository();

    private int nextId = 1;

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    public MemoryCandidateRepository() {
        save(new Candidate(1,
                "Дмитрий",
                "Имею опыт работы в...",
                LocalDateTime.of(2024, 6, 24, 14, 21)
        ));
        save(new Candidate(2,
                "Андрей",
                "Знаю необходимые технологии для...",
                LocalDateTime.of(2024, 7, 16, 15, 28)));
        save(new Candidate(3,
                "Георгиий",
                "Готов предложить свою кандидатуру...",
                LocalDateTime.of(2024, 8, 6, 17, 43)
        ));
    }

    public static MemoryCandidateRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public void deleteById(int id) {
        candidates.remove(id);
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(),
                (id, oldVacancy) -> new Candidate(
                        oldVacancy.getId(),
                        candidate.getName(),
                        candidate.getDescription(),
                        candidate.getCreateDate()
                )) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
