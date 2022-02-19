package com.mydeveloperplanet.myvaadinplanet.views.showevents;

import java.util.List;

import com.mydeveloperplanet.myvaadinplanet.data.entity.ShowEvent;
import com.mydeveloperplanet.myvaadinplanet.data.service.ShowService;
import com.mydeveloperplanet.myvaadinplanet.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Showevents")
@Route(value = "showevents", layout = MainLayout.class)
public class ShowEventsView extends VerticalLayout {
    ShowService showService;
    Grid<ShowEvent> grid = new Grid<>(ShowEvent.class);
    ShowEventForm showEventForm;

    public ShowEventsView(ShowService showService) {
        this.showService = showService;
        setSpacing(false);

        configureGrid();
        configureForm();
        add(getContent());
        updateList();
        closeEditor();

        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, showEventForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, showEventForm);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureGrid() {
        grid.addClassNames("showevents-grid");
        grid.setSizeFull();
        grid.setColumns("id", "date");
        grid.addColumn(showEvent -> showEvent.getShow().getTitle()).setHeader("Show");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editShowEvent(event.getValue()));
    }

    private void configureForm() {
        showEventForm = new ShowEventForm(showService.findAllShows());
        showEventForm.setWidth("25em");
        showEventForm.addListener(ShowEventForm.SaveEvent.class, this::saveShowEvent);
        showEventForm.addListener(ShowEventForm.DeleteEvent.class, this::deleteShowEvent);
        showEventForm.addListener(ShowEventForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveShowEvent(ShowEventForm.SaveEvent event) {
        showService.saveShowEvent(event.getShowEvent());
        updateList();
        closeEditor();
    }

    public void editShowEvent(ShowEvent showEvent) {
        if (showEvent == null) {
            closeEditor();
        } else {
            showEventForm.setShowEvent(showEvent);
            showEventForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void deleteShowEvent(ShowEventForm.DeleteEvent event) {
        showService.deleteShowEvent(event.getShowEvent());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        showEventForm.setShowEvent(null);
        showEventForm.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        DataProvider<ShowEvent, Void> dataProvider =
                DataProvider.fromCallbacks(
                        // First callback fetches items based on a query
                        query -> {
                            // The index of the first item to load
                            int offset = query.getOffset();

                            // The number of items to load
                            int limit = query.getLimit();

                            List<ShowEvent> showEvents = showService.fetchShowEvents(offset, limit);

                            return showEvents.stream();
                        },
                        // Second callback fetches the total number of items currently in the Grid.
                        // The grid can then use it to properly adjust the scrollbars.
                        query -> showService.countShowEvents());
        grid.setDataProvider(dataProvider);
    }

}
