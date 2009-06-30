package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import query.QueryProcessor;
import engine.DocumentUnit;
import engine.SearchEngine;
import file_parser.FileProcessor;

public class InteligentSearchApp extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Buttons
	 */
	private JButton openButton;
	private JButton searchButton;
	private JButton nextSearchResult;
	private JButton nextRelevantParagraph;

	/**
	 * Search query
	 */
	private JTextField query;

	/**
	 * File chooser
	 */
	private JFileChooser fileChooser;

	/**
	 * Text area
	 */
	private JTextArea fileContentTextArea;
	private JList navigationList;

	/**
	 * Combo boxs
	 */
	private JComboBox fontCombo;
	private JComboBox sizeCombo;

	private SearchEngine tokenizer = null;
	private DocumentUnit lastSearchedUnit = null;
	private QueryProcessor queryProcessor = null;
	private int searchedIndex = 0;
	private boolean isFirstTimeQuery = true;

	public InteligentSearchApp() {
		super(new BorderLayout());

		fileContentTextArea = new JTextArea(20, 80);
		fileContentTextArea.setEditable(false);
		fileContentTextArea.setLineWrap(true);
		navigationList = new JList();
		navigationList.setSize(20, 20);
		navigationList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				NavigationItem selectedParagraph = (NavigationItem) navigationList
						.getSelectedValue();
				fileContentTextArea.setCaretPosition(selectedParagraph
						.getDocumentUnit().getSrart());
				HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.LIGHT_GRAY);
				highlightDocumentUnit(selectedParagraph.getDocumentUnit(),painter);
			}

		});

		query = new JTextField("Enter search text ...");
		query.addFocusListener(new FocusListenerImpl());

		// create file chooser
		fileChooser = new JFileChooser();

		// create buttons
		openButton = new JButton("Open...");
		searchButton = new JButton("Search");
		nextSearchResult = new JButton("Next Search Result");
		nextRelevantParagraph = new JButton("Relevant Paragraph");

		// add listeners to the buttons
		openButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int retVal = fileChooser
							.showOpenDialog(InteligentSearchApp.this);

					if (retVal == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						String inputString = FileProcessor.process(file
								.getAbsolutePath());
						tokenizer = new SearchEngine(inputString);
						fileContentTextArea.setFont(new Font(
								FontFactory.defaultFont,
								FontFactory.defaultStyle,
								FontFactory.defaultSize));
						fileContentTextArea.setText(inputString);
						tokenizer.assignTfIdf();
						queryProcessor = new QueryProcessor(tokenizer);
						isFirstTimeQuery = true;

						// // fill content navigator
						NavigationItem[] elems = new NavigationItem[tokenizer
								.getDocumentUnits().size()];
						int index = 0;
						for (DocumentUnit unit : tokenizer.getDocumentUnits()) {
							elems[index] = new NavigationItem(unit, index + 1);
							index++;
						}
						navigationList.setListData(elems);
					}
				} catch (IllegalArgumentException e1) {
					System.out.println(e1.getMessage());
				}
			}

		});
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// if documents is open make search query
				if (tokenizer != null) {
					Set<String> queryBag = SearchEngine.stokanize(query
							.getText());
					queryProcessor.answer(queryBag);
					lastSearchedUnit = tokenizer.getDocumentUnits().get(0);
					highlightDocumentUnit(lastSearchedUnit);
					searchedIndex = 0;
				}
			}
		});
		nextSearchResult.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tokenizer != null) {
					if (searchedIndex < tokenizer.getDocumentUnits().size()) {
						lastSearchedUnit = tokenizer.getDocumentUnits().get(
								++searchedIndex);
						highlightDocumentUnit(lastSearchedUnit);
					}
				}
			}
		});

		nextRelevantParagraph.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (lastSearchedUnit != null) {
					System.out.println("here");
					lastSearchedUnit = queryProcessor
							.singleAnswer(lastSearchedUnit.getTerms().keySet());
					highlightDocumentUnit(lastSearchedUnit);
				}
			}

		});

		// Create combo boxes
		fontCombo = new JComboBox(
				new Vector<String>(FontFactory.fonts.keySet()));
		fontCombo.addActionListener(new ComboBoxActionListener());
		sizeCombo = new JComboBox(FontFactory.size);
		sizeCombo.addActionListener(new ComboBoxActionListener());

		// Upper panel
		JPanel upperPanel = new JPanel(new BorderLayout());

		// Action panel
		JPanel actionPanel = new JPanel();
		actionPanel.add(query);
		actionPanel.add(searchButton);
		actionPanel.add(nextSearchResult);
		actionPanel.add(nextRelevantParagraph);

		JPanel funcPanel = new JPanel();
		funcPanel.add(openButton);

		JPanel fontPanel = new JPanel();
		fontPanel.add(new JLabel("font: "));
		fontPanel.add(fontCombo);
		fontPanel.add(new JLabel("size"));
		fontPanel.add(sizeCombo);

		upperPanel.add(actionPanel, BorderLayout.CENTER);
		upperPanel.add(fontPanel, BorderLayout.EAST);
		upperPanel.add(funcPanel, BorderLayout.WEST);
		add(upperPanel, BorderLayout.NORTH);

		// panel with file content
		JPanel contentPanel = new JPanel(new BorderLayout());

		// add file content text area
		contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		contentPanel.add(new JScrollPane(fileContentTextArea),
				BorderLayout.WEST);
		contentPanel.add(new JScrollPane(navigationList), BorderLayout.EAST);

		add(contentPanel, BorderLayout.SOUTH);

	}

	private void highlightDocumentUnit(DocumentUnit documentUnit, HighlightPainter painter) {
		if (documentUnit != null) {
			Highlighter highlighter = fileContentTextArea.getHighlighter();
			highlighter.removeAllHighlights();
			try {
				highlighter.addHighlight(documentUnit.getSrart(), documentUnit
						.getEnd(), painter);
				fileContentTextArea
						.setCaretPosition((documentUnit.getSrart() + documentUnit
								.getEnd()) / 2);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}

		}
	}
	
	private void highlightDocumentUnit(DocumentUnit documentUnit) {
		HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
		highlightDocumentUnit(documentUnit,painter);
	}

	public static void createAndShowGui() {
		JFrame frame = new JFrame("Inteligent Search");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new InteligentSearchApp());
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * When the focus is gained for the first time clear the text area
	 * 
	 * @author mojorisin
	 * 
	 */
	private class FocusListenerImpl implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			if (isFirstTimeQuery) {
				query.setText(null);
				isFirstTimeQuery = false;
			}
		}

		@Override
		public void focusLost(FocusEvent e) {

		}

	}

	private class ComboBoxActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!fileContentTextArea.getText().isEmpty()) {
				String f = FontFactory.fonts.get(fontCombo.getSelectedItem()
						.toString());
				fileContentTextArea.setFont(new Font(f, Font.PLAIN,
						(Integer) sizeCombo.getSelectedItem()));
			}

		}

	}

	private class NavigationItem {
		private final DocumentUnit documentUnit;
		private String caption = null;

		public NavigationItem(final DocumentUnit unit, final int index) {
			documentUnit = unit;
			caption = "paragraph " + index;
		}

		@Override
		public String toString() {
			return caption;
		}

		public DocumentUnit getDocumentUnit() {
			return documentUnit;
		}
	}

}
