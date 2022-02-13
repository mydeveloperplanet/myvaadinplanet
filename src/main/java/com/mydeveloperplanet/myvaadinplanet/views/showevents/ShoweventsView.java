package com.mydeveloperplanet.myvaadinplanet.views.showevents;

import com.mydeveloperplanet.myvaadinplanet.data.entity.Show;
import com.mydeveloperplanet.myvaadinplanet.data.entity.ShowEvent;
import com.mydeveloperplanet.myvaadinplanet.data.service.ShowService;
import com.mydeveloperplanet.myvaadinplanet.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Showevents")
@Route(value = "showevents", layout = MainLayout.class)
public class ShoweventsView extends VerticalLayout {
    ShowService showService;
    Grid<ShowEvent> grid = new Grid<>(ShowEvent.class);

    public ShoweventsView(ShowService showService) {
        this.showService = showService;
        setSpacing(false);

        configureGrid();
        add(grid);
        updateList();

        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private void configureGrid() {
        grid.addClassNames("showevents-grid");
        grid.setSizeFull();
        grid.setColumns("id", "date");
        grid.addColumn(showEvent -> showEvent.getShow().getTitle()).setHeader("Show");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void updateList() {
        grid.setItems(showService.findAllShowEvents(""));
    }

}
