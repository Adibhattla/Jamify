package jamify_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class AllSongs extends JPanel implements ActionListener {
    JButton addNewSongBtn = new JButton("Add New Song");
    JPanel allSongsPanel;
    ArrayList<String> allSongsList = new ArrayList<>();
    final static String allSongsFileLocation = "/Users/saishree/Desktop/Allsongs.csv";
    File allSongsFile = new File(allSongsFileLocation);
    static ArrayList<PlaylistNamePath> playlistNamePathsList = new ArrayList<>();
    //to keep track of the last song number
    int lastSongNumber=0;
    int y = 40;
    JPanel eachSongPanel;
   
    static volatile Queue<String> songQueue=new LinkedList<String>();
    static volatile Stack nextstack = new Stack();

public  Stack<String> getsongStack() {
    return nextstack;
}
public  void setsongStack(Stack<String> stacktemp ) {
    nextstack = stacktemp;
}

public  Queue<String> getSongQ() {
    return songQueue;
}
public  void setSongQ(Queue<String> songQ) {
    songQueue = songQ;
}


    /* Constructor, sets up the panel and Sets the position and size of each component by calling
       its setBounds() method. */
    public AllSongs(){
        setLayout(null);
        add(addNewSongBtn);
        addNewSongBtn.setBounds(280,10,100,20);
        setLayout(null);
        setBounds(20,20,350,550);
        addNewSongBtn.addActionListener(this);

    }

    /** this method displays the songs present in the backend file on to the UI, it takes the file as an argument */

    public AllSongs displaySongsOnThePanel(File inputFile, boolean displayAddSong) {
            System.out.println("I am Called here");
        String line;
        /* flag to display the ADD NEW SONG Button or not. It should be displayed only in the main panel and not in the play lists*/
        if (!displayAddSong)
            this.remove(addNewSongBtn);

        /* reads the songs from the file and stores it in an arrayList(allSongsList) */
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            allSongsList.clear();
            while ((line = br.readLine()) != null) {
                line.trim();
                if(line.isEmpty()) continue;
                allSongsList.add(line);
            }
           boolean isWhite = false;
            /* for each song in the list, create a JLabels for song name, '+' and '-' and set the to the song name. */
            for (int i=0; i<allSongsList.size(); i++) {

                 eachSongPanel = new JPanel();
                 if (isWhite){
                     eachSongPanel.setBackground(Color.WHITE);
                     isWhite =false;
                 }
                 else{
                     eachSongPanel.setBackground(Color.lightGray);
                     isWhite=true;
                 }
//                eachSongPanel.setLayout(null);
                JLabel songName =  new JLabel(allSongsList.get(i).split(",")[1]);
                JLabel deleteSongLabel = new JLabel("-");
                JLabel addLabel = new JLabel("+");
                lastSongNumber = Integer.parseInt(allSongsList.get(i).split(",")[0]);

                deleteSongLabel.setForeground(Color.BLUE);
                addLabel.setForeground(Color.BLUE);

//                songName.setBounds(30, y+20,200,20);
//                addLabel.setBounds(200, y+20, 10,20);
//                deleteSongLabel.setBounds(220, y+20, 10,20);
                eachSongPanel.setBounds(20, y,350,30);
                y = y+30;

                eachSongPanel.add(songName);
                eachSongPanel.add(deleteSongLabel);
                eachSongPanel.add(addLabel);
                this.add(eachSongPanel);

                int finalI = i;
                String songPath = allSongsList.get(finalI).split(",")[2];

                /*onclick of the song, the song path is passed to the MP3 */
                songName.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        //path to be passed to the Mp3
                        //System.out.println(songPath);
                        
                        AudioPlay audioPlay = new AudioPlay();
                        try {
                        audioPlay.OneSong(songPath);
                        audioPlay.JamifyPlay(getSongQ());
                        }
                        catch (Exception e1){
                       e1.printStackTrace();
                        }
                    }
                });

                /*On click of '-' removes the song from the all songs panel as well as from the file */
                deleteSongLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        Container parent = songName.getParent();
                        parent.remove(songName);
                        parent.remove(deleteSongLabel);
                        parent.remove(addLabel);
                        parent.remove(deleteSongLabel);
                        parent.validate();
                        parent.repaint();

                        try {
                            File tempFile = new File("/Users/saishree/Desktop/tempFile.txt");
// /Users/satishrambhatla/NetBeansProjects/Jamify/src/newpackage/background.jpg
                            BufferedReader newBR = new BufferedReader(new FileReader(inputFile));
                            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
                            String lineToRemove = songName.getText();
                            String currentLine;

                            while((currentLine = newBR.readLine()) != null) {
                                // trim newline when comparing with lineToRemove
                                String match = currentLine.split(",")[1];
//                                String trimmedLine = currentLine.trim();
                                if(match.equals(lineToRemove)) continue;
                                bw.write(currentLine + System.getProperty("line.separator"));
                            }
                            bw.close();
                            br.close();
                            tempFile.renameTo(inputFile);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });

                /* on click of '+', menu pops up showing the play lists*/
                final JPopupMenu popup = new JPopupMenu();

                addLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        popup.removeAll();

                        /* Menu item : Queue is added to '+' and on click of queue that particular song is added to the queue and sent to the MP3 */
                        JMenuItem queue = new JMenuItem("Queue");
                        queue.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                songQueue.add(songPath);
                                setSongQ(songQueue);
                                System.out.println(songQueue.peek());
                            }
                        });
                        popup.add(queue);

                        /* add the menu item : playlist to '+'. */
                        for (int j =0; j< playlistNamePathsList.size(); j++){
                            JMenuItem playList = new JMenuItem(playlistNamePathsList.get(j).getPlayListName());
                            int finalJ = j;


                            playList.addActionListener(e12 -> {
                                try {
                                    try (FileWriter playListFile = new FileWriter(playlistNamePathsList.get(finalJ).getPlayListPath(),true)) {
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(finalI+1).append(",").append(songName.getText()).append(",").append(songPath);
                                        playListFile.write(sb.toString());
                                        playListFile.write("\n");
                                        playListFile.close();
                                    }
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            });
                            popup.add(playList);
                        }

                        popup.show(e.getComponent(), e.getX(), e.getY());
                    }
                });
            }

        }catch (FileNotFoundException e1) {
            System.out.println("File not fount: "+ inputFile.getName());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return this;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String path = " ";
        String songNameUserEntered =  JOptionPane.showInputDialog("Enter the Song name");
        JLabel newSongLabel = new JLabel(songNameUserEntered);
        JLabel deleteSongLabel = new JLabel("-");
        JLabel addLabel = new JLabel("+");

        deleteSongLabel.setForeground(Color.BLUE);
        addLabel.setForeground(Color.BLUE);

        eachSongPanel = new JPanel();
        eachSongPanel.setBounds(20, y,350,30);
        this.add(eachSongPanel);

        eachSongPanel.add(newSongLabel);
        eachSongPanel.add(deleteSongLabel);
        eachSongPanel.add(addLabel);
        Container parent = eachSongPanel.getParent();

        parent.validate();
        parent.repaint();

        JFileChooser chooser = new JFileChooser();

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            // Make sure the user didn't choose a directory.
            if (f != null) {
                //get the absolute path to selected file

                try {
                    path = f.getAbsolutePath();
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(allSongsFile,true));
                    StringBuilder sb = new StringBuilder();
                    sb.append(lastSongNumber+1).append(",").append(songNameUserEntered).append(",").append(path);
                    bufferedWriter.newLine();
                    bufferedWriter.write(sb.toString());
                    bufferedWriter.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }

        String finalPath = path;
        newSongLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //path to be passed to the Mp3
                System.out.println(finalPath);
            }
        });
    }
}
