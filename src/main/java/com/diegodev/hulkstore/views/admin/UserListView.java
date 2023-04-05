package com.diegodev.hulkstore.views.admin;

import com.diegodev.hulkstore.model.User;
import com.diegodev.hulkstore.service.UserService;
import com.diegodev.hulkstore.views.component.UserForm;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@RolesAllowed("ADMIN")
@Component
@Scope("prototype")
@Route(value = "user-list", layout = AdminPanelView.class)
@PageTitle("Users List | HulkStore")
public class UserListView extends VerticalLayout {
    Grid<User> grid = new Grid<>(User.class);
    TextField filterText = new TextField();
    UserForm form;
    UserService service;

    public UserListView(UserService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new UserForm();
        form.setWidth("25em");
        form.addListener(UserForm.SaveEvent.class, this::saveUser);
        form.addListener(UserForm.DeleteEvent.class, this::deleteUser);
        form.addListener(UserForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("users-grid");
        grid.setSizeFull();
        grid.setColumns("username", "name", "email");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editUser(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editUser(User contact) {
        if (contact == null) {
            closeEditor();
        } else {
            form.setUser(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void saveUser(UserForm.SaveEvent event) {
        service.saveUser(event.getUser());
        updateList();
        closeEditor();
    }

    private void deleteUser(UserForm.DeleteEvent event) {
        service.deleteUser(event.getUser());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setUser(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllUsers(filterText.getValue()));
    }
}