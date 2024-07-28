package bff.zona_fit;

import bff.zona_fit.gui.ZonaFitForma;
import com.formdev.flatlaf.FlatDarculaLaf;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class ZonaFitSwing {
    public static void main(String[] args) {
        //configurate dark mode
        FlatDarculaLaf.setup();
        //configurate Spring
        ConfigurableApplicationContext contextSpring = new SpringApplicationBuilder(ZonaFitSwing.class)
                                                        .headless(false)
                                                        .web(WebApplicationType.NONE)
                                                        .run(args);
        //Create swing object
        SwingUtilities.invokeLater(() -> {
            ZonaFitForma zonaFitForma = contextSpring.getBean(ZonaFitForma.class);
            zonaFitForma.setVisible(true);
        });
    }
}
