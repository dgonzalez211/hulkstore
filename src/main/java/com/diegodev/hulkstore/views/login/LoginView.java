package com.diegodev.hulkstore.views.login;

import com.diegodev.hulkstore.security.AuthenticatedUser;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.Arrays;

@AnonymousAllowed
@PageTitle("Login")
@Route(value = "login")
@CssImport(value = "./themes/hulkstore/loginBackground.css", themeFor = "vaadin-login-overlay-wrapper")
public class LoginView extends LoginOverlay implements BeforeEnterObserver {

    private final AuthenticatedUser authenticatedUser;

    public LoginView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("HulkStore");
        i18n.getHeader().setDescription("Login using your username and password");
        i18n.setAdditionalInformation(null);
        setI18n(i18n);

        setForgotPasswordButtonVisible(false);
        setOpened(true);

        Button button = new Button("Sign Up");
        button.addClickListener(buttonClickEvent -> {
            UI.getCurrent().navigate(SignUpView.class);
        });
        button.setId("signUp");

        addCustomComponent(button);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (authenticatedUser.get().isPresent()) {
            // Already logged in
            setOpened(false);
            event.forwardTo("");
        }

        setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }

    public void addCustomComponent(Component... pComponents) {
        Element loginFormElement = getElement();

        Element[] elements = Arrays.stream(pComponents).map(Component::getElement).toArray(Element[]::new);
        loginFormElement.appendChild(elements); // to have them in the DOM

        Arrays.stream(pComponents).forEach(component -> {
            String executeJsForFieldString =
                    "const form = document.getElementById(\"vaadinLoginForm\");\n" +
                            "const fields = form.getElementsByTagName(\"vaadin-button\");\n" +
                            "const loginButton = fields[0];\n" +
                            "const appendElement = document.getElementById(\""+ component.getId().get() + "\");\n" +
                            "loginButton.after(appendElement);";
            UI.getCurrent().getPage().executeJs(executeJsForFieldString);
        });
    }
}
