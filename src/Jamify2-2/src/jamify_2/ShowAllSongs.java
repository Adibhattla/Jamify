/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jamify_2;
import java.awt.event.*;
import java.io.File;
import javax.swing.JPanel;
/**
 *
 * @author satishrambhatla
 */
public class ShowAllSongs implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        AllSongs allSongs =new AllSongs();
        JPanel allSongsPanel = allSongs.displaySongsOnThePanel(new File("/Users/saishree/Desktop/Allsongs.csv"), true);
               javax.swing.GroupLayout allSongsLayout = new javax.swing.GroupLayout(allSongsPanel);
    allSongsPanel.setLayout(allSongsLayout);
    allSongsLayout.setHorizontalGroup(allSongsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING) .addGap(0, 510, Short.MAX_VALUE)
    );
    allSongsLayout.setVerticalGroup(allSongsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 420, Short.MAX_VALUE)
    );

//    NewJFrame.getContentPane().add(allSongsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 510, 420));
    }
    
}
