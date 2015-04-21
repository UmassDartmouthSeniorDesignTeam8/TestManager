import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import com.opencsv.CSVReader;

import net.miginfocom.swing.MigLayout;

import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Window.Type;
import java.awt.Dialog.ModalExclusionType;

public class SelectRosterFile extends JFrame {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	final JComboBox comboBox = new JComboBox();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SelectRosterFile dialog = new SelectRosterFile();
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JComboBox getComboBox() {
		return comboBox;
	}

	/**
	 * Create the dialog.
	 */
	public SelectRosterFile() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// make this line universal some how...
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				SelectRosterFile.class.getResource("/resources/orion_cat.png")));
		setBackground(Color.DARK_GRAY);
		getContentPane().setBackground(Color.DARK_GRAY);
		getContentPane().setForeground(Color.BLACK);
		setTitle("Upload Roster");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[81px][179px][89px][]",
				"[23px][20px][][][][]"));

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("ADD");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int idx = getComboBox().getSelectedIndex();

						File f = new File(textField.getText());

						try {
							CSVReader reader = new CSVReader(new FileReader(f));

							String[] nextLine = reader.readNext(); // skip
																	// header
																	// line

							while ((nextLine = reader.readNext()) != null) {
								String value = nextLine[idx];
								System.out.println("value is: >" + value + "<");

								Student student = new Student(value);
								/* Shawn fill in <co> */
								// addStudent(student);

							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}

			JLabel lblNewLabel = new JLabel("Select CSV File:");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabel.setForeground(Color.ORANGE);
			contentPanel.add(lblNewLabel, "cell 0 2,growx,aligny center");
			lblNewLabel.setLabelFor(textField);

			textField = new JTextField();
			contentPanel.add(textField, "cell 1 2,growx,aligny center");
			textField.setColumns(10);

			JButton btnNewButton = new JButton("Browse");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser chooser = new JFileChooser(textField.getText());
					chooser.addChoosableFileFilter(new FileFilter() {

						@Override
						public boolean accept(File arg0) {
							return arg0.isDirectory()
									|| arg0.getName().endsWith(".csv");
						}

						@Override
						public String getDescription() {
							return "(*.csv) Roster Files";
						}

					});
					if (chooser.showOpenDialog(contentPanel) == JFileChooser.APPROVE_OPTION) {
						File f = chooser.getSelectedFile();
						textField.setText(f.getPath());

						try {
							BufferedReader r = new BufferedReader(
									new FileReader(f));
							String header = r.readLine();
							StringTokenizer t = new StringTokenizer(header, ",");
							getComboBox().removeAllItems();
							while (t.hasMoreTokens()) {
								String columnName = t.nextToken();
								getComboBox().addItem(columnName);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
			contentPanel.add(btnNewButton, "cell 2 2,growx,aligny top");

			JLabel lblNewLabel_1 = new JLabel("Field to Save:");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNewLabel_1.setForeground(Color.ORANGE);
			contentPanel.add(lblNewLabel_1,
					"cell 0 5,alignx right,aligny center");
			lblNewLabel_1.setLabelFor(getComboBox());

			contentPanel.add(getComboBox(), "cell 1 5,growx,aligny top");
		}
	}

}
