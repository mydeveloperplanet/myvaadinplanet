package com.mydeveloperplanet.myvaadinplanet.data.service;

import java.util.List;

import com.mydeveloperplanet.myvaadinplanet.data.entity.Show;
import com.mydeveloperplanet.myvaadinplanet.data.entity.ShowEvent;
import com.mydeveloperplanet.myvaadinplanet.data.repository.ShowEventRepository;
import com.mydeveloperplanet.myvaadinplanet.data.repository.ShowRepository;

import org.springframework.data.domain.PageRequest;
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

    public List<ShowEvent> fetchShowEvents(int offset, int limit) {
        return showEventRepository.findAll(PageRequest.of(offset / limit, limit)).stream().toList();
    }

    public int countShowEvents() {
        return (int) showEventRepository.count();
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