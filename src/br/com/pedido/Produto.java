package br.com.pedido;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Produto extends JFrame {
	private JTextField txtID;
	private JTextField txtDtCadastro;
	private JTextField txtDescricao;
	private JTable tabProduto;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Produto frame = new Produto();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Produto() {
		setTitle("PRODUTO");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 653, 338);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(10, 11, 23, 14);
		getContentPane().add(lblNewLabel);
		
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(30, 8, 55, 20);
		getContentPane().add(txtID);
		txtID.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Data Cadastro");
		lblNewLabel_1.setBounds(89, 11, 86, 14);
		getContentPane().add(lblNewLabel_1);
		
		txtDtCadastro = new JTextField();
		txtDtCadastro.setEditable(false);
		txtDtCadastro.setBounds(173, 8, 150, 20);
		getContentPane().add(txtDtCadastro);
		txtDtCadastro.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Deescri\u00E7\u00E3o");
		lblNewLabel_2.setBounds(10, 50, 71, 14);
		getContentPane().add(lblNewLabel_2);
		
		txtDescricao = new JTextField();
		txtDescricao.setEditable(false);
		txtDescricao.setBounds(80, 47, 182, 20);
		getContentPane().add(txtDescricao);
		txtDescricao.setColumns(10);
		
		JButton btnNewButton = new JButton("Novo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtDescricao.setEditable(true);
				txtDescricao.setText(null);
				txtID.setText(null);
				txtDtCadastro.setText(null);
				txtDescricao.requestFocus();
				
				
			}
		});
		btnNewButton.setBounds(272, 46, 80, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Gravar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
						try {
							if(txtID.getText().equals("")) {
							Cadastrar();
						}else {
							Editar();
						}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			}
		});
		btnNewButton_1.setBounds(358, 46, 80, 23);
		getContentPane().add(btnNewButton_1);
		
		JButton btnexcluir = new JButton("Excluir");
		btnexcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Excluir();
					JOptionPane.showMessageDialog(null,"Cadastro excluido");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			
			}
		});
		btnexcluir.setBounds(445, 46, 80, 23);
		getContentPane().add(btnexcluir);
		
		tabProduto = new JTable();
		
		
		tabProduto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				linhaSelecionada();
			}
		});
		tabProduto.setBounds(10, 99, 602, 192);
		getContentPane().add(tabProduto);
		
		JButton btnNewButton_2 = new JButton("Atualizar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtDescricao.setEditable(true);
				txtDescricao.requestFocus();
			}
		});
		btnNewButton_2.setBounds(535, 47, 94, 23);
		getContentPane().add(btnNewButton_2);
		
		try
		{
		    listarProduto();
		}
		catch(Exception ex)
		{
			double x=0;
	     }
		}
	
	private void linhaSelecionada()
	{	desabilitarText();
		DefaultTableModel tableModel = (DefaultTableModel) tabProduto.getModel();
		int row = tabProduto.getSelectedRow();
		if (tableModel.getValueAt(row, 0).toString()!="ID")
		{  txtID.setText(tableModel.getValueAt(row, 0).toString());
		   txtDescricao.setText(tableModel.getValueAt(row, 1).toString());
		   txtDtCadastro.setText(tableModel.getValueAt(row, 2).toString());
		}
	}
	
	private void desabilitarText()
	{
		txtDescricao.setEditable(false);
		txtID.setEditable(false);
		txtDtCadastro.setEditable(false);
		
	}
	
	private void listarProduto() throws SQLException 
    {  	Connection con=null;
		ConexaoBanco objconexao=new ConexaoBanco();
		//try
		//{   
			con=objconexao.conectar();
		//}
			if(con ==null)
			{  	JOptionPane.showMessageDialog(null,"conexão não realizada");
		    }
		    else
		    {   Statement stmt = con.createStatement();
		        ResultSet rs = stmt.executeQuery("SELECT * FROM db_pedido.produto");
				    	       
		    	String[] colunasTabela= new String[]{ "ID", "Descrição", "Pontuação" };  
		    	DefaultTableModel modeloTabela = new DefaultTableModel(null,colunasTabela);
		    	modeloTabela.addRow(new String[] {"ID", "DESCRIÇÃO", "CADASTRO"}); 
		    	if(rs != null) {
		    	    while(rs.next()) {
		    	        modeloTabela.addRow(new String[] {  
		    	                String.valueOf(rs.getInt("ID")),  
		    	                rs.getString("descricao"),  
		    	                rs.getString("data_cadastro")
		    	            }); 
		    	    }
		    	}
		    	tabProduto.setModel(modeloTabela);
		    	
		    
		    }
      //  }
		/*catch(Exception ex)
		{
			con.close();
			
		}*/
	}
	
	private void Cadastrar() throws SQLException 
	{
			Connection con=null;
			ConexaoBanco objconexao=new ConexaoBanco();
	
			try
			{
			con=objconexao.conectar();
			if(con ==null)
			{  
			JOptionPane.showMessageDialog(null,"conexão não realizada");
		
			}else {
				Statement stmt = con.createStatement();
		        String query="insert into db_pedido.produto(descricao) values('"+txtDescricao.getText()+"')";
		        stmt.executeUpdate(query);
		        listarProduto();
		        txtDescricao.setText(null);
		        desabilitarText();	
		    }
        }
		catch(Exception ex)
		{
			con.close();
			JOptionPane.showMessageDialog(null,"Não foi possível fazer o novo cadastro. "+ex.getMessage());
			
		}	
		}
	
		private void Editar()throws SQLException{
			
			Connection con=null;
			ConexaoBanco objconexao=new ConexaoBanco();
			PreparedStatement stmt = null;
			
			try
			{
			con=objconexao.conectar();
			if(con ==null)
			{  
			JOptionPane.showMessageDialog(null,"conexão não realizada");
			}else 
			{
		
				stmt = con.prepareStatement("update db_pedido.produto set descricao=? where id=?");
				stmt.setString(1, txtDescricao.getText());
				stmt.setInt(2, Integer.parseInt(txtID.getText()));
				stmt.executeUpdate();
				listarProduto();
				txtDescricao.setText(null);
				desabilitarText();
	
			}
			}
			catch(Exception ex)
			{
				con.close();
				JOptionPane.showMessageDialog(null,"Não foi possível fazer alterar o cadastro."+ex.getMessage());
				
			}	
			}
	
	private void Excluir()throws SQLException{ 
	
		Connection con=null;
		ConexaoBanco objconexao=new ConexaoBanco();
		PreparedStatement stmt = null;
		
		try
		{
		con=objconexao.conectar();
		if(con ==null)
		{  
		JOptionPane.showMessageDialog(null,"conexão não realizada");
		}else 
		{
	
			stmt = con.prepareStatement("delete from db_pedido.produto where id=?");
			stmt.setInt(1, Integer.parseInt(txtID.getText()));
			stmt.executeUpdate();
			listarProduto();
			txtDescricao.setText(null);
			desabilitarText();

		}
		}
		catch(Exception ex)
		{
			con.close();
			JOptionPane.showMessageDialog(null,"Não foi possível fazer excluir o cadastro. "+ex.getMessage());
			
			
		}
		
	}
		}
	
