package jamify_2;

import java.io.*;
import java.util.ArrayList;

public class SavePlaylists  {

    public void save (){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("t.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(AllSongs.playlistNamePathsList);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void retrieve(){

        /** Provide the path of your project and append "/t.tmp" to it*/
        File f = new File("/Users/saishree/IdeaProjects/Jamify2/t.tmp");
        if(!f.exists() && !f.isDirectory()) {
            return;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("t.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);
            AllSongs.playlistNamePathsList = (ArrayList<PlaylistNamePath>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
