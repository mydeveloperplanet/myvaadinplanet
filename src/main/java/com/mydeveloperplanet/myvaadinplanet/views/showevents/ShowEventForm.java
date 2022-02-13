package com.mydeveloperplanet.myvaadinplanet.views.showevents;

import java.util.List;

import com.mydeveloperplanet.myvaadinplanet.data.entity.Show;
import com.mydeveloperplanet.myvaadinplanet.data.entity.ShowEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class ShowEventForm extends FormLayout {
    DatePicker date = new DatePicker();
    ComboBox<Show> show = new ComboBox<>("Show");

    Binder<ShowEvent> binder = new BeanValidationBinder<>(ShowEvent.class);
    private ShowEvent showEvent;

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public ShowEventForm(List<Show> shows) {
        addClassName("showevents-form");
        binder.bindInstanceFields(this);

        show.setItems(shows);
        show.setItemLabelGenerator(Show::getTitle);

        add(date,
            show,
            createButtonsLayout());
    }

    public void setShowEvent(ShowEvent showEvent) {
        this.showEvent = showEvent;
        binder.readBean(showEvent);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, showEvent)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(showEvent);
            fireEvent(new SaveEvent(this, showEvent));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class ShowEventFormEvent extends ComponentEvent<ShowEventForm> {
        private ShowEvent showEvent;

        protected ShowEventFormEvent(ShowEventForm source, ShowEvent showEvent) {
            super(source, false);
            this.showEvent = showEvent;
        }

        public ShowEvent getShowEvent() {
            return showEvent;
        }
    }

    public static class SaveEvent extends ShowEventFormEvent {
        SaveEvent(ShowEventForm source, ShowEvent showEvent) {
            super(source, showEvent);
        }
    }

    public static class DeleteEvent extends ShowEventFormEvent {
        DeleteEvent(ShowEventForm source, ShowEvent showEvent) {
            super(source, showEvent);
        }

    }

    public static class CloseEvent extends ShowEventFormEvent {
        CloseEvent(ShowEventForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}

