package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
public class MemoryCandidateRepository implements CandidateRepository {

    private int nextId = 1;

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    public MemoryCandidateRepository() {
        save(new Candidate(1,
                "Дмитрий",
                "Имею опыт работы в...",
                LocalDateTime.of(2024, 6, 24, 14, 21),
                1,
                0
        ));
        save(new Candidate(2,
                "Андрей",
                "Знаю необходимые технологии для...",
                LocalDateTime.of(2024, 7, 16, 15, 28),
                2,
                0
        ));
        save(new Candidate(3,
                "Георгиий",
                "Готов предложить свою кандидатуру...",
                LocalDateTime.of(2024, 8, 6, 17, 43),
                3,
                0
        ));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(),
                (id, oldVacancy) -> new Candidate(
                        oldVacancy.getId(),
                        candidate.getName(),
                        candidate.getDescription(),
                        candidate.getCreateDate(),
                        candidate.getCityId(),
                        candidate.getFileId()
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
