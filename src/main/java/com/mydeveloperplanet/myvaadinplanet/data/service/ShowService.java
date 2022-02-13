package com.mydeveloperplanet.myvaadinplanet.data.service;

import java.util.List;

import com.mydeveloperplanet.myvaadinplanet.data.entity.Show;
import com.mydeveloperplanet.myvaadinplanet.data.entity.ShowEvent;
import com.mydeveloperplanet.myvaadinplanet.data.repository.ShowEventRepository;
import com.mydeveloperplanet.myvaadinplanet.data.repository.ShowRepository;

import org.springframework.stereotype.Service;

@Service
public class ShowService {

    private final ShowEventRepository showEventRepository;
    private final ShowRepository showRepository;

    public ShowService(ShowEventRepository showEventRepository,
                       ShowRepository showRepository) {
        this.showEventRepository = showEventRepository;
        this.showRepository = showRepository;
    }

    public List<ShowEvent> findAllShowEvents(String stringFilter) {
//        if (stringFilter == null || stringFilter.isEmpty()) {
        return showEventRepository.findAll();
//        } else {
//            return showEventRepository.search(stringFilter);
//        }
    }

    public long countShowEvents() {
        return showEventRepository.count();
    }

    public void deleteShowEvent(ShowEvent showEvent) {
        showEventRepository.delete(showEvent);
    }

    public void saveShowEvent(ShowEvent showEvent) {
        if (showEvent == null) {
            System.err.println("ShowEvent is null. Are you sure you have connected your form to the application?");
            return;
        }
        showEventRepository.save(showEvent);
    }

    public List<Show> findAllShows() {
        return showRepository.findAll();
    }

}