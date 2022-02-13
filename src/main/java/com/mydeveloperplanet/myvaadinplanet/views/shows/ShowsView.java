package com.mydeveloperplanet.myvaadinplanet.views.shows;

import com.mydeveloperplanet.myvaadinplanet.data.entity.Show;
import com.mydeveloperplanet.myvaadinplanet.data.service.ShowService;
import com.mydeveloperplanet.myvaadinplanet.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Shows")
@Route(value = "shows", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class ShowsView extends VerticalLayout {
    ShowService showService;
    Grid<Show> grid = new Grid<>(Show.class);

    public ShowsView(ShowService showService) {
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
        grid.addClassNames("show-grid");
        grid.setSizeFull();
        grid.setColumns("id", "title");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
}

    private void updateList() {
        grid.setItems(showService.findAllShows());
    }

}
