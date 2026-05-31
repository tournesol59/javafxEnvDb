module org.openjfxroot {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
	requires java.desktop;
	requires javafx.graphics;
	requires java.prefs;
	requires javafx.base;
    
    opens org.openjfxroot.ui to javafx.fxml;
    exports org.openjfxroot.ui;
    opens org.openjfxroot to javafx.fxml;
    exports org.openjfxroot;
}
