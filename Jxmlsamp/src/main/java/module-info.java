module org.eclipse.jxmlsamp {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.eclipse.jxmlsamp to javafx.fxml;
    exports org.eclipse.jxmlsamp;
}
