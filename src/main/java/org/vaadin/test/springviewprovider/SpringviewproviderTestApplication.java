package org.vaadin.test.springviewprovider;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringviewproviderTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringviewproviderTestApplication.class, args);
    }

    @SpringUI(path = "/ui")
    @Theme("valo")
    public static class MainUI extends UI {

        @Autowired
        SpringViewProvider viewProvider;

        @Override
        protected void init(VaadinRequest request) {
            final Navigator navigator = new Navigator(this, this);
            navigator.addProvider(viewProvider);
            navigator.setErrorView(new ErrorView());
            setNavigator(navigator);

            View a = viewProvider.getView("foo");
            View b = viewProvider.getView("bar");
            View c = viewProvider.getView("hello");
            View d = viewProvider.getView("world");
        }
    }

    private static abstract class AbstactView extends CssLayout implements View {

        public AbstactView() {
            addComponent(new Label(this.getClass().getName()));
        }

        @Override
        public void enter(ViewChangeListener.ViewChangeEvent event) {
        }
    }

    @SpringView(name = "foo")
    @ViewScope
    private static class FooView extends AbstactView {}

    @SpringView(name = "bar")
    @ViewScope
    private static class BarView extends AbstactView {}

    @SpringView(name = "hello")
    @ViewScope
    private static class HelloView extends AbstactView {}

    @SpringView(name = "world")
    @ViewScope
    private static class WorldView extends AbstactView {}

    @SpringView(name = "error")
    @ViewScope
    private static class ErrorView extends AbstactView {}
}