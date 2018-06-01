package jamify_2;

/**
 * Created by SuryaRajasekaran on 5/10/18.
 */

import javafx.embed.swing.JFXPanel;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.Queue;
import java.util.Stack;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * Created by SuryaRajasekaran on 5/21/18.
 */
public class AudioPlay extends AllSongs {

    private boolean isPlaying = false;
    private boolean isPaused = false;
    private boolean isDone = false;

//    JFrame JamifyFrame = new JFrame();
//    JPanel JamifyPanel = new JPanel();
//    JButton JamifyPlayButton = new JButton("Play Song");
//    JButton JamifyStopButton = new JButton("Stop Song");
//    JButton JamifyPreviousButton = new JButton("Previous Song");
//    JButton JamifyNextButton = new JButton("Next Song");
//    JButton JamifyRewindButton = new JButton("Rewind Song");
//    JButton JamifyForwardButton = new JButton("Forward Song");
//    JButton JamifyRepeatButton = new JButton("Repeat Song");
//    JButton JamifyShuffleButton = new JButton("Shuffle");
//    JSlider timeSlider = new JSlider(0, 0, 1000, 0); //max value is the slider value
//    JSlider JamifySlider = new JSlider(0, 5, 100, 90);
    private MediaPlayer mediaPlayer;

    public AudioPlay() {  //dummy?
        super();
        AllSongs Q = new AllSongs();
    }

    public void OneSong(String path) throws Exception {
        final JFXPanel fxPanel = new JFXPanel(); //needed
//
//        JamifyFrame.add(JamifyPanel);
//        JamifyPanel.add(JamifyPlayButton);
//        JamifyFrame.setPreferredSize(new Dimension(500, 500));
//        JamifyPanel.add(JamifyPlayButton, BorderLayout.NORTH);
//        JamifyPanel.add(JamifyStopButton, BorderLayout.SOUTH);
//        JamifyPanel.add(JamifyPreviousButton, BorderLayout.NORTH);
//        JamifyPanel.add(JamifyNextButton, BorderLayout.SOUTH);
//        JamifyPanel.add(JamifyRewindButton, BorderLayout.NORTH);
//        JamifyPanel.add(JamifyForwardButton, BorderLayout.SOUTH);
//        JamifyPanel.add(JamifyRepeatButton, BorderLayout.NORTH);
//        JamifyPanel.add(JamifyShuffleButton, BorderLayout.SOUTH);
//        JamifyFrame.pack();
//        JamifyFrame.setVisible(true);
//        JamifyFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

       String bip = path;
        Media hit = new Media(new File(bip).toURI().toString());
      
        mediaPlayer = new MediaPlayer(hit);

    

//        timeSlider.addMouseMotionListener(new MouseMotionAdapter() {
//            boolean isDragging;
//            @Override
//            public void mouseDragged(MouseEvent arg0) {
//                isDragging = true;
//                try {
//                    
//                    int dv = timeSlider.getValue() * 500; //moves by 500 ms??
//                   
//                    timeSlider.setValue(dv);
//                    Duration draggedVal = new Duration(dv);
//                   
//                    mediaPlayer.seek(draggedVal);
//                } catch (Exception e3) {
//                } finally {
//                    isDragging = false;
//                }
//            }
//        });
//        JamifyPanel.add(timeSlider, BorderLayout.SOUTH);
    

//    jButton1 is the play button
        MusicPlayerUI.play.addActionListener(new ActionListener() {
//        JamifyPlayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


               JamifyPlay(getSongQ());


            }

        });

// jButton4 is the forward button 
        MusicPlayerUI.forward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.seek(mediaPlayer.getCurrentTime().multiply(1.5));
            }
        });
// jButton5 is the rewind button
        MusicPlayerUI.rewind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.seek(mediaPlayer.getCurrentTime().divide(1.5));
            }
           
        });
// jButton8 is previous
        MusicPlayerUI.previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Stack<String> tempStackPrev = getsongStack();
                System.out.println("Stack in previous before "+ tempStackPrev);
                String te= (String) tempStackPrev.pop();
                setsongStack(tempStackPrev);
                System.out.println("Stack after previous stack pop "+ tempStackPrev);
                try {
                    AudioPlay previous = new AudioPlay();
                    previous.OneSong(te);
                    //nex.mediaPlayer.play();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
// jbutton9 is next
        MusicPlayerUI.next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                Queue<String> temp = getSongQ();
              
                String t = temp.poll();
                Stack<String> tempnextstack = getsongStack();
                tempnextstack.push(t);
                setsongStack(tempnextstack);
                System.out.println("stack after push");
                System.out.println(getsongStack());


                try {
                    AudioPlay nex = new AudioPlay();
                   nex.OneSong(t);
             
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
          
                System.out.println("Q after poll");
                System.out.println(temp);

            
            }
        });
// jButton10 is the repeatbutton 
        MusicPlayerUI.repeat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.stop();
                mediaPlayer.play();
                isPlaying = !isPlaying;  //??needed?
//                NewJFrame.jButton1.setText("Pause Song"); //??needed?/

                MusicPlayerUI.pause.setText("Pause Song");
            }
        });
    }
//
//
    public void JamifyPlay(Queue SongQ) {

        Duration JamifyDuration; //= new javafx.util.Duration();
        JamifyDuration = mediaPlayer.getTotalDuration();
        double mintues = JamifyDuration.toMinutes();
        System.out.println(JamifyDuration);
        System.out.println(mintues);

       mediaPlayer.stop();
// This is the toggle portion u might want to change
        if (isPlaying) {
            mediaPlayer.pause();
            isPlaying = !isPlaying;
//           NewJFrame.jButton1.setText("Resume Song"); // no resume button???????

        } else {
            mediaPlayer.play();
            isPlaying = !isPlaying;
//           NewJFrame.jButton1.setText("Pause Song");
           MusicPlayerUI.pause.setText("Pause Song");
        }
//        jButton2 is the Stop button
        if (isPlaying) {
        	MusicPlayerUI.stop.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mediaPlayer.stop();
//                   NewJFrame.jButton1.setText("Play Song");
                    MusicPlayerUI.play.setText("Play Song");
                    isPlaying = !isPlaying;
                }
            });
        }

        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                System.out.println("print yay");
                mediaPlayer.play();
            }
        });

        while(mediaPlayer.getCurrentTime()==mediaPlayer.getTotalDuration()) {
            isDone = !isDone;
        }

System.out.println(isDone);

        if(isDone) {
            
            System.out.println("song time now ");
            JamifyPlay(getSongQ());
        }

        }
    }




 