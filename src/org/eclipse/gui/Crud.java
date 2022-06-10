package org.eclipse.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.eclipse.classes.Salle;
import org.eclipse.gui.components.RoundButton;
import org.eclipse.gui.components.RoundTextField;
import org.eclipse.services.SalleService;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

public class Crud extends JFrame {

	private SalleService salleService;

	private List<Salle> salles = new ArrayList<Salle>();

	private JPanel contentPane;
	private JTable tbSalles;
	private JTextField txtCode;
	private JTextField txtLibelle;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SalleService salleService = new SalleService();
					// On a injecte la dependance d'une nouvelle instance SalleService dans Crud par
					// son constructeur
					Crud frame = new Crud(salleService);
					// Rendre la fenêtre visible
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// les objects de la classe C1 dependent des objets de la classe C2 quand :
	// -> C1 a un attribut objet de la classe C2
	// -> C1 herite de C2
	// -> Une methode de C1, ici Crud, appelle une methode de C2, ici SalleService
	// Et pour ca on a utilise l'injection de dependance
	public Crud(SalleService salleService) {
		this.salleService = salleService;
		initFrame();
		fetch();
	}

	private void initFrame() {
		setTitle("Crud");
		setDefaultLookAndFeelDecorated(true);
		// La fenêtre doit se fermer quand on clique sur la croix rouge
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Definit la position et la taille setBounds(int x, int y, int width, int
		// height)
		setBounds(100, 100, 865, 501);
		// Creation du container Jpanel qui va comprendre un layout et des composants
		contentPane = new JPanel();
		contentPane.setBackground((new Color(41, 197, 255)));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tbSalles = new JTable();
		tbSalles.setBounds(322, 73, 501, 286);

		tbSalles.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Id", "Code", "Libelle" }));

		tbSalles.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				getValues(evt);
			}
		});

		JScrollPane scrollPane = new JScrollPane(tbSalles);
		scrollPane.setBounds(366, 67, 447, 290);
		contentPane.add(scrollPane);

		JLabel lblListeDesSalles = new JLabel("Liste des Salles :");
		lblListeDesSalles.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblListeDesSalles.setForeground(Color.WHITE);
		lblListeDesSalles.setBounds(366, 26, 177, 28);
		contentPane.add(lblListeDesSalles);

		JButton btnFetch = new RoundButton("Fetch");
		// Swing utilise une architecture par evenements
		// Un composant de notre interface graphique peut provoquer un evenement
		// Cet evenement est ensuite transmis a des objets que l on appelle des
		// listeners
		// ActionListener reagit aux clics de l'utilisateur sur JButton
		// actionPerformed() sera la fonction qui sera appele lorsqu un evenement se
		// produit sur le bouton

		btnFetch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fetch();
			}
		});

		btnFetch.setBounds(712, 29, 97, 25);
		contentPane.add(btnFetch);

		JLabel lblNouvelleSalle = new JLabel("Nouvelle Salle :");
		lblNouvelleSalle.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNouvelleSalle.setForeground(Color.WHITE);
		lblNouvelleSalle.setBounds(22, 126, 134, 16);
		contentPane.add(lblNouvelleSalle);

		JLabel lblCode = new JLabel("Code :");
		lblCode.setForeground(Color.WHITE);
		lblCode.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblCode.setBounds(22, 182, 56, 16);
		contentPane.add(lblCode);

		JLabel lblLibelle = new JLabel("Libelle :");
		lblLibelle.setForeground(Color.WHITE);
		lblLibelle.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblLibelle.setBounds(23, 248, 91, 16);
		contentPane.add(lblLibelle);

		txtCode = new RoundTextField(15);
		txtCode.setColumns(10);
		txtCode.setBounds(116, 177, 144, 28);
		contentPane.add(txtCode);

		txtLibelle = new RoundTextField(15);
		txtLibelle.setColumns(10);
		txtLibelle.setBounds(116, 236, 144, 28);
		contentPane.add(txtLibelle);

		JButton btnNewButton = new RoundButton("Ajouter");

//		btnNewButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				add(e);
//			}
//		});

		// Avec une expression lambda
		// btnNewButton.addActionListener((e) -> add(e));

		// Avec une reference de methode
		// this cible l'objet courant
		btnNewButton.addActionListener(this::add);

		btnNewButton.setBounds(27, 324, 97, 25);
		contentPane.add(btnNewButton);

		JButton btnSupprimer = new RoundButton("Supprimer");
//		btnSupprimer.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				delete(e);
//			}
//		});

		// btnSupprimer.addActionListener((e) -> delete(e));

		btnSupprimer.addActionListener(this::delete);

		btnSupprimer.setBounds(142, 324, 97, 25);
		contentPane.add(btnSupprimer);

		JButton btnEdit = new RoundButton("Edit");

//		btnEdit.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				edit(e);
//			}
//		});

		// btnEdit.addActionListener((e) -> edit(e));

		btnEdit.addActionListener(this::edit);

		btnEdit.setBounds(251, 324, 97, 25);
		contentPane.add(btnEdit);

		// Attribue une taille minimale a notre fenetre (jute assez pour voir tous les
		// composants)
		// pack();
	}

	// Affiche la liste
	private void fetch() {
		DefaultTableModel model = (DefaultTableModel) tbSalles.getModel();
		model.setRowCount(0);
		salles = salleService.findAll();
		// DefaultTableModel est le modèle de la table qui est utilisée par un JTable
		for (Salle s : salles) {
			Object[] row = new Object[3];
			row[0] = s.getId();
			row[1] = s.getCode();
			row[2] = s.getLibelle();
			model.addRow(row);
		}
	}

	// Met les champs du formulaire a vide
	private void clear() {
		txtCode.setText("");
		txtLibelle.setText("");
	}

	// Affiche la ligne selectionne ds le formulaire
	private void getValues(MouseEvent evt) {
		int i = tbSalles.getSelectedRow();
		TableModel model = tbSalles.getModel();
		txtCode.setText(model.getValueAt(i, 1).toString());
		txtLibelle.setText(model.getValueAt(i, 2).toString());
	}

	// Exercice

	// Creer un formulaire qui permet d'ajouter un element a la liste :

	// TextField id
	// TextField code
	// TextField libelle
	// JButton Ajouter

	private void add(ActionEvent evt) {
		int option = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to Add?", "Add confirmation",
				JOptionPane.YES_NO_OPTION);
		if (option == 0) {
			// trim() : eneleve les espaces au debut et a la fin
			String code = txtCode.getText().trim();
			String libelle = txtLibelle.getText().trim();
			Salle s = new Salle(code, libelle);
			salleService.create(s);
			fetch();
			clear();
		}
	}

	// Supprime un element de la liste
	private void delete(ActionEvent evt) {
		// Recupere la row selectionne
		int i = tbSalles.getSelectedRow();
		if (i >= 0) {
			// confirmation
			int option = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to Delete ?",
					"Add confirmation", JOptionPane.YES_NO_OPTION);
			if (option == 0) {
				TableModel model = tbSalles.getModel();
				String id = model.getValueAt(i, 0).toString();
				if (tbSalles.getSelectedRows().length == 1) {
					Salle s = salleService.findById(Integer.parseInt(id));
					salleService.delete(s);
					fetch();
				}
			}
		} else {
			JOptionPane.showMessageDialog(rootPane, "Please, select a row !");
		}
	}

	// Met a jour un element de la liste
	private void edit(java.awt.event.ActionEvent evt) {
		int i = tbSalles.getSelectedRow();
		if (i >= 0) {
			int option = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to Edit ?", "Add confirmation",
					JOptionPane.YES_NO_OPTION);
			if (option == 0) {
				String code = txtCode.getText().trim();
				String libelle = txtLibelle.getText().trim();
				TableModel model = tbSalles.getModel();
				String id = model.getValueAt(i, 0).toString();
				if (tbSalles.getSelectedRows().length == 1) {
					Salle s = salleService.findById(Integer.parseInt(id));
					s.setCode(code);
					s.setLibelle(libelle);
					salleService.update(s);
					fetch();
					clear();
				}
			}
		} else {
			JOptionPane.showMessageDialog(rootPane, "Please, select a row !");
		}
	}
}
