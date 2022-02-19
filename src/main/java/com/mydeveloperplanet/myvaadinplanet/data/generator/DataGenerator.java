package com.mydeveloperplanet.myvaadinplanet.data.generator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.mydeveloperplanet.myvaadinplanet.data.entity.Show;
import com.mydeveloperplanet.myvaadinplanet.data.entity.ShowEvent;
import com.mydeveloperplanet.myvaadinplanet.data.repository.ShowEventRepository;
import com.mydeveloperplanet.myvaadinplanet.data.repository.ShowRepository;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(ShowRepository showRepository, ShowEventRepository showEventRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (showRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");
            ExampleDataGenerator<Show> showGenerator = new ExampleDataGenerator<>(Show.class, LocalDateTime.now());
            showGenerator.setData(Show::setTitle, DataType.BOOK_TITLE);
            List<Show> shows = showRepository.saveAll(showGenerator.create(10, seed));

            logger.info("... generating 50 ShowEvent entities...");
            ExampleDataGenerator<ShowEvent> showEventGenerator = new ExampleDataGenerator<>(ShowEvent.class, LocalDateTime.now());
            showEventGenerator.setData(ShowEvent::setDate, DataType.DATE_NEXT_1_YEAR);

            Random r = new Random(seed);
            List<ShowEvent> showEvents = showEventGenerator.create(1000, seed).stream().map(showEvent -> {
                showEvent.setShow(shows.get(r.nextInt(shows.size())));
                return showEvent;
            }).collect(Collectors.toList());

            showEventRepository.saveAll(showEvents);

            logger.info("Generated demo data");
        };
    }

}
