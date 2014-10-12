package com.windriver.intellij.plugin.ikey;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.io.IOException;

public class IKey extends AnAction {
    private static final String TITLE = "Inject key event";

    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        String keycode = Messages.showInputDialog(project, "What is the keycode to inject",
                TITLE, Messages.getQuestionIcon());

        boolean result = doInjectKey(keycode);
        String msg = String.format("%s to inject key event %s",
                (result ? "Success" : "Fail"), keycode);

        Messages.showMessageDialog(project, msg, TITLE,  Messages.getInformationIcon());
    }

    public boolean doInjectKey(String keycode) {
        try {
            Runtime r = Runtime.getRuntime();
            r.exec("adb shell input keyevent " + keycode);
            return true;
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
