module org.kys.bnmo {
    requires javafx.controls;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.xml;
    requires lombok;
    requires org.jetbrains.annotations;
    requires java.desktop;
    requires javafx.swing;


    exports org.kys.bnmo;
    exports org.kys.bnmo.model;
}