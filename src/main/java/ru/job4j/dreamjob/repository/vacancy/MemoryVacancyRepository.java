package ru.job4j.dreamjob.repository.vacancy;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
public class MemoryVacancyRepository implements VacancyRepository {

    private int nextId = 1;

    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    private MemoryVacancyRepository() {
        save(new Vacancy(1,
                "Intern Java Developer",
                "Вакансия Intern Java Developer",
                LocalDateTime.of(2024, 6, 24, 14, 21),
                false,
                1,
                0
        ));
        save(new Vacancy(2,
                "Junior Java Developer",
                "Вакансия Junior Java Developer",
                LocalDateTime.of(2024, 6, 23, 14, 28),
                true,
                1,
                0
        ));
        save(new Vacancy(3,
                "Junior+ Java Developer",
                "Вакансия Junior+ Java Developer",
                LocalDateTime.of(2024, 6, 23, 14, 43),
                true,
                1,
                0
        ));
        save(new Vacancy(4,
                "Middle Java Developer",
                "Вакансия Middle Java Developer",
                LocalDateTime.of(2024, 6, 27, 15, 28),
                true,
                2,
                0
        ));
        save(new Vacancy(5,
                "Middle+ Java Developer",
                "Вакансия Middle+ Java Developer",
                LocalDateTime.of(2024, 6, 28, 14, 31),
                true,
                2,
                0
        ));
        save(new Vacancy(6,
                "Senior Java Developer",
                "Вакансия Senior Java Developer",
                LocalDateTime.of(2024, 6, 29, 17, 13),
                true,
                3,
                0
        ));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId++);
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(),
                (id, oldVacancy) -> new Vacancy(
                        oldVacancy.getId(),
                        vacancy.getTitle(),
                        vacancy.getDescription(),
                        vacancy.getCreationDate(),
                        vacancy.getVisible(),
                        vacancy.getCityId(),
                        vacancy.getFileId()
                )) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }
}
