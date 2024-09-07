package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryVacancyRepository implements VacancyRepository {

    private static final MemoryVacancyRepository INSTANCE = new MemoryVacancyRepository();

    private int nextId = 1;

    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    private MemoryVacancyRepository() {
        save(new Vacancy(1,
                "Intern Java Developer",
                "Вакансия Intern Java Developer",
                LocalDateTime.of(2024, 6, 24, 14, 21)
        ));
        save(new Vacancy(2,
                "Junior Java Developer",
                "Вакансия Junior Java Developer",
                LocalDateTime.of(2024, 6, 23, 14, 28)));
        save(new Vacancy(3,
                "Junior+ Java Developer",
                "Вакансия Junior+ Java Developer",
                LocalDateTime.of(2024, 6, 23, 14, 43)
        ));
        save(new Vacancy(4,
                "Middle Java Developer",
                "Вакансия Middle Java Developer",
                LocalDateTime.of(2024, 6, 27, 15, 28)
        ));
        save(new Vacancy(5,
                "Middle+ Java Developer",
                "Вакансия Middle+ Java Developer",
                LocalDateTime.of(2024, 6, 28, 14, 31)
        ));
        save(new Vacancy(6,
                "Senior Java Developer",
                "Вакансия Senior Java Developer",
                LocalDateTime.of(2024, 6, 29, 17, 13)
        ));
    }

    public static MemoryVacancyRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId++);
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public void deleteById(int id) {
        vacancies.remove(id);
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(),
                (id, oldVacancy) -> new Vacancy(
                        oldVacancy.getId(),
                        vacancy.getTitle(),
                        vacancy.getDescription(),
                        vacancy.getCreateDate()
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
