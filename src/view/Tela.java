package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import utils.ComponentState;
import utils.GlobalKeyListener;

public class Tela extends JFrame {

	protected JFrame frame;
	protected JTextField keyArea;
	protected JTextArea msgScriptArea;
	protected JCheckBox checkBoxOn;
	protected JPanel contentPanel;
	protected JButton btnAdd;
	protected JButton btnDel;
	protected JButton btnSave;
	protected JLabel txtInfo;
	protected JLabel labelTeclas;

	protected ArrayList<JTextArea> scriptAreas = new ArrayList<>();
	protected ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
	protected ArrayList<JTextField> textFields = new ArrayList<>();
	protected int scriptAreaCount = 0;

	protected static final String SAVE_FILE = "bindSalvas.ser";

	public Tela() {
		super();
	}

	public void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("MENU DE BIND'S");
		frame.setForeground(new Color(0, 0, 0));
		frame.setBackground(new Color(0, 0, 0));
		frame.getContentPane().setBackground(SystemColor.controlHighlight);
		frame.setBounds(100, 100, 771, 503);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel txtInfo2 = new JLabel("Version alpha by Gs");
		txtInfo2.setFont(new Font("Yu Gothic UI", Font.BOLD, 13));
		txtInfo2.setBounds(10, 440, 589, 14);
		Color darkOrange = new Color(255, 140, 0);
		txtInfo2.setForeground(darkOrange);
		frame.add(txtInfo2);

		contentPanel = new JPanel();
		contentPanel.setBorder(UIManager.getBorder("DesktopIcon.border"));
		contentPanel.setBackground(SystemColor.control);
		contentPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(contentPanel);
		scrollPane.setBounds(10, 10, 735, 329);
		frame.getContentPane().add(scrollPane);

		JLabel txtInfo = new JLabel(
				"Dê um duplo clique nos campos de texto para poder digitar sua mensagem personalizada.");
		txtInfo.setFont(new Font("Yu Gothic UI", Font.BOLD, 13));
		txtInfo.setBounds(27, 17, 589, 14);
		txtInfo.setOpaque(true);
		txtInfo.setForeground(Color.WHITE);
		txtInfo.setBackground(Color.BLACK);
		contentPanel.add(txtInfo);

		JLabel txtInfo3 = new JLabel("A mensagem será exibida conforme a estrutura inserida.");
		txtInfo3.setFont(new Font("Yu Gothic UI", Font.BOLD, 13));
		txtInfo3.setBounds(27, 31, 589, 14);
		txtInfo3.setOpaque(true);
		txtInfo3.setForeground(Color.WHITE);
		txtInfo3.setBackground(Color.BLACK);
		contentPanel.add(txtInfo3);

		JLabel labelTeclas = new JLabel("TECLAS");
		labelTeclas.setFont(new Font("Yu Gothic UI", Font.BOLD, 13));
		labelTeclas.setBounds(670, 17, 53, 14);
		contentPanel.add(labelTeclas);

		JButton btnAdd = new JButton("[+] ADICIONAR");
		btnAdd.setForeground(new Color(0, 0, 255));
		btnAdd.setBackground(new Color(255, 255, 255));
		btnAdd.setFont(new Font("Arial", Font.BOLD, 13));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarScriptArea();
			}
		});
		btnAdd.setBounds(386, 365, 150, 23);
		frame.getContentPane().add(btnAdd);

		JButton btnDel = new JButton("[-] REMOVER INATIVOS");
		btnDel.setForeground(new Color(255, 0, 0));
		btnDel.setBackground(new Color(255, 255, 255));
		btnDel.setFont(new Font("Arial", Font.BOLD, 13));
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletarInativos();
			}
		});
		btnDel.setBounds(551, 365, 194, 23);
		frame.getContentPane().add(btnDel);

		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(0, 0, 0));
		separator.setBackground(SystemColor.controlText);
		separator.setBounds(0, 399, 763, 2);
		frame.getContentPane().add(separator);

		Icon iconBtnSave = UIManager.getIcon("FileView.floppyDriveIcon");
		JButton btnSave = new JButton("SALVAR", iconBtnSave);
		btnSave.setBackground(Color.WHITE);
		btnSave.setFont(new Font("Arial", Font.BOLD, 13));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarComponentState();
			}
		});
		btnSave.setBounds(627, 432, 122, 23);
		frame.getContentPane().add(btnSave);

		JLabel labelKeyboard = new JLabel("Teclas de atalho");
		labelKeyboard.setHorizontalAlignment(SwingConstants.CENTER);
		labelKeyboard.setBounds(614, 11, 95, 14);
		frame.getContentPane().add(labelKeyboard);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(new Color(0, 0, 0));
		separator_1.setBackground(SystemColor.controlText);
		separator_1.setBounds(0, 349, 763, 2);
		frame.getContentPane().add(separator_1);

		frame.setVisible(true);
		loadComponentState();
	}

	private void adicionarScriptArea() {
		int y = 85 + scriptAreaCount * 40;
		scriptAreaCount++;

		JTextArea novaScriptArea = new JTextArea();
		novaScriptArea.setBounds(115, y, 504, 30);
		novaScriptArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		contentPanel.add(novaScriptArea);
		scriptAreas.add(novaScriptArea);

		novaScriptArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);

				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
					JTextArea messageArea = new JTextArea(20, 45);
					messageArea.setLineWrap(true);
					messageArea.setWrapStyleWord(true);
					messageArea.setText(novaScriptArea.getText());

					JScrollPane scrollPane = new JScrollPane(messageArea);

					int option = JOptionPane.showOptionDialog(frame, scrollPane, "Digite a mensagem personalizada",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

					if (option == JOptionPane.OK_OPTION) {
						novaScriptArea.setText(messageArea.getText());
					}
				}
			}
		});

		JCheckBox novoCheckBoxOn = new JCheckBox("INATIVO");
		novoCheckBoxOn.setBackground(Color.RED);
		novoCheckBoxOn.setBounds(22, y, 87, 29);
		contentPanel.add(novoCheckBoxOn);
		checkBoxes.add(novoCheckBoxOn);

		novoCheckBoxOn.addItemListener(e -> {
			if (novoCheckBoxOn.isSelected()) {
				novoCheckBoxOn.setBackground(Color.GREEN);
				novoCheckBoxOn.setText("ATIVO");
			} else {
				novoCheckBoxOn.setBackground(Color.RED);
				novoCheckBoxOn.setText("INATIVO");
			}
		});

		JTextField novoSpaceKey = new JTextField();
		novoSpaceKey.setToolTipText("");
		novoSpaceKey.setForeground(new Color(0, 0, 0));
		novoSpaceKey.setFont(new Font("Arial", Font.BOLD, 12));
		novoSpaceKey.setBounds(670, y, 39, 30);
		contentPanel.add(novoSpaceKey);
		textFields.add(novoSpaceKey);

		novoSpaceKey.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String newKey = novoSpaceKey.getText().trim().toUpperCase();
				
				if (newKey.length() > 2) {
		            JOptionPane.showMessageDialog(frame,
		                    "Desse jeito não vai funfar pow :/");
		            novoSpaceKey.setText("");
		            novoSpaceKey.requestFocus();
		            return;
		        }

				if (!newKey.isEmpty()) {
					boolean keyExists = textFields.stream().anyMatch(
							existingKey -> existingKey != novoSpaceKey && existingKey.getText().trim().equals(newKey));

					if (keyExists) {
						JOptionPane.showMessageDialog(frame,
								"Esta tecla de atalho já está em uso. Por favor, escolha outra.");
						novoSpaceKey.setText("");
						novoSpaceKey.requestFocus();
					} else {
						GlobalKeyListener.registerHook(novoSpaceKey, novaScriptArea, novoCheckBoxOn);
					}
				}
			}
		});

//		novoSpaceKey.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				String newKey = novoSpaceKey.getText().trim();
//
//				if (!newKey.isEmpty()) {
//					boolean keyExists = textFields.stream().anyMatch(
//							existingKey -> existingKey != novoSpaceKey && existingKey.getText().trim().equals(newKey));
//
//					if (keyExists) {
//						JOptionPane.showMessageDialog(frame,
//								"Esta tecla de atalho já está em uso. Por favor, escolha outra :)");
//						novoSpaceKey.setText("");
//					} else {
//						GlobalKeyListener.registerHook(novoSpaceKey, novaScriptArea, novoCheckBoxOn);
//					}
//				}
//			}
//		});

		int preferredHeight = Math.max(contentPanel.getPreferredSize().height, y + 30 + 10);
		contentPanel.setPreferredSize(new java.awt.Dimension(735, preferredHeight));

		frame.revalidate();
		frame.repaint();
	}

	private void deletarInativos() {
		ArrayList<Integer> indicesParaRemover = new ArrayList<>();

		for (int i = 0; i < checkBoxes.size(); i++) {
			if (!checkBoxes.get(i).isSelected()) {
				indicesParaRemover.add(i);
			}
		}

		for (int i = indicesParaRemover.size() - 1; i >= 0; i--) {
			int index = indicesParaRemover.get(i);

			contentPanel.remove(scriptAreas.get(index));
			contentPanel.remove(checkBoxes.get(index));
			contentPanel.remove(textFields.get(index));

			scriptAreas.remove(index);
			checkBoxes.remove(index);
			textFields.remove(index);
		}

		for (int i = 0; i < scriptAreas.size(); i++) {
			int y = 85 + i * 40;
			scriptAreas.get(i).setBounds(115, y, 504, 30);
			checkBoxes.get(i).setBounds(22, y, 87, 29);
			textFields.get(i).setBounds(670, y, 39, 30);
		}

		scriptAreaCount = scriptAreas.size();

		int preferredHeight = Math.max(contentPanel.getPreferredSize().height, 85 + scriptAreaCount * 40);
		contentPanel.setPreferredSize(new java.awt.Dimension(735, preferredHeight));

		frame.revalidate();
		frame.repaint();
	}

	private void salvarComponentState() {
		try {
			FileOutputStream fileOut = new FileOutputStream(SAVE_FILE);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

			ArrayList<ComponentState> componentStates = new ArrayList<>();

			for (int i = 0; i < scriptAreas.size(); i++) {
				JTextArea area = scriptAreas.get(i);
				JCheckBox checkBox = checkBoxes.get(i);
				JTextField textField = textFields.get(i);

				GlobalKeyListener.registerHook(textField, area, checkBox);

				ComponentState state = new ComponentState(area.getText(), checkBox.isSelected(), textField.getText());
				componentStates.add(state);
			}

			objectOut.writeObject(componentStates);
			objectOut.close();
			fileOut.close();
			JOptionPane.showMessageDialog(frame, "Script's salvos com sucesso, pode fechar normalmente!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void loadComponentState() {
		try {
			FileInputStream fileIn = new FileInputStream(SAVE_FILE);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);

			ArrayList<ComponentState> componentStates = (ArrayList<ComponentState>) objectIn.readObject();

			for (ComponentState state : componentStates) {

				adicionarScriptArea();
				JTextArea area = scriptAreas.get(scriptAreas.size() - 1);
				JCheckBox checkBox = checkBoxes.get(checkBoxes.size() - 1);
				JTextField textField = textFields.get(textFields.size() - 1);

				area.setText(state.getScriptAreaText());
				checkBox.setSelected(state.isCheckBoxSelected());
				textField.setText(state.getTextFieldText());

				if (!textField.getText().trim().isEmpty()) {
					GlobalKeyListener.registerHook(textField, area, checkBox);
				}
			}

			objectIn.close();
			fileIn.close();

			JOptionPane.showMessageDialog(frame, "Estado dos componentes carregado com sucesso.");

		} catch (IOException | ClassNotFoundException ex) {

			JOptionPane.showMessageDialog(frame,
					"Nenhum estado anterior encontrado. Iniciando com componentes padrão.");
		}
	}

}
