package cn.yfwz100.story.fx;

import cn.yfwz100.story.Story;
import javafx.animation.AnimationTimer;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

/**
 * The game loop of the board.
 *
 * @author yfwz100
 */
public class GameLoop extends AnimationTimer implements GameControl {

    /**
     * A property that indicates the running state.
     */
    private BooleanProperty runningProperty = new SimpleBooleanProperty(false);

    /**
     * A property that indicates the active state.
     */
    private BooleanProperty activeProperty = new SimpleBooleanProperty(false);

    /**
     * A property that indicates the keyboard state.
     */
    private MapProperty<KeyCode, Boolean> keyMapProperty = new SimpleMapProperty<>();

    /**
     * The canvas that the game is managing.
     */
    private Canvas canvas;

    /**
     * The storyProperty of current game loop.
     */
    private final ObjectProperty<Story> storyProperty = new SimpleObjectProperty<>();

    /**
     * On exit property.
     */
    private final ObjectProperty<EventHandler<ActionEvent>> onExit = new ObjectPropertyBase<EventHandler<ActionEvent>>() {
        @Override
        public Object getBean() {
            return GameLoop.this;
        }

        @Override
        public String getName() {
            return "onEndEvent";
        }
    };

    /**
     * Construct a new game loop with the given canvas.
     *
     * @param canvas the canvas.
     */
    public GameLoop(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Get the story property.
     *
     * @return the story property.
     */
    public Property<Story> storyProperty() {
        return storyProperty;
    }

    /**
     * Get the story to render.
     *
     * @return the story to render.
     */
    public Story getStory() {
        return storyProperty.getValue();
    }

    /**
     * Set the story to render.
     *
     * @param story the story to render.
     */
    public void setStory(Story story) {
        storyProperty.set(story);
    }

    /**
     * Get the onExit property.
     *
     * @return the onExit property.
     */
    public ObjectProperty<EventHandler<ActionEvent>> onExitProperty() {
        return onExit;
    }

    /**
     * Get onExit event handler.
     *
     * @return the event handler.
     */
    public EventHandler<ActionEvent> getOnExit() {
        return onExit.get();
    }

    /**
     * Set the onExit handler.
     *
     * @param onExit the onExit handler to set.
     */
    public void setOnExit(EventHandler<ActionEvent> onExit) {
        this.onExit.set(onExit);
    }

    /**
     * Invoke the onExit handler if it's not null.
     */
    private void invokeOnExit() {
        EventHandler<ActionEvent> onExitHandler = onExit.get();
        if (onExitHandler != null) {
            onExitHandler.handle(new ActionEvent());
        }
    }

    @Override
    public void handle(long now) {
        if (runningProperty.get()) {
            Story story = storyProperty.get();
            if (story != null) {
                story = story.update();

                if (story == null) {
                    invokeOnExit();
                    return;
                } else if (story != storyProperty.get()){
                    storyProperty.set(story);
                    return;
                }

                GraphicsContext g = canvas.getGraphicsContext2D();

                //<editor-fold defaultState="collapsed" desc="g.clear();">
                g.save();
                {
                    g.setFill(Color.WHITE);
                    g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                }
                g.restore();
                //</editor-fold>

                g.save();
                {
                    g.setLineWidth(0.1);
                    g.scale(5, 5);

                    story.actorIterator().forEachRemaining(actor -> {
                        if (actor instanceof ActorGraphics) {
                            ((ActorGraphics) actor).paint(g);
                        }
                    });
                }
                g.restore();
            }
        }
    }

    @Override
    public BooleanProperty activeProperty() {
        return activeProperty;
    }

    @Override
    public boolean isActive() {
        return activeProperty.get();
    }

    @Override
    public BooleanProperty runningProperty() {
        return runningProperty;
    }

    @Override
    public boolean isRunning() {
        return runningProperty.get();
    }

    MapProperty<KeyCode, Boolean> keyMapProperty() {
        return keyMapProperty;
    }

    @Override
    public void start() {
        runningProperty.set(true);
        activeProperty.set(true);
        super.start();
    }

    @Override
    public void resume() {
        runningProperty.set(true);
    }

    @Override
    public void pause() {
        runningProperty.set(false);
    }

    @Override
    public void stop() {
        runningProperty.set(false);
        activeProperty.set(false);

        super.stop();
    }
}
