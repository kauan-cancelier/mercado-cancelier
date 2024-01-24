package br.com.mercadocancelier.views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mercadocancelier.views.components.MenuBar;

@Component
public class TelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private JLabel hourLabel;

	@Autowired
	private MenuBar menuBar;

	public TelaPrincipal() {
		setTitle("Mercado Cancelier");

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1366, 768);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		hourLabel = new JLabel("Hora: ");
		hourLabel.setFont(new Font("Calibri Light", Font.PLAIN, 15));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(hourLabel, GroupLayout.PREFERRED_SIZE, 1356, GroupLayout.PREFERRED_SIZE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(703)
					.addComponent(hourLabel))
		);
		contentPane.setLayout(gl_contentPane);

		Timer timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateHourLabel();
				if (contentPane.isFocusable()) {					
					setJMenuBar(menuBar);
				}
			}
		});

		timer.start();
		setLocationRelativeTo(null);
	}


	private void updateHourLabel() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String formattedHour = now.format(formatter);
		hourLabel.setText("Hora: " + formattedHour);
	}
}
