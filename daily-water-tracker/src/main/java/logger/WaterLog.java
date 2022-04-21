package logger;

import java.io.*;

public class WaterLog {

    private PrintWriter writer;

    public void waterLog(String filePath){
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                this.writer = new PrintWriter(file); //write to new file if file not found
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                this.writer = new PrintWriter(new FileWriter(file, true)); //if file exists, append to end of file
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(String logMessage){
        writer.println(logMessage);
        writer.flush();
    }

    public void close(){
        writer.close();
    }

}
