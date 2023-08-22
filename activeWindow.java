import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

// This program gives a list of active programs.



public class activeWindow {
    public static void main(String[] args) throws Exception {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    activeWindow();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        timer.scheduleAtFixedRate(task, new Date(), 5000); //It checks the active program in each 5 seconds.
    }
    public static void activeWindow() throws IOException {

        Runtime runtime = Runtime.getRuntime();

        // It gets the information by an Apple Script that can be also run on Script Editor.
        String applescriptCommand =  "global frontApp, frontAppName, windowTitle\n" +
                "set windowTitle to \"\"\n" +
                "tell application \"System Events\"\n" +
                "\tset frontApp to first application process whose frontmost is true\n" +
                "\tset frontAppName to name of frontApp\n" +
                "\ttell process frontAppName\n" +
                "\t\ttell (1st window whose value of attribute \"AXMain\" is true)\n" +
                "\t\t\tset windowTitle to value of attribute \"AXTitle\"\n" +
                "\t\tend tell\n" +
                "\tend tell\n" +
                "end tell\n" +
                "\n" +
                "return {frontAppName, windowTitle}";

        String[] args = { "osascript", "-e", applescriptCommand };
        Process process = Runtime.getRuntime().exec(args);
        BufferedReader r =  new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        while((line=r.readLine())!=null) {

            System.out.println(line);

            }

    }
}
