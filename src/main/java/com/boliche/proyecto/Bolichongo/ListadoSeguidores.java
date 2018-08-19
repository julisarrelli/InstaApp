package com.boliche.proyecto.Bolichongo;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JProgressBar;


@SuppressWarnings("serial")
public class ListadoSeguidores extends JFrame {

	private JPanel contentPane;
	private static Plataforma plataforma;
	public static  JList<String> list;
	public static  JLabel estado;
	private JRadioButton SeguirATodos,DejarDeSeguir,DejarDeSeguirNoTeSiguen;
	private JProgressBar progressBar;
	private  JButton submit;


	/**
	 * Create the frame.
	 * @param usuarioRobado 
	 * @param usuariosRobados 
	 */

	public ListadoSeguidores( final List<InstagramUserSummary> usuariosRobados, String usuarioRobado) {
		
		plataforma=Plataforma.getInstance();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSeguidoresDe = new JLabel("Seguidores de: "+ usuarioRobado);
		lblSeguidoresDe.setBounds(6, 6, 174, 16);
		contentPane.add(lblSeguidoresDe);
		
		JLabel lblCantidad = new JLabel(usuariosRobados.size()+" seguidores");
		lblCantidad.setBounds(323, 6, 121, 16);
		contentPane.add(lblCantidad);
		

		JScrollPane menuScrollPane;
		
		DefaultListModel<String> listado= new DefaultListModel<String>();
		
		for(InstagramUserSummary usuario:usuariosRobados){
			listado.addElement(usuario.getUsername()+" / "+ usuario.getFull_name());
		}
		list = new JList<>(listado);
		list.setVisibleRowCount(10);
		list.setBounds(25, 40, 335, 150);
		list.setSize(400,150);
		//contentPane.add(list);
		
		menuScrollPane = new JScrollPane(list);
		menuScrollPane.setBounds(25, 40, 335, 150);
		menuScrollPane.setSize(400,150);
		menuScrollPane.setVisible(true);
		contentPane.add(menuScrollPane, BorderLayout.CENTER);
		
		SeguirATodos = new JRadioButton("Seguir a todos");
		SeguirATodos.setBounds(303, 194, 141, 23);
		SeguirATodos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DejarDeSeguir.setSelected(false);
				DejarDeSeguirNoTeSiguen.setSelected(false);
				
			}
		});
		contentPane.add(SeguirATodos);
		
		DejarDeSeguir = new JRadioButton("Dejar de seguir a todos");
		DejarDeSeguir.setBounds(6, 187, 252, 38);
		DejarDeSeguir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SeguirATodos.setSelected(false);
				DejarDeSeguirNoTeSiguen.setSelected(false);
				
			}
		});
		contentPane.add(DejarDeSeguir);
		
		submit = new JButton("Realizar");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				progressBar.setVisible(true);
				submit.setVisible(false);
				SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {


					protected Boolean doInBackground() throws Exception {


						if(SeguirATodos.isSelected())plataforma.SeguirUsuarios(usuariosRobados);
					
						else if(DejarDeSeguir.isSelected())plataforma.DejarSeguirUsuarios(usuariosRobados);
							
						else if(DejarDeSeguirNoTeSiguen.isSelected())plataforma.DejarSeguirNoTeSiguen(usuariosRobados);
							
						return true;

					}
					
					protected void done()
					{
					progressBar.setVisible(false);
					submit.setVisible(false);
					}

				};
				worker.execute();
				
				
				
			}
		});
		submit.setBounds(308, 222, 117, 29);
		contentPane.add(submit);
		
		DejarDeSeguirNoTeSiguen = new JRadioButton("Dejar de seguir a quienes no te siguen");
		DejarDeSeguirNoTeSiguen.setBounds(6, 223, 290, 23);
		DejarDeSeguirNoTeSiguen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SeguirATodos.setSelected(false);
				DejarDeSeguir.setSelected(false);
				
			}
		});
		contentPane.add(DejarDeSeguirNoTeSiguen);
		
		estado = new JLabel("");
		estado.setBounds(39, 256, 311, 16);
		estado.setVisible(false);
		contentPane.add(estado);
		
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setBounds(298, 226, 146, 20);
		progressBar.setVisible(false);
		contentPane.add(progressBar);

	}
}
