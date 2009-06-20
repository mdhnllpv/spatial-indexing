package gui;

import file_parser.FileProcessor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import query.QueryProcessor;
import tokenizer.DocumentUnit;
import tokenizer.TokenizerImpl;

public class InteligentSearchApp extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton openButton;

	private JButton searchButton;

	private JButton nextButton;

	private JTextField query;

	private JFileChooser fileChooser;

	private JTextArea fileContentTextArea;

	private final TokenizerImpl tokenizer = new TokenizerImpl();

	private QueryProcessor queryProcessor = null;

	private String inputString;

	private int searchedIndex = 0;

	public InteligentSearchApp() {
		super(new BorderLayout());

		fileContentTextArea = new JTextArea(20, 100);

		query = new JTextField("Enter text ...");

		// create file chooser
		fileChooser = new JFileChooser();

		// create open button
		openButton = new JButton("Open...");
		searchButton = new JButton("Search");
		nextButton = new JButton("Next");

		openButton.addActionListener(this);
		searchButton.addActionListener(this);
		nextButton.addActionListener(this);

		// action panel
		JPanel upperPanel = new JPanel();
		upperPanel.add(openButton, BorderLayout.WEST);
		upperPanel.add(searchButton, BorderLayout.AFTER_LAST_LINE);
		upperPanel.add(query, BorderLayout.EAST);
		upperPanel.add(nextButton, BorderLayout.EAST);
		add(upperPanel, BorderLayout.NORTH);

		// panel with file content
		JPanel contentPanel = new JPanel();

		// add file content text area
		contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		contentPanel.add(new JScrollPane(fileContentTextArea));

		add(contentPanel);

	}

	private void highlightDocumentUnit(DocumentUnit documentUnit) {
		if (documentUnit != null) {
			Highlighter highlighter = fileContentTextArea.getHighlighter();
			highlighter.removeAllHighlights();
			try {
				highlighter.addHighlight(documentUnit.getSrart(), documentUnit
						.getEnd(), DefaultHighlighter.DefaultPainter);
				fileContentTextArea.setCaretPosition(documentUnit.getEnd());
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == openButton) {
			try {
				int retVal = fileChooser
						.showOpenDialog(InteligentSearchApp.this);

				if (retVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					inputString = FileProcessor.process(file.getAbsolutePath());
					fileContentTextArea.setText(inputString);
					tokenizer.tokenize(inputString);
					tokenizer.assignTfIdf();
					queryProcessor = new QueryProcessor(tokenizer);

				}
			} catch (IllegalArgumentException e1) {
				System.out.println(e1.getMessage());
			}
		} else if (e.getSource() == searchButton) {
			Set<String> queryBag = TokenizerImpl.stokanize(query.getText());
			queryProcessor.answer(queryBag);
			DocumentUnit documentUnit = tokenizer.getDocumentUnits().get(0);
			highlightDocumentUnit(documentUnit);
			searchedIndex = 0;
		} else if (e.getSource() == nextButton) {
			if (searchedIndex < tokenizer.getDocumentUnits().size()) {
				DocumentUnit documentUnit = tokenizer.getDocumentUnits().get(
						++searchedIndex);
				highlightDocumentUnit(documentUnit);
			}
		}
	}

	public static void createAndShowGui() {
		JFrame frame = new JFrame("Inteligent Search");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new InteligentSearchApp());
		frame.pack();
		frame.setVisible(true);
	}

}
