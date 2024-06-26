import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import de.sandwich.Main;

import java.util.Objects;
import java.util.Stack;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;

/**
 *  <h2>Utilities for Java and JavaFx to create graphical programs</h2>
 *  <p>
 *  These utilities are intended to simplify programming with Java/JavaFX
 *  and keep the code clean.
 *  These specific utilities make some processes in JavaFX easier.
 *  Please note that you need the JavaFX Constructor Utilities to use them.
 * 
 *  This version is a work in porgress version!!!
 * 
 *  <p>
 *  <h3>Infos for this specific version:</h3>
 *  <p>
 *  You need the JavaFXConstructorUtilities to use these utilities!
 *  <p>
 * @author bauer
 * @version 1.0
 * @since 16.03.2024
 */

public class JavaFXUtilities {

    /**
     * This method places a control in the middle of a pane
     * @param child The object that should be placed in the middle.
     * @param pane The pane in which the object is located.
     */
    public static void centeringChildInPane(Control child, Pane pane) {
        child.layoutXProperty().bind(pane.widthProperty().subtract(child.widthProperty()).divide(2));
    }

    /**
     * This method calculates how large the font must be so that 
     * the label fits into the specified size. What happens is that 
     * the label is made smaller all the time until it fitse
     * @param labelo The label to be resized.
     * @param availableWidth The width that the label should have.
     * @param startSize The Size where the program starts to test if the Label fits.
     * @param precision The number that is calculated per run minus the font size. The 
     *                  larger the size, the faster the calculation will be, but the more 
     *                  accurate it will be. The smaller the more precise but the slower.
     */
    public static void fitLabel(Label label, double availableWidth, double startSize, double precision) {
        double fontSize = startSize;
        label.setFont(new Font(fontSize));

        label.widthProperty().addListener(changeListener -> {
            if (label.widthProperty().get() > availableWidth) {
                label.setFont(new Font(label.getFont().getSize() - precision));
            }
        });
    }

    /**
     * This method turns a color value into a rgb string value
     * @param color The value to be changed.
     * @return Returns the new String with the rgb value.
     */
    public static String colorToRGB(Color color) {
        return String.format("rgb(%d, %d, %d)",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255)
        );
    }

    /**
     * This method creates a plus symbol.
     * @param id This is the basic root of the IDs in plus.
     * @param lineWidth This determines the width of the lines in the plus.
     * @param lineHeight This determines the height of the lines in the plus.
     * @param rotate This determines the rotating of the hole plus.
     * @param borderWidth This determines the border width of the lines in the plus if you don't want a border set it to 0.
     * @param borderColor This determines the border color of the lines in the plus if you don't want a border set it to null.
     * @param plusColor This determines the fill color of the lines in the plus.
     * @param lineX This sets the x value from both lines
     * @param lineY This sets the y value from both lines
     * @return It returns a stack pane with two rectangles inside that have specific values.
     * One is rotated 90 degrees to represent the plus symbole.
     */
    public static StackPane buildPlus(String id, double lineWidth, double lineHeight, double rotate, double borderWidth, Color borderColor, Color plusColor, double lineX, double lineY) {
        StackPane plus = new StackPane();
        plus.setId(id);

        Rectangle lineOne = buildRectangle(id + "_LineOne", lineWidth, lineHeight, plusColor, true, borderColor, borderWidth);
        lineOne.setRotate(rotate);
        lineOne.setX(lineX);
        lineOne.setY(lineY);

        Rectangle lineTwo = buildRectangle(id + "_LineTwo", lineWidth, lineHeight, plusColor, true, borderColor, borderWidth);
        lineTwo.setRotate(90 + rotate);
        lineTwo.setX(lineX);
        lineTwo.setY(lineY);

        return new StackPane(lineOne, lineTwo);
    }

    /**
     * This method returns a node with a specific id in a pane
     * @param id The id from the node.
     * @param root The pane in which the search is being carried out.
     * @return It returns null if no node was found.
     */
    public static Object getNodeOutAPane(String id, Pane root) {

        Stack<Pane> panes = new Stack<>();
        panes.push(root);

        while (!panes.isEmpty()) {
            Pane p = panes.pop();
            for (Object c: p.getChildren().toArray()) {
                if (c instanceof Node) {
                    if(((Node) c).getId() != null) {
                        if (((Node) c).getId().equals(id))
                            return c;
                    }
                    if (c instanceof Pane) {
                        panes.push((Pane) c);
                    }
                }
            }
        }

        return null;
    }


    //Java FX Images (sourcePath start's add the Resources direction. Example: /pictures/menu/heading.png)

    public static Image creatImage(String sourcePath) {
        try {
            return new Image(Objects.requireNonNull(Main.class.getResourceAsStream(sourcePath)));
        } catch (Exception e) {
            System.out.println("\033[1;31mSource aus dem SourcePath \"" + sourcePath + "\" konnte nicht gefunden werden!\033[0m");
        }
        return null;
    }

    /**
     *  JavaFX Animations:
     *  Alle möglichen animation, einfach zu erstellen
     *  einfache Methoden!
     *  <p>Animation Typs:</p>
     *  <p>TranslateTransition: Ändert die Position eines Nodes.</p>
     *  <p>ScaleTransition: Ändert die Größe eines Nodes.</p>
     *  <p>RotateTransition: Ändert die Rotation eines Nodes.</p>
     *  <p>FadeTransition: Ändert die Transparenz eines Nodes.</p>
     *  <p>PathTransition: Bewegt einen Node entlang eines Pfads.</p>
     *  <p>FillTransition: Ändert die Füllfarbe eines Shapes.</p>
     *  <p>StrokeTransition: Ändert die Randfarbe eines Shapes.</p>
     *  <p>ParallelTransition: Ermöglicht das Ausführen mehrerer Transitionen gleichzeitig.</p>
     *  <p>SequentialTransition: Ermöglicht das Ausführen von Transitionen nacheinander.</p>
     */


    public static TranslateTransition moveAnimation(Node object, Duration length, double toY, double toX, int cycleCount) {
        TranslateTransition transition = new TranslateTransition(length, object);

        transition.setToY(toY);
        transition.setToX(toX);
        transition.setCycleCount(cycleCount);

        return transition;
    }
    public static ScaleTransition scaleAnimation(Node object, Duration length, double byY, double byX, int cycleCount) {
        ScaleTransition transition = new ScaleTransition(length, object);

        transition.setByY(byY);
        transition.setByX(byX);
        transition.setCycleCount(cycleCount);

        return transition;
    }

}
