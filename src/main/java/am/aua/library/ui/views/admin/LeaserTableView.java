package am.aua.library.ui.views.admin;

import am.aua.library.models.Institution;
import am.aua.library.models.Leaser;
import am.aua.library.repositories.InstitutionRepository;
import am.aua.library.repositories.InstitutionRepositoryImpl;
import am.aua.library.repositories.LeaserRepositoryImpl;
import am.aua.library.ui.components.AbstractPage;
import am.aua.library.ui.components.ButtonEditor;
import am.aua.library.ui.components.ButtonRenderer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class LeaserTableView extends AbstractPage {
    private static final String[] COLUMN_NAMES = {
            "ID", "Full Name", "Institution", "Update", "Delete"
    };

    private JTable leaserTable;
    private JScrollPane scrollPane;
    private JTextField filterTextField;
    private DefaultTableModel leaserTableModel;
    private TableRowSorter<TableModel> rowSorter;

    private LeaserRepositoryImpl leaserRepository;
    private InstitutionRepository institutionRepository;

    private List<Leaser> leasers;

    private LeaserInfoView leaserInfoView;

    public LeaserTableView() {
        super("Leaser's view");
    }

    @Override
    protected void setup() {
        this.setLayout(new BorderLayout());
        this.leaserRepository = new LeaserRepositoryImpl();
        this.institutionRepository = new InstitutionRepositoryImpl();

        this.leasers = leaserRepository.findAll();

        this.leaserTableModel = new DefaultTableModel(this.getUpdatedLeasers(), COLUMN_NAMES);
        this.leaserTable = new JTable(this.leaserTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        AbstractPage currentLeaser = this;
        this.leaserTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                int row = leaserTable.rowAtPoint(evt.getPoint());
                int col = leaserTable.columnAtPoint(evt.getPoint());
                if(row >= 0 && col >= 0) {
                    Leaser selectedLeaser = leasers.get(row);
                    leaserInfoView = new LeaserInfoView(currentLeaser, selectedLeaser);
                }
            }
        });

        this.leaserTable.getActionMap().put("copy", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = LeaserTableView.this.leaserTable;
                String cellValue = table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString();
                StringSelection stringSelection = new StringSelection(cellValue);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, stringSelection);
            }
        });
        this.rowSorter = new TableRowSorter<>(this.leaserTable.getModel());
        this.leaserTable.setRowSorter(this.rowSorter);
        this.scrollPane = new JScrollPane(this.leaserTable);
    }

    @Override
    protected void addComponents() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.filterTextField = new JTextField();
        this.filterTextField.setBorder(new EmptyBorder(0, 20, 0, 0));
        JButton filterRowsButton = new JButton("Search");
        filterRowsButton.addActionListener(e -> this.filterLeasers());
        this.filterTextField.addActionListener(e -> this.filterLeasers());

        panel.add(new JLabel("Specify a word to match:"), BorderLayout.WEST);
        panel.add(this.filterTextField, BorderLayout.CENTER);
        panel.add(filterRowsButton, BorderLayout.EAST);

        this.add(panel, BorderLayout.SOUTH);
        this.add(this.scrollPane, BorderLayout.CENTER);
    }

    private Object[][] getUpdatedLeasers() {
        ArrayList<java.util.List<Object>> elements = new ArrayList<>();
        for (Leaser leaser : this.leaserRepository.findAll()) {
            elements.add(List.of(leaser.getId(), leaser.getFullName(), institutionRepository.get(leaser.getInstitutionId()).getName(), "Update", "Delete"));
        }

        Object[][] raw = new Object[elements.size()][];

        for (int i = 0; i < elements.size(); i++) raw[i] = elements.get(i).toArray();
        return raw;
    }

    private void filterLeasers() {

    }
}