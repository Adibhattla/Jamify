package jamify;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreatePlayListActionListener implements ActionListener {
	public String playListName;
	int y = 50;

	@Override
	public void actionPerformed(ActionEvent e) {

		playListName = JOptionPane.showInputDialog("Enter the Play List name");
		if (null != playListName && !playListName.trim().equals("")) {

			String playListPath = MusicPlayer.commonPath + playListName + ".csv";
			File playListFile = new File(playListPath);

			PlaylistNamePath playList1 = new PlaylistNamePath();
			playList1.setPlayListName(playListName);
			playList1.setPlayListPath(playListPath);
			AllSongs.playlistNamePathsList.add(playList1);
			JButton playlistButton = new JButton(playListName);
			playlistButton.setBounds(20, y, 100, 30);
			y = y + 30;
			MusicPlayer.playlist.add(playlistButton);
			MusicPlayer.playlist.revalidate();
			MusicPlayer.playlist.repaint();
			// my trial to only create panel without teh frame

			// ActionListener Action2 = (ActionEvent e1) -> {
			// try {
			// System.out.println("Hey you show me as apanel ");
			// AllSongs allSongs = new AllSongs();
			// JPanel jp = allSongs.displaySongsOnThePanel(new
			// File(playListPath), false);
			// javax.swing.GroupLayout allSongsLayout = new
			// javax.swing.GroupLayout(jp);
			// jp.setLayout(allSongsLayout);
			// allSongsLayout.setHorizontalGroup(allSongsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			// .addGap(0, 510, Short.MAX_VALUE)
			// );
			// allSongsLayout.setVerticalGroup(allSongsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0,
			// 420, Short.MAX_VALUE));
			// MusicPlayerUI.jp.add(jp);
			// } catch (Exception p){
			// System.out.println(p);
			// }
			// };
			// playlistButton.addActionListener(Action2);

			////////// this is a redundant code
			playlistButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// JFrame jFrame = new JFrame();
					AllSongs allSongs = new AllSongs();
					// AllSongs allSongs = new AllSongs();
					JPanel jp = allSongs.displaySongsOnThePanel(new File(playListPath), false);
					javax.swing.GroupLayout allSongsLayout = new javax.swing.GroupLayout(jp);
					jp.setLayout(allSongsLayout);
					allSongsLayout.setHorizontalGroup(
							allSongsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 510,
									Short.MAX_VALUE));
					allSongsLayout.setVerticalGroup(
							allSongsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 420,
									Short.MAX_VALUE));
					// JFrame frame3 = new JFrame();
					// frame3.setSize(500,500);
					// frame3.setLocation(300,200);
					// frame3.add(jp);
					// frame3.setVisible(true);
//					MusicPlayer.window.add(jp);
					MusicPlayer.window.getContentPane().add(jp);
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(playListFile));
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					/*
					 * On click of crete playlist button, I should be able to
					 * create another button and add it to the panel
					 * ????????????
					 */

				}
			});
		}
	}
}
