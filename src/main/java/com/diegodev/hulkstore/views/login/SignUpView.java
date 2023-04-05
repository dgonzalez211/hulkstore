package com.diegodev.hulkstore.views.login;

import com.diegodev.hulkstore.exceptions.CustomException;
import com.diegodev.hulkstore.model.User;
import com.diegodev.hulkstore.service.UserService;
import com.diegodev.hulkstore.utils.NotificationHelper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Sign Up")
@Route(value = "sign-up")
@AnonymousAllowed
@Tag("vaadin-signup-form")
public class SignUpView extends Div {

    private TextField name = new TextField("Full name");
    private TextField username = new TextField("Username");
    private EmailField email = new EmailField("Email address");
    private PasswordField hashedPassword = new PasswordField("Password");
    private Button loginButton = new Button("Login");
    private Button signUpButton = new Button("Sign Up");

    private Binder<User> binder = new Binder<>(User.class);

    public SignUpView(UserService userService) {
        addClassName("sign-up-view");

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Sign Up");

        dialog.add(createFormLayout());
        dialog.add(createButtonLayout());
        dialog.setCloseOnOutsideClick(false);
        binder.bindInstanceFields(this);
        clearForm();

        signUpButton.addClickListener(e -> {
            try {
                userService.signUp(name.getValue(), username.getValue(), email.getValue(), hashedPassword.getValue());
                NotificationHelper.showNotification("You have been registered!", NotificationVariant.LUMO_SUCCESS);
                clearForm();
                UI.getCurrent().navigate(LoginView.class);
            } catch (CustomException exception) {
                NotificationHelper.showNotification(exception.getMessage(), NotificationVariant.LUMO_ERROR);
            }

        });
        loginButton.addClickListener(e -> {
            clearForm();
            UI.getCurrent().navigate(LoginView.class);
        });

        dialog.open();
    }

    private void clearForm() {
        binder.setBean(new User());
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        email.setErrorMessage("Please enter a valid email address");
        formLayout.add(name, username, email, hashedPassword, email, hashedPassword);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        signUpButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(signUpButton);
        buttonLayout.add(loginButton);
        return buttonLayout;
    }
}