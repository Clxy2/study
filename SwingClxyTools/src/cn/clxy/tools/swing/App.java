package cn.clxy.tools.swing;

import java.util.ArrayList;
import java.util.List;

import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.SingleFrameApplication;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * The main class of the application.
 */
public class App extends SingleFrameApplication {

    private View view;

    @Inject
    public void setView(View view) {
        this.view = view;
    }

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {

        List<Module> modules = new ArrayList<Module>();

        //初始化Guice。
        //配置Application和ApplicationContext
        modules.add(new Module() {

            public void configure(Binder binder) {
                binder.bind(ApplicationContext.class).toInstance(getContext());
            }
        });

        //配置View。
        modules.add(new Module() {

            public void configure(Binder binder) {
                binder.bind(View.class).toInstance(new View(Application.getInstance(App.class)));
            }
        });

        Injector injector = Guice.createInjector(modules);

        //注册Application。
        injector.injectMembers(this);

        //Start up
        show(view);
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of App
     */
    public static App getApplication() {
        return Application.getInstance(App.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(App.class, args);
    }
}
