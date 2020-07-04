package com.qst.dms.ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.qst.dms.entity.User;
import com.qst.dms.service.UserService;

//ע�ᴰ��
public class RegistFrame extends JFrame {
	// �����
	private JPanel p;
	// ��ǩ
	private JLabel lblName, lblPwd, lblRePwd, lblSex, lblHobby, lblAdress,
			lblDegree;
	// �û������ı���
	private JTextField txtName;
	// �����ȷ�����룬�����
	private JPasswordField txtPwd, txtRePwd;
	// �Ա𣬵�ѡ��ť
	private JRadioButton rbMale, rbFemale;
	// ���ã���ѡ��
	private JCheckBox ckbRead, ckbNet, ckbSwim, ckbTour;
	// ��ַ���ı���
	private JTextArea txtAdress;
	// ѧ������Ͽ�
	private JComboBox<String> cmbDegree;
	// ȷ�Ϻ�ȡ������ť
	private JButton btnOk, btnCancle;
	// ע����û�
	private static User user;

	// �û�ҵ����
	private UserService userService;

	// ���췽��
	public RegistFrame() {
		super("�û�ע��");

		// ʵ�����û�ҵ�������
		userService = new UserService();
		
		// ���ô����icon
		ImageIcon icon = new ImageIcon("images\\dms.png");
		this.setIconImage(icon.getImage());

		// ������岼�֣����񲼾�
		p = new JPanel(new GridLayout(8, 1));
		// ʵ�������
		lblName = new JLabel("��  ��  ����");
		lblPwd = new JLabel("��        �룺");
		lblRePwd = new JLabel("ȷ�����룺");
		lblSex = new JLabel("��       ��");
		lblHobby = new JLabel("��        �ã�");
		lblAdress = new JLabel("��        ַ��");
		lblDegree = new JLabel("ѧ        ����");
		txtName = new JTextField(16);
		txtPwd = new JPasswordField(16);
		txtRePwd = new JPasswordField(16);
		rbMale = new JRadioButton("��");
		rbFemale = new JRadioButton("Ů");

		// �Ա�ĵ�ѡ�߼�
		ButtonGroup bg = new ButtonGroup();
		bg.add(rbMale);
		bg.add(rbFemale);

		ckbRead = new JCheckBox("�Ķ�");
		ckbNet = new JCheckBox("����");
		ckbSwim = new JCheckBox("��Ӿ");
		ckbTour = new JCheckBox("����");
		txtAdress = new JTextArea(3, 20);

		// ��Ͽ���ʾ��ѧ������
		String str[] = { "Сѧ", "����", "����", "����", "˶ʿ", "��ʿ" };
		cmbDegree = new JComboBox<String>(str);
		// ������Ͽ�ɱ༭
		cmbDegree.setEditable(true);
		btnOk = new JButton("ȷ��");
		// ע�������������ȷ����ť
		btnOk.addActionListener(new RegisterListener());
		btnCancle = new JButton("����");
		// ע����������������ð�ť
		btnCancle.addActionListener(new ResetListener());
		// ��������������壬Ȼ��С�����������
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p1.add(lblName);
		p1.add(txtName);
		p.add(p1);
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p2.add(lblPwd);
		p2.add(txtPwd);
		p.add(p2);
		JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p3.add(lblRePwd);
		p3.add(txtRePwd);
		p.add(p3);
		JPanel p4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p4.add(lblSex);
		p4.add(rbMale);
		p4.add(rbFemale);
		p.add(p4);
		JPanel p5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p5.add(lblHobby);
		p5.add(ckbRead);
		p5.add(ckbNet);
		p5.add(ckbSwim);
		p5.add(ckbTour);
		p.add(p5);
		JPanel p6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p6.add(lblAdress);
		p6.add(txtAdress);
		p.add(p6);
		JPanel p7 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p7.add(lblDegree);
		p7.add(cmbDegree);
		p.add(p7);
		JPanel p8 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p8.add(btnOk);
		p8.add(btnCancle);
		p.add(p8);
		// �������봰����
		this.add(p);
		// ���ô����С��λ��
		this.setSize(310, 350);
		this.setLocation(300, 300);
		// ���ô��岻�ɸı��С
		this.setResizable(false);

		// ���ô����ʼ�ɼ�
		this.setVisible(true);
	}

	// �����࣬������ȷ�ϰ�ť��ҵ���߼�
	private class RegisterListener implements ActionListener {
		// ��дactionPerFormed()�������¼�������
		public void actionPerformed(ActionEvent e) {
			// ��ȡ�û����������
			String userName = txtName.getText().trim();
			String password = new String(txtPwd.getPassword());
			String rePassword = new String(txtRePwd.getPassword());
			// ���Ա��С���Ů����Ӧת��Ϊ��1����0��
			int sex = Integer.parseInt(rbFemale.isSelected() ? "0" : "1");
			String hobby = (ckbRead.isSelected() ? "�Ķ�" : "")
					+ (ckbNet.isSelected() ? "����" : "")
					+ (ckbSwim.isSelected() ? "��Ӿ" : "")
					+ (ckbTour.isSelected() ? "����" : "");
			String address = txtAdress.getText().trim();
			String degree = cmbDegree.getSelectedItem().toString().trim();
			// �ж��������������Ƿ�һ��
			if (password.equals(rePassword)) {
				// �����ݷ�װ��������
				user = new User(userName, password, sex, hobby, address, degree);
				// ��������
				if (userService.saveUser(user)) {
					// �����ʾ��Ϣ
					//System.out.println("ע��ɹ���");
					JOptionPane.showMessageDialog(null,"ע��ɹ���","�ɹ���ʾ",JOptionPane.PLAIN_MESSAGE);
					RegistFrame.this.setVisible(false);
			
				} else {
					// �����ʾ��Ϣ
					//System.out.println("ע��ʧ�ܣ�");
					JOptionPane.showMessageDialog(null,"ע��ʧ�ܣ�","������ʾ",JOptionPane.ERROR_MESSAGE);
				}
			} else {
				// �����ʾ��Ϣ
				//System.out.println("������������벻һ�£�");
				JOptionPane.showMessageDialog(null,"������������벻һ�£�","������ʾ",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// �����࣬���������ð�ť
	public class ResetListener implements ActionListener {
		// ��дactionPerFormed()������������������¼�������
		public void actionPerformed(ActionEvent e) {
			// ������������롢ȷ����������
			txtName.setText("");
			txtPwd.setText("");
			txtRePwd.setText("");
			// ���õ�ѡ��ťΪδѡ��
			rbMale.setSelected(false);
			rbFemale.setSelected(false);
			// �������еĸ�ѡ��ťΪδѡ��
			ckbRead.setSelected(false);
			ckbNet.setSelected(false);
			ckbSwim.setSelected(false);
			ckbTour.setSelected(false);
			// ��յ�ַ��
			txtAdress.setText("");
			// ������Ͽ�Ϊδѡ��״̬
			cmbDegree.setSelectedIndex(0);
		}
	}

	/*public static void main(String[] args) {
		new RegistFrame();
	}*/
}

