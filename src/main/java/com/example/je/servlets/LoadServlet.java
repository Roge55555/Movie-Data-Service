package com.example.je.servlets;

import com.example.je.RegularJob;
import com.example.je.model.Film;
import com.example.je.model.FilmCountryGenre;
import com.example.je.model.FullFilm;
import com.example.je.services.CountryService;
import com.example.je.services.FilmService;
import com.example.je.services.GenreService;
import com.google.gson.GsonBuilder;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class LoadServlet extends HttpServlet {

    private final FilmService filmService = FilmService.getService();

    private final CountryService countryService = CountryService.getService();

    private final GenreService genreService = GenreService.getService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if(req.getHttpServletMapping().getMatchValue() == null) {

            List<Film> films = filmService.loadFilms();
            List<FilmCountryGenre> filmCountryGenreList = filmService.saveFilms(films);
            countryService.saveCountry(filmCountryGenreList);
            genreService.saveGenre(filmCountryGenreList);

        } else {

            FullFilm fullFilm = filmService.loadFullFilm(Long.decode(req.getHttpServletMapping().getMatchValue()));
            List<FilmCountryGenre> filmCountryGenreList = filmService.saveFullFilm(fullFilm);
            countryService.saveCountry(filmCountryGenreList);
            genreService.saveGenre(filmCountryGenreList);

            if (Objects.nonNull(fullFilm)) {

                String filmJsonString = new GsonBuilder().serializeNulls().create().toJson(fullFilm);
                resp.getWriter().write(filmJsonString);

            }
            else {
                resp.getWriter().write("No film with such Id");
            }
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        String key = "org.quartz.impl.StdSchedulerFactory.KEY";
        ServletContext servletContext = config.getServletContext();
        StdSchedulerFactory factory = (StdSchedulerFactory) servletContext.getAttribute(key);
        try {
            Scheduler quartzScheduler = factory.getScheduler("MyQuartzScheduler");
            scheduleMainJob(quartzScheduler);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void scheduleMainJob(Scheduler scheduler) throws SchedulerException {
        requireNonNull(scheduler);

//        try {
//            Thread.sleep(30000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        JobDetail jobDetail = newJob(RegularJob.class).storeDurably()
                .withIdentity("MAIN_JOB")
                .withDescription("Main Job to Perform")
                .build();
        Trigger trigger = newTrigger().forJob(jobDetail)
                .withIdentity("MAIN_JOB_TRIGG")
                .withDescription("Trigger for Main Job")
                .withSchedule(simpleSchedule().withIntervalInSeconds(60).repeatForever())
                .startAt(Date.from(Instant.now().plus(3, ChronoUnit.MINUTES))).build();

        scheduler.scheduleJob(jobDetail, trigger);
    }
}
