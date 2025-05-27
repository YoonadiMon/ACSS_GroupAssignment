package Utils;

import MainProgram.MainMenuGUI;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowNav {

    public static class setCloseOperation {

        public setCloseOperation() {
        }
    }
    @FunctionalInterface
    public interface FrameAction {
        void showFrame();
    }

    public static void setCloseOperation(JFrame currentFrame, FrameAction action) {
        currentFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        currentFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                currentFrame.dispose();
                if (action != null) {
                    action.showFrame();
                } else {
                    new MainMenuGUI();
                }
            }
        });
    }
}